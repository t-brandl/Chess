package de.brandl.tobias.chessGUI;

/**
 * empty Field Figure class
 */
public class EmptyField implements IFigure {
    private char col;
    private final char fig;

    protected EmptyField(char playerColor, char fig){
        col = playerColor;
        this.fig = fig;
    }

    public char getColor() {
        return col;
    }
    public char getFigure() {
        return fig;
    }
}
