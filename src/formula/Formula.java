package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

import java.util.ArrayList;
import java.util.stream.Collectors;

public final class Formula {
    private final ArrayList<Quantifier> quantifiers;
    private final Wff wff;
    private final int hash;

    public Formula(ArrayList<Quantifier> quantifiers, Wff wff) {
        this.quantifiers = new ArrayList<>(quantifiers);
        this.wff = wff;
        hash = new HashCodeBuilder(7591, 15227).
                append(this.quantifiers).
                append(this.wff).
                toHashCode();
    }

    @Override
    public String toString() {
        return (quantifiers.isEmpty() ? "" : quantifiers.stream().map(Quantifier::toString).collect(Collectors.joining()) + " ") + wff;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof NestedFormula) {
            return equals(((NestedFormula) obj).getFormula());
        }
        if (!(obj instanceof Formula))
            return false;
        if (obj == this)
            return true;
        Formula toCheck = (Formula) obj;
        return new EqualsBuilder().
                append(this.quantifiers, toCheck.quantifiers).
                append(this.wff, toCheck.wff).
                isEquals();
    }

    public Operator getMainOperator() {
        return quantifiers.isEmpty() ? wff.getMainOperator() : quantifiers.get(0).getMainOperator();
    }

    public Wff getWff() {
        return wff;
    }
}
