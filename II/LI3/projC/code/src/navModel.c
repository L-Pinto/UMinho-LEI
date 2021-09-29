#include "../include/navModel.h"
/*--------------------------------NAVEGADOR------------------------------------------*/



/*--ESTRUTURAS--*/

/**
 * <estrutura NAVEGADOR para o navegador>
 */
struct navegador {
    char** catalogo; /**< ou clientes ou produtos>*/
    char letra; /**< inicialidos por letra>*/
    int tam;    /**< tamanho do catalogo>*/
    int pagina; /**< pagina atual>*/
    int filial;
    int limit;
    char produto[10];
    GTree * arvoreaux;
    GTree * treeaux;
    KEY chave;
    KEY12 chave12;
};

/**
 * <estrutura KEY para query 10>
 */
struct key{
    double qnt;
    char produto[10];
};

/**
 * <estrutura KEY12 para query 12>
 */
struct key12{
    double preco;
    char produto[10];
};

/*-------------PRIVATE---------------*/

/**
 * <metodo de ordenacao decrescente por quantidade para KEY's>
 * @param uma KEY
 * @param uma KEY
 * @return um inteiro de comparacao
 */
static gint compareKEYFavProd( KEY a, KEY b)
{
    gint res = 0;
    if (b->qnt - a->qnt > 0) res =1;
    else if (a->qnt - b->qnt >0) res = -1;
    return res;
}


/**
 * <metodo de ordenacao decrescente por preco para KEY12's>
 * @param uma KEY12
 * @param uma KEY12
 * @return um inteiro de comparacao
 */
static gint compareKEY12TopProfitProd(KEY12 a, KEY12 b)
{
    gint res = 0;
    if (b->preco - a->preco > 0) res =1;
    else if (a->preco - b->preco >0) res = -1;
    return res;
};
/*-------------PUBLIC---------------*/

/*--INIT--*/


/**
 * <inicializa a estrutura navegador>
 * @return um NAVEGADOR
 */
NAVEGADOR initNAVEGADOR()
{
    NAVEGADOR n = malloc(sizeof(struct navegador));
    n->arvoreaux = g_tree_new_full((GCompareDataFunc) compareKEYFavProd, NULL, g_free, g_free);
    n->treeaux = g_tree_new_full((GCompareDataFunc) compareKEY12TopProfitProd, NULL, g_free, g_free);
    n->catalogo = malloc( sizeof(char **));
    n->letra = ' ';
    n->tam = 0;
    n->pagina = 0;
    n->filial = 0;
    n->limit = 0;
    return n;
}

/*--GETS--*/


/**
 * @param um NAVEGADOR
 * @return tamanho do navegador
 */
int getNAVEGADORTam(NAVEGADOR nav) {
    return nav->tam;
}

/**
 * @param um NAVEGADOR
 * @return arvore auxiliar para a query 10
 */
GTree * getNAVEGADORArvoreaux10(NAVEGADOR n)
{
    return n->arvoreaux;
}

/**
 * @param um NAVEGADOR
 * @return tamanho da pagina do navegador
 */
int getNAVEGADORPag(NAVEGADOR nav){
    return nav->pagina;
}

/**
 * @param um NAVEGADOR
 * @return arvore auxiliar para a query 12
 */
GTree * getNAVEGADORArvoreaux12(NAVEGADOR n)
{
    return n->treeaux;
}

/**
 * @param um NAVEGADOR
 * @param um indice
 * @return um cliente ou um produto do catalogo do navegador
 */
char* getNAVEGADORCatalogo(NAVEGADOR nav,int i){
    return nav->catalogo[i];
}

/*--SETS--*/

/**
 * <atualiza o tamanho do navegador>
 * @param um NAVEGADOR
 * @param um tamanho
 * @return um NAVEGADOR
 */
NAVEGADOR setNAVEGADORTam(NAVEGADOR nav,int tam){
    nav->tam = tam;
    return nav;
}

/**
 * <atualiza a pagina do navegador>
 * @param um NAVEGADOR
 * @param a pagina
 * @return um NAVEGADOR
 */
