package proof;

import abstractProof.AbstractPremise;
import abstractProof.AbstractStep;
import abstractProof.AbstractSubProof;
import org.apache.commons.lang3.mutable.MutableInt;
import parser.FormulaParser;
import parser.FormulaParsingException;

import java.util.ArrayList;

/**
 * This class implements the CST representation of a Fitch subproof. It consists of a single premise and a
 * list of steps, as well in CST representation. It implements the Step interface.
 */
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

    @Override
    public AbstractStep toAbstract(MutableInt rowNr) throws ConverterException {
        AbstractPremise aPremise;
        try {
            aPremise = new AbstractPremise(FormulaParser.parse(premise.getWff()));
        } catch (FormulaParsingException e) {
            throw new ConverterException(rowNr.intValue(), e.getMessage());
        }
        rowNr.increment();
        ArrayList<AbstractStep> abstractSteps = new ArrayList<>(steps.size());
        for (Step step : steps) {
            abstractSteps.add(step.toAbstract(rowNr));
        }
        return new AbstractSubProof(aPremise, abstractSteps);
    }
}
