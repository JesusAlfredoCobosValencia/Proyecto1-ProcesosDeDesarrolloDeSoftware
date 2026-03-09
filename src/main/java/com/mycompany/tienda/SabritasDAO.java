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
        try (Connection con = ConexionBD.getConexion()) {

            // 1️⃣ buscar si ya existe
            String sqlBuscar = "SELECT id, stock FROM sabritas WHERE marca=? AND gramos=?";
            PreparedStatement psBuscar = con.prepareStatement(sqlBuscar);
            psBuscar.setString(1, s.getMarca());
            psBuscar.setInt(2, s.getGramos());

            ResultSet rs = psBuscar.executeQuery();

            if (rs.next()) {

                // 2️⃣ si existe, aumentar stock
                int id = rs.getInt("id");

                String sqlUpdate = "UPDATE sabritas SET stock = stock + ? WHERE id=?";
                PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, s.getStock());
                psUpdate.setInt(2, id);
                psUpdate.executeUpdate();

            } else {

                // 3️⃣ si no existe, insertar
                String sqlInsert = "INSERT INTO sabritas (marca, gramos, precio, stock) VALUES (?, ?, ?, ?)";
                PreparedStatement psInsert = con.prepareStatement(sqlInsert);

                psInsert.setString(1, s.getMarca());
                psInsert.setInt(2, s.getGramos());
                psInsert.setDouble(3, s.getPrecio());
                psInsert.setInt(4, s.getStock());

                psInsert.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("Error al agregar: " + e.getMessage());
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
        String sql = "SELECT id, marca, gramos, precio, stock FROM sabritas";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Sabritas(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getInt("gramos"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
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
        String sql = "SELECT id, marca, gramos, precio, stock FROM sabritas WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idBuscar);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Sabritas(
                            rs.getInt("id"),
                            rs.getString("marca"),
                            rs.getInt("gramos"),
                            rs.getDouble("precio"),
                            rs.getInt("stock")
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
    public boolean venderSabritas(int idProducto, int cantidadVendida) throws Exception{
        String sqlBuscar = "SELECT stock FROM sabritas WHERE id = ?";
        String sqlActualizar = "UPDATE sabritas SET stock = stock - ? WHERE id = ?";
        
        try(Connection con = ConexionBD.getConexion();
                PreparedStatement psBuscar = con.prepareStatement(sqlBuscar)){
            psBuscar.setInt(1, idProducto);
            
            try(ResultSet rs = psBuscar.executeQuery()){
                if(!rs.next()){
                    return false;
                }
                int stockActual = rs.getInt("stock");
                if(stockActual < cantidadVendida){
                    return false;
                }
            }
            try(PreparedStatement psActualizar = con.prepareStatement(sqlActualizar)){
                psActualizar.setInt(1, cantidadVendida);
                psActualizar.setInt(2, idProducto);
                return psActualizar.executeUpdate() > 0;
            }
        }
        
    }
    
    
    /**
     * Actualiza los datos de un registro existente.
     * @param s Objeto Sabritas con los datos actualizados
     * @return true si la actualizacion fue exitosa, false si no
     * @throws Exception Si ocurre error en la operacion
     */
    public boolean editarPorId(Sabritas s) throws Exception {
    String sql = "UPDATE sabritas SET marca = ?, gramos = ?, precio = ?, stock = ? WHERE id = ?";

    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, s.getMarca());
        ps.setInt(2, s.getGramos());
        ps.setDouble(3, s.getPrecio());
        ps.setInt(4, s.getStock());
        ps.setInt(5, s.getId());

        return ps.executeUpdate() > 0;
    }
}
    
    
    
    
    
    
}
