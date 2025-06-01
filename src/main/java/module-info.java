module com.example.rowery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;
    requires java.desktop;

    opens com.example.rowery.klasy to org.hibernate.orm.core;
    opens com.example.rowery to javafx.fxml;

    exports com.example.rowery;
    exports com.example.rowery.klasy;
}
