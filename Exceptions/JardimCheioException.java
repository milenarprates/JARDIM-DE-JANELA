package Exceptions;
public class JardimCheioException extends Exception {
    public JardimCheioException(int capacidadeMaxima) {
        super("Jardim cheio: capacidade máxima [" + capacidadeMaxima + "] atingida.");
    }
}
