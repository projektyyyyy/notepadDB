package pl.sytomczak.notepad.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NotepadItem {


    public String getText() {
        return text.get();
    }
    public Integer getNr() {return nr.get(); }

    public StringProperty textProperty() {
        return text;
    }
    public IntegerProperty nrProperty() {return nr; }

    public void setText(String text) {
        this.text.set(text);
    }

    public void setNr(Integer nr){
        this.nr.set(nr);
    }

    private StringProperty text;
    private IntegerProperty nr;

    public NotepadItem(Integer nr, String text)
    {
        this.nr = new SimpleIntegerProperty(nr);
        this.text = new SimpleStringProperty(text);
    }

    public NotepadItem()
    {
        this.nr = new SimpleIntegerProperty(1);
        this.text = new SimpleStringProperty("");
    }

}
