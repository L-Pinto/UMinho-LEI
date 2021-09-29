package SGV.Controller;

import SGV.*;
import SGV.Model.*;
import SGV.View.NavView;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class GestVendasController implements InterGestController
{
    private InterGestView view;
    private InterGestModel model;

    /**
     * Atualiza a view do programa
     * @param view - view atualizada.
     */
    public void setView(InterGestView view)
    {
        this.view = view;
    }

    /**
     * Atualiza a model do programa
     * @param model - view atualizada.
     */
    public void setModel(InterGestModel model)
    {
        this.model = model;
    }

    /**
     * Inicia a execução do programa
     */
    public void start()
    {
        String opcao = "";
        do
        {
            view.showMenu();
            view.showPointer();
            opcao = Input.lerString();
            opcao = opcao.toUpperCase();
            switch (opcao)
            {
                case "0" : break;
                case "1" : {
                    InterGestModel aux = new GestVendas();
                    view.showMessage("Nome do ficheiro dos clientes: ");
                    view.showPointer();
                    String cli = Input.lerString();
                    view.showMessage("Nome do ficheiro dos produtos: ");
                    view.showPointer();
                    String prod = Input.lerString();
                    view.showMessage("Nome do ficheiro dos vendas: ");
                    view.showPointer();
                    String vendas = Input.lerString();
                    Crono.start();
                    if(aux.leituraGestVendas(cli, prod, vendas) != 0) {
                        view.showERROR("Ficheiros Inexistentes");
                        break;
                    }
                    model = aux;
                    view.showTime(Crono.getTime());
                    view.showMessageValido("FICHEIRO LIDO COM SUCESSO!");
                    break;
                }
                case "2": {
                    view.showMessageYN("Guardar em ficheiro objeto predefenido ");
                    view.showPointer();
                    String pred = Input.lerString();
                    pred = pred.toUpperCase();
                    if(pred.equals("Y"))
                    {
                        Crono.start();
                        try {
                            model.gravarObj("gestVendas.dat");
                            view.showMessageValido("FICHEIRO GUARDADO COM SUCESSO");
                        } catch (IOException e) {
                            view.showERROR(e.getMessage());
                        }
                        view.showTime(Crono.getTime());
                    }
                    else
                    {
                        view.showMessage("Nome do ficheiro dos objeto: ");
                        view.showPointer();
                        String obj = Input.lerString();
                        Crono.start();
                        try {
                            model.gravarObj(obj);
                            view.showMessageValido("FICHEIRO GUARDADO COM SUCESSO");
                        } catch (IOException e) {
                            view.showERROR(e.getMessage());
                        }
                        view.showTime(Crono.getTime());
                    }
                    break;
                }
                case "3": {
                    view.showMessageYN("Ler ficheiro objeto predefenido ");
                    view.showPointer();
                    String pred = Input.lerString();
                    pred = pred.toUpperCase();
                    if(pred.equals("Y"))
                    {
                        Crono.start();
                        InterGestModel model;
                        try {
                            model = this.model.lerObj("gestVendas.dat");
                            this.model = new GestVendas();
                            this.model = model;
                            view.showMessageValido("FICHEIRO LIDO COM SUCESSO!");
                        } catch (IOException | ClassNotFoundException e) {
                            view.showERROR( e.getMessage());
                        }
                        view.showTime(Crono.getTime());
                    }
                    else
                    {
                        Crono.start();
                        InterGestModel model;
                        view.showMessage("Nome do ficheiro dos objeto: ");
                        view.showPointer();
                        String obj = Input.lerString();
                        try {
                            model = this.model.lerObj(obj);
                            this.model= new GestVendas();
                            this.model = model;
                            view.showMessageValido("FICHEIRO LIDO COM SUCESSO!");
                        } catch (IOException | ClassNotFoundException e) {
                            view.showERROR(e.getMessage());
                        }
                        view.showTime(Crono.getTime());
                    }
                    break;
                }
                case "4": {
                    Crono.start();
                    String vendas = model.getFileVendas();
                    int errados = model.getVendasErradas();
                    int prodDif = model.getProdDif();
                    int prodNao = model.getProdNC();
                    int produtos = prodDif + prodNao;
                    int clieDif = model.getCliC();
                    int clieNao = model.getCliNC();
                    int clientes = clieDif + clieNao;
                    int zero = model.getVendasGratis();
                    double fact = model.getFactTotal();
                    view.showTime(Crono.getTime());
                    view.showInfoVendas(vendas, errados,produtos, prodDif, prodNao, clientes, clieDif, clieNao, zero, fact);
                    break; }
                case "5": starEstatistic(); break;
                case "6": {
                    Crono.start();
                    Set<String> produtos = model.produtosNuncaComprados();
                    view.showTime(Crono.getTime());
                    view.showQuery6(produtos.size());
                    buildNavMVC(produtos);
                    break;
                }
                case "7": {
                    view.showMessage("Insira Mes: ");
                    view.showPointer();
                    int mes = Input.lerInt();
                    if(mes >12|| mes <1){view.showERROR("Mes Inválido"); break;}
                    Crono.start();
                    double[][] vendas = model.getInfoMes(mes);
                    view.showTime(Crono.getTime());
                    view.showQuery7(vendas);
                    break;
                }
                case "8":{
                    view.showMessage("Insira Cliente: ");
                    view.showPointer();
                    String c = Input.lerString();
                    c = c.toUpperCase();
                    Cliente cli = new Cliente(c);
                    if(!model.contemCliente(cli)) {view.showERROR("Cliente Inválido"); break;}
                    Crono.start();
                    double[][] query8 = model.resultadosCliente(cli);
                    view.showTime(Crono.getTime());
                    view.showQuery8(query8);
                    break;
                }
                case "9": {
                    view.showMessage("Insira Produto: ");
                    view.showPointer();
                    String p= Input.lerString();
                    p = p.toUpperCase();
                    Produto prod = new Produto(p);
                    if(!model.contemProduto(prod)) {view.showERROR("Produto Inválido"); break;}
                    Crono.start();
                    double[][] query9 = model.resultadosProduto(prod);
                    int[] clie = model.getNClientesMes(prod);
                    view.showTime(Crono.getTime());
                    view.showQuery9(query9, clie);
                    break;
                }
                case "10": {
                    view.showMessage("Insira Cliente: ");
                    view.showPointer();
                    String c = Input.lerString();
                    c = c.toUpperCase();
                    Cliente cli = new Cliente(c);
                    if(!model.contemCliente(cli)) {view.showERROR("Cliente Inválido"); break;}
                    Crono.start();
                    Set<ProdQnt>clientes = model.getProdMaisComp(cli);
                    view.showTime(Crono.getTime());
                    Q10_buildNavMVC(clientes, 10);
                    break;
                }
                case "11": {
                    view.showMessage("Insira um valor para o top N:");
                    view.showPointer();
                    int top = Input.lerInt();
                    if(top <= 0) {
                        view.showERROR("Erro. Insira um número maior do que zero.");
                        break;
                    }
                    Crono.start();
                    Map<String,Double> s = model.produtosTOPN(top);
                    int [] cliD;

                    for(Map.Entry<String,Double> e : s.entrySet()) {
                        Produto p = new Produto(e.getKey());
                        cliD = model.getNClientesMes(p);
                        double r = 0;
                        for (int i = 0; i < 12; i++) {
                            r += cliD[i];
                        }
                        s.put(e.getKey(), r);
                    }
                    view.showTime(Crono.getTime());
                    Q11_buildNavMVC(s,11);
                    break;
                }
                case "12": {
                    Crono.start();
                    String [][] topF1;
                    String [][] topF2;
                    String [][] topF3;
                    topF1 = model.getTop3BuyersFilial(1);
                    topF2 = model.getTop3BuyersFilial(2);
                    topF3 = model.getTop3BuyersFilial(3);
                    view.showTime(Crono.getTime());
                    view.showQUERY12(topF1,topF2,topF3);
                    break;
                }
                case "13": {
                    view.showMessage("Insira um valor para o top N:");
                    view.showPointer();
                    int top = Input.lerInt();
                    if(top <= 0) {
                        view.showERROR("Erro. Insira um número maior do que zero.");
                        break;
                    }
                    Crono.start();
                    Map<String,Double> res = model.clientesTOPN(top);
                    view.showTime(Crono.getTime());
                    Q11_buildNavMVC(res,13);
                    break;
                }
                case "14":{
                    view.showMessage("Insira o produto desejado: ");
                    view.showPointer();
                    String product = Input.lerString();
                    view.showMessage("Insira o top pretendido: ");
                    view.showPointer();
                    int topProd = Input.lerInt();
                    if(topProd <= 0) {
                        view.showERROR("Erro. Insira um número maior do que zero.");
                        break;
                    }
                    Produto produto1 = new Produto(product);
                    if(!model.contemProduto(produto1)) {
                        view.showERROR("Produto Inválido");
                        break;
                    }
                    Crono.start();
                    Map<String,Double> resCli = model.getClientesProduto(produto1,topProd);
                    view.showTime(Crono.getTime());
                    Q11_buildNavMVC(resCli,14);
                    break;
                }
                case "15":{
                    Crono.start();
                    Map<String, Double[][]> info = model.getFactEachProd();
                    view.showTime(Crono.getTime());
                    Q15_buildNavMVC(info);
                    break;
                }
                default: view.showERROR("Opção Invalida"); break;
            }
        }while (!opcao.equals("0"));
    }

    /**
     * Inicia a execuçao do menu e do fluxo de consulta estatistica
     */
    public void starEstatistic()
    {
        String opcao = "";
        do
        {
            view.showMenuEstatistic();
            view.showPointer();
            opcao = Input.lerString();
            opcao = opcao.toUpperCase();
            switch (opcao) {
                case "0": break;
                case "1": {
                    Crono.start();
                    int [] compras = this.model.comprasPorMes();
                    view.showTime(Crono.getTime());
                    view.showEstatistica1(compras);
                    break;
                }
                case "2": {
                    Crono.start();
                    double [] fact = this.model.getFaturacaoFilial();
                    view.showTime(Crono.getTime());
                    view.showEstatistica2(fact);
                    break;
                }
                case "3": {
                    Crono.start();
                    int [][] filiais = this.model.getClientesFilial();
                    view.showTime(Crono.getTime());
                    view.showEstatistica3(filiais);
                    break;
                }
                default: view.showERROR("Opção Invalida"); break;
            }
        }while(!opcao.equals("0"));
    }

    /**
     * Controi o MVC do navegador apartir da query 6
     * @param produtos - informacao da query6
     */
    public void buildNavMVC(Set<String> produtos)
    {
        InterNavModel navegador = new NavModel((TreeSet<String>)produtos);
        InterNavView navView = new NavView();
        InterNavController navController = new NavController();
        navController.setNavModel(navegador);
        navController.setNavView(navView);
        navController.startNav();
    }

    /**
     * Controi o MVC do navegador apartir da query 10
     * @param clientes - informacao da query10
     */
    public void Q10_buildNavMVC(Set<ProdQnt> clientes, int i)
    {
        InterNavModel navegador = new NavModel(clientes);
        InterNavView navView = new NavView();
        InterNavController navController = new NavController();
        navController.setNavModel(navegador);
        navController.setNavView(navView);
        navController.runQ10(i);
    }

    /**
     * Controi o MVC do navegador apartir da query 11,13,14
     * @param s - informacao da query11/13/14
     */
    public void Q11_buildNavMVC(Map<String,Double> s, int i)
    {
        InterNavModel navQ9 = new NavModel(s);
        InterNavView navViewQ9 = new NavView();
        InterNavController navCQ9 = new NavController();
        navCQ9.setNavModel(navQ9);
        navCQ9.setNavView(navViewQ9);
        navCQ9.runQ10(i);
    }

    /**
     * Controi o MVC do navegador apartir da query 15
     * @param info - informacao da query15
     */
    public void Q15_buildNavMVC(Map<String, Double[][]> info){

        InterNavModel modelQ15 = new NavModel((TreeMap<String, Double[][]>)info);
        InterNavView viewQ15 = new NavView();
        InterNavController controlQ15 = new NavController();
        controlQ15.setNavModel(modelQ15);
        controlQ15.setNavView(viewQ15);
        controlQ15.runQ15();
    }
}
