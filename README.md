# Autenticador Digital com Estruturas de Dados

Projeto feito para a disciplina de **Estruturas de Dados**, usando **lista dinâmica**, **árvore AVL**, **pilha** e **hash SHA-1** para autenticar o conteúdo de um arquivo texto.

---

## Visão geral

O programa lê um arquivo `.txt`, processa uma linha por vez e gera um hash para cada linha. Esse hash não sai direto do texto bruto. Antes disso, as palavras da linha passam por uma lista dinâmica, entram em uma árvore AVL e só depois o valor final é calculado.

O fluxo do projeto segue a ordem pedida no enunciado:

1. ler o arquivo texto;
2. separar cada linha em palavras usando espaços em branco;
3. armazenar essas palavras em uma lista dinâmica;
4. percorrer a lista do final para o início;
5. inserir as palavras em uma árvore AVL;
6. ignorar duplicatas com `compareToIgnoreCase()`;
7. empilhar cada árvore gerada;
8. desempilhar as árvores ao final;
9. gerar um hash SHA-1 para cada árvore;
10. imprimir os hashes, um por linha.

---

## Objetivo do projeto

A ideia aqui foi montar um autenticador digital sem pular as etapas que o trabalho exigia. Em vez de simplesmente pegar a linha e aplicar hash, o projeto organiza as palavras com estruturas de dados diferentes e usa essa organização no resultado final.

Na prática, isso ajuda a mostrar que a implementação não ficou resumida a “ler texto e gerar um código”. Cada estrutura tem uma função no processo.

---

## Fluxo da solução

| Etapa | O que acontece | Estrutura envolvida |
|---|---|---|
| 1 | O arquivo `.txt` é aberto para leitura | Leitura de arquivo |
| 2 | Cada linha é tratada separadamente | Processamento sequencial |
| 3 | As palavras da linha são separadas por espaços | String / parsing |
| 4 | As palavras são guardadas em uma lista dinâmica | Lista encadeada |
| 5 | A lista é percorrida em ordem reversa | Lista dinâmica |
| 6 | As palavras são inseridas em uma AVL | Árvore AVL |
| 7 | Duplicatas são ignoradas com `compareToIgnoreCase()` | AVL |
| 8 | A árvore pronta daquela linha é empilhada | Pilha |
| 9 | Ao final, as árvores são desempilhadas | Pilha |
| 10 | Cada árvore gera um hash hierárquico com SHA-1 | Hash SHA-1 |
| 11 | Os hashes finais são exibidos um por linha | Saída final |

---

## Estruturas de dados utilizadas

### Lista dinâmica

A lista dinâmica guarda as palavras de cada linha antes da inserção na árvore. Ela entra no projeto porque a ordem de inserção precisa ser invertida. Então primeiro as palavras são armazenadas e depois lidas do último elemento para o primeiro.

### Árvore AVL

A árvore AVL recebe as palavras já na ordem reversa. Nela acontece a comparação lexicográfica com `compareToIgnoreCase()`, o descarte das duplicatas e o balanceamento por rotações quando necessário.

### Pilha

A pilha guarda uma árvore AVL para cada linha do arquivo. Como ela trabalha em LIFO, a última árvore que entra é a primeira a sair quando os hashes começam a ser gerados.

### Hash SHA-1

O SHA-1 é usado no final para gerar o hash de cada árvore. O projeto não tira esse valor diretamente da linha original. O cálculo é feito em cima da estrutura da AVL montada para aquela linha.

---

## Lógica do hash

O cálculo do hash segue a regra pedida no trabalho:

- se o nó for folha, o hash é o da palavra armazenada nele;
- se o nó tiver filhos, o cálculo segue a forma:

```text
h(h(esquerda) + h(direita) + h(nó))
```

### Regras importantes do cálculo

| Regra | Como o projeto trata |
|---|---|
| Hash da folha | Gera SHA-1 diretamente da palavra |
| Hash de nó interno | Concatena os hashes dos filhos existentes e o hash do próprio nó |
| Filho inexistente | Não concatena nada |
| Hash final da linha | É o hash calculado na raiz da árvore |

Isso faz diferença porque o resultado final depende não só das palavras da linha, mas também da forma como elas ficaram organizadas na árvore.

---

## Organização do projeto

```text
autenticador/
├── src/
│   ├── Main.java
│   ├── LeitorTexto.java
│   ├── ListaDinamicaPalavras.java
│   ├── Folha.java
│   ├── Arvore.java
│   ├── PilhaArvores.java
│   ├── GeradorHash.java
│   └── ProcessadorDocumento.java
├── test/
│   └── ProjetoAutenticadorTest.java
├── texto.txt
├── relatorio.md
├── roteiro_video.md
└── README.md
```

---

