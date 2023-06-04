import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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

        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        /*
        Get players from the game
         */

        get("/games/:uuid/players", (req, res) -> {
            res.type("application/json");
            Gson gson = new Gson();
            Game game = null;
            for(Game g : _games)
            {
                if(g.getUUID().toString().equals(req.params(":uuid")))
                {
                    game = g;
                    break;
                }
            }
            return gson.toJson(game.getPlayers());
        });

        post("/games/:uuid/join", (req, res) -> {

            res.type("application/json");
            Gson gson = new Gson();
            Game game = null;
            for(Game g : _games)
            {
                if(g.getUUID().toString().equals(req.params(":uuid")))
                {
                    game = g;
                    break;
                }
            }
            String[] splitted = req.body().split("&");
            String username = splitted[0].split("=")[1];
            String uuids = splitted[1].split("=")[1];
            UUID uuid = UUID.fromString(uuids);

            // Check if user already exists
            for(User u : users)
            {
                if(u.getName().equals(username))
                {
                    return gson.toJson("error");
                }
            }

            User user = new User(username, uuid);
            users.add(user);
            Player player = new Player(user.getName(), Card.generateHand());
            players.put(user, player);
            inGameUsers.put(player, game);
            game.setNbPlayers(game.getNbPlayers() + 1);

            return gson.toJson(game.getUUID());
        });

        get("/games", (req, res) -> {
            res.type("application/json");
            Gson gson = new Gson();
            System.out.println(gson.toJson(_games));
            return gson.toJson(_games);
        });

        get("/game/:uuid", (req, res) -> {

            res.type("application/json");
            Game game = null;
            for(Game g : _games)
            {
                if(g.getUUID().toString().equals(req.params(":uuid")))
                {
                    game = g;
                    break;
                }
            }
            Gson gson = new Gson();
            return gson.toJson(game);
        });

        post("/create", (req, res) -> {

            res.type("application/json");
            Gson gson = new Gson();
            System.out.println(req.body());

            String[] splitted = req.body().split("&");
            String username = splitted[0].split("=")[1];
            String uuids = splitted[1].split("=")[1];
            UUID uuid = UUID.fromString(uuids);

            // Check if user already exists
            for(User u : users)
            {
                if(u.getName().equals(username))
                {
                    return gson.toJson("error");
                }
            }

            /* Create user and game */
            User user = new User(username, uuid);
            users.add(user);

            Game game = new Game(user);
            _games.add(game);
            players.put(user, game.getOwner());
            inGameUsers.put(game.getOwner(), game);
            game.setNbPlayers(game.getNbPlayers() + 1);


            return gson.toJson(game.getUUID());
        });
    }

    public void init()
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
