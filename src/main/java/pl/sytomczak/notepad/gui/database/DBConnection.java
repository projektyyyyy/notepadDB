package pl.sytomczak.notepad.gui.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String USERNAME = "dbUser";
    private static String PASSWORD = "dbPassword";
    private static String MYSQLCONN = "jdbc:mysql://localhost/login";
    private static String SQLITECONN = "jdbc:sqlite:nodepadDB.sqlite"; // jdbc:sqlite:sciezkaDoBazyDanych (na windowsie folder musi istniec, inaczej exception, nie wiem jak jest na linuxie) np. jdbc:sqlite:/GIT/notepad/db/myDatabase.db

    public static Connection getConnection(){
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQLITECONN);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void CreateDatabase(String fileName){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:"+fileName)){
            if(conn != null)
            {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Nazwa Drivera bazy danych: " + meta.getDriverName());
                System.out.println("Baza danych utworzona");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
