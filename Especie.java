
public final class Especie { //imutavel
    private final TipoPlanta tipo;
    private final long intervaloRega;
    private final int luzNecessaria;
    private final int perdaAguaPorIntervalo;
    private final int ganhoLuzPorColeta;
    private final double valor;

    public Especie(TipoPlanta tipo, long intervaloRega, int luzNecessaria,
                   int perdaAguaPorIntervalo, int ganhoLuzPorColeta, double valor) {
        this.tipo = tipo;
        this.intervaloRega = intervaloRega;
        this.luzNecessaria = luzNecessaria;
        this.perdaAguaPorIntervalo = perdaAguaPorIntervalo;
        this.ganhoLuzPorColeta = ganhoLuzPorColeta;
        this.valor = valor;
    }

    public TipoPlanta getTipo() {
        return tipo;
    }

    public long getIntervaloRega() {
        return intervaloRega;
    }

    public int getLuzNecessaria() {
        return luzNecessaria;
    }

    public int getPerdaAguaPorIntervalo() {
        return perdaAguaPorIntervalo;
    }

    public int getGanhoLuzPorColeta() {
        return ganhoLuzPorColeta;
    }

    public double getValor() {
        return valor;
    }
}