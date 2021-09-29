package SGV.Model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class CatClientes implements Serializable , InterCatClientes {
    private Map<Cliente,String> catalogo;
    private int lidas;

    /**
     * Construtor parametrizado do Catálogo de Clientes.
     * @param catalogo de clientes.
     * @param lidas número de vendas lidas.
     */
    public CatClientes(Map<Cliente,String> catalogo, int lidas) {
        this.catalogo = catalogo;
        this.lidas = lidas;
    }

    /**
     * Construtor por cópia.
     * @param c Catálogo de Clientes.
     */
    public CatClientes(CatClientes c) {
        setCatalogo(getCatalogo());
        this.lidas = getLidas();
    }

    /**
     * Construtor vazio de Catálogo de Clientes.
     */
    public CatClientes() {
        this.catalogo = new TreeMap<>();
        this.lidas = 0;
    }

    /**
     * Atualiza o catálogo de clientes.
     * @param catalogo estrutura com os clientes.
     */
    public void setCatalogo(Map<Cliente,String> catalogo) {
        this.catalogo = catalogo;
    }

    /**
     * Atualiza o número de clientes lidos.
     * @param lidas número de clientes lidos.
     */
    public void setLidas(int lidas) {
        this.lidas = lidas;
    }

    /**
     * Retorna o catálogo de clientes.
     * @return catálogo de clientes presente nesta classe.
     */
    public Map<Cliente,String> getCatalogo() {
        return this.catalogo;
    }

    /**
     * Método que retorna o número de vendas lidas.
     * @return número de vendas lidas.
     */
    public int getLidas() {
        return lidas;
    }

    /**
     * Método equals da classe CatClientes.
     * @param o objeto a comparar.
     * @return true caso os objetos sejam iguais, false caso contrário.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatClientes)) return false;
        CatClientes that = (CatClientes) o;
        return getLidas() == that.getLidas() &&
                getCatalogo().equals(that.getCatalogo());
    }


    /**
     * Método clone da classe CatClientes.
     * @return CatClientes.
     */
    public CatClientes clone() {
        return new CatClientes(this);
    }

    /**
     * Transforma a classe numa String.
     * @return String da classe CatClientes.
     */
    public String toString() {
        return "CatClientes{" +
                "catalogo=" + catalogo +
                ", lidas=" + lidas +
                '}';
    }

    /**
     * Método que verifica se contém um dado cliente na estrutura de dados do catálogo.
     * @param cliente a verificar.
     * @return true caso o cliente exista, false caso contrário.
     */
    public boolean containsCliente(Cliente cliente) {
        return this.catalogo.containsKey(cliente);
    }

    /**
     * Método que adiciona um cliente ao catálogo.
     * @param cliente a adicionar.
     */
    public void adicionaCliente(Cliente cliente) {
        String filial = "";
        this.catalogo.put(cliente,filial);
    }

    /**
     * Função que atualiza o value de cada cliente no Map do catálogo, aquando da leitura das Vendas.
     * @param cli a ser atualizado.
     * @param filial onde esse cliente efetuou compras.
     */
    public void replaceValue(Cliente cli, String filial){
        String newString = this.catalogo.get(cli);
        if (! (newString.contains(filial)) ) newString = newString+filial;
        this.catalogo.replace(cli, newString);
    }

    //QUERIES

    /**
     * Método que calcula o número de clientes que realizaram compras.
     * @return número de clientes que realizaram compras.
     */
    public int getCompraram() {
        return this.catalogo.size() - getNaoCompraram();
    }

    /**
     * Método que calcula o número de clientes que não realizaram compras.
     * @return o número de clientes que não realizaram compras.
     */
    public int getNaoCompraram() {
        int count = 0;

        for(Map.Entry<Cliente,String> c : this.catalogo.entrySet()) {
            if (c.getValue().isBlank()) count ++; 
        }

        return count;
    }

}
