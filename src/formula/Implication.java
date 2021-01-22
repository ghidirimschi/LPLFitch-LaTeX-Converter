package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

public final class Implication {
    private final Conjunction antecedent;
    private final Conjunction consequent;
    private final int hash;

    public Implication(Conjunction antecedent, Conjunction consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
        hash = new HashCodeBuilder(9781, 17137).
                append(this.antecedent).
                append(this.consequent).
                toHashCode();
    }

    @Override
    public String toString() {
        return antecedent + (consequent == null ? "" : " " + Operator.LIF.getUTFCode() + " " + consequent);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Implication))
            return false;
        if (obj == this)
            return true;

        Implication toCheck = (Implication) obj;
        return new EqualsBuilder().
                append(this.antecedent, toCheck.antecedent).
                append(this.consequent, toCheck.consequent).
                isEquals();
    }

    Operator getMainOperator() {
        return consequent == null ? antecedent.getMainOperator() : Operator.LIF;
    }

    public Conjunction getAntecedent() {
        return antecedent;
    }

    public Conjunction getConsequent() {
        return consequent;
    }

    Formula toFormula() {
        return new Wff(this, null).toFormula();
    }
}
