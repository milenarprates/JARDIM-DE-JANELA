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

        if (estagio == FaseCrescimento.SEMENTE && idadeMs >= 15_000) {
            estagio = FaseCrescimento.MUDA;
        } else if (estagio == FaseCrescimento.MUDA && idadeMs >= 35_000) {
            estagio = FaseCrescimento.JOVEM;
        } else if (estagio == FaseCrescimento.JOVEM && idadeMs >= 60_000) {
            estagio = FaseCrescimento.ADULTA;
        }
    }

    @Override
    public String getDicaCrescimento() {
        return "Os cactos crescem sozinhos, no próprio tempo. Deixe-os em paz e eles florescerão!";
    }
}

