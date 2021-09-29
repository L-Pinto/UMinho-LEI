#ifndef LI3_GESTFILIAL_1_H
#define LI3_GESTFILIAL_1_H

#endif //LI3_GESTFILIAL_1_H

#include "catClientes.h"

/* Estruturas */
typedef struct gestFilial* GEST_FILIAL;
typedef struct valueClie* VALUEClie;
typedef struct valueProd* VALUEProd;

/* GEST_FILIAL - Init, destroy*/
GEST_FILIAL initGEST_FILIAL();
void destroyGEST_FILIAL(GEST_FILIAL gf);

/* GEST_FILIAL - gets */
int getGEST_FILIALClieNC(GEST_FILIAL gestFilal);
GTree* getGEST_FILIALClieC(GEST_FILIAL gf, int branch);
double getGEST_FILIALProdC(GEST_FILIAL gf, int mes, char* clientID, int filial);

/* GEST_FILIAL - get para produtos */
double getVALUEProdFact(VALUEProd value);
double getVALUEProdQnt(VALUEProd value);

/* GEST_FILIAL - get para os clientes */
int getVALUEClieModoProduto(VALUEClie vc, char * produto);
GTree* getGEST_FILIALProdutos(VALUEClie vc, int modo, int month);

/* GEST_FILIAL - set */
GEST_FILIAL setGEST_FILIALClieNC(GEST_FILIAL fact, CLIENTES c);

/* GEST_FILIAL - inserts */
GEST_FILIAL insertGEST_FILIAL(GEST_FILIAL gf, char* venda);
gboolean insertGEST_FILIALClieNC(char* key, char* value, GEST_FILIAL gf);

