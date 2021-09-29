#include "../include/factGlobal.h"

/* ESTRUTURAS */

/*
* Strcut para o value dos produtos na arvore da faturacao
*/
typedef struct vendaProd{
    double preco;
    double qnt;
    int vendas;
}vendaPROD;

/* 
* struct para os modos promocao(1) normal(0)
*/
typedef struct modo {
    GTree* modo[2];
}MODO;

/* 
* struct para as 3 filiais
*/
typedef struct filial{
    MODO filial[3];
}FILIAL;

/* 
* struct para a faturacao
*/
struct faturacao{
    FILIAL meses[12];
    GTree* prodNV;
    int lidas;
    int validas;
};

/* INIT E DESTROY */

/*
* inicializa a estrutura da faturacao
*/
FATURACAO initFATURACAO()
{
    FATURACAO f = malloc(sizeof(struct faturacao));

    f->prodNV = g_tree_new_full((GCompareDataFunc) strcmp, NULL, g_free, g_free);
    f->lidas = 0;
    f->validas = 0;

    for(int i = 0; i< 12; i++)
    {
        for(int j = 0; j < 3; j++)
        {
            for(int k = 0; k<2 ; k++)
            {
                f->meses[i].filial[j].modo[k] = g_tree_new_full((GCompareDataFunc)strcmp, NULL, g_free, g_free);
            }
        }
    }
    return f;
}

/*
* destroi a estrutura da faturacao
*/
void destroyFATURACAO(FATURACAO f)
{
    g_tree_destroy(f->prodNV);

    for(int i = 0; i< 12; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            for(int k = 0; k<2 ; k++)
            {
                g_tree_destroy(f->meses[i].filial[j].modo[k]);
            }
        }
    }

    g_free(f);
}

/* SETS */

/*
* atualiza o número de vendas validas
*/
FATURACAO setFATURACAOValidas(FATURACAO fact, int i)
{
    fact->validas = i;
    return fact;
}

/*
* atualiza o número de vendas lidas
*/
FATURACAO setFATURACAOLidas(FATURACAO fact, int n)
{
    fact->lidas = n;
    return fact;
}

/* 
* atualiza a arvore de produtos nunca comprados
*/
FATURACAO setFATURACAOProdNV(FATURACAO fact, PRODUTOS p)
{
    g_tree_foreach(getPRODUTOSCatalogo(p),(GTraverseFunc)insertFATURACAOProvdNV, fact);
    return fact;
}

/* GETS */

/*
* @return número de vendas validas
*/
int getFATURACAOValidas(FATURACAO factGlobal)
{
    return factGlobal->validas;
}

/*
* @return número de vendas lidas
*/
int getFATURACAOLidas(FATURACAO factGlobal)
{
    return factGlobal->lidas;
}

/*
* @return número de produtos não comprados
*/
int getFATURACAOProdNV(FATURACAO factGlobal)
{
    return g_tree_nnodes(factGlobal->prodNV);
}

/*
* @return catalogo de produtos nunca vendidos
*/
GTree* getFATURACAOCatalogoProdNV(FATURACAO factGlobal)
{
    return factGlobal->prodNV;
}



/*
* @param faturacao
* @param produto
* @param mes em que o produto foi comprado
* @param modo como o produto foi vendido
* @param filial em que o produto foi comprado
* @return numero de vendas de um determinado produto
*/
int getFATURACAOVendasProd(FATURACAO fact, char * productID, int mes, int modo , int filial)
{
    vendaPROD* v;
    int res = 0;

    v = g_tree_lookup(fact->meses[mes].filial[filial].modo[modo],productID);

    if(v != NULL) res = v->vendas;

    return res;
}

/*
* @param faturacao
* @param produto
* @param mes em que o produto foi comprado
* @param modo como o produto foi vendido
* @param filial em que o produto foi comprado
* @return faturacao (preço) de um produto
*/
double getFATURACAOProd(FATURACAO fact, char * productID, int mes, int modo , int filial)
{
    vendaPROD* v;
    double res = 0;

    v = g_tree_lookup(fact->meses[mes].filial[filial].modo[modo], productID);

    if(v != NULL) res = v->preco;

    return res;
}

