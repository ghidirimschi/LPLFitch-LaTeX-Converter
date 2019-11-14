package formula;

import proof.Operator;

import java.util.ArrayList;

public class Disjunction {
    private ArrayList<Atom> atoms;

    public Disjunction(ArrayList<Atom> atoms) {
        this.atoms = atoms;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(atoms.get(0).toString());
        atoms.stream().skip(1).forEach(s -> stringBuilder.append(" ").append(Operator.LOR.getUTFCode()).append(" ").append(s));
        return stringBuilder.toString();
    }
}
