package SGV.Model;

import java.util.Map;
import java.util.Set;

public interface InterCatProdutos {
    int getLidas();
    void setLidas(int lidas);
    void replaceValue(Produto prod, String filial,double preco, double qnt);
    void adicionaProduto(Produto produto);
    boolean containsProduto(Produto p);
    String toString();
    CatProdutos clone();

    // QUERIES //

    int getSize();
    Set<String> listaProdNC();
    Map<String,Double> verificaTOPN(int top);
}
