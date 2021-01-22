package formulanew;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Name implements Argument {
    private final String name;
    private final int hash;

    public Name(String name) {
        this.name = name;
        hash = new HashCodeBuilder(4211, 9631).
                append(this.name).
                toHashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Name))
            return false;
        if (obj == this)
            return true;

        Name toCheck = (Name) obj;
        return new EqualsBuilder().
                append(this.name, toCheck.name).
                isEquals();
    }


    @Override
    public boolean isEqualWithReplacement(Argument other, Argument argument, Argument newArgument) {
        return equals(other) || (equals(argument) && other.equals(newArgument));
    }
}
