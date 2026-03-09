package com.mycompany.tienda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador de vista principal del inventario.
 * 
 * Esta clase permite gestionar los productos {@link Sabritas}
 * dentro del sistema y hacer operaciones CRUD.
 * 
 * Utiliza {@link SabritasDAO} para la interacción con la base de datos
 * y {@link Sesion} para el manejo de sesión.
 * 
 * @author Emanuel
 */

public class PrimaryController {

    @FXML private TextField txtId;
    @FXML private TextField txtMarca;
    @FXML private TextField txtGramos;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;

    @FXML private TableView<Sabritas> tabla;
    @FXML private TableColumn<Sabritas, Integer> colId;
    @FXML private TableColumn<Sabritas, String> colMarca;
    @FXML private TableColumn<Sabritas, Integer> colGramos;
    @FXML private TableColumn<Sabritas, Double> colPrecio;
    @FXML private TableColumn<Sabritas, Integer> colStock;

    @FXML private Label lblEstado;

    private final SabritasDAO dao = new SabritasDAO();
    private final ObservableList<Sabritas> data = FXCollections.observableArrayList();

    /**
     * Método que se ejecuta automáticamente al cargar la vista.
     * Configura la tabla y carga los registros existentes.
     */
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colGramos.setCellValueFactory(new PropertyValueFactory<>("gramos"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tabla.setItems(data);

        // Carga inicial para probar conexión
        refrescarTabla();
    }
    
    
    /**
     * Actualiza los datos actuales de la tabla.
     */
    private void refrescarTabla() {
    try {
        data.setAll(dao.obtenerTodas());
        lblEstado.setText("Estado: lista actualizada  (registros: " + data.size() + ")");
    } catch (Exception e) {
        lblEstado.setText("Estado: error al listar ");
        error("No se pudo listar", e.getMessage());
    }
}
    
    
/**
 * Evento que agrega productos al inventario.
 */
    @FXML
    private void onAgregar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String marca = txtMarca.getText().trim();
            int gramos = Integer.parseInt(txtGramos.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            
            if(marca.isEmpty()){
                info("Agregar", "La marca no puede ir vacía.");
                return;
            }

            dao.agregarSabritas(new Sabritas(0, marca, gramos, precio, stock));
            lblEstado.setText("Estado: agregado ");
            limpiar();
            refrescarTabla();

        } catch (Exception e) {
            error("No se pudo agregar", e.getMessage());
            lblEstado.setText("Estado: error al agregar ");
        }
    }

/**
 * Evento que elimina productos del inventario.
 */
    @FXML
    private void onEliminar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            boolean ok = dao.eliminarPorIdBool(id);

            if (ok) {
                lblEstado.setText("Estado: eliminado ");
                limpiar();
                refrescarTabla();
            } else {
                info("Eliminar", "No se eliminó (ID no existe).");
                lblEstado.setText("Estado: ID no existe ️");
            }

        } catch (Exception e) {
            error("No se pudo eliminar", e.getMessage());
            lblEstado.setText("Estado: error al eliminar ");
        }
    }

    /**
     * Limpia los campos de entrada.
     */
    private void limpiar() {
        txtId.clear();
        txtMarca.clear();
        txtGramos.clear();
        txtPrecio.clear();
        txtStock.clear();
    }

    /**
     * Muestra un mensaje informativo
     * @param titulo Titulo de la alerta
     * @param msg Mensaje de error
     */
    private void info(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void error(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
    
    /**
     * Cierra la sesion actual y regresa al login.
     */
    @FXML
private void onCerrarSesion() {
    try {
        Sesion.usuarioActual = null; // limpiar sesión
        App.setRoot("login");
    } catch (Exception e) {
        lblEstado.setText("Error: " + e.getMessage());
    }
}
    @FXML
    private void onAbrirVentas() {
        try {
            App.setRoot("venta");
        } catch (Exception e) {
            lblEstado.setText("Estado: error al abrir ventas");
            error("No se pudo abrir la ventana de ventas", e.getMessage());
        }
    }

/**
 * Evento que edita un producto del inventario.
 */
@FXML
private void onEditar() {
    try {
        int id = Integer.parseInt(txtId.getText().trim());
        String marca = txtMarca.getText().trim();
        int gramos = Integer.parseInt(txtGramos.getText().trim());
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        int stock = Integer.parseInt(txtStock.getText().trim());


        if (marca.isEmpty()) {
            info("Editar", "La marca no puede ir vacía.");
            return;
        }

        boolean ok = dao.editarPorId(new Sabritas(id, marca, gramos, precio, stock));

        if (ok) {
            lblEstado.setText("Estado: editado ✅");
            limpiar();
            refrescarTabla(); // ✅ se actualiza sola
        } else {
            info("Editar", "No se editó (ID no existe).");
            lblEstado.setText("Estado: ID no existe ⚠️");
        }

    } catch (Exception e) {
        lblEstado.setText("Estado: error al editar ❌");
        error("No se pudo editar", e.getMessage());
    }
}









}