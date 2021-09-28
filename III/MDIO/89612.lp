/*
        MDIO - 1º Trabalho
        
        Xi_j = 1, caso vertice i é emparelhado com vertice j, formando nova aresta (i,j)
              0, caso contrário
        Ci_j = custo associado a distancia percorrida entre a origem i e o destino j
*/

/* Objective function */
min: 3 X1_2 + 6.08 X1_3 + 8.06 X1_4 + 3 X1_6 + 8.54 X1_7 + 7 X1_9 + 7.28 X1_10 + 8.60 X1_11 + 10.63 X1_12
   + 3.16 X2_3 + 5.10 X2_4 + 4.24 X2_6 + 5.83 X2_7 + 7.62 X2_9 + 7.07 X2_10 + 7.28 X2_11 + 8.60 X2_12
   + 2 X3_4 + 7.21 X3_6 + 4.47 X3_7 + 10 X3_9 + 8.94 X3_10 + 8.06 X3_11 + 8.25 X3_12
   + 8.94 X4_6 + 4 X4_7 + 11.31 X4_9 + 10 X4_10 + 8.54 X4_11 + 8 X4_12
   + 8 X6_7 + 4 X6_9 + 4.47 X6_10 + 6.40 X6_11 + 8.94 X6_12
   + 8.94 X7_9 + 7.21 X7_10 + 5 X7_11 + 4 X7_12
   + 2 X9_10 + 5 X9_11 + 8 X9_12
   + 3 X10_11 + 6 X10_12
   + 3 X11_12;

/* Variable bounds */
//   Cada vertice pode ser apenas emparelhado com um outro vertice

       X1_2 + X1_3 + X1_4 + X1_6 + X1_7 + X1_9 + X1_10 + X1_11 + X1_12 = 1;           // vertice 1
       X1_2 + X2_3 + X2_4 + X2_6 + X2_7 + X2_9 + X2_10 + X2_11 + X2_12 = 1;           // vertice 2
       X1_3 + X2_3 + X3_4 + X3_6 + X3_7 + X3_9 + X3_10 + X3_11 + X3_12 = 1;           // vertice 3
       X1_4 + X2_4 + X3_4 + X4_6 + X4_7 + X4_9 + X4_10 + X4_11 + X4_12 = 1;           // vertice 4
       X1_6 + X2_6 + X3_6 + X4_6 + X6_7 + X6_9 + X6_10 + X6_11 + X6_12 = 1;           // vertice 6
       X1_7 + X2_7 + X3_7 + X4_7 + X6_7 + X7_9 + X7_10 + X7_11 + X7_12 = 1;           // vertice 7
       X1_9 + X2_9 + X3_9 + X4_9 + X6_9 + X7_9 + X9_10 + X9_11 + X9_12 = 1;           // vertice 9
       X1_10 + X2_10 + X3_10 + X4_10 + X6_10 + X7_10 + X9_10 + X10_11 + X10_12 = 1;   // vertice 10
       X1_11 + X2_11 + X3_11 + X4_11 + X6_11 + X7_11 + X9_11 + X10_11 + X11_12 = 1;   // vertice 11
       X1_12 + X2_12 + X3_12 + X4_12 + X6_12 + X7_12 + X9_12 + X10_12 + X11_12 = 1;   // vertice 12
       
/* Variaveis de Decisao*/
bin
X1_2 X1_3 X1_4 X1_6 X1_7 X1_9 X1_10 X1_11 X1_12
X2_3 X2_4 X2_6 X2_7 X2_9 X2_10 X2_11 X2_12
X3_4 X3_6 X3_7 X3_9 X3_10 X3_11 X3_12
X4_6 X4_7 X4_9 X4_10 X4_11 X4_12
X6_7 X6_9 X6_10 X6_11 X6_12
X7_9 X7_10 X7_11 X7_12
X9_10 X9_11 X9_12
X10_11 X10_12
X11_12;