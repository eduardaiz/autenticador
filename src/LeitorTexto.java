import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LeitorTexto {

    public BufferedReader abrir(String caminhoArquivo) throws IOException {
        return Files.newBufferedReader(Path.of(caminhoArquivo), StandardCharsets.UTF_8);
    }
}
