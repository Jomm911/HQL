module com.example.hql {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hql to javafx.fxml;
    exports com.example.hql;
}