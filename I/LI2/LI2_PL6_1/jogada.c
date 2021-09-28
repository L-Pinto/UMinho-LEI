#include "jogada.h"
#include "estado.h"
#include <stdlib.h>
#include <stdio.h>

/**
 * Função que dá a peça contraria a fornecida.
 * @param p Peça fornecida.
 * @return Peça contraria.
 */
VALOR contraria (VALOR p) {
    VALOR v;
    if (p == VALOR_X) v=VALOR_O;
    else if (p == VALOR_O) v=VALOR_X;
    return v;
}

/**
 * Função que dá as posições válidas para uma peça no tabuleiro.
 * @param e Estado atual.
 * @param p Posição num tabuleiro.
 * @return Lista com as posições válidas para uma peça.
 */
DUPLA posValida (ESTADO e, PONTO p)
{
    int i=1, r=0, j=0;
    DUPLA d;
    PONTO pf;
    VALOR v = contraria(e.peca);
    while (p.l > 0 && e.grelha[p.l-i][p.c] == v) { i++; r = 1; } // cima
    if (  r == 1 && (p.l-i> -1) && e.grelha[p.l-i][p.c] == VAZIA)
    {
        pf.l = p.l-i;
        pf.c = p.c;
        d.pos[j]= pf;
        j++;
    }
    r=0; i=1;
    while (p.l < 7 && e.grelha[p.l+i][p.c] == v) { i++; r = 1; } // baixo
    if ( (p.l+i < 8) && r == 1 && e.grelha[p.l+i][p.c] == VAZIA)
    {
        pf.l = p.l+i;
        pf.c = p.c;
        d.pos[j]= pf;
        j++;
    }
    r=0; i=1;
    while (p.c > 0 && e.grelha[p.l][p.c-i] == v) { i++; r = 1; } // esquerda
    if ( (p.c-i> -1) && r == 1 && e.grelha[p.l][p.c-i] == VAZIA)
    {
        pf.l = p.l;
        pf.c = p.c-i;
        d.pos[j]= pf;
        j++;
    }
    r=0; i=1;
    while (p.c < 7 && e.grelha[p.l][p.c+i] == v) { i++; r = 1; } // direita
    if ( (p.c+i< 8) && r == 1 && e.grelha[p.l][p.c+i] == VAZIA)
    {
        pf.l = p.l;
        pf.c = p.c+i;
        d.pos[j]= pf;
        j++;
    }
    r=0; i=1;
    while (p.l > 0 && p.c > 0  && e.grelha[p.l-i][p.c-i] == v) { i++; r = 1; } // CE 00 --
    if ( (p.l-i> -1) && (p.c-i> -1) && r == 1 && e.grelha[p.l-i][p.c-i] == VAZIA)
    {
        pf.l = p.l-i;
        pf.c = p.c-i;
        d.pos[j]= pf;
        j++;
    }
    r=0; i=1;
    while (p.l > 0 && p.c < 7 && e.grelha[p.l-i][p.c+i] == v) { i++; r = 1; } // CD 08 -+
    if ( (p.l-i> -1) && (p.c+i< 8) && r == 1 && e.grelha[p.l-i][p.c+i] == VAZIA)
    {
        pf.l = p.l-i;
        pf.c = p.c+i;
        d.pos[j]= pf;
        j++;
    }
    r=0; i=1;
    while (p.l < 7 && p.c > 0  && e.grelha[p.l+i][p.c-i] == v) { i++; r = 1; } // BE 80 +-
    if ( (p.l+i < 8) && (p.c-i> -1) && r == 1 && e.grelha[p.l+i][p.c-i] == VAZIA)
    {
        pf.l = p.l+i;
        pf.c = p.c-i;
        d.pos[j]= pf;
        j++;
    }
    r=0; i=1;
    while (p.l < 7 && p.c < 7 && e.grelha[p.l+i][p.c+i] == v) { i++; r = 1; } // BD 88 ++
    if ( (p.l+i < 8) && (p.c+i< 8) && r == 1 && e.grelha[p.l+i][p.c+i] == VAZIA)
    {
        pf.l = p.l+i;
        pf.c = p.c+i;
        d.pos[j]= pf;
        j++;
    }
    d.tam=j;
    return d;
}

