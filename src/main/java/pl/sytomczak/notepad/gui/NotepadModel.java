package pl.sytomczak.notepad.gui;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import pl.sytomczak.notepad.gui.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class NotepadModel {

    private Connection connection;

    public boolean isDatabaseConnecter(){ return this.connection != null; }

    public NotepadModel(){
        this.connection = DBConnection.getConnection();
    }

    public NotepadItem getItemByText(String text) throws SQLException {
        String msg = "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = this.connection.prepareStatement("SELECT * FROM texts WHERE text = ?");
            ps.setString(1, text);

            rs = ps.executeQuery();
            if(rs.next())
                return new NotepadItem( rs.getString(1));

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }
        finally {
            if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
        }
    }

    public ArrayList<NotepadItem> getItems() throws SQLException {
        String msg = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<NotepadItem> items = new ArrayList<NotepadItem>();

        try{
            ps = this.connection.prepareStatement("SELECT * FROM texts");

            rs = ps.executeQuery();
            while (rs.next())
                items.add( new NotepadItem( rs.getString(1)));

            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }
        finally {
            if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
        }
    }

    public boolean AddText(String text) throws SQLException {
        String msg = "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = this.connection.prepareStatement("INSERT INTO texts (text) VALUES (?)");
            ps.setString(1, text);
            if(ps.executeUpdate() != 0)
                return true;
            else
                return false;

            // DODATKOWO executeUpdate lub execute - korzystac do INSERT, UPDATE, DELETE zapytan, a ten powyzszy do zwrocenia jakichs danych bo zwraca ResultSet
            // sprawdzic opis mozesz wchodzac do metody np. CTRL + klik myszka na metode

            // tutaj dodatkowo dorobilem sprawdzenie czy dodano do bazy powyzszy tekst, dlatego tez zmienilem metodke
            // z voida na boola, jak nie chcesz tego to zmien na voida i usun to ponizsze sprawdzenie :)

           // ps = this.connection.prepareStatement("SELECT count(text) FROM texts WHERE text = ?");
           // ps.setString(1, text);
          //  rs = ps.executeQuery();
          //  if(ps.execute())
          //      return true;
           // else
           //     return  false;

            //Integer count = 0;
            //if(rs.next())
           //     count = rs.getInt(1);

           // if(count == 1)
            //    return true;
           // else
           //     return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }
        finally {
            if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
        }
    }

    public boolean Update(String orygText, String newText) throws SQLException {
        String msg = "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = this.connection.prepareStatement("UPDATE texts SET text = ? WHERE text = ?");
            ps.setString(1, newText);
            ps.setString(2, orygText);

            if(ps.executeUpdate() != 0)
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }
        finally {
            if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
        }
    }

}
