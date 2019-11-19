package formulanew;

import proof.Operator;

public final class Contradiction implements Sentence {
    private static final Contradiction instance = new Contradiction();

    private Contradiction() {}

    public static Contradiction getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return Operator.LCONTR.getUTFCode();
    }

}
