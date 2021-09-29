package SGV.Model;

import java.io.Serializable;

public class Cliente implements InterCliente , Comparable<Cliente> , Serializable {
    private String cliente;

    /**
     * Construtor parametrizado.
     * @param cliente a atualizar.
     */
    public Cliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * Construtor por cópia.
     * @param cliente a efetuar cópia.
     */
    public Cliente(Cliente cliente) {
        this.cliente = cliente.getCliente();
    }

    /**
     * Construtor vazio.
     */
    public Cliente() {
        this.cliente = "";
    }

    /**
     * Método que retorna a String cliente presente nesta classe.
     * @return String cliente.
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Atualiza a string cliente presente nesta classe.
     * @param cliente valor para o qual a string será atualizada.
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * Verifica se o cliente é válido.
     * @return true caso seja válido, false caso contrário.
     */
    public boolean valido_cli() {
        return Character.isUpperCase(cliente.charAt(0)) &&
                Character.isDigit(cliente.charAt(1)) &&
                Character.isDigit(cliente.charAt(2)) &&
                Character.isDigit(cliente.charAt(3)) &&
                Character.isDigit(cliente.charAt(4)) &&
                Integer.parseInt(cliente.substring(1)) >= 1000
                && Integer.parseInt(cliente.substring(1)) <= 5000;
    }

    /**
     * Verifica se dois objetos desta classe são iguais.
     * @param o objeto a comparar.
     * @return true caso sejam iguais, false caso contrário.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;

        Cliente cliente1 = (Cliente) o;

        return getCliente() != null ? getCliente().equals(cliente1.getCliente()) : cliente1.getCliente() == null;
    }

    /**
     * Método que transforma esta classe em String.
     * @return String da classe Cliente.
     */
    public String toString() {
        return "Cliente{" +
                "cliente='" + cliente + '\'' +
                '}';
    }

    /**
     * Método compareTo da classe Cliente.
     * @param c Cliente a comparar.
     * @return inteiro.
     */
    public int compareTo(Cliente c) {
        int res = this.cliente.compareTo(c.getCliente());
        return res;
    }

    /**
     * Método que efetua uma cópia desta classe.
     * @return cópia da classe.
     */
    public Cliente clone() {
        return new Cliente(this);
    }

}
