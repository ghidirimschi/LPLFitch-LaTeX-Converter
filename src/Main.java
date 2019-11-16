import formula.Formula;
import parser.FormulaParser;
import parser.FormulaParsingException;
import parser.Parser;
import proof.InvalidRuleApplicationException;
import proof.Proof;
import tokenizer.FormulaTokenizer;
import view.Menu;

import java.io.IOException;
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
            Formula formula1 = FormulaParser.parse("A ∧ B");
            Formula a = FormulaParser.parse("A");
            Formula b = FormulaParser.parse("C");
            ArrayList<Formula> test = new ArrayList<>(2);
            test.add(a);
            test.add(b);
            System.out.println(CONJ_INTRO.isValidApplicationIn(formula1, test, false));
            System.out.println(formula1.toString());
        } catch (FormulaParsingException | InvalidRuleApplicationException e) {
            e.printStackTrace();
        }
    }
}
