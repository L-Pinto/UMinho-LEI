package SGV.Model;

public class CliQnt implements Comparable<CliQnt> {
    private Cliente cliente;
    private double qnt;

    /**
     * Construtor parametrizado.
     * @param cliente que irá atualizar a classe.
     * @param qnt que irá atualizar a classe.
     */
    public CliQnt(Cliente cliente, double qnt) {
        this.cliente = cliente;
        this.qnt = qnt;
    }

    /**
     * Método que retorna a quantidade presente nesta classe.
     * @return quantidade.
     */
    public double getQnt() {
        return qnt;
    }

    /**
     * Método que retorna o código de cliente(String cliente) do cliente presente nesta classe.
     * @return String do cliente.
     */
    public String getCliString() {
        return this.cliente.getCliente();
    }

    
    /**
     * Método compareTo utilizado para ordenar objetos CliQnt.
     * @param cli objeto a ser comparado.
     * @return inteiro.
     */
    public int compareTo(CliQnt cli) {
        String t = this.getCliString();
        if(this.qnt > cli.getQnt()) return -1;
        else if(this.qnt < cli.getQnt()) return 1;
        return t.compareTo(cli.getCliString());
    }
}