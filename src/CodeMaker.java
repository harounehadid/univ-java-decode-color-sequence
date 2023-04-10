import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public class CodeMaker extends ColorCode {
    public CodeMaker(int length, String title, int cellsSize) {
        super(length, title, cellsSize);
    }

    public void launch() {
        for (int i = 0; i < this.getLength(); i++) {
            Color randColor = ColorWheel.getRandColor();
            this.updateCellColor(i, randColor);
        }
    }

    public boolean isEqual(ColorCode colorCode) {
        boolean equal = true;

        ArrayList<JLabel> codeMakerSeq = this.getColorSeq();
        ArrayList<JLabel> colorCodeSeq = colorCode.getColorSeq();

        if (codeMakerSeq.size() != colorCodeSeq.size()) return false;

        for (int i = 0; i < this.getLength(); i++) {
            if (codeMakerSeq.get(i).getBackground() != colorCodeSeq.get(i).getBackground()) {
                equal = false;
                break;
            }
        }

        return equal;
    }

    public ArrayList<Double> calculateFitnessSeq(ColorCode colorCode) {
        ArrayList<Double> fitnessSeq = new ArrayList<>();
        ArrayList<JLabel> cbColorSeq = colorCode.getColorSeq();

        ArrayList<Integer> accessedIndexes = new ArrayList<>();

        for (int i = 0; i < this.getLength(); i++) {
            double fitness = 0;

            Color curColor = cbColorSeq.get(i).getBackground();

            if (this.colorExists(curColor, null)) {

                ArrayList<Integer> indexes = this.getColorIndexes(curColor);

                for (Integer index : indexes) {
                    // System.out.println(index);
                    if (i == index && ! accessedIndexes.contains(index)) {
                        fitness += 1;
                        accessedIndexes.add(index);
                        break;
                    }
                }
            }

            fitnessSeq.add(fitness);
        }

        for (int i = 0; i < this.getLength(); i++) {
            if (fitnessSeq.get(i) == 1) continue;

            double fitness = 0;

            Color curColor = cbColorSeq.get(i).getBackground();

            if (this.colorExists(curColor, accessedIndexes)) {
                fitness += 0.6;
                fitnessSeq.set(i, fitness);
            }
        }

        return fitnessSeq;
    }

    public boolean colorExists(Color color, ArrayList<Integer> accessedIndex) {
        boolean exist = false;

        for (int i = 0; i < this.getLength(); i++) {
            if (color == this.getColorSeq().get(i).getBackground()) {
                if (accessedIndex != null) {
                    if (accessedIndex.contains(i)) continue;
                    else {
                        accessedIndex.add(i);
                    }
                }

                exist = true;
                break;
            }
        }

        return exist;
    }

    public ArrayList<Integer> getColorIndexes(Color color) {
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < this.getLength(); i++) {
            if (color == this.getColorSeq().get(i).getBackground()) {
                indexes.add(i);
            }
        }

        return indexes;
    }
}
