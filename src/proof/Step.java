package proof;

import org.apache.commons.lang3.mutable.MutableInt;

public interface Step {
    public void printStep(int level);
    public void printLatex(MutableInt row);
}