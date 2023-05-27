import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;

import static spark.Spark.*;

public class UnoController {
    private static ArrayList<User> users;
    private static HashMap<User, Player> players;
    private static HashMap<Player, Game> inGameUsers;

    private static ArrayList<Game> _games;

    public UnoController()
    {
        init();


        System.out.println("Server started");
        port(8080);

        get("/games", (req, res) -> {
            res.type("application/json");
            Gson gson = new Gson();
            System.out.println(gson.toJson(_games));
            return gson.toJson(_games);
        });
    }

    public void init()
    {
        players = new HashMap<>();
        inGameUsers = new HashMap<>();
        users = new ArrayList<>();
        _games = new ArrayList<>();

        // Create factice game
        User owner = new User("Owner");
        Game game = new Game(owner);
        _games.add(game);
        game.setNbPlayers(game.getNbPlayers() + 1);
        players.put(owner, game.getOwner());
        inGameUsers.put(game.getOwner(), game);
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
