#include "../include/interface.h"

/*----ESTRUTURA SGV(Sistema de Gestão de Vendas)----*/
struct sgv {
    FATURACAO faturacao;
    PRODUTOS produtos;
    CLIENTES clientes;
    GEST_FILIAL gestFilial;
    char pathc[100];
    char pathp[100];
    char pathv[100];
};

/*Função que, dado um SGV, retorna o caminho do ficheiro dos Clientes.*/
char * getSGVFileClient(SGV s) {
    return s->pathc;
}

/*Função que, dado um SGV, retorna o caminho do ficheiro dos Produtos.*/
char * getSGVFileProduct(SGV s) {
    return s->pathp;
}

/*Função que, dado um SGV, retorna o caminho do ficheiro das Vendas.*/
char * getSGVFileSales(SGV s) {
    return s->pathv;
}

/*Função que, dado um SGV, retorna e estrutura CLIENTES.*/
CLIENTES getSGVClientes(SGV s) {
    return s->clientes;
}

/*Função que, dado um SGV, retorna e estrutura PRODUTOS.*/
PRODUTOS getSGVProdutos(SGV s) {
    return s->produtos;
}

/*Função que, dado um SGV, retorna e estrutura FATURAÇÃO.*/
FATURACAO getSGVFaturacao(SGV s) {
    return s->faturacao;
}

/*Função que, dado um SGV, retorna e estrutura GEST_FILIAL.*/
GEST_FILIAL getSGVGestFilial(SGV s) {
    return s->gestFilial;
}

/*Função que inicializa o SGV, alocando memória para tal.*/
SGV initSGV () {
    SGV s = malloc(sizeof(struct sgv));

    s->faturacao = initFATURACAO();
    s->clientes = initCLIENTES();
    s->produtos = initPRODUTOS();
    s->gestFilial = initGEST_FILIAL();

    return s;
}


/*Função referente à query 1, que preenche as estruturas com as informações presentes em cada ficheiro.
* @param a estrutura SGV apenas com memória alocada.
* @param o caminho do ficheiro que contém os clientes.
* @param o caminho do ficheiro que contém os produtos.
* @param o caminho do ficheiro que contém as vendas.
* @return a estrutura SGV devidamente preenchida/atualizada.
*/
SGV loadSGVFromFiles(SGV s, char * clientsFilePath, char * productsFilePath, char * salesFilePath) {
    FILE * fc;
    FILE * fp;
    FILE * fv;

    strcpy(s->pathc,clientsFilePath);
    fc = fopen(clientsFilePath, "r");
    s->clientes = loadCLIENTESFromFiles(fc,s->clientes);
    fclose(fc);

    strcpy(s->pathp,productsFilePath);
    fp = fopen(productsFilePath, "r");
    loadPRODUTOSFromFiles(fp,s->produtos);
    fclose(fp);

    strcpy(s->pathv,salesFilePath);
    fv = fopen(salesFilePath, "r");
    loadVENDASFromFiles(fv,s->faturacao, s->gestFilial,s->clientes, s->produtos);
    fclose(fv);

    return s;
}


/* Função referente à query 2, que apresenta os produtos começados por uma dada letra.
* @param estrutura SGV.
* @param caracter que corresponde à letra cujos produtos queremos aceder.
* @return estrutura NAVEGADOR devidamente preenchida com os produtos começados pela 'letter'.
*/
NAVEGADOR getProductsStartedByLetter(SGV s,char letter) {
    NAVEGADOR n;
    n = initNAVEGADOR();
    n = setNAVEGADORLetra(n,letter);

    g_tree_foreach(getPRODUTOSCatalogo(s->produtos),(GTraverseFunc)insertNAVEGADORLetra, &n);

    return n;
}


/* Função referente à query 3 que, dado um mês e um produto, apresenta o número de vendas e faturação total, 
* dividindo em modo Promoção e modo Normal, apresentando resultados globais ou filial a filial.
* @param estrutura SGV.
* @param produto.
* @param mes.
* @return estrutura QUERY3 devidamente preenchida.
*/
QUERY3 getProductSalesAndProfit(SGV sgv, char * productID, int month) {
    QUERY3 q = initQUERY3();
    int i;

    for(i = 0; i < 3; i++) {
        q = setQUERY3VendasP(q,i,getFATURACAOVendasProd(sgv->faturacao,productID,month,1,i));
        q = setQUERY3VendasN(q,i,getFATURACAOVendasProd(sgv->faturacao,productID,month,0,i));
        q = setQUERY3FactP(q,i,getFATURACAOProd(sgv->faturacao,productID,month,1,i));
        q = setQUERY3FactN(q,i,getFATURACAOProd(sgv->faturacao,productID,month,0,i));
    }

    return q;
}


