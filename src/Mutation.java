import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utils.GetBaseDirPath;

public class Mutation extends ColorCode {
    private GameManager gameManager;
    private double max;
    private ArrayList<Double> fitnessSeq;
    private JLabel statusLabel;

    public Mutation(int length, String title, int cellsSize, GameManager gameManager) {
        super(length, title, cellsSize);
        this.gameManager = gameManager;

        this.statusLabel = new JLabel();

        this.updateGUI("text", "0%                 ");

        this.updateGUI("image", GetBaseDirPath.root() + "/src/media/neutrual.png");
        this.addToMainPanel(this.statusLabel);
    }

    public void updateGUI(String type, String data) {
        if (type == "image") {
            ImageIcon image = new ImageIcon(data);
            this.statusLabel.setSize(image.getIconWidth(), image.getIconHeight());
            this.statusLabel.setIcon(image);
        }
        else if (type == "text") {
            this.statusLabel.setText(data);
        }
    }

    public void decode(CodeBreaker codeBreaker) {
        this.updateGUI("image", GetBaseDirPath.root() + "/src/media/neutrual.png");

        ArrayList<Double> fitnessSeq = codeBreaker.getFitnessSeq();
        ArrayList<MissColor> missColors = new ArrayList<>();

        for (int i = 0; i < this.getLength(); i++) {
            if (fitnessSeq.get(i) == 0.5) {
                missColors.add(new MissColor(this.getColorSeq().get(i).getBackground(), i));
            }
        }

        for (MissColor missColor : missColors) {
            for (int i = missColor.getIndex() + 1; true; i++) {
                if (i == this.getLength()) i = 0;

                if (fitnessSeq.get(i) < 1) {
                    this.updateCellColor(i, missColor.getColor());
                    CustomFrame.sleep(100);
                    break;
                }
            }
        }

        // fitnessSeq = gameManager.getFitnessSeq(this);
        
        for (int i = 0; i < this.getLength(); i++) {
            if (fitnessSeq.get(i) != 1) {
                this.updateCellColor(i, ColorWheel.getRandColor());
                CustomFrame.sleep(100);
            }
            else {
                this.updateCellColor(i, codeBreaker.getColorSeq().get(i).getBackground());
                CustomFrame.sleep(100);
            }
        }

        fitnessSeq = gameManager.getFitnessSeq(this);

        this.max = 0;

        for (Double val : fitnessSeq) {
            this.max += val;
        }

        this.displayPercentage();

        this.fitnessSeq = this.gameManager.getFitnessSeq(this);

        CustomFrame.sleep(400);
    }

    public void onAccept() {
        this.updateGUI("image", GetBaseDirPath.root() + "/src/media/accepted.png");
    }

    public void onReject() {
        this.updateGUI("image", GetBaseDirPath.root() + "/src/media/rejected.png");
    }

    public void displayPercentage() {
        Double percentage = Math.floor(((this.max / this.getLength() * 100)) * 10.0) / 10.0;
        this.updateGUI("text", Double.toString(percentage) + "%");
    }

    // Getters
    public double getMax() {
        return this.max;
    }

    public ArrayList<Double> getFitnessSeq() {
        return this.fitnessSeq;
    }
}
