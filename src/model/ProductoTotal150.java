package model;

public class ProductoTotal150 extends HamburguesaException {

    public ProductoTotal150(String e) {
        super(e);
        System.out.println("El pedido no puede superar los $150000 COP");
    }
    
}
