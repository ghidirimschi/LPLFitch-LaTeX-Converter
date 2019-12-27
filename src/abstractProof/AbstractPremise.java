package abstractProof;

import formulanew.Sentence;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;

public final class AbstractPremise implements AbstractStep {
    private final Sentence sentence;


    public AbstractPremise(Sentence sentence) {
        this.sentence =sentence;
    }

    @Override
    public boolean isValid(MutableInt rowNr, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
        rowNr.increment();
        runningSteps.add(this);
        return true;
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
