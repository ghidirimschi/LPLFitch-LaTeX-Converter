package proof;

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
}
