#include <glib.h>
#include <time.h>
#include "../include/interface.h"
#include "../include/view.h"
#include "../include/navController.h"

/* Verificação de ficheiros */

/*
* <verifica se ficheiros introduzidos pelo o utilizador sao validos>
* @param ficheiro de vendas, clientes e produtos
* @return int - 1 : ficheiros invalidos 2 : ficheiros validos
*/
int verificaFicheiros(char* path_def_C, char *path_def_P, char*path_def_V)
{
    FILE* fc, *fp, *fv;

    fc = fopen(path_def_C, "r");
    if(fc == NULL) return 1;
    fclose(fc);

    fp = fopen(path_def_P, "r");
    if(fp == NULL) return 1;
    fclose(fp);

    fv = fopen(path_def_V, "r");
    if(fv == NULL) return 1;
    fclose(fv);

    return 0;
}

/* Controlo de Output */

/*
* <Funcao auxiliar de controlo de output para a query 7>
* @param estrutura da query 7
* @param cliente
*/
void printQUERY7(QUERY7 q7, char* clientID)
{
    double f1, f2, f3;
    showQUERY7Client(clientID);

    showTabela("\n-----------------------------------------");

    showQUERY7Filial();

    for(int i = 0; i < 12; i++)
    {
        f1 = getQUERY7F1(q7, i);
        f2 = getQUERY7F2(q7,i);
        f3 = getQUERY7F3(q7, i);

        showQUERY7(i, f1, f2, f3);
    }
    showTabela("-----------------------------------------");
}

/*
* <Funcao auxiliar de controlo de output para a query 3 (Modo Filial)>
* @param estrutura da query 3
*/
void printQUERY3Filial(QUERY3 q3)
{
    int vn, vp;
    double fn, fp;

    for(int i = 0; i <3; i++)
    {
        vn = getQUERY3VendasN(q3,i);
        vp = getQUERY3VendasP(q3,i);
        fn = getQUERY3FactN(q3,i);
        fp = getQUERY3FactP(q3,i);

        showQUERY3Filial(i, vn, vp, fn, fp);
    }
}

/*
* <Funcao auxiliar de controlo de output para a query 3 (Modo Global)>
* @param estrutura da query 3
*/
void printQUERY3Global(QUERY3 q3)
{
    int total_vendasN = 0, total_vendasP = 0;
    double total_factN = 0, total_factP = 0;

    for(int i = 0; i <3; i++)
    {
        total_vendasN += getQUERY3VendasN(q3,i);
        total_vendasP += getQUERY3VendasP(q3,i);
        total_factN += getQUERY3FactN(q3,i);
        total_factP += getQUERY3FactP(q3,i);
    }

    showQUERY3Global(total_vendasN, total_vendasP, total_factN, total_factP);
}

/* MENU */

