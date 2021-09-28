#include "estado.h"

#ifndef PROJ_JOGADA_H
#define PROJ_JOGADA_H

#endif //PROJ_JOGADA_H

VALOR contraria (VALOR p);
DUPLA valida (ESTADO e);
DUPLA posValida (ESTADO e, PONTO p);
DUPLA dPonto (DUPLA * d, int n);
int pertence (PONTO p, DUPLA d);
void pontinhos (DUPLA d, ESTADO e);
void sugestao (DUPLA d, ESTADO e);
ESTADO jogada (ESTADO e, PONTO p);

