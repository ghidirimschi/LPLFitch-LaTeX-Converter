package view.actionListeners;

import view.Menu;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CopyToCbActionListener implements ActionListener {
    private Menu menu;

    public CopyToCbActionListener(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(menu.getLaTexOutput().isEmpty()) {
            menu.updateStatus("<font color = 'orange'> Nothing to copy yet! </font>");
            return;
        }
        StringSelection stringSelection = new StringSelection(menu.getLaTexOutput());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        menu.updateStatus("<font color = 'green'> Successfully copied to clipboard! </font>");
    }
}
