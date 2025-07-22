package de.brandl.tobias.chessGUI;

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
