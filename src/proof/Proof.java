package proof;

import abstractProof.AbstractPremise;
import abstractProof.AbstractProof;
import abstractProof.AbstractStep;
import org.apache.commons.lang3.mutable.MutableInt;
import parser.FormulaParser;
import parser.FormulaParsingException;

import java.util.ArrayList;

/**
 * This class implements the CST representation of a Fitch proof. It consists of a list of premises and another
 * list of steps, as well in CST representation.
 */
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

    /**
     * Compiles the CST to LaTeX representation.
     * @return the LaTeX representation.
     */
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

    /**
     * Converts the CST to AST representation.
     * @return the AST representation.
     * @throws ConverterException if the conversion was impossible (e.g. one of the formula was not well-formed)
     */
    public AbstractProof toAbstract() throws ConverterException {
        MutableInt rowNr = new MutableInt(1);
        ArrayList<AbstractPremise> aPremises = new ArrayList<>(premises.size());
        for (Premise premise : premises) {
            try {
                aPremises.add(new AbstractPremise(FormulaParser.parse(premise.getWff())));
            } catch (FormulaParsingException e) {
                throw new ConverterException(rowNr.intValue(), e.getMessage());
            }
            rowNr.increment();
        }
        ArrayList<AbstractStep> aSteps = new ArrayList<>(steps.size());
        for (Step step : steps) {
            aSteps.add(step.toAbstract(rowNr));
        }
        return new AbstractProof(aPremises, aSteps);
    }
}
