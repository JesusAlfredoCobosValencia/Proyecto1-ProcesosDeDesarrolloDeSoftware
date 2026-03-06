/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 * Clase DAO encargada de gestionar las operaciones
 * CRUD para la entidad Sabritas
 * en la base de datos.
 *
 * 
 * Esta clase permite:
 * Agregar registros
 * Listar registros
 * Buscar por ID
 * Editar registros
 * Eliminar registros
 *
 * Utiliza la clase {@link ConexionBD} para establecer la conexión
 * con la base de datos.
 * @author jesus-francisco-emanuel
 */
import com.mycompany.tienda.Sabritas;
import com.mycompany.tienda.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SabritasDAO {
    /**
     * Insertar un nuevo registro de Sabritas a la base de datos 
     * @param s Objeto sabritas que contiene datos a insertar
     */
    public void agregarSabritas(Sabritas s) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionBD.getConexion();

            String sql = "INSERT INTO sabritas (id, marca, gramos) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);

            ps.setInt(1, s.getId());
            ps.setString(2, s.getMarca());
            ps.setInt(3, s.getGramos());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Agregado correctamente.");
            } else {
                System.out.println("No se pudo agregar.");
            }

        } catch (Exception e) {
            System.out.println("Error al agregar: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e2) {
                System.out.println("Error cerrando: " + e2.getMessage());
            }
        }
    }
/**
 * Muestra los registros de Sabritas
 */
    public void listarSabritas() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionBD.getConexion();

            String sql = "SELECT id, marca, gramos FROM sabritas";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            System.out.println("=== LISTA DE SABRITAS ===");
            while (rs.next()) {
                int id = rs.getInt("id");
                String marca = rs.getString("marca");
                int gramos = rs.getInt("gramos");

                System.out.println("ID: " + id + " | Marca: " + marca + " | Gramos: " + gramos);
            }

        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e2) {
                System.out.println("Error cerrando: " + e2.getMessage());
            }
        }
    }

   
/**
 * Eliminar un registro de Sabritas por su ID
 * @param idEliminar ID del registro a eliminar
 */
    public void eliminarPorId(int idEliminar) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionBD.getConexion();

            String sql = "DELETE FROM sabritas WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEliminar);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println(" Eliminado");
            } else {
                System.out.println("no se eliminó (ID no existe)");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e2) {
                System.out.println("Error cerrando: " + e2.getMessage());
            }
        }
    }
    
   
/**
 * Obtiene los registros de Sabristas en lista
 * @return Lista de Sabritas
 * @throws Exception Si ocurre errores en la consulta
 */    
    public List<Sabritas> obtenerTodas() throws Exception {
        List<Sabritas> lista = new ArrayList<>();
        String sql = "SELECT id, marca, gramos FROM sabritas";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Sabritas(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getInt("gramos")
                ));
            }
        }
        return lista;
    }
/**
 * Busca un registro de Sabritas por su ID
 * @param idBuscar ID a consultar
 * @return Objeto Sabritas, null si no se encuentra
 * @throws Exception Si ocurrer errores en la cosulta
 */
    public Sabritas obtenerPorId(int idBuscar) throws Exception {
        String sql = "SELECT id, marca, gramos FROM sabritas WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idBuscar);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Sabritas(
                            rs.getInt("id"),
                            rs.getString("marca"),
                            rs.getInt("gramos")
                    );
                }
            }
        }
        return null;
    }
/**
 * Elimina un registro mediante el ID y lo devueve en booleano
 * @param idEliminar ID del registro en eliminar
 * @return true si se encuentra, false si no
 * @throws Exception Si ocurre algun error en la consulta
 */
    public boolean eliminarPorIdBool(int idEliminar) throws Exception {
        String sql = "DELETE FROM sabritas WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEliminar);
            return ps.executeUpdate() > 0;
        }
    }
    
    
    /**
     * Actualiza los datos de un registro existente.
     * @param s Objeto Sabritas con los datos actualizados
     * @return true si la actualizacion fue exitosa, false si no
     * @throws Exception Si ocurre error en la operacion
     */
    public boolean editarPorId(Sabritas s) throws Exception {
    String sql = "UPDATE sabritas SET marca=?, gramos=? WHERE id=?";

    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, s.getMarca());
        ps.setInt(2, s.getGramos());
        ps.setInt(3, s.getId());

        return ps.executeUpdate() > 0;
    }
}
    
    
    
    
    
    
}
