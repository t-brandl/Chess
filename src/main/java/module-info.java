module de.hdmstuttgart.mi.se2.tb130.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens de.hdmstuttgart.mi.se2.tb130.chess to javafx.fxml;
    exports de.hdmstuttgart.mi.se2.tb130.chess;
}