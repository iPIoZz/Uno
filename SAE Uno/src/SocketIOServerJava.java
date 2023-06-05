import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;

public class SocketIOServerJava {

    private static SocketIOServer server;
    public static void start() {

        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(8081);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);

        server = new SocketIOServer(config);

        server.addEventListener("join", String.class, (client, data, ackSender) -> {


        });

        server.addEventListener("get-players", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("GET PLAYERS REQUESTS [" + s + "]");

                /* Find the game from UnoController._games */
                for (Game game : UnoController._games) {
                    if (game.getUUID().toString().equals(s)) {
                        System.out.println("GAME FOUND");
                        /* Send the players to the client */
                        socketIOClient.sendEvent("get-players", game.getPlayers());
                        break;
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
