package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

public final class Forall implements Quantifier {
    private final String freeVariable;
    private final int hash;

    public Forall(String freeVariable) {
        this.freeVariable = freeVariable;
        hash = new HashCodeBuilder(709, 1889).
                append(this.freeVariable).
                toHashCode();
    }

    @Override
    public String toString() {
        return Operator.LALL.getUTFCode() + freeVariable;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Forall))
            return false;
        if (obj == this)
            return true;

        Forall toCheck = (Forall) obj;
        return new EqualsBuilder().
                append(this.freeVariable, toCheck.freeVariable).
                isEquals();

    }

    @Override
    public Operator getMainOperator() {
        return Operator.LALL;
    }
}
