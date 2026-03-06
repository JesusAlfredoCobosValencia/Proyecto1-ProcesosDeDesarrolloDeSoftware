/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 *
 * Controlador de la vista de inicio de sesión del sistema Tienda.
 * Esta clase gestiona la autenticación de usuarios mediante la validación
 * de credenciales contra la base de datos utilizando {@link UsuarioDAO}.
 *
 * Dependiendo del rol del usuario autenticado, redirige a:
 * Vista de administrador (admin.fxml)
 * Lista principal de inventario (primary.fxml)
 * 
 * @author venta
 */


import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField txtUser;
    @FXML private PasswordField txtPass;
    @FXML private Label lblEstado;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    /**
     * Metodo que se ejecuta al presionar el boton de Entrar
     * 
     * Validad las credenciales ingresadas y dirige al usuario dependiendo su rol
     */

    @FXML
    private void onEntrar() {
        try {
            String user = txtUser.getText().trim();
            String pass = txtPass.getText().trim();

            Usuario u = usuarioDAO.login(user, pass);

            if (u == null) {
                lblEstado.setText("Credenciales incorrectas ❌");
                return;
            }

            // Guardar “sesión” simple
            Sesion.usuarioActual = u;

            if ("ADMIN".equalsIgnoreCase(u.getRol())) {
                App.setRoot("admin");   // admin.fxml
            } else {
                App.setRoot("primary"); // tu pantalla de sabritas
            }

        } catch (Exception e) {
            lblEstado.setText("Error: " + e.getMessage());
        }
    }
}
