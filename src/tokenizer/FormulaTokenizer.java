package tokenizer;

import proof.Operator;

import java.util.StringTokenizer;

public class FormulaTokenizer extends StringTokenizer {
    private static final String delims = " ()," + Operator.LALL.getUTFCode() + Operator.LEXST.getUTFCode();
    private String tkn = null;


    public FormulaTokenizer(String string) {
        super(string, delims, true);
        nextToken();
    }

    @Override
    public String nextToken() {
        String tkn = super.hasMoreTokens() ? super.nextToken() : null;
        while (tkn != null && tkn.equals(" ")) {
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
