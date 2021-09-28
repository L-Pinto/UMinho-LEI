package TrazAqui.Model;

import java.io.Serializable;
import java.util.*;

public class Voluntario implements InterVoluntario, Serializable {
    /**
     * variáveis de instância da classe TrazAqui.Model.Voluntario.
     */
    private String codVoluntario;
    private String nomeVoluntario;
    private GPS coordenadas;
    private double raio;
    private boolean livre;
    private Map<String,InterEncomenda> encVoluntario;
    private Map<String,Integer> classificEncVol;
    private double velocidade;
    private boolean certificado;
    private String email;
    private String password;
    private boolean aceita;

    /**
     * Construtor parametrizado.
     * @param codVoluntario codigo do voluntário.
     * @param nomeVoluntario nome do voluntario.
     * @param coordenadas localização.
     * @param raio onde o voluntário se desloca.
     * @param livre refere se o voluntário está livre ou não.
     * @param encomendas map com as encomendas que foram transportadas por um voluntário.
     * @param classificEnc map com a classificação de cada encomenda.
     * @param velocidade a que se desloca o voluntario.
     * @param certificado medico.
     */
    public Voluntario(String codVoluntario, String nomeVoluntario, GPS coordenadas, double raio, boolean livre, Map<String, InterEncomenda> encomendas, Map<String, Integer> classificEnc, double velocidade, boolean certificado, String email, String password,boolean aceita) {
        this.codVoluntario = codVoluntario;
        this.nomeVoluntario = nomeVoluntario;
        this.coordenadas = coordenadas;
        this.raio = raio;
        this.livre = livre;
        setEncVoluntario(encomendas);
        setClassificEncVol(classificEnc);
        this.velocidade = velocidade;
        this.certificado = certificado;
        this.email = email;
        this.password = password;
        this.aceita = aceita;
    }

    /**
     * Construtor por cópia.
     * @param v Voluntário.
     */
    public Voluntario(Voluntario v) {
        this.codVoluntario = v.getCodVoluntario();
        this.nomeVoluntario = v.getNomeVoluntario();
        this.coordenadas = v.getCoordenadas();
        this.raio = v.getRaio();
        this.livre = v.isLivre();
        setEncVoluntario(v.getEncVoluntario());
        setClassificEncVol(v.getClassificEncVol());
        this.velocidade = v.getVelocidade();
        this.certificado = v.isCertificado();
        this.email = v.getEmail();
        this.password = v.getPassword();
        aceitaMedicamentos(v.aceitoTransporteMedicamentos());
    }

    /**
     * Construtor vazio.
     */
    public Voluntario() {
        this.codVoluntario = " ";
        this.nomeVoluntario = " ";
        this.coordenadas = new GPS();
        this.raio = 0;
        this.livre = true;
        this.encVoluntario = new TreeMap<>();
        this.classificEncVol = new TreeMap<>();
        this.velocidade = 0;
        this.certificado = false;
        this.email = " ";
        this.password = " ";
        this.aceita = false;
    }

    /**
     * Método que devolve o email de um utilizador
     * @return email do utilizador
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que atualiza o email de um utilizador
     * @param email a atualizar
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que retorna a password de um utilizador
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método que atualiza a password de um utilizador
     * @param password do utilizador
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método que atualiza o email do utilizador
     */
    public void atualizaEmail() {
        this.email = codVoluntario + "@trazaqui.com";
    }

    /**
     * Metodo que devolve o codigo de voluntario.
     * @return código de voluntário.
     */
    public String getCodVoluntario() {
        return codVoluntario;
    }

    /**
     * Método que atualiza o código de um voluntário.
     * @param codVoluntario código que vai atualizar a classe.
     */
    public void setCodVoluntario(String codVoluntario) {
        this.codVoluntario = codVoluntario;
    }

    /**
     * Retorna o nome do voluntario.
     * @return nome do voluntário.
     */
    public String getNomeVoluntario() {
        return nomeVoluntario;
    }

