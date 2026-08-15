public class Planta {
    //atributos de classe
    static final int MAX_NIVEL_LUZ = 100;
    static final int MAX_NIVEL_AGUA = 100;
    static final int VALOR_REGA = 20;

    protected String nome;
    protected Especie especie;
    protected FaseCrescimento estagio;
    protected long ultimaRega;
    protected long momentoQueSecou;
    protected int nivelLuz;
    protected int nivelAgua;
    protected int qtdRegas;
    protected boolean taViva;

    public Planta(String nome, Especie especie) {

        this.nome = nome;
        this.especie = especie;
        this.estagio = FaseCrescimento.SEMENTE;
        this.ultimaRega = System.currentTimeMillis();
        this.nivelLuz = 0;
        this.nivelAgua = 0;
        this.qtdRegas = 0;
        this.taViva = true;
    }

    /* getters */

    public String getNome() {
        return nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public FaseCrescimento getEstagio() {
        return estagio;
    }

    public long getUltimaRega() {
        return ultimaRega;
    }

    public int getNivelLuz() {
        return nivelLuz;
    }

    public int getNivelAgua() { return  nivelAgua; }

    public int getQtdRegas() { return qtdRegas; }

    public boolean isTaViva() {
        return taViva;
    }

    /* alguns metodos da planta */

    public void serRegada() {

        if(taViva){
            ultimaRega = System.currentTimeMillis();
            nivelAgua += VALOR_REGA;
            qtdRegas++;

            if(nivelAgua > MAX_NIVEL_AGUA){
                nivelAgua = MAX_NIVEL_AGUA;
            }

        } else {
            // throw new regaInvalida;
        }

    }
    public void precisarAgua() {
        if (!taViva) return;

        long tempoDesdeUltimaRega = System.currentTimeMillis() - ultimaRega;
        long intervalosPassados = tempoDesdeUltimaRega / especie.getIntervaloRega();
        int nivelEsperado = MAX_NIVEL_AGUA - (int) (intervalosPassados * 10);

        nivelAgua = Math.min(nivelAgua, Math.max(nivelEsperado, 0));

        // se o nível de água cair para 0 por x tempo, a planta morre
        if (nivelAgua == 0) {
            momentoQueSecou = System.currentTimeMillis();
        }
    }

    public int receberLuz() {
        // ainda tô pensando nessa parte aqui..
        // talvez seja melhor implementar uma classe janela que vai controlar a quantidade de luz que entra no jardim,
        // e a planta vai receber essa informação
        return 0;
    }

    public void colher() {};

    public void crescer(){

        if(taViva){
            if(estagio == FaseCrescimento.SEMENTE && qtdRegas >= 3){
                estagio = FaseCrescimento.MUDA;
            } else if(estagio == FaseCrescimento.MUDA && qtdRegas >= 6){
                estagio = FaseCrescimento.JOVEM;
            } else if(estagio == FaseCrescimento.JOVEM && qtdRegas >= 9){
                estagio = FaseCrescimento.ADULTA;
            } else if(estagio == FaseCrescimento.ADULTA && qtdRegas >= 12){
                estagio = getEstagio();
            }
        }

    }
    public void exibirInfo() {
        System.out.println("---------------------------------");
        System.out.println("Nome: " + nome);
        System.out.println("Espécie: " + especie.getTipo());
        System.out.println("Estágio de Crescimento: " + estagio);
        System.out.println("Nível de Luz: " + nivelLuz);
        System.out.println("Nível de Água: " + nivelAgua);
        System.out.println("Quantidade de Regas: " + qtdRegas);
    }

}