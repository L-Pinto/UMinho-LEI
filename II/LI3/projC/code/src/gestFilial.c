#include "../include/gestFilial.h"

/* ESTRUTURAS */

/*
* struct para o value dos produtos na gestao de filial
*/
struct valueProd{
    double preco;
    double qnt;
    int vendas;
};

/*
* struct para os modos promocao(1) normal(0)
*/
typedef struct modo{
    GTree* prod[2];
}MODO;


/*
* struct para o value dos clientes da gestão filial
*/
struct valueClie{
    MODO meses[12];
};

/*
* struct para cada filial
*/
typedef struct filial{
    GTree* clieC; 
}FILIAL;

/*
* struct para a gestão de filial
*/
struct gestFilial{
    FILIAL filiais[3];
    GTree* clieNC;
};

/* INIT E DESTROY */

/*
* <iniciliza as estruturas para o value clientes>
* @return estrutura para o value clientes
*/
static VALUEClie initVALUEClie()
{
    VALUEClie vc = malloc(sizeof(struct valueClie));

    for(int i = 0; i <12; i++)
    {
        for(int j = 0; j <2; j++)
        {
            vc->meses[i].prod[j] =  g_tree_new_full((GCompareDataFunc) strcmp, NULL, g_free, g_free);
        }
    }
    return vc;
}

/*
* destroi as estruturas dos value clientes
*/
static void destroyVALUEClie(VALUEClie vc)
{
    for (int i = 0; i < 12; i++)
    {
        for (int j = 0; j < 2; j++)
        {
            g_tree_destroy(vc->meses[i].prod[j]);
        }
    }
    g_free(vc);
}

/*
* inicializa a estrutura de gestão filial
*/
GEST_FILIAL initGEST_FILIAL()
{
    GEST_FILIAL gf = malloc(sizeof(struct gestFilial));
    gf->clieNC =  g_tree_new_full((GCompareDataFunc) strcmp, NULL, g_free, g_free);
    gf->filiais[0].clieC = g_tree_new_full((GCompareDataFunc) strcmp, NULL, g_free,(void *)destroyVALUEClie);
    gf->filiais[1].clieC = g_tree_new_full((GCompareDataFunc) strcmp, NULL, g_free,(void *)destroyVALUEClie);
    gf->filiais[2].clieC = g_tree_new_full((GCompareDataFunc) strcmp, NULL, g_free,(void *)destroyVALUEClie);
    return gf;
}

/*
* destroi a estrutura de gestão de filial
*/
void destroyGEST_FILIAL(GEST_FILIAL gf)
{
    g_tree_destroy(gf->filiais[0].clieC);
    g_tree_destroy(gf->filiais[1].clieC);
    g_tree_destroy(gf->filiais[2].clieC);
    g_tree_destroy(gf->clieNC);
    g_free(gf);
}

/* GETS */

/*
* @return número de clientes que nunca comprou
*/
int getGEST_FILIALClieNC(GEST_FILIAL gestFilal)
{
    return g_tree_nnodes(gestFilal->clieNC);
}

/*
* @param gestão de filial
* @param filial
* @return o catalogo dos clientes que compram na filial
*/
GTree* getGEST_FILIALClieC(GEST_FILIAL gf, int branch)
{
    return gf->filiais[branch].clieC;
}

/*
* @param um value de um produto
* @return a quantidade vendida de um detreminado produto
*/
double getVALUEProdQnt(VALUEProd value)
{
    return value->qnt;
}

/*
* @param um value de um produto
* @return a faturacao de um detreminado produto
*/
double getVALUEProdFact(VALUEProd value)
{
    return value->preco;
}

/*
* <Faz o somatorio das quantidades dos produtos comprados por um determinado cliente>
* @param produto
* @param value do produto
* @param contador (somatório)
* @return FALSE - a travasia da arvore nunca para
*/
gboolean getGEST_FILIALSomatorio(char* key, VALUEProd value, double* count)
{
    *count += value->qnt;
    return FALSE;
}

/*
* @param value de um cliente
* @param modo de compra
* @param mes em que comprou
* @return a arvore de produtos de um detreminado cliente num mes e num modo
*/
GTree* getGEST_FILIALProdutos(VALUEClie vc, int modo, int month)
{
    return vc->meses[month].prod[modo];
}

