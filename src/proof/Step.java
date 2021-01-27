package proof;

import abstractProof.AbstractStep;
import org.apache.commons.lang3.mutable.MutableInt;


/**
 * This interface specifies the common methods the classes that represent CST Steps must implement.
 */
public interface Step {
    /**
     * Print the step in the Terminal. (useful for Development)
     */
    public void printStep(int level);

    /**
     * Appends the LaTeX code for this step (in textual representation) to the sb parameter.
     * @param row the row number of the current step
     */
    public void exportLatex(StringBuilder sb, MutableInt row);

    /**
     * Converts the CST to AST representation of this step.
     * @param rowNr the row number of the current step
     * @return the AST representation.
     * @throws ConverterException if the conversion was impossible (e.g. one of the formula was not well-formed)
     */
    public AbstractStep toAbstract(MutableInt rowNr) throws ConverterException;
}
