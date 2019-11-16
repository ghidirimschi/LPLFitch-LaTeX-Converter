package proof;

public class InvalidRuleApplicationException extends Exception {
    InvalidRuleApplicationException(String ruleName, String errorMessage) {
        super("Invalid application of " + ruleName + ": " + errorMessage + ".");
    }
}
