package com.uno.models;

import com.uno.enums.CardColour;

public class Card {

    private CardColour colour;
    private char type;

    public Card(CardColour colour, char type)
    {
        this.colour = colour;
        this.type = type;
    }

    public CardColour getColour() {
        return colour;
    }

    public char getType() {
        return type;
    }
}
