#include "../include/catClientes.h"

/*-------------------------CATALOGO CLIENTES-------------------------*/
/*--ESTRUTURAS--*/

/**
 * <estrutura para catalogo clientes>
 */
struct clientes {
    GTree * catalogo;
    int lidos;
};

/*--INIT--*/


/**
 * @return inicializa struct clientes
 */
CLIENTES initCLIENTES()
{
    CLIENTES clientes = malloc(sizeof(struct clientes));
    clientes->lidos = 0;
    clientes->catalogo = g_tree_new_full((GCompareDataFunc) strcmp, NULL, g_free, g_free);

    return clientes;
}
/*--GETS--*/


/**
 * @param catalogo de clientes
 * @return numero o clientes validos
 */
int getCLIENTESValidos(CLIENTES clientes) {
    return g_tree_nnodes(clientes->catalogo);
}

/**
 * @param catalogo de clientes
 * @return numero clientes lidos
 */
int getCLIENTESLidos(CLIENTES clientes) {
    return clientes->lidos;
}

/**
 * @param catalogo de clientes
 * @return retorna a arvore onde estao armazenados clientes
 */
GTree* getCLIENTESCatalogo(CLIENTES clientes) {
    return clientes->catalogo;
}

/*--SETS--*/


/**
 * <atualiza o numero clientes lidos>
 * @param catalogo de clientes
 * @param numero de clientes lidos
 * @return catalogo clientes
 */
CLIENTES setCLIENTESLidos(CLIENTES clientes,int i)
{
    clientes->lidos =i;
    return clientes;
}

/*--FUNCOES--*/


/**
 * <insere cliente no catalogo clientes>
 * @param catalogo de clientes
 * @param um cliente
 * @return catalogo clientes
 */
CLIENTES insertCLIENTES(CLIENTES clientes, char * cli_code)
{
    char * v = "0";/**< NC (ainda nao comprou )>*/
    g_tree_insert(clientes->catalogo,g_strdup(cli_code),g_strdup(v)); /**< duplicação para lista de clientes>*/
    return clientes;
}

/**
 * <Muda o value do cliente indicando a filial onde efetuou a venda>
 * @param catalogo de clientes
 * @param um cliente
 * @param uma filial
 */
void replaceCLIENTESValue(CLIENTES clientes,char * cliente, char * filial)
{
    char * aux = g_tree_lookup(clientes->catalogo,cliente);
    if (strcmp(aux,"0") == 0) /**<inicia value filial>*/
    {
        g_tree_replace(clientes->catalogo,g_strdup(cliente), g_strdup(filial));
    }
    else if (strchr(aux,filial[0]) == NULL) /**<adiciona filiais diferentes>*/
    {
        strcat(filial, aux);
        g_tree_replace(clientes->catalogo,g_strdup(cliente), g_strdup(filial));
    }
}

/**
 * <Verifica se um cliente é valido>
 * @param cliente
 * @return booleano 
 */
int valido_cli(char * cli_code)
{
    long key = strtol(cli_code+1,NULL,10);

    return ((strlen(cli_code)==5) && isupper(cli_code[0])
            && isdigit(cli_code[1])&& isdigit(cli_code[2])
            && isdigit(cli_code[3]) && isdigit(cli_code[4])
            && (key >= 1000 && key<= 5000));
}

/*--DESTROY--*/


/**
 * <destroi estrutura clientes>
 * @param catalogo de clientes
 */
void destroyCLIENTES(CLIENTES clientes)
{
    g_tree_destroy(clientes->catalogo);
    g_free(clientes);
}
