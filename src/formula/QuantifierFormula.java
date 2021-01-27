package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This class implements both the quantifier expressions of the <formula> non-terminal.
 * This allows for a minor optimization for formula having multiple nested quantifiers.
 */
public class QuantifierFormula implements Sentence {
    private final ArrayList<Quantifier> quantifiers;
    private final Sentence sentence;
    private final int hash;

    public QuantifierFormula(ArrayList<Quantifier> quantifiers, Sentence sentence) {
        this.quantifiers = new ArrayList<>(quantifiers);
        this.sentence = sentence;
        hash = new HashCodeBuilder(7591, 15227).
                append(this.quantifiers).
                append(this.sentence).
                toHashCode();
    }

    @Override
    public String toString() {
        return "(" + (quantifiers.isEmpty() ? "" : quantifiers.stream().map(s -> s.toString() + " ").collect(Collectors.joining())) + sentence + ")";
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuantifierFormula)) return false;
        if (obj == this) return true;
        QuantifierFormula rhs = (QuantifierFormula) obj;

        return new EqualsBuilder().
                append(this.quantifiers, rhs.quantifiers).
                append(this.sentence, rhs.sentence).
                isEquals();
    }

    @Override
    public boolean isEqualWithReplacement(Sentence other, Argument argument, Argument newArgument) {
        if (!(other instanceof QuantifierFormula)) return false;
        if (other == this) return true;

        if(!quantifiers.equals(((QuantifierFormula) other).quantifiers)) {
            return false;
        }

        return sentence.isEqualWithReplacement(((QuantifierFormula) other).sentence, argument, newArgument);
    }
}
