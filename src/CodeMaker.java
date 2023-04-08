import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public class CodeMaker extends ColorCode {

    private ArrayList<Boolean> cellsAccessList;

    public CodeMaker(int length, String title, int cellsSize) {
        super(length, title, cellsSize);
        this.cellsAccessList = new ArrayList<>();
        for (int i = 0; i < this.getLength(); i++) this.cellsAccessList.add(true);

        System.out.println("Code maker is done initializing and it's length is " + this.getLength());
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

        // this.curFitnessSeq = colorCode.getFitnessSeq();

        for (int i = 0; i < this.getLength(); i++) this.cellsAccessList.set(i, true);

        for (int i = 0; i < this.getLength(); i++) {
            double fitness = 0;

            Color curColor = cbColorSeq.get(i).getBackground();

            // if (this.curFitnessSeq != null) {
            //     if (this.curFitnessSeq.get(i) == 1) {
            //         fitness = 1;
            //         fitnessSeq.add(fitness);
            //         continue;
            //     }    
            // }

            if (this.colorExists(curColor)) {
                fitness += 0.6;

                ArrayList<Integer> indexes = this.getColorIndexes(curColor);
                for (Integer index : indexes) {
                    if (index == i) {
                        fitness += 0.5;
                        // subColorUnits(curColor);
                        break;
                    }
                }
            }

            fitnessSeq.add(fitness);
        }

        return fitnessSeq;
    }

    public boolean colorExists(Color color) {
        boolean exist = false;

        // if (color == this.getColorSeq().get(i).getBackground() && this.getColorUnitLeft(color) > 0 && this.cellsAccessList.get(i)) 

        for (int i = 0; i < this.getLength(); i++) {
            if (color == this.getColorSeq().get(i).getBackground() && this.cellsAccessList.get(i)) {
                exist = true;
                this.cellsAccessList.set(i, false);
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
                break;
            }
        }

        return indexes;
    }
}
