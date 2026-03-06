/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 *
 * Clase encargada de gestionar la conexion a la base de datos.
 * 
 * Utiliza JDBC para establecer la conexion con un servidor MySQL.
 * Implementa un método estático que permite a otras clases
 * obtener una conexión activa a la base de datos.
 * 
 * @author jesus-francisco-emanuel
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    /**
     * Establece la conexion con la base de datos MySQL.
     * 
     * @return {@link Connection} con la conexión activa..
     * @throws Exception Si ocurre algun error al cargar driver o establecer conexion.
     */

    private static Connection conectar() throws Exception {
 
          try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
        System.out.println("no se cargo el driver: " + e.getMessage());
        throw e;
    }
        
        
        
        
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/inventarioOxxo",
                "userOxxo",
                ""
        );
    }
/**
 * Permite obtener una conexion a la base de datos
 * @return Conexion a la base exitosa.
 * @throws Exception Si ocurre algun error en la conexion.
 */
    public static Connection getConexion() throws Exception {
        return conectar();
    }
}
