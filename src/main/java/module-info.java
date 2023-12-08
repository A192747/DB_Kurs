module com.example.Kurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires javafx.swing;

    opens com.example.Kurs to javafx.fxml;
    exports com.example.Kurs;
    exports com.example.Kurs.controllers;

    opens com.example.Kurs.controllers to javafx.fxml;
    opens com.example.Kurs.models to org.hibernate.orm.core, javafx.base;
    exports com.example.Kurs.util;
    opens com.example.Kurs.util to javafx.fxml;

}