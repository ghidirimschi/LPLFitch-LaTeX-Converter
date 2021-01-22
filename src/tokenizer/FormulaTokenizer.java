package tokenizer;

import proof.Operator;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

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

    public boolean isNextEqualTo(String string) {
        if (tkn == null || !tkn.equals(string)) {
            return false;
        }
        nextToken();
        return true;
    }
}
