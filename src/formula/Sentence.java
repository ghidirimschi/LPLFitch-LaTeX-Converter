package formula;

/**
 * This interface represents the <formula> non-terminal as specified in the BNF grammar (final report).
 */
public interface Sentence {
    /**
     * Verifies if the other sentence is equal by replacing an argument with another.
     * @param other         other sentence
     * @param argument      argument to replace with
     * @param newArgument   argument to be replaced with
     * @return              true if equal, false otherwise
     */
    public boolean isEqualWithReplacement(Sentence other, Argument argument, Argument newArgument);
}
