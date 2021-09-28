package armazem.business.catalogo;

import armazem.data.PaleteDAO;
import armazem.data.QRCodeDAO;

import java.util.Map;

public class CatalogoFacade implements ICatalogoFacade{
    /**Map com código QR Code para QRCode*/
    private Map<String, QRCode> armazenamento;

    /**Map com código de palete para Palete*/
    private Map<String, Palete> paletes;


    /**
     * Construtor da classe CatalogoFacade
     */
    public CatalogoFacade() {
        this.armazenamento = QRCodeDAO.getInstance();
        this.paletes = PaleteDAO.getInstance();
    }

    /**
     * Método que retorna o QRCode de um dado ID de QRCode
     * @param qrCode ID de QRCode.
     * @return QRCode correspondente ao ID
     */
    public QRCode getQRCode(String qrCode) {
        return armazenamento.get(qrCode);
    }

    /**
     * Retorna a primeira palete num determinado estado
     * @param estado desejado
     * @return palete com o estado
     */
    public Palete getPalete(String estado) {
        Palete palete = null;

        for(Palete p : paletes.values()) {
            if (p.getEstado().equals(estado)) {
                palete = p;
                break;
            }
        }

        return palete;
    }

    /**
     * Método que regista uma nova palete, a partir do seu QRCode.
     * @param qrCode
     */
    public String registarPalete(QRCode qrCode) {
        int nr = this.paletes.size() +1;
        String codPalete = qrCode.getCodQRCode()+nr;

        while(this.paletes.containsKey(codPalete)) {
            nr++;
            codPalete = qrCode.getCodQRCode()+nr;
        }

        Palete palete = new Palete(qrCode.getCodQRCode(),codPalete);

        paletes.put(codPalete,palete);
        QRCode qr = armazenamento.get(qrCode.getCodQRCode());
        qr.addPalete(codPalete);

        return codPalete;
    }

    /**
     * Método que verifica se existe um determinado QRCode no sistema
     * @param qrCode a verificar
     * @return true caso exista, false caso contrário
     */
    public boolean validarQRCode(String qrCode) {
        boolean res = armazenamento.containsKey(qrCode);
        return res;
    }

    /**
     * Método que atualiza o estado de uma palete consoante o estado anterior
     * @param codPalete código da palete a atualizar estado
     */
    public void atualizarEstadoPalete(String codPalete, Localizacao localizacao) {
        Palete palete = paletes.get(codPalete);
        String estado = palete.getEstado();

        switch(estado) {
            case "rececao":
                palete.setEstado("aguardaTransporte");
                palete.setLocalizacao(localizacao);
                paletes.put(codPalete,palete);
                break;
            case "aguardaTransporte":
                palete.setEstado("robot");
                break;
            case "robot":
                palete.setEstado("armazenamento");
                break;
            default:
                break;
        }
        paletes.put(codPalete,palete);
    }


    /**
     * Método que inicializa uma localização
     * @param local prateleira
     * @param zona a armazenar
     * @return localização
     */
    public Localizacao initLocalizacao(int local, String zona) {
        return new Localizacao(local,zona);
    }

    /**
     * Método que retorna a prateleira de uma dada localização
     * @param l Localização que se pretende verificar.
     * @return prateleira da localização
     */
    public int getPrateleira(Localizacao l) {
        int res;
        int corredor = l.getPrateleira();
        if(corredor>= 1 && corredor<=5) {
            res = 1;
        } else{
            res=2;
        }
        return res;
    }

    /**
     * Método que verifica se existe uma palete com o estado "rececao"
     * @return true caso exista, false caso contrário
     */
    public boolean existePaleteRececao() {

        for(Palete p: this.paletes.values()) {
            if (p.getEstado().equals("rececao")) return true;
        }
        return false;
    }

    /**
     * Método que retorna o estado de uma dada palete
     * @param palete a verificar
     * @return estado da palete
     */
    public String getEstadoPalete(String palete) {

        Palete p = this.paletes.get(palete);
        if(p == null) return "null";

        return p.getEstado();
    }

    /**
     * Método que verifica quais as prateleiras ocupadas
     * @return array de boolean que indica se as prateleiras estão ocupadas ou não
     */
    public boolean[] getLocalizacaoPaletes() {
        boolean[] visitados = new boolean[10];

        for(int i = 0; i<10; i++) {
            visitados[i] = false;
        }

        for(Palete p: this.paletes.values()) {
            int corredor = p.getPrateleira();
            if(corredor != 0) {
                visitados[p.getPrateleira() - 1] = true;
            }
        }

        return visitados;
    }
}