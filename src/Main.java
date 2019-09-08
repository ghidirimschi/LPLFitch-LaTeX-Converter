import parser.Parser;
import proof.Proof;
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
        new Menu();
    }
}