NAVEGADOR setNAVEGADORPag(NAVEGADOR nav, int pag){
    nav->pagina = pag;
    return nav;
}

/**
 * <atualiza o catalogo do navegador(para ler os dados apartir do sitio certo)>
 * @param um NAVEGADOR
 * @param um valor
 * @return um NAVEGADOR
 */
NAVEGADOR setNAVEGADORCatalogo(NAVEGADOR nav, int novo){
    nav->catalogo = nav->catalogo - novo;
    return nav;
}

/**
 * <atualiza o produto no navegador)>
 * @param um NAVEGADOR
 * @param um codigo de produto
 * @return um NAVEGADOR
 */
NAVEGADOR setNAVEGADORProduto(NAVEGADOR nav, char * produto)
{
    strcpy(nav->produto, produto);
    return nav;
}

/**
 * < atualiza a filial do navegador>
 * @param um NAVEGADOR
 * @param uma filial
 * @return um NAVEGADOR
 */
NAVEGADOR setNAVEGADORFilial(NAVEGADOR n, int branchID)
{
    n->filial = branchID;
    return n;
}

/**
 * < atualiza a letra do navegador>
 * @param um NAVEGADOR
 * @param uma letra
 * @return um NAVEGADOR
 */
NAVEGADOR setNAVEGADORLetra(NAVEGADOR n, char letra)
{
    n->letra = letra;
    return n;
}

/**
 * <atualiza o limit do navegador>
 * @param um NAVEGADOR
 * @param um numero limite
 * @return um NAVEGADOR
 */
NAVEGADOR setNAVEGADORlimit(NAVEGADOR n,int limit)
{
    n->limit = limit;
    return n;
}

/*--FUNCOES--*/


/**
 * <insere produto comecado por uma letra no catalogo navegador>
 * @param key da arvore percorrida (produto)
 * @param value da arvore percorrida
 * @param um NAVEGADOR
 * @return um booleano
 */
gboolean insertNAVEGADORLetra(char* key, char* value, NAVEGADOR* data)
{
    if(key[0] == (*data)->letra)
    {
        (*data)->catalogo = realloc( (*data)->catalogo,((*data)->tam+1)*sizeof(char*) );
        (*data)->catalogo[(*data)->tam] = strdup(key);
        (*data)->tam++;
    }
    return FALSE;
}

/**
 * <insere prodNV no catalogo navegador>
 * @param key da arvore percorrida (prodNV)
 * @param value da arvore percorrida (CPRODvalue)
 * @param um NAVEGADOR
 * @return um booleano
 */
gboolean insertNAVEGADORProdNV(char* key, CPRODvalue cpv, NAVEGADOR* data)
{
    (*data)->catalogo = realloc( (*data)->catalogo,((*data)->tam+1)*sizeof(char*) );
    (*data)->catalogo[(*data)->tam] = strdup(key);
    (*data)->tam++;
    return FALSE;
}

/**
 * <insere produtos que nunca foram comprados no catalogo navegador>
 * @param key da arvore percorrida (produto)
 * @param value da arvore percorrida (CPRODvalue)
 * @param um NAVEGADOR
 * @return um booleano
 */
gboolean insertNAVEGADORFilialNV(char* key, CPRODvalue cpv, NAVEGADOR *data)
{
    if (strchr(getCPRODvalueFilial(cpv), ((*data)->filial+48)) == NULL) /**< nao foi vendido na filial>*/
    {
        (*data)->catalogo = realloc( (*data)->catalogo,((*data)->tam+1)*sizeof(char*));
        (*data)->catalogo[(*data)->tam] = strdup(key);
        (*data)->tam++;
    }
    return FALSE;
}

/**
 * <insere cliente que comprou em todas as filiais no catalogo navegador>
 * @param key da arvore percorrida(cliente)
 * @param value da arvore percorrida (string com filiais onde produto foi comprado)
 * @param um NAVEGADOR
 * @return um booleano
 */
