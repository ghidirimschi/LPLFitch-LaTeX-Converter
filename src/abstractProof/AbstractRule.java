package abstractProof;

import formulanew.Conjunction;
import formulanew.Disjunction;
import formulanew.Sentence;

import java.util.ArrayList;

public enum AbstractRule {
    AUNDEFINED {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return false;
        }
    },
    AREIT {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 && !stepRanges.get(0).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Reiteration rule should cite only 1 step!");
            }
            int citedStepRowNr = stepRanges.get(0).getMinimum();
            Sentence sentence = AbstractRule.getSentenceAtRow(rowNr, citedStepRowNr, runningSteps);
            return sentence.equals(inference.getSentence());
        }
    },
    ACONJ_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() < 1) {
                throw new AbstractRuleCitingException(rowNr, "Conjunction introduction rule should cite at least 1 step!");
            }
            for (StepRange stepRange : stepRanges) {
                if (!stepRange.isSingleStep()) {
                    throw new AbstractRuleCitingException(rowNr, "Conjunction introduction rule should cite only single steps!");
                }
            }
            Sentence sentence = inference.getSentence();
            if (!(sentence instanceof Conjunction)) {
                return false;
            }
            ArrayList<Sentence> conjuncts = ((Conjunction) sentence).getNestedConjuncts();
            Sentence tmp;
            for (StepRange stepRange : stepRanges) {
                tmp = AbstractRule.getSentenceAtRow(rowNr, stepRange.getMinimum(), runningSteps);
                if (tmp instanceof Conjunction) {
                    if (!conjuncts.containsAll(((Conjunction) tmp).getNestedConjuncts())) {
                        return false;
                    }
                } else {
                    if (!conjuncts.contains(tmp)) {
                        return false;
                    }
                }
            }
            return true;
        }
    },
    ACONJ_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 && !stepRanges.get(0).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Conjunction elimination rule should cite only 1 single step!");
            }
            Sentence citedStep = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps);
            if (!(citedStep instanceof Conjunction)) {
                throw new AbstractRuleCitingException(rowNr, "Cited step must be a conjunction!");
            }
            ArrayList<Sentence> conjuncts = ((Conjunction) citedStep).getNestedConjuncts();
            Sentence sentence = inference.getSentence();
            if (sentence instanceof Conjunction) {
                return conjuncts.containsAll(((Conjunction) sentence).getNestedConjuncts());
            }
            return conjuncts.contains(sentence);
        }
    },
    ADISJ_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 && !stepRanges.get(0).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Disjunction introduction rule should cite only 1 step!");
            }
            Sentence sentence = inference.getSentence();
            if (!(sentence instanceof Disjunction)) {
                return false;
            }
            return ((Disjunction) sentence).getNestedDisjuncts().contains(sentence);

        }
    },
    ADISJ_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AIMPL_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AIMPL_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AEQIV_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AEQIV_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    ANEGT_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    ANEGT_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    ACNTR_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    ACNTR_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AUNVR_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AUNVR_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AEXST_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AEXST_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AEQLT_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    AEQLT_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    };

    public abstract boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException;


    private static Sentence getSentenceAtRow(int fromRow, int rowNr, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
        int run = 0;
        try {
            while ((rowNr -= runningSteps.get(run).rowSize()) > 0) {
                ++run;
            }
        } catch (IndexOutOfBoundsException e) {
            rowNr = -1;
        }
        Sentence rtn = runningSteps.get(run).getSentence();
        if (rowNr < 0 || rtn == null) {
            System.err.println(runningSteps.get(run));
            throw new AbstractRuleCitingException(fromRow, "Cited step is not accessible from the current inference!");
        }
        return rtn;
    }
}
