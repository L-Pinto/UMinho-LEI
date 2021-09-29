package SGV.Controller;

import SGV.Input;
import SGV.InterNavController;
import SGV.InterNavModel;
import SGV.InterNavView;

public class NavController implements InterNavController {
    /*--------------------------  variaveis de instancia  --------------------------*/
    /** Modelo Do Navegador */
    private InterNavModel navModel;
    /** Vista do Navegador */
    private InterNavView navView;
    /*-------------------------- gets, sets  --------------------------*/
    /** Inicializa a vista no controlador do Navegador
     * @param navModel - Modelo do Navegador*/
    public void setNavModel(InterNavModel navModel){
        this.navModel = navModel;
    }
    /** Inicializa o modelo no controlador do Navegador
     * @param navView - Vista do Navegador */
    public void setNavView(InterNavView navView) {
        this.navView = navView;
    }

    /*--------------------------Metodos Inicializao de Navegadores---------------------*/
    /** Metodo que inicializa navegador ( Query 10)*/
    public void runQ15() {
        String opcao = "";
        if (navModel.getPagNavQ15(navModel.getPaginaAtual()) != null) {
            navView.showTelaQ15(navModel.getPaginaAtual(), navModel.getMaxPagQ15(),navModel.getPagNavQ15(1));
            do {
                int atual = navModel.getPaginaAtual();
                int total = navModel.getMaxPagQ15();
                opcao = Input.lerString();
                opcao = opcao.toUpperCase();
                switch (opcao) {
                    case "0":
                        break;
                    case "1": {
                        if (atual == 1) {
                            navView.showErrorPagQ15("Impossivel Retroceder",atual,total,navModel.getPagNavQ15(atual));
                        } else {
                            navModel.setPaginaAtual(atual - 1);
                            navView.showTelaQ15(atual-1, total,navModel.getPagNavQ15(atual-1));
                        }
                        break;
                    }
                    case "2": {
                        if (atual == total) {
                            navView.showErrorPagQ15("Impossivel Avancar",atual,total,navModel.getPagNavQ15(atual));
                        } else {
                            navModel.setPaginaAtual(atual + 1);
                            navView.showTelaQ15(atual+1, total,navModel.getPagNavQ15(atual+1));
                        }
                        break;
                    }
                    case "3": {
                        navView.showNavMessage("Insira Pagina:");
                        navView.showNavPointer();
                        int tentativa = Input.lerInt();
                        if (navModel.getPagNavQ15(tentativa) == null) {
                            navView.showErrorPagQ15("Pagina invalida!",atual,total,navModel.getPagNavQ15(atual));
                        } else {
                            navModel.setPaginaAtual(tentativa);
                            navView.showTelaQ15(tentativa,total,navModel.getPagNavQ15(tentativa));
                        }
                        break;
                    }
                    case "4": {
                        navView.showNavMessage("Insira o produto:");
                        navView.showNavPointer();
                        String produto = Input.lerString();
                        produto = produto.toUpperCase();

                        int pagP = navModel.procuraProdutoQ15(produto);
                        if (pagP == 0) {
                            navView.showErrorPagQ15("Produto inexistente!",atual,total, navModel.getPagNavQ15(atual) );
                        } else {
                            navModel.setPaginaAtual(pagP);
                            navView.showTelaQ15(pagP,total,navModel.getPagNavQ15(pagP));
                        }
                        break;
                    }
                    default: {
                        navView.showErrorPagQ15("Pagina invalida!",atual,total,navModel.getPagNavQ15(atual));
                        break;
                    }
                }
            } while (!opcao.equals("0"));
        } else navView.showNavERROR("Navegador Vazio !");
    }