gboolean insertNAVEGADORTodasFiliais(char* key, char*value, NAVEGADOR *data)
{
    if ( strchr(value, '1') && strchr(value, '2') && strchr(value, '3') ) /**<se foi vendido nas 3 filiais>*/
    {
        (*data)->catalogo = realloc( (*data)->catalogo,((*data)->tam+1)*sizeof(char*));
        (*data)->catalogo[(*data)->tam] = strdup(key);
        (*data)->tam++;
    }
    return FALSE;
}

/**
 * <insere produtoN ou produtoP ,no catalogo navegador>
 * @param key da arvore percorrida (produtoP ou produtoN)
 * @param value da arvore percorrida (VALUEClie)
 * @param um NAVEGADOR
 * @return um booleano
 */
gboolean insertNAVEGADORCliente(char * key, VALUEClie value, NAVEGADOR * data) {
    int r = getVALUEClieModoProduto(value,(*data)->produto);

    char produtoNP[10];
    strcpy(produtoNP,key);

    if (r == 0) strcat(produtoNP," N");
    else if (r == 1) strcat(produtoNP," P");

    if (r != -1) {
        (*data)->catalogo = realloc((*data)->catalogo, ((*data)->tam + 1) * sizeof(char *));
        (*data)->catalogo[(*data)->tam] = strdup(produtoNP);
        (*data)->tam++;
    }

    return FALSE;
}

/**
 * <para cada key da arvore verifica se há um produto igual ao que se quer inserir>
 * @param key da arvore percorrida (KEY)
 * @param value da arvore percorrida
 * @param um NAVEGADOR
 * @return boleano
 */
static gboolean removeNAVEGADORKeyIguais(KEY k, char* value, NAVEGADOR* data)
{
    if(strcmp(k->produto, (*data)->chave->produto) == 0)
    {
        (*data)->chave->qnt = k->qnt + (*data)->chave->qnt;/**< atualiza quantidade certa>*/
        g_tree_remove((*data)->arvoreaux, k);
    }
    return FALSE;
}

/**
 * <insere estrutura KEY na arvore auxiliar para a query 10>
 * @param key da arvore percorrida(produto)
 * @param value da arvore percorrida (VALUEProd)
 * @param um NAVEGADOR
 * @return um booleano
 */
gboolean insertNAVEGADORArvoreaux10(char * key, VALUEProd vp, NAVEGADOR * data)
{
    KEY k = malloc(sizeof(struct key));

    strcpy(k->produto,key);
    k->qnt = getVALUEProdQnt(vp);

    (*data)->chave = k;

    g_tree_foreach((*data)->arvoreaux,(GTraverseFunc)removeNAVEGADORKeyIguais, data);
    g_tree_insert((*data)->arvoreaux, k, NULL); /**< insere com quantidade certa>*/

    return FALSE;
}

/**
 * <insere estrutura KEY no catalogo navegador>
 * @param key da arvore percorrida (estrutura KEY)
 * @param value da arvore percorrida (VALUEProd)
 * @param um NAVEGADOR
 * @return um booleano
 */
gboolean insertNAVEGADORFavProduto(KEY k, VALUEProd vp, NAVEGADOR  *data)
{
    (*data)->catalogo = realloc((*data)->catalogo, ((*data)->tam + 1) * sizeof(char *));
    (*data)->catalogo[(*data)->tam] = strdup(k->produto);
    (*data)->tam++;
    return FALSE;
}

/**
 * <remove chaves iguais que estejam na arvore auxiliar da query 12>
 * @param key da arvore percorrida (estrutura KEY12)
 * @param value da arvore percorrida
 * @param um NAVEGADOR
 * @return um booleano
 */
static gboolean removeNAVEGADORKey12Iguais(KEY12 k12, char* value,NAVEGADOR * data)
{
    if(strcmp(k12->produto, (*data)->chave12->produto) == 0)
    {
        (*data)->chave12->preco = k12->preco + (*data)->chave12->preco;/**< atualiza preco certo>*/
        g_tree_remove((*data)->treeaux, k12);
    }
    return FALSE;
}

