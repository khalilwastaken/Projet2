module com.example.projet {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.projet to javafx.fxml;
    exports com.example.projet;
}