package SGV;

import SGV.Controller.GestVendasController;
import SGV.Model.GestVendas;
import SGV.View.View;

public class GestVendasAppMVC
{
    /**
     * Inicia o MVC do programa
     * @param args - argumentos da main (vazio)
     */
    public static void main(String [] args)
    {
        String dClie = "../projJava/docs/Clientes.txt";
        String dProd = "../projJava/docs/Produtos.txt";
        String dVendas = "../projJava/docs/Vendas_1M.txt";
        InterGestView view = new View();
        InterGestModel model = new GestVendas();
        view.showTitulo();
        int i = model.leituraGestVendas(dClie, dProd, dVendas);
        if(i != 0)
        {
            view.showERROR("Load dos ficheiros Invalido");
            System.exit(-1);
        }
        InterGestController control = new GestVendasController();
        control.setView(view);
        control.setModel(model);
        control.start();
    }
}
