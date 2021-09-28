package TrazAqui.Controller;

import TrazAqui.Input;
import TrazAqui.InterAppController;
import TrazAqui.InterAppModel;
import TrazAqui.InterAppView;
import TrazAqui.Model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class TrazAquiController implements InterAppController {
    private InterAppView v;
    private InterAppModel d;

    /**
     * Atualiza a variável de instância desta classe
     * @param dados variável que irá atualizar
     */
    public void setDadosApp(InterAppModel dados) {
        this.d = dados;
    }

    /**
     * Atualiza a variável de instância desta class
     * @param v variável que irá atualizar
     */
    public void setView(InterAppView v) {
        this.v = v;
    }

    /**
     * Menu principal do utilizador
     */
    public void menu() {
        int opcao;

        while((opcao = Input.lerInt()) != 0) {
            switch (opcao) {
                case 1:
                    login();
                    break;
                case 2:
                    registo();
                    break;
                case 3:
                    startLerGravar();
                    break;
                case 4:
                    Map<Integer, String> top10Ut = d.getTop10Utilizadores();
                    v.showTop10Utilizadores(top10Ut);
                    break;
                case 5:
                    Map<Double, String> top10T = d.getTop10Transportadoras();
                    v.showTop10Transportadoras(top10T);
                    break;
                default:
                    v.showERROR("Insira opção válida!\n");
                    break;
            }
            v.showMenu();
            v.showPointer();
        }
    }

    /**
     * Método que grava o estado atual do programa em objeto
     */
    public void startGravar() {
        v.showMessage("Insira o nome do ficheiro a gravar: ");
        v.showPointer();
        String filename = Input.lerString();

        try {
            d.gravarObj(filename);
            v.showSucesso("FICHEIRO GUARDADO COM SUCESSO");
        } catch (IOException e) {
            v.showMessage(e.getMessage());
        }
    }

    /**
     * Método que oferece opção de ler e gravar um estado do programa/ficheiro objeto
     */
    public void startLerGravar() {
        int op;

        v.showMenuLG();
        v.showPointer();

        while ((op = Input.lerInt()) != 0) {
            String filename;
            switch (op) {
                case 1:
                    startGravar();
                    break;
                case 2:
                    v.showMessage("Insira o nome do ficheiro a ler: ");
                    v.showPointer();
                    filename = Input.lerString();

                    InterAppModel model;
                    try {
                        model = this.d.lerObj(filename);
                        this.d = new DadosApp();
                        this.d = model;
                        v.showSucesso("FICHEIRO LIDO COM SUCESSO!");
                    } catch (IOException | ClassNotFoundException e) {
                        v.showMessage( e.getMessage());
                    }

                    break;
            }

            v.showMenuLG();
            v.showPointer();

        }
    }

    /**
     * Controller para o login do utilizador
     */
    public void startUtilizador() {
        int opcao;

        while((opcao = Input.lerInt()) != 0) {
            switch(opcao) {
                case 1:
                    startGravar();
                    break;
                case 2:
                    v.showCreatEncomenda();
                    v.showPointer();

                    String med = Input.lerString();
                    med = med.toUpperCase();

                    if (!med.equals("Y") && !med.equals("N")) {
                        v.showERROR("Opção inválida!");
                        break;
                    }

                    v.showTituloL();
                    List<String> lojas = d.getLojas();
                    v.showLojas(lojas);
                    v.showPointer();
                    int l = Input.lerInt();

                    if (l <= 0 || l > lojas.size()) {
                        v.showERROR("Loja inválida!");
                        break;
                    }

                    String s = lojas.get(l-1);
                    Map<String,Double> produtos = d.getLojaNome(s);

                    InterEncomenda e = createEncomenda(produtos,s);

                    if (e != null) {
                        String codVT = "";

                        e.setCertificado(med.toUpperCase().equals("Y"));

                        try {
                            codVT = d.atribuicaoVT(e);
                        } catch (NullVTException ex) {
                            v.showERROR("Não existem entidades disponíveis para efetuar a entrega!");
                            break;
                        }

                        double tempo = d.atribuiTempo(codVT,e);

                        if (codVT.contains("t")) {
                            double taxa = d.precoPorKm(codVT,e);

                            v.showInfoEncT(codVT,e.getCustoEnc(),taxa);

                            String aceita = Input.lerString();

                            if (aceita.toUpperCase().equals("Y")) {
                                e.setCustoEnc(e.getCustoEnc()+taxa);

                                v.showInfoEncVol(codVT, e.getCodLoja(), e.getCodUser(), e.getCodEnc(), tempo, e.getCustoEnc(),e.getPesoEnc());

                                d.adicionaEncomenda(e,codVT,taxa);
                            } else {
                                v.showERROR("Encomenda cancelada!");
                            }
                        } else {
                            v.showInfoEncVol(codVT, e.getCodLoja(), e.getCodUser(), e.getCodEnc(), tempo, e.getCustoEnc(),e.getPesoEnc());

                            d.adicionaEncomenda(e,codVT,0);

                            d.setVoluntarioLivre(codVT,false);
                        }
                    }
                    break;
                case 3:
                    startHistorico();
                    break;
                case 4:
                    List<String> enc = d.getEncUtilizador();

                    v.showMessage("Escolha uma encomenda para classificar: ");

                    if (enc.size() == 0) {
                        v.showERROR("Não há encomendas a classificar!");
                        break;
                    }

                    v.showLojas(enc);
                    v.showPointer();
                    int op = Input.lerInt();

                    if (op <= 0 || op > enc.size()) {
                        v.showERROR("Opção inválida!");
                        break;
                    }

                    v.showMessage("Insira a classificação para a encomenda " + enc.get(op-1) + " [0-5]");
                    v.showPointer();
                    int classif = Input.lerInt();

                    if(classif < 0 || classif > 5) {
                        v.showERROR("Classificação inválida!");
                        break;
                    }

                    d.setClassifEnc(enc.get(op-1),classif);

                    v.showSucesso("Encomenda classificada com sucesso!");

                    break;
                case 5:
                    Map<String,String> info = d.getInfoEncomenda(d.getLog());
                    if (info.size() > 0) v.showInfoEncUlt(info);
                    else v.showERROR("Não existem encomendas a apresentar!");
                    break;
                default:
                    break;
            }
            v.showMenuUtilizador();
            v.showPointer();
        }
    }

    /**
     * Método que cria uma encomenda
     * @param produtos disponíveis para o utilizador comprar
     * @param loja onde o utilizador está a comprar
     * @return encomenda
     */
    public InterEncomenda createEncomenda(Map<String,Double> produtos, String loja) {
        String opcao;
        InterEncomenda enc = new Encomenda();
        enc.setCodEnc(d.gerarCodigoEncomenda());
        double custoEnc = 0;
        enc.setData(LocalDate.now());

        do {
            v.showProd(produtos);
            v.menuRegistarEnc();
            v.showPointer();
            opcao = Input.lerString();

            switch (opcao) {
                case "1" :
                    v.showMessage("Insira o produto: ");
                    v.showPointer();
                    int prod = Input.lerInt();

                    if(prod <= 0 || prod > produtos.size()) {
                        v.showERROR("Produto inexistente!");
                        return null;
                    }

                    v.showMessage("Insira a quantidade do produto: ");
                    v.showPointer();
                    int qnt = Input.lerInt();

                    if(qnt <= 0) {
                        v.showERROR("Quantidade inváliada!");
                        break;
                    }

                    InterLinhaEncomenda l = new LinhaEncomenda();
                    String descricao = "";
                    double preco = 0;

                    int i = 1;
                    for(Map.Entry<String,Double> e : produtos.entrySet()) {
                        if (i == prod) {
                            descricao = e.getKey(); preco = e.getValue();
                        }
                        i++;
                    }

                    String codL = d.getCodLoja(loja);
                    String codP = d.getCodProduto(descricao,codL);

                    enc.setCodLoja(codL);
                    enc.setCodUser(d.getLog());

                    InterLinhaEncomenda le = new LinhaEncomenda(codP,descricao,qnt,preco*qnt);

                    custoEnc += preco*qnt;

                    enc.adicionaLinhaEncomenda(le);

                    break;
                case "0" : break;
                default: v.showERROR("Opção Invalida"); break;
            }
        } while (!opcao.equals("0"));

        enc.setPesoEnc(d.gerarPeso());
        enc.setCustoEnc(custoEnc);

        if(custoEnc == 0) {
            v.showERROR("Não adicionou produtos à sua encomenda! ");
            return null;
        }

        return enc;
    }

    /**
     * Controller do login do voluntário
     */
    public void startVoluntario() {
        int opcao;

        while((opcao = Input.lerInt()) != 0) {
            switch(opcao) {
                case 1:
                    startGravar();
                    break;
                case 2:
                    v.showEstadoVol(d.getEstadoVol());
                    v.showPointer();
                    int res = Input.lerInt();

                    if (d.getEncomendasVT().size() > 0) {
                        v.showERROR("Impossível alterar estado! Possui encomendas em processamento");
                        break;
                    }

                    if (res == 1) d.setVoluntarioLivre(d.getLog(),true);
                    else if (res == 2) d.setVoluntarioLivre(d.getLog(),false);
                    else v.showERROR("Opção inválida!");

                    break;
                case 3:
                    List<String> s = d.getEncomendasProcessVol();
                    v.showEncProcess(s);

                    int resultado = d.buscarEncVT();

                    if (resultado == 1) {
                        v.showSucesso("Encomenda recolhida e entregue ao utilizador com sucesso!");
                        d.setVoluntarioLivre(d.getLog(),true);
                        break;
                    }
                    else {
                        v.showERROR("Não é possível entregar encomenda.");
                    }

                    break;
                case 4:
                    startHistoricoVT();
                    break;
                case 5:
                    if(d.getCertificadoMedico())
                    {
                        v.showMessage("Aceito Transporte de Medicamentos (ATUAL): " + d.setAceitoMedicamento());
                    }
                    else v.showERROR("Metodo indisponivel - Não tem certificado Médico");

                    break;
                case 6:
                    Map<String, Integer> clas = d.getClassificacao();
                    v.showClassificacao(clas);

                    break;
                default:
                    v.showERROR("Opção inválida.");
                    break;
            }
            v.showMenuVoluntario();
            v.showPointer();
        }
    }

    /**
     * Controller do login da Transportadora
     */
    public void startTransportadora() {
        int opcao;

        while((opcao = Input.lerInt()) != 0) {
            switch(opcao) {
                case 1:
                    startGravar();
                    break;
                case 2:
                    v.showEstadoVol(d.getEstadoTransp());
                    v.showPointer();
                    int res = Input.lerInt();

                    if (d.getEncomendasVT().size() > 0) {
                        v.showERROR("Impossível alterar estado! Possui encomendas em processamento");
                        break;
                    }

                    if (res == 1) d.setTranspLivre(d.getLog(),true);
                    else if (res == 2) d.setTranspLivre(d.getLog(),false);
                    else v.showERROR("Opção inválida!");

                    break;
                case 3:
                    List<String> s = d.getEncomendasProcessVol();
                    v.showEncProcess(s);

                    int resultado = d.buscarEncVT();

                    if (resultado == 1) v.showSucesso("Encomenda(s) recolhida(s) e entregue(s) ao(s) utilizador(es) com sucesso!");
                    else v.showERROR("Não é possível entregar encomenda(s).");

                    d.setTranspLivre(d.getLog(),true);

                    break;
                case 4:
                    v.showMessage("Insira o limite inferior ");
                    v.showMessage("Dia: ");
                    v.showPointer();
                    int d1 = Input.lerInt();
                    v.showMessage("Mes: ");
                    v.showPointer();
                    int m1 = Input.lerInt();

                    v.showMessage("Insira o limite superior ");
                    v.showMessage("Dia: ");
                    v.showPointer();
                    int d2 = Input.lerInt();
                    v.showMessage("Mes: ");
                    v.showPointer();
                    int m2 = Input.lerInt();

                    if (!verificaData(m1,m2,d1,d2)) {
                        v.showERROR("Dados inseridos inválidos!");
                        break;
                    }

                    LocalDate date1 = LocalDate.of(2020,m1,d1);
                    LocalDate date2 = LocalDate.of(2020,m2,d2);

                    double fact = d.getFaturacaoData(date1,date2);

                    v.showFaturacao(fact);

                    break;
                case 5:
                    startHistoricoVT();
                    break;
                case 6:
                    if(d.getCertificadoMedico())
                    {
                        v.showMessage("Aceito Transporte de Medicamentos (ATUAL): " + d.setAceitoMedicamento());
                    }
                    else v.showERROR("Metodo indisponivel - Não tem certificado Médico");

                    break;
                case 7:
                    Map<String, Integer> clas = d.getClassificacao();
                    v.showClassificacao(clas);
                    break;
                default:
                    v.showERROR("Opção inválida!");
                    break;
            }
            v.showMenuTransportadora();
            v.showPointer();
        }
    }

    /**
     * Controller do login da loja
     */
    public void startLoja() {
        int opcao;

        while((opcao = Input.lerInt()) != 0) {
            switch(opcao) {
                case 1:
                    startGravar();
                    break;
                case 2:
                    List<String> ne;

                    do {
                        ne = d.getEncLojaNE();

                        v.showEncProcess(ne);

                        if (ne.size() == 0) {
                            v.showMessage("Não há dados a apresentar!");
                        }

                        v.showMessage("Para sair pressione [0]");
                        v.showPointer();

                        int op = Input.lerInt();

                        if (op <= 0 || op > ne.size()) break;

                        String codEnc = ne.get(op - 1);

                        InterEncomenda e = d.getEncLoja(d.getLog(), codEnc);
                        d.encomendaPronta(codEnc, e.getCodUser());

                    } while(ne.size() > 0);

                    break;
                case 3:
                    List<String> encomendas = d.getEncLojaNE();
                    int tamanhoFila = encomendas.size();
                    v.showTamanhoFilaEspera(tamanhoFila);
                    break;
                case 4:
                    startHistorico();
                    break;
                default:
                    v.showERROR("Opção inválida.");
                    break;
            }
            v.showMenuLoja();
            v.showPointer();
        }
    }

    /**
     * Método que adiciona um produto a uma loja
     * @return correspondencia entre o código do produto e uma linha de encomenda
     */
    public Map<String, InterLinhaEncomenda> startProduto()
    {
        Map<String, InterLinhaEncomenda> aux = new TreeMap<>();
        String opcao = "";
        double preco;
        String nome;
        int i = 0;
        do
        {
            v.menuLerProduto();
            v.showPointer();
            opcao = Input.lerString();
            opcao = opcao.toUpperCase();
            switch (opcao)
            {
                case "1" : {
                    v.showMessage("Descricao do Produto");
                    v.showPointer();
                    nome = Input.lerString();

                    v.showMessage("Preço/Unidade");
                    v.showPointer();
                    preco = Input.lerDouble();

                    String cod = "p" + i;

                    InterLinhaEncomenda l  = new LinhaEncomenda(cod, nome, 0, preco);
                    i++;
                    aux.put(cod, l);

                    break;
                }
                case "0" : break;
                default: v.showMessage("Opção Invalida"); break;
            }
        }while (!opcao.equals("0"));

        return aux;
    }

    /**
     * Controller que verifica qual entidade efetuou o login
     */
    public void verificaLogin(){
        String entidade = d.getLog().substring(0,1);

        switch(entidade) {
            case "u":
                v.showMenuUtilizador();
                v.showPointer();
                startUtilizador();
                break;
            case "t":
                v.showMenuTransportadora();
                v.showPointer();
                startTransportadora();
                break;
            case "v":
                v.showMenuVoluntario();
                v.showPointer();
                startVoluntario();
                break;
            case "l":
                v.showMenuLoja();
                v.showPointer();
                startLoja();
                break;
            default:
                v.showERROR("Entidade inválida.");
                break;
        }

    }

    /**
     * Controller do login
     */
    public void login() {
        String email;
        String password;

        v.showMessage("E-mail:");
        v.showPointer();
        email = Input.lerString();
        v.showMessage("Password:");
        v.showPointer();
        password = Input.lerString();

        String [] cod = email.split("@");
        d.setLog(cod[0]);

        if (!password.equals("1234")) v.showERROR("Dados inválidos!\n");
        else {
            if (d.verificaEmail()) verificaLogin();
            else v.showERROR("Dados inválidos!");
        }

        d.setLog("");
    }



    /**
     * Controller para o histórico de encomendas do utilizador
     */
    public void startHistorico() {
        String op = "";

        do {
            v.showMenuHistorico();
            v.showPointer();
            op = Input.lerString();
            switch (op)  {
                case"0":
                    break;
                case"1":
                    v.showMenuPeriodo("INICIO DO PERIODO");
                    v.showPointer();
                    int mes1 = Input.lerInt();
                    v.showMessage("Insira um Dia: ");
                    v.showPointer();
                    int dia1 = Input.lerInt();

                    v.showMenuPeriodo("FIM DO PERIODO");
                    v.showPointer();
                    int mes2 = Input.lerInt();
                    v.showMessage("Insira um Dia: ");
                    v.showPointer();
                    int dia2 = Input.lerInt();

                    if(!verificaData(mes1,mes2,dia1,dia2)) break;
                    LocalDate data = LocalDate.of(2020,mes1,dia1);
                    LocalDate data2 = LocalDate.of(2020,mes2,dia2);
                    if(data.isAfter(data2)) {v.showERROR("Periodo Inválido"); break;}
                    Map<String,LocalDate> encs = d.getEncomendasPeriodo(data, data2);
                    if(encs.size()==0) {v.showERROR("Nao existe encomendas nesse periodo"); break;}
                    v.showEncPeriodo(encs);

                    break;
                case"2":
                    v.showMessage("Introduza um voluntário/transportadora: ");
                    v.showPointer();
                    String cod = Input.lerString();
                    Map<String,LocalDate> encm = d.getEncUtiVol(cod);
                    v.showEncPeriodo(encm);

                    break;
                case "3":
                    Map<String,LocalDate> encmds = d.getEncomendas();
                    v.showEncPeriodo(encmds);

                    break;
                default:
                    v.showERROR("Insira opção válida");
                    break;
            }

        }while (!op.equals("0"));
    }


    /**
     * Controller do histórico do voluntário e da transportadora.
     */
    public void startHistoricoVT()
    {
        String op = "";

        do{
            v.showMenuHistoricoVT();
            v.showPointer();
            op = Input.lerString();
            switch (op)
            {
                case"0": break;
                case"1":{
                    v.showMenuPeriodo("INICIO DO PERIODO");
                    v.showPointer();
                    int mes1 = Input.lerInt();
                    v.showMessage("Insira um Dia: ");
                    v.showPointer();
                    int dia1 = Input.lerInt();

                    v.showMenuPeriodo("FIM DO PERIODO");
                    v.showPointer();
                    int mes2 = Input.lerInt();
                    v.showMessage("Insira um Dia: ");
                    v.showPointer();
                    int dia2 = Input.lerInt();

                    if(!verificaData(mes1,mes2,dia1,dia2)) break;
                    LocalDate data = LocalDate.of(2020,mes1,dia1);
                    LocalDate data2 = LocalDate.of(2020,mes2,dia2);
                    if(data.isAfter(data2)) {v.showERROR("Periodo Inválido"); break;}
                    Map<String,LocalDate> encs = d.getEncomendasPeriodo(data, data2);
                    if(encs.size()==0) {v.showERROR("Nao existe encomendas nesse periodo"); break;}
                    v.showEncPeriodo(encs);

                    break;
                }
                case"2":
                    Map<String,LocalDate> encs = d.getEncomendas();
                    v.showEncPeriodo(encs);
                    break;
                default:
                    v.showERROR("Insira opção válida.");
                    break;
            }

        }while (!op.equals("0"));
    }


    public boolean verificaData(int mes1, int mes2, int dia1, int dia2)
    {
        boolean res = true;
        if(mes1<1 || mes1>12) { v.showERROR("1º Mes Inválido"); res = false;}
        if(mes2<1 || mes2>12) { v.showERROR("2º Mes Inválido"); res = false;}
        if(dia1<1 || dia1>31) { v.showERROR("1º Dia Inválido"); res = false;}
        if(dia2<1 || dia2>31) { v.showERROR("2º Mes Inválido"); res = false;}
        if(mes1==2 && dia1>29 || mes2==2 && dia2>29)  { v.showERROR("Data invalida"); res = false;}

        return res;

    }


    /**
     * Controller do registo
     */
    public void registo() {
        int opcao;

        v.showRegistos();
        v.showPointer();

        while((opcao = Input.lerInt()) != 0) {
            switch(opcao) {
                case 1:
                    InterUtilizador u = new Utilizador();

                    v.showMessage("Nome:");
                    v.showPointer();
                    String nome = Input.lerString();
                    u.setNomeUtilizador(nome);

                    v.showMessage("Latitude:");
                    v.showPointer();
                    double latitude = Input.lerDouble();
                    v.showMessage("Longitude:");
                    v.showPointer();
                    double longitude = Input.lerDouble();

                    GPS gps = new GPS();
                    gps.setY(latitude);
                    gps.setX(longitude);
                    u.setCoordenadas(gps);

                    u.setCodUtilizador(d.gerarCodigoUtilizador());

                    v.showMessage("Email gerado automaticamente!");
                    u.atualizaEmail();
                    v.showMessage(u.getEmail());

                    v.showMessage("Palavra passe gerada automaticamente!");
                    u.setPassword("1234");
                    v.showMessage(u.getPassword());

                    d.addUtilizador(u);

                    v.showSucesso("Dados inseridos com sucesso!\n");

                    break;
                case 2:
                    InterVoluntario vol = new Voluntario();

                    v.showMessage("Nome:");
                    v.showPointer();
                    String nomeV = Input.lerString();
                    vol.setNomeVoluntario(nomeV);

                    v.showMessage("Latitude:");
                    v.showPointer();
                    double latitudeV = Input.lerDouble();
                    v.showMessage("Longitude:");
                    v.showPointer();
                    double longitudeV = Input.lerDouble();

                    GPS gpsV = new GPS();
                    gpsV.setY(latitudeV);
                    gpsV.setX(longitudeV);
                    vol.setCoordenadas(gpsV);

                    v.showMessage("Tem certificado médico? [Y/N]");
                    v.showPointer();
                    String certf = Input.lerString();
                    vol.setCertificado(certf.toUpperCase().equals("Y"));

                    if (!certf.toUpperCase().equals("Y") && !certf.toUpperCase().equals("N")) {
                        v.showERROR("Opção inválida!");
                        break;
                    }

                    v.showMessage("Raio:");
                    v.showPointer();
                    String raioV = Input.lerString();
                    vol.setRaio(Double.parseDouble(raioV));

                    v.showMessage("Velocidade:");
                    v.showPointer();
                    String velocidade = Input.lerString();
                    vol.setVelocidade(Double.parseDouble(velocidade));

                    vol.setCodVoluntario(d.gerarCodigoVoluntario());

                    v.showMessage("Email gerado automaticamente!");
                    vol.atualizaEmail();
                    v.showMessage(vol.getEmail());

                    v.showMessage("Palavra passe gerada automaticamente!");
                    vol.setPassword("1234");
                    v.showMessage(vol.getPassword());

                    vol.setLivre(true);

                    vol.aceitaMedicamentos(vol.isCertificado());

                    d.addVoluntario(vol);

                    v.showSucesso("Dados inseridos com sucesso!\n");

                    break;
                case 3:
                    InterTransportadora t = new Transportadora();

                    v.showMessage("Nome:");
                    v.showPointer();
                    String nomeT = Input.lerString();
                    t.setNome(nomeT);

                    v.showMessage("Latitude:");
                    v.showPointer();
                    double latitudeT = Input.lerDouble();
                    v.showMessage("Longitude:");
                    v.showPointer();
                    double longitudeT = Input.lerDouble();

                    GPS gpsT = new GPS();
                    gpsT.setY(latitudeT);
                    gpsT.setX(longitudeT);
                    t.setGps(gpsT);

                    v.showMessage("NIF:");
                    v.showPointer();
                    String NIF = Input.lerString();
                    t.setNif(NIF);

                    v.showMessage("Raio:");
                    v.showPointer();
                    String raio = Input.lerString();
                    t.setRaio(Double.parseDouble(raio));

                    v.showMessage("Tem certificado médico? [Y/N]");
                    v.showPointer();
                    String cert = Input.lerString();
                    t.setCertificado(cert.toUpperCase().equals("Y"));

                    if (!cert.toUpperCase().equals("Y") && !cert.toUpperCase().equals("N")) {
                        v.showERROR("Opção inválida!");
                        break;
                    }

                    t.aceitaMedicamentos(t.isCertificado());

                    v.showMessage("Preco por km:");
                    v.showPointer();
                    String preco = Input.lerString();
                    t.setPreco(Double.parseDouble(preco));

                    String cod = d.gerarCodigoTransportadora();

                    t.setCodTransportadora(cod);

                    v.showMessage("Velocidade:");
                    v.showPointer();
                    String velocidadeT = Input.lerString();
                    t.setVelocidade(Double.parseDouble(velocidadeT));

                    v.showMessage("É transportadora rota? [Y/N] ");
                    v.showPointer();
                    String res = Input.lerString();

                    t.atualizaEmail();
                    String email = t.getEmail();

                    t.setPassword("1234");

                    t.setLivre(true);

                    if (res.toUpperCase().equals("Y")) {
                        v.showMessage("Insira a capacidade da transportadora: ");
                        v.showPointer();
                        int capacidade = Input.lerInt();

                        if (capacidade <= 1) {
                            v.showMessage("Capacidade tem que ser maior do que 1!");
                            break;
                        }

                        Map<Double, InterEncomenda> r = new HashMap<>();
                        InterTransportadora tR = new TransportadoraRota(cod, nomeT, gpsT, NIF, Double.parseDouble(raio), cert.toUpperCase().equals("Y"), Double.parseDouble(preco), new HashMap<>(), new HashMap<>(),capacidade,r,email, "1234",Double.parseDouble(velocidadeT),true);
                        d.addTransportadora(tR);
                    } else if (res.toUpperCase().equals("N")) {
                        d.addTransportadora(t);
                    } else {
                        v.showERROR("Opção inválida.");
                        break;
                    }

                    v.showMessage("Email gerado automaticamente!");
                    v.showMessage(t.getEmail());

                    v.showMessage("Palavra passe gerada automaticamente!");
                    v.showMessage(t.getPassword());

                    v.showSucesso("Dados inseridos com sucesso!\n");

                    break;
                case 4:
                    InterLoja l = new Loja();

                    v.showMessage("Nome:");
                    v.showPointer();
                    String nomeL = Input.lerString();
                    l.setNome(nomeL);

                    v.showMessage("Latitude:");
                    v.showPointer();
                    double latitudeL = Input.lerDouble();
                    v.showMessage("Longitude:");
                    v.showPointer();
                    double longitudeL = Input.lerDouble();

                    GPS gpsL = new GPS();
                    gpsL.setY(latitudeL);
                    gpsL.setX(longitudeL);
                    l.setGps(gpsL);

                    v.showMessage("Tempo médio de espera:");
                    v.showPointer();
                    String tempo = Input.lerString();
                    l.setTempo(Double.parseDouble(tempo));

                    l.setCodLoja(d.gerarCodigoLoja());

                    v.showMessage("Email gerado automaticamente!");
                    l.atualizaEmail();
                    v.showMessage(l.getEmail());

                    v.showMessage("Palavra passe gerada automaticamente!");
                    l.setPassword("1234");
                    v.showMessage(l.getPassword());

                    l.setProdutos(startProduto());

                    d.addLoja(l);

                    v.showSucesso("Dados inseridos com sucesso!\n");

                    break;
                default:
                    v.showERROR("Opção inválida.");
                    break;
            }
            v.showRegistos();
            v.showPointer();
        }
    }

}
