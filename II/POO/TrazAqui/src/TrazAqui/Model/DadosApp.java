package TrazAqui.Model;

import TrazAqui.InterAppModel;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DadosApp implements InterAppModel , Serializable {
    private Map<String, InterUtilizador>  utilizadores;
    private Map<String, InterVoluntario>  voluntarios;
    private Map<String, InterTransportadora>  transportadoras;
    private Map<String, InterLoja>  lojas;
    private Map<String, InterEncomenda>  encomendas;
    private String log;

    /**
     * Construtor vazio da classe DadosApp
     */
    public DadosApp() {
        this.utilizadores = new TreeMap<>();
        this.voluntarios = new TreeMap<>();;
        this.transportadoras = new TreeMap<>();;
        this.lojas = new TreeMap<>();;
        this.encomendas = new TreeMap<>();;
        this.log = " ";
    }

    /**
     * Método que retorna quem está no login neste momento
     * @return login atual
     */
    public String getLog() {
        return log;
    }

    /**
     * Atualiza o login
     * @param log login para atualizar
     */
    public void setLog(String log) {
        this.log = log;
    }

    /**
     * Método que adiciona um utilizador
     * @param u utilizador a adicionar
     */
    public void addUtilizador(InterUtilizador u) {
        this.utilizadores.put(u.getCodUtilizador(),u.clone());
    }

    /**
     * Método que adiciona um voluntário
     * @param v voluntário a adicionar
     */
    public void addVoluntario(InterVoluntario v) {
        this.voluntarios.put(v.getCodVoluntario(),v.clone());
    }

    /**
     * Método que adiciona uma transportadora
     * @param t transportadora a adicionar
     */
    public void addTransportadora(InterTransportadora t) {
        this.transportadoras.put(t.getCodTransportadora(),t.clone());
    }

    /**
     * Método que adiciona uma loja
     * @param l loja a adicionar
     */
    public void addLoja(InterLoja l) {
        this.lojas.put(l.getCodLoja(),l.clone());
    }

    /**
     * Método que adiciona uma encomenda
     * @param e encomenda a adicionar
     */
    public void addEncomenda(InterEncomenda e) {
        this.encomendas.put(e.getCodEnc(),e.clone());
    }

    /**
     * Método que adiciona uma encomenda a um dado utilizador
     * @param campos array de String com todos os parametros duma linha
     * @param e encomenda
     */
    public void atualizaEncUtilizador(String [] campos,InterEncomenda e) {
        if (this.utilizadores.containsKey(campos[1])) this.utilizadores.get(campos[1]).addEncomenda(e);
    }

    /**
     * Coloca os produtos duma encomenda efetuada numa loja, na própria loja
     * @param e encomenda
     */
    public void atualizaProdLoja(InterEncomenda e) {
        if (this.lojas.containsKey(e.getCodLoja())) this.lojas.get(e.getCodLoja()).atualizaProdutosEnc(e.clone());
    }

    /**
     * Método que verifica se o utilizador que fez o login está registado.
     * @return true caso se encontre registado, false caso contrário
     */
    public boolean verificaEmail() {
        boolean res = false;

        String log = this.log.substring(0,1);

        switch(log) {
            case "u":
                res = this.utilizadores.containsKey(this.log);
                break;
            case "v":
                res = this.voluntarios.containsKey(this.log);
                break;
            case "t":
                res = this.transportadoras.containsKey(this.log);
                break;
            case "l":
                res = this.lojas.containsKey(this.log);
                break;
            default:
                break;
        }

        return res;
    }

    /**
     * Método que gera o código de utilizador
     * @return código de utilizador
     */
    public String gerarCodigoUtilizador()
    {
        String codUtilizador;
        Random rad = new Random();
        do{
            int cod = rad.nextInt(100);
            codUtilizador = "u"+cod;
        }while(utilizadores.containsKey(codUtilizador));

        return codUtilizador;
    }

    /**
     * Método que gera o código de voluntário
     * @return código de voluntário
     */
    public String gerarCodigoVoluntario()
    {
        String codVoluntario;
        Random rad = new Random();
        do{
            int cod = rad.nextInt(100);
            codVoluntario = "v"+cod;
        }while(voluntarios.containsKey(codVoluntario));

        return codVoluntario;
    }

    /**
     * Método que gera o código da transportadora
     * @return código da transportadora
     */
    public String gerarCodigoTransportadora()
    {
        String codt;
        Random rad = new Random();
        do{
            int cod = rad.nextInt(100);
            codt = "t"+cod;
        }while(transportadoras.containsKey(codt));

        return codt;

    }

    /**
     * Método que gera o código da loja
     * @return código da loja
     */
    public String gerarCodigoLoja()
    {
        String codl;
        Random rad = new Random();
        do{
            int cod = rad.nextInt(100);
            codl = "l"+cod;
        }while(transportadoras.containsKey(codl));

        return codl;
    }

    /**
     * Método que gera o código da encomenda
     * @return código da encomenda
     */
    public String gerarCodigoEncomenda()
    {
        String codl;
        Random rad = new Random();
        do{
            int cod = rad.nextInt(100);
            codl = "e"+cod;
        }while(encomendas.containsKey(codl));

        return codl;
    }

    /**
     * Método que gera o peso de uma encomenda
     * @return peso da encomenda
     */
    public Double gerarPeso() {
        Random rad = new Random();
        double aux = rad.nextDouble();

        if(aux == 0) aux = 1.0;

        return aux*20;
    }

    /**
     * Método que grava o estado atual num ficheiro objeto
     * @param filename nome do ficheiro
     * @throws IOException
     */
    public void gravarObj(String filename) throws IOException {
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(filename));
        o.writeObject(this);
        o.flush();
        o.close();
    }

    /**
     * Método que lê de um ficheiro objeto e popula as estruturas
     * @param filename nome do ficheiro
     * @return Estrutura preenchida
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public DadosApp lerObj(String filename) throws IOException , ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream(new FileInputStream(filename));
        DadosApp g = (DadosApp) o.readObject();
        o.close();
        return g;
    }

    /**
     * Método que retorna as lojas do sistema.
     * @return lojas do sistema
     */
    public List<String> getLojas() {
        List<String> l = new ArrayList<>();

        for(Map.Entry<String,InterLoja> loja : this.lojas.entrySet()) {
            l.add(loja.getValue().getNome());
        }

        return l;
    }

    /**
     * Método que retorna um map com os produtos/preços de uma loja, dado o seu nome
     * @param nome da loja
     * @return produtos da loja
     */
    public Map<String,Double> getLojaNome(String nome) {

        for(Map.Entry<String,InterLoja> loja : this.lojas.entrySet()) {
            if (loja.getValue().getNome().equals(nome)) return loja.getValue().getProd();
        }

        return null;
    }

    /**
     * Métood que retorna o código de uma loja, dado o nome da mesma
     * @param nome da loja
     * @return código da loja
     */
    public String getCodLoja(String nome) {
        for(Map.Entry<String,InterLoja> loja : this.lojas.entrySet()) {
            if (loja.getValue().getNome().equals(nome)) return loja.getValue().getCodLoja();
        }
        return "";
    }

    /**
     * Método que retorna o código de um produto de uma loja, dada a descrição do mesmo
     * @param descricao do produto
     * @param codloja onde o produto foi comprado
     * @return código do produto
     */
    public String getCodProduto(String descricao, String codloja) {
        return lojas.get(codloja).getProdCodigo(descricao);
    }

    /**
     * Método que adiciona uma encomenda ao utilizador(no caso de todas as encomendas feitas)
     * @param e encomenda em questão
     * @param codUser utilizador que efetuou a encomenda
     */
    public void addEncUtilizador(InterEncomenda e, String codUser) {
        this.utilizadores.get(codUser).addEncomenda(e.clone());
    }

    /**
     * Verifica se, para uma encomenda, a log que efetuará a entrega é apta para o fazer.
     * @param enc encomenda
     * @param v voluntário/transportadora
     * @param va se o voluntario/transportadora aceitam transporte de encomendas médicas
     * @return true caso a log seja apta, false caso contrário
     */
    public boolean certificadoOKAY( boolean enc, boolean v, boolean va) {
        if(enc){

            if(v) {
                return va;
            }
            else return false;
        }
        else return true;
    }

    /**
     * Método que associa um voluntário/transportadora a uma encomenda.
     * @param enc encomenda em questão
     * @return código do voluntário ou transportadora associada
     */
    public String atribuicaoVT(InterEncomenda enc) throws NullVTException {

        InterGPS gpsU = this.utilizadores.get(enc.getCodUser()).getCoordenadas();
        InterGPS gpsL = this.lojas.get(enc.getCodLoja()).getGps();
        String res = "";
        double r = Double.POSITIVE_INFINITY;

        for(InterVoluntario v: this.voluntarios.values()) {
            InterGPS gpsV = v.getCoordenadas();
            if (v.isLivre() && gpsV.isApto(gpsL, gpsU, v.getRaio()) && v.getRaio() < r && certificadoOKAY(enc.isCertificado(),v.isCertificado(),v.aceitoTransporteMedicamentos()))  {
                r = v.getRaio();
                res = v.getCodVoluntario();
            }
        }

        if (res.equals("")) {
            for (InterTransportadora t : this.transportadoras.values()) {
                InterGPS gpsV = t.getGps();
                if (gpsV.isApto(gpsL, gpsU, t.getRaio()) && t.isLivre() && t.getRaio() < r && certificadoOKAY(enc.isCertificado(),t.isCertificado(),t.aceitoTransporteMedicamentos())) {
                    if(t instanceof TransportadoraRota) {
                        TransportadoraRota aux = (TransportadoraRota) t;
                        if (!aux.confirmaCapacidade()) {
                            r = t.getRaio();
                            res = t.getCodTransportadora();
                        }
                    } else {
                        r = t.getRaio();
                        res = t.getCodTransportadora();
                    }
                }
            }
        }

        if(res.equals("")) throw new NullVTException();

        return res;
    }

    /**
     * Método que calcula o tempo que demora a entregar uma encomenda.
     * @param vol codigo de voluntário/transportadora selecionada.
     * @param enc encomenda em causa.
     * @return o tempo que demora a entregar uma encomenda.
     */
    public double atribuiTempo(String vol, InterEncomenda enc) {
        double t = 0;
        InterGPS gpsL = this.lojas.get(enc.getCodLoja()).getGps();
        InterGPS gpsU = this.utilizadores.get(enc.getCodUser()).getCoordenadas();
        double tempo = this.lojas.get(enc.getCodLoja()).getTempo();
        double distLU = gpsL.dist2Pontos(gpsU);

        if(vol.contains("v")) {
            double velocidade = this.voluntarios.get(vol).getVelocidade();

            InterGPS gpsV = this.voluntarios.get(vol).getCoordenadas();

            double distVL = gpsV.dist2Pontos(gpsL);

            t = ((distLU + distVL) / velocidade) * 60 + tempo;
        } else {
            if (this.transportadoras.get(vol) instanceof TransportadoraRota) {
                TransportadoraRota taux = (TransportadoraRota) this.transportadoras.get(vol);
                t += taux.getTempoTotal();
            }

            double vT = this.transportadoras.get(vol).getVelocidade();

            InterGPS gpsT = this.transportadoras.get(vol).getGps();

            double distTL = gpsT.dist2Pontos(gpsL);

            t += ((distLU + distTL) / vT) * 60 + tempo;
        }

        enc.setTempoEnc(t);

        return t;
    }

    /**
     * Método que calcula a taxa de transporte de uma encomenda por uma transportadora.
     * @param codT codigo da transportadora.
     * @param e encomena em causa.
     * @return valor da taxa de transporte.
     */
    public double precoPorKm(String codT, InterEncomenda e) {

        double taxa = this.transportadoras.get(codT).getPreco();

        InterGPS gpsU = this.utilizadores.get(e.getCodUser()).getCoordenadas();
        InterGPS gpsT = this.transportadoras.get(codT).getGps();
        InterGPS gpsL = this.lojas.get(e.getCodLoja()).getGps();

        double distancia1 = gpsT.dist2Pontos(gpsL);
        double distancia = gpsU.dist2Pontos(gpsL);

        double taxaPeso = 1 + e.getPesoEnc()/40;

        return taxa*(distancia+distancia1)*taxaPeso;
    }

    /**
     * Altera o estado do voluntario para o recebido como argumento.
     * @param codV codigo do voluntario
     * @param b true se o voluntario está livre, false caso contrário
     */
    public void setVoluntarioLivre(String codV,boolean b) {
        this.voluntarios.get(codV).setLivre(b);
    }

    /**
     * Altera o estado da transportadora para o recebido como argumento.
     * @param codT codigo da transportadora
     * @param b true se a transportadora está livre, false caso contrário
     */
    public void setTranspLivre(String codT,boolean b) {
        this.transportadoras.get(codT).setLivre(b);
    }

    /**
     * Devolve o estado do voluntário.
     * @return true - livre; false - ocupado
     */
    public boolean getEstadoVol() {
        return this.voluntarios.get(log).isLivre();
    }

    /**
     * Devolve o estado da transportadora.
     * @return true - livre; false - ocupado
     */
    public boolean getEstadoTransp() {
        return this.transportadoras.get(log).isLivre();
    }

    /**
     * Método que adiciona uma encomenda à transportadora recebida como argumento.
     * @param e encomenda em questão
     * @param codT transportadora que vai efetuar o transporte da encomenda.
     */
    public void addEncomendaTransp(InterEncomenda e, String codT){
        if(this.transportadoras.get(codT) instanceof TransportadoraRota) {
            TransportadoraRota t = (TransportadoraRota) this.transportadoras.get(codT);
            t.adicionaEncRota(e.clone());
        } else {
            this.transportadoras.get(codT).adicionaEncomenda(e.clone());
        }
    }

    /**
     * Método que adiciona uma encomenda a um voluntário
     * @param e encomenda em questão
     * @param codVol código de voluntário em questão
     */
    public void addEncomendaVol(InterEncomenda e, String codVol){
        this.voluntarios.get(codVol).addEncomenda(e.clone());
    }

    /**
     * Método que adiciona uma encomenda à loja correspondente.
     * @param e encomenda em questão
     */
    public void addEncomendaLoja(InterEncomenda e) {
        this.lojas.get(e.getCodLoja()).adicionaEncomenda(e.clone());
    }

    /**
     * Método que adiciona uma encomenda
     * @param enc encomenda a adicionar
     * @param codVT codigo do voluntario/transportadora a entregar a encomena
     */
    public void adicionaEncomenda(InterEncomenda enc, String codVT,double taxa) {
        addEncomenda(enc);
        enc.setEstado(0);
        addEncomendaLoja(enc);
        addEncUtilizador(enc,log);

        if(codVT.contains("v")) {
            addEncomendaVol(enc,codVT);
        } else {
            enc.setCustoEnc(taxa);
            addEncomendaTransp(enc,codVT);
        }
    }

    /**
     * Método que elimina as lojas que não tem produtos.
     */
    public void eliminaSemProdutos() {
        List<String> eliminadas = new ArrayList<>();
        for(InterLoja l: this.lojas.values())
        {
            if(l.getProdutos().size() == 0) eliminadas.add(l.getCodLoja());
        }

        for(String s: eliminadas)
        {
            this.lojas.remove(s);
        }
    }

    /**
     * Método que retorna as encomendas não entregues de uma loja.
     * @return lista com os códigos das encomendas não entregues
     */
    public List<String> getEncLojaNE() {
        return this.lojas.get(log).getEncLojaNE();
    }

    /**
     * Entrega as encomendas ao utilizador, por parte do voluntário ou da transportadora
     * @return 1 caso tenha dado bem, 0 caso tenha dado errado
     */
    public int buscarEncVT() {
        int r = 0;
        InterEncomenda e = null;
        List<InterEncomenda> rota;

        if (log.contains("v")) e = this.voluntarios.get(log).entregaEnc();
        else if(log.contains("t")) {
            if (this.transportadoras.get(log) instanceof TransportadoraRota){
               TransportadoraRota t = (TransportadoraRota) this.transportadoras.get(log);
               rota = t.entregaEncRota();

               for(InterEncomenda encs : rota) {
                   this.lojas.get(encs.getCodLoja()).entregaEnc(encs.getCodEnc());
                   this.utilizadores.get(encs.getCodUser()).addEncomenda(encs.clone());
                   this.encomendas.put(encs.getCodEnc(),encs.clone());
               }

               if (rota.size() == 0) r = 0; else r = 1;

            } else {
                e = this.transportadoras.get(log).entregaEnc();
            }
        }

        if(e != null) {
            this.lojas.get(e.getCodLoja()).entregaEnc(e.getCodEnc());
            this.utilizadores.get(e.getCodUser()).addEncomenda(e.clone());
            this.encomendas.put(e.getCodEnc(),e.clone());
            r = 1;
        }

        return r;
    }

    /**
     * Método que coloca as encomendas duma loja prontas (altera o estado da encomenda nas diferentes entidades)
     * @param codEnc código da encomenda
     * @param codUser código de utilizador
     */
    public void encomendaPronta(String codEnc,String codUser) {

        this.lojas.get(log).encPronta(codEnc);

        for (Map.Entry<String, InterVoluntario> v : this.voluntarios.entrySet()) {
            if (v.getValue().getEncVoluntario().containsKey(codEnc)) v.getValue().encPronta(codEnc);
        }

        for (Map.Entry<String, InterTransportadora> v : this.transportadoras.entrySet()) {
            if(v.getValue() instanceof TransportadoraRota) {
                TransportadoraRota t = (TransportadoraRota) v.getValue();
                if (t.verificaEnc(codEnc)) t.encRotaPronta(codEnc);
            } else if (v.getValue().getEncomendas().containsKey(codEnc)) v.getValue().encPronta(codEnc);
        }

        this.utilizadores.get(codUser).atualizaEstado(codEnc);

    }

    /**
     * Retorna uma encomenda feita numa loja
     * @param codLoja código da loja
     * @param enc código da encomenda
     * @return encomenda
     */
    public InterEncomenda getEncLoja(String codLoja, String enc) {
        return this.lojas.get(codLoja).getEncomendasLoja().get(enc);
    }

    /**
     * Retorna as encomendas de um dado utilizador
     * @return códigos de encomenda
     */
    public List<String> getEncUtilizador(){
        List<String> res = new ArrayList<>();

        for (Map.Entry<String, InterVoluntario> v : this.voluntarios.entrySet()) {
            v.getValue().verificaEncUtilizador(log,res);
        }

        for (Map.Entry<String, InterTransportadora> v : this.transportadoras.entrySet()) {
            v.getValue().verificaEncUtilizador(log,res);
        }

        return res;
    }

    /**
     * Método que atualiza o voluntario/transportadora correspondente à encomenda
     * @param enc código da encomenda classificada
     * @param classif classificação da encomenda
     */
    public void setClassifEnc(String enc, int classif) {
        for (Map.Entry<String, InterVoluntario> v : this.voluntarios.entrySet()) {
            if (v.getValue().verificaEnc(enc)) v.getValue().setClassificacao(enc,classif);
        }

        for (Map.Entry<String, InterTransportadora> v : this.transportadoras.entrySet()) {
            if (v.getValue().verificaEnc(enc)) v.getValue().setClassificacao(enc,classif);
        }
    }

    /**
     * Método que retorna um mapeamento com a encomenda e o estado dessa encomenda
     * @param cod código do utilizador
     * @return mapeamento com a encomenda e o estado da mesma
     */
    public Map<String,String> getInfoEncomenda(String cod) {
        Map<String, String> aux = new TreeMap<>();
        for (InterEncomenda e : this.utilizadores.get(cod).getEncUtilizador().values()) {
            switch (e.getEstado()) {
                case 1: {
                    aux.put(e.getCodEnc(), "A Caminho");
                    break;
                }
                case 0: {
                    aux.put(e.getCodEnc(), "Em Loja");
                    break;
                }
            }
        }
        return aux;
    }

    /**
     * Método que calcula a faturação, para uma transportadora, entre duas datas
     * @param d1 data inferior
     * @param d2 data inferior
     * @return faturação entre as duas datas
     */
    public double getFaturacaoData(LocalDate d1, LocalDate d2) {
        double res = 0;

        if(this.transportadoras.get(log) instanceof TransportadoraRota) {
            TransportadoraRota aux = (TransportadoraRota) this.transportadoras.get(log);
            res = aux.getFaturacaoRota(d1,d2);
        } else res = this.transportadoras.get(log).getFaturacao(d1,d2);

        return res;
    }

    /**
     * Método que retorna as encomendas de uma dada entidade e a respetiva data
     * @param cod codigo de voluntário/transportadora
     * @return map com o código de encomenda e a respetiva data
     */
    public Map<String,LocalDate> getEncUtiVol(String cod)
    {
        Map<String,LocalDate> aux = new TreeMap<>();

        if(this.voluntarios.containsKey(cod) && cod.contains("v"))
        {
            for(InterEncomenda e: this.voluntarios.get(cod).getEncVoluntario().values())
            {
                if(log.contains("u"))
                {
                    if(e.getCodUser().equals(log)&&e.getEstado()==2) aux.put(e.getCodEnc(),e.getData());
                }
                else if(e.getCodLoja().equals(log)&&e.getEstado()==2) aux.put(e.getCodEnc(),e.getData());
            }
        } else if (this.transportadoras.containsKey(cod)) {
            for(InterEncomenda e: this.transportadoras.get(cod).getEncomendas().values())
            {
                if(log.contains("u"))
                {
                    if(e.getCodUser().equals(log)&&e.getEstado()==2) aux.put(e.getCodEnc(),e.getData());
                }
                else if(e.getCodLoja().equals(log)&&e.getEstado()==2) aux.put(e.getCodEnc(),e.getData());
            }
        }
        return aux;
    }

    /**
     * Método que retorna as encomendas com a respetiva data, num dado período de tempo
     * @param data1 limite inferior
     * @param data2 limite superior
     * @return map com a correspondência entre o código da encomenda e a data em que foi efetuada
     */
    public Map<String,LocalDate> getEncomendasPeriodo(LocalDate data1, LocalDate data2)
    {
        Map<String,LocalDate> aux = new TreeMap<>();
        Map<String,InterEncomenda> enc = new TreeMap<>();

        if(log.contains("u")) enc = this.utilizadores.get(log).getEncUtilizador();
        else if(log.contains("l")) enc = this.lojas.get(log).getEncomendasLoja();
        else if (log.contains("v")) enc = this.voluntarios.get(log).getEncVoluntario();
        else if (log.contains("t")) enc = this.transportadoras.get(log).getEncomendas();

        for(InterEncomenda l: enc.values())
        {
            if(data1.isBefore(l.getData())&& l.getData().isBefore(data2) && l.getEstado()==2)
            {
                aux.put(l.getCodEnc(),l.getData());
            }
        }
        return aux;
    }

    /**
     * Método que retorna as encomendas referentes à entidade que está em login
     * @return map com a correspondência entre os códigos de encomenda e a respetiva data
     */
    public Map<String,LocalDate> getEncomendas()
    {
        Map<String,LocalDate> aux = new TreeMap<>();
        Map<String,InterEncomenda> enc = new TreeMap<>();

        if(log.contains("u")) enc = this.utilizadores.get(log).getEncUtilizador();
        else if(log.contains("l")) enc = this.lojas.get(log).getEncomendasLoja();
        else if (log.contains("v")) enc = this.voluntarios.get(log).getEncVoluntario();
        else if (log.contains("t")) enc = this.transportadoras.get(log).getEncomendas();

        for(InterEncomenda l: enc.values())
        {
            if(l.getEstado()==2) aux.put(l.getCodEnc(),l.getData());
        }
        return aux;
    }

    /**
     * Método que calcula o top 10 de utilizadores que mais utilizam o sistema
     * @return map com a correspondência entre o código de utilizador e o respetivo número de encomendas
     */
    public Map<Integer, String> getTop10Utilizadores() {
        Map<Integer, String> res = new TreeMap<>(new Comparator<Integer>() {
            public int compare(Integer integer, Integer t1) {
                return (t1-integer == 0)? 1 : t1-integer;
            }
        }) ;
        for (Map.Entry<String, InterUtilizador> aux : this.utilizadores.entrySet()){
            String utilizador = aux.getKey();
            int numeroEncs = aux.getValue().getEncUtilizador().size();
            res.put(numeroEncs,utilizador);
        }
        return res;
    }

    /**
     * Método que calcula o top 10 de transportadoras que mais utilizam o sistema
     * @return map com a correspondência entre o código da transportadora e o respetivo nr de kms
     */
    public Map<Double, String> getTop10Transportadoras(){

        Map<Double, String> res = new TreeMap<>(new Comparator<Double>() {
            public int compare(Double aDouble, Double t1) {
                int res;
                if (t1 < aDouble) res = -1;
                else res = 1;
                return res;
            }
        });

        for (Map.Entry<String, InterTransportadora> aux : this.transportadoras.entrySet()){
            String transportadora = aux.getKey();
            double kmsPercorridos = 0.0;
            InterTransportadora t = aux.getValue();

            if ( t instanceof TransportadoraRota) kmsPercorridos += ((TransportadoraRota) t).kmsPercorridos();
            kmsPercorridos += t.contaKms();

            res.put(kmsPercorridos,transportadora);
        }
        return res;
    }

    /**
     * Método que retorna a classificação de uma dada encomenda, para um voluntário/transportadora
     * @return map com o código de encomenda e a respetiva classificação
     */
    public Map<String, Integer> getClassificacao() {
        if (log.contains("v")) {
            return this.voluntarios.get(log).getClassificEncVol();
        } else {
            return this.transportadoras.get(log).getClassificacao();
        }
    }

    /**
     * Método que atribui uma encomenda a um voluntário ou transportadora, na classe parse
     * @param e encomenda em causa
     * @param ls lista de entidades de transporte
     */
    public void atualizaEncVT(InterEncomenda e, List<String> ls)
    {
        Random ran = new Random();
        int i = ran.nextInt(ls.size());
        String cod = ls.get(i);
        if(cod.contains("v")) this.voluntarios.get(cod).addEncomenda(e.clone());
        else this.transportadoras.get(cod).addEncomenda(e.clone());
    }


    /**
     * Método que retorna o certificado médico da entidade que está no login
     * @return certificado
     */
    public boolean getCertificadoMedico()
    {
        if(log.contains("v"))
        {
            return this.voluntarios.get(log).isCertificado();
        }
        else return this.transportadoras.get(log).isCertificado();
    }

    /**
     * Método que altera o estado de aceitação de medicamentos
     * @return novo estado
     */
    public boolean setAceitoMedicamento() {
        if (log.contains("v")) {
            InterVoluntario v = this.voluntarios.get(log);
            v.aceitaMedicamentos(!v.aceitoTransporteMedicamentos());
            return v.aceitoTransporteMedicamentos();
        } else {
            InterTransportadora v = this.transportadoras.get(log);
            v.aceitaMedicamentos(!v.aceitoTransporteMedicamentos());
            return v.aceitoTransporteMedicamentos();
        }
    }

    /**
     * Método que retorna as encomendas que estão em processamento
     * @return lista de encomendas em processamento
     */
    public List<String> getEncomendasProcessVol()
    {
        List<String> aux = new ArrayList<>();

        if(log.contains("v")) {
            for (InterEncomenda l : this.voluntarios.get(log).getEncVoluntario().values()) {
                if (l.getEstado() == 1) aux.add(l.getCodEnc());
            }
        }
        else
        {
            InterTransportadora t = this.transportadoras.get(log);
            if(t instanceof TransportadoraRota)
            {
                TransportadoraRota taux = (TransportadoraRota)t;
                for (InterEncomenda l : taux.getRota().values()) {
                    if (l.getEstado() == 1) aux.add(l.getCodEnc());
                }
            }
            else {
                for (InterEncomenda l : t.getEncomendas().values()) {
                    if (l.getEstado() == 1) aux.add(l.getCodEnc());
                }
            }
        }
        return aux;
    }

    /**
     * Método que retorna as encomendas que ainda nao foram entregues
     * @return lista de encomendas em processamento
     */
    public List<String> getEncomendasVT()
    {
        List<String> aux = new ArrayList<>();

        if(log.contains("v")) {
            for (InterEncomenda l : this.voluntarios.get(log).getEncVoluntario().values()) {
                if (l.getEstado() != 2) aux.add(l.getCodEnc());
            }
        }
        else
        {
            InterTransportadora t = this.transportadoras.get(log);
            if(t instanceof TransportadoraRota)
            {
                TransportadoraRota taux = (TransportadoraRota)t;
                for (InterEncomenda l : taux.getRota().values()) {
                    if (l.getEstado() != 2) aux.add(l.getCodEnc());
                }
            }
            else {
                for (InterEncomenda l : t.getEncomendas().values()) {
                    if (l.getEstado() != 2) aux.add(l.getCodEnc());
                }
            }
        }
        return aux;
    }


    /**
    * Método toString da classe DadosApp
    * @return String da classe DadosApp
    */
    public String toString() {
        return "DadosApp{" +
                "utilizadores=" + utilizadores +
                ", voluntarios=" + voluntarios +
                ", transportadoras=" + transportadoras +
                ", lojas=" + lojas +
                ", encomendas=" + encomendas +
                ", log='" + log + '\'' +
                '}';
    }
}
