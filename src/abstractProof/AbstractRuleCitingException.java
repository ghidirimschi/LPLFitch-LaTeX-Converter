package abstractProof;

public class AbstractRuleCitingException extends Exception
{
    AbstractRuleCitingException(int rownNr, String errorMessage) {
        super("Invalid citing on row " + rownNr + "! " + errorMessage);
    }
}
