package formula;

import proof.Operator;

public class Forall implements Quantifier {
    private String freeVariable;

    public Forall(String freeVariable) {
        this.freeVariable = freeVariable;
    }

    @Override
    public String toString() {
        return Operator.LALL.getUTFCode() + freeVariable;
    }
}
