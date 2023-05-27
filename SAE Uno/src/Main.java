import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Main {
    private static ArrayList<User> users;
    private static HashMap<User, Player> players;
    private static HashMap<Player, Game> inGameUsers;
    public static void main(String[] args) {
        //init();

        UnoController controller = new UnoController();
    }

    private static void init()
    {
        users = new ArrayList<>();
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
}