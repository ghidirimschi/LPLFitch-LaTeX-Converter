package formula;

/**
 * This interface represents the <term> non-terminal as specified in the BNF grammar (final report).
 */
public interface Argument {
    public boolean isEqualWithReplacement(Argument other, Argument argument, Argument newArgument);
}
