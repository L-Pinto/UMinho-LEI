#include "../include/leitura.h"

/*Função que, dada uma venda (string), verifica se uma dada venda é válida.
* @param venda(string);
* @param estrutura dos CLIENTES.
* @param estrutura dos PRODUTOS.
* @return 1(True) ou 0(False), consoante a venda seja válida ou não.
*/
int valido_vendas(char * venda, CLIENTES c, PRODUTOS p) {
    int res = 0;

    strtok(venda, " ");
    if(g_tree_lookup_extended(getPRODUTOSCatalogo(p),venda,NULL,NULL) == FALSE) {
        res = 1;
        //printf("Produtos: %s\n", venda);
    }

    char * preco = strtok(NULL ," ");
    if(res == 0 && (strtod(preco, NULL) < 0.0 || strtod(preco, NULL) >= 1000) ) {
        res = 1;
        //printf("preco: %s\n", preco);
    }

    char * numero = strtok(NULL ," ");
    if(res == 0 && (strtol(numero, NULL, 10) < 1 || strtol(numero, NULL, 10) > 200) ) {
        res = 1;
        //printf("nº produtos: %s\n", numero);
    }

    char * promocao = strtok(NULL ," ");
    if(res == 0 && (strcmp(promocao,"P") != 0 && strcmp(promocao, "N") != 0)) {
        res = 1;
        //res = 1; printf("promoção: %s\n", promocao);
    }

    char * cli = strtok(NULL, " ");
    if(res == 0 && g_tree_lookup_extended(getCLIENTESCatalogo(c),cli,NULL,NULL) == FALSE) {
        res = 1;
        //printf("clientes: %s\n", cli);
    }

    char * mes = strtok(NULL, " ");
    if(res == 0 && (strtol(mes, NULL, 10) < 1 || strtol(mes, NULL, 10) > 12) ) {
        res = 1;
        //printf("Mes: %s\n", mes);
    }

    char * filial = strtok(NULL, " ");
    if(res == 0 && (strtol(filial, NULL, 10) < 1 || strtol(filial, NULL, 10) > 3) ) {
        res = 1;
        //res = 1; printf("filial: %s\n", filial);
    }

    return res;
}

/*Função que faz a leitura de um dado ficheiro de clientes para a estrutura CLIENTES.
* @param ficheiro que vai ser lido.
* @param estrutura dos CLIENTES.
* @return estrutura dos CLIENTES devidamente preenchida com o conteúdo válido do ficheiro recebido.
*/
CLIENTES loadCLIENTESFromFiles(FILE *f, CLIENTES c) {
    int len = 8;
    char cli_code[len];
    int i = 0;

    while (fgets(cli_code, len, f)) {
        strtok(cli_code, "\r\n");
        i++;
        if (valido_cli(cli_code)) {
           c = insertCLIENTES(c,cli_code);
        }
    }

    c = setCLIENTESLidos(c,i);

    return c;
}

/*Função que faz a leitura de um dado ficheiro de produtos para a estrutura PRODUTOS.
* @param ficheiro que vai ser lido.
* @param estrutura dos PRODUTOS.
* @return estrutura dos PRODUTOS devidamente preenchida com o conteúdo válido do ficheiro recebido.
*/
PRODUTOS loadPRODUTOSFromFiles(FILE *f, PRODUTOS p) {
    int len = 10;
    char prod_code[len];
    int i = 0;

    while (fgets(prod_code, len, f)) {
        strtok(prod_code, "\r\n");
        i++;
        if (valido_prod(prod_code)) {
            p = insertPRODUTOSValue(p,prod_code);
        }
    }

    p = setPRODUTOSLidos(p,i);

    return p;
}

/*Funcao que faz a leitura de um dado ficheiro de vendas e preenche/atualiza as estruturas constituintes do SGV.
* @param ficheiro das vendas, que vai ser lido.
* @param estrutura FATURACAO, que vai ser preenchida/atualizada.
* @param estrutura GEST_FILIAL, que vai ser preenchida/atualizada.
* @param estrutura CLIENTES, que vai ser preenchida/atualizada.
* @param estrutura PRODUTOS, que vai ser preenchida/atualizada.
*/
void loadVENDASFromFiles(FILE *f,FATURACAO fact, GEST_FILIAL filial, CLIENTES c, PRODUTOS p) {
    int len = 35;
    char venda_code[len];
    char ultimo[len];
    char gf[len];
    char aux[len];
    int nvlidas = 0;
    int nv_val = 0;

    while (fgets(venda_code, len, f)) {
        strtok(venda_code, "\r\n");
        strcpy(ultimo,venda_code);
        strcpy(gf,venda_code);
        strcpy(aux,venda_code);
        nvlidas++;
        if (valido_vendas(venda_code,c,p) == 0) {
            filial = insertGEST_FILIAL(filial,gf);
            fact = insertFATURACAO(fact,ultimo);
            nv_val++;
            char * produto = strtok(aux, " ");
            strtok(NULL ," ");
            char * qtd = strtok(NULL ," ");
            strtok(NULL ," ");
            char * cli = strtok(NULL, " ");
            strtok(NULL, " ");
            char * fili = strtok(NULL, " ");
            replacePRODUTOSValue(p,produto,fili,(double)strtol(qtd,NULL,10));
            replaceCLIENTESValue(c,cli,fili);
        }
    }

    filial = setGEST_FILIALClieNC(filial,c);
    fact = setFATURACAOLidas(fact,nvlidas);
    fact = setFATURACAOValidas(fact,nv_val);
    fact = setFATURACAOProdNV(fact,p);
}


