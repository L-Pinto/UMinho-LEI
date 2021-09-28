package TrazAqui;

import TrazAqui.Model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface InterAppModel {

    String getLog();

    void setLog(String log);

    void addUtilizador(InterUtilizador u);

    void addVoluntario(InterVoluntario v);

    void addTransportadora(InterTransportadora t);

    void addLoja(InterLoja l);

    void addEncomenda(InterEncomenda e);

    void atualizaEncUtilizador(String[] campos, InterEncomenda e);

    void atualizaProdLoja(InterEncomenda e);

    boolean verificaEmail();

    String gerarCodigoUtilizador();

    String gerarCodigoVoluntario();

    String gerarCodigoTransportadora();

    String gerarCodigoLoja();

    String gerarCodigoEncomenda();

    Double gerarPeso();

    void gravarObj(String filename) throws IOException;

    DadosApp lerObj(String filename) throws IOException, ClassNotFoundException;

    List<String> getLojas();

    Map<String, Double> getLojaNome(String nome);

    String getCodProduto(String descricao, String codloja);

    String getCodLoja(String nome);

    String atribuicaoVT(InterEncomenda enc) throws NullVTException;

    double atribuiTempo(String vol, InterEncomenda enc);

    double precoPorKm(String codT, InterEncomenda e);

    void setVoluntarioLivre(String codV, boolean b);

    boolean getEstadoVol();

    void adicionaEncomenda(InterEncomenda enc, String codVT,double taxa);

    void eliminaSemProdutos();

    List<String> getEncLojaNE();

    int buscarEncVT();

    void encomendaPronta(String codEnc, String codUser);

    boolean getEstadoTransp();

    void setTranspLivre(String codT, boolean b);

    InterEncomenda getEncLoja(String codLoja, String enc);

    List<String> getEncUtilizador();

    void setClassifEnc(String enc, int classif);

    Map<String,String> getInfoEncomenda(String cod);

    Map<String,LocalDate> getEncUtiVol(String cod);

    double getFaturacaoData(LocalDate d1, LocalDate d2);

    Map<String,LocalDate> getEncomendas();

    Map<String,LocalDate> getEncomendasPeriodo(LocalDate data1, LocalDate data2);

    Map<Integer, String> getTop10Utilizadores();

    Map<Double, String> getTop10Transportadoras();

    Map<String, Integer> getClassificacao();

    void atualizaEncVT(InterEncomenda e, List<String> ls);

    boolean certificadoOKAY( boolean enc, boolean v, boolean va);

    boolean getCertificadoMedico();

    boolean setAceitoMedicamento();

    List<String> getEncomendasProcessVol();

    List<String> getEncomendasVT();

}
