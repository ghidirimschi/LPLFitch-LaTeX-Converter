package proof;

import org.apache.commons.lang3.mutable.MutableInt;

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

    void printLatex(MutableInt row) {
        System.out.println("\t\\hypo {" + row.getAndIncrement() + "} {" + Operator.convertWff(wff) + "}");
    }
}
