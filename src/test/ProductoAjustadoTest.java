package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ProductoAjustado;

public class ProductoAjustadoTest {

    private ProductoAjustado productoAjustado;

    @BeforeEach
    public void inicializarProducto() throws Exception {
        productoAjustado = new ProductoAjustado("PruebaNombreAjustado", 150, 100);
    }

    @Test
    public void testGetPrecio() {
        assertEquals(150, productoAjustado.getPrecio());
    }

    @Test
    public void testGetNombre() {
        assertEquals("PruebaNombreAjustado", productoAjustado.getNombre());
    }

    @Test
    public void testGenerarTextoFacturaNotNull() {
        assertNotNull(productoAjustado.generarTextoFactura());
    }

    @Test
    public void testGetCalorias() {
        assertEquals(100, productoAjustado.getCalorias());
    }
}
