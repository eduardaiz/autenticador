public class PilhaArvores {

    private static class No {
        private final Arvore arvore;
        private No proximo;

        private No(Arvore arvore) {
            this.arvore = arvore;
        }
    }

    private No topo;

    public void empilhar(Arvore arvore) {
        No novoNo = new No(arvore);
        novoNo.proximo = topo;
        topo = novoNo;
    }

    public Arvore desempilhar() {
        if (topo == null) {
            throw new IllegalStateException("Pilha vazia");
        }

        Arvore arvore = topo.arvore;
        topo = topo.proximo;
        return arvore;
    }

    public boolean estaVazia() {
        return topo == null;
    }
}
