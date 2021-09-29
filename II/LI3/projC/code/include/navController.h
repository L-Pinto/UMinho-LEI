#ifndef LI3_NAVCONTROLLER_H
#define LI3_NAVCONTROLLER_H

#include "navModel.h"
#include "navView.h"
#endif //LI3_NAVCONTROLLER_H
/*----------------------NAVEGADOR----------------------*/

void printLinhaNAVEGADOR(NAVEGADOR n);
NAVEGADOR paginasNAVEGADOR(NAVEGADOR nav, char *opcao, int max, int nrPag);
int leituraNAVEGADOR(NAVEGADOR nav);

/*----------------------NAVEGADOR11----------------------*/

void printLinhaNAVEGADORQ11(NAVEGADORQ11 n, int paginaAtual);
NAVEGADORQ11 paginasNAVEGADORQ11 (NAVEGADORQ11 n, char * opcao, int max, int total_pag);
int leituraNAVEGADORQ11 (NAVEGADORQ11 n);
















