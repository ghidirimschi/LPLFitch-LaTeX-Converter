package formulanew;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

public class BiImplication implements Sentence {
    private final Sentence antecedent;
    private final Sentence consequent;
    private final int hash;

    public BiImplication(Sentence antecedent, Sentence consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;
        hash = new HashCodeBuilder(2309, 5843).
                append(this.antecedent).
                append(this.consequent).
                toHashCode();
    }

    @Override
    public String toString() {
        return "(" + antecedent + " " + Operator.LIFF.getUTFCode() + " " + consequent + ")";
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BiImplication))
            return false;
        if (obj == this)
            return true;

        BiImplication other = (BiImplication) obj;
        return new EqualsBuilder().
                append(this.antecedent, other.antecedent).
                append(this.consequent, other.consequent).
                isEquals();
    }
}
