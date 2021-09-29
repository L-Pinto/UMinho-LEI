package SGV.Model;

public class ValueQ11 implements Comparable<ValueQ11>  {
    private String produto;
    private double qnt;

    /**
     * Construtor parametrizado da classe ValueQ11.
     * @param produto que irá atualizar a variável de instância produto.
     * @param qnt que irá atualizar a variável de instância qnt.
     */
    public ValueQ11(String produto, double qnt) {
        this.produto = produto;
        this.qnt = qnt;
    }

    /**
     * Retorna a String produto presente nesta classe.
     * @return String do produto.
     */
    public String getProduto() {
        return produto;
    }

    /**
     * Atualiza a String produto presente nesta classe.
     * @param produto que irá atualizar a classe.
     */
    public void setProduto(String produto) {
        this.produto = produto;
    }

    /**
     * Retorna a variável quantidade.
     * @return quantidade.
     */
    public double getQnt() {
        return qnt;
    }

    /**
     * Método compareTo para ordenação de objetos ValueQ11.
     * @param ValueQ11 objeto a ser comparado.
     * @return inteiro.
     */
    public int compareTo(ValueQ11 ValueQ11) {
        int res = (int) (ValueQ11.getQnt()-this.qnt);

        if (res == 0) res = ValueQ11.getProduto().compareTo(produto);

        return res;
    }
}
