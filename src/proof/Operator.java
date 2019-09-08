package proof;

import org.apache.commons.lang3.StringUtils;

public final class Operator {
    private static final String[] htmlchars = new String[]{"\u2227", "\u2228", "\u2192", "\u2194", "\u00AC", "\u22A5", "\u2200", "\u2203"};
    private static final String[] texCodes = new String[]{"\\land ", "\\lor ", "\\rightarrow ", "\\leftrightarrow ", "\\neg ", "\\bot ", "\\forall ", "\\exists "};

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
