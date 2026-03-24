import java.io.BufferedReader;
import java.io.IOException;

public class ProcessadorDocumento {

    private final LeitorTexto leitorTexto;
    private final GeradorHash geradorHash;

    public ProcessadorDocumento(LeitorTexto leitorTexto, GeradorHash geradorHash) {
        this.leitorTexto = leitorTexto;
        this.geradorHash = geradorHash;
    }

    public String processar(String caminhoArquivo) throws IOException {
        PilhaArvores pilhaDeArvores = new PilhaArvores();

        try (BufferedReader leitor = leitorTexto.abrir(caminhoArquivo)) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                pilhaDeArvores.empilhar(processarLinha(linha));
            }
        }

        StringBuilder saida = new StringBuilder();
        boolean primeiraLinha = true;
        while (!pilhaDeArvores.estaVazia()) {
            if (!primeiraLinha) {
                saida.append(System.lineSeparator());
            }
            saida.append(pilhaDeArvores.desempilhar().gerarHash(geradorHash));
            primeiraLinha = false;
        }
        return saida.toString();
    }

    private Arvore processarLinha(String linha) {
        ListaDinamicaPalavras listaDePalavras = new ListaDinamicaPalavras();

        if (!linha.isBlank()) {
            String[] palavras = linha.trim().split("\\s+");
            for (String palavra : palavras) {
                if (!palavra.isEmpty()) {
                    listaDePalavras.adicionar(palavra);
                }
            }
        }

        Arvore arvore = new Arvore();
        listaDePalavras.inserirEmOrdemReversaNaArvore(arvore);
        return arvore;
    }
}
