import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Arvore arvoreAVL = new Arvore();

        try {
            List<String> linhas = Files.readAllLines(Paths.get("teste_texto.txt"));
            List<String> todasAsPalavras = new ArrayList<>();

            for (String linha : linhas) {
                String[] palavras = linha.split("\\s+"); 
                for (String palavra : palavras) {
                    if (!palavra.trim().isEmpty()) {
                        todasAsPalavras.add(palavra);
                    }
                }
            }

            System.out.println("Lidas " + todasAsPalavras.size() + " palavras no total.");
            System.out.println("Inserindo na árvore AVL lendo a lista no sentido reverso...\n");

            for (int i = todasAsPalavras.size() - 1; i >= 0; i--) {
                String palavra = todasAsPalavras.get(i);
                arvoreAVL.inserir(new Folha(palavra));
            }

            System.out.println("Inserção concluída. Palavras na árvore em ordem alfabética (sem duplicatas):");
            arvoreAVL.imprimirEmOrdem();

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}