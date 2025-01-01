package de.hdmstuttgart.mi.se2.tb130.chess;

import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * GUI for the Chess Program
 */
public class ChessGUI extends Application {

    private static Logger log = LogManager.getLogger(ChessGUI.class);

    private GridPane gridpane;
    private Label turnPreview;
    private volatile ControlFlow mainflow;
    private Button[][] playfield;
    private int fromX, fromY;
    private boolean click1Occured, whiteClickable, blackClickable, emptyClickable, loadToken;
    private EventHandler whiteFigureEvent, blackFigureEvent, emptyFigureEvent;

    /*
     * 0 = bb
     * 1 = bk
     * 2 = bn
     * 3 = bp
     * 4 = bq
     * 5 = br
     * 6 = wb
     * 7 = wk
     * 8 = wn
     * 9 = wp
     * 10 = wq
     * 11 = wr
     * 12 = empty
     */
    private HashMap<Integer, Image> images = new HashMap<Integer, Image>();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Visualize Playfield in GUI
     *
     * @param col  Color of Figure
     * @param xpos x-Position
     * @param ypos y-Position
     */
    private void initButton(char col, int xpos, int ypos) {
        log.debug("initialize Buttons");
        gridpane.add(playfield[xpos][ypos], xpos, ypos);
        playfield[xpos][ypos].setOnAction(event -> {
            if (!click1Occured && !emptyClickable) {
                log.debug("1st click by " + col + " at x: " + xpos + " y: " + ypos);
                fromX = xpos;
                fromY = ypos;
                click1Occured = true;
                if (mainflow.getTurn()) {
                    log.debug("1st click happened, whites turn");
                    whiteClickable = false;
                    blackClickable = true;
                    emptyClickable = true;
                } else {
                    log.debug("1st click happened, blacks turn");
                    whiteClickable = true;
                    blackClickable = false;
                    emptyClickable = true;
                }

            } else if (click1Occured) {
                log.debug("2nd click happened, making move");
                click1Occured = false;
                synchronized (mainflow) {
                    mainflow.makeMove(fromX, fromY, xpos, ypos);
                }
                playfield[xpos][ypos].setGraphic(playfield[fromX][fromY].getGraphic());
                playfield[xpos][ypos].removeEventFilter(ActionEvent.ACTION, emptyFigureEvent);
                playfield[xpos][ypos].removeEventFilter(ActionEvent.ACTION, whiteFigureEvent);
                playfield[xpos][ypos].removeEventFilter(ActionEvent.ACTION, blackFigureEvent);
                playfield[fromX][fromY].removeEventFilter(ActionEvent.ACTION, whiteFigureEvent);
                playfield[fromX][fromY].removeEventFilter(ActionEvent.ACTION, blackFigureEvent);
                if (mainflow.getFigureAt(xpos, ypos).getColor() == 'b') {
                    playfield[xpos][ypos].addEventFilter(ActionEvent.ACTION, blackFigureEvent);
                } else {
                    playfield[xpos][ypos].addEventFilter(ActionEvent.ACTION, whiteFigureEvent);
                }
                playfield[fromX][fromY].addEventFilter(ActionEvent.ACTION, emptyFigureEvent);
                playfield[fromX][fromY].setGraphic(new ImageView(images.get(12)));
                if ((mainflow.getFigureAt(xpos, ypos).getFigure() == 'P') && (ypos == 7)) {
                    promotionWindow(mainflow.getFigureAt(xpos, ypos).getColor(), xpos, ypos);
                } else if ((mainflow.getFigureAt(xpos, ypos).getFigure() == 'p') && (ypos == 0)) {
                    promotionWindow(mainflow.getFigureAt(xpos, ypos).getColor(), xpos, ypos);
                }
                if (mainflow.getTurn()) {
                    log.debug("2nd click happened, whites turn");
                    whiteClickable = true;
                    blackClickable = false;
                    emptyClickable = false;
                } else {
                    log.debug("2nd click happened, blacks turn");
                    whiteClickable = false;
                    blackClickable = true;
                    emptyClickable = false;
                }
            }
        });
        log.debug("Adding EventFilter");
        if (col == 'w') {
            playfield[xpos][ypos].addEventFilter(ActionEvent.ACTION, whiteFigureEvent);
        } else if (col == 'b') {
            playfield[xpos][ypos].addEventFilter(ActionEvent.ACTION, blackFigureEvent);
        } else {
            playfield[xpos][ypos].addEventFilter(ActionEvent.ACTION, emptyFigureEvent);
        }
    }

