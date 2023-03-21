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
        ArrayList<MissColor> missColors = new ArrayList<>();

        for (int i = 0; i < this.getLength(); i++) {
            if (this.fitnessSeq.get(i) == 0.5) {
                missColors.add(new MissColor(this.getColorSeq().get(i).getBackground(), i));
            }
        }

        for (MissColor missColor : missColors) {
            for (int i = missColor.getIndex() + 1; true; i++) {
                if (i == this.getLength()) i = 0;

                if (this.fitnessSeq.get(i) != 1) {
                    this.updateCellColor(i, missColor.getColor());
                    break;
                }
            }
        }
        
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