/* 
* <MENU>
* @param opção do utilizador
* @param sgv
* @return sgv atualizada
*/
SGV menu (char * opcao, SGV s)
{
    char op;
    char* path_def_C = "../projC/docs/Clientes.txt";
    char* path_def_V = "../projC/docs/Vendas_1M.txt";
    char* path_def_P = "../projC/docs/Produtos.txt";
    char path_novo_C[100], path_novo_V[100], path_novo_P[100] ;
    int escolha = (int)strtol(opcao, NULL, 10);
    char letra, res;
    char produto[8];
    char cliente[6];
    char m[2];
    int mes;
    int brunchID;
    char filial;
    char filial_novo[2];
    char mesMin[2];
    char mesMax[2];
    char limite[100];
    NAVEGADOR nav;
    QUERY3 q3;
    QUERY6 q6;
    QUERY7 q7;
    QUERY8 q8;
    NAVEGADORQ11 nav11;
    
    if(escolha > 14 || escolha <= 0) {showInvalido(); return s;}
    else showQuery(escolha);

    
    switch(escolha)
    {
        case 1 : {
            showQUERY1Msg();
            showPoiter();
            scanf(" ");
            scanf("%c", &op);
            switch (toupper(op))
            {
                case 'Y':{
                    int nodos = getCLIENTESValidos(getSGVClientes(s));
                    if (nodos != 0){destroySGV(s); s = initSGV();}
		            if (verificaFicheiros(path_def_C, path_def_P, path_def_V) == 1) {showWrongPreDefineFile(); break;}
                    s = loadSGVFromFiles(s, path_def_C, path_def_P, path_def_V);
                    break;
                }
                case 'N':
                {
                    if (getCLIENTESValidos(getSGVClientes(s)) != 0)
                    {
                        destroySGV(s);
                        s = initSGV();
                    }
                    showMessage("\nCaminho para o ficheiro de Dados dos Produtos: ");
                    showPoiter();
                    scanf("%s", path_novo_P);
                    showMessage("\nCaminho para o ficheiro de Dados dos Clientes: ");
                    showPoiter();
                    scanf("%s", path_novo_C);
                    showMessage("\nCaminho para o ficheiro de Dados dos Vendas: ");
                    showPoiter();
                    scanf("%s", path_novo_V);
                    if (verificaFicheiros(path_novo_C, path_novo_P,path_novo_V) == 1) {showWrongFile(); break;}
                    s = loadSGVFromFiles(s, path_novo_C, path_novo_P, path_novo_V);
                    break;
                }
                default:
                    showInvalido();
                    break;
            }
            break;
        }
        case 2 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            showMessage("Escreva uma Letra: ");
            showPoiter();
            scanf(" ");
            scanf("%c", &letra);
            if(isalpha(letra))
            {
                letra = toupper(letra);
                nav = getProductsStartedByLetter(s, letra);
                showQUERY2(letra, getNAVEGADORTam(nav));
                leituraNAVEGADOR(nav);
                destroyNAVEGADOR(nav);
            }
            else showInvalido();
            break;
        case 3 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            showMessage("Escreva um Produto: ");
            showPoiter();
            scanf("%s", produto);
            showMessage("Escreva um Mes: ");
            showPoiter();
            scanf(" ");
            scanf("%s",m);
            mes = (int) strtol(m, NULL, 10);
            if(valido_prod(produto) && (mes >= 1 && mes <= 12))
            {
                showQUERY3Msg();
                showPoiter();
                scanf(" ");
                scanf("%c", &res);
                q3 = getProductSalesAndProfit(s, produto, mes-1);
                switch(toupper(res))
                {
                    case 'G':
                        printQUERY3Global(q3);
                        break;
                    case 'F':
                        printQUERY3Filial(q3);
                        break;
                    default:
                        showInvalido();
                        break;
                }
                destroyQUERY3(q3);
            }
            else showInvalido();
            break;

        case 4 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            showMsgQUERY4();
            showPoiter();
            scanf(" ");
            scanf("%s", filial_novo);
            brunchID = (int) strtol(filial_novo, NULL,10);
            if(isdigit(filial_novo[0]) && brunchID >= 0 && brunchID <= 3)
            {
                nav = getProductsNeverBought(s, brunchID);
                showQUERY4(brunchID, getNAVEGADORTam(nav));
                leituraNAVEGADOR(nav);
                destroyNAVEGADOR(nav);
            }
            else showInvalido();
            break;
        case 5 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            nav = getClientsOfAllBranches(s);
            showQUERY5(getNAVEGADORTam(nav));
            leituraNAVEGADOR(nav);
            destroyNAVEGADOR(nav);
            break;
        case 6 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            q6  = getClientsAndProductsNeverBoughtCount(s);
            int nprod = getQUERY6Prod(q6);
            int ncli = getQUERY6Cli(q6);
            showQUERY6(nprod, ncli);
            destroyQUERY6(q6);
            break;
        case 7 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            showMessage("Insira um Cliente: ");
            showPoiter();
            scanf("%s", cliente);
            if(g_tree_lookup(getCLIENTESCatalogo(getSGVClientes(s)), cliente) != NULL)
            {
                q7 = getProductsBoughtByClient(s, cliente);
                printQUERY7(q7, cliente);
                destroyQUERY7(q7);
            }
            else showInvalido();
            break;

        case 8 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            showMessage("Mes minimo: ");
            showPoiter();
            scanf("%s", mesMin);
            showMessage("Mes maximo: ");
            showPoiter();
            scanf("%s", mesMax);
            int min = (int)strtol(mesMin,NULL, 10);
            int max = (int)strtol(mesMax,NULL, 10);
            if(min < max && min >= 1 && min <= 12 && max >= 1 && max <= 12)
            {
                q8 = getSalesAndProfit(s, min-1, max-1);
                showQUERY8Meses(min, max);
                int vendas = getQUERY8Vendas(q8);
                double fact = getQUERY8Fact(q8);
                showQUERY8(vendas, fact);
                destroyQUERY8(q8);
            }
            else showInvalido();
            break;

        case 9 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            showMessage("Insira um Produto: ");
            showPoiter();
            scanf("%s", produto);
            showMessage("Insira uma filial: ");
            showPoiter();
            scanf("%s",filial_novo);
            int id = (int) strtol(filial_novo, NULL, 10);
            if(g_tree_lookup(getPRODUTOSCatalogo(getSGVProdutos(s)), produto) != NULL && id <= 3 && id >= 1)
            {
                nav = getProductBuyers(s, produto, id-1);
                showQUERY9(produto, id, getNAVEGADORTam(nav));
                leituraNAVEGADOR(nav);
                destroyNAVEGADOR(nav);
            }
            else showInvalido();
            break;
        case 10 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            showMessage("Insira um Cliente: ");
            showPoiter();
            scanf("%s", cliente);
            showMessage("Insira um mes: ");
            showPoiter();
            scanf("%s", m);
            int month = (int) strtol(m,NULL, 10);
            if(g_tree_lookup(getCLIENTESCatalogo(getSGVClientes(s)), cliente) != NULL && month <= 12 && month >= 1)
            {
                nav = getClientFavoriteProducts(s, cliente, month-1);
                leituraNAVEGADOR(nav);
                destroyNAVEGADOR(nav);
            }
            else showInvalido();
            break;
        case 11 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            showMessage("Qual o número de produtos que pertende: ");
            showPoiter();
            scanf("%s", limite);
            int limit = (int) strtol(limite, NULL, 10);
            if(isdigit(limite[0]))
            {
                nav11 = getTopSelledProducts(s, limit);
                leituraNAVEGADORQ11(nav11);
                destroyNAVEGADORQ11(nav11);
            }
            else showInvalido();
            break;
        case 12 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            showMessage("Qual o Cliente que pertende: ");
            showPoiter();
            scanf("%s", cliente);
            showMessage("Qual o número de produtos que pertende: ");
            showPoiter();
            scanf("%s", limite);
            int l = (int) strtol(limite, NULL, 10);
            if(isdigit(limite[0]))
            {
                nav11 = getClientTopProfitProducts(s, cliente, l);
                setNAVEGADORQ11Query(nav11,12);
                leituraNAVEGADORQ11(nav11);
                destroyNAVEGADORQ11(nav11);
            }
            else showInvalido();
            break;
        case 13 :
            if(getCLIENTESValidos(getSGVClientes(s)) == 0) {showWarningQuery(); break;}
            s = getCurrentFilesInfo(s);
            int vc = getCLIENTESValidos(getSGVClientes(s));
            int lc = getCLIENTESLidos(getSGVClientes(s));
            int vp = getPRODUTOSValidos(getSGVProdutos(s));
            int lp = getPRODUTOSLidos(getSGVProdutos(s));
            int vs = getFATURACAOValidas(getSGVFaturacao(s));
            int ls = getFATURACAOLidas(getSGVFaturacao(s));
            showQUERY13(getSGVFileClient(s),getSGVFileProduct(s), getSGVFileSales(s), vc, lc, vp, lp, vs, ls);
            break;
        case 14 :
            showHelp();  
            break;  
        default:
            showInvalido();
    }
    return s;
}

/*
* inicializa o programa de sgv
*/
int main()
{
   char opcao[100];


    SGV s = initSGV();
    __clock_t start, end;

    start = clock();

    showSGV();
    showMenu();
    showPoiter();

    while(scanf("%s", opcao) && strcmp(opcao, "0") != 0)
    {
        s = menu(opcao, s);
        showMenu();
        showPoiter();
        scanf(" ");
    }

    end = clock();

    showTime((double) end-start);

    destroySGV(s);

    return 0;
}
