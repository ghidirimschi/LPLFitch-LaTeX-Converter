package proof;

import org.apache.commons.lang3.StringUtils;

public final class Operator {
    private static final String[] htmlchars = new String[]{"\u2227", "\u2228", "\u2192", "\u2194", "\u00AC", "\u22A5"};
    private static final String[] texCodes = new String[]{"\\land ", "\\lor ", "\\rightarrow ", "\\leftrightarrow ", "\\neg ", "\\bot "};

    public static String convertWff(String wff) {
        return StringUtils.replaceEach(wff, htmlchars, texCodes);
    }


}
