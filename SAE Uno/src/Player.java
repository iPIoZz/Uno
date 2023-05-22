import java.util.ArrayList;
import java.util.UUID;

public class Player extends User {
    ArrayList<Card> hand;
    public Player(String name, ArrayList<Card> hand) {
        super(name);
        this.hand = hand;
        Main.getPlayers().put(Main.getUserFromName(name), this);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
