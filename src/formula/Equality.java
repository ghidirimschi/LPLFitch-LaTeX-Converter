package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Equality implements AtomicSentence {
    private final Argument firstOperand;
    private final Argument secondOperand;
    private final int hash;

    public Equality(Argument firstOperand, Argument secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        hash = new HashCodeBuilder(3023, 5903).
                append(this.firstOperand).
                append(this.secondOperand).
                toHashCode();
    }

    @Override
    public String toString() {
        return "(" +  firstOperand + " = " + secondOperand + ")";
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Equality))
            return false;
        if (obj == this)
            return true;

        Equality toCheck = (Equality) obj;
        return new EqualsBuilder().
                append(this.firstOperand, toCheck.firstOperand).
                append(this.secondOperand, toCheck.secondOperand).
                isEquals();

    }

    public Argument getFirstOperand() {
        return firstOperand;
    }

    public Argument getSecondOperand() {
        return secondOperand;
    }

    @Override
    public boolean isEqualWithReplacement(Sentence other, Argument argument, Argument newArgument) {
        if (!(other instanceof Equality))
            return false;
        if (other == this)
            return true;

        return firstOperand.isEqualWithReplacement(((Equality) other).firstOperand, argument, newArgument) && secondOperand.isEqualWithReplacement(((Equality) other).secondOperand, argument, newArgument);
    }
}
