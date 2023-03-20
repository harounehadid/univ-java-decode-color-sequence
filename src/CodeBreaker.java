import java.awt.Color;
import java.util.ArrayList;

public class CodeBreaker extends ColorCode {
    private ArrayList<Double> fitnessSeq;
    private GameManager gameManager;

    public CodeBreaker(int length, String title, int cellsSize) {
        super(length, title, cellsSize);
    }

    public void launch(GameManager gameManager) {
        for (int i = 0; i < this.getLength(); i++) {
            this.updateCellColor(i, ColorWheel.getRandColor());
        }

        this.gameManager = gameManager;
    }

    public void decode() {
        System.out.println("Decode >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        this.fitnessSeq = this.gameManager.getFitnessSeq(this);

        // Get the two mutations and check between them the cell with the bigest fitness value is put to feed the new code

        ArrayList<MissColor> colorsToShift = new ArrayList<MissColor>();
        ArrayList<Integer> restrictedIndexesList = new ArrayList<Integer>();

        System.out.println(this.fitnessSeq);
        
        for (int i = 0; i < this.getLength(); i++) {
            if (this.fitnessSeq.get(i) == 0.5) {
                MissColor missColor = new MissColor(this.getColorSeq().get(i).getBackground(), i);
                colorsToShift.add(missColor);
                System.out.println("color picked " + missColor.getColor());
            }
        }

        // for (MissColor missColor : colorsToShift) {
        //     for (int i = missColor.getIndex() + 1; i != missColor.getIndex(); i++) {
        //         if (i >= this.getLength()) i = 0;

        //         if (this.fitnessSeq.get(i) < 1 && !restrictedIndexesList.contains(i)) {
        //             this.updateCellColor(i, missColor.getColor());
        //             restrictedIndexesList.add(i);
        //             System.out.println("Do not touch " + i);
        //             break;
        //         }
        //     }
        // }

        for (int i = 0; i < this.getLength(); i++) {
            if (this.fitnessSeq.get(i) < 1) {
                this.updateCellColor(i, ColorWheel.getRandColor());
                System.out.println("Update " + i);
            }
        }
    }
}
