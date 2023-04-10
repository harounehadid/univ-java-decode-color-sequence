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
        CustomFrame.sleep(50);
        System.out.println("\nSELECT POPULATION --------------------------------");
        this.selectPopulations();
        // CustomFrame.sleep(250);

        System.out.println("\nCROSSOVER --------------------------------");
        this.crossover();
        // CustomFrame.sleep(250);

        System.out.println("\nMUTATION --------------------------------");
        this.mutation();
        // CustomFrame.sleep(250);

        if (codeFound()) this.onCodeFound();
    }

    private void selectPopulations() {
        // Order parents depending on their percentage
        ArrayList<Integer> indexesOrderConv = new ArrayList<>();
        ArrayList<Integer> visitedIndexes = new ArrayList<>();

        for (int i = 0; i < this.mutationsList.size(); i++) {
            System.out.println("Current population percentages " + this.mutationsList.get(i).calculatePercentage());
        }

        for (int i = 0; i < this.mutationsList.size(); i++) {
            double max = -1;
            int index = -1;

            for (int j = 0; j < this.mutationsList.size(); j++) {
                if (this.mutationsList.get(j).calculatePercentage() >= max && ! visitedIndexes.contains(j)) {
                    max = this.mutationsList.get(j).calculatePercentage();
                    index = j;
                }
            }

            indexesOrderConv.add(index);
            visitedIndexes.add(index);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        for (int i = 0; i < this.mutationsList.size(); i++) {
            System.out.println("Resulted order " + this.mutationsList.get(indexesOrderConv.get(i)).calculatePercentage());
        }

        ArrayList<Mutation> rearrangedParents = this.shallowCopy(this.mutationsList, indexesOrderConv);

        for (int i = 0; i < this.mutationsList.size(); i++) {
            this.mutationsList.get(i).copyColorSeq(rearrangedParents.get(i));
        }

        // Calculate the probability for each population
        ArrayList<Double> probEnd = new ArrayList<>();
        double allFitnessSum = 0.0;

        for (Mutation mutation : this.mutationsList) {
            allFitnessSum += mutation.getFitnessSum();
        }

        // System.out.println("All fitness sum is " + allFitnessSum);

        double curRange = 0.0;

        for (int i = 0; i < this.mutationsList.size() - 1; i++) {
            curRange += calculatePercentage(this.mutationsList.get(i).getFitnessSum(), allFitnessSum);
            probEnd.add(curRange);
            System.out.println("Population selection prob is " + probEnd.get(i));
        }

        // Selecting the new parents and storing their indexes
        ArrayList<Integer> selectedIndexes = new ArrayList<>();

        for (int i = 0; i < this.mutationsList.size(); i++) {
            double randProb = this.calculatePercentage(Math.random() * 100, 100);
            // System.out.println("Selected prob is " + randProb);

            for (int j = 0; j < probEnd.size(); j++) {
                if (randProb < probEnd.get(j)) {
                    selectedIndexes.add(j);
                    System.out.println("Selected population is " + j);
                    break;
                }
                else {
                    if (j == probEnd.size() - 1) {
                        selectedIndexes.add(j + 1);
                        System.out.println("Selected population is " + (j + 1));
                    }
                }
            }
        }

        // Update each population depending on the results of roling
        ArrayList<Mutation> updatedPopulation = this.shallowCopy(this.mutationsList, selectedIndexes);

        for (int i = 0; i < this.mutationsList.size(); i++) {
            this.mutationsList.get(i).copyColorSeq(updatedPopulation.get(i));
            // CustomFrame.sleep(250);
        }
    }

    private double calculatePercentage(double fraction, double whole) {
        // System.out.println(">>>>>>>>>>>>>>>>>>>> Fraction = " + fraction + " >>>>>>>>>>>>>>>>>>>>>>>>>>>> whole = " + whole);
        return Math.floor(((fraction / whole * 100)) * 100.0) / 100.0;
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

        ArrayList<Mutation> newParents = new ArrayList<>();
        ArrayList<Integer> recordedIndexes = new ArrayList<>();

        for (int i = 0; i < this.mutationsList.size(); i += 2) {
            // Check the probabilities of each pair
            if (this.mutationsList.get(pairsList.get(i)).calculatePercentage() <= 70 || this.mutationsList.get(pairsList.get(i + 1)).calculatePercentage() <= 70) {
                // Select rand index
                int randIndex = (int)Math.floor(Math.random() * this.codeMaker.getLength());

                // Cross between the pairs
                Mutation fstCopy = new Mutation(this.codeMaker.getLength(), null, 0, this.gameManager).initialize();
                Mutation sndCopy = new Mutation(this.codeMaker.getLength(), null, 0, this.gameManager).initialize();
                
                for (int j = 0; j < this.codeMaker.getLength(); j++) {
                    if (j < randIndex) {
                        fstCopy.updateCellColor(j, this.mutationsList.get(pairsList.get(i)).getColorSeq().get(j).getBackground());
                    }
                    else {
                        fstCopy.updateCellColor(j, this.mutationsList.get(pairsList.get(i + 1)).getColorSeq().get(j).getBackground());
                    }
                }

                for (int j = 0; j < this.codeMaker.getLength(); j++) {
                    if (j < randIndex) {
                        sndCopy.updateCellColor(j, this.mutationsList.get(pairsList.get(i + 1)).getColorSeq().get(j).getBackground());
                    }
                    else {
                        sndCopy.updateCellColor(j, this.mutationsList.get(pairsList.get(i)).getColorSeq().get(j).getBackground());
                    }
                }

                newParents.add(fstCopy);
                recordedIndexes.add(pairsList.get(i));

                newParents.add(sndCopy);
                recordedIndexes.add(pairsList.get(i + 1));
            }
        }


        for (int i = 0; i < newParents.size(); i++) {
            this.mutationsList.get(recordedIndexes.get(i)).copyColorSeq(newParents.get(i));
        }
    }

    private void mutation() {
        for (int i = 0; i < this.mutationsList.size(); i++) {
            // Check the probabilities of each pair
            double randProb = this.calculatePercentage(Math.random() * 100, 100);
            if (randProb <= 10) {
                // Select rand index
                int randIndex = (int)Math.floor(Math.random() * this.codeMaker.getLength());

                // Mutate the cell
                this.mutationsList.get(i).mutateCell(randIndex, ColorWheel.getRandColor());
                break;
            }
        }
    }

    private ArrayList<Mutation> shallowCopy(ArrayList<Mutation> list, ArrayList<Integer> copyOrder) {
        ArrayList<Mutation> updatedList = new ArrayList<>();

        if (copyOrder == null) {
            for (int i = 0; i < list.size(); i++) {
                copyOrder.add(i);
            }
        }

        for (int i = 0; i < this.mutationsList.size(); i++) {
            Mutation selectedPopulation = new Mutation(this.codeMaker.getLength(), null, 0, this.gameManager).initialize();
            selectedPopulation.copyColorSeq(this.mutationsList.get(copyOrder.get(i)));
            updatedList.add(selectedPopulation);
        }

        return updatedList;
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
