
#include <stdio.h>
#include "estado.h"

/**
 * Função que indica o score dos jogadores.
 * @param e Estado atual.
 * @return O score de cada jogador.
 */
PONTO score (ESTADO e) {
    PONTO p;
    int x = 0, o = 0, i, j;
    for (i=0;i<8;i++)
    {
        for (j=0;j<8;j++)
        {
            if (e.grelha[i][j] == VALOR_O) o++;
            else if (e.grelha[i][j] == VALOR_X) x++;
        }
    }
    p.l = x;
    p.c = o;
    return  p;
}

/**
 * Função para criar o tabuleiro inicial.
 * @param e Estado atual.
 * @param d Peça do jogador atual.
 * @return Estado do novo jogo.
 */
ESTADO novo_jogo (ESTADO e, VALOR d) {

//estado inicial do tabuleiro. Inicio do jogo!
    e.grelha[3][4] = VALOR_O; //preenche o tabuleiro nestas posicoes
    e.grelha[4][3] = VALOR_O;
    e.grelha[3][3] = VALOR_X;
    e.grelha[4][4] = VALOR_X;
    e.peca = d;
    return e;
}

/**
 * Função que transforma um caracter em valor.
 * @param c Caracter.
 * @return Valor peça.
 */
VALOR chartovalor (char c)
{
    switch (c)
    {
        case 'X' :
            return VALOR_X;
        case 'O' :
            return VALOR_O;
        default:
            return VAZIA;
    }
}

/**
 * Função que transforma um valor em caracter.
 * @param v Valor peça.
 * @return Caracter.
 */
char valortochar (VALOR v)
{
    switch (v)
    {
        case VALOR_X:
            return 'X';
        case VALOR_O:
            return 'O';
        default:
            return '-';
    }
}

/**
 * Função para imprimir o tabuleiro no terminal.
 * @param e Estado atual.
 */
void printa(ESTADO e)
{
    PONTO p; char c = ' ';
    if (e.peca == VAZIA || (e.modo == 1 && e.nivel != 1 && e.nivel != 2 && e.nivel != 3)) printf("Tabuleiro Inválido\n");
    else
    {
        if (e.modo == 1) printf("A");
        else if (e.modo == 0) printf("M");
        if (e.peca == VALOR_O) printf(" O");
        else if (e.peca == VALOR_X) printf(" X");
        if (e.modo == 0) printf("\n");
        else if (e.modo == 1) printf(" %d\n", e.nivel);
        printf("  1 2 3 4 5 6 7 8 \n");
        for (int i = 0; i < 8; i++)
        {
            printf("%d ", i + 1);
            for (int j = 0; j < 8; j++) {c = valortochar(e.grelha[i][j]); printf("%c ", c); }
            printf("\n");
        }
        printf("\n");
        p = score(e);
        printf("X: %d  O: %d\n\n", p.l, p.c);
    }
}