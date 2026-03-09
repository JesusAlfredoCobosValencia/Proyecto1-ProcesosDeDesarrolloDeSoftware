/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 *Clase que representa un usuario dentro del sistema tienda
 * 
 * Contiene la informacion basica de un usuario como:
 * id, nombre, contraseña, rol y estado de actividad
 * @author venta
 */


public class Usuario {
    private int id;
    private String username;
    private String password;
    private String rol;
    private boolean activo;

    public Usuario() {}
    
    /**
     * Constructor con parametros para inicializar todos los atributos 
     * de cada usuario
     * @param id Identificador unico del usuario
     * @param username Nombre del usuario
     * @param password Contraseña del usuario
     * @param rol Rol asignado del usuario
     * @param activo Estado del usuario
     */
    public Usuario(int id, String username, String password, String rol, boolean activo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.activo = activo;
    }
/**
 * Obtiene el identificador del usuario
 * @return 
 */
    public int getId() {
        return id;
    }
    /**
     * Establece el identificador del usuario
     * @param id 
     */
    public void setId(int id) { 
        this.id = id; 
    }
/**
 * Obtiene el nombre del usuario
 * @return 
 */
    public String getUsername() {
        return username; 
    }
    /**
     * Establece el nombre del usuario
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }
/**
 * Obtiene la contraseña del usuario
 * @return 
 */
    public String getPassword() {
        return password; 
    }
    /**
     * Establece la contraseña del usuario
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password; 
    }
    /**
     * Obtiene el rol del usuario
     * @return 
     */

    public String getRol() {
        return rol;
    }
    /**
     * Establece el rol del usuario
     * @param rol 
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
/**
 * Indica si el usuario esta activo
 * @return 
 */
    public boolean isActivo() {
        return activo; 
    }
    /**
     * Cambia el estado activo del usuario
     * @param activo 
     */
    public void setActivo(boolean activo) { 
        this.activo = activo;
    }
}
