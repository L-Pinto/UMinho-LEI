package SGV.Model;

import java.util.Set;

public class CliVendas implements Comparable<CliVendas>
{
    private Cliente cliente;
    private double vendas;

    /**
     * Construtor parametrizado da classe CliVendas.
     * @param cliente a preencher a classe.
     * @param vendas a preencher a classe.
     */
    public CliVendas(Cliente cliente, double vendas)
    {
        this.cliente = cliente;
        this.vendas = vendas;
    }

    /**
     * Retorna o número de vendas.
     * @return nº de vendas.
     */
    public double getVendas() {
        return vendas;
    }

    /**
     * Retorna o código de cliente.
     * @return código de cliente.
     */
    public String getCodClie()
    {
        return cliente.getCliente();
    }

    /**
     * ComparaTo para a classe CliVendas.
     * @param c CliVendas a comparar.
     * @return inteiro.
     */
    public int compareTo(CliVendas c)
    {
        String t = this.cliente.getCliente();
        if(this.vendas > c.getVendas()) return -1;
        else if(this.vendas < c.getVendas()) return 1;
        return t.compareTo(c.getCodClie());

    }

    /**
     * Verifica se o cliente desta classe está no conjunto recebido como argumento.
     * @param aux conjunto onde vamos verificar se pertence.
     * @return true caso pertença, false caso não pertence.
     */
    public boolean containsCliente(Set<CliVendas> aux)
    {
        for (CliVendas cli: aux)
        {
            if(cli.getCodClie().equals(this.cliente.getCliente()))
            {
                double q = cli.getVendas()+this.vendas;
                aux.remove(cli);
                CliVendas c = new CliVendas(this.cliente,q);
                aux.add(c);
                return false;
            }
        }
        return true;
    }

}


