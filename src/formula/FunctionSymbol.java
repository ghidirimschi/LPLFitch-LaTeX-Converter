package formula;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.stream.Collectors;

public final class FunctionSymbol implements Argument {
    private final String funcName;
    private final ArrayList<Argument> arguments;
    private final int hash;

    public FunctionSymbol(String funcName, ArrayList<Argument> arguments) {
        this.funcName = funcName;
        this.arguments = new ArrayList<>(arguments);
        hash = new HashCodeBuilder(16831, 26687).
                append(this.funcName).
                append(this.arguments).
                toHashCode();
    }

    @Override
    public String toString() {
        return funcName + "(" + arguments.stream().limit(arguments.size() - 1).map(s -> s + ", ").collect(Collectors.joining())
                + arguments.get(arguments.size() - 1) + ")";
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FunctionSymbol))
            return false;
        if (obj == this)
            return true;

        FunctionSymbol toCheck = (FunctionSymbol) obj;
        return new EqualsBuilder().
                append(this.funcName, toCheck.funcName).
                append(this.arguments, toCheck.arguments).
                isEquals();

    }


}
