public class Lirio extends Planta {

    public Lirio(String nome, Especie especie) {
        super(nome, especie);
    }

    @Override
    public void crescer() {
        if (!taViva) return;

        if (estagio == FaseCrescimento.SEMENTE && qtdRegas >= 2) {
            estagio = FaseCrescimento.MUDA;
        } else if (estagio == FaseCrescimento.MUDA && qtdRegas >= 4) {
            estagio = FaseCrescimento.JOVEM;
        } else if (estagio == FaseCrescimento.JOVEM && qtdRegas >= 6) {
            estagio = FaseCrescimento.ADULTA;
        }
    }

    @Override
    public String getDicaCrescimento() {
        return "Lírio tem muita sede! Regue-o com frequência para que ele cresça saudável e bonito.";
    }
}