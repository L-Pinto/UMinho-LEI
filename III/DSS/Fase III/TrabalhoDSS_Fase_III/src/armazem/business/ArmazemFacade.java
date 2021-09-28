package armazem.business;

import armazem.business.abastecimento.AbastecimentoFacade;
import armazem.business.abastecimento.IAbastecimentoFacade;
import armazem.business.abastecimento.Robot;
import armazem.business.catalogo.*;

import java.util.List;

public class ArmazemFacade implements IArmazemFacade{
    private ICatalogoFacade modelCatalogo;
    private IAbastecimentoFacade modelAbastecimento;

    public ArmazemFacade() {
        this.modelAbastecimento = new AbastecimentoFacade();
        this.modelCatalogo = new CatalogoFacade();
    }

    /**
     * Obter um QRCode referente a um ID de QRCode.
     * @param qrCode ID de QRCode.
     * @return QRCode.
     */
    public QRCode getQRCode(String qrCode) {
        return modelCatalogo.getQRCode(qrCode);
    }

    /**
     * Obter uma palete num determinado estado.
     * @param estado em que se pretende obter uma palete.
     * @return Palete.
     */
    public Palete getPalete(String estado) {
        return modelCatalogo.getPalete(estado);
    }

    /**
     * Registar uma nova palete.
     * @param qrCode da palete.
     * @return Codigo da palete registada.
     */
    public String registarPalete(QRCode qrCode) {
        return modelCatalogo.registarPalete(qrCode);
    }

    /**
     * Valida um QRCode.
     * @param qrCode a validar.
     * @return True se for válido, False caso contrário.
     */
    public boolean validarQRCode(String qrCode) {
        return modelCatalogo.validarQRCode(qrCode);
    }

    /**
     * Atualiza o estado de uma palete.
     * @param codPalete Codigo de palete que se pretende atualizar o estado.
     * @param localizacao da palete a atualizar.
     */
    public void atualizarEstadoPalete(String codPalete, Localizacao localizacao) {
        modelCatalogo.atualizarEstadoPalete(codPalete,localizacao);
    }

    /**
     * Obter Listagem de ocupações.
     * @return Listagem.
     */
    public List<String> getListagem() {
        return modelAbastecimento.getListagem();
    }

    /**
     * Obter a Localização futura da palete.
     * @return ID da localização futura.
     */
    public Localizacao getLocalizacao() {
        boolean[] visitados = modelCatalogo.getLocalizacaoPaletes();
        int local = modelAbastecimento.getLocalizacao(visitados);
        return modelCatalogo.initLocalizacao(local, "armazenamento");
    }

    /**
     * Obter um robot para realizar o transporte.
     * @return Robot selecionado.
     */
    public Robot getRobot() {
        return modelAbastecimento.getRobot();
    }

    /**
     * Obter o percurso para o robot.
     * @param destino Destino da palete.
     * @param p Palete a transportar.
     * @return Percurso.
     */
    public List<Integer> getPercurso(Localizacao destino, Palete p) {
        return modelAbastecimento.getPercurso(destino,p);
    }

    /**
     * Notificar o robot com a informação para realização do percurso.
     * @param p Palete a transportar.
     * @param percurso a percorrer.
     * @param robot a notificar.
     */
    public void notificarRobot(Palete p, List<Integer> percurso, Robot robot) {
        modelAbastecimento.notificarRobot(p,percurso,robot);
    }

    /**
     * Atualiza a ocupação de um corredor.
     * @param corredor a atualizar.
     */
    public void atualizaOcupacao(int corredor) {
        modelAbastecimento.atualizaOcupacao(corredor);
    }

    /**
     * Obter o identificador de uma palete que se encontra num robot.
     * @param robot onde procurar.
     * @return identificador da palete.
     */
    public String getPaleteRobot(String robot) {
        return modelAbastecimento.getPaleteRobot(robot);
    }

    /**
     * Obter localização futura da palete.
     * @param robot que contém o percurso e consequentemente a localização futura da palete.
     * @return ID da localização.
     */
    public Localizacao getLocalizacaoPalete(String robot) {
        int local = modelAbastecimento.getLocalizacaoPalete(robot);
        return modelCatalogo.initLocalizacao(local, "armazenamento") ;
    }

    /**
     * Atualiza o estado do robot.
     * @param robot a atualizar.
     */
    public void atualizaRobot(String robot) {
        modelAbastecimento.atualizaRobot(robot);
    }

    /**
     * Método que verifica se existe espaço no armazém
     * @return true caso exista, false caso contrário
     */
    public boolean existeEspacoArmazem() {

        return modelAbastecimento.existeEspacoArmazem();
    }

    /**
     * Método que verifica se existe alguma palete na receção
     * @return true caso exista, false caso contrário
     */
    public boolean existePaleteRececao() {

        return modelCatalogo.existePaleteRececao();
    }

    /**
     * Método que verifica se existe um robot livre
     * @return true caso exista, false caso contrário
     */
    public boolean existeRobot() {

        return modelAbastecimento.existeRobot();
    }

    /**
     * Método que retorna o estado atual de uma dada palete
     * @param palete código de palete a verificar
     * @return estado atual da palete
     */
    public String getEstadoPalete(String palete) {

        return modelCatalogo.getEstadoPalete(palete);
    }
}