    /**
     * Atualiza o nome do voluntário da classe, com o nome recebido como argumento.
     * @param nomeVoluntario que vai atualizar a classe.
     */
    public void setNomeVoluntario(String nomeVoluntario) {
        this.nomeVoluntario = nomeVoluntario;
    }

    /**
     * Retorna a localização do voluntario.
     * @return TrazAqui.Model.GPS
     */
    public GPS getCoordenadas() {
        return coordenadas;
    }

    /**
     * Atualiza a localização do voluntário.
     * @param coordenadas TrazAqui.Model.GPS
     */
    public void setCoordenadas(GPS coordenadas) {
        this.coordenadas = coordenadas;
    }

    /**
     * Retorna o raio onde o voluntário
     * @return o raio.
     */
    public double getRaio() {
        return raio;
    }

    /**
     * Atualiza o raio da classe, com o raio recebido como argumento.
     * @param raio raio
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }

    /**
     * Informa se o voluntário está livre ou não
     * @return booleano
     */
    public boolean isLivre() {
        return livre;
    }

    /**
     * Atualiza o booleano da classe, para o valore recebido como argumento.
     * @param livre booleano
     */
    public void setLivre(boolean livre) {
        this.livre = livre;
    }

    /**
     * Método que retorna uma cópia das encomendas da classe Voluntário.
     * @return cópia das encomendas da classe.
     */
    public Map<String, InterEncomenda> getEncVoluntario() {
        TreeMap<String,InterEncomenda> enc = new TreeMap<>();

        for(Map.Entry<String,InterEncomenda> e : this.encVoluntario.entrySet())
            enc.put(e.getKey(),e.getValue().clone());

        return enc;
    }

    /**
     * Atualiza a encVoluntario, pelo Map recebido como argumento.
     * @param encomendas encomendas que o voluntário já transportou.
     */
    public void setEncVoluntario(Map<String, InterEncomenda> encomendas) {
        this.encVoluntario = new TreeMap<>();

        for(Map.Entry<String,InterEncomenda> e : encomendas.entrySet())
            this.encVoluntario.put(e.getKey(),e.getValue().clone());

    }

    /**
     *
     * @return cópia do map com as classificações referentes a cada encomenda.
     */
    public Map<String, Integer> getClassificEncVol() {
        TreeMap<String,Integer> enc = new TreeMap<>();

        for(Map.Entry<String,Integer> e : this.classificEncVol.entrySet())
            enc.put(e.getKey(),e.getValue());

        return enc;
    }

    /**
     * Atualiza a classificEncVol com os valores recebidos na estrutura do argumento.
     * @param classificEnc Map.
     */
    public void setClassificEncVol(Map<String, Integer> classificEnc) {
        this.classificEncVol = new TreeMap<>();

        for(Map.Entry<String,Integer> e : classificEnc.entrySet())
            this.classificEncVol.put(e.getKey(),e.getValue());

    }

    /**
     * Método que retorna a velocidade média a que o voluntário se desloca.
     * @return velocidade.
     */
    public double getVelocidade() {
        return velocidade;
    }

    /**
     * Atualiza a velocidade do voluntário na classe, pela velocidade recebida como argumento.
     * @param velocidade média do voluntário.
     */
    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    /**
     * Informa se o voluntário está certificado para transportar encomendas médicas ou não.
     * @return booleano.
     */
    public boolean isCertificado() {
        return certificado;
    }

    /**
     * Atualiza o booleano da classe, para o valor recebido como argumento.
     * @param certificado booleano
     */
    public void setCertificado(boolean certificado) {
        this.certificado = certificado;
    }


    /**
     * Método que adiciona uma encomenda ao voluntário
     * @param e encomenda a adicionar
     */
    public void addEncomenda(InterEncomenda e) {
        this.encVoluntario.put(e.getCodEnc(),e);
    }

    /**
     * Método que entrega uma encomenda
     * @return encomenda entregue ou null
     */
    public InterEncomenda entregaEnc(){
        InterEncomenda enc = null;

        for(Map.Entry<String,InterEncomenda> e : this.encVoluntario.entrySet()) {
            if (e.getValue().getEstado() == 1) {
                e.getValue().setEstado(2);
                enc = e.getValue();
            }
        }

        return enc;
    }

