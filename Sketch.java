import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.util.HashMap;

import Exceptions.JardimCheioException;
import Exceptions.PlantaInadequadaException;
import Exceptions.RegaInvalidaException;

/*

 * OBS: aqui escolher no inventário já cria a planta (PlantaFactory) e planta direto no Jardim depois do nome ser digitado
 * não passa pelo ArrayList<Planta> sementes do Inventario.

 */
public class Sketch extends PApplet {

    static final int LARGURA = 900;
    static final int ALTURA = 650;
    static final int LARGURA_SIDEBAR = 90;
    static final int TAM_SLOT = 460; // tamanho de cada slot de planta no jardim
    static final int COLUNAS = 3;

    enum Estado { JARDIM, INVENTARIO, NOME_PLANTA, ACOES_PLANTA, INFO_PLANTA }

    GerenciadorJogo gerenciador;
    Estado estadoAtual = Estado.JARDIM;

    TipoPlanta[] tiposDisponiveis = TipoPlanta.values();
    int indiceTipoSelecionado = 0;

    TipoPlanta tipoEmCriacao;
    StringBuilder nomeDigitado = new StringBuilder();

    int indicePlantaSelecionada = -1;

    // Variáveis para animação de itens (regador, fertilizante)
    int tempoAnimacaoItem = 0;
    int indicePlantaAnimada = -1;
    String spriteAnimacaoAtual = "";
    HashMap<String, PImage> sprites = new HashMap<>();

    static final String[] NOMES_SPRITES = {
            "CactoAdulto", "CactoJovem", "CactoMuda", "CactoSemente",
            "Fertilizante", "IconeInventario", "JanelaAberta", "JanelaFechada",
            "LirioAdulto", "LirioJovem", "LirioMuda", "LirioSemente",
            "Regador", "SementeCacto", "SementeLirio", "SementeTomate",
            "TomateAdulto", "TomateJovem", "TomateSemente"
    };

    public void settings() {
        size(LARGURA, ALTURA);
        smooth(8);
    }

    public void setup() {
        for (String nome : NOMES_SPRITES) {
            sprites.put(nome, loadImage("sprites/" + nome + ".png"));
        }

        gerenciador = new GerenciadorJogo();
        if (GerenciadorArquivos.existeArquivo()) {
            try {
                gerenciador.recarregarJogo();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Não foi possível carregar o jogo salvo: " + e.getMessage());
            }
        }
    }

    public void draw() {
        background(235, 245, 235);
        gerenciador.atualizarJardim();

        desenharSidebar();
        desenharJanelaEPlantas();

        desenharAnimacoes();

        switch (estadoAtual) {
            case INVENTARIO:
                desenharInventario();
                break;
            case NOME_PLANTA:
                desenharCampoNome();
                break;
            case ACOES_PLANTA:
                desenharMenuAcoes();
                break;
            case INFO_PLANTA:
                desenharInfoPlanta();
                break;
            default:
                break;
        }
    }