/**
 * Função que verifica se o ponto pertence as posições válidas de um jogador.
 * @param p Posição(peça) a verificar.
 * @param d Lista de posições válidas.
 * @return 0 se for falso, 1 se for verdade.
 */
int pertence (PONTO p, DUPLA d)
{
    int i,r=0;
    for (i =0; i < d.tam; i++)
    {
        if (d.pos[i].c == p.c && d.pos[i].l == p.l) r = 1;
    }
    return r;
}

/**
 * Função para imprimir as posições válidas no tabuleiro.
 * @param d Lista de posições válidas.
 * @param e Estado atual.
 */
void pontinhos (DUPLA d, ESTADO e)
{
    char c = ' ';
    PONTO p, ps;
    if (e.modo == 1) printf("A");
    else if (e.modo == 0) printf("M");
    if (e.peca == VALOR_O) printf(" O");
    else if (e.peca == VALOR_X) printf(" X");
    if (e.modo == 0) printf("\n");
    else if (e.modo == 1) printf(" %d\n", e.nivel);
    printf("  1 2 3 4 5 6 7 8 \n");
    for (int i = 0; i < 8; i++)
    {
        printf("%d ",i+1);
        for (int j = 0; j < 8; j++)
        {
            switch (e.grelha[i][j])
            {
                case VALOR_O: {
                    c = 'O';
                    break;
                }
                case VALOR_X: {
                    c = 'X';
                    break;
                }
                case VAZIA: {
                    p.l = i;
                    p.c = j;
                    if (pertence (p, d)) c = '*';
                    else c = '-';
                    break;
                }
            }
            printf("%c ", c);
        }
        printf("\n");
    }
    printf("\n");
    ps = score(e);
    printf("X: %d  O: %d\n\n",ps.l,ps.c);
}

/**
 * Função que junta as posições válidas de cada peça numa lista de posições válidas para o jogador.
 * @param d Lista de posições válidas de cada peça.
 * @param n Tamanho da lista.
 * @return Lista de posições válidas do jogador.
 */
DUPLA dPonto (DUPLA *d, int n)
{
    DUPLA h;
    int i,j,a=0;
    for (i = 0; i < n; i++)
    {
        for (j = 0; j < d[i].tam; j++)
        {
            h.pos[a] = d[i].pos[j];
            a++;
        }
    }
    h.tam = a;
    return h;
}

/**
 * Função que imprime uma sugestão de jogada.
 * @param d Lista de posições válidas.
 * @param e Estado atual.
 */
void sugestao (DUPLA d, ESTADO e)
{
    char c = ' ';
    PONTO p, ps;
    p = d.pos[0];
    if (e.modo == 1) printf("A");
    else if (e.modo == 0) printf("M");
    if (e.peca == VALOR_O) printf(" O");
    else if (e.peca == VALOR_X) printf(" X");
    if (e.modo == 0) printf("\n");
    else if (e.modo == 1) printf(" %d\n", e.nivel);
    printf("  1 2 3 4 5 6 7 8 \n");
    for (int i = 0; i < 8; i++)
    {
        printf("%d ",i+1);
        for (int j = 0; j < 8; j++)
        {
            switch (e.grelha[i][j])
            {
                case VALOR_O: {
                    c = 'O';
                    break;
                }
                case VALOR_X: {
                    c = 'X';
                    break;
                }
                case VAZIA: {
                    if (i == p.l && j == p.c) c = '?';
                    else c = '-';
                    break;
                }
            }
            printf("%c ", c);
        }
        printf("\n");
    }
    printf("\n");
    ps = score(e);
    printf("X: %d  O: %d\n\n",ps.l,ps.c);
}

/**
 * Função que dá as posições válidas do jogador.
 * @param e Estado atual.
 * @return Lista posições validas
 */
DUPLA valida (ESTADO e)
{
    int i,j,n=0;
    PONTO ponto;
    DUPLA h;
    DUPLA d [MAX_BUFFER];
    for(i=0;i<8;i++)
    {
        for (j=0;j<8;j++)
        {
            if ( e.peca == e.grelha[i][j] )
            {
                ponto.l = i;
                ponto.c = j;
                d[n]=posValida (e, ponto);
                n++;
            }
        }
    }
    h = dPonto(d,n);
    return h;
}

