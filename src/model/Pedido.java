package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Pedido {

    private int idPedido;
    private String nombreCliente;
    private String direccionCliente;
    private float precioNeto;
    private int calorias;
    private ArrayList<Producto> productos;
    private Hashtable<Integer, String> facturasTabla;

    public Pedido(String nombreCliente, String direccionCliente) {
        this.productos = new ArrayList<>();
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.precioNeto = 0;
        this.facturasTabla = new Hashtable<>();
    }

    public int getIdPedido() {
        return this.idPedido;
    }

    public void setId(int id) {
        this.idPedido = id;
    }

    public void agregarProducto(Producto nuevoItem) throws ProductoTotal150 {
        int nuevoPrecio = (int) (nuevoItem.getPrecio() + this.precioNeto);
        validarLimitePrecio(nuevoPrecio);
        this.productos.add(nuevoItem);
        this.precioNeto += nuevoItem.getPrecio();
        this.calorias += nuevoItem.getCalorias();
    }

    private void validarLimitePrecio(int nuevoPrecio) throws ProductoTotal150 {
        if (nuevoPrecio > 150000) {
            throw new ProductoTotal150("No puede añadir este producto: " + productos.get(productos.size() - 1).getNombre());
        }
    }

    public String getNombreCliente() {
        return this.nombreCliente;
    }

    public String getDireccionCliente() {
        return this.direccionCliente;
    }

    public ArrayList<Producto> getProductos() {
        return this.productos;
    }

    public float getPrecioNetoPedido() {
        return this.precioNeto;
    }

    public double getPrecioTotalPedido() {
        return this.precioNeto + getPrecioIVAPedido();
    }

    public int getTotalCalorias() {
        return this.calorias;
    }

    public double getPrecioIVAPedido() {
        return this.precioNeto * 0.19;
    }

    public String generarTextoFactura() {
        StringBuilder factura = new StringBuilder();
        factura.append("Pedido #").append(this.idPedido).append("\n");
        factura.append("Nombre: ").append(this.nombreCliente).append("\n");
        factura.append("Dirección: ").append(this.direccionCliente).append("\n\n");
        factura.append("PRODUCTOS").append("\n");

        int indice = 1;
        for (Producto producto : productos) {
            factura.append(indice++).append(". ").append(producto.getNombre()).append(" || $")
                    .append(producto.getPrecio()).append(" || cal:").append(producto.getCalorias()).append("\n");
        }

        factura.append("\nIVA: $").append(getPrecioIVAPedido()).append("\n");
        factura.append("Total Neto: $").append(getPrecioNetoPedido()).append("\n");
        factura.append("Total Pedido: $").append(getPrecioTotalPedido()).append("\n");
        factura.append("Total calorias: ").append(getTotalCalorias()).append("\n");

        return factura.toString();
    }

    public void guardarFactura(File archivo) {
        facturasTabla.put(this.idPedido, generarTextoFactura());
        try (FileWriter bw = new FileWriter(archivo)) {
            bw.write(generarTextoFactura());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
