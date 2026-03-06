module com.mycompany.tienda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.tienda to javafx.fxml;
    exports com.mycompany.tienda;
}
