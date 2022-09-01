//package com.testarmy.todoappjava;
//
//import java.sql.*;
//import java.util.Scanner;
//
//public class taskCreator {
//
//    private static void createTask() throws SQLException {
//        try (Connection connection = DriverManager.getConnection(
//                "jdbc:mysql://localhost:8012/to_do_app",
//                "root",
//                "")){
//            Statement statement = connection.createStatement();
//        String sqlCreateTask = """
//            CREATE TABLE task(
//                    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
//                    title VARCHAR(255) NOT NULL,
//                    description TEXT NOT NULL,
//                    done BOOLEAN NOT NULL DEFAULT FALSE,
//                    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
//                    priority ENUM ('high', 'normal', 'low') NOT NULL DEFAULT 'low',
//                    user_id INT NOT NULL,
//                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE);
//                                         """;
//        statement.executeUpdate(sqlCreateTask);
//    }
//    public static void addTask(Connection connection) throws SQLException{
//            try (Connection connection = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/to_do_list",
//                    "root",
//                    "Grupa03!")){
//                Statement statement = connection.createStatement();
//
//        ResultSet resultSetInsert = statement.getGeneratedKeys();
//        String sqlUpdate = """
//          INSERT INTO task(title,description,priority,user_id) VALUES
//         (?,?,?,?)
//             """;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("podaj nazwę zadania");
//        String title = scanner.nextLine();
//        System.out.println("podaj opis zadania");
//        String description = scanner.nextLine();
//        System.out.println("podaj priorytet zadania");
//        String priority = scanner.nextLine();
//        System.out.println("podaj numer użytkownika");
//        String userId = scanner.nextLine();
//        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
//        preparedStatement.setString(1,title);
//        preparedStatement.setString(2,description);
//        preparedStatement.setString(3,priority);
//        preparedStatement.setInt(4,Integer.parseInt(userId));
//
//        preparedStatement.executeUpdate();
//
//        if(resultSetInsert != null && resultSetInsert.next()) {
//            System.out.println("test: "+resultSetInsert.getInt(1));
//        }
//    }
//    private static void update(Connection connection) throws SQLException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("podaj id zadania");
//        String id = scanner.nextLine();
//        System.out.println("wybierz status");
//        String status = scanner.nextLine();
//        String sqlUpdate = "UPDATE task SET status = ? WHERE id = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
//        preparedStatement.setString(1,status);
//        preparedStatement.setInt(2,Integer.parseInt(id));
//        preparedStatement.executeUpdate();
//    }
//
//
//}
//    }
//}
