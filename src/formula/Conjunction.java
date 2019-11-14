package formula;


import proof.Operator;

import java.util.ArrayList;

public class Conjunction {
    private ArrayList<Disjunction> disjunctions;

    public Conjunction(ArrayList<Disjunction> disjunctions) {
        this.disjunctions = disjunctions;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(disjunctions.get(0).toString());
        disjunctions.stream().skip(1).forEach(s -> stringBuilder.append(" ").append(Operator.LAND.getUTFCode()).append(" ").append(s));
        return stringBuilder.toString();
    }
}
