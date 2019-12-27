package abstractProof;

import formulanew.Sentence;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;

public final class AbstractSubProof implements AbstractStep {
    private final AbstractPremise premise;
    private final ArrayList<AbstractStep> steps;

    public AbstractSubProof(AbstractPremise premise, ArrayList<AbstractStep> steps) {
        this.premise = premise;
        this.steps = new ArrayList<>(steps);
    }

    @Override
    public boolean isValid(MutableInt rowNr, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
        ArrayList<AbstractStep> tmpRunningSteps = new ArrayList<>(runningSteps);
        premise.isValid(rowNr, tmpRunningSteps);
        for (AbstractStep step : steps) {
            if (!step.isValid(rowNr, tmpRunningSteps)) {
                return false;
            }
        }
        runningSteps.add(this);
        return true;
    }

    @Override
    public int rowSize() {
        int sum = 1;
        for (AbstractStep step : steps) {
            sum += step.rowSize();
        }
        return sum;
    }

    @Override
    public Sentence getSentence() {
        return null;
    }
}
