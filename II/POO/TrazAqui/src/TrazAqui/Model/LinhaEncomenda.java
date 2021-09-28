package TrazAqui.Model;

import java.io.Serializable;

public class LinhaEncomenda implements InterLinhaEncomenda , Serializable {

    /*--------------------------  variaveis de instancia  --------------------------*/
    /** Codido de Produto */
    private String codProd;
    /** Descricao de Produto */
    private String descricaoProd;
    /** Quantidade de produto */
    private double qntProd;
    /** Preco de produto */
    private double precoProd;

    /*--------------------------  construtores  --------------------------*/

    /** Metodo construtor vazio */
    public LinhaEncomenda() {
        this.codProd = new String("");
        this.descricaoProd = new String("");
        this. qntProd = 0;
        this.precoProd = 0.0;
    }

    /** Metodo construtor parametrizado
     * @param codProd - codigo produto
     * @param descricaoProd - descricao produto
     * @param qntProd - quantidade produto
     * @param precoProd - preco unitario produto */
    public LinhaEncomenda(String codProd, String descricaoProd, int qntProd, double precoProd) {
        this.codProd = codProd;
        this.descricaoProd = descricaoProd;
        this.qntProd = qntProd;
        this.precoProd = precoProd;
    }

    /** Metodo construtor por copia
     * @param l - Linha de TrazAqui.Model.Encomenda */
    public LinhaEncomenda(LinhaEncomenda l) {
        setCodProd(l.getCodProd());
        setDescricaoProd(l.getDescricaoProd());
        setQntProd(l.getQntProd());
        setPrecoProd(l.getPrecoProd());
    }

    /*-------------------------- gets, sets  --------------------------*/

    /** Método para retorno de um codido de produto
     * @return String - Codigo do produto*/
    public String getCodProd() {
        return codProd;
    }

    /** Método para retorno de uma descricao de produto
     * @return String - Descricao do produto*/
    public String getDescricaoProd() {
        return descricaoProd;
    }

    /** Método para retorno de uma quantidade de produto
     * @return int - Quantidade produto*/
    public double getQntProd() {
        return qntProd;
    }

    /** Método para retorno de um preco unitario de produto
     * @return double - Preco unitario produto*/
    public double getPrecoProd() {
        return precoProd;
    }


    /** Método para definição de um codido de produto
     * @param codProd - codigo produto*/
    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    /** Método para definição de uma descricao de produto
     * @param descricaoProd - descricao produto*/
    public void setDescricaoProd(String descricaoProd) {
        this.descricaoProd = descricaoProd;
    }

    /** Método para definição de uma quantidade de produto
     * @param qntProd - quantidade */
    public void setQntProd(double qntProd) {
        this.qntProd = qntProd;
    }

    /** Método para definição de um preco unitario de produto
     * @param precoProd - preco unitario*/
    public void setPrecoProd(double precoProd) {
        this.precoProd = precoProd;
    }

    /*-------------------------- clone, equals, toString --------------------------*/

    /** Metodo de copia de Linha de TrazAqui.Model.Encomenda
     * @return TrazAqui.Model.LinhaEncomenda - TrazAqui.Model.LinhaEncomenda clonada*/
    public LinhaEncomenda clone() {
        return new LinhaEncomenda(this);
    }

    /** Metodo de comparacao entre duas Linhas de TrazAqui.Model.Encomenda
     * @param o - TrazAqui.Model.LinhaEncomenda de comparacao
     * @return boolean - verdade ou falso */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinhaEncomenda that = (LinhaEncomenda) o;

        if (getQntProd() != that.getQntProd()) return false;
        if (Double.compare(that.getPrecoProd(), getPrecoProd()) != 0) return false;
        if (getCodProd() != null ? !getCodProd().equals(that.getCodProd()) : that.getCodProd() != null) return false;
        return getDescricaoProd() != null ? getDescricaoProd().equals(that.getDescricaoProd()) : that.getDescricaoProd() == null;
    }

    /** Metodo para retorno de uma mensagem de uma Linha de TrazAqui.Model.Encomenda.
     * @return String - TrazAqui.Model.LinhaEncomenda + (respetivos parametros)*/
    public String toString() {
        return "\nLinhaEncomenda{" +
                "codProd='" + codProd + '\'' +
                ", descricaoProd='" + descricaoProd + '\'' +
                ", qntProd=" + qntProd +
                ", precoProd=" + precoProd +
                '}';
    }
}