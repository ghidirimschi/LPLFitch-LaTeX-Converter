package tokenizer;

import proof.Operator;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


/**
 * This class extends the functionality of StringTokenizer.
 * Particularly, a token can be inspected without advancing the pointer.
 */
public class FormulaTokenizer extends StringTokenizer {
    private static final String skipdelims = " ";
    private static final String delims = "(),"+ Arrays.stream(Operator.values()).map(Operator::getUTFCode).collect(Collectors.joining());
    private String tkn = null;


    public FormulaTokenizer(String string) {
        super(string, skipdelims + delims, true);
        nextToken();
    }

    @Override
    public String nextToken() {
        String tkn = super.hasMoreTokens() ? super.nextToken() : null;
        while (tkn != null && tkn.length() == 1 && skipdelims.contains(tkn)) {
            tkn = super.hasMoreTokens() ? super.nextToken() : null;
        }
        String tmp = this.tkn;
        this.tkn = tkn;
        return tmp;
    }

    @Override
    public boolean hasMoreTokens() {
        return tkn != null;
    }

    /**
     * Verifies if the next token is equal to the specified string.
     * If it is the case the token pointer is advanced.
     * @param string the next token
     * @return true if token is equal, otherwise false.
     */
    public boolean isNextEqualTo(String string) {
        if (tkn == null || !tkn.equals(string)) {
            return false;
        }
        nextToken();
        return true;
    }
}
