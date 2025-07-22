package de.brandl.tobias.chessGUI;

/**
 * Pawn Figures Class
 */
public class Pawn implements IFigure {
    private final char col;
    private final char fig;

    protected Pawn(char playerColor, char fig){
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
