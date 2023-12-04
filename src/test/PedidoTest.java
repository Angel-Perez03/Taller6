package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Pedido;
import model.ProductoMenu;
import model.ProductoTotal150;


public class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    public void setUp() throws Exception {
        pedido = new Pedido("Angel", "Calle 2 #16a-38");
    }
    
    @Test
    public void testGetIdPedido() {
        pedido.setId(1);
        assertEquals(1, pedido.getIdPedido());
    }

    @Test
    public void testAgregarProductoExcepcion() {
        ProductoMenu productoMenu = new ProductoMenu("PruebaNombre", 151000, 100);
        assertThrows(ProductoTotal150.class, () -> pedido.agregarProducto(productoMenu));
    }

    @Test
    public void testGetProductos() throws ProductoTotal150 {
        ProductoMenu productoMenu = new ProductoMenu("PruebaNombre", 140000, 100);
        pedido.agregarProducto(productoMenu);
        ArrayList<ProductoMenu> items = new ArrayList<ProductoMenu>();
        items.add(productoMenu);
        assertEquals(items, pedido.getProductos());
    }

    @Test
    public void testGetNombreCliente() {
        assertEquals("Angel", pedido.getNombreCliente());
    }

    @Test
    public void testGetDireccionCliente() {
        assertEquals("Calle 2 #16a-38", pedido.getDireccionCliente());
    }

    @Test
    public void testGetPrecioNeto() throws ProductoTotal150 {
        ProductoMenu productoMenu = new ProductoMenu("PruebaNombre", 140000, 100);
        pedido.agregarProducto(productoMenu);
        assertEquals(140000, pedido.getPrecioNetoPedido());
    }

    @Test
    public void testGetPrecioTotal() throws ProductoTotal150 {
        ProductoMenu productoMenu = new ProductoMenu("PruebaNombre", 140000, 100);
        pedido.agregarProducto(productoMenu);
        assertEquals(166600, pedido.getPrecioTotalPedido());
    }

    @Test
    public void testGetCal() throws ProductoTotal150{
        ProductoMenu productoMenu = new ProductoMenu("PruebaNombre", 140000, 100);
        pedido.agregarProducto(productoMenu);
        assertEquals(100, pedido.getTotalCalorias());
    }

    @Test
    public void testGetPrecioIva() throws ProductoTotal150{
        ProductoMenu productoMenu = new ProductoMenu("PruebaNombre", 140000, 100);
        pedido.agregarProducto(productoMenu);
        assertEquals(26600, pedido.getPrecioIVAPedido());
    }

    @Test
    public void testGenerarTextoFactura() throws ProductoTotal150{
        ProductoMenu productoMenu = new ProductoMenu("PruebaNombre", 140000, 100);
        pedido.agregarProducto(productoMenu);
        pedido.setId(0);
        String msj = "Pedido #" + 0;
		msj += "\n";
		msj += "Nombre: " + "Angel";
		msj += "\n";
		msj += "Dirección: " + "Calle 2 #16a-38";
		msj += "\n";
		msj += "\n";
		msj += "PRODUCTOS";
		msj += "\n";
		
        msj += 1 + ". " + "PruebaNombre" + " || $" + 140000.0 + " || cal:" + 100;
        msj += "\n";
		
		msj += "\n";
		msj += "IVA: $" + 26600.0;
		msj += "\n";
		msj += "Total Neto: $" + 140000.0;
		msj += "\n";
		msj += "Total Pedido: $" + 166600.0;
		msj += "\n";
		msj += "Total calorias: " + 100;
		msj += "\n";

        assertEquals(msj, pedido.generarTextoFactura());

    }

    @Test
    public void testGuardarFactura() throws IOException, ProductoTotal150 {
        
        ProductoMenu productoMenu = new ProductoMenu("especial", 24000, 52);
        pedido.setId(1);
        pedido.agregarProducto(productoMenu);
        File archivo = new File("./Data/" + this.pedido.getIdPedido() + ".txt");
        pedido.guardarFactura(archivo);

        File facturaGuardada = new File("./Data/" + this.pedido.getIdPedido() + ".txt");
        assertTrue(facturaGuardada.exists());
    }
    
}
