import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public class CodeMaker extends ColorCode {
    // private ArrayList<Double> curFitnessSeq;
    // Keep track of CodeMaker data
    // private ArrayList<Color> colorsList;
    // private ArrayList<Integer> colorsNum;

    private ArrayList<Boolean> cellsAccessList;

    public CodeMaker(int length, String title, int cellsSize) {
        super(length, title, cellsSize);
        // this.colorsList = new ArrayList<>();
        // this.colorsNum = new ArrayList<>();

        this.cellsAccessList = new ArrayList<>();
        for (int i = 0; i < this.getLength(); i++) this.cellsAccessList.add(true);
    }

    public void launch() {
        for (int i = 0; i < this.getLength(); i++) {
            Color randColor = ColorWheel.getRandColor();
            this.updateCellColor(i, randColor);
            // this.arrangeColorsData(randColor);
        }
    }

    // public void arrangeColorsData(Color color) {
    //     for (int i = 0; i < this.colorsList.size(); i++) {
    //         if (this.colorsList.get(i) == color) {
    //             this.colorsNum.set(i, this.colorsNum.get(i) + 1);
    //             return;
    //         }
    //     }

    //     this.colorsList.add(color);
    //     this.colorsNum.add(1);
    // }

    // public void subColorUnits(Color color) {
    //     for (int i = 0; i < this.colorsList.size(); i++) {
    //         if (this.colorsList.get(i) == color) {
    //             this.colorsNum.set(i, this.colorsNum.get(i) - 1);
    //             return;
    //         }
    //     }
    // }

    // public int getColorUnitLeft(Color color) {
    //     for (int i = 0; i < this.colorsList.size(); i++) {
    //         if (this.colorsList.get(i) == color) {
    //             return this.colorsNum.get(i);
    //         }
    //     }

    //     return 0;
    // }

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

    public ArrayList<Double> calculateFitnessSeq(CodeBreaker colorCode) {
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
                fitness += 0.5;

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

    public ArrayList<Double> calculateFitnessSeq(Mutation colorCode) {
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
                fitness += 0.5;

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
