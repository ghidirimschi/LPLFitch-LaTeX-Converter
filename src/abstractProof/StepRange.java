package abstractProof;

import org.apache.commons.lang3.Range;


/**
 * This class implements a list of ranges and common operations used for this data structure.
 * It is used to represent ranges of steps in cited steps of inferences.
 */
public final class StepRange {
    private final Range<Integer> range;

    public StepRange(int from, int to)
    {
        range = Range.between(from, to);
    }

    public StepRange(int rowNr)
    {
        range = Range.between(rowNr, rowNr);
    }

    public boolean isSingleStep() {
        return range.getMinimum().equals(range.getMaximum());
    }

    public int getMinimum() {
        return range.getMinimum();
    }

    public int getMaximum() {
        return range.getMaximum();
    }

}
