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

    public Connection conn = null;
    public Statement statmt;
    public ResultSet resSet;

    public Controller() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Database is connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addQuery(){
        try {
            statmt = conn.createStatement();
            String queryAdd = String.format("INSERT INTO people (FIO, AGE) " +
                    "VALUES ('%s', %s)", fio1.getText(), age1.getText());
            ResultSet rs = statmt.executeQuery(queryAdd);
        } catch (SQLException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    public void remQuery(){
        try {
            statmt = conn.createStatement();
            String queryRem = String.format("DELETE FROM people " +
                    "WHERE FIO = '%s'", fio2.getText());
            ResultSet rs = statmt.executeQuery(queryRem);
        } catch (SQLException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    public void updQuery(){
        try {
            statmt = conn.createStatement();
            String queryUpd = String.format("UPDATE people SET AGE = %d WHERE FIO = '%s'",
                    Integer.parseInt(age3.getText()), fio3.getText());
            ResultSet rs = statmt.executeQuery(queryUpd);
        } catch (SQLException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    private void disconnect(){
        try {
            resSet.close();
            statmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Соединения закрыты");
    }

    private void tableUpdate() {
        try {
            resSet = statmt.executeQuery("SELECT * FROM people");
        } catch (SQLException e) {
            e.printStackTrace();
            disconnect();
        }

        System.out.println("Таблица выведена");
    }
}