/**
 * < insere estrutura KEY12 na arvore auxiliar para a query 12>
 * @param key da arvore percorrida (produto)
 * @param value da arvore percorrida (VALUEProd)
 * @param um NAVEGADOR
 * @return um booleano
 */
gboolean insertNAVEGADORArvoreaux12(char* produto ,VALUEProd vp,NAVEGADOR * data)
{
    KEY12 k12 = malloc(sizeof(struct key12));

    strcpy(k12->produto,produto);
    k12->preco = getVALUEProdFact(vp);

    (*data)->chave12 = k12;

    g_tree_foreach((*data)->treeaux,(GTraverseFunc)removeNAVEGADORKey12Iguais, data);
    g_tree_insert((*data)->treeaux, k12 ,NULL);
    return FALSE;
}



/**
 * <Atualiza o navegador de forma a apresentar a informacao correta da pagina>
 * @param um NAVEGADOR
 * @param uma pagina
 * @param um numero para recuar ou avancar catalogo
 * @param um tamanho do catalogo
 * @return NAVEGADOR
 */
NAVEGADOR atualNAVEGADORPag(NAVEGADOR nav,int pagina,int catalogo, int tam){
    nav->pagina = nav->pagina +pagina;
    nav->catalogo = nav->catalogo +catalogo;  /**< recua apontador>*/
    nav->tam = tam;                    /**< tam maximo da pagina>*/
    return nav;
}

/*--DESTROY--*/


/**
 * <destroi a estrutura NAVEGADOR>
 */

void destroyNAVEGADOR(NAVEGADOR nav)
{
    for (int i = 0; i < nav->tam; ++i) {
        g_free(nav->catalogo[i]);
    }
    g_tree_destroy(nav->arvoreaux);
    g_tree_destroy(nav->treeaux);
    g_free(nav->catalogo);
    g_free(nav);
}
/*--------------------------NAVEGADOR 11----------------------------*/



/*--ESTRUTURAS--*/


/**
 * <estrutura para a query 11>
 */
struct navegadorq11{
    KEY11 *keys; 
    int tam;
    int pagina;
    int limite;
    int query;
};

/*--INIT--*/


/**
 * <inicializa estrutura navegadorq11>
 * @return um NAVEGADORQ11
 */
NAVEGADORQ11 initNAVEGADORQ11() {
    NAVEGADORQ11 nav = malloc(sizeof(struct navegadorq11));
    nav->keys = malloc(getKEY11Size());
    nav->tam = 0;
    nav->query = 0;
    nav->limite = 0;
    return nav;
}

/*--GETS--*/

/**
* @paragm NAVEGADORQ11
* @return query que o navegador imprime.
*/
int getNAVEGADORQ11Query(NAVEGADORQ11 n) {
    return n->query;
}


/**
 * @param NAVEGADORQ11
 * @return tamanho do NAVEGADORQ11
 */

int getNAVEGADORQ11Tam(NAVEGADORQ11 nav11){
    return nav11->tam;
}

/**
 * @param NAVEGADORQ11
 * @return pagina do NAVEGADORQ11
 */
int getNAVEGADORQ11Pag(NAVEGADORQ11 nav11){
    return nav11->pagina;
}


/**
 * @param NAVEGADORQ11
 * @param um indice
 * @return KEY11 do NAVEGADORQ11
 */
KEY11 getNAVEGADORQ11Keys(NAVEGADORQ11 n11, int i){
    return n11->keys[i];
}

/*--SETS--*/


/**
* atualiza a query no NAVEGADORQ11
* @param NAVEGADORQ11
* @param query
*/
NAVEGADORQ11 setNAVEGADORQ11Query(NAVEGADORQ11 n, int query) {
    n->query = query;
    return n;
}



/**
 * <atualiza o tamanho do NAVEGADORQ11>
 * @param NAVEGADORQ11
 * @param um tamanho
 * @return NAVEGADORQ11
 */
NAVEGADORQ11 setNAVEGADORQ11Tam(NAVEGADORQ11 nav11,int tam){
    nav11->tam = tam;
    return nav11;
}

