package armazem.business.catalogo;

public class Palete {
    private String codPalete;
    private String estado;
    private Localizacao localizacao;
    private String qrCode;

    /**
     * Construtor de uma nova palete.
     * @param qrCode código QR da palete
     * @param codPalete código identificador da palete
     */
    public Palete (String qrCode, String codPalete) {
        this.estado = "rececao";
        this.localizacao = new Localizacao();
        this.qrCode = qrCode;
        this.codPalete = codPalete;
    }

    public Palete(String codPalete, String estado, String qrCode, Localizacao localizacao) {
        this.codPalete = codPalete;
        this.estado = estado;
        this.localizacao = localizacao;
        this.qrCode = qrCode;
    }

    public void setCodPalete(String codPalete) {
        this.codPalete = codPalete;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getCodPalete() {
        return codPalete;
    }

    public int getPrateleira() {
        return localizacao.getPrateleira();
    }

    public String getZona() {
        return localizacao.getZona();
    }

    public String getEstado() {
        return this.estado;
    }

    @Override
    public String toString() {
        return "\033[0;34m" + "Palete: " + "\033[0m" + "\n" +
                "  codPalete = " + codPalete + "\n" +
                "  estado = " + estado  + "\n" +
                "  localizacao = " + localizacao + "\n" +
                "  qrCode = " + qrCode ;
    }
}
