package de.brandl.tobias.chessGUI;

/**
 * Knight Figures Class
 */
public class Knight implements IFigure {
    private final char col;
    private final char fig;

    protected Knight(char playerColor, char fig){
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
