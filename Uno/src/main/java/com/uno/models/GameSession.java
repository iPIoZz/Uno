package com.uno.models;

import com.uno.Main;
import com.uno.enums.CardColour;
import com.uno.enums.GameState;

import java.util.*;

public class GameSession {
    private UUID gameId;
    private Player owner;
    private HashMap<Player, Boolean> players = new HashMap();
    private HashMap<Card, Integer> deck = new HashMap<>();
    private int currentTurn;
    private Boolean reversed;
    private Rules rules;
    private GameState gameState;
    private Card lastCard;
    private CardColour currentColour;

    public GameSession(Player owner)
    {
        this.gameId = UUID.randomUUID();
        this.owner = owner;
        this.players.put(owner, false);
        this.gameState = GameState.WAITING;
        this.reversed = false;
        this.currentTurn = 0;
        generateDeck();
    }

    private void generateDeck()
    {
        for (CardColour color :
                CardColour.values()) {
            if(color != CardColour.SPECIAL)
            {
                for (int i = 0; i < 10; i++) {
                    deck.put(new Card(color, (char) (i+'0')), 4);
                }
                deck.put(new Card(color, 'p'), 4); // +2
            }
            else
            {
                deck.put(new Card(color, 'p'), 4); // +4
                deck.put(new Card(color, 'b'), 4); // block
                deck.put(new Card(color, 'r'), 4); // reverse
                deck.put(new Card(color, 'w'), 4); // wild
            }
        }
    }
    public HashMap<Player, Boolean> getPlayers()
    {
        return players;
    }
    public Player[] getPlayersArray()
    {
        return (Player[]) getPlayers().keySet().toArray();
    }
    public void addPlayer(Player player) {
        this.players.put(player, false);
        if(player.getSessionId() != null)
            player.setSessionId(this.gameId);
        player.clearDeck();
    }
    public void removePlayer(Player player) {
        this.players.remove(player);
        player.setSessionId(null);
    }

    public void startGame(Rules rules) {
        this.rules = rules;
        this.gameState = GameState.ONGOING;
    }

    public void endPlayerTurn()
    {
        if(!reversed)
        {
            currentTurn++;
        }
        else
        {
            currentTurn--;
        }
    }

    public void reverseOrder()
    {
        reversed = !reversed;
    }
    public void blockNextPlayer()
    {
        endPlayerTurn();
    }
    public Player getNextPlayer()
    {
        int player = (currentTurn+1)%players.size();

        return getPlayersArray()[player];
    }
    public Card getLastCard()
    {
        return lastCard;
    }

    public void setLastCard(Card card)
    {
        lastCard = card;
        currentColour = card.getColour();
    }

    public void endGame() {
        this.gameState = GameState.WAITING;
    }

    public void closeGame() {
        for (Player p :
                this.players.keySet()) {
            removePlayer(p);
        }
        this.gameState = GameState.CLOSED;
    }
    public void drawCard(Player p, Card card)
    {
        p.getDeck().add(generateCard());
    }
    public void drawCard(Player p, Card card, int amount)
    {
        for (int i = 0; i < amount; i++)
        {
            p.getDeck().add(generateCard());
        }
    }
    public Card generateCard()
    {
        Random r = new Random();
        int index = r.nextInt(deck.size());

        Card[] keySet = (Card[]) deck.keySet().toArray();

        Card card = keySet[index];

        deck.replace(card, deck.get(card)-1);

        return keySet[index];
    }

    public CardColour getCurrentColour()
    {
        return currentColour;
    }

    private void setCurrentColour(CardColour colour)
    {
        currentColour = colour;
    }
    public void setWildColour(CardColour colour)
    {
        if(colour == CardColour.SPECIAL)
            return;
        else
        {
            currentColour = colour;
        }
    }

    public static GameSession getFromUUID(UUID uuid) { return Main.getGames().get(uuid); }
}
