import java.util.ArrayList;

public class GeneticsAlgo {
    private CodeMaker codeMaker;
    private ArrayList<Mutation> mutationsList;
    private GameManager gameManager;

    public GeneticsAlgo(CodeMaker codeMaker, ArrayList<Mutation> mutationsList, GameManager gameManager) {
        this.codeMaker = codeMaker;
        this.mutationsList = mutationsList;
        this.gameManager = gameManager;
    }

    public void launch() {
        this.codeMaker.launch();

        for (Mutation mutation : this.mutationsList) {
            mutation.initialize();
        }

        if (codeFound()) this.onCodeFound();
    }

    public void decode() {
        this.selectPopulations();

        this.crossover();
        this.mutation();

        if (codeFound()) this.onCodeFound();
    }

    private void selectPopulations() {
        // Calculate the probability for each population
        ArrayList<Double> probEnd = new ArrayList<>();
        double allFitnessSum = 0.0;

        for (Mutation mutation : this.mutationsList) {
            allFitnessSum += mutation.getFitnessSum();
        }

        System.out.println("All fitness sum is " + allFitnessSum);

        for (int i = 0; i < this.mutationsList.size() - 1; i++) {
            double prob = this.mutationsList.get(i).getFitnessSum() / allFitnessSum * 100;
            probEnd.add(prob);
            System.out.println("Population selection prob is " + probEnd.get(i));
        }

        // Run random number generator and compare with probabilities
        ArrayList<Integer> selectedIndexes = new ArrayList<>();

        for (int i = 0; i < this.mutationsList.size(); i++) {
            double randProb = Math.random() * 100;
            System.out.println("Selected prob is " + randProb);

            for (int j = 0; j < probEnd.size(); j++) {
                if (randProb < probEnd.get(i)) {
                    selectedIndexes.add(j);
                    System.out.println("Selected population is " + j);
                    break;
                }
                else {
                    if (j == probEnd.size() - 1) {
                        selectedIndexes.add(j);
                        System.out.println("Selected population is " + j);
                    }
                }
            }
        }

        // Update each population depending on the results of roling
        ArrayList<Mutation> updatedPopulation = new ArrayList<>();

        for (int i = 0; i < this.mutationsList.size(); i++) {
            Mutation selectedPopulation = new Mutation(this.codeMaker.getLength(), null, 0, null).initialize();
            selectedPopulation.copyColorSeq(this.mutationsList.get(selectedIndexes.get(i)));
            updatedPopulation.add(selectedPopulation);
            System.out.println("Loading the selected population");
        }

        for (int i = 0; i < this.mutationsList.size(); i++) {
            this.mutationsList.get(i).copyColorSeq(updatedPopulation.get(i));
            CustomFrame.sleep(250);
        }
    }

    private ArrayList<Integer> selectPairs() {
        // Randomly create pairs
        ArrayList<Integer> pairsList = new ArrayList<>();

        for (int i = 0; i < this.mutationsList.size(); i++) {
            while (true) {
                Integer randIndex = (int)Math.floor(Math.random() * this.mutationsList.size());

                if (! pairsList.contains(randIndex)) {
                    pairsList.add(randIndex);
                    break;
                }
            }
        }

        return pairsList;
    }

    private void crossover() {
        // Using this method, both methods bellow should loop using i += 2 instead of the common progression
        ArrayList<Integer> pairsList = this.selectPairs();

        for (int i = 0; i < this.mutationsList.size(); i += 2) {
            // Check the probabilities of each pair
            if (this.mutationsList.get(i).calculatePercentage() > 70 || this.mutationsList.get(i + 1).calculatePercentage() > 70) {
                // Select rand index
                int randIndex = (int)Math.floor(Math.random() * this.codeMaker.getLength());

                // Cross between the pairs
                Mutation fstCopy = new Mutation(this.codeMaker.getLength(), null, 0, null).initialize();
                Mutation sndCopy = new Mutation(this.codeMaker.getLength(), null, 0, null).initialize();
                
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < this.codeMaker.getLength(); k++) {
                        if (k < randIndex) {
                            fstCopy.updateCellColor(randIndex, this.mutationsList.get(i).getColorSeq().get(k).getBackground());
                        }
                        else {
                            fstCopy.updateCellColor(randIndex, this.mutationsList.get(i + 1).getColorSeq().get(k).getBackground());
                        }
                    }
                }

                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < this.codeMaker.getLength(); k++) {
                        if (k < randIndex) {
                            sndCopy.updateCellColor(randIndex, this.mutationsList.get(i + 1).getColorSeq().get(k).getBackground());
                        }
                        else {
                            sndCopy.updateCellColor(randIndex, this.mutationsList.get(i).getColorSeq().get(k).getBackground());
                        }
                    }
                }

                this.mutationsList.get(i).copyColorSeq(fstCopy);
                this.mutationsList.get(i + 1).copyColorSeq(sndCopy);
            }
        }
    }

    private void mutation() {
        for (int i = 0; i < this.mutationsList.size(); i++) {
            // Check the probabilities of each pair
            if (this.mutationsList.get(i).calculatePercentage() < 10) {
                // Select rand index
                int randIndex = (int)Math.floor(Math.random() * this.codeMaker.getLength());

                // Mutate the cell
                this.mutationsList.get(i).mutateCell(randIndex, null);
            }
        }
    }

    private boolean codeFound() {
        boolean codeFound = false;

        for (Mutation mutation : this.mutationsList) {
            if (this.codeMaker.isEqual(mutation)) {
                codeFound = true;
                break;
            }
        }

        return codeFound;
    }

    private void onCodeFound() {
        this.gameManager.gameOver();
    }
}