    /**
     * Método que atualiza uma dada encomenda
     * @param codEnc código da encomenda a atualizar
     */
    public void encPronta(String codEnc) {
        this.encVoluntario.get(codEnc).setEstado(1);
    }

    /**
     * Método que verifica as encomendas de um dado utilizador
     * @param codUser código do utilizador
     * @param res lista a preencher com os códigos de encomenda dum dado utilizador
     */
    public void verificaEncUtilizador(String codUser,List<String> res) {

        for(Map.Entry<String,InterEncomenda> e : this.encVoluntario.entrySet())
            if (e.getValue().getCodUser().equals(codUser) && !this.classificEncVol.containsKey(e.getKey())
                && e.getValue().getEstado() == 2) res.add(e.getKey());

    }

    /**
     * Método que adiciona classificação a uma dada encomenda
     * @param codEnc código da encomenda a classificar
     * @param classif classificação
     */
    public void setClassificacao(String codEnc, int classif) {
        this.classificEncVol.put(codEnc,classif);
    }

    /**
     * Método que verifica se existe uma encomenda com o código de encomenda recebido como argumento
     * @param codEnc código de encomenda a verificar
     * @return true caso exista, false caso contrário
     */
    public boolean verificaEnc(String codEnc) {
        boolean res = false;

        for(Map.Entry<String,InterEncomenda> e : this.encVoluntario.entrySet()) {
            if (e.getKey().equals(codEnc)) {
                res = true;
                break;
            }
        }

        return res;
    }

    /**
     * Método que retorna o estado de aceitação de encomendas médicas de um voluntário
     * @return estado de aceitação de encomendas médicas
     */
    public boolean aceitoTransporteMedicamentos() {
        return aceita;
    }

    /**
     * Método que altera o estado de aceitação de encomendas médicas
     * @param state estado a alterar
     */
    public void aceitaMedicamentos(boolean state) {
        aceita = state;
    }

    /**
     * Método toString da classe TrazAqui.Model.Voluntario. Transforma a classe TrazAqui.Model.Voluntario numa String.
     * @return String.
     */
    public String toString() {
        return "Voluntario{" +
                "codVoluntario='" + codVoluntario + '\'' +
                ", nomeVoluntario='" + nomeVoluntario + '\'' +
                ", coordenadas=" + coordenadas +
                ", raio=" + raio +
                ", livre=" + livre +
                ", encVoluntario=" + encVoluntario +
                ", classificEncVol=" + classificEncVol +
                ", velocidade=" + velocidade +
                ", certificado=" + certificado +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", aceita=" + aceita +
                '}';
    }

    /**
     * Método equals referente à classe TrazAqui.Model.Voluntario. Verifica se dois objetos desta classe são iguais.
     * @param o objeto da classe TrazAqui.Model.Voluntario
     * @return true ou false, caso sejam iguais ou não, respetivamente.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voluntario that = (Voluntario) o;
        return Double.compare(that.raio, raio) == 0 &&
                livre == that.livre &&
                Double.compare(that.velocidade, velocidade) == 0 &&
                certificado == that.certificado &&
                aceita == that.aceita &&
                Objects.equals(codVoluntario, that.codVoluntario) &&
                Objects.equals(nomeVoluntario, that.nomeVoluntario) &&
                Objects.equals(coordenadas, that.coordenadas) &&
                Objects.equals(encVoluntario, that.encVoluntario) &&
                Objects.equals(classificEncVol, that.classificEncVol) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    /**
     * Método que retorna uma cópia de um objeto da classe TrazAqui.Model.Voluntario.
     * @return TrazAqui.Model.Voluntario.
     */
    public Voluntario clone() {
        return new Voluntario(this);
    }

    /**
     * Calcula o código de hash.
     * @return codigo de hash.
     */
    public int hashCode() {
        return Objects.hash(codVoluntario, nomeVoluntario, coordenadas, raio, livre, encVoluntario, classificEncVol, velocidade, certificado, email, password, aceita);
    }
}
