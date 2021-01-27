package abstractProof;

import formula.*;

import java.util.ArrayList;


/**
 * This enum class implements the abstract representation of a Fitch rule and enumerates all the possible rules.
 * All listed rules must implement the isValidApplicationIn method (see description below) and optionally the
 * isPedanticApplicationIn method.
 */
public enum AbstractRule {
    AUNDEFINED {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) {
            return false;
        }
    }, AREIT {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 || !stepRanges.get(0).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Reiteration rule should cite only one support step!");
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
                throw new AbstractRuleCitingException(rowNr, "Conjunction introduction rule should cite at least one support step!");
            }
            for (StepRange stepRange : stepRanges) {
                if (!stepRange.isSingleStep()) {
                    throw new AbstractRuleCitingException(rowNr, "Conjunction introduction rule should cite only cite support steps!");
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
                throw new AbstractRuleCitingException(rowNr, "Conjunction elimination rule should cite only one support step!");
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
                throw new AbstractRuleCitingException(rowNr, "Disjunction introduction rule should cite only one support step!");
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
                throw new AbstractRuleCitingException(rowNr, "Disjunction elimination rule should cite exactly one support step!");
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
                throw new AbstractRuleCitingException(rowNr, "Implication elimination rule should cite exactly two support steps!");
            }
            Sentence sentence = inference.getSentence(), citedSentenceOne = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps),
                     citedSentenceTwo = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(1).getMinimum(), runningSteps);
            Implication tmpImpl;
            return ((citedSentenceOne instanceof Implication) && (tmpImpl = (Implication)citedSentenceOne).getAntecedent().equals(citedSentenceTwo)
                    && tmpImpl.getConsequent().equals(sentence)) || ((citedSentenceTwo instanceof Implication) &&
                    (tmpImpl = (Implication)citedSentenceTwo).getAntecedent().equals(citedSentenceOne) && tmpImpl.getConsequent().equals(sentence));
        }

        @Override
        public void isPedanticApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRulePedanticException, AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            Sentence sentence = inference.getSentence(), citedSentenceOne = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps),
                    citedSentenceTwo = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(1).getMinimum(), runningSteps);
            Implication tmpImpl;
            if (!((citedSentenceOne instanceof Implication) && (tmpImpl = (Implication)citedSentenceOne).getAntecedent().equals(citedSentenceTwo)
                    && tmpImpl.getConsequent().equals(sentence))) {
                throw new AbstractRulePedanticException(rowNr, "The first cited step must be the antecedent of the second step, which must be an implication!");
            }
        }


    }, AEQIV_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 2 || stepRanges.get(0).isSingleStep() || stepRanges.get(1).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Equivalence introduction rule should cite exactly two subproofs!");
            }
            Sentence sentence = inference.getSentence();
            if (!(sentence instanceof BiImplication)) {
                return false;
            }
            BiImplication biImplication = (BiImplication) sentence;
            AbstractSubProof subProofOne = AbstractRule.getSubProofAtRows(rowNr, stepRanges.get(0).getMinimum(), stepRanges.get(0).getMaximum(), runningSteps),
                             subProofTwo = AbstractRule.getSubProofAtRows(rowNr, stepRanges.get(1).getMinimum(), stepRanges.get(1).getMaximum(), runningSteps);
            Sentence antecedent = biImplication.getAntecedent(), consequent = biImplication.getConsequent(),
                    subProofOnePremise = subProofOne.getPremise().getSentence(), subProofTwoPremise= subProofTwo.getPremise().getSentence();
            return (antecedent.equals(subProofOnePremise) && subProofOne.isDeryiving(consequent) || antecedent.equals(subProofTwoPremise) && subProofTwo.isDeryiving(consequent)) &&
                   (consequent.equals(subProofOnePremise) && subProofOne.isDeryiving(antecedent) || consequent.equals(subProofTwoPremise) && subProofTwo.isDeryiving(antecedent));
        }
    }, AEQIV_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 2 || !stepRanges.get(0).isSingleStep() || !stepRanges.get(1).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Equivalence elimination rule should cite exactly two support steps!");
            }
            Sentence sentence = inference.getSentence(), citedSentenceOne = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps),
                    citedSentenceTwo = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(1).getMinimum(), runningSteps);
            BiImplication tmpBiImpl;
            return ((citedSentenceOne instanceof BiImplication) && ((tmpBiImpl = (BiImplication)citedSentenceOne).getAntecedent().equals(citedSentenceTwo)
                    && tmpBiImpl.getConsequent().equals(sentence) || tmpBiImpl.getConsequent().equals(citedSentenceTwo) &&  tmpBiImpl.getAntecedent().equals(sentence))) ||
                    ((citedSentenceTwo instanceof BiImplication) && ((tmpBiImpl = (BiImplication) citedSentenceTwo).getAntecedent().equals(citedSentenceOne) && tmpBiImpl.getConsequent().equals(sentence)
                    || tmpBiImpl.getConsequent().equals(citedSentenceOne) && tmpBiImpl.getAntecedent().equals(sentence)));
        }
    }, ANEGT_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 || stepRanges.get(0).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Negation introduction rule should cite exactly one subproof!");
            }
            Sentence sentence = inference.getSentence();
            if (!(sentence instanceof Negation)) {
                return false;
            }
            Negation negation = (Negation) sentence;
            AbstractSubProof citedSubProof = AbstractRule.getSubProofAtRows(rowNr, stepRanges.get(0).getMinimum(), stepRanges.get(0).getMaximum(), runningSteps);
            return citedSubProof.getPremise().getSentence().equals(negation.getSentence()) && citedSubProof.isDeryiving(Contradiction.getInstance());
        }
    }, ANEGT_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 || !stepRanges.get(0).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Negation elimination rule should cite exactly one support step!");
            }
            Sentence citedSentence = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps);
            if (!(citedSentence instanceof Negation)) {
                return false;
            }
            Sentence innerSentence = ((Negation) citedSentence).getSentence();
            if (!(innerSentence instanceof Negation)) {
                return false;
            }
            return inference.getSentence().equals(((Negation) innerSentence).getSentence());

        }
    }, ACNTR_INTRO {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 2 || !stepRanges.get(0).isSingleStep() || !stepRanges.get(1).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Contradiction introduction rule should cite exactly two support steps!");
            }
            Sentence sentence = inference.getSentence();
            if (!(sentence instanceof Contradiction)) {
                return false;
            }
            Sentence citedSentenceOne = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps),
                     citedSentenceTwo = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(1).getMinimum(), runningSteps);
            return isSentenceNegationOf(citedSentenceOne, citedSentenceTwo) || isSentenceNegationOf(citedSentenceTwo, citedSentenceOne);
        }

        @Override
        public void isPedanticApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRulePedanticException, AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            Sentence citedSentenceOne = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps),
                    citedSentenceTwo = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(1).getMinimum(), runningSteps);

            if (!isSentenceNegationOf(citedSentenceOne, citedSentenceTwo)) {
                throw new AbstractRulePedanticException(rowNr, "The second cited step must be the negation of the first cited step. The reverse is not pedantically valid!");
            }

        }

        private boolean isSentenceNegationOf(Sentence sentence, Sentence negated) {
            return (negated instanceof Negation) && ((Negation) negated).getSentence().equals(sentence);
        }
    }, ACNTR_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 1 || !stepRanges.get(0).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Negation elimination rule should cite exactly one support step!");
            }
            return AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps).equals(Contradiction.getInstance());
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
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (!stepRanges.isEmpty()) {
                throw new AbstractRuleCitingException(rowNr, "Equality introduction rule should cite no steps!");
            }
            Sentence sentence = inference.getSentence();
            if (!(sentence instanceof Equality)) {
                return false;
            }
            Equality equality = (Equality) sentence;
            return equality.getFirstOperand().equals(equality.getSecondOperand());
        }
    }, AEQLT_ELIM {
        @Override
        public boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            if (stepRanges.size() != 2 || !stepRanges.get(0).isSingleStep() || !stepRanges.get(1).isSingleStep()) {
                throw new AbstractRuleCitingException(rowNr, "Equivalence elimination rule should cite exactly two support steps!");
            }
            Sentence sentence = inference.getSentence(), citedSentenceOne = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps),
                    citedSentenceTwo = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(1).getMinimum(), runningSteps);

            return isSentenceValidReplacementOf(sentence, citedSentenceOne, citedSentenceTwo) || isSentenceValidReplacementOf(sentence, citedSentenceTwo, citedSentenceOne);
        }

        @Override
        public void isPedanticApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRulePedanticException, AbstractRuleCitingException {
            ArrayList<StepRange> stepRanges = inference.getCitedSteps();
            Sentence sentence = inference.getSentence(), citedSentenceOne = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(0).getMinimum(), runningSteps),
                    citedSentenceTwo = AbstractRule.getSentenceAtRow(rowNr, stepRanges.get(1).getMinimum(), runningSteps);
            if (! (citedSentenceTwo instanceof Equality)) {
                throw new AbstractRulePedanticException(rowNr, "The second cited step must be the replacing equality!");
            }
            Equality equality = (Equality) citedSentenceTwo;

            if (! citedSentenceOne.isEqualWithReplacement(sentence, equality.getFirstOperand(), equality.getSecondOperand())) {
                throw new AbstractRulePedanticException(rowNr, "Only the left hand side operand of the equality can be replaced by the right hand side one. The converse is not allowed.");
            }

        }

        private boolean isSentenceValidReplacementOf(Sentence sentence, Sentence ofSentence, Sentence equalitySentence) {
            if (!(equalitySentence instanceof Equality)) {
                return false;
            }
            Equality equality = (Equality) equalitySentence;
            return sentence.isEqualWithReplacement(ofSentence, equality.getFirstOperand(), equality.getSecondOperand()) ||
                   sentence.isEqualWithReplacement(ofSentence, equality.getSecondOperand(), equality.getFirstOperand());
        }
    };

    /**
     * Verifies if a given inference is valid based on a list of running steps. It is an abstract method
     * and all rules must implement it.
     * @param rowNr         the number of the row on which the inference is located
     * @param inference     the inference, represented as an abstract inference
     * @param runningSteps  the list of running steps
     * @return true if valid, false otherwise
     * @throws AbstractRuleCitingException if the format of the cited steps does not correspond to the required format of the rule
     * (e.g. wrong number of steps cited, an inference cited instead of a subproof)
     */
    public abstract boolean isValidApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRuleCitingException;

    /**
     * Verifies if a given inference is pedantically valid based on a list of running steps. This is an optional
     * method which allows rules to implement other validity constraints.
     * @param rowNr         the number of the row on which the inference is located
     * @param inference     the inference, represented as an abstract inference
     * @param runningSteps  the list of running steps
     * @throws AbstractRulePedanticException if the rule violates the pedantic constraints
     */
    public void isPedanticApplicationIn(int rowNr, AbstractInference inference, ArrayList<AbstractStep> runningSteps) throws AbstractRulePedanticException, AbstractRuleCitingException {
    }

    /**
     * Returns the corresponding sentence at a given global row from the specified list of running steps.
     * @param fromRow       the row number of the inference from which the sentence is tried to be accessed.
     * @param rowNr         the row number of the inference which is tried to be accessed
     * @param runningSteps  the list of running steps
     * @return              the sentence
     * @throws AbstractRuleCitingException if the rule can not be accessed from the source row number.
     */
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

    /**
     * Same as above, but for subproofs.
     */
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
