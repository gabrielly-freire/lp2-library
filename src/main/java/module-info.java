module br.ufrn.imd {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires transitive javafx.graphics;
    requires transitive java.sql;

    opens br.ufrn.imd to javafx.fxml;

    exports br.ufrn.imd;
    exports br.ufrn.imd.model;
    exports br.ufrn.imd.dao;
    exports br.ufrn.imd.model.enums;

}
