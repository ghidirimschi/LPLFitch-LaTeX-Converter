package abstractProof;

import formulanew.Sentence;

import java.util.ArrayList;

public final class AbstractProof {
    private final ArrayList<Sentence> premises;
    private final ArrayList<AbstractStep> steps;

    public AbstractProof(ArrayList<Sentence> premises, ArrayList<AbstractStep> steps) {
        this.premises = new ArrayList<>(premises);
        this.steps = new ArrayList<>(steps);
    }
}
