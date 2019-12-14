package abstractProof;

import formulanew.Sentence;

public final class AbstractInference implements AbstractStep {
    private final Sentence sentence;
    private final AbstractRule rule;

    public AbstractInference(Sentence sentence, AbstractRule rule) {
        this.sentence = sentence;
        this.rule = rule;
    }
}
