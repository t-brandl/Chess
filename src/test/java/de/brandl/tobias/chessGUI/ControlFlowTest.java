package de.brandl.tobias.chessGUI;

import org.junit.Test;
import org.junit.Assert;

public class ControlFlowTest {

    @Test
    public void testMakeMove(){
        ControlFlow testFlow = new ControlFlow();
        int xPawn = 1;
        int yPawn = 0;
        int pawnToX = 3;
        int pawnToY = 0;
        IFigure pawnStart = testFlow.getFigureAt(xPawn, yPawn);
        IFigure emptyStart = testFlow.getFigureAt(pawnToX, pawnToY);

        Assert.assertNotSame(pawnStart.getFigure(), emptyStart.getFigure());
        testFlow.makeMove(xPawn, yPawn, pawnToX, pawnToY);
        Assert.assertNotSame(pawnStart.getFigure(), emptyStart.getFigure());
        Assert.assertSame(pawnStart.getFigure(), testFlow.getFigureAt(pawnToX, pawnToY).getFigure());
        Assert.assertSame(FigureFactory.getFigure('-', 'e').getFigure(), testFlow.getFigureAt(xPawn, yPawn).getFigure());


    }

    @Test
    public void testInitialize(){
        ControlFlow testFlow = new ControlFlow();

        for(int i = 2; i < 6; i++){
            for(int j = 0; j < 8; j++){
                Assert.assertSame( testFlow.getFigureAt(j, i).getFigure(), FigureFactory.getFigure('-', 'e').getFigure());
            }
        }
    }

    @Test
    public void testPawnPromote(){
        ControlFlow testFlow = new ControlFlow();
        int y = 1;
        int x = 0;
        int yNew = 7;
        int xNew = 0;

        Assert.assertSame(testFlow.getFigureAt(x, y).getFigure(), FigureFactory.getFigure('P', 'b').getFigure());
        testFlow.makeMove(x, y, xNew, yNew);
        Assert.assertSame(testFlow.getFigureAt(xNew, yNew).getFigure(), FigureFactory.getFigure('P', 'b').getFigure());
        testFlow.promotePawn(xNew, yNew, 'Q');
        Assert.assertSame(testFlow.getFigureAt(xNew, yNew).getFigure(), FigureFactory.getFigure('Q', 'b').getFigure());
    }

    @Test
    public void testCorrectFigureCount(){
        ControlFlow testFlow = new ControlFlow();
        long temp = testFlow.getFigureCount('w');
        long test = 16;
        Assert.assertSame(temp, test);
    }

    @Test
    public void testChangedFigureCount() {
        ControlFlow testFlow = new ControlFlow();
        long test = 15;
        testFlow.makeMove(1, 1, 6, 6);
        long temp = testFlow.getFigureCount('w');
        Assert.assertSame(temp, test);
    }

    @Test
    public void testFigureCount(){
        ControlFlow testFlow = new ControlFlow();
        long test = 16;
        testFlow.makeMove(1, 1, 6, 6);
        long temp = testFlow.getFigureCount('w');
        Assert.assertFalse(temp == test);
    }

    @Test
    public void negativeTestMakeMove(){
        ControlFlow testFlow = new ControlFlow();
        int x = 0;
        int y = 1;
        int xnew = -5;
        int ynew = 8;
        IFigure test = testFlow.getFigureAt(x, y);
        testFlow.makeMove(x, y, xnew, ynew);
        Assert.assertEquals(test.getFigure(), testFlow.getFigureAt(x, y).getFigure());

    }
}
