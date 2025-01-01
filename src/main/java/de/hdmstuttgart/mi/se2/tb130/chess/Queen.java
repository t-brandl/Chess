package de.hdmstuttgart.mi.se2.tb130.chess;

/**
 * Queen Figures class
 */
public class Queen implements IFigure {
    private final char col;
    private final char fig;

    protected Queen(char playerColor, char fig){
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
