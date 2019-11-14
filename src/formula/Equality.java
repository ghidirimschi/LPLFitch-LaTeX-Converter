package formula;

public class Equality implements Predicate {
    private Argument firstOperand;
    private Argument secondOperand;

    public Equality(Argument firstOperand, Argument secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    @Override
    public String toString() {
        return firstOperand + " = " + secondOperand;
    }
}
