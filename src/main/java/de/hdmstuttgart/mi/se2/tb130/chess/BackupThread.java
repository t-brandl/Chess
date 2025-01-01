package de.hdmstuttgart.mi.se2.tb130.chess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Creates a backup after 45 seconds every 5 seconds
 */
public class BackupThread extends Thread {
    private static Logger log = LogManager.getLogger(BackupThread.class);
    private ControlFlow mainflow;

    public BackupThread(ControlFlow mainflow) {
        this.mainflow = mainflow;
    }

    @Override
    public void run() {
        log.debug("starting Backup Thread");
        try{
            sleep(45000);
        } catch (InterruptedException e){
            log.error("Cannot sleep for some reason" + e);
        }
        while (true) {

            createBackup();
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                log.error("Cannot sleep for some reason " + e);
            }
        }
    }

    /**
     * Creates the synchronized backup every 5 seconds
     */
    private void createBackup() {
        synchronized (mainflow) {
            mainflow.saveGame("backup.txt");
        }
    }
}
