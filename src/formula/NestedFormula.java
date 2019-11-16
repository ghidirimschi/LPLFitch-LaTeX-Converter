package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import proof.Operator;

public final class NestedFormula implements Predicate {
    private final Formula formula;
    private final int hash;

    public NestedFormula(Formula formula) {
        this.formula = formula;
        hash = formula.hashCode();
    }

    @Override
    public String toString() {
        return "(" + formula + ")";
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Formula) {
            return formula.equals(obj);
        }
        if (!(obj instanceof NestedFormula))
            return false;
        if (obj == this)
            return true;

        NestedFormula toCheck = (NestedFormula) obj;
        return new EqualsBuilder().
                append(this.formula, toCheck.formula).
                isEquals();
    }

    @Override
    public Operator getMainOperator() {
        return formula.getMainOperator();
    }

    public Formula getFormula() {
        return formula;
    }
}
