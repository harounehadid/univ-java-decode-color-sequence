import java.util.ArrayList;

public class CodeBreaker extends ColorCode {
    private ArrayList<Double> fitnessSeq;
    private GameManager gameManager;
    private ArrayList<Mutation> mutations;

    public CodeBreaker(int length, String title, int cellsSize) {
        super(length, title, cellsSize);
    }

    public void launch(GameManager gameManager, ArrayList<Mutation> mutations) {
        for (int i = 0; i < this.getLength(); i++) {
            this.updateCellColor(i, ColorWheel.getRandColor());
        }

        this.gameManager = gameManager;
        this.mutations = mutations;
        this.fitnessSeq = this.gameManager.getFitnessSeq(this);
    }

    public void decode() {
        for (Mutation mutation : this.mutations) {
            mutation.decode(this);
        }

        ArrayList<Double> fstSeq = new ArrayList<>();
        ArrayList<Double> sndSeq = new ArrayList<>();

        double max1 = 0;
        double max2 = 0;

        int index1 = -1;
        int index2 = -1;

        for (int i = 0; i < this.mutations.size(); i++) {
            if (this.mutations.get(i).getMax() > max1) {
                max1 = this.mutations.get(i).getMax();
                index1 = i;
            }
            else {
                if (this.mutations.get(i).getMax() > max2) {
                    max2 = this.mutations.get(i).getMax();
                    index2 = i;
                }
            }
        }

        for (int i = 0; i < this.mutations.size(); i++) {
            if (i == index1) {
                fstSeq = this.mutations.get(i).getFitnessSeq();
                this.mutations.get(i).onAccept();
            }
            else if (i == index2) {
                sndSeq = this.mutations.get(i).getFitnessSeq();
                this.mutations.get(i).onAccept();
            }
            else {
                this.mutations.get(i).onReject();
            }
        }

        for (int i = 0; i < this.getLength(); i++) {
            if (fstSeq.get(i) >= sndSeq.get(i)) {
                this.updateCellColor(i, this.mutations.get(index1).getColorSeq().get(i).getBackground());
                this.fitnessSeq.set(i, fstSeq.get(i));
            }
            else {
                this.updateCellColor(i, this.mutations.get(index2).getColorSeq().get(i).getBackground());
                this.fitnessSeq.set(i, sndSeq.get(i));
            }
        }

        // ArrayList<MissColor> missColors = new ArrayList<>();

        // for (int i = 0; i < this.getLength(); i++) {
        //     if (this.fitnessSeq.get(i) == 0.5) {
        //         missColors.add(new MissColor(this.getColorSeq().get(i).getBackground(), i));
        //     }
        // }

        // for (MissColor missColor : missColors) {
        //     for (int i = missColor.getIndex() + 1; true; i++) {
        //         if (i == this.getLength()) i = 0;

        //         if (this.fitnessSeq.get(i) != 1) {
        //             this.updateCellColor(i, missColor.getColor());
        //             break;
        //         }
        //     }
        // }
        
        // for (int i = 0; i < this.getLength(); i++) {
        //     if (this.fitnessSeq.get(i) != 1) {
        //         this.updateCellColor(i, ColorWheel.getRandColor());
        //     }
        // }

    //    this.fitnessSeq = this.gameManager.getFitnessSeq(this);
    }

    public ArrayList<Double> getFitnessSeq() {
        return this.fitnessSeq;
    }
}
