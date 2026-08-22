import java.util.ArrayList;
import java.util.HashSet;

public class Inventario {
    private ArrayList<Planta> sementes;
    private HashSet<Ferramenta> ferramentas;

    public Inventario() {
        this.sementes = new ArrayList<>();
        this.ferramentas = new HashSet<>();

        ferramentas.add(Ferramenta.REGADOR);
        ferramentas.add(Ferramenta.FERTILIZANTE);
    }

    public void adicionarSemente(Planta semente) {
        sementes.add(semente);
    }

    public Planta removerSemente(int indice) {
        return sementes.remove(indice);
    }

    public ArrayList<Planta> getSementes() {
        return sementes;
    }

    public boolean possuiFerramenta(Ferramenta ferramenta) {
        return ferramentas.contains(ferramenta);
    }
}
