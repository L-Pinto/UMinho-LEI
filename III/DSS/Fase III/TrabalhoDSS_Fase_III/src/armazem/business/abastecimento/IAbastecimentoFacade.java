package armazem.business.abastecimento;

import armazem.business.catalogo.Localizacao;
import armazem.business.catalogo.Palete;

import java.util.List;

public interface IAbastecimentoFacade
{

    List<String> getListagem();

    int getLocalizacao(boolean[] visitados);

    Robot getRobot();

    List <Integer> getPercurso(Localizacao destino, Palete p);

    void notificarRobot(Palete p, List<Integer> percurso, Robot robot);

    void atualizaOcupacao(int corredor);

    String getPaleteRobot(String robot);

    int getLocalizacaoPalete(String robot);

    void atualizaRobot(String robot);

    boolean existeEspacoArmazem();

    boolean existeRobot();
}
