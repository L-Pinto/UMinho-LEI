package TrazAqui.Model;

import TrazAqui.InterAppModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Parse {

    /**
     * Método que populaciona a estrutura principal do programa, após leitura do ficheiro logs
     * @param d classe principal do programa
     */
    public void parse(InterAppModel d){
        List<String> linhas = lerFicheiro("Logs.csv");
        String[] linhaPartida;

        List<String> vt = new ArrayList<>();

        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0]){
                case "Utilizador":
                    InterUtilizador u = parseUtilizador(linhaPartida[1]);
                    d.addUtilizador(u);

                    break;
                case "Loja":
                    InterLoja l = parseLoja(linhaPartida[1]);
                    d.addLoja(l);

                    break;
                case "Encomenda":
                    InterEncomenda e = parseEncomenda(linhaPartida[1]);
                    d.addEncomenda(e);

                    String [] campos = linhaPartida[1].split(",");
                    d.atualizaEncUtilizador(campos,e);

                    d.atualizaProdLoja(e);

                    d.atualizaEncVT(e,vt);

                    break;
                case "Voluntario":
                    InterVoluntario v = parseVoluntario(linhaPartida[1]);
                    vt.add(v.getCodVoluntario());
                    d.addVoluntario(v);

                    break;
                case "Transportadora":
                    InterTransportadora t = parseTransportadora(linhaPartida[1]);
                    vt.add(t.getCodTransportadora());
                    d.addTransportadora(t);

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Método que gera uma velocidade aleatoriamente
     * @return velocidade
     */
    public double gerarVelocidade()
    {
        Random rad = new Random();
        double aux = rad.nextDouble();

        if(aux == 0) aux = 1.0;

        return aux*120;
    }

    /**
     * Método que gera um tempo de fila aleatório
     * @return tempo de fila
     */
    public double gerarTempoFila()
    {
        Random rad = new Random();
        double aux = rad.nextDouble();

        return aux*15;
    }

    /**
     * Método que gera um tempo de encomenda aleatório
     * @return tempo de encomenda
     */
    public double gerarTempoEnc()
    {
        Random rad = new Random();
        double aux = rad.nextDouble();

        if(aux == 0) aux = 1.0;

        return aux*30;
    }

    /**
     * Método que gera um certificado aleatório
     * @return certificado
     */
    public boolean gerarCertificado()
    {
        Random rad = new Random();
        return rad.nextBoolean();
    }

    /**
     * Método que gera uma data aleatória
     * @return data
     */
    public LocalDate gerarData()
    {
        Random rad = new Random();
        int mes = rad.nextInt(6);
        int dia = rad.nextInt(28);

        if(mes==0) mes = 1;
        if(dia==0) dia = 1;

        return LocalDate.of(2020,mes,dia);
    }

    /**
     * Método que parte uma linha de utilizador nos seus diversos campos, e cria um utilizador
     * @param input linha de utilizador
     * @return utilizador
     */
    public InterUtilizador parseUtilizador(String input){
        String[] campos = input.split(",");

        InterUtilizador u = new Utilizador();

        String nome = campos[1];
        String codUtilizador = campos[0];
        InterGPS gps = new GPS();
        gps.setX(Double.parseDouble(campos[2]));
        gps.setY(Double.parseDouble(campos[3]));

        u.setNomeUtilizador(nome);
        u.setCodUtilizador(codUtilizador);
        u.atualizaEmail();
        u.setCoordenadas(gps);

        return u;
    }

    /**
     * Método que parte uma linha de loja nos seus diversos campos, e cria uma loja
     * @param input linha de loja
     * @return loja
     */
    public Loja parseLoja(String input){
        String[] campos = input.split(",");

        Loja l = new Loja();

        String codLoja = campos[0];
        String nomeLoja = campos[1];
        GPS gps = new GPS();
        gps.setX(Double.parseDouble(campos[2]));
        gps.setY(Double.parseDouble(campos[3]));

        l.setGps(gps);
        l.setTempo(gerarTempoFila());
        l.setCodLoja(codLoja);
        l.atualizaEmail();
        l.setPassword("1234");
        l.setNome(nomeLoja);

        return l;
    }

    /**
     * Método que parte uma linha de encomenda nos seus diversos campos, e cria uma encomenda
     * @param input linha da encomenda
     * @return encomenda
     */
    public Encomenda parseEncomenda(String input) {
        String[] campos = input.split(",");
        double preco = 0;

        Encomenda e = new Encomenda();

        String codEnc = campos[0];
        String codUser = campos[1];
        String codLoja = campos[2];
        double pesoEnc = Double.parseDouble(campos[3]);

        e.setCodEnc(codEnc);
        e.setCodUser(codUser);
        e.setCodLoja(codLoja);
        e.setPesoEnc(pesoEnc);
        e.setCertificado(gerarCertificado());
        e.setTempoEnc(gerarTempoEnc());
        e.setData(gerarData());

        int i = 4;
        while(i < campos.length) {
            LinhaEncomenda l = new LinhaEncomenda();
            l.setCodProd(campos[i++]);
            l.setDescricaoProd(campos[i++]);
            double qnt = Double.parseDouble(campos[i++]);
            l.setQntProd(qnt);
            double p = Double.parseDouble(campos[i++]);
            l.setPrecoProd(p);

            preco += qnt * p;

            e.adicionaLinhaEncomenda(l);
        }

        e.setCustoEnc(preco);

        return e;
    }

    /**
     * Método que parte uma linha de Transportadora nos seus diversos campos, e cria uma transportadora
     * @param input linha da transportadora
     * @return transportadora
     */
    public Transportadora parseTransportadora(String input) {
        String[] campos = input.split(",");

        Transportadora t = new Transportadora();

        String codTrans = campos[0];
        String nome = campos[1];
        GPS gps = new GPS();
        gps.setX(Double.parseDouble(campos[2]));
        gps.setY(Double.parseDouble(campos[3]));
        String nif = campos[4];
        double raio = Double.parseDouble(campos[5]);
        double preco = Double.parseDouble(campos[6]);

        t.setCodTransportadora(codTrans);
        t.atualizaEmail();
        t.setPassword("1234");
        t.setNome(nome);
        t.setGps(gps);
        t.setNif(nif);
        t.setRaio(raio);
        t.setPreco(preco);
        t.setCertificado(gerarCertificado());
        t.setVelocidade(gerarVelocidade());
        t.setLivre(true);

        t.aceitaMedicamentos(t.isCertificado());

        return t;
    }

    /**
     * Método que parte uma linha de voluntario nos seus diversos campos, e cria um voluntario
     * @param input linha do voluntário
     * @return voluntário
     */
    public Voluntario parseVoluntario(String input) {
        String[] campos = input.split(",");

        Voluntario v = new Voluntario();

        String cod = campos[0];
        String nome = campos[1];
        GPS gps = new GPS();
        gps.setX(Double.parseDouble(campos[2]));
        gps.setY(Double.parseDouble(campos[3]));
        double raio = Double.parseDouble(campos[4]);

        v.setCodVoluntario(cod);
        v.setLivre(true);
        v.atualizaEmail();
        v.setNomeVoluntario(nome);
        v.setCoordenadas(gps);
        v.setRaio(raio);
        v.setCertificado(gerarCertificado());
        v.setVelocidade(gerarVelocidade());
        v.atualizaEmail();
        v.setPassword("1234");

        v.aceitaMedicamentos(v.isCertificado());

        return v;
    }


    /**
     * Método que efetua a leitura dum ficheiro
     * @param nomeFich nome do ficheiro
     * @return Lista com todas as linhas do ficheiro em String
     */
    public List<String> lerFicheiro(String nomeFich) {
        List<String> lines = new ArrayList<>();
        try { lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); }
        catch(IOException exc) {exc.getMessage();}
        return lines;
    }


}
