package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

import java.util.ArrayList;
import java.util.HashSet;

public final class Disjunction implements Sentence {
    private final Sentence firstDisjunct;
    private final Sentence secondDisjunct;
    private final ArrayList<Sentence> nestedDisjuncts;
    private final HashSet<Sentence> nestedDisjunctsSet;
    private final int hash;

    public Disjunction(Sentence firstDisjunct, Sentence secondDisjunct) {
        this.firstDisjunct = firstDisjunct;
        this.secondDisjunct = secondDisjunct;
        this.nestedDisjuncts = getNestedDisjuncts();
        this.nestedDisjunctsSet = new HashSet<>(nestedDisjuncts);
        hash = new HashCodeBuilder(1439, 7219).
                append(this.nestedDisjunctsSet).
                toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(nestedDisjuncts.get(0).toString());
        nestedDisjuncts.stream().skip(1).forEach(s -> stringBuilder.append(" ").append(Operator.LOR.getUTFCode()).append(" ").append(s));
        return "(" +  stringBuilder.toString() + ")";
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Disjunction)) return false;
        if (obj == this) return true;

        Disjunction other = (Disjunction) obj;
        return new EqualsBuilder().
                append(this.nestedDisjunctsSet, other.nestedDisjunctsSet).
                isEquals();
    }

    public ArrayList<Sentence> getNestedDisjuncts() {
        ArrayList<Sentence> nestedConjuncts = new ArrayList<>(2);
        if (firstDisjunct instanceof Disjunction) {
            nestedConjuncts.addAll(((Disjunction) firstDisjunct).getNestedDisjuncts());
        } else {
            nestedConjuncts.add(firstDisjunct);
        }
        if (secondDisjunct instanceof Disjunction) {
            nestedConjuncts.addAll(((Disjunction) secondDisjunct).getNestedDisjuncts());
        } else {
            nestedConjuncts.add(secondDisjunct);
        }
        return nestedConjuncts;
    }

    @Override
    public boolean isEqualWithReplacement(Sentence other, Argument argument, Argument newArgument) {
        if (!(other instanceof Disjunction))
            return false;
        if (other == this)
            return true;

        return firstDisjunct.isEqualWithReplacement(((Disjunction) other).firstDisjunct, argument, newArgument) && secondDisjunct.isEqualWithReplacement(((Disjunction) other).secondDisjunct, argument, newArgument);
    }
}
