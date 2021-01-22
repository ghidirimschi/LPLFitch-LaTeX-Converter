package abstractProof;

public class AbstractRulePedanticException extends Exception
{
    AbstractRulePedanticException(int rownNr, String errorMessage) {
        super("Invalid pedantic application on row " + rownNr + ": " + errorMessage);
    }
}