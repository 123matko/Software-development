module com.example.dayplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires opencsv;

    opens com.example.dayplanner to javafx.fxml;
    exports com.example.dayplanner;
}