
#ifndef LI3_MENU_H
#define LI3_MENU_H
#endif

#include <stdio.h>

/* GERAL */ 
void showSGV();
void showMenu();
void showPoiter();
void showTime(double final);
void showMessage(char * string);
void showQuery(int escolha);
void showWarningQuery();
void showInvalido();
void showSGVInfo();
void showTabela( char* string);
void showHelp();

/* QUERY 1 */
void showWrongFile();
void showWrongPreDefineFile();
void showQUERY1Msg();

/* QUERY 2 */
void showQUERY2(char letra, int tam);

/* QUERY 3 */
void showQUERY3Msg();
void showQUERY3Filial(int i, int vn, int vp, double fn, double fp);
void showQUERY3Global(int tvn, int tvp, double tfn, double tfp);

/* QUERY 4 */
void showMsgQUERY4();
void showQUERY4(int brunch, int tam);

/* QUERY 5 */
void showQUERY5(int tam);

/* QUERY 6 */
void showQUERY6(int nprod, int ncli);

/* QUERY 7 */
void showQUERY7(int i, double f1, double f2, double f3);
void showQUERY7Client( char* clientID);
void showQUERY7Filial();

/* QUERY 8 */
void showQUERY8Mgs();
void showQUERY8Meses(int min, int max);
void showQUERY8(int vendas, double fact);

/* QUERY 9 */
void showQUERY9(char * produto, int id, int tam);

/* QUERY 13 */
void showQUERY13(char* cli, char* prod, char* sales, int vc, int lc, int vp, int lp, int vs, int ls);





