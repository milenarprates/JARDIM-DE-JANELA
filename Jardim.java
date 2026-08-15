import java.util.ArrayList;

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
