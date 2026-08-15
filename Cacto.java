public class Cacto extends Planta {

    static final int PERDA_AGUA_CACTO = 5; //1/4 da perda de agua de uma planta normal
    static final int GANHO_LUZ_CACTO = 18; //aproveita bem a luz

    public Cacto(String nome, Especie especie) {
        super(nome, especie);
    }

    @Override
    public void crescer() {
        if (taViva) {
            if (estagio == FaseCrescimento.SEMENTE && qtdRegas >= 4) {
                estagio = FaseCrescimento.MUDA;
            } else if (estagio == FaseCrescimento.MUDA && qtdRegas >= 8) {
                estagio = FaseCrescimento.JOVEM;
            } else if (estagio == FaseCrescimento.JOVEM && qtdRegas >= 12) {
                estagio = FaseCrescimento.ADULTA;
            }
        }
    }

    @Override
    public void precisarAgua() {
        if (taViva) {
            long tempoDesdeUltimaRega = System.currentTimeMillis() - ultimaRega;
            long intervaloRega = especie.getIntervaloRega();

            if (tempoDesdeUltimaRega >= intervaloRega) {
                nivelAgua -= PERDA_AGUA_CACTO;
                if (nivelAgua < 0) {
                    nivelAgua = 0;
                }
            }
        }
    }

    @Override
    public int receberLuz() {
        nivelLuz = Math.min(nivelLuz + GANHO_LUZ_CACTO, MAX_NIVEL_LUZ);
        return nivelLuz;
    }
}