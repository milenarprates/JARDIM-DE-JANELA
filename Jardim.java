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

    public double colher(int indice) throws PlantaInadequadaException {
        Planta plantaTemp = plantas.get(indice);
        if(plantaTemp.getEstagio() != FaseCrescimento.ADULTA) throw new PlantaInadequadaException(plantaTemp.getNome());
        plantas.remove(indice);
        return plantaTemp.getEspecie().getValor() * 1.25; // ao colher, ganha o valor da planta + lucro
    }

    public void regar(int indice) throws RegaInvalidaException {
        plantas.get(indice).serRegada();
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
