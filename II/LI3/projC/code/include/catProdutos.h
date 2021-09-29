#include <ctype.h>
#include <glib.h>

/*ESTRUTURAS*/
typedef struct produtos * PRODUTOS;
typedef struct cprodvalue * CPRODvalue;

/*GETS*/
char * getCPRODvalueFilial(CPRODvalue vp);
double getCPRODvalueQnt(CPRODvalue v, int filial);
int getCPRODvalueNCli(CPRODvalue v, int filial);

/*FUNÇÕES REFERENTES ÀS ESTRUTURAS*/
PRODUTOS initPRODUTOS ();
int valido_prod(char * prod_code);
GTree * getPRODUTOSCatalogo(PRODUTOS p);
int getPRODUTOSLidos(PRODUTOS p);
int getPRODUTOSValidos(PRODUTOS p);
PRODUTOS setPRODUTOSLidos (PRODUTOS p, int i);
PRODUTOS insertPRODUTOSValue (PRODUTOS p, char * prod_code);
void replacePRODUTOSValue(PRODUTOS p, char * prod, char * v, double qtd);
void destroyPRODUTOS(PRODUTOS p);



