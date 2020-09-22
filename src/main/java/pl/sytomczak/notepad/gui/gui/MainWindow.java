package pl.sytomczak.notepad.gui.gui;

import pl.sytomczak.notepad.gui.NotepadOperationsWithDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JPanel panel1;
    private JTextArea textArea1;
    private JTextField searchField;

    JMenuBar menuBar;
    JMenu menuFile, menuEdit, menuHelp;
    JMenuItem fNew, fOpen, fSave, fSaveAs, fClose, eCut, eCopy, ePaste, eSelectAll;
    JButton searchButton;
  //  NotepadOperations notepadOperations;
    NotepadOperationsWithDatabase notepadOperationsWithDatabase;

    public MainWindow() {
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        notepadOperationsWithDatabase = new NotepadOperationsWithDatabase(textArea1, panel1);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Notepad");
        InitializeTopMenuItems();
        InitializeMenuItemMethods();

        fOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenOrSaveDialogWindow openOrSaveDialogWindow = new OpenOrSaveDialogWindow();
                openOrSaveDialogWindow.pack();
                openOrSaveDialogWindow.setVisible(true);
                openOrSaveDialogWindow.setResizable(false);
                openOrSaveDialogWindow.setLocationRelativeTo(null);

            }
        });
}

    private void InitializeTopMenuItems() {


        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");

        setJMenuBar(menuBar);
        setContentPane(panel1);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);


        fNew = new JMenuItem("New");
        fOpen = new JMenuItem("Open");
        fSave = new JMenuItem("Save");
        fSaveAs = new JMenuItem("Save as");
        fClose = new JMenuItem("Close");

        eCut = new JMenuItem("Cut");
        eCopy = new JMenuItem("Copy");
        ePaste = new JMenuItem("Paste");
        eSelectAll = new JMenuItem("Select all");

        menuFile.add(fNew);
        menuFile.add(fOpen);
        menuFile.add(fSave);
        menuFile.add(fSaveAs);
        menuFile.add(fClose);

        menuEdit.add(eCut);
        menuEdit.add(eCopy);
        menuEdit.add(ePaste);
        menuEdit.add(eSelectAll);
    }

    private void InitializeMenuItemMethods() {

        // wszedzie zamienione z notepadOptions na ten nowy notepadOperationsWithDatabase
        // notepadOp do usuniecia

        fNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.New();

            }
        });
        fOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.Open();
            }
        });
        fSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.Save(false);
            }
        });
        fSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.SaveAs(false);
            }
        });
        fClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.Close();
            }
        });

        eCut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.Cut();
            }
        });
        eCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.Copy();
            }
        });
        ePaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.Paste();
            }
        });
        eSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.SelectAll();
            }
        });


        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notepadOperationsWithDatabase.Search(textArea1, searchField.getText());
                    }
                });
    }

    public static void main(String[] args) {
        MainWindow appMenu = new MainWindow();
        appMenu.setVisible(true);

    }
}