module de.brandl.tobias.chessGUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens de.brandl.tobias.chessGUI to javafx.fxml;
    exports de.brandl.tobias.chessGUI;
}