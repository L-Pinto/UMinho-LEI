package TrazAqui.Model;

import java.util.Map;

public interface InterUtilizador {
    String getEmail();

    void setEmail(String email);

    void setPassword(String password);

    String getPassword();

    Map<String, InterEncomenda> getEncUtilizador();

    void setEncUtilizador(Map<String, InterEncomenda> encomendas);

    String getCodUtilizador();

    void setCodUtilizador(String codigo);

    String getNomeUtilizador();

    void setNomeUtilizador(String nome);

    InterGPS getCoordenadas();

    void setCoordenadas(InterGPS g);

    void atualizaEmail();

    void addEncomenda(InterEncomenda e);

    void atualizaEstado(String codEnc);

    Utilizador clone();

}
