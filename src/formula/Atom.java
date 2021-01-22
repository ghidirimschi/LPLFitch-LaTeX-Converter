package formula;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

import java.util.ArrayList;


public final class Atom {
    private final int negationNr;
    private final Predicate predicate;
    private final int hash;

    public Atom(int negationNr, Predicate predicate) {
        this.negationNr = negationNr;
        this.predicate = predicate;
        hash = new HashCodeBuilder(1097, 4801).
                append(this.negationNr).
                append(this.predicate).
                toHashCode();
    }

    @Override
    public String toString() {
        return StringUtils.repeat(Operator.LNOT.getUTFCode(), negationNr) + predicate;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Atom))
            return false;
        if (obj == this)
            return true;

        Atom toCheck = (Atom) obj;
        return new EqualsBuilder().
                        append(this.negationNr, toCheck.negationNr).
                        append(this.predicate, toCheck.predicate).
                        isEquals();
    }

    Operator getMainOperator() {
        return negationNr == 0 ? predicate.getMainOperator() : Operator.LNOT;
    }

    public Formula toFormula() {
        ArrayList<Atom> atoms = new ArrayList<>(1);
        atoms.add(this);
        return new Disjunction(atoms).toFormula();
    }

    public int getNegationNr() {
        return negationNr;
    }

    public Predicate getPredicate() {
        return predicate;
    }
}
