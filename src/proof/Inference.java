package proof;

import abstractProof.AbstractInference;
import abstractProof.AbstractStep;
import formulanew.Sentence;
import org.apache.commons.lang3.mutable.MutableInt;
import parser.FormulaParser;
import parser.FormulaParsingException;

import java.util.ArrayList;

public class Inference implements Step {
    private String wff;
    private InferenceRule rule;
    private String ruleSupport;

    public Inference(String wff, InferenceRule rule, String ruleSupport) {
        this.wff = wff;
        this.rule = rule;
        this.ruleSupport = ruleSupport;
    }

    public String getWff() {
        return wff;
    }

    public InferenceRule getRule() {
        return rule;
    }

    public void printStep(int level) {
        for(int i = 0; i < level; ++i) {
            System.out.print('|');
        }
        System.out.println(' ' + wff + "\t" + ruleSupport);
    }

    @Override
    public void exportLatex(StringBuilder sb, MutableInt row) {
        sb.append("\t\\have {").append(row.getAndIncrement()).append("} {").append(Operator.convertWff(wff)).append("} \t ").
                append(rule.getLatexCode()).append("{").append(ruleSupport).append("}\n");
    }

    @Override
    public AbstractStep toAbstract(MutableInt rowNr) throws ConverterException {
        Sentence aWff;
        try {
            aWff = FormulaParser.parse(wff);
        } catch (FormulaParsingException e) {
            throw new ConverterException(rowNr.intValue(), e.getMessage());
        }
        rowNr.increment();
        String [] strSteps = ruleSupport.split(",");
        ArrayList<Integer> citedSteps = new ArrayList<>(strSteps.length);
        for (String str : strSteps) {
            try {
                citedSteps.add(Integer.valueOf(str.trim()));
            } catch (NumberFormatException e) {
                throw new ConverterException(rowNr.intValue(), "Cited step must be a positive integer!");
            }
        }
        return new AbstractInference(aWff, rule.toAbstract(), citedSteps);
     }

}
