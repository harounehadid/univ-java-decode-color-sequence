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
            this.statusLabel.setIcon(image);
        }
        else if (type == "text") {
            this.statusLabel.setText(data);
        }
    }

    public Mutation initialize() {
        // Generate random color seq
        for (int i = 0; i < this.getLength(); i++) {
            Color randColor = ColorWheel.getRandColor();
            this.updateCellColor(i, randColor);
        }

        this.updateStats();

        return this;
    }

    public void copyColorSeq(ColorCode colorCode) {
        for (int i = 0; i < this.getLength(); i++) {
            this.updateCellColor(i, colorCode.getColorSeq().get(i).getBackground());
        }

        this.updateStats();
    }

    public void mutateCell(int index, Color newColor) {
        this.updateCellColor(index, newColor);
        this.updateStats();
    }

    public void updateStats() {
        this.fitnessSeq = this.gameManager.getFitnessSeq(this);
        this.calculateFitnessSum().displayPercentage();
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
    }

    public void onSelect() {
        this.updateGUI("image", GetBaseDirPath.root() + "/src/media/accepted.png");
    }

    // Getters
    public double getFitnessSum() {
        return this.fitnessSum;
    }
}
