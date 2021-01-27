package proof;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 * This is a Premise subclass in CST representation which contains a guard variable.
 */
public class GuardPremise extends Premise {
    private String guardVar;

    public GuardPremise(String wff, String guardVar) {
        super(wff);
        this.guardVar = guardVar.replace(" ", ",\\ ");
    }

    @Override
    void exportLatex(StringBuilder sb, MutableInt row) {
        sb.append("\t\\hypo {").append(row.getAndIncrement()).append("} {\\boxed{").append(guardVar).append("} ").append(Operator.convertWff(getWff())).append("}\n");
    }
}
