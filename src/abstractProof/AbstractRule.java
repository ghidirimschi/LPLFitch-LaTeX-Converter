package abstractProof;

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
                throw new AbstractRuleCitingException(rowNr, "Reiteration rule should cite only 1 inference step!");
            }
            int citedStepRowNr = stepRanges.get(0).getMinimum();
            AbstractStep reiteratedStep;
            try {
                reiteratedStep = AbstractRule.getStepAtRow(citedStepRowNr, runningSteps);
            } catch (IndexOutOfBoundsException e) {
                throw new AbstractRuleCitingException(rowNr, "Cited step is not accessible from the current inference!");
            }
            Sentence sentence = reiteratedStep.getSentence();
            if (sentence == null) {
                throw new AbstractRuleCitingException(rowNr, "Cited rule must be a premise or an inference!");
            }
            return sentence.equals(inference.getSentence());
        }
    },
    ACONJ_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    ACONJ_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    },
    ADISJ_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
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

    private static AbstractStep getStepAtRow(int rowNr, ArrayList<AbstractStep> runningSteps) throws IndexOutOfBoundsException {
        int run = 0;
        while ((rowNr -= runningSteps.get(run).rowSize()) > 0) {
            ++run;
        }
        return runningSteps.get(run);
    }
}
