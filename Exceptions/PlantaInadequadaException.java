package Exceptions;
public class PlantaInadequadaException extends Exception {
    public PlantaInadequadaException(String nome) {
        super("Planta " + nome + " não pode ser colhida: não está na fase adulta.");
    }
}
