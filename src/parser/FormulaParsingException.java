package parser;

public class FormulaParsingException extends Exception {
    FormulaParsingException(String errorMessage) {
        super("Error parsing formula!" + errorMessage);
    }
}
