package controller;

import abstractProof.AbstractProof;
import abstractProof.AbstractRuleCitingException;
import abstractProof.AbstractRulePedanticException;
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
            if (!view.isCheckWfCheckBoxSelected()) {
                view.updateStatus("");
                continue;
            }
            AbstractProof abstractProof;
            try {
                abstractProof = proof.toAbstract();
            } catch (ConverterException e) {
                view.updateStatus("<font color = 'red'> Well-formedness error:</font> " + e.getMessage(), 2);
                continue;
            }
            view.updateStatus("<font color = 'green'>All inferences in " + file.getName() + " are well-formed!</font>");
            if (!view.isValidityCheckBoxSelected()) {
                view.updateStatus("");
                continue;
            }
            try {
                if (abstractProof.isValid()) {
                    view.updateStatus("<font color = 'green'>" + file.getName() + " is valid. </font>");
                } else {
                    view.updateStatus("<font color = 'red'>" + file.getName() + " is invalid. </font>", 2);
                    continue;
                }
            } catch (AbstractRuleCitingException e) {
                view.updateStatus("<font color = 'red'> Citing error:</font> " + e.getMessage(), 2);
                continue;
            }
            if (!view.isPedanticCheckBoxSelected()) {
                view.updateStatus("");
                continue;
            }
            try {
                abstractProof.checkPedanticValidity();
            } catch (AbstractRuleCitingException | AbstractRulePedanticException e) {
                view.updateStatus("<font color = 'maroon'> Pedantic error:" + e.getMessage() + "</font>");
            }
            view.updateStatus("");
        }
        view.setOutputAreaText(texText.toString());
        view.updateStatus("Successfully converted " + parsedNr + "/" + files.length + " files!<hr>", 0);


    }
}
