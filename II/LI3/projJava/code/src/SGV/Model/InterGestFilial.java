package SGV.Model;

import java.util.Map;
import java.util.Set;

public interface InterGestFilial {
    void insereVenda(String [] venda);
    int [][] getClientesFilial();
    double [][] verificaCliente(Cliente cliente);
    Set<Cliente> getClientesProd(Produto prd, int mes);

    String[][] getTop3Filial(int filial);

    Set<ProdQnt> cliProdMaisComp(Cliente cli);

    double[][] verificaMes(int mes);

    Map<String, Double> verificaTOPN(int top);
    Map<Cliente,Double[]> getClientesProduto(Produto prod);
}
