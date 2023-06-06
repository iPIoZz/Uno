import com.google.gson.Gson;

import java.util.UUID;

import static spark.Spark.*;
public class SparkServer {

    public static void start() {

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


        get("/games", (req, res) -> {
            res.type("application/json");
            Gson gson = new Gson();
            System.out.println(gson.toJson(UnoController._games));
            return gson.toJson(UnoController._games);
        });

        post("/games/:uuid/join", (req, res) -> {

            res.type("application/json");
            Gson gson = new Gson();
            System.out.println(req.body());

            String[] splitted = req.body().split("&");
            String username = splitted[0].split("=")[1];
            String uuids = splitted[1].split("=")[1];
            UUID uuid = UUID.fromString(uuids);

            // Check if user already exists
            for(User u : UnoController.users)
            {
                if(u.getName().equals(username))
                {
                    return gson.toJson("error");
                }
            }

            /* Create user and game */
            User user = new User(username, uuid);
            UnoController.users.add(user);

            Game game = null;
            for(Game g : UnoController._games)
            {
                if(g.getUUID().toString().equals(req.params(":uuid")))
                {
                    game = g;
                    break;
                }
            }

            if(game == null)
            {
                return gson.toJson("error");
            }

            UnoController.players.put(user, (Player) user);
            UnoController.inGameUsers.put(game.getOwner(), game);
            game.setNbPlayers(game.getNbPlayers() + 1);

            return gson.toJson(game.getUUID());
        });

        get("/game/:uuid", (req, res) -> {

            res.type("application/json");
            Game game = null;
            for(Game g : UnoController._games)
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
            for(User u : UnoController.users)
            {
                if(u.getName().equals(username))
                {
                    return gson.toJson("error");
                }
            }

            /* Create user and game */
            User user = new User(username, uuid);
            Game game = new Game(user);
            return gson.toJson(game.getUUID());
        });
    }
}
