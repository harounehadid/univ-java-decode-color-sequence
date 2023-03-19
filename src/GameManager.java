import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class GameManager implements Runnable {
    private String gameStatus = "idle";
    private CustomFrame gameFrame;
    private JLabel gameStatusLabel;
    private final String successState = "the secret code is found";
    private final int colorCodeLength = 7;
    private final int colorCodeCellsSize = 64;
    private int generationNum;

    public GameManager() {
        // Create a new thread to run game manager in parallel
        Thread gameMangerThread = new Thread(this);

        // // Handle GUI >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        this.gameFrame = new CustomFrame("Decode Color Sequence");
        // // Create the launch button
        // JButton launchBtn = this.gameFrame.createButton("Launch");
        // launchBtn.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         gameMangerThread.start();
        //         launchBtn.setVisible(false);
        //     }
        // });

        // // Setting game status label and adding it to the south panel
        // this.gameStatusLabel = this.gameFrame.createLabel("text", this.gameStatus);

        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.white);
        this.gameFrame.addItem(northPanel, "north");

        JPanel eastPanel = new JPanel();
        this.gameFrame.addItem(eastPanel, "east");
        
        // JPanel centerPanel = new JPanel();
        // this.gameFrame.addItem(centerPanel, "center");

        JPanel southPanel = new JPanel();
        this.gameFrame.addItem(southPanel, "south");

        JPanel westPanel = new JPanel();
        this.gameFrame.addItem(westPanel, "west");

        // East
        CodeBreaker codebreaker = new CodeBreaker(this.colorCodeLength, "Code Breaker", this.colorCodeCellsSize);
        eastPanel.add(codebreaker.getGUI());

        // North
        this.gameStatusLabel = new JLabel(this.gameStatus);
        this.generationNum = 0;
        JButton startBtn = new JButton("Start");
        startBtn.setBackground(Color.white);
        JLabel cPLabel = new JLabel("Generation: " + this.generationNum);
        northPanel.setLayout(new GridLayout(0, 5, 5, 5));
        northPanel.add(startBtn, BorderLayout.WEST);
        northPanel.add(this.gameStatusLabel, BorderLayout.WEST);
        northPanel.add(cPLabel, BorderLayout.CENTER);
        JButton restartBtn = new JButton("Restart");
        restartBtn.setBackground(Color.white);
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBackground(Color.white);
        northPanel.add(restartBtn, BorderLayout.EAST);
        northPanel.add(exitBtn, BorderLayout.EAST);

        // South
        Mutation mutation1 = new Mutation(colorCodeLength, "mutation #1", colorCodeCellsSize);
        southPanel.add(mutation1.getGUI());

        Mutation mutation2 = new Mutation(colorCodeLength, "mutation #2", colorCodeCellsSize);
        southPanel.add(mutation2.getGUI());

        Mutation mutation3 = new Mutation(colorCodeLength, "mutation #3", colorCodeCellsSize);
        southPanel.add(mutation3.getGUI());

        Mutation mutation4 = new Mutation(colorCodeLength, "mutation #4", colorCodeCellsSize);
        southPanel.add(mutation4.getGUI()); 

        southPanel.setLayout(new GridLayout(0, 2, 0, 0));

        // West
        CodeMaker codeMaker = new CodeMaker(this.colorCodeLength, "Code Maker", this.colorCodeCellsSize);
        westPanel.add(codeMaker.getGUI());

        this.gameFrame.finalizeFrameSetup();
        // -----------------------------------------------------------------------------
    }

    public void run() {
        this.launch();
    }

    private void launch() {
        this.gameStatus = "decoding";

        // while (!this.isGameOver()) {

        //     // Check if player stayed at the same cell
        //     if (this.player.isStuck(prevIndexPos) || (nextPlayerDestination.getX() < 0 && nextPlayerDestination.getY() < 0)) {
        //         if (!this.player.findOtherPath()) {
        //             setStatusToFail();
        //             break;
        //         }
        //     }

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
        return this.gameStatus == this.successState;
    }

    private void setStatusToSuccess() {
        this.gameStatus = this.successState;
    }
}
