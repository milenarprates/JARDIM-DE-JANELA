import Exceptions.RegaInvalidaException;

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

    //basicamenta aumenta o nivel de agua da planta e atualiza a hora da ultima rega
    public void serRegada() throws RegaInvalidaException {

        if(taViva){
            ultimaRega = System.currentTimeMillis();
            nivelAgua += VALOR_REGA;
            qtdRegas++;

            if(nivelAgua > MAX_NIVEL_AGUA){
                nivelAgua = MAX_NIVEL_AGUA;
            }

        } else {
            throw new RegaInvalidaException(nome);
        }

    }

    //decrementa o nivel de agua
    public void absorveAgua() {
        if (!taViva) return;

        long tempoDesdeUltimaRega = System.currentTimeMillis() - ultimaRega;
        long intervalosPassados = tempoDesdeUltimaRega / especie.getIntervaloRega();

        nivelAgua -= intervalosPassados * especie.getPerdaAguaPorIntervalo();
        nivelAgua = Math.max(nivelAgua, 0);
        ultimaRega += intervalosPassados * especie.getIntervaloRega();
    }

    public int recebeLuz(){
        nivelLuz = Math.min(nivelLuz + especie.getGanhoLuzPorColeta(), MAX_NIVEL_LUZ);
        return nivelLuz;
    }

    public boolean precisaSerRegada() {
        return nivelAgua < especie.getPerdaAguaPorIntervalo();
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