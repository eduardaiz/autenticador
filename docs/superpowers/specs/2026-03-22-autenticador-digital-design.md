# Especificacao de Design - Autenticador Digital

## Contexto

Este documento descreve a solucao para o Trabalho Vivencial de Estruturas de Dados. O objetivo e implementar um autenticador digital para documentos `.txt`, utilizando lista dinamica, arvore AVL, pilha e geracao de hash SHA-1 conforme o enunciado da disciplina.

O repositorio atual ja possui uma implementacao inicial de arvore AVL e leitura basica de texto, mas ainda nao atende o fluxo completo exigido no PDF. Esta especificacao organiza a evolucao do projeto de modo que a implementacao, o relatorio e o roteiro do video fiquem coerentes entre si.

## Objetivo da Entrega

Entregar um projeto Java de console capaz de:

1. Ler um arquivo de texto com multiplas linhas.
2. Processar cada linha individualmente.
3. Inserir as palavras de cada linha em uma lista dinamica.
4. Montar uma arvore AVL por linha com insercao em ordem reversa.
5. Ignorar palavras duplicadas na mesma linha usando comparacao case-insensitive.
6. Empilhar cada arvore construida.
7. Desempilhar as arvores e calcular um hash SHA-1 para cada uma.
8. Exibir o autenticador final com um hash por linha.

Tambem fazem parte da entrega:

1. Um relatorio academico em portugues cobrindo titulo, introducao, metodologia, resultados e conclusao.
2. Um roteiro-base para video com explicacao da ideia, estruturas de dados e demonstracao do sistema.

## Requisitos Funcionais

### Entrada

- O programa deve receber um arquivo `.txt`.
- O texto deve ser lido linha por linha.

### Processamento por linha

Para cada linha:

1. Quebrar a linha em palavras usando espacos em branco como separador.
2. Armazenar essas palavras em uma estrutura de lista dinamica.
3. Percorrer a lista do fim para o inicio.
4. Inserir cada palavra em uma arvore AVL.
5. Descartar duplicatas quando `compareToIgnoreCase()` retornar `0`.
6. Inserir a arvore gerada em uma pilha.

### Processamento final

Depois que todas as linhas forem processadas:

1. Desempilhar uma arvore por vez.
2. Calcular o hash da raiz daquela arvore.
3. Concatenar todos os hashes, separados por quebra de linha.
4. Exibir o resultado no console.

## Regra do Hash

Cada no da arvore tera seu hash calculado de forma recursiva.

- O valor base do no sera o SHA-1 da palavra armazenada nele.
- O hash final do no sera calculado combinando:
  - o hash do filho esquerdo, se existir;
  - o hash do filho direito, se existir;
  - o hash da palavra do proprio no.
- A combinacao sera feita apenas com os elementos existentes; nenhum hash sera gerado para `null`.

A regra implementada sera equivalente a:

`hash(no) = SHA1(hash(esquerda) + hash(direita) + SHA1(valorDoNo))`

Quando so um dos filhos existir, a combinacao usara apenas esse filho e o hash do no atual. Quando nenhum filho existir, o resultado sera o SHA-1 da propria palavra do no.

## Arquitetura Proposta

O projeto sera organizado em classes pequenas, cada uma com responsabilidade clara.

### Nucleo da aplicacao

- `Main`
  - ponto de entrada;
  - recebe o caminho do arquivo ou usa um caminho padrao;
  - coordena a execucao e imprime a saida final.

- `ProcessadorDocumento`
  - orquestra o fluxo completo do trabalho;
  - transforma linhas em arvores;
  - usa a pilha;
  - gera a assinatura final.

- `LeitorTexto`
  - encapsula a leitura de arquivos `.txt`;
  - devolve a lista de linhas do documento.

### Estruturas de dados

- `Folha`
  - representa a palavra armazenada em um no.

- `ArvoreAVL`
  - responsavel por insercao, balanceamento, busca de duplicatas e acesso ao no raiz;
  - mantem a logica da estrutura balanceada.

- `NoAVL`
  - representa cada no da arvore com:
    - palavra;
    - ponteiro para esquerda;
    - ponteiro para direita;
    - altura.

