
#include "../include/view.h"
#include <time.h>

#define TITULO "\033[1;96m"
#define RESET "\033[0m"
#define CYANO "\033[0;96m"
#define QUER "\033[1;36m"
#define RED "\033[1;31m"
#define CYN "\033[0;36m"
#define BGREEN "\033[1;32m"
#define GREEN "\033[0;32m" 

/* SGV - Menu, Titulo, Query, Poiter */

/* 
* apresenta o titulo do sgv
*/
void showSGV()
{
    printf( TITULO "\nSGV- SISTEMA DE GESTÃO DE VENDAS\n" RESET);
}

/* 
* apresenta o menu do programa
*/
void showMenu()
{
    printf(CYANO "\n»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»» QUERIES «««««««««««««««««««««««««««««««««««\n" RESET);
    printf(CYANO "0 " RESET "Sair do programa.\n");
    printf(CYANO "1 " RESET "Leitura e tratamento de ficheiros.\n");
    printf(CYANO "2 " RESET "Códigos dos produtos inicializados por uma dada letra.\n");
    printf(CYANO "3 " RESET "Número de vendas e o total faturado num dado mes associado a um produto.\n");
    printf(CYANO "4 " RESET "Lista ordenada de produtos que nunca foram comprados.\n");
    printf(CYANO "5 " RESET "Lista ordenada de clientes que compram nas 3 filiais.\n");
    printf(CYANO "6 " RESET "Número de Produtos nunca comprados e Número de clientes que nunca compraram.\n");
    printf(CYANO "7 " RESET "Número total de produtos comprados dado um cliente.\n");
    printf(CYANO "8 " RESET "Número total de vendas e o total faturado no periodo fechado de meses.\n");
    printf(CYANO "9 " RESET "Lista dos clientes que compraram um determinado produto uma filial.\n");
    printf(CYANO "10 " RESET "Lista dos produtos mais comprados por um cliente num dado mes.\n");
    printf(CYANO "11 " RESET "Top N produtos mais vendidos por filial num ano.\n");
    printf(CYANO "12 " RESET "Top N dos produtos em que um cliente gastou mais dinheiro.\n");
    printf(CYANO "13 " RESET "Informações sobre sistema.\n");
    printf(CYANO "14 " RESET "SOS Programa.\n");
    printf(CYANO "»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»««««««««««««««««««««««««««««««««««««««««\n" RESET);
}

/* 
* apresenta o apontador para o utilizador
*/
void showPoiter()
{
    printf(CYANO "=> " RESET);
}

/* 
* aprenta as queries
*/
void showQuery(int escolha)
{
    printf(QUER"\n*** QUERY %d ***\n" RESET, escolha);
}

/* SGV - Warnnings */

/* 
* apresenta erro de ficheiro nunca lido
*/
void showWarningQuery()
{
    printf(RED "ERROR: " RESET "Nenhum ficheiro foi lido.\n");
}

/* 
* apresenta opção invalida 
*/
void showInvalido()
{
    printf(RED "ERROR: "RESET "Opção inválida\n");
}

/* 
* apresenta erro de invalidez de um ficheiro
*/
void showWrongFile()
{
    printf(RED "ERROR: "RESET"Ficheiro Inválido\n");
}

/* 
* apresenta erro de invalidez de um ficheiro pre-definido
*/
void showWrongPreDefineFile()
{
    printf(RED "ERROR: "RESET"Ficheiros pre-definidos não se encontram na pasta 'docs'\n");
}


/* SGV - Outros */

/* 
* apresnta tempo de execucao do programa
*/
void showTime(double final)
{
    printf("\nTIME : %f SECS\n", final/CLOCKS_PER_SEC);
}

/* 
* apresenta mensagem
*/
void showMessage(char* string)
{
    printf("%s\n", string);
}

/* 
* apresenta estruturas para tabela
*/
void showTabela( char* string)
{
    printf(QUER "%s\n" RESET, string);
}

/*
* Apresenta o menu de ajuda para o sistema 
*/
void showHelp()
{
    printf(BGREEN "\n                      >>>> HELP <<<<" RESET);
    printf(BGREEN"\nQuery 1:" RESET" Opcao [Y/N] e Caminho dos ficheiros têm de existir");
    printf(BGREEN"\nQuery 2:" RESET" Letra [A..Z]");
    printf(BGREEN "\nQuery 3:" RESET" Mes [1..12] e *Produto");
    printf(BGREEN "\nQuery 4:" RESET" Opcao [0..3]");
    printf(BGREEN "\nQuery 5:" RESET" ---");
    printf(BGREEN "\nQuery 6:" RESET" ---");
    printf(BGREEN "\nQuery 7:" RESET" *Cliente");
    printf(BGREEN "\nQuery 8:" RESET" Intervalo de meses. Mes min < Mes max e Mes [1..12]");
    printf(BGREEN"\nQuery 9:" RESET" *Produto e Filial [1..3]");
    printf(BGREEN "\nQuery 10:" RESET" *Cliente e Mes [1..12]");
    printf(BGREEN "\nQuery 11:" RESET" Número de produtos [1..[");
    printf(BGREEN "\nQuery 12:" RESET" *Cliente e número de produtos [1..[\n");
    printf(RED"\nAtenção\n"RESET);
    printf("       Exemplo > *Cliente: L1782 - O Cliente tem de existir no catalogo dos clientes para ser considerado válido\n");
    printf("               > *Produto: OP1244 - O Produto tem de existir no catalogo dos produtos para ser considerado válido\n");

}


