package model;

public class ProductoRepetidoException extends HamburguesaException{

    public ProductoRepetidoException(String e) {
        super(e);
        System.out.println("No se puede agregar el mismo producto dos veces");
    }
    
}