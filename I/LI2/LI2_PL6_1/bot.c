#include "bot.h"
#include "estado.h"
#include <stdio.h>
#include "jogada.h"

/**
 * Função que faz a jogada do bot básico.
 * @param peca Peça do bot básico.
 * @param e Estado atual.
 * @return Estado com a jogada do bot básico.
 */
ESTADO bot1 (VALOR peca, ESTADO e)
{
    DUPLA d = valida(e);
    e = jogada(e, d.pos[0]);
    return e;
}

/**
 * Função que faz a jogada do bot médio.
 * @param peca Peça do bot médio.
 * @param e Estado atual.
 * @return Estado com a jogada do bot médio.
 */
ESTADO bot2 (VALOR peca, ESTADO e)
{
    int i, r=0, scoreM=0;
    ESTADO e1;
    DUPLA d = valida(e);
    PONTO p;
    for(i=0;i<d.tam;i++)
    {
        e1 = jogada(e, d.pos[i]);
        p = score(e1);
        if (peca == VALOR_X)
        {
            if (p.l > scoreM)
            {
                r = i;
                scoreM = p.l;
            }
        }
        else if (peca == VALOR_O)
        {
            if (p.c > scoreM)
            {
                r = i;
                scoreM = p.c;
            }
        }
    }
    e = jogada(e,d.pos[r]);
    return e;
}

/**
 * Função que atraves de uma condição testa se um ponto pertence ao canto
 * @param pos Ponto a ser verificado
 * @return Boleano Retorna 1 se o ponto pertence e 2 se não pertence ao canto
 */
int canto(PONTO pos)
{
    int r = 0;
    if ((pos.l == 7 && pos.c == 7) || (pos.l == 0 && pos.c == 0) || (pos.l == 0 && pos.c == 7) || (pos.l == 7 && pos.c == 0)) r = 1;
    return r;
}

/**
 * Função que atraves de uma condição testa se um ponto pertence ao meio do tabuleiro
 * @param pos Ponto a ser verificado
 * @return Boleano Retorna 1 se o ponto pertence e 2 se não pertence ao cmeio do tabuleiro
 */
int meio (PONTO p)
{
    int r = 0;
    if ((p.l >= 2 && p.l <= 5) && (p.c >= 2 && p.c <= 5) ) r = 1;
    return r;
}

/**
 * Função que atraves de uma condição testa se um ponto pertence á borda do tabuleiro
 * @param pos Ponto a ser verificado
 * @return Boleano Retorna 1 se o ponto pertence e 2 se não pertence á borda do tabuleiro
 */
int borda (PONTO p)
{
    int r = 0;
    if (((p.l == 0 || p.l == 7) && p.c >= 2 && p.c <= 5) || ((p.c == 0 || p.c == 7) && p.l >= 2 && p.l <= 5)) r=1;
    return r;
}

/**
 * Função que atraves de uma condição testa se um ponto pertence ás bordas dos cantos do tabuleiro
 * @param pos Ponto a ser verificado
 * @return Boleano Retorna 1 se o ponto pertence e 2 se não pertence ás bordas dos cantos do tabuleiro
 */
int pBorda(PONTO p)
{
    int r = 0;
    if (((p.l == 1 || p.l == 6) && p.c >= 2 && p.c <= 5) || ((p.c == 1 || p.c == 6) && p.l >= 2 && p.l <= 5)) r=1;
    return r;
}

/**
 * Função que divide a dupla em regiões do tauleiro para facilitar priorizar algumas posições
 * @param d Array com as posiçoes validas do bot
 * @param r Array que vai conter as diversas regiões
 * @return Array com as posiçoes divididas nas várias regiões
 */
REGIAO parte(DUPLA d, REGIAO r)
{
    int i;
    int a=0,b=0,c=0,e=0,f=0;
    for(i == 0; i < d.tam; i++)
    {
        if(canto(d.pos[i]))
        {
            r.reg1.pos[a] = d.pos[i];
            a++;
        }
        else if (borda(d.pos[i]))
        {
            r.reg2.pos[b] = d.pos[i];
            b++;
        }
        else if (meio(d.pos[i]))
        {
            r.reg3.pos[c] = d.pos[i];
            c++;
        }
        else if (pBorda(d.pos[i]))
        {
            r.reg4.pos[e] = d.pos[i];
            e++;
        }
        else
        {
            r.reg5.pos[f] = d.pos[i];
            f++;
        }
    }
    r.reg1.tam = a;
    r.reg2.tam = b;
    r.reg3.tam = c;
    r.reg4.tam = e;
    r.reg5.tam = f;
    return r;
}

/**
 * Função para determinar o maior score de uma lista de jogadas possiveis do bot.
 * @param moves Lista com score e indice da jogada do bot.
 * @param N Tamanho da lista de jogadas.
 * @return Indice da jogada com o score maximo.
 */
int maxScore (PONTO moves[], int N)
{
    int m=0,r=0;
    for (int i = 0; i < N; i++)
    {
        if (moves[i].c > m)
        {
            m = moves[i].c;
            r = moves[i].l;
        }
    }
    return r;
}

/**
 * Função que faz a jogada do bot profissional.
 * @param peca Peça do bot profissional.
 * @param e Estado atual.
 * @return Estado com a jogada do bot profissional.
 */
ESTADO bot3 (VALOR peca, ESTADO e, DUPLA d)
{
    PONTO p, move;
    PONTO moves[MAX_BUFFER];
    ESTADO e1;
    int i, ind;

    for (i = 0; i < d.tam; i++)
    {
        e1 = jogada(e, d.pos[i]);
        e1 = bot(3,contraria(e.peca), e1);
        p = score(e1);
        move.l = i;
        if (peca == VALOR_X) move.c = p.l;
        else  move.c = p.c;
        moves[i] = move;
    }
    ind = maxScore(moves, i);
    e = jogada(e, d.pos[ind]);
    return e;
}


/**
 * Função que prioriza uma região sobre a outra
 * @param peca Peça do bot
 * @param e Estado atual do jogo
 * @param r Array com as diversas regiões
 * @return Esatdo após a jogada do bot
 */
ESTADO prioridade (VALOR peca,ESTADO e,REGIAO r)
{
    if (r.reg1.tam != 0) e = bot3(peca,e,r.reg1);
    else if (r.reg2.tam != 0) e = bot3(peca,e,r.reg2);
    else if (r.reg3.tam != 0) e = bot3(peca,e,r.reg3);
    else if (r.reg4.tam != 0) e = bot3(peca,e,r.reg4);
    else if (r.reg5.tam != 0) e = bot3(peca,e,r.reg5);
    return e;
}

/**
 * Função menu dos bots.
 * @param nv Nivel do bot.
 * @param peca Peça do bot.
 * @param e Estado atual.
 * @return Estado com a jogada do bot.
 */
ESTADO bot( int nv, VALOR peca, ESTADO e)
{
    if (nv == 1) e = bot1(peca, e);
    else if (nv == 2) e = bot2(peca, e);
    else if (nv == 3)
    {
        DUPLA d = valida(e);
        REGIAO r = parte (d, r);
        e = prioridade(peca, e,r);
    }
    return e;
}
