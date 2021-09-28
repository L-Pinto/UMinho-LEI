package TrazAqui.Model;

import java.time.LocalDate;
import java.util.*;

public class TransportadoraRota extends Transportadora {
    /**
     * Variaveis de instancia para TransportadoraRota
     */
    private int capacidade;
    private Map<Double, InterEncomenda> rota;

    /** CONSTRUTORES*/

    /**
     * Construtor Vazio
     */
    public TransportadoraRota() {
        super();
        this.capacidade = 0;
        this.rota = new LinkedHashMap<>();
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
     * @param capacidade capacidade de transporte
     * @param rota rota de transporte das encomendas
     */
    public TransportadoraRota(String codTransportadora, String nome, InterGPS gps, String nif, double raio, boolean certificado, double preco, Map<String, InterEncomenda> encomendas, Map<String, Integer> classificacao, int capacidade, Map<Double, InterEncomenda> rota,String email, String password,double velocidade,boolean livre) {
        super(codTransportadora, nome, gps, nif, raio, certificado, preco, encomendas, classificacao,email,password,velocidade,certificado,livre);
        this.capacidade = capacidade;
        setRota(rota);
    }

    /**
     * Construtor cópia
     */
    public TransportadoraRota(TransportadoraRota t)
    {
        super(t);
        this.capacidade = t.capacidade;
        setRota(t.rota);
    }

    /** SETS & GETS*/

    /**
     * Metodo que retorna a capacidade de transporte da trasnportadora
     * @return capacidade
     */
    public int getCapacidade()
    {
        return capacidade;
    }

    /**
     * Metodo que atualiza a capacidade de transporte da trasnportadora
     * @param capacidade para ser atualizada
     */
    public void setCapacidade(int capacidade)
    {
        this.capacidade = capacidade;
    }

    /**
     * Metodo que retorna a rota das encomendas que a transportadora transporte
     * @return capacidade
     */
    public Map<Double, InterEncomenda> getRota()
    {
        Map<Double, InterEncomenda> aux = new LinkedHashMap<>();

        for(Map.Entry<Double, InterEncomenda> par: this.rota.entrySet())
        {
            aux.put(par.getKey(), par.getValue().clone());
        }
        return aux;
    }

    /**
     * Método que atualiza o estado de uma dada encomenda numa transportadora rota
     * @param codEnc código da encomenda
     */
    public void encRotaPronta(String codEnc) {

        for(Map.Entry<Double,InterEncomenda> e : this.rota.entrySet()) {
            if (e.getValue().getCodEnc().equals(codEnc)) e.getValue().setEstado(1);
        }

    }

    /**
     * Método que verifica se existe uma encomenda com um dado código de encomenda na rota
     * @param codEnc código da encomenda a verificar
     * @return true caso exista, false caso contrário
     */
    public boolean verificaEnc(String codEnc) {
        boolean res = false;

        for(Map.Entry<Double,InterEncomenda> e : this.rota.entrySet()) {
            if (e.getValue().getCodEnc().equals(codEnc)) {
                res = true;
                break;
            }
        }

        return res;
    }

    /**
     * Método que remove a encomenda dada como argumento da rota
     * @param enc código de encomenda a remover
     */
    public void removeEnc(String enc)
    {
        Double rm = 0.0;
        InterEncomenda ex = new Encomenda();
        for(Map.Entry<Double,InterEncomenda> e: this.rota.entrySet())
        {
            if(e.getValue().getCodEnc().equals(enc)) {rm = e.getKey(); ex= e.getValue();}
        }
        this.rota.remove(rm);
        ex.setEstado(2);
        super.getEncomendas().put(ex.getCodEnc(),ex);
    }

    /**
     * Método que entrega as encomendas de uma rota
     * @return lista com as encomendas que foram entregues
     */
    public List<InterEncomenda> entregaEncRota(){
        InterEncomenda enc = null;
        List<String> remover = new ArrayList<>();
        List<InterEncomenda> res = new ArrayList<>();

        for(Map.Entry<Double,InterEncomenda> e : this.rota.entrySet()) {
            if (e.getValue().getEstado() == 1) {
                e.getValue().setEstado(2);
                enc = e.getValue();
                adicionaEncomenda(e.getValue());
                remover.add(e.getValue().getCodEnc());

                res.add(e.getValue().clone());
            }
        }

        for(String s: remover) {
            removeEnc(s);
        }

        return res;
    }

    /**
     * Metodo que atualiza a rota das encomendas que a transportadora transporte
     * @param rota para ser atualizada
     */
    public void setRota(Map<Double, InterEncomenda> rota)
    {
        this.rota = new LinkedHashMap<>();
        for(Map.Entry<Double, InterEncomenda> par: rota.entrySet())
        {
            this.rota.put(par.getKey(), par.getValue().clone());
        }
    }

    /**
     * Método que confirma a capacidade da transportadora
     * @return true se a transportadora estiver cheia, false caso contrário
     */
    public boolean confirmaCapacidade() { //true se a transportadora estiver cheia
        return this.rota.size() >= capacidade;
    }

    /**
     * Método que adiciona uma encomenda à rota
     * @param e encomenda a adicionar
     */
    public void adicionaEncRota(InterEncomenda e) {
        this.rota.put(e.getTempoEnc(),e);
    }

    /**
     * Método que calcula o tempo total da rota
     * @return tempo
     */
    public double getTempoTotal() {
        double count = 0;

        for(Double tempo: rota.keySet())
        {
            count += tempo;
        }
        return count;
    }

    /**
     * Método que calcula a faturação de uma transportadora rota entre 2 datas
     * @param d1 limite inferior
     * @param d2 limite sueprior
     * @return faturação
     */
    public double getFaturacaoRota(LocalDate d1, LocalDate d2) {
        double res = 0;

        for(InterEncomenda e : this.rota.values()) {
            if (e.getData().isAfter(d1) && e.getData().isBefore(d2)) res += e.getCustoEnc();
        }

        return res + getFaturacao(d1,d2);
    }

    /**
     * Método que calcula os kms percorridos por uma transportadora rota
     * @return kms percorridos
     */
    public double kmsPercorridos(){
        double res = 0.0;
        for (InterEncomenda t :rota.values()){

            double kmEnc = (t.getTempoEnc()/60)*this.getVelocidade();
            res += kmEnc;
        }
        return res;
    }

    /** OUTROS METODOS*/

    /**
     * Metodo que determina se dois objetos da classe TransportadoraRota são iguais
     * @param o objeto a comparar
     * @return boolean (igual ou não)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransportadoraRota that = (TransportadoraRota) o;
        return capacidade == that.capacidade &&
                Objects.equals(rota, that.rota);
    }

    /**
     * Método que passa a classe TransportadoraRota para uma String
     * @return String
     */
    @Override
    public String toString() {
        return "TransportadoraRota{" +
                "capacidade=" + capacidade +
                ", rota=" + rota +
                '}';
    }

    /**
     * Metodo que clona um objeto da classe TransportadoraRota
     * @return copia do TransportadoraRota
     */
    public TransportadoraRota clone()
    {
        return new TransportadoraRota(this);
    }

    /**
     * Calcula o codigo de hash.
     * @return codigo de hash.
     */
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCapacidade(), getRota());
    }

}