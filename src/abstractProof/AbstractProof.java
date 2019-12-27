package abstractProof;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;

public final class AbstractProof {
    private final ArrayList<AbstractPremise> premises;
    private final ArrayList<AbstractStep> steps;

    public AbstractProof(ArrayList<AbstractPremise> premises, ArrayList<AbstractStep> steps) {
        this.premises = new ArrayList<>(premises);
        this.steps = new ArrayList<>(steps);
    }


    public boolean isValid() throws AbstractRuleCitingException {
        MutableInt rowNr = new MutableInt(1);
        ArrayList<AbstractStep> runningSentences = new ArrayList<>(premises);
        for (AbstractStep step : steps) {
            if (!step.isValid(rowNr, runningSentences)) {
                return false;
            }
        }
        return true;
    }
}
