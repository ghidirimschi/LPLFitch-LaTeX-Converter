import formula.Formula;
import parser.FormulaParser;
import parser.FormulaParsingException;
import parser.Parser;
import proof.Proof;
import tokenizer.FormulaTokenizer;
import view.Menu;

import java.io.IOException;

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
            Formula formula = FormulaParser.parse("∀x (Cube(x) → (Large(x) ∧ LeftOf(c,x)))");
            System.out.println(formula.toString());
        } catch (FormulaParsingException e) {
            e.printStackTrace();
        }
    }
}
