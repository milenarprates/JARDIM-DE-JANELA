package Exceptions;

public class FaltaDinheiroException extends Exception {
    public FaltaDinheiroException() {
        super("Dinheiro insuficiente para comprar semente.");
    }
}

