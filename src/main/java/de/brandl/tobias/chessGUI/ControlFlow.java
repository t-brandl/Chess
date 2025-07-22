package de.brandl.tobias.chessGUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

/**
 * Controls the mainflow of the program
 */
public class ControlFlow {

    private static final Logger log = LogManager.getLogger(ControlFlow.class);
    private IFigure[][] playfield;
    private boolean turnToken;


    public ControlFlow() {
        initializeField();
    }

    /**
     * returns Figure at the Coordinates (xPos|yPos)
     *
     * @param xPos X-Axis Coordinate
     * @param yPos Y-Axis Coordinate
     * @return Figure at (xPos|yPos)
     */
    protected final IFigure getFigureAt(int xPos, int yPos) {
        log.info("returning Figure{} at x: {}, y: {}", playfield[xPos][yPos].getFigure(), xPos, yPos);
        return FigureFactory.getFigure(playfield[xPos][yPos].getFigure(), playfield[xPos][yPos].getColor());
    }

    /**
     * This method calculates the amount of figures a player has, using Streams, based on the current playfield
     * This method exists solely for the purpose of using Streams for the 3 points in that category,
     * as I chose to go with a 2D-Array for my playfield, instead of a list
     *
     * @return amount of figures a player currently has
     */
    public long getFigureCount(char playerColor) {
        LinkedList<IFigure> list = figsToList();
        long count = list.parallelStream().filter(item -> (item.getColor() == playerColor)).count();
        log.info("counted {} figues for {}", count, playerColor);
        return count;
    }

