package com.mycompany.tienda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Clase principal de la aplicacion Tienda.
 * 
 * Esta clase es responsable de iniciar la aplicacion, carga
 * la vista inicial y gestiona el cambio a FXML.
 * 
 * La vista inicializa la aplicacion mediante el login.fxml
 * @author Emanuel
 */

public class App extends Application {

    private static Scene scene;
    /**
     * Metodo que incia la aplicacion JavaFx
     * @param stage Ventana proporcionada por JavaFx
     * @throws IOException Si ocurre algun error al carga el FXML.
     */

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setTitle("Tienda");
        stage.setScene(scene);
        stage.show();
    }
/**
 * Cambia la vista actual de la aplicacion.
 * @param fxml Nombre del archivo FXML sin extension
 * @throws IOException Si ocurre algun error
 */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
/**
 * Carga un archivo FXML desde la ruta del paquete
 * @param fxml Nombre del archivo sin extension
 * @return Nodo raiz desde el FXML
 * @throws IOException Si ocurre error al cargar
 */
    private static javafx.scene.Parent loadFXML(String fxml) throws IOException {
    String ruta = "/com/mycompany/tienda/" + fxml + ".fxml";
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(ruta));
    return fxmlLoader.load();
}
/**
 * Metodo principal que envia la aplicacion 
 * @param args Argumentos en linea de comandos
 */
    public static void main(String[] args) {
        launch(args);
    }
}