    public void dispose() {
        try {
            gerenciador.salvarJogo();
        } catch (IOException e) {
            System.err.println("Não foi possível salvar o jogo: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // DESENHO
    // ---------------------------------------------------------------

    void desenharSidebar() {
        fill(200, 220, 200);
        noStroke();
        rect(0, 0, LARGURA_SIDEBAR, ALTURA);

        PImage icone = sprites.get("IconeInventario");
        image(icone, -35, 0, 150, 150);

        fill(0); // Cor preta
        textAlign(CENTER);
        textSize(14); // Tamanho da fonte
        text("Inventário", LARGURA_SIDEBAR / 2f, 120);
    }

    void desenharJanelaEPlantas() {
        PImage fundo = gerenciador.getJardim().isJanelaAberta()
                ? sprites.get("JanelaAberta")
                : sprites.get("JanelaFechada");
        image(fundo, LARGURA_SIDEBAR, 0, LARGURA - LARGURA_SIDEBAR, ALTURA);

        for (int i = 0; i < gerenciador.getJardim().getPlantas().size(); i++) {
            Planta planta = gerenciador.getJardim().getPlantas().get(i);
            int[] xy = posicaoSlot(i);

            PImage img = imagemDaPlanta(planta);
            if (img != null) {
                if (planta.getEstagio() == FaseCrescimento.MURCHA) {
                    tint(160); // planta murcha aparece meio "apagada"
                }
                image(img, xy[0], xy[1], TAM_SLOT - 10, TAM_SLOT - 10);
                noTint();
            }

            fill(0);
            textAlign(CENTER);
            textSize(12);
            text(planta.getNome(), xy[0] + (TAM_SLOT - 10) / 2f, xy[1] + TAM_SLOT);

            if (planta.isTaViva() && planta.precisaSerRegada()) {
                desenharGota(xy[0] + 120, xy[1] + 220);
            }
        }
    }

    // gota simples desenhada com formas do Processing (sem precisar de sprite novo)
    void desenharGota(float cx, float cy) {
        noStroke();
        fill(50, 140, 230);
        ellipse(cx, cy + 6, 20, 20);
        triangle(cx - 8, cy + 2, cx + 8, cy + 2, cx, cy - 10);
        fill(200, 230, 255);
        ellipse(cx - 4, cy + 4, 5, 5);
    }

    void desenharAnimacoes() {
        // Só desenha se houver tempo restante e a planta for válida
        if (tempoAnimacaoItem > 0 && indicePlantaAnimada >= 0 && indicePlantaAnimada < gerenciador.getJardim().getPlantas().size()) {
            int[] xy = posicaoSlot(indicePlantaAnimada);
            PImage imgSprite = sprites.get(spriteAnimacaoAtual);

            if (imgSprite != null) {
                float balancoY = sin(tempoAnimacaoItem * 0.3f) * 10;

                // Desenha o item centralizado e acima da planta
                image(imgSprite, xy[0] + 200, xy[1] + 60 + balancoY, 150, 150);
            }

            tempoAnimacaoItem--;
        }
    }

    int[] posicaoSlot(int indice) {
        int coluna = indice % COLUNAS;
        int espacamentoX = 145;

        int x = LARGURA_SIDEBAR + 20 + (coluna * espacamentoX);
        int linhaDoChao = 630;

        int y = linhaDoChao - TAM_SLOT;

        return new int[]{x, y};
    }

    // mapeia Planta -> chave do hashMap de sprites
    PImage imagemDaPlanta(Planta planta) {
        String tipo = nomeTipo(planta.getEspecie().getTipo());
        String estagio = nomeEstagio(planta.getEstagio());
        if (estagio == null) {
            estagio = "Semente";
        }
        return sprites.get(tipo + estagio);
    }

    String nomeTipo(TipoPlanta tipo) {
        switch (tipo) {
            case CACTO: return "Cacto";
            case LIRIO: return "Lirio";
            case TOMATEIRO: return "Tomate";
            default: return "";
        }
    }

    String nomeEstagio(FaseCrescimento estagio) {
        switch (estagio) {
            case SEMENTE: return "Semente";
            case MUDA: return "Muda";
            case JOVEM: return "Jovem";
            case ADULTA: return "Adulto";
            case MURCHA: return null;
            default: return "";
        }
    }

    void desenharInventario() {
        fill(255, 255, 255, 230);
        stroke(0);
        rect(LARGURA_SIDEBAR + 30, 30, 260, 200, 8);

        textAlign(LEFT);
        textSize(14);
        fill(0);
        text("Escolha uma semente (use setas + enter):", LARGURA_SIDEBAR + 45, 55);

        for (int i = 0; i < tiposDisponiveis.length; i++) {
            int y = 70 + i * 50;
            boolean selecionado = (i == indiceTipoSelecionado);

            if (selecionado) {
                fill(210, 235, 210);
                noStroke();
                rect(LARGURA_SIDEBAR + 40, y - 5, 240, 44, 6);
            }

            PImage icone = sprites.get("Semente" + nomeTipo(tiposDisponiveis[i]));
            image(icone, LARGURA_SIDEBAR + 45, y - 5, 40, 40);

            fill(0);
            text(nomeTipo(tiposDisponiveis[i]), LARGURA_SIDEBAR + 95, y + 20);
        }
    }

    void desenharCampoNome() {
        fill(255, 255, 255, 230);
        stroke(0);
        rect(LARGURA_SIDEBAR + 30, 30, 300, 90, 8);

        fill(0);
        textAlign(LEFT);
        textSize(14);
        text("Nome da planta (enter confirma, esc cancela):", LARGURA_SIDEBAR + 45, 55);

        fill(245);
        stroke(150);
        rect(LARGURA_SIDEBAR + 45, 65, 270, 30, 4);

        fill(0);
        text(nomeDigitado.toString() + "|", LARGURA_SIDEBAR + 52, 85);
    }

    void desenharMenuAcoes() {
        if (indicePlantaSelecionada < 0 || indicePlantaSelecionada >= gerenciador.getJardim().getPlantas().size()) {
            estadoAtual = Estado.JARDIM;
            return;
        }
        Planta planta = gerenciador.getJardim().getPlantas().get(indicePlantaSelecionada);
        int[] xy = posicaoSlot(indicePlantaSelecionada);
        String[] opcoes = opcoesDeAcao(planta);

        int alturaMenu = opcoes.length * 28 + 10;
        int menuY = xy[1] - alturaMenu;

        fill(255, 255, 255, 235);
        stroke(0);
        rect(xy[0], menuY, 150, alturaMenu, 6);

        textAlign(LEFT);
        textSize(13);
        for (int i = 0; i < opcoes.length; i++) {
            fill(0);
            text(opcoes[i], xy[0] + 10, menuY + 20 + i * 28);
        }
    }

    // opções variam conforme a espécie/estágio
    String[] opcoesDeAcao(Planta planta) {
        java.util.List<String> opcoes = new java.util.ArrayList<>();
        if (!planta.isTaViva()) {
            opcoes.add("Descartar");
        } else {
            opcoes.add("Regar");
            if (planta instanceof Tomateiro) opcoes.add("Fertilizar");
            if (planta.getEstagio() == FaseCrescimento.ADULTA) opcoes.add("Colher");
        }
        opcoes.add("Ver informações");
        opcoes.add("Fechar (x)");
        return opcoes.toArray(new String[0]);
    }

    void desenharInfoPlanta() {
        if (indicePlantaSelecionada < 0 || indicePlantaSelecionada >= gerenciador.getJardim().getPlantas().size()) {
            estadoAtual = Estado.JARDIM;
            return;
        }
        Planta planta = gerenciador.getJardim().getPlantas().get(indicePlantaSelecionada);

        fill(255, 255, 255, 240);
        stroke(0);
        rect(300, 200, 300, 300, 8);

        fill(0);
        textAlign(LEFT);
        textSize(14);
        int x = 315, y = 225, salto = 24;
        text("Nome: " + planta.getNome(), x, y);
        text("Espécie: " + planta.getEspecie().getTipo(), x, y + salto);
        text("Estágio: " + planta.getEstagio(), x, y + salto * 2);
        text("Nível de luz: " + planta.getNivelLuz(), x, y + salto * 3);
        text("Nível de água: " + planta.getNivelAgua(), x, y + salto * 4);
        text("Qtd. de regas: " + planta.getQtdRegas(), x, y + salto * 5);
        text("Viva: " + (planta.isTaViva() ? "sim" : "não"), x, y + salto * 6);

        textSize(12);
        text(planta.getDicaCrescimento(), x, y + salto * 7, 270, 60);
        textSize(14);

        fill(120, 0, 0);
        textAlign(RIGHT);
        text("[x] fechar", 585, 210);
    }

    // -----------------------------------
    // INTERAÇÃO
    // -----------------------------------

    public void mousePressed() {
        switch (estadoAtual) {
            case JARDIM:
                if (mouseX >= 0 && mouseX <= LARGURA_SIDEBAR && mouseY >= 10 && mouseY <= 150) {
                    abrirInventario();
                    return;
                }

                for (int i = gerenciador.getJardim().getPlantas().size() - 1; i >= 0; i--) {
                    int[] xy = posicaoSlot(i);

                    if (mouseX >= xy[0] + 80 && mouseX <= xy[0] + TAM_SLOT - 80
                            && mouseY >= xy[1] + 80 && mouseY <= xy[1] + TAM_SLOT - 20) {
                        indicePlantaSelecionada = i;
                        estadoAtual = Estado.ACOES_PLANTA;
                        return;
                    }
                }
                break;

            case INVENTARIO:
                for (int i = 0; i < tiposDisponiveis.length; i++) {
                    int y = 70 + i * 50;
                    if (mouseX >= LARGURA_SIDEBAR + 40 && mouseX <= LARGURA_SIDEBAR + 280
                            && mouseY >= y - 5 && mouseY <= y + 39) {
                        indiceTipoSelecionado = i;
                        confirmarEscolhaTipo();
                        return;
                    }
                }
                break;

            case ACOES_PLANTA:
                Planta planta = gerenciador.getJardim().getPlantas().get(indicePlantaSelecionada);
                String[] opcoes = opcoesDeAcao(planta);
                int[] xy = posicaoSlot(indicePlantaSelecionada);

                int alturaMenu = opcoes.length * 28 + 10;
                int menuY = xy[1] - alturaMenu;

                for (int i = 0; i < opcoes.length; i++) {
                    int yOpcao = menuY + 20 + i * 28;
                    if (mouseX >= xy[0] && mouseX <= xy[0] + 150
                            && mouseY >= yOpcao - 15 && mouseY <= yOpcao + 10) {
                        executarAcao(opcoes[i]);
                        return;
                    }
                }
                // Se clicou fora das opções, fecha o menu
                estadoAtual = Estado.JARDIM;
                break;
            default:
                break;
        }
    }

    void executarAcao(String opcao) {
        try {
            switch (opcao) {
                case "Regar":
                    gerenciador.regarPlanta(indicePlantaSelecionada);
                    indicePlantaAnimada = indicePlantaSelecionada;
                    spriteAnimacaoAtual = "Regador";
                    tempoAnimacaoItem = 45;
                    estadoAtual = Estado.JARDIM;
                    break;
                case "Fertilizar":
                    gerenciador.usarFertilizante(indicePlantaSelecionada);
                    indicePlantaAnimada = indicePlantaSelecionada;
                    spriteAnimacaoAtual = "Fertilizante"; // Usa o sprite de fertilizante!
                    tempoAnimacaoItem = 45;
                    estadoAtual = Estado.JARDIM;
                    break;
                case "Colher":
                case "Descartar":
                    gerenciador.colherPlanta(indicePlantaSelecionada);
                    indicePlantaSelecionada = -1;
                    estadoAtual = Estado.JARDIM;
                    break;
                case "Ver informações":
                    estadoAtual = Estado.INFO_PLANTA;
                    break;
                default: // "Fechar (x)"
                    estadoAtual = Estado.JARDIM;
                    break;
            }
        } catch (RegaInvalidaException | PlantaInadequadaException e) {
            System.err.println(e.getMessage());
            estadoAtual = Estado.JARDIM;
        }
    }

    public void keyPressed() {
        switch (estadoAtual) {
            case INVENTARIO:
                if (keyCode == UP) {
                    indiceTipoSelecionado = (indiceTipoSelecionado - 1 + tiposDisponiveis.length) % tiposDisponiveis.length;
                } else if (keyCode == DOWN) {
                    indiceTipoSelecionado = (indiceTipoSelecionado + 1) % tiposDisponiveis.length;
                } else if (keyCode == ENTER || keyCode == RETURN) {
                    confirmarEscolhaTipo();
                } else if (keyCode == ESC) {
                    estadoAtual = Estado.JARDIM;
                }
                break;

            case NOME_PLANTA:
                if (keyCode == ENTER || keyCode == RETURN) {
                    confirmarPlantio();
                } else if (keyCode == BACKSPACE) {
                    if (nomeDigitado.length() > 0) {
                        nomeDigitado.deleteCharAt(nomeDigitado.length() - 1);
                    }
                } else if (keyCode == ESC) {
                    estadoAtual = Estado.JARDIM;
                } else if (Character.isLetterOrDigit(key) || key == ' ') {
                    if (nomeDigitado.length() < 20) {
                        nomeDigitado.append(key);
                    }
                }
                break;

            case ACOES_PLANTA:
                if (key == 'x' || key == 'X' || keyCode == ESC) {
                    estadoAtual = Estado.JARDIM;
                }
                break;

            case INFO_PLANTA:
                if (key == 'x' || key == 'X' || keyCode == ESC) {
                    estadoAtual = Estado.JARDIM;
                }
                break;

            case JARDIM:

                if (key == 'j' || key == 'J') {
                    gerenciador.alterarJanela();
                }
                break;

            default:
                break;
        }
    }

    void abrirInventario() {
        indiceTipoSelecionado = 0;
        estadoAtual = Estado.INVENTARIO;
    }

    void confirmarEscolhaTipo() {
        tipoEmCriacao = tiposDisponiveis[indiceTipoSelecionado];
        nomeDigitado.setLength(0);
        estadoAtual = Estado.NOME_PLANTA;
    }

    void confirmarPlantio() {
        String nome = nomeDigitado.toString().trim();
        if (nome.isEmpty()) return;

        Planta novaPlanta = PlantaFactory.plantarSemente(tipoEmCriacao, nome);
        try {
            gerenciador.getJardim().plantar(novaPlanta);
        } catch (JardimCheioException e) {
            System.err.println(e.getMessage());
        }
        estadoAtual = Estado.JARDIM;
    }

    public static void main(String[] args) {
        PApplet.main("Sketch");
    }
}