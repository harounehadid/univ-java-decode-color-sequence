import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JLabel generationLabel;
    private CodeMaker codeMaker;
    private CodeBreaker codeBreaker;
    private ArrayList<Mutation> mutationsList;

    public GameManager() {
        // Create a new thread to run game manager in parallel
        Thread gameMangerThread = new Thread(this);

        mutationsList = new ArrayList<Mutation>();
        this.generationLabel = new JLabel("Generation: " + 0);

        // // Handle GUI >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        this.gameFrame = new CustomFrame("Decode Color Sequence");

        // North
        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.white);
        this.gameFrame.addItem(northPanel, "north");

        this.gameStatusLabel = new JLabel(this.gameStatus);
        JButton startBtn = new JButton("Start");
        startBtn.setBackground(Color.white);
        northPanel.setLayout(new GridLayout(0, 5, 5, 5));
        northPanel.add(startBtn, BorderLayout.WEST);
        northPanel.add(this.gameStatusLabel, BorderLayout.WEST);
        northPanel.add(this.generationLabel, BorderLayout.CENTER);
        JButton restartBtn = new JButton("Restart");
        restartBtn.setBackground(Color.white);
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBackground(Color.white);
        northPanel.add(restartBtn, BorderLayout.EAST);
        northPanel.add(exitBtn, BorderLayout.EAST);

        // East
        JPanel eastPanel = new JPanel();
        this.gameFrame.addItem(eastPanel, "east");

        this.codeBreaker = new CodeBreaker(this.colorCodeLength, "Code Breaker", this.colorCodeCellsSize);
        eastPanel.add(codeBreaker.getGUI());

        // South
        JPanel southPanel = new JPanel();
        this.gameFrame.addItem(southPanel, "south");

        southPanel.setLayout(new GridLayout(0, 2, 0, 0));
        int mutationsNum = 4;

        for (int i = 0; i < mutationsNum; i++) {
            Mutation newMutation = new Mutation(this.colorCodeLength, "mutation #" + (i + 1), this.colorCodeCellsSize, this);
            this.mutationsList.add(newMutation);
            southPanel.add(newMutation.getGUI());
        }

        // West
        JPanel westPanel = new JPanel();
        this.gameFrame.addItem(westPanel, "west");

        this.codeMaker = new CodeMaker(this.colorCodeLength, "Code Maker", this.colorCodeCellsSize);
        westPanel.add(this.codeMaker.getGUI());

        // Wrap the frame around its components
        this.gameFrame.finalizeFrameSetup();
        // -----------------------------------------------------------------------------

        this.launch();
    }

    public void run() {
        this.launch();
    }

    private void launch() {
        this.gameStatus = "decoding";
        this.gameFrame.updateLabel(this.gameStatusLabel, "text", this.gameStatus);

        this.codeMaker.launch();
        this.codeBreaker.launch(this, this.mutationsList);

        this.generationNum = 0;

        while (true) {
            // Check if the code got breaken
            if (this.codeMaker.isEqual(this.codeBreaker)) {
                this.setStatusToSuccess();
                break;
            }

            this.codeBreaker.decode();

            this.generationNum++;
            this.gameFrame.updateLabel(this.generationLabel, "text", "Generation: " + this.generationNum);

            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.gameFrame.updateLabel(this.gameStatusLabel, "text", this.gameStatus);
    }

    public ArrayList<Double> getFitnessSeq(ColorCode colorCode) {
        return this.codeMaker.calculateFitnessSeq(colorCode);
    }

    private void setStatusToSuccess() {
        this.gameStatus = this.successState;
    }
}
