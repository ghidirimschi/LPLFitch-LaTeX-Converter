package formulanew;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

public final class Exists implements Quantifier {
    private final String freeVariable;
    private final int hash;

    public Exists(String freeVariable) {
        this.freeVariable = freeVariable;
        hash = new HashCodeBuilder(2543, 7459).
                append(this.freeVariable).
                toHashCode();
    }

    @Override
    public String toString() {
        return Operator.LEXST.getUTFCode() + freeVariable;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Exists))
            return false;
        if (obj == this)
            return true;

        Exists toCheck = (Exists) obj;
        return new EqualsBuilder().
                append(this.freeVariable, toCheck.freeVariable).
                isEquals();

    }
}
