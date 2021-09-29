package SGV;

import SGV.Model.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface InterGestModel {
    int leituraGestVendas(String clientes, String produtos, String vendas);

    String getFileVendas();

    int getVendasErradas();

    void setFileVendas(String fileVendas);

    void gravarObj(String filename) throws IOException;

    GestVendas lerObj(String filename) throws IOException, ClassNotFoundException;

    int vendasValidas();

    int vendasLidas();

    int getProdDif();

    int getProdNC();

    int getCliC();

    int getCliNC();

    int getVendasGratis();

    double getFactTotal();

    int[][] getClientesFilial();

    int[] comprasPorMes();

    double[] getFaturacaoFilial();

    Set<String> produtosNuncaComprados();

    double[][] resultadosCliente(Cliente cliente);

    double[][] getInfoMes(int mes);

    boolean contemCliente(Cliente cli);

    boolean contemProduto(Produto prod);

    double[][] resultadosProduto(Produto produto);

    int[] getNClientesMes(Produto prod);

    Map<String,Double> produtosTOPN(int top);

    Set<ProdQnt> getProdMaisComp(Cliente cli);

    String[][] getTop3BuyersFilial(int filial);

    Map<String,Double> getClientesProduto(Produto prod, int top);

    Map<String,Double> clientesTOPN(int top);

    Map<String, Double[][]> getFactEachProd();
}

