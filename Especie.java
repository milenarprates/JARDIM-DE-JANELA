/* cada tipo de planta pode ter um intervalo entre as regas e uma necessidade de luz diferentes */
/* pensei em implementar o padrao observer p notificar as atualizacoes do estado da planta.. */
/* ou o factory, pra toda vez q o jogador quiser plantar uma nova semente */

    public final class Especie { //imutavel
    private final TipoPlanta tipo;
    private final long intervaloRega;
    private final int luzNecessaria;

    public Especie(TipoPlanta tipo, long intervaloRega, int luzNecessaria) {
        this.tipo = tipo;
        this.intervaloRega = intervaloRega;
        this.luzNecessaria = luzNecessaria;
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

}
