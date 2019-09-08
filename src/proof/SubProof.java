package proof;

import org.apache.commons.lang3.mutable.MutableInt;

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

    public void printStep(int level) {
        ++level;
        premise.printPremise(level);
        for(int i = 0; i < level; ++i) {
            System.out.print('|');
        }
        System.out.println('-');
        for(Step step : steps) {
            step.printStep(level);
        }

    }

    @Override
    public void exportLatex(StringBuilder sb, MutableInt row) {
        sb.append("\t\\open\n");
        premise.exportLatex(sb, row);
        for(Step step : steps) {
            step.exportLatex(sb, row);
        }
        sb.append("\t\\close\n");
    }
}
