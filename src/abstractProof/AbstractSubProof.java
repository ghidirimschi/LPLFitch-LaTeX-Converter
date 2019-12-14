package abstractProof;

import formulanew.Sentence;

import java.util.ArrayList;

public final class AbstractSubProof implements AbstractStep {
    private final Sentence premise;
    private final ArrayList<AbstractStep> steps;

    public AbstractSubProof(Sentence premise, ArrayList<AbstractStep> steps) {
        this.premise = premise;
        this.steps = new ArrayList<>(steps);
    }
}
