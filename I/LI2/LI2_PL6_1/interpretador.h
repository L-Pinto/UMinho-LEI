#ifndef PROJ_INTERPRETADOR_H
#define PROJ_INTERPRETADOR_H
#include "estado.h"
#include "undo.h"
#endif //PROJ_INTERPRETADOR_H
TRIPLA verf_fich (char * ficheiro, TRIPLA t);
void printpront (ESTADO e);
TRIPLA interpretar (char *linha, TRIPLA t);
void interpretador(TRIPLA t);
void guardar (ESTADO e, char * ficheiro);
ESTADO ler (ESTADO e, char * ficheiro);
int passarJogada(ESTADO e);
ESTADO fimDeJogo (ESTADO e);