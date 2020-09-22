package pl.sytomczak.notepad.gui.gui;


import pl.sytomczak.notepad.gui.NotepadOperationsWithDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenOrSaveDialogWindow extends JFrame {
    private JPanel panel1;
    private JRadioButton saveRadioButton;
    private JRadioButton openRadioButton;

    private NotepadOperationsWithDatabase notepadOperationsWithDatabase;

    public OpenOrSaveDialogWindow() {

        setContentPane(panel1);
        panel1.setPreferredSize(new Dimension(600, 100));
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        openRadioButton.addActionListener(e -> onSelectOpenRadioButton());
        saveRadioButton.addActionListener(e -> onSelectSaveRadioButton());

        notepadOperationsWithDatabase = new NotepadOperationsWithDatabase(null, panel1);
    }

    private void onSelectSaveRadioButton() {
        if(notepadOperationsWithDatabase != null)
        notepadOperationsWithDatabase.Save(true);
    }

    private void onSelectOpenRadioButton() {
        notepadOperationsWithDatabase.Open();
    }


}