package model;

public class IngredienteRepetidoException extends HamburguesaException{

    public IngredienteRepetidoException(String e) {
        super(e);
        System.out.println("Un Ingrediente no se puede agregar mas de una vez");
        
    }
    
}
