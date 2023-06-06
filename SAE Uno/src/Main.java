import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Main {
    private static ArrayList<User> users;
    private static ArrayList<Game> games;
    private static HashMap<User, Player> players;
    private static HashMap<Player, Game> inGameUsers; //
    public static void main(String[] args) throws InterruptedException {
        init();

        UnoController.start();
    }

    private static void init()
    {
        users = new ArrayList<>();
        games = new ArrayList<>();
        players = new HashMap<>();
        inGameUsers = new HashMap<>();
    }

    public static User getUserFromName(String name)
    {
        for (User user :
                users) {
            if (Objects.equals(user.getName(), name))
                return user;
        }
        return null;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User u)
    {
        users.add(u);
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

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void addGame(Game game) {
        games.add(game);
    }
}