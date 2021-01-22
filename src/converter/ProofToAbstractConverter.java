package converter;

import java.util.ArrayList;

public final class ProofToAbstractConverter {
//    public static AbstractProof convert(Proof proof) throws ConverterException {
//        MutableInt rowNr = new MutableInt(1);
//        ArrayList<Premise> premises = proof.getPremises();
//        ArrayList<Sentence> aPremises = new ArrayList<>(premises.size());
//        for (Premise premise : premises) {
//            try {
//                aPremises.add(FormulaParser.parse(premise.getWff()));
//            } catch (FormulaParsingException e) {
//                throw new ConverterException(rowNr.getValue(), e.getMessage());
//            }
//            rowNr.increment();
//        }
//
//        ArrayList<Step> steps = proof.getSteps();
//        ArrayList<AbstractStep> aSteps = new ArrayList<>(steps.size());
//        for (Step step : steps) {
//            convert(step);
//        }
//    }
//
//    private static AbstractSubProof convert(SubProof subProof) {
//
//        return null;
//    }
//
//    private static AbstractInference convert(Inference inference) {
//        return null;
//    }

}
