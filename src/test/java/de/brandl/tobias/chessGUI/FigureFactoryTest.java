package de.brandl.tobias.chessGUI;

import org.junit.Test;
import org.junit.Assert;

public class FigureFactoryTest {

    @Test
    public void testFactoryGetFigure(){

        //bishop
        IFigure bishop = FigureFactory.getFigure('b', 'w');
        Assert.assertNotNull(bishop);
        Assert.assertSame(bishop.getFigure(), new Bishop('w', 'b').getFigure());

        //empty field
        IFigure emptyField = FigureFactory.getFigure('-', 'e');
        Assert.assertNotNull(emptyField);
        Assert.assertSame(emptyField.getFigure(), new EmptyField('e', '-').getFigure());

        //rook
        IFigure rook = FigureFactory.getFigure('r', 'w');
        Assert.assertNotNull(rook);
        Assert.assertSame(rook.getFigure(), new Rook('w', 'r').getFigure());

        //queen
        IFigure queen = FigureFactory.getFigure('Q', 'b');
        Assert.assertNotNull(queen);
        Assert.assertSame(queen.getFigure(), new Queen('b', 'Q').getFigure());

        //pawn
        IFigure pawn = FigureFactory.getFigure('p', 'w');
        Assert.assertNotNull(pawn);
        Assert.assertSame(pawn.getFigure(), new Pawn('w', 'p').getFigure());

        //knight
        IFigure knight = FigureFactory.getFigure('N', 'b');
        Assert.assertNotNull(knight);
        Assert.assertSame(knight.getFigure(), new Knight('b', 'N').getFigure());
    }
}
