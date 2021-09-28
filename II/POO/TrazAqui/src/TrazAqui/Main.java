package TrazAqui;

import TrazAqui.Controller.TrazAquiController;
import TrazAqui.Model.DadosApp;
import TrazAqui.Model.Parse;
import TrazAqui.View.View;

public class Main {

    /**
     * Método que inicializa o programa
     * @param args
     */
    public static void main(String[] args) {
        Parse p = new Parse();
        InterAppModel d = new DadosApp();
        InterAppView v = new View();

        p.parse(d);
        d.eliminaSemProdutos();

        TrazAquiController c = new TrazAquiController();
        c.setDadosApp(d);
        c.setView(v);

        v.showMenu();
        v.showPointer();

        c.menu();

    }
}
