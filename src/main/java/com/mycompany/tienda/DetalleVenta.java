/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 * Clase que representa un producto agregado al carrito de compra.
 *
 * Guarda el producto, la cantidad seleccionada, el precio unitario y el
 * subtotal.
 *
 * @author Emanuel
 */
public class DetalleVenta {

    private int idProducto;
    private String marca;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    /**
     * Constructor con parámetros.
     *
     * @param idProducto Identificador del producto.
     * @param marca Marca del producto.
     * @param cantidad Cantidad seleccionada.
     * @param precioUnitario Precio por unidad.
     */
    public DetalleVenta(int idProducto, String marca, int cantidad, double precioUnitario) {
        this.idProducto = idProducto;
        this.marca = marca;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    /**
     * @return ID del producto.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto Nuevo ID del producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return Marca del producto.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca Nueva marca.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return Cantidad del producto en el carrito.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad y recalcula el subtotal.
     *
     * @param cantidad Nueva cantidad.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = this.cantidad * this.precioUnitario;
    }

    /**
     * @return Precio unitario.
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario y recalcula el subtotal.
     *
     * @param precioUnitario Nuevo precio unitario.
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.subtotal = this.cantidad * this.precioUnitario;
    }

    /**
     * @return Subtotal del producto.
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal Nuevo subtotal.
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
