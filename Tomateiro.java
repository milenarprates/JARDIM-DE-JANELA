public class Tomateiro extends Planta {

    private boolean usouFertilizante;

    public Tomateiro(String nome, Especie especie) {
        super(nome, especie);
        this.usouFertilizante = false;
    }

    public void setUsouFertilizante(boolean usouFertilizante) {
        this.usouFertilizante = usouFertilizante;
    }
    @Override
    public void crescer() {
        if(!taViva) return;

        if(usouFertilizante == true && estagio == FaseCrescimento.SEMENTE) {
            estagio = FaseCrescimento.JOVEM;
            usouFertilizante = false;
        } else if(usouFertilizante == true && estagio == FaseCrescimento.JOVEM) {
            estagio = FaseCrescimento.ADULTA;
            usouFertilizante = false;
        }
    }

    @Override
    public String getDicaCrescimento() {
        return "Ouvi dizer que se você fertilizar o tomateiro duas vezes ele cresce super rápido!";
    }
}
