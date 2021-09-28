package TrazAqui.Model;

import java.util.List;
import java.util.TreeMap;
import java.util.Map;

public interface InterLoja {

    Map<String, InterLinhaEncomenda> getProdutos();

    void setProdutos(Map<String, InterLinhaEncomenda> produtos);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    String getCodLoja();

    void setCodLoja(String cod);

    String getNome();

    void setNome(String nome);

    InterGPS getGps();

    void setGps(GPS gps);

    double getTempo();

    void setTempo(double tempo);

    Map<String, InterEncomenda> getEncomendasLoja();

    void setEncomendasLoja(Map<String, InterEncomenda> encomendaLoja);

    void atualizaProdutosEnc(InterEncomenda e);

    void atualizaEmail();

    Loja clone();

    Map<String,Double> getProd();

    String getProdCodigo(String descricao);

    void adicionaEncomenda(InterEncomenda e);

    List<String> getEncLojaNE();

    void entregaEnc(String codEnc);

    List<String> encomendasProntas();

    void encPronta(String codEnc);
}
