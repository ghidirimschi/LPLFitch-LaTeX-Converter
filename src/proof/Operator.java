package proof;

import org.apache.commons.lang3.StringUtils;

/**
 * This enum class implements the CST representation of logical operators and enumerates them.
 */
public enum Operator {
    LAND("\u2227", "\\land "),
    LOR("\u2228", "\\lor "),
    LIF("\u2192", "\\rightarrow "),
    LIFF("\u2194", "\\leftrightarrow "),
    LNOT("\u00AC", "\\neg "),
    LCONTR("\u22A5", "\\bot "),
    LALL("\u2200", "\\forall "),
    LEXST("\u2203", "\\exists "),
    EQUALS("=", "=");

    private final String UTFCode;
    private final String TEXCode;


    Operator(String UTFCode, String TEXCode) {
        this.UTFCode = UTFCode;
        this.TEXCode = TEXCode;

    }

    public String getUTFCode() {
        return UTFCode;
    }

    private static final String[] htmlchars = new String[]{"\u2227", "\u2228", "\u2192", "\u2194", "\u00AC", "\u22A5", "\u2200", "\u2203"};
    private static final String[] texCodes = new String[]{"\\land ", "\\lor ", "\\rightarrow ", "\\leftrightarrow ", "\\neg ", "\\bot ", "\\forall ", "\\exists "};

    /**
     * This method normalizes the string representation of formula.
     * @param wff the formula
     * @return the normalized reprsentation
     */
    public static String convertWff(String wff) {
        StringBuilder sb = new StringBuilder();
        char curr;
        int len = wff.length();
        for(int i = 0; i < len; ++i) {
            curr = wff.charAt(i);
            if(Character.isLetter(curr)) {
                sb.append("\\textrm{").append(curr);
                while(++i < len && Character.isLetter((curr = wff.charAt(i)))) {
                    sb.append(curr);
                }
                --i;
                sb.append("}");
            } else {
             sb.append(curr);
            }
        }


        return StringUtils.replaceEach(sb.toString(), htmlchars, texCodes);
    }


}
