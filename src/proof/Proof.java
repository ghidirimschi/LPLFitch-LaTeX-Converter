package proof;

import org.apache.commons.lang3.mutable.MutableInt;

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

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void printProof() {
        for(Premise premise : premises) {
            premise.printPremise(1);
        }
        System.out.println("|-");
        for(Step step : steps) {
            step.printStep(1);
        }
    }

    public void printLatex() {
        MutableInt row = new MutableInt(1);
        System.out.println("\\[\n\\begin{nd}");
        for(Premise premise : premises) {
            premise.printLatex(row);
        }
        for(Step step : steps) {
            step.printLatex(row);
        }
        System.out.println("\\end{nd}\n\\]");
    }
}
