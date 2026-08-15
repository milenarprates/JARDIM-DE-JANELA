public abstract class Planta {

    //atributos de classe
    static final int MAX_NIVEL_LUZ = 100;
    static final int MAX_NIVEL_AGUA = 100;
    static final int VALOR_REGA = 20;

    protected String nome;
    protected Especie especie;
    protected FaseCrescimento estagio;
    protected long ultimaRega;
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

    //calcula o nivel de agua esperado da planta com base no tempo desde a ultima rega e na especie da planta
    // ... de acordo com o intervalo de rega e a perda de agua por intervalo da especie
    public void precisarAgua() {
        if (!taViva) return;

        long tempoDesdeUltimaRega = System.currentTimeMillis() - ultimaRega;
        long intervalosPassados = tempoDesdeUltimaRega / especie.getIntervaloRega();
        int nivelEsperado = MAX_NIVEL_AGUA - (int) (intervalosPassados * especie.getPerdaAguaPorIntervalo());

        nivelAgua = Math.min(nivelAgua, Math.max(nivelEsperado, 0));
    }

    public int recebeLuz(){
        nivelLuz = Math.min(nivelLuz + especie.getGanhoLuzPorColeta(), MAX_NIVEL_LUZ);
        return nivelLuz;
    }

    public void colher(){

    }

    //cada subtipo de planta implementa uma lógica de crescimento diferente
    public abstract void crescer();

    public void exibeInfo() {
        System.out.println("---------------------------------");
        System.out.println("Nome: " + nome);
        System.out.println("Espécie: " + especie.getTipo());
        System.out.println("Estágio de Crescimento: " + estagio);
        System.out.println("Nível de Luz: " + nivelLuz);
        System.out.println("Nível de Água: " + nivelAgua);
        System.out.println("Quantidade de Regas: " + qtdRegas);
    }

}