    /**
     * Turns the 2D Playfield Array into a LinkedList
     *
     * @return LinkedList of the playfield
     */
    private LinkedList<IFigure> figsToList() {
        LinkedList<IFigure> list = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                list.addLast(playfield[i][j]);
            }
        }
        return list;
    }

    /**
     * Returns the currently on the playfield existing figures of a player
     * Not in use, because I chose to use a 2D Array as playfield
     *
     * @param playerColor color of the player
     * @return returns a players figures
     */
    private List<IFigure> getOnePlayersFigures(char playerColor) {
        LinkedList<IFigure> list = figsToList();
        List<IFigure> fig = list.parallelStream().filter(item -> (item.getColor() == playerColor)).collect(Collectors.toList());
        log.info("Current lost contains for {}: {}", playerColor, fig.toString());
        return fig;
    }

    /**
     * Places chess figures on the field to form the initial starting Position.
     * First turn goes to white
     */
    protected void initializeField() {
        log.debug("initializing Field");

        playfield = new IFigure[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                playfield[i][j] = FigureFactory.getFigure('-', 'e');
            }
        }
        for (int i = 0; i < 8; i++) {
            playfield[i][1] = FigureFactory.getFigure('P', 'b');
            playfield[i][6] = FigureFactory.getFigure('p', 'w');
        }
        for (int i = 0; i < 3; i++) {
            if (i % 3 == 0) {
                playfield[i][0] = FigureFactory.getFigure('R', 'b');
                playfield[i][7] = FigureFactory.getFigure('r', 'w');
                playfield[i + 5][0] = FigureFactory.getFigure('B', 'b');
                playfield[i + 5][7] = FigureFactory.getFigure('b', 'w');
            } else if (i % 3 == 1) {
                playfield[i][0] = FigureFactory.getFigure('N', 'b');
                playfield[i][7] = FigureFactory.getFigure('n', 'w');
                playfield[i + 5][0] = FigureFactory.getFigure('N', 'b');
                playfield[i + 5][7] = FigureFactory.getFigure('n', 'w');
            } else {
                playfield[i][0] = FigureFactory.getFigure('B', 'b');
                playfield[i][7] = FigureFactory.getFigure('b', 'w');
                playfield[i + 5][0] = FigureFactory.getFigure('R', 'b');
                playfield[i + 5][7] = FigureFactory.getFigure('r', 'w');
            }
        }
        playfield[4][0] = FigureFactory.getFigure('K', 'b');
        playfield[3][0] = FigureFactory.getFigure('Q', 'b');
        playfield[4][7] = FigureFactory.getFigure('k', 'w');
        playfield[3][7] = FigureFactory.getFigure('q', 'w');
        turnToken = true;
        log.debug("field in default starting Position");
    }

    /**
     * Moves a figure from Point A (fromX | fromY) to Point B (toX | toY)
     *
     * @param fromX X-axis point where the figure is placed
     * @param fromY Y-axis point where the figure is placed
     * @param toX   X-axis point where the figure wants to go
     * @param toY   Y-axis point where the figure wants to go
     */
    protected void makeMove(int fromX, int fromY, int toX, int toY) {
        log.info("Moving the Figure: {} from x:{} y:{} to x:{} y:{}", playfield[fromX][fromY].getFigure(), fromX, fromY, toX, toY);
        if ((!checkValidMove(fromX, fromY)) || (!checkValidMove(toX, toY))) {
            return;
        }
        playfield[toX][toY] = playfield[fromX][fromY];
        playfield[fromX][fromY] = FigureFactory.getFigure('-', 'e');
    }

    /**
     * Promotes a Pawn, which reached the opposite top of the field, to another figure
     *
     * @param toX    X-Destination of the Pawn
     * @param toY    Y-Destination of the Pawn
     * @param figure Figure to be promoted
     */
    protected void promotePawn(int toX, int toY, char figure) {
        log.info("Promoting Pawn at x:{} y:{}", toX, toY);
        playfield[toX][toY] = FigureFactory.getFigure(figure, playfield[toX][toY].getColor());
    }

    /**
     * saves the current state of the chess field to a file in the same directory named "savedata.txt"
     * lowercase Letters are whites figures
     * uppercase ones are blacks figures
     *
     * @param fileName name of the savefile
     */
    protected void saveGame(String fileName) {
        try (PrintWriter write_line = new PrintWriter(new FileWriter(fileName))) {
            log.info("Saving game to savefile named: {}", fileName);
            StringBuilder saveLine = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    saveLine.append(playfield[j][i].getFigure());
                }
                log.info("Writing following to savefile : {}", saveLine);
                write_line.printf("%s" + "%n", saveLine);
                saveLine = new StringBuilder();
            }
            if (turnToken) {
                write_line.printf("w");
            } else {
                write_line.printf("b");
            }
        } catch (Exception e) {
            log.error("Can't write to file {}", String.valueOf(e));
        }
    }

    /**
     * loads the game from a local file , which lies in the same folder as the project.
     *
     * @param file filename of the savefile
     */
    protected void loadGameFromFile(String file) {
        IFigure[][] loadedPlayfield = new IFigure[8][8];
        Set<Character> blackFigures = Set.of('B', 'K', 'N', 'P', 'Q', 'R');
        Set<Character> whiteFigures = Set.of('b', 'k', 'n', 'p', 'q', 'r');
        boolean loadedTurnToken = false;
        log.info("Loading game from savefile: {}", file);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int row = 0;

            // Read each line and process it
            while ((line = br.readLine()) != null) {
                log.info("Reading from line {}: {}", row, line);
                if (row < 8) {
                    // For the first 8 rows, populate the board
                    for (int col = 0; col < line.length() && col < 8; col++) {
                        if (blackFigures.contains(line.charAt(col))) {
                            loadedPlayfield[col][row] = FigureFactory.getFigure(line.charAt(col), 'b');
                        } else if (whiteFigures.contains(line.charAt(col))) {
                            loadedPlayfield[col][row] = FigureFactory.getFigure(line.charAt(col), 'w');
                        } else if (line.charAt(col) == '-') {
                            loadedPlayfield[col][row] = FigureFactory.getFigure('-', 'e');
                        } else {
                            throw new IOException("Illegal Character detected in savefile: " + file + "\n Given row: " + line + "\n Permitted characters: b,k,n,p,q,r, B, K, N, P, Q, R.");
                        }
                    }
                    row++;
                } else if (row == 8) {
                    // The last line contains the turn token
                    if (!line.isEmpty()) {
                        if (line.charAt(0) == 'b') {
                            loadedTurnToken = false;
                        } else if (line.charAt(0) == 'w') {
                            loadedTurnToken = true;
                        } else {
                            throw new IOException("Illegal Character for the turn indicator in save file: " + file + "\n Given character: " + line + "\n Given character should be b or w.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }
        log.info("Assigning playfield and turn token");
        playfield = loadedPlayfield;
        turnToken = loadedTurnToken;
    }

    /**
     * ends the turn for the current player, which means it's the opposing players turn
     */
    protected void endTurn() {
        log.info("Ending Turn");
        turnToken = !turnToken;
    }

    /**
     * white = true, black = false
     *
     * @return true for white, false for black
     */
    protected boolean getTurn() {
        log.info("getting Turn");
        return turnToken;
    }

    /**
     * checks if move is in bound of array
     *
     * @param a X-axis coordinate
     * @param b Y-axis coordinate
     * @return true if valid move, else false
     */
    private boolean checkValidMove(int a, int b) {
        return a >= 0 && a < 8 && b >= 0 && b < 8;
    }
}
