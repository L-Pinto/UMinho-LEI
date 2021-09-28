package TrazAqui.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Transportadora implements InterTransportadora , Serializable {
    /**
     * Variaveis de instancia da classe transportadora
     */
    private String codTransportadora;
    private String nome;
    private InterGPS gps;
    private String nif;
    private double raio;
    private boolean certificado;
    private double preco;
    private Map<String, InterEncomenda> encomendas;
    private Map<String, Integer>  classificacao;
    private String email;
    private String password;
    private boolean livre;
    private double velocidade;
    private boolean aceita;

    /** CONSTRUTORES * */

    /**
     * Construtor Vazio
     */
    public Transportadora()
    {
        this.codTransportadora = " ";
        this.nome = " ";
        this.gps = new GPS();
        this.nif = " ";
        this.raio = 0;
        this.certificado = false;
        this.preco = 0;
        this.encomendas = new TreeMap<>();
        this.classificacao = new TreeMap<>();
        this.email = " ";
        this.password = " ";
        this.livre = true;
        this.velocidade = 0;
        this.aceita = false;
    }

    /**
     * Construtor parametrizado
     * @param codTransportadora código da transportadora
     * @param nome nome da transportadora
     * @param gps localização
     * @param nif nif da transportadora
     * @param raio raio de ação
     * @param certificado de transporte de medicamentos
     * @param preco preço/km
     * @param encomendas encomendas da transportadora
     * @param classificacao classificação dos utilizadores á transportadora
     */
    public Transportadora(String codTransportadora, String nome, InterGPS gps, String nif, double raio, boolean certificado, double preco, Map<String, InterEncomenda> encomendas, Map<String, Integer> classificacao, String email, String password,double velocidade,boolean aceita,boolean livre)
    {
        this.codTransportadora = codTransportadora;
        this.nome = nome;
        this.gps = gps;
        this.nif = nif;
        this.raio = raio;
        this.certificado = certificado;
        this.preco = preco;
        setEncomendas(encomendas);
        setClassificacao(classificacao);
        this.email = email;
        this.password = password;
        this.livre = livre;
        this.velocidade = velocidade;
        this.aceita = aceita;
    }

    /**
     * Construtor por copia
     */
    public Transportadora(Transportadora t)
    {
        this.codTransportadora = t.getCodTransportadora();
        this.nome = t.getNome();
        this.gps = t.getGps();
        this.nif = t.getNif();
        this.raio = t.getRaio();
        this.certificado = t.isCertificado();
        this.preco = t.getPreco();
        setEncomendas(t.getEncomendas());
        setClassificacao(t.getClassificacao());
        this.email = t.getEmail();
        this.password = t.getPassword();
        setLivre(t.isLivre());
        setVelocidade(t.getVelocidade());
        aceitaMedicamentos(t.aceitoTransporteMedicamentos());
    }

    /** GETS & SETS*/

    /**
     * Método que retorna o email de uma transportadora
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que atualiza o email de uma transportadora
     * @param email email a atualizar
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que retorna a password de uma transportadora
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método que atualiza a password da transportadora
     * @param password a atualizar
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método que retorna a velocidade da transportadora
     * @return velocidade
     */
    public double getVelocidade() {
        return velocidade;
    }

    /**
     * Método que atualiza a velocidade da transportadora
     * @param velocidade a atualizar
     */
    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    /**
     * Método que atualiza o email da transportadora
     */
    public void atualizaEmail() {
        this.email = codTransportadora + "@trazaqui.com";
    }

    /**
     * Método que atualiza o gps da transportadora
     * @param gps a atualizar
     */
    public void setGps(InterGPS gps) {
        this.gps = gps;
    }

    /**
     * Método que verifica se a transportadora está livre
     * @return true caso esteja livre, false caso não esteja
     */
    public boolean isLivre() {
        return livre;
    }

    /**
     * Método que altera o estado da transportadora
     * @param livre estado a alterar
     */
    public void setLivre(boolean livre) {
        this.livre = livre;
    }

    /**
     * Método que devolve o código da transportadora
     * @return Código da transportadora
     */
    public String getCodTransportadora() {
        return codTransportadora;
    }

    /**
     * Metodo que atualiza o codigo da trnasportadora
     * @param codTransportadora código para atualizar
     */
    public void setCodTransportadora(String codTransportadora) {
        this.codTransportadora = codTransportadora;
    }

    /**
     * Método que devolve o nome da transportadora
     * @return nome da transportadora
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo que atualiza o nome da trnasportadora
     * @param nome para atualizar
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método que devolve as coordenadas da transportadora
     * @return cordenadas transportadora
     */
    public GPS getGps() {
        return (GPS) gps;
    }

    /**
     * Método que atualiza as coordenadas da transportadora
     * @param gps coordenadas para serem atulizadas
     */
    public void setGps(GPS gps) {
        this.gps = gps.clone();
    }

    /**
     * Método que devolve o nif da transportadora
     * @return nif da transportadora
     */
    public String getNif() {
        return nif;
    }

    /**
     * Método que atualiza o nif da transportadora
     * @param nif para atulizar
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Método que devolve o raio de ação da transportadora
     * @return raio da transportadora
     */
    public double getRaio() {
        return raio;
    }

    /**
     * Método que atualiza o raio de ação da transportadora
     * @param raio para atualizar
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }

    /**
     * Método que devolve se a transportadora tem certificado médico
     * @return certificado da transportadora
     */
    public boolean isCertificado() {
        return certificado;
    }

    /**
     * Método que atualiza o certificado médico da transportadora
     * @param certificado (boolean que atualiza o certificado)
     */
    public void setCertificado(boolean certificado) {
        this.certificado = certificado;
    }

    /**
     * Método que devolve o preço/km da transportadora
     * @return preço da transportadora
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Método que atualiza o preço/km da transportadora
     * @param preco para atualizar
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Método que devolve as encomendas feitas pela transportadora
     * @return encomendas da transportadora
     */
    public Map<String, InterEncomenda> getEncomendas()
    {
        Map<String, InterEncomenda> aux = new TreeMap<>();

        for(Map.Entry<String, InterEncomenda> par: this.encomendas.entrySet())
        {
            aux.put(par.getKey(), par.getValue().clone());
        }
        return aux;
    }

    /**
     * Método que atualiza as encomendas feitas pela transportadora
     * @param encomendas a serem a atualizadas
     */
    public void setEncomendas(Map<String, InterEncomenda> encomendas)
    {
        this.encomendas = new TreeMap<>();

        for(Map.Entry<String, InterEncomenda> par: encomendas.entrySet())
        {
            this.encomendas.put(par.getKey(), par.getValue().clone());
        }
    }

    /**
     * Método que devolve as classificações que os utilizadores fizeram á transportadora
     * @return classificações da transportadora
     */
    public Map<String, Integer> getClassificacao()
    {
        Map<String, Integer> aux = new TreeMap<>();

        for(Map.Entry<String, Integer> par: this.classificacao.entrySet())
        {
            aux.put(par.getKey(), par.getValue());
        }
        return aux;
    }

    /**
     * Método que atualiza as classificações que os utilizadores fizeram á transportadora
     * @param classificacao a serem atualizadas
     */
    public void setClassificacao(Map<String, Integer> classificacao)
    {
        this.classificacao = new TreeMap<>();

        for(Map.Entry<String, Integer> par: classificacao.entrySet())
        {
            this.classificacao.put(par.getKey(), par.getValue());
        }
    }


    /**
     * Método que adiciona uma encomenda à transportadora
     * @param e encomenda a adicionar
     */
    public void adicionaEncomenda(InterEncomenda e) {
        this.encomendas.put(e.getCodEnc(),e);
        this.livre = false;
    }

    /**
     * Método que atualiza o estado de uma encomenda
     * @param codEnc encomenda a atualizar
     */
    public void encPronta(String codEnc) {
        this.encomendas.get(codEnc).setEstado(1);
    }

    /**
     * Método que atualiza a encomenda pronta a ser entregue, para entregue
     * @return encomenda entregue
     */
    public InterEncomenda entregaEnc(){
        InterEncomenda enc = null;

        for(Map.Entry<String,InterEncomenda> e : this.encomendas.entrySet()) {
            if (e.getValue().getEstado() == 1) {
                e.getValue().setEstado(2);
                enc = e.getValue();
            }
        }

        return enc;
    }

    /**
     * Método que verifica as encomendas da transportadora realizadas por um dado utilizador
     * @param codUser código do utilizador
     * @param res lista a ser atualizada com os códigos de encomenda
     */
    public void verificaEncUtilizador(String codUser, List<String> res) {

        for(Map.Entry<String,InterEncomenda> e : this.encomendas.entrySet())
            if (e.getValue().getCodUser().equals(codUser) && !this.classificacao.containsKey(e.getKey())
                && e.getValue().getEstado() == 2) res.add(e.getKey());

    }

    /**
     * Método que atualiza a classificação de uma dada encomenda
     * @param codEnc código da encomenda a atualizar
     * @param classif classificação daquela encomenda
     */
    public void setClassificacao(String codEnc, int classif) {
        this.classificacao.put(codEnc,classif);
    }

    /**
     * Método que adiciona uma encomenda à transportadora
     * @param e encomenda a adicionar
     */
    public void addEncomenda(InterEncomenda e) { //DOIS METODOS IGUAIS. APAGAR 1
        this.encomendas.put(e.getCodEnc(),e);
    }


    /**
     * Método que verifica se existe uma encomenda com um dado código de encomenda na transportadora
     * @param codEnc código da encomenda a verificar
     * @return true caso exista, false caso contrário
     */
    public boolean verificaEnc(String codEnc) {
        boolean res = false;

        for(Map.Entry<String,InterEncomenda> e : this.encomendas.entrySet()) {
            if (e.getKey().equals(codEnc)) {
                res = true;
                break;
            }
        }

        return res;
    }

    /**
     * Método que retorna o estado de aceitação de medicamentos
     * @return true caso aceite, false caso contrário
     */
    public boolean aceitoTransporteMedicamentos() {
        return aceita;
    }

    /**
     * Método que atualiza o estado de aceitação de encomendas médicas
     * @param state estado a atualizar
     */
    public void aceitaMedicamentos(boolean state) {
        aceita = state;
    }

    /**
     * Método que retorna a faturação da loja entre duas datas
     * @param d1 limite inferior
     * @param d2 limite superior
     * @return faturação
     */
    public double getFaturacao(LocalDate d1, LocalDate d2) {
        double fact = 0;

        for(InterEncomenda e : this.encomendas.values()) {
            if (e.getData().isAfter(d1) && e.getData().isBefore(d2)) fact += e.getCustoEnc();
        }

        return fact;
    }

    /**
     * Método que calcula os kms percorridos pela transportadora
     * @return kms
     */
    public double contaKms(){
        double res =0.0;
        for(InterEncomenda aux : this.encomendas.values()){
            res += (aux.getTempoEnc()/60)*this.velocidade;
        }
        return res;
    }


    /** OUTROS METODOS*/

    /**
     * Método que passa a classe TrazAqui.Model.Transportadora para uma String
     * @return String
     */
    public String toString() {
        return "Transportadora{" +
                "codTransportadora='" + codTransportadora + '\'' +
                ", nome='" + nome + '\'' +
                ", gps=" + gps +
                ", nif='" + nif + '\'' +
                ", raio=" + raio +
                ", certificado=" + certificado +
                ", preco=" + preco +
                ", encomendas=" + encomendas +
                ", classificacao=" + classificacao +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", livre=" + livre +
                ", velocidade=" + velocidade +
                ", aceita=" + aceita +
                '}';
    }

    /**
     * Metodo que determina se dois objetos da classe TrazAqui.Model.Transportadora são iguais
     * @param o objeto a comparar
     * @return boolean (iguais ou nao)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transportadora that = (Transportadora) o;
        return Double.compare(that.raio, raio) == 0 &&
                certificado == that.certificado &&
                Double.compare(that.preco, preco) == 0 &&
                livre == that.livre &&
                Double.compare(that.velocidade, velocidade) == 0 &&
                aceita == that.aceita &&
                Objects.equals(codTransportadora, that.codTransportadora) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(gps, that.gps) &&
                Objects.equals(nif, that.nif) &&
                Objects.equals(encomendas, that.encomendas) &&
                Objects.equals(classificacao, that.classificacao) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    /**
     * Metodo que clona um objeto da classe TrazAqui.Model.Transportadora
     * @return copia do TrazAqui.Model.Transportadora
     */
    public Transportadora clone()
    {
        return new Transportadora(this);
    }

    /**
     * Calcula o codigo de hash.
     * @return codigo de hash.
     */
    public int hashCode() {
        return Objects.hash(codTransportadora, nome, gps, nif, raio, certificado, preco, encomendas, classificacao, email, password, livre, velocidade, aceita);
    }
}