/* Função referente à query 4 que, dada uma filial, apresenta os produtos que nunca foram comprados nessa filial.
* @param estrutura SGV.
* @param filial.
* @return estrutura NAVEGADOR devidamente preenchida com os produtos a apresentar ao utilizador.
*/
NAVEGADOR getProductsNeverBought(SGV sgv, int branchID) {
    NAVEGADOR n = initNAVEGADOR();

    n = setNAVEGADORFilial(n,branchID);
    if (branchID == 0) g_tree_foreach(getFATURACAOCatalogoProdNV(sgv->faturacao),(GTraverseFunc)insertNAVEGADORProdNV, &n);
    else g_tree_foreach(getPRODUTOSCatalogo(sgv->produtos),(GTraverseFunc)insertNAVEGADORFilialNV, &n);

    return n;
}


/* Função referente à query 5 que retorna o NAVEGADOR preenchido com os clientes que compraram produtos em todas
* as filials.
* @param estrutura SGV.
* @return estrutura NAVEGADOR devidamente preenchida pronta para apresentar ao utilizador.
*/
NAVEGADOR getClientsOfAllBranches(SGV sgv) {
    NAVEGADOR n = initNAVEGADOR();

    g_tree_foreach(getCLIENTESCatalogo(sgv->clientes),(GTraverseFunc)insertNAVEGADORTodasFiliais, &n);

    return n;
}


/* Função referente à query 6 que retorna uma QUERY6 que contém informação sobre o número de clientes que não
* registaram compras e o número de produtos que ninguém comprou.
* @param estrutura SGV.
* @return estrutura QUERY6 devidamente preenchida pronta para apresentar ao utilizador.
*/
QUERY6 getClientsAndProductsNeverBoughtCount(SGV sgv) {
    QUERY6 q = initQUERY6();

    q = setQUERY6Cli(q,getGEST_FILIALClieNC(getSGVGestFilial(sgv)));
    q = setQUERY6Prod(q,getFATURACAOProdNV(getSGVFaturacao(sgv)));

    return q;
}


/* Função referente à query 7 que retorna uma QUERY7 que contém informação sobre o número de produtos comprados
* em cada mes, para cada filial, para um dado cliente recebido como argumento.
* @param estrutura SGV.
* @param cliente.
* @return estrutura QUERY7 devidamente preenchida pronta para apresentar ao utilizador.
*/
QUERY7 getProductsBoughtByClient(SGV sgv, char* clientID) {
    QUERY7 q = initQUERY7();
    int i;

    for(i = 0; i < 12; i++) {
        setQUERY7F1(q,getGEST_FILIALProdC(getSGVGestFilial(sgv), i, clientID, 0),i);
        setQUERY7F2(q,getGEST_FILIALProdC(getSGVGestFilial(sgv), i, clientID, 1),i);
        setQUERY7F3(q,getGEST_FILIALProdC(getSGVGestFilial(sgv), i, clientID, 2),i);
    }

    return q;
}


/* Função referente à query 8 que retorna uma QUERY8 que contém informação sobre a faturação total e o 
* número de registos de venda num dado intervalo de meses.
* @param estrutura SGV.
* @param mês mínimo.
* @param mês máximo.
* @return estrutura QUERY7 devidamente preenchida pronta para apresentar ao utilizador.
*/
QUERY8 getSalesAndProfit(SGV sgv, int minMonth, int maxMonth) {
    QUERY8 q8 = initQUERY8();

    int vendas = getFATURACAOVendasMes(sgv->faturacao,minMonth,maxMonth);
    double fact = getFATURACAOFactMes(sgv->faturacao,minMonth,maxMonth);

    setQUERY8(q8,fact,vendas);

    return q8;
}


