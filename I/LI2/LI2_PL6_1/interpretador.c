#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "interpretador.h"
#include "estado.h"
#include <ctype.h>
#define PRONT "Reversi"
#include "jogada.h"
#include "undo.h"
#include "bot.h"

/**
 * Função para indicar jogador atual e algumas informações básicas.
 * @param e Estado atual do jogo.
 */
void printpront (ESTADO e) {
    switch (e.peca) {
        case VALOR_X:
            printf("Para informações digite <I>\n");
            printf("%s X >", PRONT);
            break;
        case VALOR_O :
            printf("Para informações digite <I>\n");
            printf("%s O >", PRONT);
            break;
        default:
            printf("\nPara iniciar o jogo\n   | N <peca> : Novo Jogo Manual\n   | L <ficheiro> : Lê jogo de um ficheiro guardado\n   | A <peca> <nivel> : Novo jogo automático contra bot de certo nível\n");
            printf("Nota\n   | <peca>: X ou O\n   | <nivel>: 1, 2 ou 3\n");
            printf("%s ? >", PRONT);
    }
}

/**
 * Função para guardar o estado atual do jogo num ficheiro.
 * @param e Estado atual do jogo.
 * @param ficheiro ficheiro em que irá guardar o estado do jogo.
 */
void guardar (ESTADO e, char * ficheiro)
{
    FILE *f;
    f = fopen(ficheiro, "w");
    char c = ' ';
    char modoj;
    if (e.modo == 0) modoj = 'M';
    else if (e.modo == 1) modoj = 'A';
    if (e.peca == VALOR_O) fprintf(f, "%c O", modoj);
    else if (e.peca == VALOR_X) fprintf(f, "%c X", modoj);
    if (e.modo == 0) fprintf(f,"\n");
    else if (e.modo == 1) fprintf(f , " %d\n", e.nivel);
    for (int i = 0; i < 8; i++)
    {
        for (int j = 0; j < 8; j++)
        {
            c = valortochar(e.grelha[i][j]);
            fprintf(f, "%c ", c);
        }
        fprintf(f, "\n");
    }
    fprintf(f, "\n");
    fclose(f);
}

/**
 * Função que lê de um ficheiro o estado jogo.
 * @param e Estado atual do jogo.
 * @param ficheiro Ficheiro donde será lido o estado do jogo.
 * @return Retorna o estado lido.
 */
ESTADO ler (ESTADO e, char * ficheiro)
{
    FILE * f;
    char c;
    char modo[2],peca[2], nivel[2];
    int i = 0, j = 0;
    f = fopen(ficheiro, "r");

    fscanf(f,"%s %s %s\n", modo, peca, nivel);

    if (modo[0] == 'M') e.modo = 0;
    else if (modo[0] == 'A') e.modo = 1;
    if (peca[0] == 'X') e.peca = VALOR_X;
    else if (peca[0] == 'O') e.peca = VALOR_O;

    if (e.modo == 1) e.nivel = nivel[0] - 48;
    else { fseek(f, -2, SEEK_CUR);e.nivel = 0; }
    c = fgetc(f);
    while (c != EOF)
    {
        switch (c)
        {
            case 'O': {
                e.grelha[i][j] = VALOR_O;
                j++;
                break;
            }
            case 'X': {
                e.grelha[i][j] = VALOR_X;
                j++;
                break;
            }
            case '-': {
                e.grelha[i][j] = VAZIA;
                j++;
                break;
            }
            case '\n': {
                i++;
                j = 0;
                break;
            }
        }
        c = fgetc(f);
    }
    printa (e);
    fclose(f);
    return e;
}

/**
 * Função para verificar a existência de um ficheiro.
 * @param ficheiro Ficheiro que irá ser verificado.
 * @param t Tripla que contem o estado e a stack atual.
 * @return Tripla com estado atualizado.
 */
