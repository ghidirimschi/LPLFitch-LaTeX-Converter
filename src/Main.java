import formulanew.*;
import parser.FormulaParser;
import parser.FormulaParsingException;
import proof.InvalidRuleApplicationException;

import java.util.ArrayList;

import static proof.InferenceRule.CONJ_INTRO;

public class Main {

    public static void main(String[] args) {
//        if(args.length != 1) {
//            System.out.println("Please indicate the html file name.");
//            return;
//        }
//        Proof proof = null;
//        try {
//             proof = Parser.parse(args[0]);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        proof.exportLatex();
//      FROM HERE WAS CORRECT:
//      new Menu();
        try {
            Sentence sentence = FormulaParser.parse("¬¬A ∧ ¬(B ∨ D) ∧ (¬¬¬D ∨ K ∨ ¬∀x P(x))");
            System.out.println(sentence);
        } catch (FormulaParsingException e) {
            e.printStackTrace();
        }
    }
}
