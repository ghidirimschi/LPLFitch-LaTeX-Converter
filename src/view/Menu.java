package view;

import controller.Controller;
import org.apache.commons.lang3.StringUtils;
import view.actionListeners.CopyToCbActionListener;
import view.actionListeners.LoadActionListener;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.io.IOException;

/**
 * This class implements the view part of our application, according to the MVC pattern.
 */
public class Menu {
    private JFrame frame;
    private JPanel ButtonsPanel;
    private JButton loadHTMLbutton;
    private JButton copyToCBbutton;
    private JTextArea outputArea;
    private JTextPane statusArea;
    private JCheckBox checkWfCheckBox;
    private JCheckBox checkValidityCheckBox;
    private JCheckBox pedanticCheckBox;
    private Controller controller;

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Menu() {
        frame = new JFrame("LPL Fitch LaTex Converter");
        frame.setVisible(true);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ButtonsPanel = new JPanel();
        ButtonsPanel.setLayout(new BoxLayout(ButtonsPanel, BoxLayout.Y_AXIS));


        loadHTMLbutton = new JButton(" Load HTML proof");
        loadHTMLbutton.addActionListener(new LoadActionListener(this));
        ButtonsPanel.add(loadHTMLbutton);

        ButtonsPanel.add(Box.createRigidArea(new Dimension(0, screenSize.width/100)));

        copyToCBbutton = new JButton("Copy to clipboard");
        copyToCBbutton.addActionListener(new CopyToCbActionListener(this));
        ButtonsPanel.add(copyToCBbutton);

        checkWfCheckBox = new JCheckBox("Check well-formedness");
        checkValidityCheckBox = new JCheckBox("Check validity");
        pedanticCheckBox = new JCheckBox("Pedantic");

        checkValidityCheckBox.setEnabled(false);
        pedanticCheckBox.setEnabled(false);

        checkWfCheckBox.addActionListener(e -> {
            if (!isCheckWfCheckBoxSelected()) {
                checkValidityCheckBox.setSelected(false);
                pedanticCheckBox.setSelected(false);
            }
            checkValidityCheckBox.setEnabled(isCheckWfCheckBoxSelected());
            pedanticCheckBox.setEnabled(isValidityCheckBoxSelected());

        });

        checkValidityCheckBox.addActionListener(e -> {
            if (!isValidityCheckBoxSelected()) {
                pedanticCheckBox.setSelected(false);
            }
            pedanticCheckBox.setEnabled(isValidityCheckBoxSelected());
        });

        ButtonsPanel.add(Box.createRigidArea(new Dimension(0, screenSize.width/100)));
        ButtonsPanel.add(checkWfCheckBox);

        ButtonsPanel.add(Box.createRigidArea(new Dimension(0, screenSize.width/800)));
        ButtonsPanel.add(checkValidityCheckBox);

        ButtonsPanel.add(Box.createRigidArea(new Dimension(0, screenSize.width/800)));
        ButtonsPanel.add(pedanticCheckBox);

        frame.add(Box.createHorizontalStrut(screenSize.width/100));
        frame.add(ButtonsPanel);
        frame.add(Box.createRigidArea(new Dimension(screenSize.width/100, 0)));

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));


        outputArea = new JTextArea();
        outputArea.setEditable(false);
        Dimension areaDimension = new Dimension(screenSize.width/4, screenSize.height/2);
        JScrollPane scrollBox = new JScrollPane(outputArea);
        scrollBox.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBox.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollBox.setMinimumSize(areaDimension);
        scrollBox.setPreferredSize(areaDimension);
        scrollBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        subPanel.add(scrollBox);


        statusArea = new JTextPane();
        statusArea.setContentType("text/html");
        statusArea.setEditable(false);
        Dimension statusDimension = new Dimension(screenSize.width/4, screenSize.height/4);
        JScrollPane scrollStatusBox = new JScrollPane(statusArea);
        scrollStatusBox.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollStatusBox.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollStatusBox.setMinimumSize(statusDimension);
        scrollStatusBox.setPreferredSize(statusDimension);
        scrollStatusBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        subPanel.add(scrollStatusBox);
        updateStatus("Waiting for input...<hr>", 0);

        frame.add(subPanel);


        frame.pack();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setOutputAreaText(String string) {
        outputArea.setText(string);
    }

    public void updateStatus(String string) {
        updateStatus(string, 1);
    }

    public void updateStatus(String string, int breakLinesNr)
    {
        HTMLDocument doc=(HTMLDocument) statusArea.getStyledDocument();
        try {
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), string + StringUtils.repeat("<br>", breakLinesNr));
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getLaTexOutput() {
        return outputArea.getText();
    }

    public boolean isCheckWfCheckBoxSelected() {
        return checkWfCheckBox.isSelected();
    }

    public boolean isValidityCheckBoxSelected() {
        return checkValidityCheckBox.isSelected();
    }

    public boolean isPedanticCheckBoxSelected() {
        return pedanticCheckBox.isSelected();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }
}
