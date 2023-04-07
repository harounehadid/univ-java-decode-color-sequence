import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utils.GetBaseDirPath;

public class Mutation extends ColorCode {
    private GameManager gameManager;
    private ArrayList<Double> fitnessSeq;
    private double fitnessSum;
    private JLabel statusLabel;

    public Mutation(int length, String title, int cellsSize, GameManager gameManager) {
        super(length, title, cellsSize);
        this.gameManager = gameManager;
        
        this.statusLabel = new JLabel();
        this.updateGUI("text", "0%                 ");
        this.updateGUI("image", GetBaseDirPath.root() + "/src/media/neutrual.png");
        this.addToMainPanel(this.statusLabel);
    }

    private void updateGUI(String type, String data) {
        if (type == "image") {
            ImageIcon image = new ImageIcon(data);
            this.statusLabel.setSize(image.getIconWidth(), image.getIconHeight());
            this.statusLabel.setIcon(image);
        }
        else if (type == "text") {
            this.statusLabel.setText(data);
        }
    }

    public Mutation initialize() {
        System.out.println("Mutation initializing ...");
        // Generate random color seq
        for (int i = 0; i < this.getLength(); i++) {
            Color randColor = ColorWheel.getRandColor();
            this.updateCellColor(i, randColor);
        }

        // Get fitness seq
        this.fitnessSeq = this.gameManager.getFitnessSeq(this);

        // Calculate percentage
        this.calculateFitnessSum().displayPercentage();

        System.out.println("Mutation done initializing");

        return this;
    }

    public void copyColorSeq(ColorCode colorCode) {
        for (int i = 0; i < this.getLength(); i++) {
            this.updateCellColor(i, colorCode.getColorSeq().get(i).getBackground());
        }

        // Update stats
        this.updateStats();
    }

    // public void onAccept() {
    //     this.updateGUI("image", GetBaseDirPath.root() + "/src/media/accepted.png");
    // }

    // public void onReject() {
    //     this.updateGUI("image", GetBaseDirPath.root() + "/src/media/rejected.png");
    // }

    public void updateStats() {
        this.fitnessSeq = this.gameManager.getFitnessSeq(this);
        this.calculateFitnessSum();
        System.out.println("New fitness sum is " + this.fitnessSum);
    }

    public Mutation calculateFitnessSum() {
        this.fitnessSum = 0;

        for (Double val : this.fitnessSeq) {
            this.fitnessSum += val;
        }

        return this;
    }

    public double calculatePercentage() {
        return Math.floor(((this.fitnessSum / this.getLength() * 100)) * 10.0) / 10.0;
    }

    public void displayPercentage() {
        Double percentage = calculatePercentage();
        this.updateGUI("text", Double.toString(percentage) + "%");
        System.out.println("Mutation percentage is " + percentage);
    }

    // Getters
    public double getFitnessSum() {
        return this.fitnessSum;
    }
}