    /** Metodo que inicializa diversos Navegadores (Queries : 5, 6, 8 e 9)
     @param num - Numero de distincao de queries */
    public void runQ10(int num){
        String opcao = "";
        if (navModel.getPagNavQ10(navModel.getPaginaAtual()) != null) {
            navView.showTelaQ10(navModel.getPaginaAtual(), navModel.getMaxPagQ10(),navModel.getPagNavQ10(1),num);
            do {
                int atual = navModel.getPaginaAtual();
                int total = navModel.getMaxPagQ10();
                opcao = Input.lerString();
                opcao = opcao.toUpperCase();
                switch (opcao) {
                    case "0":
                        break;
                    case "1": {
                        if (atual == 1) {
                            navView.showErrorPagQ10("Impossivel Retroceder",atual,total,navModel.getPagNavQ10(atual),num);
                        } else {
                            navModel.setPaginaAtual(atual - 1);
                            navView.showTelaQ10(atual-1, total,navModel.getPagNavQ10(atual-1),num);
                        }
                        break;
                    }
                    case "2": {
                        if (atual == total) {
                            navView.showErrorPagQ10("Impossivel Avancar",atual,total,navModel.getPagNavQ10(atual),num);
                        } else {
                            navModel.setPaginaAtual(atual + 1);
                            navView.showTelaQ10(atual+1, total,navModel.getPagNavQ10(atual+1),num);
                        }
                        break;
                    }
                    case "3": {
                        navView.showNavMessage("Insira Pagina:");
                        navView.showNavPointer();
                        int tentativa = Input.lerInt();
                        if (navModel.getPagNavQ10(tentativa) == null) {
                            navView.showErrorPagQ10("Pagina invalida!",atual,total,navModel.getPagNavQ10(atual),num);
                        } else {
                            navModel.setPaginaAtual(tentativa);
                            navView.showTelaQ10(tentativa,total,navModel.getPagNavQ10(tentativa), num);
                        }
                        break;
                    }
                    case "4": {
                        if(num == 13 || num == 14) navView.showNavMessage("Insira um Cliente:");
                        else navView.showNavMessage("Insira um Produto:");
                        navView.showNavPointer();
                        String produto = Input.lerString();
                        produto = produto.toUpperCase();
                        int pagP = navModel.procuraProdutoQ10(produto);
                        if (pagP == 0) {
                            if(num == 13 || num == 14) navView.showErrorPagQ10("Cliente inexistente!",atual,total, navModel.getPagNavQ10(atual), num);
                            else navView.showErrorPagQ10("Produto inexistente!",atual,total, navModel.getPagNavQ10(atual), num);
                        } else {
                            navModel.setPaginaAtual(pagP);
                            navView.showNavegador(pagP, total);
                            navView.showPagProdQ10(navModel.getPagNavQ10(pagP), produto, num);
                            navView.showNavMenu();
                            navView.showNavPointer();
                        }
                        break;
                    }
                    default: {
                        navView.showErrorPagQ10("Pagina invalida!",atual,total,navModel.getPagNavQ10(atual), num);
                        break;
                    }
                }
            } while (!opcao.equals("0"));
        } else navView.showNavERROR("Navegador Vazio !");
    }

    /** Metodo Para Inicializar o Navegador (QUERY 1)*/
    public void startNav(){
        String opcao = "";
        if (navModel.getPagina(navModel.getPaginaAtual()) != null) {
            navView.showTela(navModel.getPaginaAtual(), navModel.getSizeTotal(),navModel.getPagina(1));
            do {
                int pagATUAL = navModel.getPaginaAtual();
                int total = navModel.getSizeTotal();
                opcao = Input.lerString();
                opcao = opcao.toUpperCase();
                switch (opcao) {
                    case "0":
                        break;
                    case "1": {
                        if (pagATUAL == 1) {
                            navView.showErrorPag("Impossivel Retroceder!",pagATUAL,total,navModel.getPagina(pagATUAL));
                        } else {
                            navModel.setPaginaAtual(pagATUAL-1);
                            navView.showTela(pagATUAL-1, total,navModel.getPagina(pagATUAL));
                        }
                        break;
                    }
                    case "2": {
                        if (pagATUAL == total) {
                            navView.showErrorPag("Impossivel Avancar!",pagATUAL,total,navModel.getPagina(pagATUAL));
                        } else {
                            navModel.setPaginaAtual(pagATUAL+1);
                            navView.showTela(pagATUAL+1, total,navModel.getPagina(pagATUAL));
                        }
                        break;
                    }
                    case "3": {
                        navView.showNavMessage("Para que pagina quer saltar");
                        navView.showNavPointer();
                        int tentativa = Input.lerInt();

                        if (navModel.getPagina(tentativa) == null) {
                            navView.showErrorPag("Pagina invalida!",pagATUAL,total,navModel.getPagina(pagATUAL));
                        } else {
                            navModel.setPaginaAtual(tentativa);
                            navView.showTela(tentativa, total,navModel.getPagina(tentativa));
                        }
                        break;
                    }
                    case "4": {
                        navView.showNavMessage("Insira o produto:");
                        navView.showNavPointer();
                        String produto = Input.lerString();
                        produto = produto.toUpperCase();
                        int pagProd = navModel.procuraProduto(produto);

                        if (pagProd == 0) {
                            navView.showErrorPag("Produto inexistente!",pagATUAL,total,navModel.getPagina(pagATUAL));
                        }
                        else {
                            navModel.setPaginaAtual(pagProd);
                            navView.showNavegador(pagProd, total);
                            navView.showPagProd(navModel.getPagina(pagProd), produto);
                            navView.showNavMenu();
                            navView.showNavPointer();
                        }
                        break;
                    }
                    default:
                        navView.showErrorPag("Pagina invalida!",pagATUAL,total,navModel.getPagina(pagATUAL));
                        break;
                }
            }
            while (!opcao.equals("0"));
        } else navView.showNavERROR("Navegador Vazio !");
    }
}
