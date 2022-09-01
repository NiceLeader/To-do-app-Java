package com.testarmy.todoappjava;
import java.sql.*;

public class JDBC {
    public static void main(String[] args) {
        try {
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/to_do_app",
                            "root",
                            ""
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
