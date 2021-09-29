package SGV.Model;

import java.io.Serializable;

public class Produto implements InterProduto , Comparable<Produto> , Serializable {
    private String produto;

    /**
     * Construtor parametrizado da classe Produto.
     * @param produto que irá atualizar as variáveis de instância da classe.
     */
    public Produto(String produto) {
        this.produto = produto;
    }

    /**
     * Construtor por cópia.
     * @param produto Produto a ser copiado.
     */
    public Produto(Produto produto) {
        this.produto = produto.getProduto();
    }

    /**
     * Método que retorna a String produto deste produto.
     * @return string do produto.
     */
    public String getProduto() {
        return produto;
    }

    /**
     * Método que verifica se o produto é válido.
     * @return true caso o produto seja válido, false caso contrário.
     */
    public boolean valido_prod() {
        return (this.produto.length() == 6
                && Character.isUpperCase(this.produto.charAt(0))
                && Character.isUpperCase(this.produto.charAt(1))
                && Character.isDigit(this.produto.charAt(2))
                && (this.produto.charAt(2) != '0')
                && Character.isDigit(this.produto.charAt(3))
                && Character.isDigit(this.produto.charAt(4))
                && Character.isDigit(this.produto.charAt(5)));
    }

    /**
     * Método compareTo que coloca os produtos por ordem alfabetica.
     * @param produto A comparar.
     * @return inteiro.
     */
    public int compareTo(Produto produto) {
        String cod = this.produto;
        String cod1 = produto.getProduto();

        return cod.compareTo(cod1);
    }

    /**
     * Método que efetua a cópia de um Produto.
     * @return novo Produto igual ao Produto desta classe.
     */
    public Produto clone() {
        return new Produto(this);
    }
}