TRIPLA verf_fich (char * ficheiro, TRIPLA t)
{
    FILE *file;
    file = fopen(ficheiro, "r");
    if (file != NULL)
    {
        t.e = ler(t.e, ficheiro);
        t.e = bot(3, t.e.peca, t.e);
        t.e.peca = contraria(t.e.peca);
        guardar(t.e, ficheiro);
        fclose(file);
    }
    else
    {
        t.e.modo = 1;
        t.e.nivel = 3;
        t.e = novo_jogo(t.e, VALOR_X);
        guardar(t.e, ficheiro);
    }
    return t;
}

/**
 * Função para interpretar os comandos fornecidos pelo utilizador.
 * @param linha Comando inserido.
 * @param t Tripla com estado e stack atual.
 * @return Tripla com estado e stack atualizados.
 */
TRIPLA interpretar (char *linha, TRIPLA t) {
    char nivel[2];
    int nv;
    char cmd[MAX_BUFFER];
    char peca[MAX_BUFFER];
    char ficheiro[MAX_BUFFER];
    char nome_fich[MAX_BUFFER];
    char l[2], c[2];
    PONTO p;
    DUPLA d;
    VALOR pc, v;
    ESTADO e1 = {0};                    //mete tabuleiro nulo


    sscanf(linha,"%s",cmd);
    switch (toupper (cmd[0])) {
        case 'N' :
            t.s = NULL;                 // previne conflitos jogos anteriores
            sscanf(linha,"%s %s",cmd,peca);
            t.e = e1;
            t.e.nivel = 0;
            pc = chartovalor(toupper (peca[0]));
            if (pc != VAZIA)
            {
                t.e = novo_jogo(t.e,pc);
                t.e.modo = 0;
                t.s = push(t);
            }
            else printf("Peça inválida! \n");
            break;
        case 'L' :
            sscanf(linha,"%s %s",cmd,ficheiro);
            t.s = NULL;
            t.e = ler (t.e, ficheiro);
            t.s = push(t);
            break;
        case 'E' :
            sscanf(linha,"%s %s",cmd,ficheiro);
            guardar (t.e, ficheiro);
            break;
        case 'J' :
            sscanf(linha,"%s %s %s",cmd, l,c);
            p.l=l[0]-49;
            p.c=c[0]-49;
            d = valida(t.e);
            if (pertence(p,d))
            {
                t.e = jogada(t.e,p);
                t.e.peca = contraria(t.e.peca);
                if (t.e.modo == 0) t.s = push(t);
            }
            else
            {
                printf ("Posição Inválida! Tente Novamente!\n");
                break;
            }
            if (t.e.modo == 1)
            {
                if (passarJogada(t.e) == 1) printf("\nBOT PASSOU A JOGADA!\n\n");
                else t.e = bot(t.e.nivel, t.e.peca ,t.e);
                t.e.peca = contraria(t.e.peca);
                t.s = push(t);
            }
            break;
        case 'S' :
            d = valida(t.e);
            pontinhos(d,t.e);
            break;
        case 'H' :
            d = valida(t.e);
            sugestao(d,t.e);
            break;
        case 'U':
            if (t.s == NULL || t.s->prox == NULL) printf("Impossível retroceder.\n");
            else t = pop(t);
            break;
        case 'A' :
            sscanf(linha,"%s %s %s",cmd,peca,nivel);
            t.s = NULL;
            t.e = e1;
            t.e.nivel = nivel[0]-48;
            t.e.modo = 1;
            v = chartovalor(toupper (peca[0]));
            if (t.e.nivel == 1 ||t.e.nivel == 2 || t.e.nivel == 3) {
                if (v == VALOR_X) {
                    t.e = novo_jogo(t.e, v);
                    t.e = bot(t.e.nivel, v, t.e);
                    t.e.peca = contraria(t.e.peca);
                } else t.e = novo_jogo(t.e, contraria(v));
                t.s = push(t);
            }
            else
            {
                printf("Nível do bot inválido.\n");
                t.e.peca = VAZIA;
            }
            break;
        case 'I' :
            printf("N <peca> - Novo Jogo.\n"
                   "L <ficheiro> - Lê um ficheiro guardado.\n"
                   "E <ficheiro> - Guarda o jogo atual num ficheiro.\n"
                   "J <l> <c> - Joga a peça na posição desejada.\n"
                   "S - Para imprimir um ponto nas posições com jogada válida.\n"
                   "H - Para sugestão de jogada.\nU - Para desfazer a última jogada.\n"
                   "A <peca> <nivel> - Novo jogo contra bot em que o computador joga com a peça, com um nível(1 a 3).\n"
                   "Q - Sair do jogo.\n");
            break;
        case 'C' :
            sscanf(linha, "%s %s", cmd, ficheiro);
            if (ficheiro[0] != '\0')
            {
                t = verf_fich(ficheiro, t);
                strcpy(nome_fich,ficheiro);
            }
            else
            {
                if(nome_fich[0] == '\0') printf("Ficheiro inexistente. Ainda não foi guardado nenhum ficheiro");
                else
                {
                    t.e = ler(t.e, nome_fich);
                    t.e = bot(3, t.e.peca, t.e);
                    t.e.peca = contraria(t.e.peca);
                    guardar(t.e, nome_fich);
                }
            }
            break;
        case 'Q' :
            exit(0);
        default:
            printf("Comando inválido. Ler as regras!\n");
    }
    return t;
}

