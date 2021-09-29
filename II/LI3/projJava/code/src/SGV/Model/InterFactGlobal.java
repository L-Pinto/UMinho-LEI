package SGV.Model;

import java.util.Map;

public interface InterFactGlobal
{
    public void setFactT(double factT);
    public void limpaFaturacao();
    public void setGratis(int gratis);
    public void setLidas(int lidas);
    public void setValidas(int validas);
    public void insereVenda(String[] venda);

    // QUERIES //

    int getErradas();
    public int getLidas();
    public int getValidas();
    public int getSize();
    public int getGratis();
    public double getFactT();
    public int [] vendasMes();
    public double[] getFactFilial();
    public double[][] verificaProduto(Produto produto);
    public Map<String,Double[][]> factEachProd();

    // OUTROS //
    public String toString();

}
