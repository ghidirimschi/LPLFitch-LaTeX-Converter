package proof;

import abstractProof.AbstractRule;
import formula.*;

import java.util.ArrayList;
import java.util.HashMap;

public enum InferenceRule {
    UNDEFINED("unspecified rule", "", AbstractRule.AUNDEFINED),
    REIT("Reit", "\\by{reit}", AbstractRule.AREIT),
    CONJ_INTRO("\u2227 Intro", "\\by{$\\land$ Intro}", AbstractRule.ACONJ_INTRO),
    CONJ_ELIM("\u2227 Elim", "\\by{$\\land$ Elim}", AbstractRule.ACONJ_ELIM),
    DISJ_INTRO("\u2228 Intro", "\\by{$\\lor$ Intro}", AbstractRule.ADISJ_INTRO),
    DISJ_ELIM("\u2228 Elim", "\\by{$\\lor$ Elim}", AbstractRule.ADISJ_ELIM) ,
    IMPL_INTRO("\u2192 Intro", "\\by{$\\rightarrow$ Intro}", AbstractRule.AIMPL_INTRO) ,
    IMPL_ELIM("\u2192 Elim", "\\by{$\\rightarrow$ Elim}", AbstractRule.AIMPL_ELIM),
    EQIV_INTRO("\u2194 Intro", "\\by{$\\leftrightarrow$ Intro}", AbstractRule.AEQIV_INTRO),
    EQIV_ELIM("\u2194 Elim", "\\by{$\\leftrightarrow$ Elim}", AbstractRule.AEQIV_ELIM),
    NEGT_INTRO("\u00AC Intro", "\\by{$\\neg$ Intro}", AbstractRule.ANEGT_INTRO),
    NEGT_ELIM("\u00AC Elim", "\\by{$\\neg$ Elim}", AbstractRule.ANEGT_ELIM),
    CNTR_INTRO("\u22A5 Intro", "\\by{$\\bot$ Intro}", AbstractRule.ACNTR_INTRO) ,
    CNTR_ELIM("\u22A5 Elim", "\\by{$\\bot$ Elim}", AbstractRule.ACNTR_ELIM) ,
    UNVR_INTRO("\u2200 Intro", "\\by{$\\forall$ Intro}", AbstractRule.AUNVR_INTRO),
    UNVR_ELIM("\u2200 Elim", "\\by{$\\forall$ Elim}", AbstractRule.AUNVR_ELIM),
    EXST_INTRO("\u2203 Intro", "\\by{$\\exists$ Intro}", AbstractRule.AEXST_INTRO),
    EXST_ELIM("\u2203 Elim", "\\by{$\\exists$ Elim}", AbstractRule.AEXST_ELIM),
    EQLT_INTRO("= Intro", "\\by{$=$ Intro}", AbstractRule.AEQLT_INTRO) ,
    EQLT_ELIM("= Elim", "\\by{$=$ Elim}", AbstractRule.AEQLT_ELIM);

    private final String latexCode;
    public final String ruleName;
    private final AbstractRule abstractRule;

    InferenceRule(String ruleName, String latexCode, AbstractRule abstractRule) {
        this.ruleName = ruleName;
        this.latexCode = latexCode;
        this.abstractRule = abstractRule;

    }


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

    public AbstractRule toAbstract() {
        return abstractRule;
    }

}
