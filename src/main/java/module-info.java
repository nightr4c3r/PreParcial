module com.nightr4c3r.preparcial {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nightr4c3r.preparcial to javafx.fxml;
    exports com.nightr4c3r.preparcial;
    exports com.nightr4c3r.preparcial.Models;
    opens com.nightr4c3r.preparcial.Models to javafx.fxml, javafx.base;
    exports com.nightr4c3r.preparcial.Controllers;
    opens com.nightr4c3r.preparcial.Controllers to javafx.fxml;
    exports com.nightr4c3r.preparcial.Repositories;
    opens com.nightr4c3r.preparcial.Repositories to javafx.base, javafx.fxml;
}