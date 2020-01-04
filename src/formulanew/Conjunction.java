package formulanew;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

import java.util.ArrayList;
import java.util.HashSet;

public final class Conjunction implements Sentence {
    private final Sentence firstConjunct;
    private final Sentence secondConjunct;
    private final ArrayList<Sentence> nestedConjuncts;
    private final HashSet<Sentence> nestedConjunctsSet;
    private final int hash;

    public Conjunction(Sentence firstConjunct, Sentence secondConjunct) {
        this.firstConjunct = firstConjunct;
        this.secondConjunct = secondConjunct;
        this.nestedConjuncts = getNestedConjuncts();
        this.nestedConjunctsSet = new HashSet<>(nestedConjuncts);
        hash = new HashCodeBuilder(1523, 2287).
                append(this.nestedConjunctsSet).
                toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(nestedConjuncts.get(0).toString());
        nestedConjuncts.stream().skip(1).forEach(s -> stringBuilder.append(" ").append(Operator.LAND.getUTFCode()).append(" ").append(s));
        return "(" + stringBuilder.toString() + ")";
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Conjunction)) return false;
        if (obj == this) return true;

        Conjunction other = (Conjunction) obj;
        return new EqualsBuilder().
                append(this.nestedConjunctsSet, other.nestedConjunctsSet).
                isEquals();
    }

    public ArrayList<Sentence> getNestedConjuncts() {
        ArrayList<Sentence> nestedConjuncts = new ArrayList<>(2);
        if (firstConjunct instanceof Conjunction) {
            nestedConjuncts.addAll(((Conjunction) firstConjunct).getNestedConjuncts());
        } else {
            nestedConjuncts.add(firstConjunct);
        }
        if (secondConjunct instanceof Conjunction) {
            nestedConjuncts.addAll(((Conjunction) secondConjunct).getNestedConjuncts());
        } else {
            nestedConjuncts.add(secondConjunct);
        }
        return nestedConjuncts;
    }
}
