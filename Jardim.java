import java.util.ArrayList;

import Exceptions.JardimCheioException;
import Exceptions.PlantaInadequadaException;
import Exceptions.RegaInvalidaException;

public class Jardim {

    private ArrayList<Planta> plantas;
    private int capacidadeMaxima;
    private boolean janelaAberta; //vai definir se as plantas recebem luz ou não

    public Jardim() {
        this.capacidadeMaxima = 6;
        this.plantas = new ArrayList<>();
        this.janelaAberta = false;
    }

    public void plantar(Planta planta) throws JardimCheioException {
        if (plantas.size() >= capacidadeMaxima) {
            throw new JardimCheioException(capacidadeMaxima);
        }
        plantas.add(planta);
    }

    public Planta colher(int indice) throws PlantaInadequadaException {
        Planta plantaTemp = plantas.get(indice);

        if(plantaTemp.getEstagio() != FaseCrescimento.ADULTA) throw new PlantaInadequadaException(plantaTemp.getNome());
        plantas.remove(indice);

        return plantaTemp;
    }

    public void regar(int indice) throws RegaInvalidaException {
        plantas.get(indice).serRegada();
    }

    // usa fertilizante em uma planta específica do jardim. retorna true se a planta for um tomateiro
    public boolean usarFertilizante(int indice) {
        if(plantas.get(indice) instanceof Tomateiro) {
            Tomateiro tomateiro = (Tomateiro) plantas.get(indice);
            tomateiro.setUsouFertilizante(true);
            return true;
        }
        return false;
    }

    public boolean isJanelaAberta() {
        return janelaAberta;
    }

    public void setJanelaAberta(boolean janelaAberta) {
        this.janelaAberta = janelaAberta;
    }

    public void atualizarTodasPlantas() {
        for (Planta planta : plantas) {
            planta.absorveAgua();

            if (janelaAberta) {
                planta.recebeLuz();
            }

            planta.crescer();
        }
    }
}
