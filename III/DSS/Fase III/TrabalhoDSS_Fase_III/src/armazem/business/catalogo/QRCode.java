package armazem.business.catalogo;

import java.util.Collection;

public class QRCode {
    private String codQRCode;
    private String descricao;
    private boolean perecivel;
    private Collection<String> paletes;


    public QRCode(String codQRCode, String descricao, boolean perecivel, Collection<String> paletes) {
        this.codQRCode = codQRCode;
        this.descricao = descricao;
        this.perecivel = perecivel;
        this.paletes = paletes;
    }

    public String getCodQRCode() {
        return codQRCode;
    }


    public void addPalete(String codPalete) {
        paletes.add(codPalete);
    }


}