/* Função referente à query 9 que retorna uma QUERY9 que contém informação sobre os clientes que compraram
* um dado produto numa dada filial.
* @param estrutura SGV.
* @param produto.
* @param filial.
* @return estrutura QUERY9. 
*/
NAVEGADOR getProductBuyers(SGV sgv, char * productID, int branch) {
    NAVEGADOR n = initNAVEGADOR();

    n = setNAVEGADORProduto(n,productID);
    g_tree_foreach(getGEST_FILIALClieC(sgv->gestFilial,branch),(GTraverseFunc)insertNAVEGADORCliente,&n);

    return n;
}



/* Função referente à query 10 que retorna um NAVEGADOR devidamente preenchido com os produtos mais comprados
* por um cliente num dado mês.
* @param estrutura SGV.
* @param cliente.
* @param mês.
* @return estrutura NAVEGADOR. 
*/
NAVEGADOR getClientFavoriteProducts(SGV sgv, char * clientID, int month) {
    NAVEGADOR n = initNAVEGADOR();
    int i,modo;
    VALUEClie vc;

    n = setNAVEGADORFilial(n,month);
    //n = setNAVEGADORCliente(n,clientID);

    for(i = 0; i < 3; i++) {
        vc = g_tree_lookup(getGEST_FILIALClieC(sgv->gestFilial, i),clientID);
        if (vc != NULL)
            for(modo = 0; modo < 2; modo++) {
                g_tree_foreach(getGEST_FILIALProdutos(vc, modo, month), (GTraverseFunc)insertNAVEGADORArvoreaux10,&n);
            }
    }

    g_tree_foreach(getNAVEGADORArvoreaux10(n),(GTraverseFunc)insertNAVEGADORFavProduto,&n);

    return n;
}


/*Função referente à query 11 que retorna um NAVEAGADORQ11 devidamente preenchido com os produtos por ordem
* decrescente de quantidade.
* @param estrutura SGV.
* @param limite de produtos a apresentar ao utilizador.
* @return estrutura NAVEGADORQ11. 
*/
NAVEGADORQ11 getTopSelledProducts(SGV sgv, int limit) {
    QUERY11 q11 = initQUERY11();
    NAVEGADORQ11 nav = initNAVEGADORQ11();

    setNAVEGADORQ11Limite(nav,limit);

    g_tree_foreach(getPRODUTOSCatalogo(sgv->produtos),(GTraverseFunc)insertCATALOGO, &q11);

    g_tree_foreach(getQUERY11Top(q11),(GTraverseFunc)insertNAVEGADORQ11Top,&nav);

    destroyQUERY11(q11);

    return nav;
}



/*Função referente à query 12 que retorna um NAVEGADOR devidamente preenchido com os produtos em que um dado
* cliente mais gastou dinheiro durante o ano.
* @param estrutura SGV.
* @param cliente.
* @param limite de produtos a apresentar ao utilizado.
* @return estrutura NAVEGADORQ11. 
*/
NAVEGADORQ11 getClientTopProfitProducts(SGV sgv, char * clientID, int limit) {
    NAVEGADORQ11 n = initNAVEGADORQ11();
    VALUEClie vc;
    NAVEGADOR nav = initNAVEGADOR();

    setNAVEGADORQ11Limite(n,limit);
    setNAVEGADORlimit(nav,limit);
    /*n = setNAVEGADORCliente(n,clientID);*/

    for(int i = 0; i < 3; i++) {
        vc = g_tree_lookup(getGEST_FILIALClieC(sgv->gestFilial, i), clientID);
        if (vc != NULL) {
            for (int modo = 0; modo < 2; modo++)
                for(int mes = 0; mes < 12; mes++)
                    g_tree_foreach(getGEST_FILIALProdutos(vc, modo, mes),(GTraverseFunc)insertNAVEGADORArvoreaux12,&nav);
        }
    }

    g_tree_foreach(getNAVEGADORArvoreaux12(nav),(GTraverseFunc)insertNAVEGADORQ11TopProfitProd,&n);
    destroyNAVEGADOR(nav);
    return n;
}


/*Função referente à query 13, que dada uma estrutura SGV, retorna a estrutura SGV preenchida.
* @param estrutura SGV.
* @return estrutura SGV.
*/
SGV getCurrentFilesInfo(SGV sgv) {
    return sgv;
}

/*Destrói a estrutura SGV, libertando a memória previamente alocada.*/
void destroySGV(SGV s) {
    destroyCLIENTES(s->clientes);
    destroyFATURACAO(s->faturacao);
    destroyPRODUTOS(s->produtos);
    destroyGEST_FILIAL(s->gestFilial);
    g_free(s);
}
