package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;

//<GridPane alignment="center" hgap="10" vgap="10"
public class Controller {
    @FXML
    Button addBtn;

    @FXML
    Button updBtn;

    @FXML
    Button remBtn;

    @FXML
    TextField fio1;

    @FXML
    TextField fio2;

    @FXML
    TextField fio3;

    @FXML
    TextField age1;

    @FXML
    TextField age3;

    public static Connection conn = null;
    public static Statement statmt;
    public static ResultSet resSet;

    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            //statmt = conn.createStatement();
            System.out.println("Database is connected!");
        } catch (Exception e) {
            e.printStackTrace();
            disconnect();
        }
//        try {
//            tableUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void addQuery(){
        try {
            statmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            disconnect();
        }
        String queryAdd = String.format("INSERT INTO people (FIO, AGE)\n" +
                "VALUES ('%s', %d)", fio1.getText(), Integer.parseInt(age1.getText()));

        try {
            ResultSet rs = statmt.executeQuery(queryAdd);
        } catch (SQLException sqle) {
            System.err.println("Connection failed...");
            disconnect();
        }
    }

    public void remQuery(){
        String queryRem = String.format("DELETE FROM people\n" +
                "WHERE FIO = '%s'", fio2.getText());
        try {
            ResultSet rs = statmt.executeQuery(queryRem);
        } catch (SQLException sqle) {
            System.err.println("Connection failed...");
            disconnect();
        }
    }

    public void updQuery(){
        String queryUpd = String.format("UPDATE people SET AGE = '%d'\n WHERE FIO = '%s'",
                                        Integer.parseInt(age3.getText()), fio3.getText());
        try {
            ResultSet rs = statmt.executeQuery(queryUpd);
        } catch (SQLException sqle) {
            System.err.println("Connection failed...");
            disconnect();
        }
    }

    private static void disconnect(){
        try {
            conn.close();
            statmt.close();
            resSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Соединения закрыты");
    }

    private static void tableUpdate() throws SQLException {
        try {
            resSet = statmt.executeQuery("SELECT * FROM people");
        } catch (SQLException e) {
            e.printStackTrace();
            disconnect();
        }

//        while(resSet.next())
//        {
//            int id = resSet.getInt("id");
//            String  name = resSet.getString("name");
//            String  phone = resSet.getString("phone");
//            System.out.println( "ID = " + id );
//            System.out.println( "name = " + name );
//            System.out.println( "phone = " + phone );
//            System.out.println();
//        }

        System.out.println("Таблица выведена");
    }
}
