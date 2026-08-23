import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.*;

public class GerenciadorArquivos {

    // constantes para o caminho do jardim e do inventário
    // são atributos para não ter que recriar toda vez que os métodos forem chamados
    private static final Path CAMINHO_JARDIM = Paths.get("jardim.dat");
    private static final Path CAMINHO_INVENTARIO = Paths.get("inventario.dat");

    // escreve o Jardim e o Inventario
    public static void escrever(Jardim jardim, Inventario inventario) throws IOException { 

        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(CAMINHO_JARDIM))) {
            oos.writeObject(jardim);
        }
        
        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(CAMINHO_INVENTARIO))) {
            oos.writeObject(inventario);
        }
        
    }

    // métodos static: pertence a classe, pode ser chamado sem instanciar

    // lê o arquivo de Jardim, retorna o jardim lido
    public static Jardim lerJardim() throws IOException, ClassNotFoundException {
        if(Files.exists(CAMINHO_JARDIM)) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(CAMINHO_JARDIM))) {
                return (Jardim) ois.readObject();
            }
        } else {
            throw new FileNotFoundException("Arquivo não encontrado: " + CAMINHO_JARDIM);
        }
    }

    // lê o arquivo de inventário, retorna o inventário lido
    public static Inventario lerInventario() throws IOException, ClassNotFoundException {
        if(Files.exists(CAMINHO_INVENTARIO)) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(CAMINHO_INVENTARIO))) {
                return (Inventario) ois.readObject();
            }
        } else {
            throw new FileNotFoundException("Arquivo não encontrado: " + CAMINHO_INVENTARIO);
        }
    }

    public static boolean existeArquivo() {
        return Files.exists(CAMINHO_JARDIM) && Files.exists(CAMINHO_INVENTARIO);
    }
}
