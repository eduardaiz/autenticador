import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProjetoAutenticadorTest {
    public static void main(String[] args) throws Exception {
        deveGerarUmHashPorLinhaEmOrdemDeDesempilhamento();
        deveIgnorarDuplicatasComCompareToIgnoreCaseEUsarInsercaoReversa();
        mainDeveImprimirApenasOsHashes();
        System.out.println("OK");
    }

    private static void deveGerarUmHashPorLinhaEmOrdemDeDesempilhamento() throws Exception {
        Path arquivo = criarArquivoTemporario("alpha\nbeta");
        ProcessadorDocumento processador = novoProcessador();

        String resultado = processador.processar(arquivo.toString());

        String esperado = sha1("beta") + System.lineSeparator() + sha1("alpha");
        assertEquals(esperado, resultado, "cada linha deve gerar sua propria AVL e a pilha deve inverter a ordem de saida");
    }

    private static void deveIgnorarDuplicatasComCompareToIgnoreCaseEUsarInsercaoReversa() throws Exception {
        Path arquivo = criarArquivoTemporario("b A a c");
        ProcessadorDocumento processador = novoProcessador();

        String resultado = processador.processar(arquivo.toString());

        String hashEsquerda = sha1("a");
        String hashDireita = sha1("c");
        String hashRaiz = sha1("b");
        String esperado = sha1(hashEsquerda + hashDireita + hashRaiz);
        assertEquals(esperado, resultado, "a AVL precisa receber as palavras em ordem reversa e ignorar duplicatas por compareToIgnoreCase");
    }

    private static void mainDeveImprimirApenasOsHashes() throws Exception {
        Path arquivo = criarArquivoTemporario("alpha\nbeta");
        ByteArrayOutputStream saidaCapturada = new ByteArrayOutputStream();
        PrintStream saidaOriginal = System.out;

        try {
            System.setOut(new PrintStream(saidaCapturada, true, StandardCharsets.UTF_8));
            Main.main(new String[]{arquivo.toString()});
        } finally {
            System.setOut(saidaOriginal);
        }

        String esperado = sha1("beta") + System.lineSeparator() + sha1("alpha") + System.lineSeparator();
        assertEquals(esperado, saidaCapturada.toString(StandardCharsets.UTF_8), "a execucao principal deve imprimir apenas um hash por linha");
    }

    private static ProcessadorDocumento novoProcessador() {
        return new ProcessadorDocumento(new LeitorTexto(), new GeradorHash());
    }

    private static Path criarArquivoTemporario(String conteudo) throws IOException {
        Path arquivo = Files.createTempFile("autenticador-", ".txt");
        Files.writeString(arquivo, conteudo, StandardCharsets.UTF_8);
        return arquivo;
    }

    private static String sha1(String valor) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(valor.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-1 indisponivel", e);
        }
    }

    private static void assertEquals(String esperado, String atual, String mensagem) {
        if (!esperado.equals(atual)) {
            throw new AssertionError(mensagem + "\nEsperado: " + esperado + "\nAtual: " + atual);
        }
    }
}