## Principais arquivos

| Arquivo | Responsabilidade |
|---|---|
| `Main.java` | Inicia a execução do programa |
| `LeitorTexto.java` | Faz a abertura do arquivo de entrada |
| `ListaDinamicaPalavras.java` | Guarda as palavras da linha e permite percorrer de trás para frente |
| `Arvore.java` | Implementa a AVL e também o hash hierárquico |
| `PilhaArvores.java` | Empilha e desempilha as árvores de cada linha |
| `GeradorHash.java` | Gera SHA-1 com `MessageDigest` |
| `ProcessadorDocumento.java` | Controla o processamento completo do arquivo |
| `ProjetoAutenticadorTest.java` | Testa partes principais da lógica implementada |

---

## Aderência ao enunciado

| Exigência | Situação no projeto |
|---|---|
| Ler arquivo `.txt` | Atendido |
| Processar cada linha separadamente | Atendido |
| Inserir palavras em lista dinâmica | Atendido |
| Percorrer a lista em ordem reversa | Atendido |
| Inserir em árvore AVL | Atendido |
| Comparar com `compareToIgnoreCase()` | Atendido |
| Ignorar duplicatas | Atendido |
| Empilhar uma árvore por linha | Atendido |
| Desempilhar as árvores ao final | Atendido |
| Gerar hash SHA-1 | Atendido |
| Usar hash hierárquico da árvore | Atendido |
| Imprimir hashes por linha | Atendido |

---

## Arquivos e métodos onde cada tópico é atendido:

| Tópico | Status | Arquivo(s) | Método(s) |
|---|---|---|---|
| 1. Leitura de arquivo textual `.txt` | Atendido | `Main.java`, `LeitorTexto.java`, `ProcessadorDocumento.java` | `main`, `abrir`, `processar` |
| 2. Consumir cada linha individualmente e inserir palavras em lista dinâmica | Atendido | `ProcessadorDocumento.java`, `ListaDinamicaPalavras.java` | `processar`, `processarLinha`, `adicionar` |
| 3. Acessar a lista em ordem reversa e inserir na AVL com `compareToIgnoreCase()`, ignorando duplicatas | Atendido | `ListaDinamicaPalavras.java`, `Arvore.java`, `Folha.java` | `inserirEmOrdemReversaNaArvore`, `inserir`, `inserir(No,Folha)` |
| 4. Inserir a árvore AVL da linha em uma pilha | Atendido | `ProcessadorDocumento.java`, `PilhaArvores.java` | `processar`, `empilhar` |
| 5. Repetir o processo para todas as linhas do texto | Atendido | `ProcessadorDocumento.java` | `processar` |
| 6. Desempilhar as árvores e gerar o hash de cada uma | Atendido | `ProcessadorDocumento.java`, `PilhaArvores.java`, `Arvore.java`, `GeradorHash.java` | `processar`, `desempilhar`, `gerarHash`, `gerarSha1` |
| 7. Concatenar os hashes separados por quebra de linha | Atendido | `ProcessadorDocumento.java`, `Main.java` | `processar`, `main` |

---

## Como executar

O projeto foi escrito em **Java** usando apenas bibliotecas padrão da linguagem.

### Entrada

Por padrão, o programa lê o arquivo:

```text
texto.txt
```

Também é possível informar outro caminho de arquivo por argumento na execução.

### Saída esperada

A saída principal do programa é formada apenas por:

```text
um hash por linha
sem mensagens extras
sem rótulos decorativos
```

---

## Exemplo de comportamento esperado

Se duas linhas forem processadas, o programa vai:

- gerar uma AVL para a primeira linha;
- gerar outra AVL para a segunda linha;
- empilhar as duas;
- desempilhar em ordem inversa;
- calcular um hash para cada árvore;
- imprimir os resultados em linhas separadas.

Uma mudança pequena no texto já tende a alterar bastante o hash final. É isso que faz o autenticador ser útil para verificar integridade.

---

## Observações técnicas

- o projeto usa `UTF-8` na leitura do arquivo;
- a separação das palavras é feita por espaços em branco;
- não há remoção automática de pontuação;
- o conteúdo textual da palavra é preservado;
- duplicatas são ignoradas apenas quando a comparação lexicográfica desconsiderando maiúsculas e minúsculas resulta em igualdade.

---

## Considerações finais

Esse projeto foi montado para seguir o que a disciplina pediu e, ao mesmo tempo, deixar claro o papel de cada estrutura dentro da solução. A lista dinâmica, a AVL, a pilha e o SHA-1 não estão ali só para “aparecer”. Cada parte entra em um momento específico do processamento.

No fim, o programa entrega os hashes por linha como o trabalho pede, mas o mais importante é o caminho usado para chegar nesse resultado.
