import java.util.Scanner;

public class Main {

    final static int REGA_LIRIO = 20;
    final static int LUZ_LIRIO = 10;
    final static int PERDA_AGUA_LIRIO = 5;
    final static int GANHO_LUZ_LIRIO = 5;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o nome da sua nova planta:");
        String nomePlanta = sc.nextLine();

        Especie lirioEspecie = new Especie(TipoPlanta.LIRIO,  REGA_LIRIO, LUZ_LIRIO, PERDA_AGUA_LIRIO, GANHO_LUZ_LIRIO);
        Lirio lirio = new Lirio(nomePlanta, lirioEspecie);

        lirio.exibeInfo();
        lirio.serRegada();
        lirio.serRegada();
        lirio.serRegada();
        lirio.crescer();
        lirio.exibeInfo();

        sc.close();
    }
}