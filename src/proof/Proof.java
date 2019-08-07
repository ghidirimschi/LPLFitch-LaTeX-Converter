package proof;

import java.util.ArrayList;

public class Proof {
    private ArrayList<Premise> premises;
    private ArrayList<Step> steps;

    public Proof() {
        premises = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public void addPremise(Premise premise) {
        premises.add(premise);
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public ArrayList<Premise> getPremises() {
        return premises;
    }
}
