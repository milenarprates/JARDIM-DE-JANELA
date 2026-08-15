import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // criação de uma planta de exemplo

        System.out.println("Digite o nome da sua nova planta:");
        String nomePlanta = sc.nextLine();

        Especie cacto = new Especie(TipoPlanta.CACTO, 50 * 1000, 8); // intervalo de rega de 24h e necessidade de luz de 8 horas
        Cacto cactoPlanta = new Cacto(nomePlanta, cacto);

        cactoPlanta.exibirInfo();
        cactoPlanta.serRegada();
        cactoPlanta.exibirInfo();
        cactoPlanta.serRegada();
        cactoPlanta.serRegada();
        cactoPlanta.serRegada();
        cactoPlanta.crescer();
        cactoPlanta.exibirInfo();

        sc.close();
    }
}