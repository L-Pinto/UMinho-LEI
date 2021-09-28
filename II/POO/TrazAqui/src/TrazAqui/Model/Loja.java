package TrazAqui.Model;

import java.io.Serializable;
import java.util.*;
import java.util.TreeMap;

public class Loja implements InterLoja , Serializable
{
    /**
     * Variaveis de instancia para a classe Loja
     */
    private String codLoja;
    private String nome;
    private InterGPS gps;
    private double tempo;
    private Map<String, InterEncomenda> encomendasLoja;
    private String email;
    private String password;
    private Map<String,InterLinhaEncomenda> produtos;


    /** CONSTRUTORES */

    /**
     * Construtor vazio
     */
    public Loja ()
    {
        this.codLoja = " ";
        this.nome = " ";
        this.gps = new GPS();
        this.tempo = 0;
        this.encomendasLoja = new TreeMap<>();
        this.email = " ";
        this.password = " ";
        this.produtos = new TreeMap<>();
    }

    /**
     * Construtor Parametrizado
     * @param codLoja código da loja
     * @param nome nome da loja
     * @param gps localização
     * @param tempo de espera na loja
     * @param encomendasLoja encomendas da loja
     */
    public Loja(String codLoja, String nome, GPS gps, double tempo, Map<String, InterEncomenda> encomendasLoja, String email, String password,Map<String,InterLinhaEncomenda> prod)
    {
        this.codLoja = codLoja;
        this.nome = nome;
        this.gps = gps;
        this.tempo = tempo;
        setEncomendasLoja(encomendasLoja);
        this.email = email;
        this.password = password;
        setProdutos(prod);
    }

    /**
     * Construtor Cópia
     */
    public Loja(Loja l)
    {
        this.codLoja = l.getCodLoja();
        this.nome = l.getNome();
        this.gps = l.getGps();
        this.tempo = l.getTempo();
        setEncomendasLoja(l.getEncomendasLoja());
        this.email = l.getEmail();
        this.password = l.getPassword();
        setProdutos(l.getProdutos());
    }


    /** SETS & GETS */

    /**
     * Método que retorna os produtos de uma dada loja
     * @return map com o código do produto e a linha de encomenda correspondente
     */
    public Map<String, InterLinhaEncomenda> getProdutos() {
        return produtos;
    }

    /**
     * Método que atualiza os produtos
     * @param produtos que irão atualizar
     */
    public void setProdutos(Map<String, InterLinhaEncomenda> produtos) {
        this.produtos = produtos;
    }

    /**
     * Método que retorna o email de uma loja
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que atualiza o email de uma loja
     * @param email a atualizar
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que retorna a password de uma loja
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método que atualiza a password de uma loja
     * @param password a atualizar
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Metodo que retorna o código da TrazAqui.Model.Loja
     * @return código da loja
     */
    public String getCodLoja() {
        return codLoja;
    }

    /**
     * Metodo que retorna o código da TrazAqui.Model.Loja
     * @return código da loja
     */
    public void setCodLoja(String cod) {
        this.codLoja = cod;
    }

    /**
     * Metodo que retorna o nome da TrazAqui.Model.Loja
     * @return nome da loja
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo que retorna o nome da TrazAqui.Model.Loja
     * @return nome da loja
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo que retorna as coordenadas da TrazAqui.Model.Loja
     * @return coordenadas da loja
     */
    public InterGPS getGps()
    {
        return gps;
    }

    public void setGps(GPS gps)
    {
        this.gps = gps;
    }

    /**
     * Metodo que retorna o tempo de espera dentro da TrazAqui.Model.Loja
     * @return tempo de espera da loja
     */
    public double getTempo() {
        return tempo;
    }

    /**
     * Metodo que atualiza o tempo de espera dentro da TrazAqui.Model.Loja
     * @param tempo para ser atualizado
     */
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    /**
     * Metodo que retorna as encomendas da TrazAqui.Model.Loja
     * @return encomendas da loja
     */
    public Map<String, InterEncomenda> getEncomendasLoja()
    {
        Map<String, InterEncomenda> aux = new TreeMap<>();

        for(Map.Entry<String, InterEncomenda> par: this.encomendasLoja.entrySet())
        {
            aux.put(par.getKey(), par.getValue().clone());
        }
        return aux;
    }

    /**
     * Metodo que atualiza as encomendas da Loja
     * @param encomendaLoja encomendas da loja para serem atualizadas
     */
    public void setEncomendasLoja(Map<String, InterEncomenda> encomendaLoja)
    {
        this.encomendasLoja = new TreeMap<>();
        for(Map.Entry<String, InterEncomenda> par: encomendaLoja.entrySet())
        {
            this.encomendasLoja.put(par.getKey(), par.getValue().clone());
        }
    }

