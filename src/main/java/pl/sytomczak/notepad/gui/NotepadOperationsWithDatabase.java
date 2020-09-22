package pl.sytomczak.notepad.gui;

import pl.sytomczak.notepad.gui.gui.OpenOrSaveDialogWindow;
import sun.plugin.viewer.context.PluginBeansContext;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static javax.swing.text.DefaultHighlighter.*;

public class NotepadOperationsWithDatabase {
    private JTextArea textArea;
    private JPanel panel;
    private String filePath;
    private NotepadModel notepadModel = new NotepadModel();
    private NotepadItem selectedItem = new NotepadItem();
    private String orygText;

    public NotepadOperationsWithDatabase(JTextArea textArea, JPanel panel) {
        if (textArea == null)
            return;

        this.textArea = textArea;
        this.panel = panel;
    }

    public void New() {
        if (textArea == null)
            return;
        if (filePath != null)
            if (JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(), "Do you want to overwrite changes") == 0)
                SaveNew();
        textArea.selectAll();
        textArea.replaceSelection("");
        selectedItem = new NotepadItem();
        orygText = "";
    }

    public void OpenDialog(String text) throws SQLException {
        selectedItem = notepadModel.getItemByText(text);
        if(selectedItem != null)
            orygText = selectedItem.getText();
    }

    public void SaveDialog(String text) throws SQLException {
        notepadModel.AddText(text);
    }



    public void OpenOrSaveDialog(Boolean openOrSave) throws SQLException {

        boolean save = true;
        boolean open = true;

        if(openOrSave == open) {
            selectedItem = notepadModel.getItemByText("po jakims konkretnym tekscie");
             orygText = selectedItem.getText();
        }

        else if(openOrSave == save) {
            notepadModel.AddText(selectedItem.getText());
        }

        // tutaj na razie mozesz zrobic proste okno z pytaniem czy user chce zapisac czy odczytac dane
        // jesli kliknie np. 'OK' to odczyta tekst z bazy, jak 'Anuluj' to zapisze czy cos w podobie, jesli np. da sie zmienic
        // tekst przyciskow no to proste, zamiana 'OK' na 'Odczytaj / Wczytaj' i 'Anuluj' na 'Zapisz'

        // jesli odczyt to mozna brac np.:
        //selectedItem = notepadModel.getItemByText("po jakims konkretnym tekscie");
        // orygText = selectedItem.getText();
        //lub
        //selectedItem = notepadModel.getItems().get(notepadModel.getItems().size()-1);
        //orygText = selectedItem.getText();

        // jesli zapis no to albo update albo save
        //notepadModel.Update(orygText, selectedItem.getText());
        // lub
        //notepadModel.AddText(selectedItem.getText());

        if(openOrSave)
        {
            ArrayList<NotepadItem> items = notepadModel.getItems();

            Integer nr = 0;
            if(items.size()>0) {
                nr = items.size() - 1;
                selectedItem = items.get(nr);
            }
            orygText = selectedItem.getText();
        }else {
            notepadModel.AddText(selectedItem.getText());
            orygText = selectedItem.getText();
        }
    }

    public void Open() {
        if (textArea == null)
            return;

        try {

            OpenOrSaveDialog(true);
            textArea.setText(selectedItem.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Save(Boolean showDialog) {
        if (textArea == null)
            return;

        String content;
        content = textArea.getText();
        if(selectedItem != null)
            selectedItem.setText(content);


        // tutaj tez musisz sobie ogarnac jak chcesz zeby dzialalo, czy to z jakims oknem czy jak
        try {
            notepadModel.Update(orygText, selectedItem.getText());
            notepadModel.AddText(selectedItem.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void SaveNew() {
        if (textArea == null)
            return;

        try {
            selectedItem.setText(textArea.getText());
            notepadModel.AddText(selectedItem.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SaveAs(Boolean showDialog) {
        if (textArea == null)
            return;

        // tutaj też do przerobienia jakoś, na obecnej bazie z jedna kolumna sie nie oplaca, a tak to np. sprawdzalabys jakos po nr albo
        // po innej kolumnie wybierajac ktory wiersz mialby byc nadpisany czy cos w podobie
        String content;
        content = textArea.getText();
        try {
            selectedItem.setText(textArea.getText());
            notepadModel.AddText(selectedItem.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Close() {
        System.exit(0);
    }

    public void Cut() {
        Clipboard clipboard;
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        String cutString = textArea.getSelectedText();
        StringSelection cutSelection = new StringSelection(cutString);
        clipboard.setContents(cutSelection, cutSelection);
        textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());

    }

    public void Copy() {

        Clipboard clipboard;
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        String copyText = textArea.getSelectedText();
        StringSelection copySelection = new StringSelection(copyText);
        clipboard.setContents(copySelection, copySelection);
    }


    public void Paste() {
        Clipboard clipboard;
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        try {
            Transferable pasteText = clipboard.getContents(textArea);
            String str = (String) pasteText.getTransferData(DataFlavor.stringFlavor);
            textArea.replaceRange(str, textArea.getSelectionStart(), textArea.getSelectionEnd());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SelectAll() {
        textArea.selectAll();
    }


    class myHighlighter extends DefaultHighlightPainter{
        public  myHighlighter(Color color) {
            super(color);
        }
    }

    HighlightPainter highlightPainter = new NotepadOperationsWithDatabase.myHighlighter(Color.yellow);


    public void removeHighLight(JTextComponent textComponent){
        Highlighter removeHighlighter = textComponent.getHighlighter();
        Highlighter.Highlight[] remove = removeHighlighter.getHighlights();

        for(int i = 0; i<remove.length; i++){
            if(remove[i].getPainter() instanceof NotepadOperations.myHighlighter){
                removeHighlighter.removeHighlight(remove[i]);
            }
        }
    }
    public void Search(JTextComponent textComponent, String textString){
        removeHighLight(textComponent);

        try{
            Highlighter highlighter = textComponent.getHighlighter();
            Document doc = textComponent.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;

            while((pos = text.toUpperCase().indexOf(textString.toUpperCase(), pos)) >= 0) {
                highlighter.addHighlight(pos, pos+textString.length(), highlightPainter);
                pos += textString.length();
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
