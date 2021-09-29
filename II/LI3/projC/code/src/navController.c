#include "../include/navController.h"
#define COL 4  //numero de colunas de produtos
#define LIN 20 //numero de linhas de produtos
#define PAG (COL*LIN) //numero de produtos no output

/*----------------------NAVEGADOR----------------------*/

/**
 * <Linha do navegador>
 */
void printLinhaNAVEGADOR(NAVEGADOR n) {
    int i,x=COL;
    for (i=0; i<getNAVEGADORTam(n) ; i++)
    {
        showNAVLinha(getNAVEGADORCatalogo(n,i)); x--;
        if(x == (PAG-(COL*LIN)))
        {showNAVLinhaEmpty(); x = COL;}; 
    }
}

/**
 * <Paginas do navegador>
 */
NAVEGADOR paginasNAVEGADOR(NAVEGADOR nav, char *opcao, int max, int nrPagTotal) {
    long int escolha = strtol(opcao, NULL, 10);

    switch(escolha)
    {
        case 1 : {
            if (getNAVEGADORPag(nav) == 1) {  /**< primeira pagina>*/
                showNAVError1();
                showNAV(getNAVEGADORPag(nav),nrPagTotal);
                printLinhaNAVEGADOR(nav);
            }
            else{
                nav = atualNAVEGADORPag(nav, -1, -PAG, PAG);
                showNAV(getNAVEGADORPag(nav),nrPagTotal);
                printLinhaNAVEGADOR(nav);
            }
            break;
        }
        case 2 :
        {
            if (getNAVEGADORPag(nav) == nrPagTotal) { /**< ultima pagina>*/
                showNAVError2();
                showNAV(getNAVEGADORPag(nav),nrPagTotal);
                printLinhaNAVEGADOR(nav);
            }
            else
            {
                if(getNAVEGADORPag(nav) == nrPagTotal-1 && max%PAG != 0) setNAVEGADORTam(nav,max%PAG); /**<penultima pagina>*/
                else setNAVEGADORTam(nav,PAG);
                nav = atualNAVEGADORPag(nav, 1, PAG, getNAVEGADORTam(nav));
                showNAV(getNAVEGADORPag(nav),nrPagTotal);
                printLinhaNAVEGADOR(nav);
            }
            break;
        }
        default :
            showNAVError();
            showNAV(getNAVEGADORPag(nav),nrPagTotal);
            printLinhaNAVEGADOR(nav);
            break;
    }
    return nav;
}

/**
 * <percorre catalogo navegador>
 */
int leituraNAVEGADOR(NAVEGADOR nav)
{
    if (getNAVEGADORTam(nav) !=0)
    {
        char opcao[2];
        int max = getNAVEGADORTam(nav);

        if (max >= PAG) nav = setNAVEGADORTam(nav,PAG);
        nav = setNAVEGADORPag(nav,1);

        int nrPagTotal;

        if (max%PAG == 0) nrPagTotal = max/PAG;
        else nrPagTotal = max/PAG+1;

        showNAV(getNAVEGADORPag(nav),nrPagTotal);
        printLinhaNAVEGADOR(nav);
        showNAVMenu();
        scanf(" ");

        while (strcmp(fgets(opcao, 2, stdin), "0") != 0) {
            nav = paginasNAVEGADOR(nav, opcao, max, nrPagTotal);
            showNAVMenu();
            scanf(" ");
        }
        if (getNAVEGADORPag(nav) != 1) nav = setNAVEGADORCatalogo(nav, PAG * (getNAVEGADORPag(nav) - 1));
        setNAVEGADORTam(nav,max);
    }
    return 0;
}
/*----------------------NAVEGADOR11----------------------*/

/**
 * <Linha do Navegador da query 11>
 */
void printLinhaNAVEGADORQ11(NAVEGADORQ11 n11, int paginaAtual) {
    int somav = 0;
    double somaq = 0;
    KEY11 k;

    if (getNAVEGADORQ11Query(n11) == 12) {
        for(int i = 0; i < getNAVEGADORQ11Tam(n11); i++) {
            k = getNAVEGADORQ11Keys(n11, i);
            int unidades = i + 1;
            int dezenas = 10 *(paginaAtual-1);
            int rank = unidades + dezenas;
            showNAV12Linha(getKEY11Produto(k),getKEY11Qnt(k,0),rank);
        }
    } else {
   	 for(int i = 0; i < getNAVEGADORQ11Tam(n11); i++) {
        	k = getNAVEGADORQ11Keys(n11, i);
        	somav = 0;
       		somaq = 0;
        	for(int j = 0; j < 3; j++){
            		somaq += getKEY11Qnt(k,j);
            		somav += getKEY11Vendas(k,j);
       		 }
	int unidades = i+1;
	int dezenas = 10*(paginaAtual -1);
	int rank = unidades + dezenas;
        showNAV11Linha(getKEY11Produto(k), getKEY11Qnt(k, 0),getKEY11Qnt(k, 1),getKEY11Qnt(k, 2),getKEY11Vendas(k,0), getKEY11Vendas(k,1), getKEY11Vendas(k,2) ,somaq , somav, rank);
    	}
    }
}

