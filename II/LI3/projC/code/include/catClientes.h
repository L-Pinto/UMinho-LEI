#ifndef LI3_CATCLIENTES_H
#define LI3_CATCLIENTES_H

#include <glib.h>
#include <ctype.h>

#endif //LI3_CATCLIENTES_H
/*-------------------------CATALOGO CLIENTES-------------------------*/
/*--ESTRUTURAS--*/
typedef struct clientes * CLIENTES;

/*--INIT--*/
CLIENTES initCLIENTES();

/*--GETS--*/
int getCLIENTESLidos(CLIENTES clientes);
int getCLIENTESValidos(CLIENTES clientes);
GTree* getCLIENTESCatalogo(CLIENTES clientes);

/*--SETS--*/
CLIENTES setCLIENTESLidos(CLIENTES clientes,int i);

/*--FUNCOES--*/
int valido_cli(char * cli_code);
CLIENTES insertCLIENTES(CLIENTES clientes, char * cli_code);
void replaceCLIENTESValue(CLIENTES clientes,char * cliente, char * filial);

/*--DESTROY--*/
void destroyCLIENTES(CLIENTES clientes);
