package com.mycompany.tienda;

import java.io.IOException;
import javafx.fxml.FXML;
/**
 * Controlador de la vista secundaria del sistema Tienda.
 * 
 * Esta clase maneja los eventos asociados a la interfaz gráfica
 * definida en el archivo FXML correspondiente a la vista secundaria.
 * 
 * Permite realizar la navegación entre vistas dentro de la aplicación.
 * 
 * 
 * @author venta
 */


public class SecondaryController {
    /**
     * Cambia la vista actual por la principal.
     * 
     * Es invocado desde la interfaz FXML
     * mediante una accion asociada a un boton
     * 
     * @throws IOException Si llega a ocurrir algun error al cargar la vista
     */

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}