package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

public class Negation implements Sentence {
    private final Sentence sentence;
    private final int hash;

    public Negation(Sentence sentence) {
        this.sentence = sentence;
        hash = new HashCodeBuilder(1097, 4801).
                append(this.sentence).
                toHashCode();
    }

    @Override
    public String toString() {
        return "(" + Operator.LNOT.getUTFCode() + sentence + ")";
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Negation))
            return false;
        if (obj == this)
            return true;

        Negation toCheck = (Negation) obj;
        return new EqualsBuilder().
                append(this.sentence, toCheck.sentence).
                isEquals();
    }

    public Sentence getSentence() {
        return sentence;
    }

    @Override
    public boolean isEqualWithReplacement(Sentence other, Argument argument, Argument newArgument) {
        if (!(other instanceof Negation))
            return false;
        if (other == this)
            return true;

        return sentence.isEqualWithReplacement(((Negation) other).sentence, argument, newArgument);
    }
}
