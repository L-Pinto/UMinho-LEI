package armazem.business.catalogo;

public interface ICatalogoFacade {

    QRCode getQRCode(String qrCode);

    Palete getPalete(String estado);

    String registarPalete(QRCode qrCode);

    boolean validarQRCode(String qrCode);

    void atualizarEstadoPalete(String codPalete, Localizacao localizacao);

    Localizacao initLocalizacao(int local, String armazenamento);

    int getPrateleira(Localizacao l);

    boolean existePaleteRececao();

    String getEstadoPalete(String palete);

    boolean[] getLocalizacaoPaletes();
}
