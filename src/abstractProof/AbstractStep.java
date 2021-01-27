package abstractProof;

import formula.Sentence;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;

/**
 * This interface specifies the common methods the classes that represent Abstract Steps must implement.
 */
public interface AbstractStep {
    /**
     * This method verifies if this abstract step is valid.
     * @return true if valid, false otherwise
     * @throws AbstractRuleCitingException if a rule citing is incorrect.
     */
    public boolean isValid(MutableInt rowNr, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException;
    /**
     * This method verifies if this abstract step is pedantically valid and throws an error if not.
     * Must only be called if isValid() returned true.
     * @throws AbstractRulePedanticException if the abstract step is pedantically invalid.
     */
    public void checkPedanticValidity(MutableInt rowNr, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException, AbstractRulePedanticException;

    /**
     * This method returns the number of rows this abstract step occupies in the proof.
     * @return the number of rows
     */
    public int rowSize();

    /**
     * Returns the sentence representation of this abstract step. Note that not all abstract step can have a sentence representation.
     * @return the sentence, or null
     */
    public Sentence getSentence();
}
