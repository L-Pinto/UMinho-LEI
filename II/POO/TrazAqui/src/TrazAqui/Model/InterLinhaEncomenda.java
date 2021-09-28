package TrazAqui.Model;

public interface InterLinhaEncomenda {

    String getCodProd();

    String getDescricaoProd();

    double getQntProd();

    double getPrecoProd();

    void setCodProd(String codProd);

    void setDescricaoProd(String descricaoProd);

    void setQntProd(double qntProd);

    void setPrecoProd(double precoProd);

    LinhaEncomenda clone();
}
