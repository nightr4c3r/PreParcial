module com.nightr4c3r.preparcial {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nightr4c3r.preparcial to javafx.fxml;
    exports com.nightr4c3r.preparcial;
}