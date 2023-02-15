package com.uno.enums;

public enum CardColour {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    SPECIAL;

    public String toString()
    {
        String colour = name().toLowerCase();
        return name().substring(0,1) + colour.substring(1);
    }
}
