#include "factGlobal.h"
#include "gestFilial.h"
#include <stdio.h>

int valido_vendas(char * venda, CLIENTES c, PRODUTOS p);
CLIENTES loadCLIENTESFromFiles(FILE *f, CLIENTES c);
PRODUTOS loadPRODUTOSFromFiles(FILE *f, PRODUTOS p);
void loadVENDASFromFiles(FILE *f,FATURACAO fact, GEST_FILIAL filial, CLIENTES c, PRODUTOS p);

