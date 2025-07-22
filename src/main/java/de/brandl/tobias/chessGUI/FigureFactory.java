package de.brandl.tobias.chessGUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FigureFactory {

    private static final Logger log = LogManager.getLogger(FigureFactory.class);

    /**
     * Factory method for the chess figures.
     * Will select the following:
     * -   = Empty Field
     * b|B = Bishop
     * k|K = King
     * n|N = Knight
     * p|P = Pawn
     * q|Q = Queen
     * r|R = Rook
     * @param figure the selected figure
     * @param color color of the player, b for black, w for white, e for empty
     * @return selected chess figure in the specified player color
     */
    public static IFigure getFigure(char figure, char color){
        log.debug("Creating a Figure: {}", figure);
        if(color == 'e'){
            return new EmptyField(color, figure);
        } else if(color == 'w'){
            if(figure == 'b') {
                return new Bishop(color, figure);
            }else if (figure == 'k'){
                return new King(color, figure);
            }else if (figure == 'n'){
                return new Knight(color, figure);
            }else if (figure == 'p'){
                return new Pawn(color, figure);
            }else if (figure == 'q'){
                return new Queen(color, figure);
            }else if(figure == 'r'){
                return new Rook(color, figure);
            } else {
                log.error("Wrong white Figure selected: {}", figure);
            }
        } else if (color == 'b') {
            if(figure == 'B') {
                return new Bishop(color, figure);
            }else if (figure == 'K'){
                return new King(color, figure);
            }else if (figure == 'N'){
                return new Knight(color, figure);
            }else if (figure == 'P'){
                return new Pawn(color, figure);
            }else if (figure == 'Q'){
                return new Queen(color, figure);
            }else if(figure == 'R'){
                return new Rook(color, figure);
            } else {
                log.error("Wrong black Figure selected: {}", figure);
            }
        } else {
            log.error("Wrong Color selected: {}", color);
        }


        return null;
    }
}