- `PilhaArvores`
  - abstrai o armazenamento LIFO das arvores geradas por linha.

### Hash e saida

- `GeradorHash`
  - calcula o SHA-1 de strings;
  - calcula o hash recursivo de uma arvore AVL.

- `ResultadoAutenticacao`
  - encapsula:
    - quantidade de linhas processadas;
    - quantidade de arvores geradas;
    - lista final de hashes;
    - texto final concatenado.

## Fluxo de Dados

1. `Main` inicia o programa.
2. `LeitorTexto` le o arquivo `.txt`.
3. `ProcessadorDocumento` percorre cada linha.
4. Para cada linha:
   - cria uma lista dinamica de palavras;
   - monta a AVL com leitura reversa da lista;
   - empilha a arvore.
5. Ao final da leitura:
   - desempilha cada arvore;
   - chama `GeradorHash` para obter o hash da raiz;
   - armazena o resultado em uma lista final.
6. `Main` imprime o autenticador completo e um resumo amigavel.

## Casos Especiais e Regras de Consistencia

- Palavras repetidas na mesma linha nao devem ser inseridas novamente na arvore.
- A comparacao entre palavras sera feita com `compareToIgnoreCase()`.
- Espacos extras no inicio, meio e fim devem ser ignorados.
- Linhas vazias nao devem derrubar o programa.
- O sistema deve tratar arquivo inexistente com mensagem clara de erro.
- Nao deve haver geracao de hash para filhos nulos.

### Decisao para linhas vazias

Linhas vazias ou compostas apenas por espacos serao ignoradas na geracao de arvores. Essa decisao evita criar estruturas vazias sem significado sem violar o fluxo do trabalho. O relatorio vai explicitar essa escolha como tratamento de robustez da entrada.

## Estrategia de Saida

O programa apresentara:

1. Informacoes resumidas de execucao:
   - quantidade de linhas lidas;
   - quantidade de linhas validas processadas;
   - quantidade de hashes gerados.
2. O autenticador final:
   - um hash por linha;
   - na ordem em que as arvores forem desempilhadas.

## Estrategia de Testes

Serão cobertos, no minimo, os seguintes cenarios:

1. Insercao AVL sem duplicatas considerando maiusculas e minusculas.
2. Balanceamento correto da arvore.
3. Processamento de uma linha simples.
4. Geração do hash de uma arvore folha.
5. Geração do hash de arvore com filhos.
6. Processamento de multiplas linhas com pilha.
7. Tratamento de linha vazia.
8. Tratamento de arquivo inexistente.

Os testes servirao tambem como base dos exemplos mostrados no relatorio e no video.

## Materiais da Entrega

### Implementacao

- Codigo Java completo e executavel.
- Arquivo de texto de exemplo com no minimo 30 linhas.
- Instrucoes de execucao atualizadas no `README`.

### Relatorio

O relatorio sera preparado em um arquivo de documentacao do projeto contendo:

1. Titulo.
2. Introducao.
3. Metodologia.
4. Resultados.
5. Conclusao.

O texto sera escrito em tom academico, pronto para adaptacao ao modelo oficial da disciplina quando ele estiver disponivel.

### Roteiro de Video

Sera criado um roteiro resumido com:

1. Abertura e apresentacao.
2. Problema proposto.
3. Estruturas de dados utilizadas.
4. Fluxo do algoritmo.
5. Demonstracao da execucao.
6. Encerramento.

## Riscos e Cuidados

- O enunciado usa a expressao "lista dinamica", entao a implementacao deve explicitar esse ponto no codigo e no relatorio.
- O hash precisa seguir exatamente a combinacao recursiva dos filhos com o no atual.
- O uso da pilha precisa ficar visivel na implementacao e na explicacao do video.
- O projeto deve evitar dependencia externa desnecessaria, para facilitar execucao na entrega.

## Resultado Esperado

Ao final, o repositorio deve conter uma solucao completa e defensavel em apresentacao:

- correta em relacao ao PDF;
- organizada para facilitar avaliacao;
- documentada para o relatorio;
- clara para demonstracao em video.