/*
* @param gestão de filial
* @param mes em que comprou
* @param cliente
* @param filial em que comprou
* @return a lista de produtos que foram comparados por um cliente numa dada filial num dado mes
*/
double getGEST_FILIALProdC(GEST_FILIAL gf, int mes, char* clientID, int filial)
{
    double count = 0;

    VALUEClie vc = g_tree_lookup(gf->filiais[filial].clieC, clientID);

    for(int i = 0; i <2; i++)
    {
        g_tree_foreach(vc->meses[mes].prod[i],(GTraverseFunc)getGEST_FILIALSomatorio, &count);
    }
    return count;
}

/*
* @param value cliente
* @param produto
* @return modo que um cliente comprou um produto (-1 - o cliente não comprou este produto)
*/
int getVALUEClieModoProduto(VALUEClie vc, char * produto)
{
    int i,j = 0,r = -1;

    for(i = 0; i < 12; i++)
    {
        for(j = 0; j < 2; j++)
        {
            if (g_tree_lookup(vc->meses[i].prod[j], produto) != NULL) r = j;
        }
    }
    return r;
}

/* SET */

/*
* <atualiza o catalogo dos clientes que nunca compraram>
* @param cliente
* @param gestão de filial
* @return gestão de filial atualizada
*/
GEST_FILIAL setGEST_FILIALClieNC(GEST_FILIAL fact, CLIENTES c)
{
    g_tree_foreach(getCLIENTESCatalogo(c),(GTraverseFunc)insertGEST_FILIALClieNC, fact);
    return fact;
}

/* INSERTS */

/*
* <insere uma venda na gestão de filial>
* @param venda em forma de string
* @param gestão de filial
* @return gestão de filial atualizada
*/
GEST_FILIAL insertGEST_FILIAL(GEST_FILIAL gf, char* venda)
{
    VALUEClie vc;
    VALUEProd vp;
    VALUEProd prod;

    int m = 1;
    char * produto = strtok(venda, " ");
    double preco = strtof(strtok(NULL ," "),NULL);
    double qnt = strtof(strtok(NULL, " "), NULL);
    char * promocao = strtok(NULL, " ");
    char * cliente = strtok(NULL, " ");
    long int mes = strtol(strtok(NULL, " "), NULL, 10);
    long int  filial = strtol(strtok(NULL, " "), NULL, 10);

    if(strcmp(promocao, "N") == 0) m = 0;

    vc = g_tree_lookup(gf->filiais[filial-1].clieC,cliente);

    if(vc == NULL)
    {
        vp = malloc(sizeof(struct valueProd));
        vc = initVALUEClie();
        vp->preco = preco*qnt;
        vp->qnt = qnt;
        vp->vendas = 1;
        g_tree_insert(vc->meses[mes-1].prod[m], g_strdup(produto), vp);
        g_tree_insert(gf->filiais[filial-1].clieC, g_strdup(cliente), vc);
    }
    else
    {
        vp = g_tree_lookup(vc->meses[mes-1].prod[m],produto);
        if(vp == NULL)
        {
            vp = malloc(sizeof(struct valueProd));
            vp->preco = preco*qnt;
            vp->qnt = qnt;
            vp->vendas = 1;
            g_tree_insert(vc->meses[mes-1].prod[m], g_strdup(produto), vp);
        }
        else
        {
            prod = malloc(sizeof(struct valueProd));
            prod->qnt = vp->qnt + qnt;
            prod->preco = vp->preco+ qnt*preco;
            prod->vendas = vp->vendas +1;
            g_tree_insert(vc->meses[mes-1].prod[m], g_strdup(produto), prod);
        }
    }
    return gf;
}

/*
* <insere um cliente que nunca comprou no catalogo>
* @param cliente
* @param value cliente
* @param gestão de filial
* @return FALSE - tranvesia da arvore nunca para 
*/
gboolean insertGEST_FILIALClieNC(char* key, char* value, GEST_FILIAL gf)
{
    if(strcmp(value, "0") == 0) g_tree_insert(gf->clieNC, g_strdup(key), NULL);
    return FALSE;
}

