import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String caminhoArquivo = args.length > 0 ? args[0] : "texto.txt";
        ProcessadorDocumento processadorDocumento = new ProcessadorDocumento(new LeitorTexto(), new GeradorHash());

        try {
            String hashes = processadorDocumento.processar(caminhoArquivo);
            if (!hashes.isEmpty()) {
                System.out.println(hashes);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
