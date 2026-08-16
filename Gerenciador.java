import java.util.ArrayList;
import java.util.HashSet;

import Exceptions.FaltaDinheiroException;
import Exceptions.JardimCheioException;
import Exceptions.PlantaInadequadaException;
import Exceptions.RegaInvalidaException;

public class Gerenciador {

    // atributos: jardim, lista de sementes, ferramentas (regador e fertilizante), dinheiro    
    private Jardim jardim;
    private ArrayList<Planta> sementes;
    private HashSet<Ferramenta> ferramentas; // como implementar?
    private double dinheiro;

    public Gerenciador() {
        this.jardim = new Jardim();
        this.sementes = new ArrayList<>();
        this.ferramentas = new HashSet<>();
        this.dinheiro = 0.0;

        ferramentas.add(Ferramenta.REGADOR);
        ferramentas.add(Ferramenta.FERTILIZANTE);
    }

    public void plantarSemente(int indice) throws JardimCheioException { // planta uma semente do inventário no jardim
        jardim.plantar(sementes.get(indice));
        sementes.remove(indice);
    }

    public void colherPlanta(int indice) throws PlantaInadequadaException { // colhe uma semente do jardim, incrementa dinehiro
        dinheiro += jardim.colher(indice);
    }

    public void comprarSemente(TipoPlanta tipo, String nome) throws FaltaDinheiroException { // se o jogador tiver dinheiro suficiente, cria uma semente (factory) e compra
        Planta novaSemente = PlantaFactory.plantarSemente(tipo, nome);
        if(dinheiro < novaSemente.getEspecie().getValor()) throw new FaltaDinheiroException();
        dinheiro -= novaSemente.getEspecie().getValor();
        sementes.add(novaSemente);
    }

    public void regarPlanta(int indice) throws RegaInvalidaException { // rega uma planta específica do jardim
        jardim.regar(indice);
    }

    public void alterarJanela() { // muda o estado da janela. se estiver aberta, fecha, se estiver fechada, abre
        jardim.setJanelaAberta(!(jardim.isJanelaAberta()));
    }

    public void atualizarJardim() { // chama atualizarTodasPlantas
        jardim.atualizarTodasPlantas();
    }

}
