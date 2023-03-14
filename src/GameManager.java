import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;

public class GameManager implements Runnable {
    private String gameStatus = "idle";
    // private Field gameField;
    private CustomFrame gameFrame;
    private JLabel gameStatusLabel;
    // private Player player;
    private String failState = "player failed";
    private String successState = "player succeeded";
    private TwoDimVal goalxyi;
    // private Battery playerBattery;

    public GameManager(TwoDimVal fieldDims) {
        // Create a new thread to run game manager in parallel
        Thread gameMangerThread = new Thread(this);

        // Set the playing field
        // this.gameField = new Field(fieldDims);

        // // Handle GUI >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // this.gameFrame = new CustomFrame("A Start Path Finding");
        // this.gameFrame.addItem(this.gameField.getFieldPanel(), "center");

        // // Create north panel
        // JPanel northPanel = new JPanel();
        // this.gameFrame.addItem(northPanel, "north");

        // // Create the battery for the player
        // this.playerBattery = new Battery();
        // northPanel.add(this.playerBattery.getGUI());

        // // Create south panel
        // JPanel southPanel = new JPanel();
        // this.gameFrame.addItem(southPanel, "south");

        // // Create the launch button
        // JButton launchBtn = this.gameFrame.createButton("Launch");
        // launchBtn.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         gameMangerThread.start();
        //         launchBtn.setVisible(false);
        //     }
        // });
        
        // // Adding the launch button to the south panel
        // southPanel.add(launchBtn);

        // // Setting game status label and adding it to the south panel
        // this.gameStatusLabel = this.gameFrame.createLabel("text", this.gameStatus);
        // southPanel.add(this.gameStatusLabel);

        // This should be called after all GUIs needed are setup
        this.gameFrame.finalizeFrameSetup();
        // -----------------------------------------------------------------------------
    }

    public void run() {
        this.launch();
    }

    private void launch() {
        // this.gameStatus = "Started";
        // this.gameFrame.updateLabel(this.gameStatusLabel, "text", this.gameStatus);

        // this.player = new Player(this);
        // this.gameField.spawnPlayer(this.player);

        // for (Cell curCell : this.gameField.getCellsArr()) {
        //     if (curCell.getType() == "goal") {
        //         this.goalxyi = new TwoDimVal(curCell.getDualIndex().getX(), curCell.getDualIndex().getY());
        //     }
        // }

        // while (!this.isGameOver()) {
        //     TwoDimVal nextPlayerDestination = this.player.setNextMove();
        //     TwoDimVal prevIndexPos = this.player.move(nextPlayerDestination);

        //     // Check if player stayed at the same cell
        //     if (this.player.isStuck(prevIndexPos) || (nextPlayerDestination.getX() < 0 && nextPlayerDestination.getY() < 0)) {
        //         if (!this.player.findOtherPath()) {
        //             setStatusToFail();
        //             break;
        //         }
        //     }

        //     // Check if player hit the goal
        //     if (this.player.hitCell(this.goalxyi)) setStatusToSuccess();

        //     this.gameField.detectPlayerMovement(this.player.getIndexPos(), prevIndexPos);

        //     try {
        //         Thread.sleep(900);
        //     } catch (InterruptedException e) {
        //         // TODO Auto-generated catch block
        //         e.printStackTrace();
        //     }
        // }

        // this.gameFrame.updateLabel(this.gameStatusLabel, "text", this.gameStatus);
    }

    private boolean isGameOver() {
        return this.gameStatus == this.failState || this.gameStatus == this.successState;
    }

    private void setStatusToFail() {
        this.gameStatus = this.failState;
    }

    private void setStatusToSuccess() {
        this.gameStatus = this.successState;
    }

    // Getters
    public String getGameStatus() {
        return this.gameStatus;
    }
    // Setters
    public void setGameStatus(String status) {
        this.gameStatus = status;
    }
}
