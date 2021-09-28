package armazem.business;

import armazem.business.abastecimento.Robot;
import armazem.business.catalogo.Localizacao;
import armazem.business.catalogo.Palete;
import armazem.business.catalogo.QRCode;

import java.util.List;

public interface IArmazemFacade {

    QRCode getQRCode(String qrCode);

    Palete getPalete(String estado);

    String registarPalete(QRCode qrCode);

    boolean validarQRCode(String qrCode);

    void atualizarEstadoPalete(String codPalete, Localizacao localizacao);

    List<String> getListagem();

    Localizacao getLocalizacao();

    Robot getRobot();

    List <Integer> getPercurso(Localizacao destino, Palete p);

    void notificarRobot(Palete p, List<Integer> percurso, Robot robot);

    void atualizaOcupacao(int corredor);

    String getPaleteRobot(String robot);

    Localizacao getLocalizacaoPalete(String robot);

    void atualizaRobot(String robot);

    boolean existeEspacoArmazem();

    boolean existePaleteRececao();

    boolean existeRobot();

    String getEstadoPalete(String palete);
}
