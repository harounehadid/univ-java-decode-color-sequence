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
        this.fitnessSeq = this.gameManager.getFitnessSeq(this);
    }

    public void decode() {
       for (int i = 0; i < this.getLength(); i++) {
            if (this.fitnessSeq.get(i) != 1) {
                this.updateCellColor(i, ColorWheel.getRandColor());
            }
       }

       this.fitnessSeq = this.gameManager.getFitnessSeq(this);
    }

    public ArrayList<Double> getFitnessSeq() {
        return this.fitnessSeq;
    }
}
