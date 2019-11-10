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

    public String exportLatex() {
        StringBuilder sb = new StringBuilder();
        MutableInt row = new MutableInt(1);
        sb.append("$\n\\begin{nd}\n");
        for(Premise premise : premises) {
            premise.exportLatex(sb, row);
        }
        for(Step step : steps) {
            step.exportLatex(sb, row);
        }
        sb.append("\\end{nd}\n$\n");
        return sb.toString();
    }
}
