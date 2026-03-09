package com.mycompany.tienda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * Esta clase permite gestionar usuarios del sistemas como:
 * altas, bajas, navegacion y cierre de sesion
 * @author Emanuel
 */

public class AdminController {

    @FXML private TextField txtNuevoUser;
    @FXML private PasswordField txtNuevoPass;
    @FXML private ComboBox<String> cbRol;
    @FXML private Label lblEstado;

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colUser;
    @FXML private TableColumn<Usuario, String> colRol;
    @FXML private TableColumn<Usuario, String> colPass;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ObservableList<Usuario> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbRol.setItems(FXCollections.observableArrayList("ADMIN", "USER"));
        cbRol.getSelectionModel().select("USER");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("password"));
     

        tablaUsuarios.setItems(data);

        refrescarTablaUsuarios(); // ✅ carga al abrir
    }
/**
 * Recarga los usuarios de la base de datos y acualiza la tabla 
*/
    private void refrescarTablaUsuarios() {
        try {
            data.setAll(usuarioDAO.listarUsuarios());
            lblEstado.setText("Usuarios: " + data.size());
        } catch (Exception e) {
            lblEstado.setText("Error listando: " + e.getMessage());
        }
    }

    /**
     * Evento que se ejecuta al presionar el botón "Agregar Usuario".
     * Valida campos y registra el usuario en la base de datos.
     */
    @FXML
    private void onAgregarUsuario() {
        try {
            String user = txtNuevoUser.getText().trim();
            String pass = txtNuevoPass.getText().trim();
            String rol = cbRol.getValue();

            if (user.isEmpty() || pass.isEmpty()) {
                lblEstado.setText("Campos vacíos ❌");
                return;
            }

            usuarioDAO.altaUsuario(user, pass, rol);

            lblEstado.setText("Usuario creado ✅");
            txtNuevoUser.clear();
            txtNuevoPass.clear();

            refrescarTablaUsuarios(); // ✅ se actualiza sola

        } catch (Exception e) {
            // Si es duplicado, se verá más claro
            String msg = e.getMessage();
            if (msg != null && msg.toLowerCase().contains("duplicate")) {
                lblEstado.setText("Ese usuario ya existe ⚠️");
            } else {
                lblEstado.setText("Error: " + e.getMessage());
            }
        }
    }
/**
 * Evento que se ejecuta al presionar el botón "Baja Usuario".
 * Elimina el usuario seleccionado en la tabla.
 */
    @FXML
    private void onBajaUsuario() {
        try {
            Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                lblEstado.setText("Selecciona un usuario primero ️");
                return;
            }

            if ("admin".equalsIgnoreCase(seleccionado.getUsername())) {
                lblEstado.setText("No des de baja al admin principal ️");
                return;
            }

            boolean ok = usuarioDAO.eliminarUsuario(seleccionado.getId());
            lblEstado.setText(ok ? "Baja realizada " : "No se pudo dar de baja ");

            refrescarTablaUsuarios(); 

        } catch (Exception e) {
            lblEstado.setText("Error baja: " + e.getMessage());
        }
    }
/**
 * Navega a la vista de inventario
 */
    @FXML
    private void onIrInventario() {
        try {
            App.setRoot("primary");
        } catch (Exception e) {
            lblEstado.setText("Error: " + e.getMessage());
        }
    }
/**
 * Cierra la sesion actual y regresa a la pantalla de login
 */
    @FXML
    private void onSalir() {
        try {
            Sesion.usuarioActual = null;
            App.setRoot("login");
        } catch (Exception e) {
            lblEstado.setText("Error: " + e.getMessage());
        }
    }
}