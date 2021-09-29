#include "../include/catProdutos.h"

/*---ESTRUTURA PARA ARMAZENAR OS PRODUTOS---*/

struct produtos {
    GTree * catalogo;
    int lidos; /*numero de produtos lidos*/
};


/*Função que inicializa a estrutura PRODUTOS, alocando memória para tal.*/
PRODUTOS initPRODUTOS () {
    PRODUTOS p = malloc(sizeof(struct produtos));

    p->catalogo = g_tree_new_full((GCompareDataFunc) strcmp, NULL, g_free,g_free);
    p->lidos = 0;

    return p;
}

/*Dado um produto, a função 'valido_prod' verifica se ele é válido.
*
* @param produto
* @return 1(True) ou 0(False), se o produto for válido ou não.
*/
int valido_prod(char * prod_code) {
    return ((strlen(prod_code)==6)      && isupper(prod_code[0])
            && isupper(prod_code[1]) && isdigit(prod_code[2])
            && prod_code[2]!='0'        && isdigit(prod_code[3])
            && isdigit(prod_code[4]) && isdigit(prod_code[5]));
}

/*Função que, dada uma estrutura PRODUTOS como argumento, retorna a árvore do catálogo de produtos.
* @param estrutura dos PRODUTOS.
* @return árvore presente na estrutura dos produtos.
*/
GTree * getPRODUTOSCatalogo(PRODUTOS p) {
    return p->catalogo;
}

/*Função que, dada uma estrutura PRODUTOS como argumento, retorna o número de produtos que foram lidos.
* @param estrutura dos PRODUTOS.
* @return número de produtos lidos.
*/
int getPRODUTOSLidos(PRODUTOS p) {
    return p->lidos;
}

/*Função que, dada uma estrutura PRODUTOS como argumento, retorna o número de produtos válidos.
* @param estrutura dos PRODUTOS.
* @return numero de produtos válidos.
*/
int getPRODUTOSValidos(PRODUTOS p) {
    return g_tree_nnodes(p->catalogo);
}


/*Função que, dada uma estrutura PRODUTOS e um inteiro, atualiza o número de produtos lidos para esse inteiro.
*@param estrutura dos produtos.
*@param número de produtos lidos.   
*@return estrutura de produtos atualizada.
*/
PRODUTOS setPRODUTOSLidos (PRODUTOS p, int i) {
    p->lidos = i;
    return p;
}




/*-----ESTRUTURA PARA O VALUE DE CADA KEY(produto) NA ARVORE----*/
struct cprodvalue {
    int ncli[3];
    double qnt[3];
    char filial[4]; /*Filial*/
};


/*Função que inicializa a estrutura CPRODvalue, alocando memória para tal.*/
CPRODvalue initCPRODvalue() {
    CPRODvalue v = malloc(sizeof(struct cprodvalue));
    int i;

    for(i = 0; i < 3; i++) {
        v->qnt[i] = 0;
        v->ncli[i] = 0;
    }

    return v;
}

/*Função que, dado um CPRODvalue, retorna uma string que contém a concatenação 
*das filiais onde esse produto foi comprado.
* @param estrutura para o Value de cada produto, na árvore de produtos.
* @return string que contém a concatenação das filiais onde o produto foi comprado.
*/
char * getCPRODvalueFilial(CPRODvalue vp) {
    return vp->filial;
}

/*Função que, dado um CPRODvalue, retorna a quantidade do produto(que se encontra na key), vendido na filial
*recebida como argumento.
* @param estrutura para o Value de cada produto, na árvore de produtos.
* @param filial.
* @return a quantidade vendida daquele produto na filial recebida como argumento.
*/
double getCPRODvalueQnt(CPRODvalue v, int filial) {
    return v->qnt[filial];
}

/*Função que, dado um CPRODvalue, retorna o numero de clientes que compraram um produto(que se encontra na key),
*vendido na filial recebida como argumento.
* @param estrutura para o Value de cada produto, na árvore de produtos.
* @param filial.
* @return o numero de clientes que compraram aquele produto na filial recebida como argumento.
*/
int getCPRODvalueNCli(CPRODvalue v, int filial) {
    return v->ncli[filial];
}


/*Função que, dada a estrutura PRODUTOS e uma string(produto), insere o produto na árvore da estrutura, 
*juntamente com o CPRODvalue inicializado.
* @param estrutura dos PRODUTOS.
* @param produto.
* @return estrutura dos PRODUTOS, com o produto inserido.
*/
PRODUTOS insertPRODUTOSValue (PRODUTOS p, char * prod_code) {
    CPRODvalue v = initCPRODvalue();

    strcpy(v->filial,"0");

    g_tree_insert(p->catalogo,g_strdup(prod_code),v);

    return p;
}


/*Função que, dada a estrutura PRODUTOS, um produto, uma string contendo as filiais onde foi comprado o produto
* e a quantidade comprada, atualiza o value do produto recebido na árvore.
* @param estrutura dos PRODUTOS.
* @param produto.
* @param string que contém a concatenação das filiais onde esse produto foi comprado.
* @param quantidade do produto que foi vendido naquela venda.
*/
void replacePRODUTOSValue(PRODUTOS p, char * prod, char * v, double qtd) {
    CPRODvalue value = g_tree_lookup(p->catalogo,prod);
    CPRODvalue vp = initCPRODvalue();
    int f = (int) strtol(v,NULL,10);

    for(int i = 0; i < 3; i++) {
        vp->qnt[i] += value->qnt[i];
        vp->ncli[i] += value->ncli[i];
    }

    vp->qnt[f-1] += qtd;
    vp->ncli[f-1] += 1;

    if (strcmp(value->filial,"0") != 0 && strchr(value->filial,v[0]) == NULL) {
        strcpy(vp->filial,v);
        strcat(vp->filial,value->filial);
    } else if (strcmp(value->filial,"0") == 0) {
        strcpy(vp->filial,v);
    } else strcpy(vp->filial,value->filial);

    g_tree_replace(p->catalogo,g_strdup(prod),vp);
}

/*Função que destrói a estrutura PRODUTOS, libertando o espaço desta.
* @param estrutura dos PRODUTOS.
*/
void destroyPRODUTOS(PRODUTOS p) {
    g_tree_destroy(p->catalogo);
    g_free(p);
}