/**
 * <Paginas do navegador da query 11>
 */
NAVEGADORQ11 paginasNAVEGADORQ11 (NAVEGADORQ11 n11, char * opcao, int max, int total_pag) {

    long int l = strtol(opcao,NULL,10);

    switch(l) {
        case 1 : {
            if (getNAVEGADORQ11Pag(n11) == 1) {
                showNAVError1();

                if (getNAVEGADORQ11Query(n11) == 12) showNAV12(getNAVEGADORQ11Pag(n11), total_pag);
                else showNAV11(getNAVEGADORQ11Pag(n11), total_pag);

                printLinhaNAVEGADORQ11(n11,getNAVEGADORQ11Pag(n11));
                break;
            } else {
                n11 = atualNAVEGADORQ11Pag(n11, -1, -10, 10);

                if (getNAVEGADORQ11Query(n11) == 12) showNAV12(getNAVEGADORQ11Pag(n11), total_pag);
                else showNAV11(getNAVEGADORQ11Pag(n11), total_pag);

                printLinhaNAVEGADORQ11(n11,getNAVEGADORQ11Pag(n11));
            }
            break;
        }
        case 2 : {
            if(getNAVEGADORQ11Pag(n11) == total_pag) {
                showNAVError2();

                if (getNAVEGADORQ11Query(n11) == 12) showNAV12(getNAVEGADORQ11Pag(n11), total_pag);
                else showNAV11(getNAVEGADORQ11Pag(n11), total_pag);

                printLinhaNAVEGADORQ11(n11,getNAVEGADORQ11Pag(n11));
            }
            else {
                if(getNAVEGADORQ11Pag(n11) == total_pag-1 && max%10 != 0) n11 = setNAVEGADORQ11Tam(n11,max%10);
                else setNAVEGADORQ11Tam(n11,10);
                n11 = atualNAVEGADORQ11Pag(n11, 1, 10, getNAVEGADORQ11Tam(n11));

                if (getNAVEGADORQ11Query(n11) == 12) showNAV12(getNAVEGADORQ11Pag(n11), total_pag);
                else showNAV11(getNAVEGADORQ11Pag(n11), total_pag);

                printLinhaNAVEGADORQ11(n11,getNAVEGADORQ11Pag(n11));
            }
            break;
        }
        default :
            showNAVError();

            if (getNAVEGADORQ11Query(n11) != 12) showNAV11(getNAVEGADORQ11Pag(n11), total_pag);
            else showNAV12(getNAVEGADORQ11Pag(n11), total_pag);

            printLinhaNAVEGADORQ11(n11,getNAVEGADORQ11Pag(n11));
            break;
    }
    return n11;
}

/**
 * <percorre catalogo navegador da query 11>
 */
int leituraNAVEGADORQ11 (NAVEGADORQ11 nav11) {
    if(getNAVEGADORQ11Tam(nav11) == 0) {
        showNAVErrorEmpty();
        return 0;
    }

    char opcao[2];
    int max =getNAVEGADORQ11Tam(nav11);
    if (getNAVEGADORQ11Tam(nav11) > 10) setNAVEGADORQ11Tam(nav11,10);
    nav11 = setNAVEGADORQ11Pag(nav11,1);
    int total_pag = 0;

    if(max%10 == 0) total_pag = max/10; else total_pag = max/10 + 1;

    if (getNAVEGADORQ11Query(nav11) == 12) {
        showNAV12(getNAVEGADORQ11Pag(nav11), total_pag);
        printLinhaNAVEGADORQ11(nav11,getNAVEGADORQ11Pag(nav11));
        showNAV12EndLine();
        showNAVMenu();
        scanf(" ");
    } else {
        showNAV11(getNAVEGADORQ11Pag(nav11), total_pag);
        printLinhaNAVEGADORQ11(nav11,getNAVEGADORQ11Pag(nav11));
        showNAV11EndLine();
        showNAVMenu();
        scanf(" ");
    }

    while(strcmp(fgets(opcao,2,stdin),"0") != 0) {
        nav11 = paginasNAVEGADORQ11(nav11,opcao,max,total_pag);
        if (getNAVEGADORQ11Query(nav11) != 12) showNAV11EndLine();
        else showNAV12EndLine();
        showNAVMenu();
        scanf(" ");
    }

    if (getNAVEGADORQ11Pag(nav11) != 1) nav11 = setNAVEGADORQ11Catalogo(nav11, 10 * (getNAVEGADORQ11Pag(nav11) - 1));

    setNAVEGADORQ11Tam(nav11,max);

    return 0;
}
