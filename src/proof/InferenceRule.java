package proof;

import java.util.HashMap;

public enum InferenceRule {
    UNDEFINED (""),
    REIT ("\\r"),
    CONJ_INTRO ("\\ai"),
    CONJ_ELIM ("\\ae"),
    DISJ_INTRO ("\\oi"),
    DISJ_ELIM ("\\oe"),
    IMPL_INTRO ("\\ii"),
    IMPL_ELIM ("\\ie"),
    EQIV_INTRO ("undefined"),
    EQIV_ELIM ("undefined"),
    NEGT_INTRO ("\\ni"),
    NEGT_ELIM ("\\ne"),
    CNTR_INTRO ("\\ne"),
    CNTR_ELIM ("\\be");

    InferenceRule(String latexCode) {
        this.latexCode = latexCode;
    }

    private final String latexCode;

    public String getLatexCode() {
        return latexCode;
    }

    private final static HashMap<String, InferenceRule> hashMap = new HashMap<>();
    static {
        hashMap.put("unspecified rule", UNDEFINED);
        hashMap.put("Reit", REIT);
        hashMap.put("\u2227 Intro", CONJ_INTRO);
        hashMap.put("\u2227 Elim", CONJ_ELIM);
        hashMap.put("\u2228 Intro", DISJ_INTRO);
        hashMap.put("\u2228 Elim", DISJ_ELIM);
        hashMap.put("\u2192 Intro", IMPL_INTRO);
        hashMap.put("\u2192 Elim", IMPL_ELIM);
        hashMap.put("\u2194 Intro", EQIV_INTRO);
        hashMap.put("\u2194 Elim", EQIV_ELIM);
        hashMap.put("\u00AC Intro", NEGT_INTRO);
        hashMap.put("\u00AC Elim", NEGT_ELIM);
        hashMap.put("\u22A5 Intro", CNTR_INTRO);
        hashMap.put("\u22A5 Elim", CNTR_ELIM);
    }


    public static InferenceRule parseInferenceRule(String s) {
        return hashMap.get(s);
    }
}
