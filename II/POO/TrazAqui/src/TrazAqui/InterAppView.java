package TrazAqui;

import TrazAqui.Model.InterLoja;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InterAppView {

    void showMenu();

    void showMenuUtilizador();

    void showMenuTransportadora();

    void showMenuVoluntario();

    void showMenuLoja();

    void menuLerProduto();

    void showMessage(String s);

    void showM(String s);

    void showRegistos();

    void showPointer();

    void showMenuLG();

    void showLojas(List<String> lojas);

    void showProd(Map<String, Double> prod);

    void menuRegistarEnc();

    void showInfoEncVol(String vol, String loja, String user, String enc, double tempo, double preco, double peso);

    void showInfoEncT(String vol, double preco, double taxa);

    void showERROR(String s);

    void showEstadoVol(boolean estado);

    void showTamanhoFilaEspera(int tamanhoFila);

    void showSucesso(String s);

    void showInfoEncUlt(Map<String, String> info);

    void showMenuHistorico();

    void showFaturacao(double fact);

    void showMenuHistoricoVT();

    void showEncPeriodo(Map<String, LocalDate> encs);

    void showMenuPeriodo(String s);

    void showTop10Utilizadores(Map<Integer, String> top10Ut);

    void showTop10Transportadoras(Map<Double, String> top10T);

    void showClassificacao(Map<String, Integer> clas);

    void showEncProcess(List<String> enc);

    void showCreatEncomenda();

    void showTituloL();
}
