package formula;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

import java.util.ArrayList;

public final class Conjunction {
    private final ArrayList<Disjunction> disjunctions;
    private final int hash;

    public Conjunction(ArrayList<Disjunction> disjunctions) {
        this.disjunctions = new ArrayList<>(disjunctions);
        hash = new HashCodeBuilder(1523, 2287).
                append(this.disjunctions).
                toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(disjunctions.get(0).toString());
        disjunctions.stream().skip(1).forEach(s -> stringBuilder.append(" ").append(Operator.LAND.getUTFCode()).append(" ").append(s));
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Conjunction)) return false;
        if (obj == this) return true;

        Conjunction toCheck = (Conjunction) obj;
        return new EqualsBuilder().
                append(this.disjunctions, toCheck.disjunctions).
                isEquals();
    }

    Operator getMainOperator() {
        return disjunctions.size() == 1 ? disjunctions.get(0).getMainOperator() : Operator.LAND;
    }

    public ArrayList<Disjunction> getDisjunctions() {
        return new ArrayList<>(disjunctions);
    }

    Formula toFormula() {
        return new Implication(this, null).toFormula();
    }
}
