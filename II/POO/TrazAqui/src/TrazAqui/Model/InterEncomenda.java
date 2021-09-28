package TrazAqui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InterEncomenda {

    void adicionaLinhaEncomenda(InterLinhaEncomenda e);

    String getCodEnc();

    String getCodUser();

    String getCodLoja();

    double getPesoEnc();

    double getCustoEnc();

    double getTempoEnc();

    List<LinhaEncomenda> getEncomenda();

    boolean isCertificado();

    void setCodEnc(String codEnc);

    void setCodUser(String codUser);

    void setCodLoja(String codLoja);

    void setPesoEnc(double pesoEnc);

    void setCustoEnc(double custoEnc);

    void setTempoEnc(double tempoEnc);

    void setEncomenda(List<LinhaEncomenda> enc);

    void setCertificado(boolean certificado);

    Map<String,InterLinhaEncomenda> getLinhasEncomenda();

    Encomenda clone();

    int getEstado();

    void setEstado(int estado);

    LocalDate getData();

    void setData(LocalDate data);
}
