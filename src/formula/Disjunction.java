package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import proof.Operator;

import java.util.ArrayList;

public final class Disjunction {
    private final ArrayList<Atom> atoms;
    private final int hash;


    public Disjunction(ArrayList<Atom> atoms) {
        this.atoms = new ArrayList<>(atoms);
        hash = new HashCodeBuilder(1439, 7219).
                append(this.atoms).
                toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(atoms.get(0).toString());
        atoms.stream().skip(1).forEach(s -> stringBuilder.append(" ").append(Operator.LOR.getUTFCode()).append(" ").append(s));
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Disjunction))
            return false;
        if (obj == this)
            return true;

        Disjunction toCheck = (Disjunction) obj;
        return new EqualsBuilder().
                append(this.atoms, toCheck.atoms).
                isEquals();
    }

    Operator getMainOperator() {
        return atoms.size() == 1 ? atoms.get(0).getMainOperator() : Operator.LOR;
    }

    public ArrayList<Atom> getAtoms() {
        return new ArrayList<>(atoms);
    }

    public Formula toFormula() {
        ArrayList<Disjunction> disjunctions = new ArrayList<>(1);
        disjunctions.add(this);
        return new Conjunction(disjunctions).toFormula();
    }
}
