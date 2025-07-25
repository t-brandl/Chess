package de.brandl.tobias.chessGUI;

/**
 * Rook figures class
 */
public class Rook implements IFigure {
    private final char col;
    private final char fig;

    protected Rook(char playerColor, char fig){

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
