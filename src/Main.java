import parser.Parser;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Please indicate the html file name.");
            return;
        }
        try {
            Parser.parse(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
