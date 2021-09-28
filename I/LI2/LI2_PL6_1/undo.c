#include "undo.h"
#include "estado.h"
#include <stdlib.h>
#include "interpretador.h"
#include <stdio.h>

/**
 * Função para retirar da stack o ultimo estado.
 * @param t recebe uma tripla com estado atual do jogo e a stack.
 * @return uma tripla sem o ultimo estado.
 */
TRIPLA pop(TRIPLA t)
{
    t.e = t.s->prox->e;
    t.s = t.s->prox;
    return t;
}

/**
 * Função para adicionar o estado atual a stack.
 * @param t recebe uma tripla com a stack e estado atual do jogo.
 * @return Uma stack com o estado atual adicionado.
 */
STACK *  push(TRIPLA t)
{
    STACK* novo;
    novo = malloc(sizeof(struct stack));
    novo->e = t.e;
    novo->prox = t.s;
    t.s = novo;
    return  t.s;
}