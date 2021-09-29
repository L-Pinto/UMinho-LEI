#ifndef LI3_FACTGLOBAL_H
#define LI3_FACTGLOBAL_H

#endif //LI3_FACTGLOBAL_H

#include "catProdutos.h"

/* Estrutura */
typedef struct faturacao* FATURACAO;

/* FATURACAO - init, destroy */
FATURACAO initFATURACAO();
void destroyFATURACAO(FATURACAO f);

/* FATURACAO - set */
FATURACAO setFATURACAOValidas(FATURACAO fact, int i);
FATURACAO setFATURACAOLidas(FATURACAO fact, int n);
FATURACAO setFATURACAOProdNV(FATURACAO fact, PRODUTOS p);

/* FATURACAO - gets */
int getFATURACAOLidas(FATURACAO factGlobal);
int getFATURACAOValidas(FATURACAO factGlobal);
int getFATURACAOProdNV(FATURACAO factGlobal);
GTree* getFATURACAOCatalogoProdNV(FATURACAO factGlobal);

/* FATURACAO - gets (queries) */
double getFATURACAOProd(FATURACAO fact, char * productID, int mes, int modo , int filial);
int getFATURACAOVendasProd(FATURACAO fact, char * productID, int mes, int modo , int filial);
int getFATURACAOVendasMes(FATURACAO fact, int minMonth, int maxMonth);
double getFATURACAOFactMes(FATURACAO fact, int minMonth, int maxMonth);

/* FATURACAO - inserts */
FATURACAO insertFATURACAO(FATURACAO f, char* venda);
gboolean insertFATURACAOProvdNV(char* key, CPRODvalue value, FATURACAO fact);