#include "../include/queries.h"
#include "../include/interface.h"


/*---ESTRUTURA DE DADOS AUXILIAR À QUERY11---*/
struct key11 {
    char produto[12];
    double qnt[3];
    int vendas[3]; /*NUMERO DE CLIENTES = VENDAS*/
};


/*Função que inicializa a KEY11, alocando memória para tal e inicializando a zero o array.*/
KEY11 initKEY11() {
    KEY11 k = malloc(sizeof(struct key11));
    int i;

    for(i = 0; i < 3; i++) {
        k->qnt[i] = 0;
        k->vendas[i] = 0;
    }

    return k;
}


/*Função que retorna o size(tamanho) da struct key11 (estrutura KEY11), auxiliar à query11.*/
size_t getKEY11Size() {
    return (sizeof(struct key11));
}

/*Função que dada uma KEY11, retorna o produto presente nessa estrutura.*/
char * getKEY11Produto(KEY11 k) {
    return k->produto;
}

/*Função que, dada uma KEY11 e uma filial, retorna a quantidade associada a essa filial, nessa key.*/
double getKEY11Qnt(KEY11 k, int filial) {
    return k->qnt[filial];
}



/*Função que, dada uma KEY11 e uma filial, retorna o número de vendas(clientes) associada a essa filial, nessa key.*/
int getKEY11Vendas(KEY11 k, int filial) {
    return k->vendas[filial];
}


/*Função que atualiza numa KEY11 o produto 
void setKEY11Produto(KEY11 chave, KEY11 k) {
    strcpy(chave->produto,k->produto);
}*/


/*Função que, dada uma KEY11 e um preco, atualiza o preço no indice zero na quantidade.*/
void setKEY11Qnt12(KEY11 k, double preco) {
    k->qnt[0] = preco;
}

/*Função que dadas duas KEY11, atualiza o valor da quantidade da primeira KEY11 recebida, com o valor da 
* segunda filial, na filial recebida como argumento.
*/
void setKEY11Qnt(KEY11 chave, KEY11 k, int filial) {
    chave->qnt[filial] = k->qnt[filial];
}

/*Função que dadas duas KEY11, atualiza o valor das vendas(número de clientes) da primeira KEY11 recebida, 
* com o valor da segunda filial, na filial recebida como argumento.
*/
void setKEY11Vendas(KEY11 chave, KEY11 k, int filial) {
    chave->vendas[filial] = k->vendas[filial];
}





/*---ESTRUTURAS DE DADOS AUXILIARES À QUERY11---*/
struct query11 {
    GTree * top; /*Árvore em que cada key corresponde a uma estrutura KEY11.*/
};


/*Funcao (GCompareDataFunc) utilizada na árvore da estrutura QUERY11, de modo que seja possível ordenar 
* os produtos por ordem decrescente de quantidade.
*/
gint compareQUERY11(KEY11 k1, KEY11 k2) {
    double qt1 = 0, qt2 = 0;
    int i;

    for(i = 0; i < 3; i++) {
        qt1 += k1->qnt[i];
        qt2 += k2->qnt[i];
    }

    int res = (int) (qt2-qt1);
    if (res == 0) res = 1;

    return res;
}


/*Funcão que inicializa a query11, alocando memória para a mesma.*/
QUERY11 initQUERY11(){
    QUERY11 q11 = malloc(sizeof(struct query11));

    q11->top = g_tree_new_full((GCompareDataFunc)compareQUERY11,NULL,g_free,g_free);

    return q11;
}

/*Função que, dada uma QUERY11, retorna a árvore presente nessa estrutura.*/
GTree * getQUERY11Top(QUERY11 q){
    return q->top;
}

