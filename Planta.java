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
    protected int idadeDias;
    protected boolean taViva;

    public Planta(String nome, Especie especie, FaseCrescimento estagio, long ultimaRega,
                  int nivelLuz, int nivelAgua, int idadeDias, boolean taViva) {
        this.nome = nome;
        this.especie = especie;
        this.estagio = FaseCrescimento.SEMENTE;
        this.ultimaRega = System.currentTimeMillis();
        this.nivelLuz = 0;
        this.nivelAgua = 0;
        this.idadeDias = 1;
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

    public int getQtdRegas() { return qtdRegas; }

    public int getNivelLuz() {
        return nivelLuz;
    }

    public int getNivelAgua() { return  nivelAgua; }

    public int getIdadeDias() {
        return idadeDias;
    }

    public boolean isTaViva() {
        return taViva;
    }

    /* alguns metodos da planta */

    public void serRegada() {

        if(taViva){
            ultimaRega = System.currentTimeMillis();
            nivelAgua += VALOR_REGA;

            if(nivelAgua > MAX_NIVEL_AGUA){
                nivelAgua = MAX_NIVEL_AGUA;
            }

        } else {
            // throw new regaInvalida("A planta está morta e não pode ser regada.");
        }

    }

    public int receberLuz(){
        // ainda tô pensando nessa parte aqui..
        // talvez seja melhor implementar uma classe janela que vai controlar a quantidade de luz que entra no jardim,
        // e a planta vai receber essa informação
        return 0;
    }

    public void colher(){

    }

    public void crescer(){

        if(taViva){
            idadeDias++;
        }

        switch(idadeDias){
            case 1:
                estagio = FaseCrescimento.SEMENTE;
                break;
            case 5:
                estagio = FaseCrescimento.MUDA;
                break;
            case 10:
                estagio = FaseCrescimento.JOVEM;
                break;
            case 15:
                estagio = FaseCrescimento.ADULTA;
                break;
            default:
                estagio = FaseCrescimento.MORTA;
                taViva = false;
        }

    }
    public void exibirInfo(){
        System.out.println("Nome: " + nome);
        System.out.println("Espécie: " + especie.getTipo());
        System.out.println("Estágio de Crescimento: " + estagio);
        System.out.println("Nível de Luz: " + nivelLuz);
        System.out.println("Nível de Água: " + nivelAgua);
        System.out.println("Idade (dias): " + idadeDias);
    }

}