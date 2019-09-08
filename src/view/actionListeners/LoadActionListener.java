package view.actionListeners;

import view.Menu;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class LoadActionListener implements ActionListener {
    private Menu menu;
    private static final FileFilter filter = new FileNameExtensionFilter("HTML proof file", "html");
    private File lastDirectory = null;

    public LoadActionListener(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fileChooser = new JFileChooser(lastDirectory);
        fileChooser.setFileFilter(filter);
        int rtn = fileChooser.showOpenDialog(menu.getFrame());
        if(rtn == JFileChooser.APPROVE_OPTION) {
            menu.updateParse(fileChooser.getSelectedFile());
            lastDirectory = fileChooser.getCurrentDirectory();
        }
    }
}
