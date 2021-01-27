package proof;

import org.apache.commons.lang3.mutable.MutableInt;


/**
 * This class implements the CST representation of a premise. It consists of a single logical formula
 * in String representation. It implements the Step interface.
 */
public class Premise {
    private String wff;

    public Premise(String wff) {
        this.wff = wff;
    }

    public String getWff() {
        return wff;
    }

    void printPremise(int level) {
        for(int i = 0; i < level; ++i) {
            System.out.print('|');
        }
        System.out.println(' ' + wff);
    }

    void exportLatex(StringBuilder sb, MutableInt row) {
        sb.append("\t\\hypo {").append(row.getAndIncrement()).append("} {").append(Operator.convertWff(wff)).append("}\n");
    }
}
