#include "navModel.h"
#include "leitura.h"

typedef struct sgv * SGV;

char * getSGVFileClient(SGV s);
char * getSGVFileProduct(SGV s);
char * getSGVFileSales(SGV s);
CLIENTES getSGVClientes(SGV s);
PRODUTOS getSGVProdutos(SGV s);
FATURACAO getSGVFaturacao(SGV s);
GEST_FILIAL getSGVGestFilial(SGV s);

SGV initSGV();
SGV loadSGVFromFiles(SGV s, char * clientsFilePath, char * productsFilePath, char * salesFilePath);
NAVEGADOR getProductsStartedByLetter(SGV s,char letter);
QUERY3 getProductSalesAndProfit(SGV sgv, char * productID, int month);
NAVEGADOR getProductsNeverBought(SGV sgv, int branchID);
NAVEGADOR getClientsOfAllBranches(SGV sgv);
QUERY6 getClientsAndProductsNeverBoughtCount(SGV sgv);
QUERY7 getProductsBoughtByClient(SGV sgv, char* clientID);
QUERY8 getSalesAndProfit(SGV sgv, int minMonth, int maxMonth);
NAVEGADOR getProductBuyers(SGV sgv, char * productID, int branch);
NAVEGADOR getClientFavoriteProducts(SGV sgv, char * clientID, int month);
NAVEGADORQ11 getTopSelledProducts(SGV sgv, int limit);
NAVEGADORQ11 getClientTopProfitProducts(SGV sgv, char * clientID, int limit);
SGV getCurrentFilesInfo(SGV sgv);
void destroySGV(SGV s);
