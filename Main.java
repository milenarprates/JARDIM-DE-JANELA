import java.util.Scanner;
 
import Exceptions.JardimCheioException;
import Exceptions.PlantaInadequadaException;
import Exceptions.RegaInvalidaException;
 
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GerenciadorJogo gerenciador = new GerenciadorJogo();

        int opcao = -1;

        if(GerenciadorArquivos.existeArquivo()) {
            try {
                gerenciador.recarregarJogo();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }

        do {
            System.out.println("\n--------- JARDIM ---------\n");
            for(Planta planta : gerenciador.getJardim().getPlantas()) {
                planta.exibeInfo();
            }
            System.out.println("\n1 - Criar semente.");
            System.out.println("2 - Plantar semente.");
            System.out.println("3 - Regar planta.");
            System.out.println("4 - Usar fertilizante.");
            System.out.println("5 - Colher planta.");
            System.out.println("6 - Abrir/fechar janela.");
            System.out.println("7 - Atualizar jardim.");
            System.out.println("0 - Sair.");
            System.out.print("-> ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            System.out.println("--------------------------");

            switch (opcao) {
                case 1:
                    System.out.println("\nTIPOS: CACTO, TOMATEIRO, LIRIO");
                    System.out.print("Tipo: ");

                    String tipoString = scanner.next().toUpperCase();
                    scanner.nextLine();
                    TipoPlanta tipo = TipoPlanta.valueOf(tipoString);

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    Planta semente = PlantaFactory.plantarSemente(tipo, nome);
                    gerenciador.getInventario().adicionarSemente(semente);

                    System.out.println("Nova semente adicionada!");
                    break;
                case 2:
                    if(gerenciador.getInventario().getSementes().isEmpty()) break;
                    gerenciador.getInventario().exibirSementes();

                    System.out.print("\nDigite o indice da semente a ser plantada: ");
                    int indice = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        gerenciador.plantarSemente(indice);
                        System.out.println("Semente plantada!");
                    } catch (JardimCheioException e) {
                        System.err.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 3:
                    gerenciador.getJardim().exibirPlantas();

                    System.out.print("\nDigite o indice da semente a ser regada: ");
                    int indiceR = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        gerenciador.regarPlanta(indiceR);
                        System.out.println("Planta regada.");
                    } catch (RegaInvalidaException e) {
                        System.err.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 4:
                    gerenciador.getJardim().exibirPlantas();

                    System.out.print("\nDigite o indice da semente a ser fertilizada: ");
                    int indiceF = scanner.nextInt();
                    scanner.nextLine();
                    
                    try {
                        boolean usouFertilizante = gerenciador.usarFertilizante(indiceF);
                        System.out.println(usouFertilizante ? "Fertilizante usado." : "Planta não aceita fertilizante.");
                    } catch(IndexOutOfBoundsException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 5:
                    gerenciador.getJardim().exibirPlantas();

                    System.out.print("\nDigite o indice da semente a ser colhida: ");
                    int indiceC = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        System.out.println("Planta " + gerenciador.colherPlanta(indiceC).getNome() + " colhida.");
                    } catch (PlantaInadequadaException e) {
                        System.err.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 6:
                    gerenciador.alterarJanela();
                    System.out.println("Janela " + (gerenciador.getJardim().isJanelaAberta() ? "aberta." : "fechada."));
                    break;
                case 7:
                    gerenciador.atualizarJardim();
                    System.out.println("Jardim atualizado.");
                    break;
                case 0:
                    try {
                        gerenciador.salvarJogo();
                        System.out.println("Jogo salvo com sucesso. Até mais!");
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Indice inválido. Tente novamente!");
            }
        } while (opcao != 0);

        scanner.close();
    }
}