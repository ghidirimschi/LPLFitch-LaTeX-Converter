package formula;

import proof.Operator;

public class Contradiction implements Predicate {
    private static Contradiction instance = new Contradiction();

    private Contradiction() {

    }

    public static Contradiction getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return Operator.LCONTR.getUTFCode();
    }
}
