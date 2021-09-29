package SGV;

import java.util.Map;
import java.util.Set;

public interface InterNavModel {
    void setPaginaAtual(int pagA);
    int getPaginaAtual();
    Set<String> getPagina(int pag);
    int getSizeTotal();
    int procuraProduto(String produto);
    int getMaxPagQ10();
    int procuraProdutoQ10(String produto);
    Map<String,Double> getPagNavQ10(int i);

    int getMaxPagQ15();
    int procuraProdutoQ15(String produto);
    Map<String,Double[][]> getPagNavQ15(int i);
}
