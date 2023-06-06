import java.util.ArrayList;
import java.util.UUID;

public class Player extends User {
    ArrayList<Card> hand;
    public Player(User user, ArrayList<Card> hand) {
        super(user.getName());
        user.setBusy(true);
        this.hand = hand;
        Main.getPlayers().put(user, this);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
