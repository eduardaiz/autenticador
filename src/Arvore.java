public class Arvore {

    private class No {
        Folha folha;
        No esquerda;
        No direita;
        int altura;

        No(Folha folha) {
            this.folha = folha;
            this.altura = 1;
        }
    }

    private No raiz;

    public Arvore() {
        this.raiz = null;
    }

    private int altura(No N) {
        if (N == null)
            return 0;
        return N.altura;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    private int getBalance(No N) {
        if (N == null)
            return 0;
        return altura(N.esquerda) - altura(N.direita);
    }

    public void inserir(Folha folha) {
        raiz = inserir(raiz, folha);
    }

    private No inserir(No no, Folha folha) {
        if (no == null) {
            return (new No(folha));
        }

        int comparacao = folha.getPalavra().compareToIgnoreCase(no.folha.getPalavra());

        if (comparacao < 0)
            no.esquerda = inserir(no.esquerda, folha);
        else if (comparacao > 0)
            no.direita = inserir(no.direita, folha);
        else 
            return no;

        no.altura = 1 + max(altura(no.esquerda), altura(no.direita));

        int balance = getBalance(no);

        if (balance > 1 && folha.getPalavra().compareToIgnoreCase(no.esquerda.folha.getPalavra()) < 0)
            return rotacaoDireita(no);

        if (balance < -1 && folha.getPalavra().compareToIgnoreCase(no.direita.folha.getPalavra()) > 0)
            return rotacaoEsquerda(no);

        if (balance > 1 && folha.getPalavra().compareToIgnoreCase(no.esquerda.folha.getPalavra()) > 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balance < -1 && folha.getPalavra().compareToIgnoreCase(no.direita.folha.getPalavra()) < 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void imprimirEmOrdem() {
        imprimirEmOrdem(raiz);
        System.out.println();
    }

    private void imprimirEmOrdem(No no) {
        if (no != null) {
            imprimirEmOrdem(no.esquerda);
            System.out.print(no.folha.getPalavra() + " | ");
            imprimirEmOrdem(no.direita);
        }
    }
}