package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

import java.util.ArrayList;

public final class Wff {
    private final Implication antecedent;
    private final Implication consequent;
    private final int hash;

    public Wff(Implication antecedent, Implication consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
        hash = new HashCodeBuilder(2309, 5843).
                append(this.antecedent).
                append(this.consequent).
                toHashCode();
    }

    @Override
    public String toString() {
        return antecedent + (consequent == null ? "" : " " + Operator.LIFF.getUTFCode() + " " + consequent);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Wff))
            return false;
        if (obj == this)
            return true;

        Wff toCheck = (Wff) obj;
        return new EqualsBuilder().
                append(this.antecedent, toCheck.antecedent).
                append(this.consequent, toCheck.consequent).
                isEquals();
    }

    Operator getMainOperator() {
        return consequent == null ? antecedent.getMainOperator() : Operator.LIFF;
    }

    public Implication getAntecedent() {
        return antecedent;
    }

    public Implication getConsequent() {
        return consequent;
    }

    Formula toFormula() {
        return new Formula(new ArrayList<>(0), this);
    }
}