/**
 * Função que faz uma jogada.
 * @param e Estado atual.
 * @param p Posição onde jogar.
 * @return Estado com a jogada efetuada na posição p.
 */
ESTADO jogada (ESTADO e, PONTO p)
{
        int i=1, r=0;
        VALOR v = contraria(e.peca);
        e.grelha[p.l][p.c] = e.peca;
        while (p.l > 0 && e.grelha[p.l-i][p.c] == v) { i++; r = 1; } // cima
        if ( (p.l-i> -1) && r == 1 && e.grelha[p.l-i][p.c] == e.peca)
        {
            i=1;
            while(e.grelha[p.l-i][p.c] == v)
            {
                e.grelha[p.l-i][p.c] = e.peca;
                i++;
            }
        }
        r=0; i=1;
        while (p.l < 7 && e.grelha[p.l+i][p.c] == v) { i++; r = 1; } // baixo
        if ( (p.l+i < 8) && r == 1 && e.grelha[p.l+i][p.c] == e.peca)
        {
            i=1;
            while(e.grelha[p.l+i][p.c] == v)
            {
                e.grelha[p.l+i][p.c] = e.peca;
                i++;
            }
        }
        r=0; i=1;
        while (p.c > 0 && e.grelha[p.l][p.c-i] == v) { i++; r = 1; } // esquerda
        if ( (p.c-i> -1) && r == 1 && e.grelha[p.l][p.c-i] == e.peca)
        {
            i=1;
            while(e.grelha[p.l][p.c-i] == v)
            {
                e.grelha[p.l][p.c-i] = e.peca;
                i++;
            }
        }
        r=0; i=1;
        while (p.c < 7 && e.grelha[p.l][p.c+i] == v) { i++; r = 1; } // direita
        if ( (p.c+i< 8) && r == 1 && e.grelha[p.l][p.c+i] == e.peca)
        {
            i=1;
            while(e.grelha[p.l][p.c+i] == v)
            {
                e.grelha[p.l][p.c+i] = e.peca;
                i++;
            }
        }
        r=0; i=1;
        while (p.l > 0 && p.c > 0  && e.grelha[p.l-i][p.c-i] == v) { i++; r = 1; } // CE 00 --
        if ( (p.l-i> -1) && (p.c-i> -1) && r == 1 && e.grelha[p.l-i][p.c-i] == e.peca)
        {
            i=1;
            while(e.grelha[p.l-i][p.c-i] == v)
            {
                e.grelha[p.l-i][p.c-i] = e.peca;
                i++;
            }
        }
        r=0; i=1;
        while (p.l > 0 && p.c < 7 && e.grelha[p.l-i][p.c+i] == v) { i++; r = 1; } // CD 08 -+
        if ( (p.l-i> -1) && (p.c+i< 8) && r == 1 && e.grelha[p.l-i][p.c+i] == e.peca)
        {
            i=1;
            while(e.grelha[p.l-i][p.c+i] == v)
            {
                e.grelha[p.l-i][p.c+i] = e.peca;
                i++;
            }
        }
        r=0; i=1;
        while (p.l < 7 && p.c < 7  && e.grelha[p.l+i][p.c+i] == v) { i++; r = 1; } // BD 00 ++
        if ( (p.l+i < 8) && (p.c+i < 8) && r == 1 && e.grelha[p.l+i][p.c+i] == e.peca)
        {
            i=1;
            while(e.grelha[p.l+i][p.c+i] == v)
            {
                e.grelha[p.l+i][p.c+i] = e.peca;
                i++;
            }
        }
        r=0; i=1;
        while (p.l < 7 && p.c > 0 && e.grelha[p.l+i][p.c-i] == v) { i++; r = 1; } // BE 88 +-
        if ( (p.l+i < 8) && (p.c-i> -1) && r == 1 && e.grelha[p.l+i][p.c-i] == e.peca)
        {
            i=1;
            while(e.grelha[p.l+i][p.c-i] == v)
            {
                e.grelha[p.l+i][p.c-i] = e.peca;
                i++;
            }
        }
        return e;
}