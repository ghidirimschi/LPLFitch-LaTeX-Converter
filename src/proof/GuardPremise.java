package proof;

import org.apache.commons.lang3.mutable.MutableInt;

public class GuardPremise extends Premise {
    private String guardVar;

    public GuardPremise(String wff, String guardVar) {
        super(wff);
        this.guardVar = guardVar.replace(" ", ",\\ ");
    }

    @Override
    void printLatex(MutableInt row) {
        System.out.println("\t\\hypo {" + row.getAndIncrement() + "} {\\boxed{" + guardVar + "} " +  Operator.convertWff(getWff()) + "}");
    }
}
