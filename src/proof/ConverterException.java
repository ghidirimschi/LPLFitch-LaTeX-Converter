package proof;

public class ConverterException extends Exception  {
    ConverterException(int rowNr, String errorMessage) {
        super("Row " + rowNr + ": Error parsing formula!" + errorMessage);
    }
}

