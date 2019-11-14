package formula;

import proof.Operator;

public class Implication {
    private Conjunction antecedent;
    private Conjunction consequent;

    public Implication(Conjunction antecedent, Conjunction consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    @Override
    public String toString() {
        return antecedent + (consequent == null ? "" : " " + Operator.LIF.getUTFCode() + " " + consequent);
    }
}
