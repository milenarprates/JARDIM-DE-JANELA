import java.io.IOError;
import java.util.ArrayList;
import java.util.HashSet;

import Exceptions.JardimCheioException;
import Exceptions.PlantaInadequadaException;
import Exceptions.RegaInvalidaException;
import java.io.IOException;

public class GerenciadorJogo {

    private Jardim jardim;
    private Inventario inventario;

    public GerenciadorJogo() {
        this.jardim = new Jardim();
        this.inventario = new Inventario();
    }

    public void salvarJogo() throws IOException {
        GerenciadorArquivos.escrever(jardim, inventario);
    }

    public void recarregarJogo() throws IOException, ClassNotFoundException {
        this.inventario = GerenciadorArquivos.lerInventario();
        this.jardim = GerenciadorArquivos.lerJardim();
    }

    // planta uma semente (do inventario)
    public void plantarSemente(int indice) throws JardimCheioException {
        Planta semente = inventario.getSementes().get(indice);
        jardim.plantar(semente);
        inventario.removerSemente(indice);
    }

    public Planta colherPlanta(int indice) throws PlantaInadequadaException { // colhe uma semente do jardim
        return jardim.colher(indice);
    }

    public void regarPlanta(int indice) throws RegaInvalidaException { // rega uma planta específica do jardim
        jardim.regar(indice);
    }

    public boolean usarFertilizante(int indice) {
        return jardim.usarFertilizante(indice);
    }

    public void alterarJanela() { // muda o estado da janela. se estiver aberta, fecha, se estiver fechada, abre
        jardim.setJanelaAberta(!(jardim.isJanelaAberta()));
    }

    public void atualizarJardim() { // chama atualizarTodasPlantas
        jardim.atualizarTodasPlantas();
    }

}