/**
 * <atualiza a pagina do NAVEGADORQ11>
 * @param NAVEGADORQ11
 * @param uma pagina
 * @return NAVEGADORQ11
 */
NAVEGADORQ11 setNAVEGADORQ11Pag(NAVEGADORQ11 nav11, int pag){
    nav11->pagina = pag;
    return nav11;
}

/**
 * <atualiza o catalogo do NAVEGADORQ11(para comecar a ler os dados no sitio certo)>
 * @param NAVEGADORQ11
 * @param um valor
 * @return NAVEGADORQ11
 */
NAVEGADORQ11 setNAVEGADORQ11Catalogo(NAVEGADORQ11 nav11, int novo){
    nav11->keys = nav11->keys - novo;
    return nav11;
}

/**
 * <atualiza o limite do NAVEGADORQ11>
 * @param NAVEGADORQ11
 * @param um numero limite
 * @return NAVEGADORQ11
 */
void setNAVEGADORQ11Limite(NAVEGADORQ11 nav11, int limite) {
    nav11->limite = limite;
}

/*--FUNCOES--*/

/**
 * <Insere produto no navegadorq11, será usado na query 12>
 * @param key da arvore percorrida (KEY12)
 * @param value da arvore percorrida
 * @param um NAVEGADORQ11
 * @return um booleano
 */
gboolean insertNAVEGADORQ11TopProfitProd(KEY12 k12, char* value ,NAVEGADORQ11 *data)
{
    if((*data)->limite >0)
    {
        (*data)->keys = realloc((*data)->keys, ((*data)->tam + 1) * getKEY11Size());

        KEY11 aux11 = malloc(getKEY11Size());

        strcpy(getKEY11Produto(aux11), k12->produto);

        for (int i = 0; i < 3; i++) {
            setKEY11Qnt12(aux11,k12->preco);
        }

        (*data)->keys[(*data)->tam] = aux11;
        (*data)->tam++;

        (*data)->limite--;
        return FALSE;
    }
    
    return TRUE;
}


/**
 * <insere KEY11 na estrutura NAVEGADORQ11>
 * @param key da arvore percorrida (KEY11)
 * @param value da arvore percorrida
 * @param um NAVEGADORQ11
 * @return um booleano
 */
gboolean insertNAVEGADORQ11Top(KEY11 k11, char * value, NAVEGADORQ11 * data) {

    if((*data)->limite > 0)
    {
        (*data)->keys = realloc((*data)->keys, ((*data)->tam + 1) * getKEY11Size());

        KEY11 aux11 = malloc(getKEY11Size());

        strcpy(getKEY11Produto(aux11), getKEY11Produto(k11));

        for (int i = 0; i < 3; i++) {
            setKEY11Qnt(aux11, k11,i);
            setKEY11Vendas(aux11, k11,i);
        }

        (*data)->keys[(*data)->tam] = aux11;
        (*data)->tam++;

        (*data)->limite--;
        return FALSE;
    }
    return TRUE;
}

/**
 * <Atualiza o navegadorq11 de forma a apresentar a informacao correta da pagina>
 * @param um NAVEGADORQ11
 * @param uma pagina
 * @param um numero para recuar ou avancar catalogo
 * @param um tamanho do catalogo
 * @return NAVEGADORQ11
 */
NAVEGADORQ11 atualNAVEGADORQ11Pag(NAVEGADORQ11 nav,int pagina,int catalogo, int tam){
    nav->pagina = nav->pagina +pagina;
    nav->keys = nav->keys +catalogo;  
    nav->tam = tam;                    
    return nav;
}

/*--DESTROY--*/


/**
 * <destroi a estrutura navegadorq11>
 * @param NAVEGADORQ11
 */
void destroyNAVEGADORQ11(NAVEGADORQ11 nav11) {

    for (int i = 0; i < nav11->tam; i++)
        g_free(nav11->keys[i]);
    g_free(nav11->keys);
    g_free(nav11);
}

