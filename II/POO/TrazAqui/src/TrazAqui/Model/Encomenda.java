package TrazAqui.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Encomenda implements InterEncomenda , Serializable {

    /*--------------------------  variaveis de instancia  --------------------------*/
    /** Codido de Encomenda */
    private String codEnc;
    /** Codido de Utilizador */
    private String codUser;
    /** Codido de Loja */
    private String codLoja;
    /** Peso da Encomenda */
    private double pesoEnc;
    /** Custo da Encomenda */
    private double custoEnc;
    /** Tempo da Encomenda */
    private double tempoEnc;
    /** Encomenda */
    private List<InterLinhaEncomenda> encomenda;
    /** Certificado medico*/
    private boolean certificado;
    /** Estado da encomenda */
    private int estado;
    /** Data da encomenda */
    private LocalDate data;

    /*--------------------------  construtores  --------------------------*/

    /** Metodo construtor vazio */
    public Encomenda() {
        this.codEnc = ("");
        this.codUser = ("");
        this.codLoja = ("");
        this.pesoEnc = 0.0;
        this.custoEnc = 0.0;
        this.tempoEnc = 0.0;
        this.encomenda = new ArrayList<>();
        this.certificado = false;
        this.estado = 2;
        data = LocalDate.now();
    }

    /** Metodo construtor parametrizado
     * @param codEnc - codigo encomenda
     * @param codUser - codigo utilizador
     * @param codLoja - codigo loja
     * @param pesoEnc - peso encomenda
     * @param custoEnc - custo encomenda
     * @param tempoEnc - tempo encomenda
     * @param certificado - certificado medico
     * @param encomenda - encomenda */
    public Encomenda(String codEnc, String codUser, String codLoja, double pesoEnc, double custoEnc, double tempoEnc, ArrayList<InterLinhaEncomenda> encomenda, boolean certificado, int estado,LocalDate data) {
        this.codEnc = codEnc;
        this.codUser = codUser;
        this.codLoja = codLoja;
        this.pesoEnc = pesoEnc;
        this.custoEnc = custoEnc;
        this.tempoEnc = tempoEnc;
        this.encomenda = encomenda;
        this.certificado = certificado;
        this.estado = estado;
        this.data = data;
    }

    /** Metodo construtor parametrizado
     * @param e - TrazAqui.Model.Encomenda*/
    public Encomenda(Encomenda e) {
        setCodEnc(e.getCodEnc());
        setCodUser(e.getCodUser());
        setCodLoja(e.getCodLoja());
        setPesoEnc(e.getPesoEnc());
        setCustoEnc(e.getCustoEnc());
        setTempoEnc(e.getTempoEnc());
        setEncomenda(e.getEncomenda());
        setCertificado(e.isCertificado());
        setEstado(e.getEstado());
        setData(e.getData());
    }


    /*-------------------------- gets, sets  --------------------------*/
    /** Método para retorno de um codido de TrazAqui.Model.Encomenda
     * @return String - Codigo de TrazAqui.Model.Encomenda */
    public String getCodEnc() {
        return codEnc;
    }

    /** Método para retorno de um codido de utilizador
     * @return String - Codigo de utilizador */
    public String getCodUser() {
        return codUser;
    }

    /** Método para retorno de um codido de loja
     * @return String - Codigo de loja */
    public String getCodLoja() {
        return codLoja;
    }

    /** Método para retorno de um peso de encomenda
     * @return double - Peso de encomenda */
    public double getPesoEnc() {
        return pesoEnc;
    }

    /** Método para retorno de um custo de encomenda
     * @return double - Custo de encomenda */
    public double getCustoEnc() {
        return custoEnc;
    }

    /**Método para retorno de um tempo de encomenda
     * @return LocalTime - tempo de encomenda */

    public double getTempoEnc() {
        return tempoEnc;
    }
    /** Método para retorno de uma encomenda
     * @return ArrayList - encomenda(varias linhas) */
    public List<LinhaEncomenda> getEncomenda() {
        List<LinhaEncomenda> enc = new ArrayList<>();

        for(InterLinhaEncomenda e : this.encomenda)
            enc.add(e.clone());

        return enc;
    }

    /** Metodo para retorno de prova se tem ou nao certificado medico
     * @return boolean - se tem ou nao um certificado
     */
    public boolean isCertificado() { return certificado; }

    /** Método para definição de um codido de TrazAqui.Model.Encomenda
     * @param codEnc - Codigo encomenda */
    public void setCodEnc(String codEnc) {
        this.codEnc = codEnc;
    }

    /** Método para definição de um codido de utilizador
     * @param codUser - Codido de utilizador */
    public void setCodUser(String codUser) {
        this.codUser = codUser;
    }

    /** Método para definição de um codido de loja
     * @param codLoja - Codido de loja */
    public void setCodLoja(String codLoja) {
        this.codLoja = codLoja;
    }

    /** Método para definição de um peso de encomenda
     * @param pesoEnc - Peso de encomenda */
    public void setPesoEnc(double pesoEnc) { this.pesoEnc = pesoEnc; }

    /** Método para definição de um custo de encomenda
     * @param custoEnc - Custo de encomenda */
    public void setCustoEnc(double custoEnc) {
        this.custoEnc = custoEnc;
    }

    /** Método para definição de um tempo de encomenda
     * @param tempoEnc - Tempo de encomenda*/
    public void setTempoEnc(double tempoEnc) {
        this.tempoEnc = tempoEnc;
    }

    /** Método para definição de uma encomenda
     * @param enc - TrazAqui.Model.Encomenda*/
    public void setEncomenda(List<LinhaEncomenda> enc) {
        this.encomenda = new ArrayList<>();
        for(LinhaEncomenda e : enc)
            this.encomenda.add(e.clone());
    }

    /** Metodo para definicao de certificado
     * @param certificado - tem ou nao certificado*/
    public void setCertificado(boolean certificado) { this.certificado = certificado; }

    /**
     * Retorna a data da encomenda
     * @return data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Atualiza a data da encomenda
     * @param data da encomenda
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Método que retorna as linhas de encomenda da encomenda
     * @return linhas de encomenda
     */
    public Map<String,InterLinhaEncomenda> getLinhasEncomenda() {
        TreeMap<String,InterLinhaEncomenda> t = new TreeMap<>();

        for(InterLinhaEncomenda l : this.encomenda) {
            t.put(l.getCodProd(),l);
        }

        return t;
    }


    /**
     * Retorna o estado atual da encomenda.
     * @return estado da encomenda
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Atualiza o estado da encomenda, para o estado recebido como argumento
     * @param estado a atualizar
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Adiciona uma linha de encomenda à encomenda em questão
     * @param e linha de encomenda
     */
    public void adicionaLinhaEncomenda(InterLinhaEncomenda e) {
        this.encomenda.add(e);
    }

    /*-------------------------- clone, equals, toString --------------------------*/

    /** Metodo de copia de uma TrazAqui.Model.Encomenda
     * @return TrazAqui.Model.Encomenda - encomenda clonada*/
    public Encomenda clone() {
        return new Encomenda(this);
    }

    /** Metodo de comparacao entre duas Encomendas
     * @param o - encomenda de comparacao
     * @return boolean - verdade ou falso */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encomenda encomenda1 = (Encomenda) o;
        return Double.compare(encomenda1.pesoEnc, pesoEnc) == 0 &&
                Double.compare(encomenda1.custoEnc, custoEnc) == 0 &&
                Double.compare(encomenda1.tempoEnc, tempoEnc) == 0 &&
                certificado == encomenda1.certificado &&
                estado == encomenda1.estado &&
                Objects.equals(codEnc, encomenda1.codEnc) &&
                Objects.equals(codUser, encomenda1.codUser) &&
                Objects.equals(codLoja, encomenda1.codLoja) &&
                Objects.equals(encomenda, encomenda1.encomenda) &&
                Objects.equals(data, encomenda1.data);
    }

    /** Metodo para retorno de uma mensagem de uma TrazAqui.Model.Encomenda.
     * @return String - TrazAqui.Model.Encomenda + (respetivos parametros)*/
    public String toString() {
        return "Encomenda{" +
                "codEnc='" + codEnc + '\'' +
                ", codUser='" + codUser + '\'' +
                ", codLoja='" + codLoja + '\'' +
                ", pesoEnc=" + pesoEnc +
                ", custoEnc=" + custoEnc +
                ", tempoEnc=" + tempoEnc +
                ", encomenda=" + encomenda +
                ", certificado=" + certificado +
                ", estado=" + estado +
                ", data=" + data +
                '}';
    }
}