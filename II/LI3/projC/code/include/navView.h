#ifndef LI3_NAVVIEW_H
#define LI3_NAVVIEW_H

#endif //LI3_NAVVIEW_H

#include <stdio.h>

///----------------------GERAL----------------------///

void clearNAV();
void showNAVError1();
void showNAVError2();
void showNAVError();
void showNAVErrorEmpty();
void showNAVMenu();

///----------------------NAVEGADOR----------------------///

void showNAV(int pagAtual, int pagTotal);
void showNAVLinha(char* message);
void showNAVLinhaEmpty();

///----------------------NAVEGADORQ11----------------------///
void showNAV11(int pagAtual, int pagTotal);
void showNAV11EndLine();
void showNAV11Linha(char* prod,double q1 , double q2, double q3, int v1, int v2, int v3,double qnt , int venda, int rank);
void showNAV12(int pagAtual, int pagTotal);
void showNAV12EndLine();
void showNAV12Linha(char* prod, double preco, int rank);