/**
 * Função para passar jogada.
 * @param e Estado atual.
 * @return Retorna três valores:
 * 0 caso o jogador atual tenha posições válidas;
 * 1 caso o jogador atual não tenha posições válidas;
 * 2 caso o ambos jogadores não tenha posições válidas(sinal de fim de jogo);
 */
int passarJogada (ESTADO e)
{
    DUPLA d = valida(e);
    int r = 0;
    if (d.tam == 0)
    {
        e.peca = contraria(e.peca);
        d = valida(e);
        r = 1;
        if (d.tam == 0) r = 2;
    }
    return r;
}

/**
 * Função para terminar o jogo e indicar o vencedor do jogo.
 * @param e Estado atual.
 * @return Estado final de jogo.
 */
ESTADO fimDeJogo (ESTADO e)
{
    PONTO p;

    p = score(e);
    if (p.l > p.c) printf("'X' GANHOU O JOGO! PARABENS!\n");
    else if (p.l ==p.c) printf("EMPATOU O JOGO!\n");
    else printf("'O' GANHOU O JOGO! PARABENS!\n");
    printf("FIM DE JOGO\n\n");
    e.peca = VAZIA;
    return  e;
}

/**
 * Função para gerar o tabuleiro através dos comandos fornecidos pelo utilizador.
 * @param t Tripla com estado e stack atual.
 */
void interpretador(TRIPLA t) {
    char linha[MAX_BUFFER];
    printpront(t.e);
    int r;
    while(fgets(linha,MAX_BUFFER,stdin))
    {
        t = interpretar(linha, t);
        r = passarJogada(t.e);
        if (linha[0]!= 'L' && linha[0]!= 'l'  && linha[0]!= 'S' && linha[0]!= 's' && linha[0]!= 'H' && linha[0]!= 'h') printa(t.e);
        if (r == 1)
        {
            printf("'%c' PASSOU A JOGADA!\n\n", valortochar(t.e.peca));
            t.e.peca = contraria(t.e.peca);
            if (t.e.modo == 1)
            {
                t.e = bot(t.e.nivel, t.e.peca, t.e);
                t.e.peca = contraria(t.e.peca);
                printa(t.e);
                if (passarJogada(t.e) == 2 && t.e.peca != VAZIA) { t.e = fimDeJogo(t.e); t.s = NULL; }
            }
        }
        else if (r == 2 && t.e.peca != VAZIA) { t.e = fimDeJogo(t.e); t.s = NULL; }
        printpront(t.e);
    }
}