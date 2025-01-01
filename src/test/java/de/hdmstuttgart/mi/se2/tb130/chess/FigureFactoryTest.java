package de.hdmstuttgart.mi.se2.tb130.chess;

import org.junit.Test;
import org.junit.Assert;

public class FigureFactoryTest {

    @Test
    public void testFactoryGetFigure(){
        Assert.assertSame(FigureFactory.getFigure('b', 'w').getFigure(), new Bishop('w', 'b').getFigure());
        Assert.assertSame(FigureFactory.getFigure('-', 'e').getFigure(), new EmptyField('e', '-').getFigure());
        Assert.assertSame(FigureFactory.getFigure('r', 'w').getFigure(), new Rook('w', 'r').getFigure());
        Assert.assertSame(FigureFactory.getFigure('q', 'w').getFigure(), new Queen('w', 'q').getFigure());
        Assert.assertSame(FigureFactory.getFigure('p', 'w').getFigure(), new Pawn('w', 'p').getFigure());
        Assert.assertSame(FigureFactory.getFigure('w', 'w').getFigure(), new Knight('w', 'w').getFigure());
    }
}
