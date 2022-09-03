package com.testarmy.todoappjava;
import java.sql.*;
import java.util.Scanner;

public class JDBC {
    protected static  String DATABASE_URL = "jdbc:mysql://localhost:3306/to_do_app";
    protected static  String DATABASE_USERNAME = "root";
    protected int returnGeneratedKeys = Statement.RETURN_GENERATED_KEYS;
    protected static  String DATABASE_PASSWORD = "Grupa03!";
    protected static  String SELECT_QUERY = "SELECT * FROM registration WHERE email_id = ? and password = ?";


    public boolean validate(String emailId, String password){

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setString(1, emailId);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }


        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    private static void createTableTask(Connection connection) throws SQLException {
            String sqlCreateTableTask = """
                    CREATE TABLE IF NOT EXISTS task(
                            id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            description TEXT NOT NULL,
                            done BOOLEAN NOT NULL DEFAULT FALSE,
                            created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            priority ENUM ('wysoki', 'średni', 'niski') NOT NULL DEFAULT 'niski',
                            user_id INT NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE);
                                                 """;
        PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateTableTask);
        preparedStatement.executeUpdate();
        }

        private static void createTableUser(Connection connection) throws SQLException {
                String sqlCreateTableUser = """
                    CREATE TABLE IF NOT EXISTS user(
                            id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(128) NOT NULL,
                            surname VARCHAR(255) NOT NULL,
                            login VARCHAR(128) NOT NULL UNIQUE,
                            password CHAR(60) NOT NULL,
                            email VARCHAR(255) UNIQUE);
                                                 """;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateTableUser);
            preparedStatement.executeUpdate();
        }

    public static void addTask(Connection connection) throws SQLException {
            String sqlUpdate = """
                     INSERT INTO task(title,description,priority,user_id) VALUES
                    (?,?,?,?)
                        """;
            Scanner scanner = new Scanner(System.in);
            System.out.println("podaj nazwę zadania");
            String title = scanner.nextLine();
            System.out.println("podaj opis zadania");
            String description = scanner.nextLine();
            System.out.println("podaj priorytet zadania (niski, średni, lub wysoki)");
            String priority = scanner.nextLine();
            System.out.println("podaj numer użytkownika do którego należy zadanie");
            String userId = scanner.nextLine();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, priority);
            preparedStatement.setInt(4, Integer.parseInt(userId));
            preparedStatement.executeUpdate();
        }
    private static void updateStatusOfTask(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("podaj numer zadania");
        String id = scanner.nextLine();
        System.out.println("wybierz status (1 - zadanie wykonane/0 - zadanie nie wykonane)");
        String status = scanner.nextLine();
        String sqlUpdate = "UPDATE task SET done = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
        preparedStatement.setString(1,status);
        preparedStatement.setInt(2,Integer.parseInt(id));
        preparedStatement.executeUpdate();
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        //updateStatusOfTask(connection);
        addTask(connection);
       // createTableTask(connection);
    }
}

