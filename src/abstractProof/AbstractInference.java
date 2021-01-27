package abstractProof;

import formula.Sentence;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;


/**
 * This class implements the abstract representation of an inference. It consists of a well-formed formula (logical sentence),
 * a logical rule and a list of cited steps. It implements the AbstractStep interface.
 */
public final class AbstractInference implements AbstractStep {
    private final Sentence sentence;
    private final AbstractRule rule;
    private final ArrayList<StepRange> citedSteps;

    public AbstractInference(Sentence sentence, AbstractRule rule, ArrayList<StepRange> citedSteps) {
        this.sentence = sentence;
        this.rule = rule;
        this.citedSteps = new ArrayList<>(citedSteps);
    }

    public ArrayList<StepRange> getCitedSteps() {
        return new ArrayList<>(citedSteps);
    }

    @Override
    public boolean isValid(MutableInt rowNr, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
        boolean rtn = rule.isValidApplicationIn(rowNr.intValue(), this, runningSteps);
        runningSteps.add(this);
        rowNr.increment();
        return rtn;
    }

    @Override
    public void checkPedanticValidity(MutableInt rowNr, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException, AbstractRulePedanticException {
        rule.isPedanticApplicationIn(rowNr.intValue(), this, runningSteps);
        runningSteps.add(this);
        rowNr.increment();
    }

    @Override
    public int rowSize() {
        return 1;
    }

    @Override
    public Sentence getSentence() {
        return sentence;
    }
}
