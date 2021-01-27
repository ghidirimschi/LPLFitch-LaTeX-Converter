package abstractProof;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;


/**
 * This class implements the abstract representation of a Fitch proof. It consists of a list of premises and another
 * list of steps.
 */
public final class AbstractProof {
    private final ArrayList<AbstractPremise> premises;
    private final ArrayList<AbstractStep> steps;

    public AbstractProof(ArrayList<AbstractPremise> premises, ArrayList<AbstractStep> steps) {
        this.premises = new ArrayList<>(premises);
        this.steps = new ArrayList<>(steps);
    }

    /**
     * This method verifies if this abstract proof is valid.
     * @return true if valid, false otherwise
     * @throws AbstractRuleCitingException if a rule citing is incorrect.
     */
    public boolean isValid() throws AbstractRuleCitingException {
        MutableInt rowNr = new MutableInt(premises.size() + 1);
        ArrayList<AbstractStep> runningSentences = new ArrayList<>(premises);
        for (AbstractStep step : steps) {
            if (!step.isValid(rowNr, runningSentences)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method verifies if this proof is pedantically valid and throws an error if not.
     * Must only be called if isValid() returned true.
     * @throws AbstractRulePedanticException if the proof is pedantically invalid.
     */
    public void checkPedanticValidity() throws AbstractRuleCitingException, AbstractRulePedanticException {
        MutableInt rowNr = new MutableInt(premises.size() + 1);
        ArrayList<AbstractStep> runningSentences = new ArrayList<>(premises);
        for (AbstractStep step : steps) {
           step.checkPedanticValidity(rowNr, runningSentences);
        }
    }
}