    /**
     * PopUp Dialog to exchange a pawn to another figure (Queen, Bishop, Knight, Rook)
     * upon reaching the other end of the playfield
     *
     * @param color Playercolor
     * @param posX  x-Position
     * @param posY  y-Position
     */
    private void promotionWindow(char color, int posX, int posY) {
        log.debug("Building Promotion Window");
        Button queen = new Button("");
        queen.setOnAction(event -> {
            if (color == 'w')
                promotingPawn(posX, posY, 'q', 10);
            else
                promotingPawn(posX, posY, 'Q', 4);
        });
        Button bishop = new Button("");
        bishop.setOnAction(event -> {
            if (color == 'w')
                promotingPawn(posX, posY, 'b', 6);
            else
                promotingPawn(posX, posY, 'B', 0);
        });
        Button knight = new Button("");
        knight.setOnAction(event -> {
            if (color == 'w')
                promotingPawn(posX, posY, 'n', 7);
            else
                promotingPawn(posX, posY, 'N', 1);
        });
        Button rook = new Button("");
        rook.setOnAction(event -> {
            if (color == 'w')
                promotingPawn(posX, posY, 'r', 11);
            else
                promotingPawn(posX, posY, 'R', 5);
        });

        if (color == 'b') {
            queen.setGraphic(new ImageView(images.get(4)));
            bishop.setGraphic(new ImageView(images.get(0)));
            knight.setGraphic(new ImageView(images.get(2)));
            rook.setGraphic(new ImageView(images.get(5)));
        } else {
            queen.setGraphic(new ImageView(images.get(10)));
            bishop.setGraphic(new ImageView(images.get(6)));
            knight.setGraphic(new ImageView(images.get(7)));
            rook.setGraphic(new ImageView(images.get(11)));
        }
        log.debug("Building Stage of Promotion Window");
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setId("promotionWindow");
        Label promLabel = new Label("Select your figure:");
        promLabel.setId("turnPrevLabel");
        HBox figureDisplay = new HBox(20);
        Region region0 = new Region();
        Region region1 = new Region();
        Region region2 = new Region();
        Region region3 = new Region();
        VBox.setVgrow(region0, Priority.ALWAYS);
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox.setHgrow(region3, Priority.ALWAYS);
        figureDisplay.setPadding(new Insets(0, 0, 25, 0));
        figureDisplay.getChildren().addAll(queen, region1, rook, region2, bishop, region3, knight);
        dialogVbox.getChildren().addAll(promLabel, region0, figureDisplay);
        Scene dialogScene = new Scene(dialogVbox, 600, 200);
        dialog.setScene(dialogScene);
        dialogScene.getStylesheets().add("guiCSS.css");
        dialog.setTitle("Select a Figure");
        log.debug("showing Stage of Promotion Window");
        dialog.show();
    }

    /**
     * Promotion Event
     *
     * @param posX     x-Position
     * @param posY     y-Position
     * @param figure   selected Figure
     * @param selected Chess Figure Icon ID
     */
    private void promotingPawn(int posX, int posY, char figure, int selected) {
        log.info("Promoting a pawn to " + figure);
        synchronized (mainflow) {
            mainflow.promotePawn(posX, posY, figure);
        }
        playfield[posX][posY].setGraphic(new ImageView(images.get(selected)));

    }

