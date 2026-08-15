public class Tomateiro extends Planta {

    private boolean usouFertilizante;

    public Tomateiro(String nome, Especie especie) {
        super(nome, especie);
        this.usouFertilizante = false;
    }

    @Override
    public void crescer() {
        if(!taViva) return;

        if(usouFertilizante == true && estagio == FaseCrescimento.SEMENTE) {
            estagio = FaseCrescimento.JOVEM;
        } else if(usouFertilizante == true && estagio == FaseCrescimento.JOVEM) {
            estagio = FaseCrescimento.ADULTA;
        }
    }
}
