public class ListaDinamicaPalavras {

    private static class No {
        private final String palavra;
        private No anterior;
        private No proximo;

        private No(String palavra) {
            this.palavra = palavra;
        }
    }

    private No primeiro;
    private No ultimo;

    public void adicionar(String palavra) {
        No novoNo = new No(palavra);
        if (primeiro == null) {
            primeiro = novoNo;
            ultimo = novoNo;
            return;
        }

        ultimo.proximo = novoNo;
        novoNo.anterior = ultimo;
        ultimo = novoNo;
    }

    public void inserirEmOrdemReversaNaArvore(Arvore arvore) {
        No atual = ultimo;
        while (atual != null) {
            arvore.inserir(new Folha(atual.palavra));
            atual = atual.anterior;
        }
    }
}
