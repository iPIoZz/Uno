import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Game {
    private final UUID uuid;
    private final Player owner;
    private String password;
    private final ArrayList<Card> deck;
    private final ArrayList<Card> usedDeck;
    private int currentTurn;
    private boolean reversed;
    private Card currentCard;
    private CardColour currentColour;
    private char currentType;
    private final GameState gameState;

    public Game(Player owner)
    {
        this.uuid = UUID.randomUUID();
        this.owner = owner;
        this.password = "";
        this.deck = Card.generateDeck();
        this.usedDeck = new ArrayList<>();
        this.currentTurn = 0;
        this.reversed = false;

        while(currentColour == null || currentColour == CardColour.SPECIAL || currentCard.isSpecialColoured())
        {
            Random r = new Random();
            placeCard(deck.get(r.nextInt(deck.size()))); // sets currentCard, currentColour and currentType
        }
        this.gameState = GameState.ONGOING;

        Main.addInGameUser(owner, this);
    }

    public Game(Player owner, String password)
    {
        this(owner);
        this.password = password;
    }

    public UUID getUUID() {
        return uuid;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> getUsedDeck() {
        return usedDeck;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public boolean isReversed() {
        return reversed;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public CardColour getCurrentColour() {
        return currentColour;
    }

    public GameState getGameState() {
        return gameState;
    }

    public ArrayList<Player> getPlayers()
    {
        ArrayList<Player> players = new ArrayList<>();
        for (Map.Entry<Player, Game> entry :
        Main.getInGameUsers().entrySet()){
            if(entry.getValue() == this)
                players.add(entry.getKey());
        }

        return players;
    }

    public boolean canBePlaced(Card card)
    {
        if(card.getColour() == CardColour.SPECIAL)
            return true;
        if(card.getColour() == getCurrentColour())
            return true;
        return card.getType() == getCurrentCard().getType();
    }

    public void useCard(Card card)
    {
        if(getDeck().contains(card))
        {
            getDeck().remove(card);
            getUsedDeck().add(card);
        }
    }

    public Player nextPlayer()
    {
        if(reversed)
            return getPlayers().get(getCurrentTurn()-1);
        else
            return getPlayers().get(getCurrentTurn()+1);
    }

    public void placeCard(Card card)
    {
        if(deck.contains(card) && canBePlaced(card))
        {
            useCard(card);
            currentCard = card;
            if(card.getColour() != CardColour.SPECIAL || card.isSpecialColoured()) {
                currentColour = card.getColour();
                currentType = card.getType();
            }
        }

        if(card.getColour() != CardColour.SPECIAL) {
            switch (card.getType()) {
                case 'p' -> drawCard(nextPlayer(), 2);
                case 's' -> currentTurn++;
                case 'r' -> reversed = !reversed;
                default -> {
                }
            }
        }else {
            switch (card.getType()) {
                case 'p' -> drawCard(nextPlayer(), 4);
                case 'w' -> Network.sendColorChooseRequest(this, nextPlayer());
            }
        }
        endRound();
    }

    private void endRound()
    {
        if(isReversed())
            currentTurn--;
        else
            currentTurn++;
    }

    public static void createPlayer(User user)
    {
        Player p = new Player(user.getName(), Card.generateHand());

        Game game = new Game(p);

        for (Card card :
                p.getHand()) {
            game.useCard(card);
        }
    }

    public static void createPlayer(User user, Game game)
    {
        if(game.getPlayers().size() >= 8 || game.getGameState() != GameState.WAITING)
            return;

        Player p = new Player(user.getName(), Card.generateHand());

        for (Card card :
                p.getHand()) {
            game.useCard(card);
        }

        Main.addInGameUser(p, game);
    }

    private void drawCard(Player player)
    {
        player.getHand().add(Card.generateCard(this));
    }

    private void drawCard(Player player, int amount)
    {
        for (int i = 0; i < amount; i++) {
            drawCard(player);
        }
    }
}
