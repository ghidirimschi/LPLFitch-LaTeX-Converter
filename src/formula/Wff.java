package formula;

import proof.Operator;

public class Wff {
    private Implication antecedent;
    private Implication consequent;

    public Wff(Implication antecedent, Implication consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    @Override
    public String toString() {
        return antecedent + (consequent == null ? "" : " " + Operator.LIFF.getUTFCode() + " " + consequent);
    }
}