/*
* <Calcula o omatorio de todas as vendas dos produtos comprados por um cliente>
* @param produto
* @param value do produto
* @param countador (somatorio)
* @return FALSE - travesia da arvore nunca para
*/
static gboolean getFATURACAOSomatorioVendas(char* key, vendaPROD* value, int* count)
{
    *count += value->vendas;
    return FALSE;
}

/*
* <Calcula o de todas somatorio as faturcações dos produtos comprados por um cliente>
* @param produto
* @param value do produto
* @param countador (somatorio)
* @return FALSE - travesia da arvore nunca para
*/
static gboolean getFATURACAOSomatorioFact(char* key, vendaPROD* value, double* count)
{
    *count += value->preco;
    return FALSE;
}

/*
* @param faturacao 
* @param mes inicial (min)
* @param mes final (max)
* @return retorna as vendas entre dois meses (min e max)
*/
int getFATURACAOVendasMes(FATURACAO fact, int minMonth, int maxMonth)
{
    int count = 0;

    for(int filial = 0; filial< 3; filial++)
    {
        for(int i = minMonth; i<= maxMonth; i++)
        {
            for(int x = 0 ; x<2; x++)
            {
                g_tree_foreach(fact->meses[i].filial[filial].modo[x], (GTraverseFunc)getFATURACAOSomatorioVendas, &count);
            }
        }
    }
   return count;
}

/*
* @param faturacao 
* @param mes inicial (min)
* @param mes final (max)
* @return retorna a faturcao entre dois meses (min e max)
*/
double getFATURACAOFactMes(FATURACAO fact, int minMonth, int maxMonth)
{
    double count = 0;

    for(int filial = 0; filial< 3; filial++)
    {
        for(int i = minMonth; i<= maxMonth; i++)
        {
            for(int x = 0 ; x<2; x++)
            {
                g_tree_foreach(fact->meses[i].filial[filial].modo[x], (GTraverseFunc)getFATURACAOSomatorioFact, &count);
            }
        }
    }
    return count;
}



/* INSERTS */

/*
* <insere uma venda na faturacao>
* @param venda em forma de string
* @param faturacao
* @return faturaco atualizada
*/
FATURACAO insertFATURACAO(FATURACAO f, char* venda)
{
    vendaPROD* v;
    vendaPROD* VAR;

    int p = 1;
    char * produto = strtok(venda, " ");
    double preco = strtof(strtok(NULL ," "),NULL);
    double numero = strtof(strtok(NULL, " "), NULL);
    char * promocao = strtok(NULL, " ");
    char * cli = strtok(NULL, " ");
    long int mes = strtol(strtok(NULL, " "), NULL, 10);
    long int  filial = strtol(strtok(NULL, " "), NULL, 10);

    if(strcmp(promocao, "N") == 0) p = 0;

    VAR = g_tree_lookup(f->meses[mes-1].filial[filial-1].modo[p],produto);

    if(VAR == NULL)
    {
        v = malloc(sizeof(struct vendaProd));
        v->preco = preco*numero;
        v->qnt = numero;
        v->vendas = 1;
    }
    else
    {
        v = malloc(sizeof(struct vendaProd));
        v->preco = VAR->preco+ preco*numero;
        v->qnt = VAR->qnt+ numero;
        v->vendas = VAR->vendas +1;
    }

    g_tree_insert(f->meses[mes-1].filial[filial-1].modo[p], g_strdup(produto), v);
    return f;
}

/*
* <insere um produto que nunca comprou no catalogo>
* @param produto
* @param value produto
* @param faturacao
* @return FALSE - tranvesia da arvore nunca para 
*/
gboolean insertFATURACAOProvdNV(char* key, CPRODvalue value, FATURACAO fact)
{
    if(strcmp(getCPRODvalueFilial(value), "0") == 0) g_tree_insert(fact->prodNV, g_strdup(key), NULL);
    return FALSE;
}