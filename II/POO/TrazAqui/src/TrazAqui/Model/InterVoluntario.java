package TrazAqui.Model;

import java.util.List;
import java.util.Map;

public interface InterVoluntario {
    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    void atualizaEmail();

    String getCodVoluntario();

    void setCodVoluntario(String codVoluntario);

    String getNomeVoluntario();

    void setNomeVoluntario(String nomeVoluntario);

    GPS getCoordenadas();

    void setCoordenadas(GPS coordenadas);

    double getRaio();

    void setRaio(double raio);

    boolean isLivre();

    void setLivre(boolean livre);

    Map<String, InterEncomenda> getEncVoluntario();

    void setEncVoluntario(Map<String, InterEncomenda> encomendas);

    Map<String, Integer> getClassificEncVol();

    void setClassificEncVol(Map<String, Integer> classificEnc);

    double getVelocidade();

    void setVelocidade(double velocidade);

    boolean isCertificado();

    void setCertificado(boolean certificado);

    Voluntario clone();

    void addEncomenda(InterEncomenda e);

    InterEncomenda entregaEnc();

    void encPronta(String codEnc);

    void verificaEncUtilizador(String codUser,List<String> res);

    void setClassificacao(String codEnc, int classif);

    boolean verificaEnc(String codEnc);

    boolean aceitoTransporteMedicamentos();

    void aceitaMedicamentos(boolean state);
}

