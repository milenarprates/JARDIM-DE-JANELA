package Exceptions;
public class PlantaInadequadaException extends Exception {
    public PlantaInadequadaException(String nome) {
        super("Planta " + nome + " não pode ser colhida nem descartada: ainda está em crescimento.");
    }
}
