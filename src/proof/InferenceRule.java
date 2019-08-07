package proof;

import java.util.HashMap;

public enum InferenceRule {
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


    }

    public static InferenceRule parseInferenceRule(String s) {
        return hashMap.get(s);
    }
}
