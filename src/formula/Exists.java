package formula;

import proof.Operator;

public class Exists implements Quantifier {
    private String freeVariable;

    public Exists(String freeVariable) {
        this.freeVariable = freeVariable;
    }

    @Override
    public String toString() {
        return Operator.LEXST.getUTFCode() + freeVariable;
    }
}
