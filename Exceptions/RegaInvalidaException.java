package Exceptions;
public class RegaInvalidaException extends Exception {
    public RegaInvalidaException(String nome) {
        super("Rega inválida: planta " + nome + "não está viva.");
    }
}
