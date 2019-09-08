package proof;

import org.apache.commons.lang3.mutable.MutableInt;

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
}
