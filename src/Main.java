import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Arvore arvore = new Arvore (new Folha(30));
        arvore.inserir(new Folha(15));
        arvore.inserir(new Folha(45));
        arvore.inserir(new Folha(25));
        arvore.inserir(new Folha(16));


        try {
            List<String> line = Files.readAllLines(Paths.get("texto.txt"));
            for (String lines : line) {
                String[] palavras = lines.split(" ");

                //System.out.println(Arrays.toString(palavras));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}