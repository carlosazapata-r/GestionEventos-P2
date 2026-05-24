module com.proyecto.gestioneventosp2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.proyecto.gestioneventosp2 to javafx.fxml;
    exports com.proyecto.gestioneventosp2;
}