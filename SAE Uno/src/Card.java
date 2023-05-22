import java.util.ArrayList;
import java.util.Random;

public class Card {
    private char type;
    private CardColour colour;

    public Card(char type, CardColour colour)
    {
        this.type = type;
        this.colour = colour;
    }

    public char getType() {
        return type;
    }

    public CardColour getColour() {
        return colour;
    }

    public boolean isSpecialColoured()
    {
        if((getType() != 'p' || getType() != 'r' || getType() != 's') && getColour() == CardColour.SPECIAL)
            return true;
        return false;
    }

    private static Card generateCard()
    {
        ArrayList<Card> cards = generateDeck();
        Random r = new Random();
        int index = r.nextInt(cards.size());
        return cards.get(index);
    }

    public static Card generateCard(Game game)
    {
        ArrayList<Card> useableCards = getUseableDeck(game.getUsedDeck());
        Random r = new Random();
        int index = r.nextInt(useableCards.size());
        return useableCards.get(index);
    }

    public static ArrayList<Card> generateHand()
    {
        ArrayList<Card> result = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            result.add(generateCard());
        }

        return result;
    }

    public static ArrayList<Card> generateDeck()
    {
        ArrayList<Card> deck = new ArrayList<>();

        for (CardColour colour :
                CardColour.values()) {

            if(colour == CardColour.SPECIAL)
            {
                for (int i = 0; i < 4; i++) {
                    deck.add(new Card('p', colour));
                    deck.add(new Card('w', colour));
                }
            }
            else {
                deck.add(new Card('0', colour));

                for (int i = 0; i < 2; i++) {
                    for (int j = 49; j < 58; j++) {
                        deck.add(new Card((char) j, colour));
                    }

                    deck.add(new Card('p', colour));
                    deck.add(new Card('r', colour));
                    deck.add(new Card('s', colour));
                }
            }
        }

        return deck;
    }

    private int maxCardsAmount(Card card) throws Exception
    {
        if(!(card.colour == CardColour.SPECIAL))
        {
            if(card.type == '0')
                return 1;
            else if(card.type < 10 || card.type == 'd' || card.type == 'r' || card.type == 's')
                return 2;
        }
        else {
            if(card.type == 'p' || card.type == 'w')
                return 4;
        }
        throw new Exception("Carte invalide: " + type + ", " + colour.toString());
    }
    
    private static ArrayList<Card> getUseableDeck(ArrayList<Card> usedCards)
    {
        ArrayList<Card> result = generateDeck();
            for (Card usedCard :
                    usedCards) {
                result.remove(usedCard);
            }
        return result;
    }

    public static ArrayList<Card> generateDeck(Game game)
    {
        return getUseableDeck(game.getUsedDeck());
    }

    public String toString()
    {
        String s = "Carte: ";
        switch(type)
        {
            case 'p':
                if(colour == CardColour.SPECIAL)
                    s += "+4 ";
                else
                    s += "+2 ";
                break;
            case 'r':
                s += "Reverse ";
                break;
            case 's':
                s += "Skip ";
                break;
            case 'w':
                s += "Wild ";
                break;
            default:
                s += type + " ";
                break;
        }
        s += getColour().name();
        return s;
    }
}
