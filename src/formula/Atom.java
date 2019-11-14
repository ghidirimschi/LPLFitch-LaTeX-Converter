package formula;

import org.apache.commons.lang3.StringUtils;
import proof.Operator;


public class Atom {
    private int negationNr;
    private Predicate predicate;

    public Atom(int negationNr, Predicate predicate) {
        this.negationNr = negationNr;
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return StringUtils.repeat(Operator.LNOT.getUTFCode(), negationNr) + predicate;
    }
}
