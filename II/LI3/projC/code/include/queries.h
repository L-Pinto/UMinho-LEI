#include <glib.h>
#include <stdio.h>
#include "gestFilial.h"
#include "factGlobal.h"

typedef struct query3 * QUERY3;
typedef struct query6 * QUERY6;
typedef struct query7 * QUERY7;
typedef struct query8 * QUERY8;
typedef struct query11 * QUERY11;
typedef struct key11 * KEY11;

/*KEY11*/
KEY11 initKEY11();

size_t getKEY11Size();
char * getKEY11Produto(KEY11 k);
double getKEY11Qnt(KEY11 k, int filial);
int getKEY11Vendas(KEY11 k, int filial);

void setKEY11Qnt12(KEY11 k, double preco);
void setKEY11Produto(KEY11 chave, KEY11 k);
void setKEY11Qnt(KEY11 chave, KEY11 k, int filial);
void setKEY11Vendas(KEY11 chave, KEY11 k, int filial);


/*QUERY11*/
QUERY11 initQUERY11();
GTree * getQUERY11Top(QUERY11 q);
gboolean insertCATALOGO(char * produto, CPRODvalue v, QUERY11 * q11);
void destroyQUERY11(QUERY11 q11);


/*QUERY8*/
QUERY8 initQUERY8();

double getQUERY8Fact(QUERY8 q);
int getQUERY8Vendas(QUERY8 q);

void setQUERY8(QUERY8 q8, double fact, int vendas);
void destroyQUERY8(QUERY8 q8);


/*QUERY7*/
QUERY7 initQUERY7();

double getQUERY7F1(QUERY7 q, int mes);
double getQUERY7F2(QUERY7 q, int mes);
double getQUERY7F3(QUERY7 q, int mes);

void setQUERY7F1(QUERY7 q, double count, int mes);
void setQUERY7F2(QUERY7 q, double count, int mes);
void setQUERY7F3(QUERY7 q, double count, int mes);
void destroyQUERY7(QUERY7 q);


/*QUERY6*/
QUERY6 initQUERY6();

int getQUERY6Prod(QUERY6 q);
int getQUERY6Cli(QUERY6 q);

QUERY6 setQUERY6Prod(QUERY6 q, int i);
QUERY6 setQUERY6Cli(QUERY6 q, int i);
void destroyQUERY6(QUERY6 q);


/*QUERY3*/
QUERY3 initQUERY3();

double getQUERY3VendasP(QUERY3 q3, int fil);
double getQUERY3VendasN(QUERY3 q3, int fil);
double getQUERY3FactP(QUERY3 q3, int fil);
double getQUERY3FactN(QUERY3 q3, int fil);

QUERY3 setQUERY3VendasP(QUERY3 q, int i, double n);
QUERY3 setQUERY3VendasN(QUERY3 q, int i, double n);
QUERY3 setQUERY3FactN(QUERY3 q, int i, double n);
QUERY3 setQUERY3FactP(QUERY3 q, int i, double n);
void destroyQUERY3(QUERY3 q);