/* SGV - Quereis */

/* 
* apresenta menu da query 1
*/
void showQUERY1Msg()
{
    printf("Ler Ficheiros Pre-Definidos " QUER "[Y/N]\n" RESET);
}

/* 
* apresenta resultado da query 2
*/
void showQUERY2(char letra, int tam)
{
    printf("\nNúmero Total de Produtos Começados por" QUER " %c " RESET ": %d\n", letra,tam);
}

void showQUERY3Msg()
{
    printf("Deseja o resultado global" QUER" [G] "RESET "ou filial a filial" QUER" [F] \n"RESET);
}
/* 
* apresenta resultado da query 3 por filial
*/
void showQUERY3Filial(int i, int vn, int vp, double fn, double fp)
{
    printf(QUER "\n---- FILIAL %d ----\n" RESET, i+1);
    printf("Número de Vendas no Modo N: %d\n", vn);
    printf("Número de Vendas no Modo P: %d\n", vp);
    printf("Faturacão no Modo N: %.2f\n", fn);
    printf("Faturacão no Modo P: %.2f\n", fp);
}

/* 
* apresenta resultado da query 3 por global
*/
void showQUERY3Global(int tvn, int tvp, double tfn, double tfp)
{
    printf(QUER "\n---- RESULTADO GLOBAL ----\n" RESET);
    printf("Número de Vendas no Modo N: %d\n", tvn);
    printf("Número de Vendas no Modo P: %d\n", tvp);
    printf("Faturacão no Modo N: %.2f\n", tfn);
    printf("Faturacão no Modo P: %.2f\n", tfp);
}

/* 
* apresenta menu da query 4
*/
void showMsgQUERY4()
{
    printf("Resultado Global " QUER "[0]\n" RESET);
    printf("Filial " QUER "[1]\n" RESET);
    printf("Filial " QUER "[2]\n" RESET);
    printf("Filial " QUER "[3]\n" RESET);
}

/* 
* apresenta resultado da query 4
*/
void showQUERY4(int brunch, int tam)
{
    if(brunch == 0) printf("\nNúmero total de produtos que nunca foram comprados em nenhuma filial: %d\n",tam);
    else printf("\nNúmero total de produtos não comprados na filial" QUER " %d" RESET ": %d\n", brunch, tam);
}

/* 
* apresenta resultado da query 5
*/
void showQUERY5(int tam)
{
    printf("\nNúmero total de Clientes que compraram em todas as filiais: %d \n",tam);
}

/* 
* apresenta resultado da query 6
*/
void showQUERY6(int nprod, int ncli)
{
    printf("Número de Produtos que nunca vendidos: %d\n", nprod);
    printf("Número de Clientes que nunca compararam: %d\n", ncli);
}

/* 
* apresenta resultado da query 7 
*/
void showQUERY7Client( char* clientID)
{
    printf(QUER "\nTABELA DE PRODUTOS PARA O CLIENTE -" RESET " %s\n", clientID);
}

/* 
* apresenta resultado da query 7 para as filiais
*/
void showQUERY7Filial()
{
    printf(QUER"FILIAL:   %6d      %6d      %6d\n"RESET,1,2,3);
}

/* 
* apresenta resultado da query 7 para os meses
*/
void showQUERY7(int i, double f1, double f2, double f3)
{
    printf(QUER"MES %3d:   "RESET"%6.0f      %6.0f      %6.0f\n",i+1,f1,f2,f3);
}

/* 
* apresenta resultado da query 8 para os rsepetivos meses
*/
void showQUERY8Meses(int min, int max)
{
    printf("\nPara o periodo de meses entre "QUER "%d" RESET " e " QUER "%d\n\n" RESET , min , max);
}

/* 
* apresenta resultado da query 8 
*/
void showQUERY8(int vendas, double fact)
{
    printf("Número de vendas: %d\n", vendas);
    printf("Faturacao Total: %0.2f\n", fact);
}

/* 
* apresenta resultado da query 9 
*/
void showQUERY9(char * produto, int id, int tam)
{
    printf("\nNúmero de Clientes que compraram o produto" QUER " %s " RESET "na filial" QUER " %d " RESET ": %d\n", produto, id, tam);
}

/* 
* apresenta resultado da query 13 - Info do sistema
*/
void showQUERY13(char* cli, char* prod, char* sales, int vc, int lc, int vp, int lp, int vs, int ls)
{
    printf(QUER "\n---- CLIENTES ----\n"RESET);
    printf("Ficheiro: %s\n", cli);
    printf("Número de Validos: %d\n",vc);
    printf("Número de Lidos: %d\n",lc);

    printf(QUER "\n---- PRODUTOS ----\n" RESET);
    printf("Ficheiro: %s\n", prod);
    printf("Numero de Validos: %d\n", vp);
    printf("Número de Lidos: %d\n", lp);

    printf(QUER "\n---- VENDAS ----\n" RESET);
    printf("Ficheiro: %s\n", sales);
    printf("Numero de Validas: %d\n", vs);
    printf("Numero de Lidas: %d\n\n", ls);
}