/*Funcao que insere as informaçoes presentes no catalogo de produtos na estrutura da query11(árvore)
* para que seja possível ordenar por ordem decrescente de quantidades.
* @param produto.
* @param estrutura presente no Value de cada produto, na árvore dos produtos. 
* @param estrutura QUERY11, onde vão ser inseridos os produtos por ordem decrescente de quantidades vendidas.
* @return FALSE, para prosseguir a travessia da árvore.
*/
gboolean insertCATALOGO(char * produto, CPRODvalue v, QUERY11 * q11) {
    KEY11 k = initKEY11();

    strcpy(k->produto,produto);

    for(int i = 0; i < 3; i++) {
        k->qnt[i] = getCPRODvalueQnt(v,i);
        k->vendas[i] = getCPRODvalueNCli(v,i);
    }

    g_tree_insert((*q11)->top,k,NULL);

    return FALSE;
}

/*Função que destrói e liberta o espaço alocado para a query11.
* @param estrutura QUERY11 cujo espaço vai ser libertado.
*/
void destroyQUERY11(QUERY11 q11){
    g_tree_destroy(q11->top);
    g_free(q11);
}





/*----ESTRUTURA AUXILIAR À QUERY8----*/
struct query8 {
    int vendas;
    double fact;
};


/*Função que inicializa a query8, alocando a memória necessária.*/
QUERY8 initQUERY8() {
    QUERY8 q8 = malloc(sizeof(struct query8));
    return q8;
}

/*Função que, dada uma estrutura QUERY8, retorna a faturação referente a um intervalo de meses.*/
double getQUERY8Fact(QUERY8 q) {
    return q->fact;
}

/*Função que, dada uma estrutura QUERY8, retorna o número de vendas referente a um intervalo de meses.*/
int getQUERY8Vendas(QUERY8 q) {
    return q->vendas;
}

/*Função que, dada uma estrutura QUERY8, atualiza os campos fact e vendas da estrutura, para os valores
* recebidos como argumento. 
*/
void setQUERY8(QUERY8 q8, double fact, int vendas) {
    q8->fact = fact;
    q8->vendas = vendas;
}

/*Função que destrói a query8, libertando o espaço alocado para a mesma.*/
void destroyQUERY8(QUERY8 q8) {
    g_free(q8);
}




/*----ESTRUTURA AUXILIAR À QUERY7----*/
struct query7 {
    double f1[12];
    double f2[12];
    double f3[12];
};


/*Função que inicializa a query7, alocando memória para a mesma.*/
QUERY7 initQUERY7() {
    QUERY7 q = malloc(sizeof(struct query7));
    return q;
}


/*Dada uma estrutura QUERY7 e um mes, retorna o número de unidades compradas nesse mês para a filial 1.*/
double getQUERY7F1(QUERY7 q, int mes){
    return q->f1[mes];
}

/*Dada uma estrutura QUERY7 e um mes, retorna o número de unidades compradas nesse mês para a filial 2.*/
double getQUERY7F2(QUERY7 q, int mes){
    return q->f2[mes];
}

/*Dada uma estrutura QUERY7 e um mes, retorna o número de unidades compradas nesse mês para a filial 3.*/
double getQUERY7F3(QUERY7 q, int mes){
    return q->f3[mes];
}


/*Função que, dada uma estrutura QUERY7, atualiza o valor da quantidade vendida na filial 1 no mês 
* recebido como argumento.
*/
void setQUERY7F1(QUERY7 q, double count, int mes) {
    q->f1[mes] = count;
}


/*Função que, dada uma estrutura QUERY7, atualiza o valor da quantidade vendida na filial 2 no mês 
* recebido como argumento.
*/
void setQUERY7F2(QUERY7 q, double count, int mes) {
    q->f2[mes] = count;
}


/*Função que, dada uma estrutura QUERY7, atualiza o valor da quantidade vendida na filial 3 no mês 
* recebido como argumento.
*/
void setQUERY7F3(QUERY7 q, double count, int mes) {
    q->f3[mes] = count;
}

