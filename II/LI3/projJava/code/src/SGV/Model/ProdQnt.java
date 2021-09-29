package SGV.Model;

import java.util.Set;

public class ProdQnt implements Comparable<ProdQnt>
{
    private Produto produto;
    private double qnt;

    /**
     * Construtor parametrizado.
     * @param produto produto que irá atualizar a classe.
     * @param qnt quantidade que irá atualizar a classe.
     */
    public ProdQnt(Produto produto, double qnt) {
        this.produto = produto;
        this.qnt = qnt;
    }

    /**
     * Retorna o produto presente nesta classe.
     * @return Produto.
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Retorna a variável de instância quantidade desta classe.
     * @return quantidade.
     */
    public double getQnt() {
        return qnt;
    }

    /**
     * Retorna o código do Produto presente nesta classe.
     * @return codigo de produto.
     */
    public String getProdString() {
        return this.produto.getProduto();
    }

    /**
     * Método compareTo utilizado para comparar objetos desta clase.
     * @param prod Produto a ser comparado.
     * @return inteiro que dita o modo de ordenação.
     */
    public int compareTo(ProdQnt prod) {
        String t = this.getProdString();
        if(this.qnt > prod.getQnt()) return -1;
        else if(this.qnt < prod.getQnt()) return 1;
        return t.compareTo(prod.getProdString());
    }

    /**
     * Método que atualiza a variável de instância quantidade desta classe.
     * @param qnt que irá atualizar a classe.
     */
    public void setQnt(double qnt) {
        this.qnt = qnt;
    }

    /**
     * Método que verifica se um conjunto contém o Produto presente nesta classe.
     * @param aux conjunto que irá ser percorrido.
     * @return true caso o produto esteja no conjunto, false caso contrário.
     */
    public boolean containsString(Set<ProdQnt> aux) {

        for (ProdQnt prod: aux) {
            if(prod.getProdString().equals(this.produto.getProduto())) {
                double q = prod.getQnt()+this.qnt;
                prod.setQnt(q);
                return true;
            }
        }
        return false;
    }

}
