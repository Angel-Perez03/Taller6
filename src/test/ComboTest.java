package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Combo;
import model.Producto;
import model.ProductoMenu;

public class ComboTest {

    private Combo combito;
    private ProductoMenu productoMenu;
    ArrayList<Producto> items;

    @BeforeEach
    public void setUp() throws Exception {
        combito = new Combo("PruebaCombo", 10);
        productoMenu = new ProductoMenu("PruebaNombre", 151000, 100);
    }

    @Test
    public void testGetPrecio() {
        assertEquals(10, combito.getPrecio());
    }

    @Test
    public void testGetItems() {
        items = new ArrayList<Producto>();
        items.add(productoMenu);
        combito.agregarItemACombo(productoMenu);
        assertEquals(items, combito.getItems());
    }

    @Test
    public void testGetNombre() {
        assertEquals("PruebaCombo", this.combito.getNombre());
    }

    @Test
    public void testGenerarTextoFactura() {
        assertEquals(null, this.combito.generarTextoFactura());
    }

    @Test
    public void testGetCal() {
        combito.agregarItemACombo(productoMenu);
        assertEquals(100, this.combito.getCalorias());
    }

    @Test
    public void testGenerarFactura() {
        assertEquals(null, this.combito.generarTextoFactura());
    }
}
