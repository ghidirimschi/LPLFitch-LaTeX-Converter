package formula;

public class NestedFormula implements Predicate {
    Formula formula;

    public NestedFormula(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "(" + formula + ")";
    }
}
