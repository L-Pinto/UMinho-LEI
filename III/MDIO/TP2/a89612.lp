/*
        MDIO - 2º Trabalho

        DADOS:
        - Cj,   custo do vertice
        


        Xj = 1, caso vertice j pertencer ao fecho
              0, caso contrário
        Cj = custo associado a um vertice

   G=(V,A) - prob. original    (V=40,A=84 ?)
    |
    |
     - >  G'=(V',A') - prob. auxiliar  (V'=42, A' = 124 = 84 +12 +28 )
                                                  84 - internas
                       (s,j) =  se Cj >=0         12 - ligam fonte ?
                       (j,t) =  se Cj <=0         28 - ligam terminal ?
*/

/* Objective function */
max: f;

/* Variable bounds */
//Conservação Fluxo
            f = Xs1 + Xs           //vertice s
            
                                   //vertice 1
                                   //vertice 2
                                   //vertice 3
                                   //vertice 4
                                   //vertice 5
                                   //vertice 6
                                   //vertice 7
                                   //vertice 8
                                   //vertice 9
                                   //vertice 10
                                   
                                   //vertice 11
                                   //vertice 12
                                   //vertice 13
                                   //vertice 14
                                   //vertice 15
                                   //vertice 16
                                   //vertice 17
                                   //vertice 18
                                   //vertice 19
                                   //vertice 20

                                   //vertice 21
                                   //vertice 22
                                   //vertice 23
                                   //vertice 24
                                   //vertice 25
                                   //vertice 26
                                   //vertice 27
                                   //vertice 28
                                   //vertice 29
                                   //vertice 30
                                   
                                   //vertice 31
                                   //vertice 32
                                   //vertice 33
                                   //vertice 34
                                   //vertice 35
                                   //vertice 36
                                   //vertice 37
                                   //vertice 38
                                   //vertice 39
                                   //vertice 40
            x3t +x4t = f           //vertice t
//Capacidade arcos arestas

/* Decision Variables*/
bin
X29 X30 X31 X32 X33 X34 X35 X36 X37 X38 X39 X40
    X19 X20 X21 X22 X23 X24 X25 X26 X27 X28
        X11 X12 X13 X14 X15 X16 X17 X18
            X5  X6  X7  X8  X9 X10
                X1  X2  X3  X4;