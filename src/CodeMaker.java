import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public class CodeMaker extends ColorCode {
    public CodeMaker(int length, String title, int cellsSize) {
        super(length, title, cellsSize);
    }

    public void launch() {
        for (int i = 0; i < this.getLength(); i++) {
            this.updateCellColor(i, ColorWheel.getRandColor());
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
        ArrayList<Double> fitnessSeq = new ArrayList<Double>();
        ArrayList<JLabel> colorCodeSeq = colorCode.getColorSeq();

        for (int i = 0; i < this.getLength(); i++) {
            double fitness = 0;
            Color color = colorCodeSeq.get(i).getBackground();

            if (this.colorExists(color)) {
                fitness += 0.5;

                ArrayList<Integer> indexList = this.getColorIndexList(color);
                if (indexList.size() > 0) {
                    for (Integer index : indexList) {
                        if (index == i) {
                            fitness += 0.5;
                            break;
                        }
                    }
                }
            }

            fitnessSeq.add(fitness);
        }

        return fitnessSeq;
    }

    public boolean colorExists(Color color) {
        boolean exist = false;

        ArrayList<JLabel> colorSeq = this.getColorSeq();

        for (int i = 0; i < this.getLength(); i++) {
            if (colorSeq.get(i).getBackground() == color) {
                exist = true;
                break;
            }
        }

        return exist;
    }

    public ArrayList<Integer> getColorIndexList(Color color) { // If we have two of the same color we'll end up with bugs
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        ArrayList<JLabel> colorSeq = this.getColorSeq();

        for (int i = 0; i < this.getLength(); i++) {
            if (colorSeq.get(i).getBackground() == color) {
                indexList.add(i);
            }
        }

        return indexList;
    }
}
