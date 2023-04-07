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
    private boolean gameOver = false;
    private final int colorCodeLength = 5;
    private final int colorCodeCellsSize = 64;
    private int generationNum;
    private JLabel generationLabel;
    private CodeMaker codeMaker;
    private ArrayList<Mutation> mutationsList;
    private GeneticsAlgo geneticsAlgo;

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

        eastPanel.add(new JLabel("Extra Info"));

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

        this.geneticsAlgo = new GeneticsAlgo(this.codeMaker, this.mutationsList, this);
        this.geneticsAlgo.launch();

        this.generationNum = 0;

        while (! this.gameOver) {
            this.generationNum++;
            this.gameFrame.updateLabel(this.generationLabel, "text", "Generation: " + this.generationNum);
            
            this.geneticsAlgo.decode();
        }

        this.gameFrame.updateLabel(this.gameStatusLabel, "text", this.gameStatus);
    }

    public ArrayList<Double> getFitnessSeq(ColorCode colorCode) {
        return this.codeMaker.calculateFitnessSeq(colorCode);
    }
    public void gameOver() {
        this.gameStatus = this.successState;
        this.gameOver = true;
    }
}
