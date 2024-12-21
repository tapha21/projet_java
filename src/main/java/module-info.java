module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    // requires org.yaml.snakeyaml;
    requires lombok;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.yaml.snakeyaml;
    opens com.controller to javafx.fxml;
    opens com.entities to javafx.base, org.hibernate.orm.core;
        exports com;
    exports com.entities to org.hibernate.orm.core;

}
