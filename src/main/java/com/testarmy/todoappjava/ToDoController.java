package com.testarmy.todoappjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static com.testarmy.todoappjava.JDBC.*;

public class ToDoController {



    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private Button register;

    public ToDoController() throws SQLException {
    }

    @FXML
    public void registerButton(ActionEvent event, Stage stage) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        connection.setCatalog("to_do_app");
        System.out.println("obecna baza danych: "+connection.getCatalog());
    }
    @FXML
    public void loginUser(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        connection.setCatalog("to_do_app");
        Scanner scannerLogin = new Scanner(loginField.getText());
        System.out.println("podaj login");
        String login = scannerLogin.nextLine();
        scannerLogin.close();
        Scanner scannerPassword = new Scanner(passwordField.getText());
        System.out.println("podaj hasło");
        String password = scannerPassword.nextLine();
        scannerPassword.close();
        String sqlUser = "SELECT id,name,password FROM user WHERE login=? AND password=?;";
        PreparedStatement statement = connection.prepareStatement(sqlUser);
        String sqlPswd = "SELECT password FROM user WHERE login=?;";
        PreparedStatement pswd = connection.prepareStatement(sqlPswd);
        pswd.setString(1, login);
        pswd.executeUpdate();
        statement.setString(1, login);
        if (BCrypt.checkpw(password, sqlPswd)) {
            statement.setString(2, password);
//            String sqlName = "SELECT name FROM user WHERE login=?;";
//            PreparedStatement name = connection.prepareStatement(sqlName);
//            name.setString(1,login);
            statement.executeUpdate();
        } System.out.println("błędne hasło");
    }

    @FXML
    public void login(ActionEvent event) throws SQLException {

        Window owner = submitButton.getScene().getWindow();

        System.out.println(loginField.getText());
        System.out.println(passwordField.getText());

        if (loginField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String emailId = loginField.getText();
        String password = passwordField.getText();

        JDBC jdbc = new JDBC();
        boolean flag = jdbc.validate(emailId, password);

        if (!flag) {
            infoBox("Type correct e-mail and password", null, "Failed");
        } else {
            infoBox("Login Successful!", null, "Failed");
        }
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }


    }