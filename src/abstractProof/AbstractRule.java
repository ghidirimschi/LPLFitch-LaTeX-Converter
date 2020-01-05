package abstractProof;

import formulanew.Conjunction;
import formulanew.Disjunction;
import formulanew.Implication;
import formulanew.Sentence;

import java.util.ArrayList;

public enum AbstractRule {
    AUNDEFINED {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return false;
        }
    }, AREIT {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 || !stepRanges.get(0).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Reiteration rule should cite only 1 step!");
            }
            int citedStepRowNr = stepRanges.get(0).getMinimum();
            Sentence sentence = AbstractRule.getSentenceAtRow(rowNr, citedStepRowNr, runningSteps);
            return sentence.equals(inference.getSentence());
        }
    }, ACONJ_INTRO {
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
    }, ACONJ_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 || !stepRanges.get(0).isSingleStep()) {
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
    }, ADISJ_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 || !stepRanges.get(0).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Disjunction introduction rule should cite only 1 step!");
            }
            Sentence sentence = inference.getSentence();
            Sentence citedSentence = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps);
            if (!(sentence instanceof Disjunction)) {
                return false;
            }
            ArrayList<Sentence> disjuncts = ((Disjunction) sentence).getNestedDisjuncts();
            if (citedSentence instanceof Disjunction) {
                ArrayList<Sentence> citedDisjuncts = ((Disjunction) citedSentence).getNestedDisjuncts();
                return disjuncts.containsAll(citedDisjuncts) && !citedDisjuncts.containsAll(disjuncts);
            }
            return disjuncts.contains(citedSentence);

        }
    }, ADISJ_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() < 2) {
                throw new AbstractRuleCitingException(rowNr, "Disjunction elimination rule should cite at least 2 steps!");
            }
            int disjunctionStepNr = 0;
            for (StepRange stepRange : stepRanges) {
                if (!stepRange.isSingleStep()) {
                    continue;
                }
                if (disjunctionStepNr != 0) {
                    disjunctionStepNr = 0;
                    break;
                }
                disjunctionStepNr = stepRange.getMinimum();
            }
            if (disjunctionStepNr == 0) {
                throw new AbstractRuleCitingException(rowNr, "Disjunction elimination rule should cite exactly one single step!");
            }
            Sentence sentence = inference.getSentence();
            Sentence citedSingleStep = AbstractRule.getSentenceAtRow(rowNr, disjunctionStepNr, runningSteps);
            ArrayList<Sentence> citedSingleStepDisjuncts = new ArrayList<>();
            if (citedSingleStep instanceof Disjunction) {
                citedSingleStepDisjuncts.addAll(((Disjunction) citedSingleStep).getNestedDisjuncts());
            } else {
                citedSingleStepDisjuncts.add(citedSingleStep);
            }
            ArrayList<Sentence> citedSubProofsPremiseDisjuncts = new ArrayList<>();
            AbstractSubProof currentSubProof;
            Sentence premiseSentence;
            for (StepRange stepRange : stepRanges) {
                if (stepRange.isSingleStep()) {
                    continue;
                }
                currentSubProof = AbstractRule.getSubProofAtRows(rowNr, stepRange.getMinimum(), stepRange.getMaximum(), runningSteps);
                if (!currentSubProof.isDeryiving(sentence)) {
                    return false;
                }
                premiseSentence = currentSubProof.getPremise().getSentence();
                if (premiseSentence instanceof Disjunction) {
                    citedSubProofsPremiseDisjuncts.addAll(((Disjunction) premiseSentence).getNestedDisjuncts());
                } else {
                    citedSubProofsPremiseDisjuncts.add(premiseSentence);
                }
            }
            return citedSingleStepDisjuncts.containsAll(citedSubProofsPremiseDisjuncts);
        }
    }, AIMPL_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            StepRange subProofRowRange;
            if (stepRanges.size() != 1 || (subProofRowRange = stepRanges.get(0)).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Implication introduction rule should cite exactly one subproof!");
            }
            Sentence sentence = inference.getSentence();
            if (!(sentence instanceof Implication)) {
                return false;
            }
            Implication implication = (Implication) sentence;
            AbstractSubProof citedSubProof = AbstractRule.getSubProofAtRows(rowNr, subProofRowRange.getMinimum(), subProofRowRange.getMaximum(), runningSteps);
            return implication.getAntecedent().equals(citedSubProof.getPremise().getSentence()) && citedSubProof.isDeryiving(implication.getConsequent());
        }
    }, AIMPL_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 2 || !stepRanges.get(0).isSingleStep() || !stepRanges.get(1).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Implication introduction rule should cite exactly two single steps!");
            }
            Sentence sentence = inference.getSentence(), citedSentenceOne = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps),
                     citedSentenceTwo = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(1).getMinimum(), runningSteps);
            Implication tmpImpl;
            return ((citedSentenceOne instanceof Implication) && (tmpImpl = (Implication)citedSentenceOne).getAntecedent().equals(citedSentenceTwo)
                    && tmpImpl.getConsequent().equals(sentence)) || ((citedSentenceTwo instanceof Implication) &&
                    (tmpImpl = (Implication)citedSentenceTwo).getAntecedent().equals(citedSentenceOne) && tmpImpl.getConsequent().equals(sentence));
        }
    }, AEQIV_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, AEQIV_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, ANEGT_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, ANEGT_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, ACNTR_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, ACNTR_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, AUNVR_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, AUNVR_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, AEXST_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, AEXST_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, AEQLT_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            return true;
        }
    }, AEQLT_ELIM {
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
            throw new AbstractRuleCitingException(fromRow, "Cited step is not accessible from the current inference!");
        }
        return rtn;
    }

    private static AbstractSubProof getSubProofAtRows(int fromRow, int rowNrBegin, int rowNrEnd, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
        int run = 0, rowNr = rowNrBegin;
        AbstractStep rtn = null;
        try {
            while ((rowNr -= runningSteps.get(run).rowSize()) > 1) {
                ++run;
            }
            rowNr -= 1;
            rtn = runningSteps.get(++run);
        } catch (IndexOutOfBoundsException e) {
            rowNr = -1;
        }
        if (rowNr < 0 || !(rtn instanceof AbstractSubProof) || (rtn.rowSize() != rowNrEnd - rowNrBegin + 1)) {
            throw new AbstractRuleCitingException(fromRow, "No accessible subproof found on rows " + rowNrBegin + "-" + rowNrEnd + "!");
        }
        return (AbstractSubProof) rtn;
    }
}
