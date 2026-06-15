module com.ascadd.ascadd {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ascadd.ascadd to javafx.fxml;
    exports com.ascadd.ascadd;
}