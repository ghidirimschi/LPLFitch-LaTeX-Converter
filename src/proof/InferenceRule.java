package proof;

import java.util.HashMap;

public enum InferenceRule {
    UNDEFINED,
    REIT,
    CONJ_INTRO,
    CONJ_ELIM,
    DISJ_INTRO,
    DISJ_ELIM,
    IMPL_INTRO,
    IMPL_ELIM,
    EQIV_INTRO,
    EQIV_ELIM,
    NEGT_INTRO,
    NEGT_ELIM,
    CNTR_INTRO,
    CNTR_ELIM;

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
