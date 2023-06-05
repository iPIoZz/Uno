import java.util.ArrayList;
import java.util.HashMap;

import com.corundumstudio.socketio.*;

public class UnoController {
    public static ArrayList<User> users;
    public static HashMap<User, Player> players;
    public static HashMap<Player, Game> inGameUsers;

    public static ArrayList<Game> _games;

    public UnoController() throws InterruptedException {

    }

    public static void start() throws InterruptedException
    {
        init();;
        Thread sparkThread = new Thread(SparkServer::start);
        Thread socketThread = new Thread(SocketIOServerJava::start);

        sparkThread.start();
        socketThread.start();
    }

    public static void init()
    {
        players = new HashMap<>();
        inGameUsers = new HashMap<>();
        users = new ArrayList<>();
        _games = new ArrayList<>();

        /*// Create factice game
        User owner = new User("Owner");
        Game game = new Game(owner);
        _games.add(game);
        game.setNbPlayers(game.getNbPlayers() + 1);
        players.put(owner, game.getOwner());
        inGameUsers.put(game.getOwner(), game);*/
    }

    public static HashMap<User, Player> getPlayers() {
        return players;
    }

    public static HashMap<Player, Game> getInGameUsers() {
        return inGameUsers;
    }

    public static void addInGameUser(Player p, Game game)
    {
        inGameUsers.put(p, game);
    }
}
