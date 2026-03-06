/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 * Clase que representa un producto tipo Sabritas dentro del sistema Tienda.
 *
 * Esta clase funciona como modelo del inventario y contiene
 * la información básica de cada producto:
 * 
 * dentificador único (id)
 * Marca del producto
 * Cantidad en gramos
 *
 * Es utilizada principalmente por {@link SabritasDAO} para realizar
 * operaciones CRUD en la base de datos.
 * @author jesus-francisco-emanuel
 */


public class Sabritas {
    private int id;
    private String marca;
    private int gramos;

    /**
     * Constructor vacio que permite crear un objeto sabritas sin ningun atributo.
     */
    public Sabritas() {
    }

    /**
     * Constructor con parametros inicializados
     * @param id Identificador del producto
     * @param marca Marca del producto
     * @param gramos Cantidad en gramos del producto
     */
    public Sabritas(int id, String marca, int gramos) {
        this.id = id;
        this.marca = marca;
        this.gramos = gramos;
    }

    /**
     * Obtiene el identificador del producto
     * @return Nuevo identificador
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del producto
     * @param id Nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la marca del producto
     * @return Marca del producto
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del producto
     * @param marca Marca del producto
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene la cantidad del producto en gramos
     * @return Gramos del producto
     */
    public int getGramos() {
        return gramos;
    }

    /**
     * Establece la cantidad del producto en gramos
     * @param gramos Gramos del producto
     */
    public void setGramos(int gramos) {
        this.gramos = gramos;
    }

    /**
     * Devuelve la representacion del objeto enm texto
     * @return Cadena de datos del producto.
     */
    @Override
    public String toString() {
        return "Sabritas { id=" + id + ", marca='" + marca + "', gramos=" + gramos + " }";
    }
}