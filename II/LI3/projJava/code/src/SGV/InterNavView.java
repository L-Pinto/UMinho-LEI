package SGV;

import java.util.Map;
import java.util.Set;

public interface InterNavView {
    void showNavTitulo();
    void showNavMenu();
    void showNavMessage(String s);
    void showNavPointer();
    void showNavERROR(String s);
    void showNavegador(int pag, int total);
    void showPagina(Set<String> pag);
    void showPagProd(Set<String> pagina, String produto);
    void showErrorPag(String s, int pagAt, int totalSize, Set<String> pagina);
    void showTela(int pagAt, int totalSize, Set<String> pagina);
    void showPaginaQ10(Map<String,Double> pag, int num) ;
    void showPagProdQ10(Map<String,Double> pagina, String produto, int num);
    void showErrorPagQ10(String s, int pagAt, int totalSize, Map<String,Double> pagina, int num);
    void showTelaQ10(int pagAt, int totalSize,Map<String,Double>  pagina, int num);

    void showPaginaQ15(Map<String,Double[][]> pag );
    void showErrorPagQ15( String s, int pagAt, int totalSize, Map<String,Double[][]>  pagina);
    void showTelaQ15(int pagAt, int totalSize, Map<String,Double[][]>  pagina );
}
