package de.hdmstuttgart.mi.se2.tb130.chess;

/**
 * Bishop figures class
 */
public class Bishop implements IFigure {
    private final char col;
    private final char fig;

    protected Bishop(char playerColor, char fig){
        col = playerColor;
        this.fig = fig;
    }

    public char getColor() { return col; }
    public char getFigure(){
        return fig;
    }
}
