import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;

import static spark.Spark.*;

public class UnoController {
    private static ArrayList<User> users;
    private static HashMap<User, Player> players;
    private static HashMap<Player, Game> inGameUsers;

    public UnoController()
    {
        init();


        System.out.println("Server started");
        port(8080);

        get("/games", (req, res) -> {
            System.out.println(inGameUsers);
            res.type("application/json");
            Gson gson = new Gson();
            return gson.toJson(inGameUsers);
        });
    }

    public void init()
    {
        players = new HashMap<>();
        inGameUsers = new HashMap<>();
        users = new ArrayList<>();

        // Create factice game
        User owner = new User("Owner");
        Game game = new Game(owner);
        players.put(owner, game.getOwner());
        inGameUsers.put(game.getOwner(), game);
    }
}
