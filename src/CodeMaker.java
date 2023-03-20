import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public class CodeMaker extends ColorCode {
    private ArrayList<Double> curFitnessSeq;

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
        ArrayList<Double> fitnessSeq = new ArrayList<Double>();
        ArrayList<Integer> restrictedIndexesList = new ArrayList<Integer>();

        for (int i = 0; i < this.getLength(); i++) {
            double fitness = 0;
            Color color = colorCode.getColorSeq().get(i).getBackground();

            if (this.colorExists(color)) {
                if (this.curFitnessSeq != null) {
                    if (this.curFitnessSeq.get(i) == 1) {
                        fitness = 1;
                        restrictedIndexesList.add(i);
                        fitnessSeq.add(fitness);
                        continue;
                    }
                }

                fitness += 0.5;

                if (this.getColorIndex(color, restrictedIndexesList) == i) {
                    fitness += 0.5;
                    restrictedIndexesList.add(i);
                }
            }

            fitnessSeq.add(fitness);
        }

        this.curFitnessSeq = fitnessSeq;

        return fitnessSeq;
    }

    public boolean colorExists(Color color) {
        boolean exist = false;

        for (int i = 0; i < this.getLength(); i++) {
            if (this.getColorSeq().get(i).getBackground() == color) {
                if (this.curFitnessSeq != null) {
                    if (this.curFitnessSeq.get(i) == 1) continue;
                }

                exist = true;
                break;
            }
        }

        return exist;
    }

    public Integer getColorIndex(Color color, ArrayList<Integer> restrictedIndexesList) {
        int index = -1;

        for (int i = 0; i < this.getLength(); i++) {
            if (this.getColorSeq().get(i).getBackground() == color && !restrictedIndexesList.contains(i)) {
                index = i;
            }
        }

        return index;
    }
}
