#ifndef LI3_NAVEGADOR_H
#define LI3_NAVEGADOR_H

#endif //LI3_NAVEGADOR_H

#include "queries.h"
/*--------------------------------NAVEGADOR------------------------------------------*/
/*--ESTRUTURAS--*/
typedef struct navegador *NAVEGADOR;
typedef struct key *KEY;
typedef struct key12 *KEY12;

/*--Init--*/
NAVEGADOR initNAVEGADOR();

/*--GETS--*/
GTree * getQUERY11Top(QUERY11 q);
int getNAVEGADORTam(NAVEGADOR nav);
int getNAVEGADORPag(NAVEGADOR nav);
GTree * getNAVEGADORArvoreaux12(NAVEGADOR n);
GTree * getNAVEGADORArvoreaux10(NAVEGADOR n);
char* getNAVEGADORCatalogo(NAVEGADOR nav,int i);

/*--SETS--*/
NAVEGADOR setNAVEGADORTam(NAVEGADOR nav,int PAG);
NAVEGADOR setNAVEGADORPag(NAVEGADOR nav, int pag);
NAVEGADOR setNAVEGADORlimit(NAVEGADOR n,int limit);
NAVEGADOR setNAVEGADORLetra(NAVEGADOR n, char letra);
NAVEGADOR setNAVEGADORFilial(NAVEGADOR n, int branchID);
NAVEGADOR setNAVEGADORCatalogo(NAVEGADOR nav, int novo);
NAVEGADOR setNAVEGADORProduto(NAVEGADOR nav, char * produto);

/*--FUNCOES--*/
NAVEGADOR atualNAVEGADORPag(NAVEGADOR nav,int pagina,int catalogo, int tam);
//Inserts--NAVEGADOR
gboolean insertNAVEGADORLetra(char* key, char* value, NAVEGADOR* data);
gboolean insertNAVEGADORFavProduto(KEY k, VALUEProd vp, NAVEGADOR  *data);
gboolean insertNAVEGADORProdNV(char* key, CPRODvalue cpv, NAVEGADOR* data);
gboolean insertNAVEGADORFilialNV(char* key, CPRODvalue cpv, NAVEGADOR *data);
gboolean insertNAVEGADORTodasFiliais(char* key, char*value, NAVEGADOR *data);
gboolean insertNAVEGADORCliente(char * key, VALUEClie value, NAVEGADOR * data);
gboolean insertNAVEGADORArvoreaux10(char * key, VALUEProd vp, NAVEGADOR * data);
gboolean insertNAVEGADORArvoreaux12(char* produto ,VALUEProd vp,NAVEGADOR * data);


/*--DESTROY--*/
void destroyNAVEGADOR(NAVEGADOR nav);


/*--------------------------------NAVEGADORQ11------------------------------------------*/


/*--ESTRUTURAS--*/
typedef struct navegadorq11 * NAVEGADORQ11;

/*--Init--*/
NAVEGADORQ11 initNAVEGADORQ11();

/*--GETS--*/
int getNAVEGADORQ11Query(NAVEGADORQ11 n);
int getNAVEGADORQ11Tam(NAVEGADORQ11 nav11);
int getNAVEGADORQ11Pag(NAVEGADORQ11 nav11);
KEY11 getNAVEGADORQ11Keys(NAVEGADORQ11 n11, int i);

/*--SETS--*/
NAVEGADORQ11 setNAVEGADORQ11Tam(NAVEGADORQ11 nav11,int tam);
NAVEGADORQ11 setNAVEGADORQ11Pag(NAVEGADORQ11 nav11, int pag);
NAVEGADORQ11 setNAVEGADORQ11Catalogo(NAVEGADORQ11 nav11, int novo);
void setNAVEGADORQ11Limite(NAVEGADORQ11 nav, int limite);

/*--FUNCOES--*/
gboolean insertNAVEGADORQ11TopProfitProd(KEY12 k12, char* value,NAVEGADORQ11 * data);
gboolean insertNAVEGADORQ11Top(KEY11 k, char * value, NAVEGADORQ11 * data);
NAVEGADORQ11 atualNAVEGADORQ11Pag(NAVEGADORQ11 nav,int pagina,int catalogo, int tam);

/*--DESTROY--*/
void destroyNAVEGADORQ11(NAVEGADORQ11 nav);
