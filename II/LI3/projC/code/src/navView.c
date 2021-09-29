#include "../include/navView.h"

#define BHCYN "\e[1;96m" // bold high intensity SGV, =>
#define HCYN "\e[0;96m"  // high intensity      QUERIES
#define BCYN "\e[1;36m"  // regular bold        NUMEROS, comandos ,paginas
#define CYN "\e[0;36m"   // regular
#define BRED "\e[1;31m"  // erros
#define reset "\e[0m"
#define clearScreen "\e[1;1H\e[2J"

/*----------------------------------------------GERAL-----------------------------------------------*/

/**
 * <limpa ecra>
 */
void clearNAV(){
    printf(clearScreen);
}

/**
 * <Mensagem de erro, impossivel retroceder pagina no navegador em questao>
 */
void showNAVError1(){
    printf(BRED "ERROR: " reset "Impossivel retroceder.\n");
}

/**
 * <Mensagem de erro, impossivel avancar pagina no navegador em questao>
 */
void showNAVError2(){
    printf(BRED "ERROR: " reset "Impossivel Avançar.\n");
}

/**
 * <Mensagem de erro, comando invalido inserido no navegador em questao>
 */
void showNAVError(){
    printf(BRED "ERROR: " reset "insira comando válido. \n");
}

/**
 * <Mensagem de erro, no navegador em questao vazio>
 */
void showNAVErrorEmpty(){
    printf(BRED "ERROR: " reset "NAVEGADOR VAZIO!\n");
}

/**
 * <Apresentacao do menu dos navegadores>
 */
void showNAVMenu(){
    printf("\nPagina anterior " BCYN "[1]" reset "\n");
    printf("Pagina seguinte " BCYN "[2]" reset "\n");
    printf("Sair " BCYN "[0] " reset "\n");
    printf(BHCYN "=> " reset);
}
/*-----------------------------------------------NAVEGADOR-----------------------------------------------*/

/**
 * <Cabeçalho do navegador>
 */
void showNAV(int pagAtual, int pagTotal){
    printf(BCYN "\n-------------------------------------------\n" reset);
    printf(BCYN "             NAVEGADOR (%d/%d)               " reset,pagAtual,pagTotal);
    printf(BCYN "\n-------------------------------------------\n" reset);
}

/**
 * <Linha do Navegador>
 */
void showNAVLinha(char* message){
    printf("%s      ", message);
}

/**
 * <Linha Vazia do Navegador>
 */
void showNAVLinhaEmpty(){
    printf("\n");
}
/*-----------------------------------------------NAVEGADOR11-----------------------------------------------*/

/**
 * <Cabeçalho do Navegador Query 11>
 */
void showNAV11(int pagAtual, int pagTotal){
    printf(BCYN "-------------------------------------------------------------------------------------------------------\n" reset);
    printf(BCYN "                              RANK PRODUTOS MAIS VENDIDOS - PAGINA (%d/%d)                             \n" reset, pagAtual, pagTotal);
    printf(BCYN "-------------------------------------------------------------------------------------------------------\n" reset);
    printf(HCYN "RANK     PRODUTO     QT1         QT2         QT3         C1         C2         C3         TQ         TC\n" reset);
}

/**
 * <Cabeçalho do Navegador para a Query 12>
 */
void showNAV12(int pagAtual, int pagTotal) {
    printf(BCYN "------------------------------\n" reset);
    printf(BCYN "         RANK (%d/%d)          \n" reset, pagAtual, pagTotal);
    printf(BCYN "------------------------------\n" reset);
    printf(HCYN "RANK     PRODUTO      PRECO    \n" reset);
}

/**
 * <Linha apos pagina impressa do navegador da query 11>
 */
void showNAV11EndLine(){
    printf(BCYN "-------------------------------------------------------------------------------------------------------\n" reset);
}

/**
 * <Linha apos pagina impressa do navegador para a query 12>
 */
void showNAV12EndLine(){
   printf(BCYN "------------------------------\n" reset);
}

/**
 * <Linhas da pagina atual do navegador da query 11>
 */
void showNAV11Linha(char* prod,double q1 , double q2, double q3, int v1, int v2, int v3,double qnt , int venda, int rank){
    printf("%-7d  ",rank);
    printf("%4s      ", prod);
    printf("%-10.0f  %-10.0f  %-10.0f  ",q1, q2, q3);
    printf("%-10d %-10d %-10d", v1, v2, v3);
    printf("%-10.0f  %-10d\n",qnt, venda);
}

/**
 * <Linhas da pagina atual do navegador para a query 12>
 */
void showNAV12Linha(char* prod, double preco, int rank) {
    printf("%-7d  ",rank);
    printf("%4s      ",prod);
    printf("%-20.2f \n",preco);
}




