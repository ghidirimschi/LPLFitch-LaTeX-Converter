package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

import java.util.ArrayList;
import java.util.stream.Collectors;

public final class Formula implements Predicate {
    private final ArrayList<Quantifier> quantifiers;
    private final Wff wff;
    private final int hash;
    private final boolean parentheses;

    public Formula(ArrayList<Quantifier> quantifiers, Wff wff, boolean parentheses) {
        this.quantifiers = new ArrayList<>(quantifiers);
        this.wff = wff;
        this.parentheses = parentheses;
        hash = new HashCodeBuilder(7591, 15227).
                append(this.quantifiers).
                append(this.wff).
                toHashCode();
    }

    public Formula(ArrayList<Quantifier> quantifiers, Wff wff) {
        this(quantifiers, wff, false);
    }


    @Override
    public String toString() {
        return (parentheses ? "(" : "") + (quantifiers.isEmpty() ? "" : quantifiers.stream().map(Quantifier::toString).collect(Collectors.joining()) + " ") + wff + (parentheses ? ")" : "");
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Formula)) return false;
        if (obj == this) return true;
        Formula rhs = (Formula) obj;

        return new EqualsBuilder().
                append(this.quantifiers, rhs.quantifiers).
                append(this.wff, rhs.wff).
                isEquals();
    }

    public Operator getMainOperator() {
        return quantifiers.isEmpty() ? wff.getMainOperator() : quantifiers.get(0).getMainOperator();
    }

    public Wff getWff() {
        return wff;
    }

    public ArrayList<Quantifier> getQuantifiers() {
        return new ArrayList<>(quantifiers);
    }

    public Formula simplified() {
        if (!quantifiers.isEmpty()) {
            return this;
        }
        if (wff.getConsequent() != null) {
            return this;
        }
        Implication implication = wff.getAntecedent();
        if (implication.getConsequent() != null) {
            return this;
        }
        Conjunction conjunction = implication.getAntecedent();
        if (conjunction.getDisjunctions().size() > 1) {
            return this;
        }
        Disjunction disjunction = conjunction.getDisjunctions().get(0);
        if (disjunction.getAtoms().size() > 1) {
            return this;
        }
        Atom atom = disjunction.getAtoms().get(0);
        if (atom.getNegationNr() > 0) {
            return this;
        }
        Predicate predicate = atom.getPredicate();
        if (!(predicate instanceof Formula)) {
            return this;
        }
        return ((Formula) predicate).simplified();
    }
}