/*Funçao que destroy a query7, libertando o espaço para ela alocado.*/
void destroyQUERY7(QUERY7 q) {
    g_free(q);
}



/*----ESTRUTURA AUXILIAR À QUERY6----*/
struct query6 {
    int prod;
    int cli;
};

/*Função que inicializa a query6, alocando memória para tal.*/
QUERY6 initQUERY6() {
    QUERY6 q = malloc(sizeof(struct query6));
    return q;
}

/*Função que, dada uma estrutura QUERY6, retorna o número de produtos que nunca foram vendidos.*/
int getQUERY6Prod(QUERY6 q) {
    return q->prod;
}

/*Função que, dada uma estrutura QUERY6, retorna o número de clientes que nunca compraram nada.*/
int getQUERY6Cli(QUERY6 q) {
    return q->cli;
}

/*Função que, dada uma estrutura QUERY6 e um inteiro, atualiza o número de produtos que nunca foram vendidos.*/
QUERY6 setQUERY6Prod(QUERY6 q, int i) {
    q->prod = i;
    return q;
}

/*Função que, dada uma estrutura QUERY6 e um inteiro, atualiza o número de clientes que nunca compraram nada.*/
QUERY6 setQUERY6Cli(QUERY6 q, int i) {
    q->cli = i;
    return q;
}

/*Função que destrói a query6, libertando a memória para ela alocada.*/
void destroyQUERY6(QUERY6 q) {
    g_free(q);
}



/*----ESTRUTURA DE DADOS AUXILIAR À QUERY 3----*/
struct query3 {
    double nvendasP[3];
    double nvendasN[3];
    double factN[3];
    double factP[3];
};


/*Função que inicializa a query3, alocando memória para tal.*/
QUERY3 initQUERY3() {
    QUERY3 q = malloc(sizeof(struct query3));
    return q;
}

/*Função que, dada uma QUERY3 e uma filial, retorna o número de vendas em Promoção efetuadas nessa filial.*/
double getQUERY3VendasP(QUERY3 q3, int fil) {
    return q3->nvendasP[fil];
}

/*Função que, dada uma QUERY3 e uma filial, retorna o número de vendas em modo Normal efetuadas nessa filial.*/
double getQUERY3VendasN(QUERY3 q3, int fil) {
    return q3->nvendasN[fil];
}

/*Função que, dada uma QUERY3 e uma filial, retorna a faturação em Promoção dessa filial.*/
double getQUERY3FactP(QUERY3 q3, int fil) {
    return q3->factP[fil];
}

/*Função que, dada uma QUERY3 e uma filial, retorna a faturação em modo Normal dessa filial.*/
double getQUERY3FactN(QUERY3 q3, int fil) {
    return q3->factN[fil];
}


/*Função que, dada uma QUERY3, atualiza o valor do número de vendas em Promoção para a filial recebida
* como argumento.
*/
QUERY3 setQUERY3VendasP(QUERY3 q, int i, double n) {
    q->nvendasP[i] = n;
    return q;
}


/*Função que, dada uma QUERY3, atualiza o valor do número de vendas em Promoção para a filial recebida
* como argumento.
*/
QUERY3 setQUERY3VendasN(QUERY3 q, int i, double n) {
    q->nvendasN[i] = n;
    return q;
}


/*Função que, dada uma QUERY3, atualiza o valor da faturação em modo Normal para a filial recebida
* como argumento.
*/
QUERY3 setQUERY3FactN(QUERY3 q, int i, double n) {
    q->factN[i] = n;
    return q;
}


/*Função que, dada uma QUERY3, atualiza o valor da faturação em Promoção para a filial recebida
* como argumento.
*/
QUERY3 setQUERY3FactP(QUERY3 q, int i, double n) {
    q->factP[i] = n;
    return q;
}

/*Função que destroy a query3, libertando o espaço para ela alocado.*/
void destroyQUERY3(QUERY3 q) {
    g_free(q);
}
