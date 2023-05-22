import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static ArrayList<User> users;
    private static HashMap<User, Player> players;
    private static HashMap<Player, Game> inGameUsers;
    public static void main(String[] args) {
        init();

        User user = new User("Léo");
        Game.createPlayer(user);

        System.out.println(inGameUsers.size());
        Player p = players.get(user);

        Game g = getInGameUsers().get(p);

        System.out.println(g.getCurrentCard().toString());
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
            if (user.getName() == name)
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