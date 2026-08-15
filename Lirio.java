public class Lirio extends Planta {

    public Lirio(String nome, Especie especie) {
        super(nome, especie);
    }

    @Override
    public void crescer() {
        if (!taViva) return;

        if (estagio == FaseCrescimento.SEMENTE && qtdRegas >= 2) {
            estagio = FaseCrescimento.MUDA;
        } else if (estagio == FaseCrescimento.MUDA && qtdRegas >= 6) {
            estagio = FaseCrescimento.JOVEM;
        } else if (estagio == FaseCrescimento.JOVEM && qtdRegas >= 9) {
            estagio = FaseCrescimento.ADULTA;
        } else {
            System.out.println("O lírio ainda não está pronto para crescer. Regue mais vezes!");
        }
    }
}