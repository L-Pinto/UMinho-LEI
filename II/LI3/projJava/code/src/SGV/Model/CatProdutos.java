package SGV.Model;

import java.io.Serializable;
import java.util.*;

public class CatProdutos implements Serializable , InterCatProdutos  {
    private Map<Produto,ValueProd> catalogo;
    private int lidas;

    /**
     * Construtor parametrizado da classe Catálogo de Produtos.
     * @param catalogo de produtos que atualizará o catálogo presente na classe.
     * @param lidos variável para atualizar
     */
    public CatProdutos(Map<Produto,ValueProd> catalogo, int lidos) {
        this.catalogo = catalogo;
        this.lidas = lidos;
    }

    /**
     * Construtor por cópia do Catálogo de Produtos.
     * @param c catálogo para efetuar a cópia.
     */
    public CatProdutos(CatProdutos c) {
        setCatalogo(c.getCatalogo());
        this.lidas = c.getLidas();
    }

    /**
     * Construtor vazio do catálogo de produtos.
     */
    public CatProdutos() {
        this.catalogo = new TreeMap<>();
        this.lidas = 0;
    }

    /**
     * Método que retorna o catálogo de produtos.
     * @return catálogo de produtos presente nesta classe.
     */
    public Map<Produto,ValueProd> getCatalogo() {
        return catalogo;
    }

    /**
     * Método que atualiza o catálogo de produtos.
     * @param catalogo de produtos que atualizará a classe.
     */
    public void setCatalogo(Map<Produto,ValueProd> catalogo) {
        this.catalogo = catalogo;
    }

    /**
     * Retorna o número de produtos lidos.
     * @return número de produtos lidos.
     */
    public int getLidas() {
        return lidas;
    }

    /**
     * Atualiza o número de produtos lidos para o número recebido como argumento.
     * @param lidos número de produtos lidos.
     */
    public void setLidas(int lidos) {
        this.lidas = lidos;
    }

    /**
     * Verifica se dois objetos desta classe são iguais.
     * @param o objeto a comparar.
     * @return true caso sejam iguais, false caso contrário.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatProdutos)) return false;
        CatProdutos that = (CatProdutos) o;
        return getLidas() == that.getLidas() &&
                getCatalogo().equals(that.getCatalogo());
    }

    /**
     * Método que transforma a classe numa String.
     * @return String da classe CatProdutos.
     */
    public String toString() {
        return "CatProdutos{" +
                "catalogo=" + catalogo +
                ", lidos=" + lidas +
                '}';
    }

    /**
     * Método que efetua uma cópia desta classe.
     * @return cópia do CatProdutos.
     */
    public CatProdutos clone() {
        return new CatProdutos(this);
    }

    /**
     * Método que verifica se um dado produto pertence ao catálogo.
     * @param produto a verificar.
     * @return true caso o produto exista na classe, false caso contrário.
     */
    public boolean containsProduto(Produto produto) {
        return this.catalogo.containsKey(produto);
    }

    /**
     * Método que adiciona um produto ao catálogo de produtos.
     * @param produto
     */
    public void adicionaProduto(Produto produto) {
        ValueProd v = new ValueProd();
        this.catalogo.put(produto,v);
    }

    /**
     * Método que atualiza o value do Produto.
     * @param prod cujo value irá ser atualizado.
     * @param filial onde esse produto foi comprado.
     * @param preco da venda desse produto.
     * @param qnt desse produto comprada.
     */
    public void replaceValue(Produto prod, String filial,double preco, double qnt){
        this.catalogo.get(prod).atualizaVProd(filial,preco,qnt);
    }

    /**
     * Método que retorna o tamanho do catálogo de produtos.
     * @return tamanho do catálogo de produtos.
     */
    public int getSize() {
        return this.catalogo.size();
    }

    /**
     * Método que lista o conjunto dos produtos não comprados.
     * @return produtos não comprados.
     */
    public Set<String> listaProdNC(){
        TreeSet<String> t = new TreeSet<>();
        this.catalogo.entrySet().stream().filter(s -> s.getValue().verificaFilial())
                                         .forEach(s -> t.add(s.getKey().getProduto()));
        return t;
    }

    /**
     * Função que verifica o TOP N de produtos mais vendidos, por ordem decrescente.
     * @param top número de produtos pretendido.
     * @return Map com o produto e o preço(inicializado a zero).
     */
    public Map<String,Double> verificaTOPN(int top) {
        TreeSet<ValueQ11> s = new TreeSet<>();

        for(Map.Entry<Produto,ValueProd> vp : this.catalogo.entrySet()) {
            ValueQ11 v = new ValueQ11(vp.getKey().getProduto(),vp.getValue().getQnt());
            s.add(v);
        }

        Map<String,Double> res = new LinkedHashMap<>();

        int i = 0;

        for(ValueQ11 v : s) {
            if (i == top) return res;
            i++;
            res.put(v.getProduto(),0.0);
        }

        return res;
    }

}