    /**
     * Método que atualiza os produtos da loja, consoante os produtos vendidos pela mesma numa encomenda
     * @param e encomenda
     */
    public void atualizaProdutosEnc(InterEncomenda e) {
        Map<String, InterLinhaEncomenda> lista = e.getLinhasEncomenda();

        for (Map.Entry<String, InterLinhaEncomenda> l : lista.entrySet()) {
            if (!this.produtos.containsKey(l.getKey())) this.produtos.put(l.getKey(), l.getValue());
        }

        this.encomendasLoja.put(e.getCodEnc(), e);
    }

    /**
     * Método que calcula/atualiza o email da loja
     */
    public void atualizaEmail() {
        this.email = codLoja + "@trazaqui.com";
    }


    /**
     * Método que recolhe a descrição dos produtos e o seu respetivo preço num map
     * @return map com a descrição produtos e a respetiva quantidade
     */
    public Map<String,Double> getProd() {
        Map<String,Double> map = new LinkedHashMap<>();

        for(Map.Entry<String,InterLinhaEncomenda> p : this.produtos.entrySet()) {
            map.put(p.getValue().getDescricaoProd(),p.getValue().getPrecoProd());
        }

        return map;
    }

    /**
     * Método que retorna o código de um produto, dada a descrição do mesmo
     * @param descricao do produto
     * @return código do produto
     */
    public String getProdCodigo(String descricao) {
        String cod = "";

        for(Map.Entry<String,InterLinhaEncomenda> l : this.produtos.entrySet()) {
            if (l.getValue().getDescricaoProd().equals(descricao)) cod = l.getValue().getCodProd();
        }

        return cod;
    }

    /**
     * Método que adiciona uma encomenda a uma loja
     * @param e encomenda a adicionar
     */
    public void adicionaEncomenda(InterEncomenda e) {
        this.encomendasLoja.put(e.getCodEnc(),e);
    }

    /**
     * Calcula as encomendas que ainda não foram entregues de uma dada loja
     * @return lista com os códigos de encomenda que ainda não foram entregues
     */
    public List<String> getEncLojaNE(){
        List<String> res = new ArrayList<>();

        for(Map.Entry<String,InterEncomenda> e : this.encomendasLoja.entrySet()) {
            if (e.getValue().getEstado() == 0) res.add(e.getValue().getCodEnc());
        }

        return res;
    }

    /**
     * Método que efetua a entrega de uma encomenda
     * @param codEnc código de encomenda a entregar
     */
    public void entregaEnc(String codEnc) {
        this.encomendasLoja.get(codEnc).setEstado(1);
    }

    /**
     * Método que calcula as encomendas que estão prontas
     * @return lista com os códigos de encomenda que estão prontas
     */
    public List<String> encomendasProntas(){
        List<String> l = new ArrayList<>();

        for(Map.Entry<String,InterEncomenda> e : this.encomendasLoja.entrySet()) {
            if (e.getValue().getEstado() == 0) {
                e.getValue().setEstado(1);
                l.add(e.getKey());
            }
        }

        return l;
    }

    /**
     * Método que coloca uma encomenda pronta a ser entregue à transportadora.
     * @param codEnc código da encomenda
     */
    public void encPronta(String codEnc) {
        this.encomendasLoja.get(codEnc).setEstado(1);
    }

    /** OUTROS MÉTODOS */

    /**
     * Método que passa a classe TrazAqui.Model.Loja para uma String
     * @return String
     */
    public String toString()
    {
        return  "Loja { " +
                "codLoja = '" + codLoja + '\'' +
                ", nome = '" + nome + '\'' +
                ", gps = " + gps +
                ", tempo = " + tempo +
                ", encomendasLoja = " + encomendasLoja +
                ", PRODUTOS = " + produtos +
                '}';
    }

    /**
     * Metodo que determina se dois objetos da classe TrazAqui.Model.Loja são iguais
     * @param o objeto a comparar
     * @return boolean (igual ou não)
     */
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loja loja = (Loja) o;

        if (Double.compare(loja.getTempo(), getTempo()) != 0) return false;
        if (getCodLoja() != null ? !getCodLoja().equals(loja.getCodLoja()) : loja.getCodLoja() != null) return false;
        if (getNome() != null ? !getNome().equals(loja.getNome()) : loja.getNome() != null) return false;
        if (getGps() != null ? !getGps().equals(loja.getGps()) : loja.getGps() != null) return false;
        return getEncomendasLoja() != null ? getEncomendasLoja().equals(loja.getEncomendasLoja()) : loja.getEncomendasLoja() == null;
    }

    /**
     * Metodo que clona um objeto da classe TrazAqui.Model.Loja
     * @return copia do TrazAqui.Model.Loja
     */
    public Loja clone()
    {
        return new Loja(this);
    }

    /**
     * Calcula o codigo de hash.
     * @return codigo de hash.
     */
    public int hashCode() {
        return Objects.hash(getCodLoja(), getNome(), getGps(), getTempo(), getEncomendasLoja());
    }
}