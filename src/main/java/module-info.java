module com.testarmy.todoappjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires junit;


    opens com.testarmy.todoappjava to javafx.fxml;
    exports com.testarmy.todoappjava;
}