    /**
     * Cleaning the Playfield
     */
    private void deletePlayFieldContent() {
        log.debug("deleting Playfield Content");
        gridpane.getChildren().removeAll();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                playfield[i][j].setGraphic(new ImageView());
            }
        }
    }

    /**
     * reset Playfield to default starting position
     */
    private void resetPlayField() {
        log.debug("loading Chessfigures Icons for the playfield");
        if(!loadToken){
            synchronized (mainflow) {
                mainflow.initializeField();
            }

        }
        turnState();

        IFigure tempFigure;
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                tempFigure = mainflow.getFigureAt(i, j);

                switch (tempFigure.getFigure()) {
                    case 'b':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(6))));
                        break;
                    case 'B':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(0))));
                        break;
                    case 'k':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(7))));
                        break;
                    case 'K':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(1))));
                        break;
                    case 'n':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(8))));
                        break;
                    case 'N':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(2))));
                        break;
                    case 'p':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(9))));
                        break;
                    case 'P':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(3))));
                        break;
                    case 'q':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(10))));
                        break;
                    case 'Q':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(4))));
                        break;
                    case 'r':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(11))));
                        break;
                    case 'R':
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(5))));
                        break;
                    default:
                        playfield[i][j] = (new Button(" ", new ImageView(images.get(12))));
                        break;
                }
                initButton(tempFigure.getColor(), i, j);
            }
        }

    }

    /**
     * checks player turns, changes permissions based on it
     */
    private void turnState() {
        click1Occured = false;
        emptyClickable = false;
        log.info("changing Player");
        if (mainflow.getTurn()) {
            turnPreview.setText("Whites Turn");
            whiteClickable = true;
            blackClickable = false;
            emptyClickable = false;
        } else {
            turnPreview.setText("Blacks Turn");
            whiteClickable = false;
            blackClickable = true;
            emptyClickable = false;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        log.debug("Initializing Variables and Events");
        gridpane = new GridPane();
        playfield = new Button[8][8];
        mainflow = new ControlFlow();
        loadToken = false;
        whiteFigureEvent = event -> {
            if (!whiteClickable) {
                log.debug("consuming event white " + whiteClickable);
                event.consume();
            }
        };
        blackFigureEvent = event -> {
            if (!blackClickable) {
                log.debug("consuming event black " + blackClickable);
                event.consume();
            }
        };
        emptyFigureEvent = event -> {
            if (!emptyClickable) {
                log.debug("consuming event empty " + emptyClickable);
                event.consume();
            }
        };
        log.debug("Initialize Image Icon HashMap");
        images.put(0, new Image(getClass().getResourceAsStream("/images/bb.png")));
        images.put(1, new Image(getClass().getResourceAsStream("/images/bk.png")));
        images.put(2, new Image(getClass().getResourceAsStream("/images/bn.png")));
        images.put(3, new Image(getClass().getResourceAsStream("/images/bp.png")));
        images.put(4, new Image(getClass().getResourceAsStream("/images/bq.png")));
        images.put(5, new Image(getClass().getResourceAsStream("/images/br.png")));
        images.put(6, new Image(getClass().getResourceAsStream("/images/wb.png")));
        images.put(7, new Image(getClass().getResourceAsStream("/images/wk.png")));
        images.put(8, new Image(getClass().getResourceAsStream("/images/wn.png")));
        images.put(9, new Image(getClass().getResourceAsStream("/images/wp.png")));
        images.put(10, new Image(getClass().getResourceAsStream("/images/wq.png")));
        images.put(11, new Image(getClass().getResourceAsStream("/images/wr.png")));
        images.put(12, new Image(getClass().getResourceAsStream("/images/empty.png")));
        click1Occured = false;
        log.debug("initialize playfield");
        turnPreview = new Label("");
        resetPlayField();

        log.debug("Setting up Backup Thread");
        Thread t = new Thread(new BackupThread(mainflow), "Backup");
        t.setDaemon(true);
        t.start();

        log.debug("building GUI");
        Button switchTurn = new Button("End Turn");
        switchTurn.setId("turnButton");
        switchTurn.setOnAction(event -> {
            synchronized (mainflow) {
                mainflow.endTurn();
            }

            turnState();
        });
        turnPreview.setId("turnPrevLabel");
        log.debug("Building menuBar");
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("Menu");
        MenuItem menuSave = new MenuItem("Save to File");
        menuSave.setOnAction(event -> {
            synchronized (mainflow) {
                mainflow.saveGame("savedata.txt");
            }
        });
        MenuItem menuLoad = new MenuItem("Load from File");
        menuLoad.setOnAction(event -> {
            loadToken = true;
            synchronized (mainflow) {
                mainflow.loadGameFromFile("savedata.txt");
            }
            deletePlayFieldContent();
            resetPlayField();
            loadToken = false;
        });
        MenuItem menuLoadBackup = new MenuItem("Load from Backup");
        menuLoadBackup.setOnAction(event -> {
            loadToken = true;
            synchronized (mainflow) {
                mainflow.loadGameFromFile("backup.txt");
            }
            deletePlayFieldContent();
            resetPlayField();
            loadToken = false;
        });
        MenuItem menuReset = new MenuItem("Reset Field");
        menuReset.setOnAction(event -> {
            deletePlayFieldContent();
            resetPlayField();
        });
        menuFile.getItems().addAll(menuSave, menuLoad, menuLoadBackup, menuReset);
        menuBar.getMenus().addAll(menuFile);
        log.debug("Building Window");
        VBox root = new VBox(menuBar);
        gridpane.setId("gridp");
        gridpane.setAlignment(Pos.CENTER);
        VBox.setMargin(gridpane, new Insets(20, 0, 15, 0));
        VBox.setMargin(turnPreview, new Insets(10, 0, 10, 0));

        root.getChildren().add(gridpane);
        root.getChildren().add(turnPreview);
        root.getChildren().add(switchTurn);
        log.debug("Building Scene");
        Scene scene = new Scene(root, 1000, 900);
        scene.getStylesheets().add("guiCSS.css");
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
        log.debug("GUI built");
    }
}
