package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ProductoMenu;

public class ProductoMenuTest {

    private ProductoMenu productoMenu;

    @BeforeEach
    public void inicializarMenu() throws Exception {
        productoMenu = new ProductoMenu("PruebaNombre", 100, 100);
    }
	
    @Test
    public void testGetPrecio() {
        assertEquals(100, productoMenu.getPrecio());
    }

    @Test
    public void testGetNombre() {
        assertEquals("PruebaNombre", productoMenu.getNombre());
    }

    @Test
    public void testGenerarTextoFacturaNotNull() {
        assertNotNull(productoMenu.generarTextoFactura());
    }

    @Test
    public void testGetCal() {
        assertEquals(100, productoMenu.getCalorias());
    }
}
