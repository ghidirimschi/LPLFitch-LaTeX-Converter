package proof;

import java.util.HashMap;

public enum InferenceRule {
    UNDEFINED (""),
    REIT ("\\by{reit}"),
    CONJ_INTRO ("\\by{$\\land$ Intro}"),
    CONJ_ELIM ("\\by{$\\land$ Elim}"),
    DISJ_INTRO ("\\by{$\\lor$ Intro}"),
    DISJ_ELIM ("\\by{$\\lor$ Elim}"),
    IMPL_INTRO ("\\by{$\\rightarrow$ Intro}"),
    IMPL_ELIM ("\\by{$\\rightarrow$ Elim}"),
    EQIV_INTRO ("\\by{$\\leftrightarrow$ Intro}"),
    EQIV_ELIM ("\\by{$\\leftrightarrow$ Elim}"),
    NEGT_INTRO ("\\by{$\\neg$ Intro}"),
    NEGT_ELIM ("\\by{$\\neg$ Elim}"),
    CNTR_INTRO ("\\by{$\\bot$ Intro}"),
    CNTR_ELIM ("\\by{$\\bot$ Elim}"),
    UNVR_INTRO ("\\by{$\\forall$ Intro}"),
    UNVR_ELIM ("\\by{$\\forall$ Elim}"),
    EXST_INTRO ("\\by{$\\exists$ Intro}"),
    EXST_ELIM ("\\by{$\\exists$ Elim}"),
    EQLT_INTRO("\\by{$=$ Intro}"),
    EQLT_ELIM("\\by{$=$ Elim}");

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
        hashMap.put("\u2200 Intro", UNVR_INTRO);
        hashMap.put("\u2200 Elim", UNVR_ELIM);
        hashMap.put("\u2203 Intro", EXST_INTRO);
        hashMap.put("\u2203 Elim", EXST_ELIM);
        hashMap.put("= Intro", EQLT_INTRO);
        hashMap.put("= Elim", EQLT_ELIM);
    }


    public static InferenceRule parseInferenceRule(String s) {
        return hashMap.get(s);
    }
}
