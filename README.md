# Autenticador Digital

Este projeto tem como objetivo desenvolver um autenticador digital baseado em estruturas de dados e técnicas de criptografia.
O projeto consiste em entrar com um texto de no mínimo 30 linhas, em seguida, cada linha é dividida em palavras individuais. As repetições são eliminadas, de modo que apenas palavras únicas sejam consideradas. Logo depois, cada lista é colocada em uma árvore AVL, e cada árvore é armazenada em uma pilha, garantindo balanceamento e eficiência nas operações de inserção e busca. Essa pilha é consumida por um método, que consiste em transformar cada árvore em um código hash, fazendo assim uma criptografia do texto, funcionando como uma assinatura digital. 
