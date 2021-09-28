package TrazAqui.Model;

import java.io.Serializable;
import java.util.*;

public class Utilizador implements InterUtilizador , Serializable  {
    /**
     * variáveis de instância(privadas) da classe TrazAqui.Model.Utilizador.
     */
    private String codUtilizador;
    private String nomeUtilizador;
    private InterGPS coordenadas;
    private Map<String,InterEncomenda> encUtilizador;
    private String email;
    private String password;

    /**
     * Construtor parametrizado.
     * @param codigo do utilizador
     * @param nome do utilizador
     * @param coordenadas do TrazAqui.Model.GPS(localização do utilizador)
     * @param encomendas efetuadas pelo utilizador
     * @param password palavra passe do utilizador
     * @param email do utilizador
     */
    public Utilizador(String codigo, String nome, GPS coordenadas, Map<String, InterEncomenda> encomendas, String password, String email) {
        this.codUtilizador = codigo;
        this.nomeUtilizador = nome;
        this.coordenadas = coordenadas;
        setEncUtilizador(encomendas);
        this.email = email;
        this.password = password;
    }

    /**
     * Construtor por cópia.
     * @param u utilizador
     */
    public Utilizador(Utilizador u) {
        this.codUtilizador = u.getCodUtilizador();
        this.nomeUtilizador = u.getNomeUtilizador();
        this.coordenadas = u.getCoordenadas();
        setEncUtilizador(u.getEncUtilizador());
        this.email = u.getEmail();
        this.password = u.getPassword();
    }

    /**
     * Construtor vazio.
     */
    public Utilizador() {
        this.codUtilizador = "";
        this.nomeUtilizador = "";
        this.coordenadas = new GPS();
        this.encUtilizador = new TreeMap<>();
        this.email = "";
        this.password = "";
    }

    /**
     * Método que retorna o email do utilizador
     * @return email do utilizador
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que atualiza o email do utilizador
     * @param email do utilizador
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que retorna a password do utilizador
     * @return palavra passe do utilizador
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método que atualiza a password do utilizador
     * @param password do utilizador
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Método que retorna a cópia das encomendas do utilizador
     * @return cópia do TreeMap da classe utilizador
     */
    public Map<String, InterEncomenda> getEncUtilizador() {
        TreeMap<String, InterEncomenda> enc = new TreeMap<>();

        for(Map.Entry<String,InterEncomenda> e : encUtilizador.entrySet())
            enc.put(e.getKey(),e.getValue().clone());

        return enc;
    }

    /**
     * Efetua uma cópia das encomendas recebidas como parâmetro, para o Map de encomendas presente nesta classe.
     * @param encomendas efetuadas pelo utilizador.
     */
    public void setEncUtilizador(Map<String, InterEncomenda> encomendas) {
        this.encUtilizador = new TreeMap<>();

        for(Map.Entry<String,InterEncomenda> e : encomendas.entrySet())
            this.encUtilizador.put(e.getKey(),e.getValue().clone());

    }

    /**
     * Devolve o código de utilizador.
     * @return codigo
     */
    public String getCodUtilizador() {
        return codUtilizador;
    }

    /**
     * Altera o valor do código de utilizador desta classe, para o código recebido como argumento.
     * @param codigo de utilizador
     */
    public void setCodUtilizador(String codigo) {
        this.codUtilizador = codigo;
    }

    /**
     * Retorna o nome do utilizador.
     * @return nome do utilizador
     */
    public String getNomeUtilizador() {
        return nomeUtilizador;
    }

    /**
     * Altera o valor do nome de utilizador desta classe, para o nome recebido como argumento.
     * @param nome do utilizador
     */
    public void setNomeUtilizador(String nome) {
        this.nomeUtilizador = nome;
    }

    /**
     * Retorna um TrazAqui.Model.GPS(coordenadas do utilizador);
     * @return TrazAqui.Model.GPS
     */
    public InterGPS getCoordenadas() {
        return this.coordenadas;
    }

    /**
     * Atualiza o TrazAqui.Model.GPS presente nesta classe, com as informações do TrazAqui.Model.GPS dadas como argumento.
     * @param g TrazAqui.Model.GPS(localização do utilizador)
     */
    public void setCoordenadas(InterGPS g) {
        this.coordenadas.setX(g.getX());
        this.coordenadas.setY(g.getY());
    }

    /**
     * Método que atualiza o email do utilizador.
     */
    public void atualizaEmail() {
        this.email = this.codUtilizador + "@trazaqui.com";
    }

    /**
     * Método que adiciona uma encomenda ao utilizador.
     * @param e encomenda a adicionar
     */
    public void addEncomenda(InterEncomenda e) {
        this.encUtilizador.put(e.getCodEnc(),e);
    }

    /**
     * Método que atualiza o estado duma encomenda ao utilizador
     * @param codEnc encomenda a atualizar
     */
    public void atualizaEstado(String codEnc) {
        this.encUtilizador.get(codEnc).setEstado(1);
    }

    /**
     * Método clone.
     * @return novo utilizador.
     */
    public Utilizador clone(){
        return new Utilizador(this);
    }

    /**
     * Método que transforma a classe numa string.
     * @return string da classe
     */
    @Override
    public String toString() {
        return "Utilizador{" +
                "codUtilizador='" + codUtilizador + '\'' +
                ", nomeUtilizador='" + nomeUtilizador + '\'' +
                ", coordenadas=" + coordenadas +
                ", encUtilizador=" + encUtilizador +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Método que verifica se dois objetos desta classe são iguais
     * @param o objeto a comparar
     * @return true caso sejam iguais, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return Objects.equals(codUtilizador, that.codUtilizador) &&
                Objects.equals(nomeUtilizador, that.nomeUtilizador) &&
                Objects.equals(coordenadas, that.coordenadas) &&
                Objects.equals(encUtilizador, that.encUtilizador) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    /**
     * Método que calcula o hashCode
     * @return hashCode
     */
    public int hashCode() {
        return Objects.hash(codUtilizador, nomeUtilizador, coordenadas, encUtilizador, email, password);
    }
}
