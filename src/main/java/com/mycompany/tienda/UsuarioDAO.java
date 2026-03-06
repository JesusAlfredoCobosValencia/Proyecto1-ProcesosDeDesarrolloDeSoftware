/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 * Gestiona las operaciones relacionadas con los usuarios
 * en la base de datos. Permite autenticar mediante el login, 
 * registrar nuevos usuarios, listar usuarios registrados y eliminar.
 * 
 * Utiliza la clase ConexionBD para conectarse a la base
 * @author Emanuel
 */



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    /**
     * Metodo que permite autenticar a un usuario
     * 
     * Realiza una consulta a la base de datos buscando el usuario
     * y contraseña que conincida.
     * 
     * @param username Nombre del usuario ingresado
     * @param password Contraseña ingresada
     * @return Objeto Usuario si las credenciales son correctas,
     * o null si no hay coincidencia
     * @throws Exception  Si ocurre un error dentro de la consulta
     */

 public Usuario login(String username, String password) throws Exception {
    String sql = "SELECT id, username, password, rol FROM usuarios WHERE username=? AND password=?";

    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username);
        ps.setString(2, password);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("rol"),
                    true // ya no existe activo, ponemos true fijo
                );
            }
        }
    }
    return null;
}
 /**
  * Metodo que registra un nuevo usuario a la base
  * 
  * @param username Nombre del nuevo usuario.
  * @param password Contraseña del usuario.
  * @param rol Rol del usuario dentro del sistema.
  * @throws Exception Si ocurre un error dentro de la consulta
  */

    public void altaUsuario(String username, String password, String rol) throws Exception {
        String sql = "INSERT INTO usuarios (username, password, rol) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, rol);
            ps.executeUpdate();
        }
    }
/**
 * Método que obtiene la lista de todos los usuarios registrados 
 * en la base de datos.
 * 
 * Realiza consulta SELECT y crea objetos con los datos obtenidos.
 * @return Lista de objetos Usuario registrados en el sistema.
 * @throws Exception Si ocurre un error dentro de la consulta.
 */
   

    public List<Usuario> listarUsuarios() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, username, password, rol FROM usuarios";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("rol"),
                    true
                ));
            }
        }
        return lista;
    }
    
    
    
    
    
 /**
  * Metodo que elimina un usuario utilizando su identificador
  * 
  * @param id Identificador unico del usuario
  * @return true si el usuario es eliminado correctamente
  * false si no se encontro
  * @throws Exception Si ocurre un error dentro de la consulta
  */   
    public boolean eliminarUsuario(int id) throws Exception {
    String sql = "DELETE FROM usuarios WHERE id=?";

    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}
    
    
    
    
    
    
    
}
