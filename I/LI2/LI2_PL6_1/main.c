#include <stdio.h>
#include "estado.h"
#include "interpretador.h"
#include "jogada.h"
#include "undo.h"

/**
 * Função para iniciar inicia o jogo
 * @return int 0 ou 1
 */
int main()
{
    ESTADO e = {0};
    STACK *s = NULL;
    TRIPLA t;
    t.e = e;
    t.s = s;
    t.e.nivel = 0;
    interpretador(t);
    return 0;
}