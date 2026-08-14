public class JardimCheio extends Exception {
    public JardimCheio(String message) {
        super(message);
    }
}

public class regaInvalida extends Exception { //nao pode serRegada se a planta tiver morta
    public regaInvalida(String message) {
        super(message);
    }
}


