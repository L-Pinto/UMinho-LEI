package armazem.business.abastecimento;

import armazem.data.CorredorDAO;
import armazem.data.RobotDAO;
import armazem.business.catalogo.Localizacao;
import armazem.business.catalogo.Palete;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AbastecimentoFacade implements IAbastecimentoFacade {
    private Map<String, Robot> frota;
    private Map<Integer, Corredor> corredores;
    private Mapa mapa;

    /**
     * Construtor da classe AbastecimentoFacade
     */
    public AbastecimentoFacade() {
        this.frota = RobotDAO.getInstance();
        this.corredores = CorredorDAO.getInstance();
        this.mapa = new Mapa(13);

        //robot desloca-se para a rececao
        this.mapa.addVertice(11,0);
        // desloca-se pelo corredor 1 com destino 12
        this.mapa.addVertice(0,1);
        this.mapa.addVertice(1,2);
        this.mapa.addVertice(2,4);
        this.mapa.addVertice(4,6);
        this.mapa.addVertice(6,8);
        this.mapa.addVertice(8,12);
        //desloca-se pelo corredor 2 com destino 12
        this.mapa.addVertice(0,11);
        this.mapa.addVertice(11,3);
        this.mapa.addVertice(3,5);
        this.mapa.addVertice(5,7);
        this.mapa.addVertice(7,9);
        this.mapa.addVertice(9,10);
        this.mapa.addVertice(10,12);
    }

    /**
     * Método que obtem a listagem com a ocupação do armazém
     * @return lista de string com as ocupações
     */
    public List<String> getListagem() {
        List<String> listagem = new ArrayList<>();

        for(Corredor c: corredores.values()) {
            listagem.add(c.toString());
        }
        return listagem;
    }

    /**
     * Método que obtém a primeira localização livre e mais próxima da receção
     * @param visitados array com as prateleiras visitadas
     * @return primeira prateleira vazia encontrada
     */
    public int getLocalizacao(boolean[] visitados) {

        int local = -1;

        for(int i = 0; i <10 && local == -1; i++) {
            if(!visitados[i]) local = i+1;
        }

        return local;
    }

    /**
     * Método que retorna um robot
     * @return robot
     */
    public Robot getRobot() {
        boolean ocupado = true;
        Robot res = null;
        Iterator iterator = frota.values().iterator();

        while(iterator.hasNext() && ocupado) {

            Robot r = (Robot) iterator.next();
            ocupado = r.isOcupado();

            if(!ocupado) {
                r.setOcupado(true);
                frota.put(r.getCodRobot(), r);
                res = r;
            }
        }

        return res;
    }

    /**
     * Método que calcula o percurso do robot
     * @param destino Destino da palete.
     * @param p Palete a transportar.
     * @return lista com as prateleiras (vértices) a percorrer desde a origem até ao destino
     */
    public List<Integer> getPercurso(Localizacao destino, Palete p) {

        int s = p.getPrateleira();
        int d = destino.getPrateleira();

        List<Integer> pathRP = this.mapa.caminho(11,s);
        List<Integer> pathPD = this.mapa.caminho(s,d);

        List<Integer> percurso = this.mapa.juntaCaminhos(pathRP,pathPD);

        return percurso;
    }

    /**
     * Método que notifica o robot em questão
     * @param p Palete a transportar.
     * @param percurso a percorrer.
     * @param robot a notificar.
     */
    public void notificarRobot(Palete p, List<Integer> percurso, Robot robot) {

        String codPalete = p.getCodPalete();

        robot.setCodPalete(codPalete);
        robot.setPercurso(percurso);

        frota.put(robot.getCodRobot(),robot);

        int prateleira = getLocalizacaoPalete(robot.getCodRobot());
        int corredor = -1;

        if(prateleira==1 ||prateleira==2 || prateleira==4 || prateleira==6 || prateleira==8){
            corredor = 1;
        }else corredor = 2;

        atualizaOcupacao(corredor);


    }

    /**
     * Método que atualiza a ocupação num dado corredor
     * @param corredor a atualizar.
     */
    public void atualizaOcupacao(int corredor) {

        Corredor c = this.corredores.get(corredor);
        int ocupacao = c.getOcupacao();
        c.setOcupacao(ocupacao + 1);
        this.corredores.put(corredor, c);
        if(corredor == 1 || corredor == 2) {
            Corredor cc = this.corredores.get(0);
            int ocup = cc.getOcupacao();
            cc.setOcupacao(ocup - 1);
            this.corredores.put(0, cc);
        }
    }

    /**
     * Método que retorna o código da palete cujo robot está a transportar
     * @param robot onde procurar.
     * @return código de palete em questão
     */
    public String getPaleteRobot(String robot) {
        Robot r = frota.get(robot);
        return r.getCodPalete();
    }

    /**
     * Método que retorna qual o destino do robot
     * @param robot que contém o percurso e consequentemente a localização futura da palete.
     * @return local de armazenamento da palete
     */
    public int getLocalizacaoPalete(String robot) {
        Robot r = frota.get(robot);
        return r.getDestino();
    }

    /**
     * Método que "inicializa" o robot
     * @param robot a atualizar.
     */
    public void atualizaRobot(String robot) {
        Robot r = frota.get(robot);
        r.setOcupado(false);
        r.setCodPalete(null);
        r.setPercurso(null);

        frota.put(robot,r);

    }

    /**
     * Método que verifica se existe espaço no armazém
     * @return true caso exista espaço, false caso contrário
     */
    public boolean existeEspacoArmazem() {

        int total = 0;

        for(Corredor c: this.corredores.values()) {
            total += c.getOcupacao();
        }
        return (total < 10);
    }

    /**
     * Método que verifica se existe pelo menos um robot que não esteja ocupado
     * @return true caso exista, false caso contrário
     */
    public boolean existeRobot() {

        for(Robot r: this.frota.values()){
            if(!r.isOcupado()) return true;
        }
        return false;
    }
}