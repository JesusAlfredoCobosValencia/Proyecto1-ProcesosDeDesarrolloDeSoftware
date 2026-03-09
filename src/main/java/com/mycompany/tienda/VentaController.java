/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador de la vista de ventas.
 *
 * Permite cargar inventario, agregar productos al carrito, eliminar productos
 * del carrito, calcular el total y finalizar la venta actualizando el stock.
 *
 * @author Emanuel
 */
public class VentaController {

    @FXML
    private TableView<Sabritas> tablaProductos;
    @FXML
    private TableColumn<Sabritas, Integer> colIdProducto;
    @FXML
    private TableColumn<Sabritas, String> colMarcaProducto;
    @FXML
    private TableColumn<Sabritas, Integer> colGramosProducto;
    @FXML
    private TableColumn<Sabritas, Double> colPrecioProducto;
    @FXML
    private TableColumn<Sabritas, Integer> colStockProducto;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TableView<DetalleVenta> tablaCarrito;
    @FXML
    private TableColumn<DetalleVenta, String> colMarcaCarrito;
    @FXML
    private TableColumn<DetalleVenta, Integer> colCantidadCarrito;
    @FXML
    private TableColumn<DetalleVenta, Double> colPrecioUnitarioCarrito;
    @FXML
    private TableColumn<DetalleVenta, Double> colSubtotalCarrito;

    @FXML
    private Label lblTotal;
    @FXML
    private Label lblEstado;

    private final SabritasDAO dao = new SabritasDAO();
    private final ObservableList<Sabritas> inventario = FXCollections.observableArrayList();
    private final ObservableList<DetalleVenta> carrito = FXCollections.observableArrayList();

    /**
     * Inicializa la vista y configura las tablas.
     */
    @FXML
    public void initialize(){
	colIdProducto.setCellValueFactory(new PropertyValueFactory<>("id"));
	colMarcaProducto.setCellValueFactory(new PropertyValueFactory<>("marca"));
	colGramosProducto.setCellValueFactory(new PropertyValueFactory<>("gramos"));
	colPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("precio"));
	colStockProducto.setCellValueFactory(new PropertyValueFactory<>("stock"));

	colMarcaCarrito.setCellValueFactory(new PropertyValueFactory<>("marca"));
	colCantidadCarrito.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
	colPrecioUnitarioCarrito.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
	colSubtotalCarrito.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

	tablaProductos.setItems(inventario);
	tablaCarrito.setItems(carrito);

	tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	tablaCarrito.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	refrescarInventario();
	actualizarTotal();
    }

    /**
     * Recarga el inventario desde la base de datos.
     */
    private void refrescarInventario() {
        try {
            inventario.setAll(dao.obtenerTodas());
            lblEstado.setText("Estado: inventario actualizado");
        } catch (Exception e) {
            lblEstado.setText("Estado: error al cargar inventario");
            error("Error", e.getMessage());
        }
    }

    /**
     * Agrega el producto seleccionado al carrito.
     */
    @FXML
    private void onAgregarCarrito() {
        try {
            Sabritas producto = tablaProductos.getSelectionModel().getSelectedItem();

            if (producto == null) {
                info("Carrito", "Selecciona un producto.");
                return;
            }

            if (txtCantidad.getText().trim().isEmpty()) {
                info("Carrito", "Ingresa una cantidad.");
                return;
            }

            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (cantidad <= 0) {
                info("Carrito", "La cantidad debe ser mayor a cero.");
                return;
            }

            DetalleVenta itemExistente = null;
            int cantidadYaEnCarrito = 0;

            for (DetalleVenta item : carrito) {
                if (item.getIdProducto() == producto.getId()) {
                    itemExistente = item;
                    cantidadYaEnCarrito = item.getCantidad();
                    break;
                }
            }

            if (cantidadYaEnCarrito + cantidad > producto.getStock()) {
                info("Carrito", "No hay suficiente stock.");
                return;
            }

            if (itemExistente != null) {
                itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
                tablaCarrito.refresh();
            } else {
                carrito.add(new DetalleVenta(
                        producto.getId(),
                        producto.getMarca(),
                        cantidad,
                        producto.getPrecio()
                ));
            }

            txtCantidad.clear();
            actualizarTotal();
            lblEstado.setText("Estado: producto agregado al carrito");

        } catch (Exception e) {
            error("Error al agregar", e.getMessage());
        }
    }

    /**
     * Elimina del carrito el producto seleccionado.
     */
    @FXML
    private void onEliminarDelCarrito() {
        DetalleVenta item = tablaCarrito.getSelectionModel().getSelectedItem();

        if (item == null) {
            info("Carrito", "Selecciona un producto del carrito.");
            return;
        }

        carrito.remove(item);
        actualizarTotal();
        lblEstado.setText("Estado: producto eliminado del carrito");
    }

    /**
     * Limpia todo el carrito.
     */
    @FXML
    private void onLimpiarCarrito() {
        carrito.clear();
        actualizarTotal();
        lblEstado.setText("Estado: carrito limpiado");
    }

    /**
     * Finaliza la venta actual y descuenta el stock.
     */
    @FXML
    private void onFinalizarVenta() {
        try {
            if (carrito.isEmpty()) {
                info("Venta", "El carrito está vacío.");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar venta");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Deseas finalizar la venta?");

            Optional<ButtonType> respuesta = confirmacion.showAndWait();

            if (respuesta.isEmpty() || respuesta.get() != ButtonType.OK) {
                return;
            }

            for (DetalleVenta item : carrito) {
                Sabritas productoActual = dao.obtenerPorId(item.getIdProducto());

                if (productoActual == null) {
                    info("Venta", "Uno de los productos ya no existe.");
                    return;
                }

                if (item.getCantidad() > productoActual.getStock()) {
                    info("Venta", "Stock insuficiente para: " + item.getMarca());
                    refrescarInventario();
                    return;
                }
            }

            for (DetalleVenta item : carrito) {
                boolean ok = dao.venderSabritas(item.getIdProducto(), item.getCantidad());

                if (!ok) {
                    info("Venta", "No se pudo vender: " + item.getMarca());
                    refrescarInventario();
                    return;
                }
            }

            info("Venta realizada", "Venta completada.\nTotal: $" + String.format("%.2f", calcularTotal()));

            carrito.clear();
            actualizarTotal();
            refrescarInventario();
            lblEstado.setText("Estado: venta finalizada");

        } catch (Exception e) {
            error("Error al finalizar venta", e.getMessage());
        }
    }

    /**
     * Regresa a la pantalla principal.
     */
    @FXML
    private void onVolver() {
        try {
            App.setRoot("primary");
        } catch (Exception e) {
            error("Error al volver", e.getMessage());
        }
    }

    /**
     * Calcula el total del carrito.
     *
     * @return Total acumulado.
     */
    private double calcularTotal() {
        double total = 0;

        for (DetalleVenta item : carrito) {
            total += item.getSubtotal();
        }

        return total;
    }

    /**
     * Actualiza el label del total.
     */
    private void actualizarTotal() {
        lblTotal.setText("Total: $" + String.format("%.2f", calcularTotal()));
    }

    /**
     * Muestra una alerta informativa.
     *
     * @param titulo Título.
     * @param msg Mensaje.
     */
    private void info(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    /**
     * Muestra una alerta de error.
     *
     * @param titulo Título.
     * @param msg Mensaje.
     */
    private void error(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
