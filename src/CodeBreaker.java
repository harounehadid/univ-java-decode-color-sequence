import java.util.ArrayList;

public class CodeBreaker extends ColorCode {
    private ArrayList<Double> fitnessSeq;

    public CodeBreaker(int length, String title, int cellsSize) {
        super(length, title, cellsSize);
    }

    public void launch() {
        for (int i = 0; i < this.getLength(); i++) {
            this.updateCellColor(i, ColorWheel.getRandColor());
        }
    }

    public void decode() {
        for (int i = 0; i < this.getLength(); i++) {
            if (this.fitnessSeq.get(i) != 1) {
                this.updateCellColor(i, ColorWheel.getRandColor());
            }
        }
    }

    public void updateFitnessSeq(ArrayList<Double> fitnessSeq) {
        this.fitnessSeq = fitnessSeq;
    }
}
