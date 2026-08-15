public class Cacto extends Planta {

    private long tempoPlantio; //guarda a hora em que a planta foi plantada

    public Cacto(String nome, Especie especie) {
        super(nome, especie);
        this.tempoPlantio = System.currentTimeMillis();
    }

    //nao precisa regar, cresce sozinho baseado no tempo de vida
    @Override
    public void crescer() {
        if (!taViva) return;

        long idadeMs = System.currentTimeMillis() - tempoPlantio;

        if (estagio == FaseCrescimento.SEMENTE && idadeMs >= 5_000) {
            estagio = FaseCrescimento.MUDA;
        } else if (estagio == FaseCrescimento.MUDA && idadeMs >= 20_000) {
            estagio = FaseCrescimento.JOVEM;
        } else if (estagio == FaseCrescimento.JOVEM && idadeMs >= 30_000) {
            estagio = FaseCrescimento.ADULTA;
        }


    }
}
