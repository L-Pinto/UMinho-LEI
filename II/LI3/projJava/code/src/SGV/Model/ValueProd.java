package SGV.Model;


import java.io.Serializable;

public class ValueProd implements Serializable {
    private int vendas;
    private double qnt;
    private double preco;
    private String filial;

    /**
     * Construtor parametrizado da classe ValueProd.
     * @param vendas de um produto
     * @param qnt vendida de um produto
     * @param preco do produto
     * @param filial onde o produto foi vendido
     */
    public ValueProd(String filial, double qnt, double preco,int vendas) {
        this.vendas = vendas;
        this.qnt = qnt;
        this.preco = preco * qnt;
        this.filial = filial;
    }

    
    /**
     * Construtor vazio da classe ValueProd.
     */
    public ValueProd() {
        this.filial = "";
        this.qnt = 0.0;
        this.preco = 0.0;
        this.vendas = 0;
    }

    /**
     * Método que atualiza as variáveis de instância presentes nesta classe consoante os argumentos recebidos.
     * @param filial onde o produto foi comprado.
     * @param preco do produto.
     * @param qnt de produtos vendidos naquela venda.
     */
    public void atualizaVProd(String filial,double preco, double qnt) {
        if (!this.filial.contains(filial)) this.filial = this.filial+filial;
        this.preco = this.preco + preco*qnt;
        this.qnt = this.qnt + qnt;
        this.vendas = this.vendas+1;
    }

    /**
     * Método que verifica se a String filial desta classe é vazia ou não.
     * @return true caso a String seja vazia, false caso contrário.
     */
    public boolean verificaFilial(){
        boolean res = false;
        if (this.filial.isEmpty()) res = true;
        return res;
    }

    /**
     * Atualiza o preço do produto.
     * @param preco que irá atualizar a classe.
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Retorna o número de vendas daquele produto.
     * @return numero de vendas-
     */
    public int getVendas() {
        return vendas;
    }

    /**
     * Método que atualiza a variável vendas desta classe, para o valor recebido como argumento.
     * @param ncli valor que ira atualizar a variável vendas.
     */
    public void setVendas(int ncli) {
        this.vendas = ncli;
    }

    /**
     * Método que retorna a quantidade do produto que foi vendida.
     * @return quantidade do produto.
     */
    public double getQnt() {
        return qnt;
    }

    /**
     * Método que atualiza a quantidade vendida do produto.
     * @param qnt que irá atualizar a classe.
     */
    public void setQnt(double qnt) {
        this.qnt = qnt;
    }

    /**
     * Transforma a classe ValueProd em String.
     * @return String do ValueProd.
     */
    public String toString() {
        return "\nValueProd{" +
                "vendas=" + vendas +
                ", qnt=" + qnt +
                ", preco=" + preco +
                ", filial='" + filial + '\'' +
                '}';
    }

    /**
     * Retorna o preço a que o produto foi vendido.
     * @return preco.
     */
    public double getFact() {
        return this.preco;
    }

}
