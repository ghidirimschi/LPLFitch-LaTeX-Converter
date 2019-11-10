package view;

import parser.Parser;
import view.actionListeners.CopyToCbActionListener;
import view.actionListeners.LoadActionListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu {
    private JFrame frame;
    private JPanel ButtonsPanel;
    private JButton loadHTMLbutton;
    private JButton copyToCBbutton;
    private JTextArea outputArea;
    private JLabel statusField;


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



        statusField = new JLabel("Status: Waiting for input...");
        statusField.setAlignmentX(Component.LEFT_ALIGNMENT);
        subPanel.add(statusField);

        frame.add(subPanel);


        frame.pack();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void updateParse(File[] files) {
        StringBuilder texText = new StringBuilder();
        for(File file : files) {
            try {
                texText.append("\\subsection*{").append(file.getName().replaceFirst("[.][^.]+$", "")).append("}");
                texText.append(Parser.parse(file).exportLatex());
            } catch (IOException e) {
                setStatus("Error parsing " + file.getName() +"! <font color = 'red'>" + e.getMessage() + "</font>");
                return;
            }
            setStatus("<font color = 'green'> Successfully converted " + files.length + " files! </font>");
        }
        outputArea.setText(texText.toString());
        setStatus("<font color = 'green'> Successfully converted! </font>");
    }

    public void setStatus(String string) {
        statusField.setText("<html>Status: " + string + "</html>");
    }

    public String getLaTexOutput() {
        return outputArea.getText();
    }
}
