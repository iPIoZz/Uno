package com.uno.models;

import com.uno.HTTPRequests;
import com.uno.enums.CardColour;

import java.util.ArrayList;
import java.util.UUID;

public class Player {
    private Long uid;
    private String username;
    private UUID sessionId;
    private GameSession gameSession = GameSession.getFromUUID(sessionId);
    private ArrayList<Card> deck = new ArrayList<>();

    public Player(Long uid, String username)
    {
        this.uid = uid;
        this.username = username;
    }

    public Long getUID() { return uid; }

    public String getUsername() { return username; }

    public UUID getSessionId() { return sessionId; }
    public void setSessionId(UUID sessionId)
    {
        if(!isAlreadyInSession())
            this.sessionId = sessionId;
    }
    public ArrayList<Card> getDeck()
    {
        return this.deck;
    }
    public void clearDeck()
    {
        deck.clear();
    }
    private void cardCode(Card card)
    {
        char type = card.getType();
        if(card.getColour() == CardColour.SPECIAL)
        {
            switch(type)
            {
                case 'd': // +4
                    gameSession.drawCard(gameSession.getNextPlayer(), gameSession.generateCard(), 4);
                    break;
                case 'b': // BLOCK
                    gameSession.blockNextPlayer();
                    break;
                case 'r': // REVERSE
                    gameSession.reverseOrder();
                    break;
                default:
                    return;
            }
        }
        else
        {
            if(type == 'd') // +2
                gameSession.drawCard(gameSession.getNextPlayer(), gameSession.generateCard(), 2);
            else
                return;
        }
        gameSession.setLastCard(card);
        gameSession.endPlayerTurn();
    }

    private void cardCode(Card card, CardColour colour)
    {
        char type = card.getType();
        if(card.getColour() == CardColour.SPECIAL && type == 'w')
        {
            gameSession.setWildColour(colour);
        }
    }
    public void useCard(Card card)
    {
        String cardColour = card.getColour().toString();
        char cardType = card.getType();

        deck.remove(card);
        if(card.getColour() != CardColour.SPECIAL)
        {
            try
            {
                Character.getNumericValue(card.getType());
                cardCode(card);
            }
            catch (Exception ex)
            {
                System.out.println("Non special card has non-numeric type.");
                return;
            }
        }
        else
        {
            if(!(cardType == 'w'))
            {
                cardCode(card);
            }
        }
        HTTPRequests.PlaceCard(cardColour, cardType);
        gameSession.setLastCard(card);
    }
    public void useCard(Card card, CardColour colour)
    {
        String cardColour = card.getColour().toString();
        char cardType = card.getType();

        deck.remove(card);
        if(card.getColour() == CardColour.SPECIAL && cardType == 'w')
        {
            cardCode(card, colour);
        }

        HTTPRequests.PlaceCard(cardColour, cardType);
        gameSession.setLastCard(card);
    }
    public Boolean isAlreadyInSession()
    {
        if(sessionId == null)
            return false;
        else return true;
    }
}
