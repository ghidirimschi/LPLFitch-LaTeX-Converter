package proof;

import java.util.ArrayList;

public class SubProof implements Step {
    private Premise premise;
    private ArrayList<Step> steps;

    public SubProof(Premise premise) {
        this.premise = premise;
        steps = new ArrayList<>();
    }

    public void addStep(Step step) {
        steps.add(step);
    }
}
