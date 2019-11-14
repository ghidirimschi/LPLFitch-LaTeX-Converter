package formula;

import proof.Operator;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Formula {
    private ArrayList<Quantifier> quantifiers;
    private Wff wff;

    public Formula(ArrayList<Quantifier> quantifiers, Wff wff) {
        this.quantifiers = quantifiers;
        this.wff = wff;
    }

    @Override
    public String toString() {
        return (quantifiers.isEmpty() ? "" : quantifiers.stream().map(Quantifier::toString).collect(Collectors.joining()) + " ") + wff;
    }


}
