package TrazAqui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InterTransportadora {

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    void atualizaEmail();

    String getCodTransportadora();

    void setCodTransportadora(String codTransportadora);

    String getNome();

    void setNome(String nome);

    GPS getGps();

    void setGps(GPS gps);

    String getNif();

    void setNif(String nif);

    double getRaio();

    void setRaio(double raio);

    boolean isCertificado();

    void setCertificado(boolean certificado);

    double getPreco();

    void setPreco(double preco);

    Map<String, InterEncomenda> getEncomendas();

    void setEncomendas(Map<String, InterEncomenda> encomendas);

    Map<String, Integer> getClassificacao();

    void setClassificacao(Map<String, Integer> classificacao);

    Transportadora clone();

    double getVelocidade();

    void setVelocidade(double velocidade);

    boolean isLivre();

    void setLivre(boolean livre);

    void adicionaEncomenda(InterEncomenda e);

    void encPronta(String codEnc);

    InterEncomenda entregaEnc();

    void verificaEncUtilizador(String codUser, List<String> res);

    void setClassificacao(String codEnc, int classif);

    void addEncomenda(InterEncomenda e);

    boolean verificaEnc(String codEnc);

    double getFaturacao(LocalDate d1, LocalDate d2);

    boolean aceitoTransporteMedicamentos();

    void aceitaMedicamentos(boolean state);

    double contaKms();
}
