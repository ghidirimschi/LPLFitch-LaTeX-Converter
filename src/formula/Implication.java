package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

public final class Implication implements Sentence {
    private final Sentence antecedent;
    private final Sentence consequent;
    private final int hash;

    public Implication(Sentence antecedent, Sentence consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
        hash = new HashCodeBuilder(9781, 17137).
                append(this.antecedent).
                append(this.consequent).
                toHashCode();
    }

    @Override
    public String toString() {
        return "(" + antecedent + " " + Operator.LIF.getUTFCode() + " " + consequent + ")";
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

    public Sentence getAntecedent() {
        return antecedent;
    }

    public Sentence getConsequent() {
        return consequent;
    }

    @Override
    public boolean isEqualWithReplacement(Sentence other, Argument argument, Argument newArgument) {
        if (!(other instanceof Implication))
            return false;
        if (other == this)
            return true;
        return antecedent.isEqualWithReplacement(((Implication) other).antecedent, argument, newArgument) && consequent.isEqualWithReplacement(((Implication) other).consequent, argument, newArgument);
    }
}
