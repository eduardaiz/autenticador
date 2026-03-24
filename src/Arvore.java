public class Arvore {

    private static class No {
        private final Folha folha;
        private No esquerda;
        private No direita;
        private int altura;

        private No(Folha folha) {
            this.folha = folha;
            this.altura = 1;
        }
    }

    private No raiz;

    public void inserir(Folha folha) {
        raiz = inserir(raiz, folha);
    }

    public String gerarHash(GeradorHash geradorHash) {
        if (raiz == null) {
            return geradorHash.gerarSha1("");
        }
        return gerarHash(raiz, geradorHash);
    }

    private No inserir(No noAtual, Folha folha) {
        if (noAtual == null) {
            return new No(folha);
        }

        int comparacao = folha.getPalavra().compareToIgnoreCase(noAtual.folha.getPalavra());
        if (comparacao < 0) {
            noAtual.esquerda = inserir(noAtual.esquerda, folha);
        } else if (comparacao > 0) {
            noAtual.direita = inserir(noAtual.direita, folha);
        } else {
            return noAtual;
        }

        atualizarAltura(noAtual);
        return balancear(noAtual, folha);
    }

    private String gerarHash(No noAtual, GeradorHash geradorHash) {
        String hashDaPalavra = geradorHash.gerarSha1(noAtual.folha.getPalavra());
        if (noAtual.esquerda == null && noAtual.direita == null) {
            return hashDaPalavra;
        }

        StringBuilder conteudo = new StringBuilder();
        if (noAtual.esquerda != null) {
            conteudo.append(gerarHash(noAtual.esquerda, geradorHash));
        }
        if (noAtual.direita != null) {
            conteudo.append(gerarHash(noAtual.direita, geradorHash));
        }
        conteudo.append(hashDaPalavra);
        return geradorHash.gerarSha1(conteudo.toString());
    }

    private No balancear(No noAtual, Folha folhaInserida) {
        int balanceamento = fatorBalanceamento(noAtual);

        if (balanceamento > 1
                && folhaInserida.getPalavra().compareToIgnoreCase(noAtual.esquerda.folha.getPalavra()) < 0) {
            return rotacaoDireita(noAtual);
        }

        if (balanceamento < -1
                && folhaInserida.getPalavra().compareToIgnoreCase(noAtual.direita.folha.getPalavra()) > 0) {
            return rotacaoEsquerda(noAtual);
        }

        if (balanceamento > 1
                && folhaInserida.getPalavra().compareToIgnoreCase(noAtual.esquerda.folha.getPalavra()) > 0) {
            noAtual.esquerda = rotacaoEsquerda(noAtual.esquerda);
            return rotacaoDireita(noAtual);
        }

        if (balanceamento < -1
                && folhaInserida.getPalavra().compareToIgnoreCase(noAtual.direita.folha.getPalavra()) < 0) {
            noAtual.direita = rotacaoDireita(noAtual.direita);
            return rotacaoEsquerda(noAtual);
        }

        return noAtual;
    }

    private No rotacaoDireita(No noDesbalanceado) {
        No novaRaiz = noDesbalanceado.esquerda;
        No subArvoreTransferida = novaRaiz.direita;

        novaRaiz.direita = noDesbalanceado;
        noDesbalanceado.esquerda = subArvoreTransferida;

        atualizarAltura(noDesbalanceado);
        atualizarAltura(novaRaiz);
        return novaRaiz;
    }

    private No rotacaoEsquerda(No noDesbalanceado) {
        No novaRaiz = noDesbalanceado.direita;
        No subArvoreTransferida = novaRaiz.esquerda;

        novaRaiz.esquerda = noDesbalanceado;
        noDesbalanceado.direita = subArvoreTransferida;

        atualizarAltura(noDesbalanceado);
        atualizarAltura(novaRaiz);
        return novaRaiz;
    }

    private void atualizarAltura(No noAtual) {
        noAtual.altura = Math.max(altura(noAtual.esquerda), altura(noAtual.direita)) + 1;
    }

    private int fatorBalanceamento(No noAtual) {
        return noAtual == null ? 0 : altura(noAtual.esquerda) - altura(noAtual.direita);
    }

    private int altura(No noAtual) {
        return noAtual == null ? 0 : noAtual.altura;
    }
}
