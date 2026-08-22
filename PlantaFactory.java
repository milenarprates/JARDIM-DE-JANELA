public class PlantaFactory {

    private static final long INTERVALO_REGA_CACTO = 30 * 1000;
    private static final int LUZ_NECESSARIA_CACTO = 30;
    private static final int PERDA_AGUA_CACTO = 5;
    private static final int GANHO_LUZ_CACTO = 30;

    private static final Especie ESPECIE_CACTO = new Especie(
            TipoPlanta.CACTO,
            INTERVALO_REGA_CACTO,
            LUZ_NECESSARIA_CACTO,
            PERDA_AGUA_CACTO,
            GANHO_LUZ_CACTO
    );

    private static final long INTERVALO_REGA_TOMATEIRO = 15 * 1000;
    private static final int LUZ_NECESSARIA_TOMATEIRO = 20;
    private static final int PERDA_AGUA_TOMATEIRO = 8;
    private static final int GANHO_LUZ_TOMATEIRO = 20;

    private static final Especie ESPECIE_TOMATEIRO = new Especie(
            TipoPlanta.TOMATEIRO,
            INTERVALO_REGA_TOMATEIRO,
            LUZ_NECESSARIA_TOMATEIRO,
            PERDA_AGUA_TOMATEIRO,
            GANHO_LUZ_TOMATEIRO
    );

    private static final long INTERVALO_REGA_LIRIO = 10 * 1000;
    private static final int LUZ_NECESSARIA_LIRIO = 18;
    private static final int PERDA_AGUA_LIRIO = 12;
    private static final int GANHO_LUZ_LIRIO = 18;

    private static final Especie ESPECIE_LIRIO = new Especie(
            TipoPlanta.LIRIO,
            INTERVALO_REGA_LIRIO,
            LUZ_NECESSARIA_LIRIO,
            PERDA_AGUA_LIRIO,
            GANHO_LUZ_LIRIO
    );

    public static Planta plantarSemente(TipoPlanta tipo, String nome) {
        switch (tipo) {
            case CACTO:
                return new Cacto(nome, ESPECIE_CACTO);
            case TOMATEIRO:
                return new Tomateiro(nome, ESPECIE_TOMATEIRO);
            case LIRIO:
                return new Lirio(nome, ESPECIE_LIRIO);
            default:
                throw new IllegalArgumentException("Tipo de planta inválido");
        }
    }
}