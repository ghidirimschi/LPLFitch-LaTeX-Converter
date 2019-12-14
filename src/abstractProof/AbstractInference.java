package abstractProof;

import formulanew.Sentence;

import java.util.ArrayList;

public final class AbstractInference implements AbstractStep {
    private final Sentence sentence;
    private final AbstractRule rule;
    private final ArrayList<Integer> citedSteps;

    public AbstractInference(Sentence sentence, AbstractRule rule, ArrayList<Integer> citedSteps) {
        this.sentence = sentence;
        this.rule = rule;
        this.citedSteps = new ArrayList<>(citedSteps);
    }
}
