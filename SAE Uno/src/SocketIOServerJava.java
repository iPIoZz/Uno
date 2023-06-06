import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.ArrayList;
import java.util.UUID;

public class SocketIOServerJava {

    private static SocketIOServer server;
    public static void start() {

        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(8081);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);

        server = new SocketIOServer(config);

        server.addEventListener("join", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {

                String[] splitted = s.split("&");
                String username = splitted[0].split("=")[1];
                String uuids = splitted[1].split("=")[1];
                String gameUuids = splitted[2].split("=")[1];
                UUID uuid = UUID.fromString(uuids);
                UUID gameUuid = UUID.fromString(gameUuids);

                System.out.println("JOIN REQUESTS [FROM USER - " + uuid + " FOR THE GAME - ]");

                // Check if user already exists
                for(User u : Main.getUsers())
                {
                    if(u.getName().equals(username))
                    {
                        socketIOClient.sendEvent("join", "already-exists-user");
                        return;
                    }
                }

                // Check if game exists
                for (Game game : Main.getGames())
                {
                    if (game.getUUID() == gameUuid)
                    {
                        // Check if game is full
                        if (game.getPlayers().size() >= 4)
                        {
                            socketIOClient.sendEvent("join", "error");
                            return;
                        }
                        // Add user to game
                        game.createPlayer(new User(username, uuid));
                        socketIOClient.sendEvent("join-players", game.getPlayers());
                        return;
                    }
                }
                socketIOClient.sendEvent("join", "error");
            }
        });

        server.addEventListener("get-players", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("GET PLAYERS REQUESTS [" + s + "]");


                for (Game game : Main.getGames())
                {
                    if (game.getUUID() == UUID.fromString(s))
                    {
                        ArrayList<String> players = new ArrayList<>();
                        for (Player player : game.getPlayers())
                        {
                            players.add(player.getName());
                        }
                        socketIOClient.sendEvent("get-players", players);
                    }
                }
            }
        });

        server.start();
    }

    public static void stop() {
        server.stop();
    }
}
