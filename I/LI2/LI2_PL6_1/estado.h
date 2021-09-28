#ifndef PROJ_ESTADO_H
#define PROJ_ESTADO_H
#define MAX_BUFFER 1024

///
/// @brief Esta estrutura codifica um ponto na grelha.
///
/// Numa grelha, um ponto é representado por um par inteiros.
///
typedef struct ponto {
    int l;///< Componente linha do PONTO
    int c;///< Componente coluna do PONTO
} PONTO;

///
/// @brief Esta estrutura codifica dois fatores: um array de pontos e o seu respetivo tamanho.
///
/// Uma DUPLA é geralmente utilizada para guardar as posiçôes válidas para um jogador.
///
typedef struct Dupla {
    PONTO pos[64];///< Componente PONTOS da DUPLA
    int tam;///< Componente tamanho da DUPLA
} DUPLA;

///
/// @brief Esta estrutura divide uma lista de posições em regiões do tabuleiro.
///
/// Esta divisão facilita as estratégias de jogo, visto que estão ordenadas decrescentemente por ordem de prioridade.
///
typedef struct regiao {
    DUPLA reg1;///< Cantos do tabuleiro.
    DUPLA reg2;///< Bordas do tabuleiro.
    DUPLA reg3;///< Zona central do tabuleiro.
    DUPLA reg4;///< Junto as bordas do tabuleiro.
    DUPLA reg5;///< Junto aos cantos do tabuleiro.
}REGIAO;
// definição de valores possiveis no tabuleiro
typedef enum {VAZIA, VALOR_X, VALOR_O} VALOR;

///
/// @brief Esta estrutura codifica um estado de jogo.
///
///
typedef struct estado {
    VALOR peca; ///< peça do jogador que vai jogar!
    VALOR grelha[8][8];///< tabuleiro do jogo
    char modo; ///< modo em que se está a jogar! 0-> manual, 1-> contra computador
    int nivel;
} ESTADO;

///
/// @brief Esta estrutura codifica uma STACK de estados de jogo.
///
///
typedef struct stack{
    ESTADO e;///< Estado de jogo
    struct stack * prox;///< Apontador para o estado de jogo seguinte
} STACK;

///
/// @brief Esta estrutura codifica um estado e uma stack com todos os estados do jogo.
///
///
typedef struct tripla
{
    ESTADO e;///< Estado de jogo
    STACK *s;///< Stack com estados do jogo
}TRIPLA;
void printa(ESTADO);
ESTADO novo_jogo (ESTADO e, VALOR VALOR_X);
char valortochar (VALOR v);
VALOR chartovalor (char c);
PONTO score (ESTADO e);
#endif //PROJ_ESTADO_H