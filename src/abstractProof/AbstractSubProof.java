package abstractProof;

import formulanew.Sentence;

import java.util.ArrayList;

public final class AbstractSubProof implements AbstractStep {
    private final Sentence premise;
    private final ArrayList<AbstractInference> inferences;

    public AbstractSubProof(Sentence premise, ArrayList<AbstractInference> inferences) {
        this.premise = premise;
        this.inferences = new ArrayList<>(inferences);
    }
}
