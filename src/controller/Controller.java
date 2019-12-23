package controller;

import parser.Parser;
import proof.ConverterException;
import proof.Proof;
import view.Menu;

import java.io.File;
import java.io.IOException;

public final class Controller {
    private final Menu view;

    public Controller(Menu view) {
        this.view = view;
        view.setController(this);
    }

    public void updateParse(File[] files) {
        StringBuilder texText = new StringBuilder();
        int parsedNr = 0;
        boolean checkWf = view.isCheckWfCheckBoxSelected();
        for(File file : files) {
            view.updateStatus("Parsing " + file.getName() + "...");
            Proof proof;
            try {
                proof = Parser.parse(file);
            } catch (IOException e) {
                view.updateStatus("Error parsing " + file.getName() +"! <font color = 'red'>" + e.getMessage() + "</font>", 2);
                continue;
            }
            texText.append("\\subsection*{").append(file.getName().replaceFirst("[.][^.]+$", "")).append("}");
            texText.append(proof.exportLatex());
            view.updateStatus("<font color = 'green'> Successfully converted " + file.getName() + ". </font>");
            ++parsedNr;
            if (!checkWf) {
                view.updateStatus("");
                continue;
            }
            try {
                proof.toAbstract();
            } catch (ConverterException e) {
                view.updateStatus("<font color = 'red'> Well-formedness error:</font> " + e.getMessage(), 2);
                continue;
            }
            view.updateStatus("<font color = 'green'>All inferences in " + file.getName() + "are well-formed!</font>", 2);

        }
        view.setOutputAreaText(texText.toString());
        view.updateStatus("Successfully converted " + parsedNr + "/" + files.length + " files!<hr>", 0);
    }
}
