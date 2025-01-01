package de.hdmstuttgart.mi.se2.tb130.chess;

/**
 * King figures class
 */
public class King implements IFigure {
    private final char col;
    private final char fig;

    protected King(char playerColor, char fig){
        col = playerColor;
        this.fig = fig;
    }

    public char getColor() {
        return col;
    }

    public char getFigure(){
        return fig;
    }
}
