package TrazAqui.View;

import TrazAqui.InterAppView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class View implements InterAppView {
    private String YELLOW;
    private String RESET;
    private String BYELLOW;
    private String TITULO;
    private String RED;
    private String BLUE;


    /**
     * Construtor vazio da classe View
     */
    public View() {
        this.YELLOW = "\033[0;33m";
        this.RESET = "\033[0m";
        this.BYELLOW = "\033[1;93m";
        this.TITULO = "\033[1;33m";
        this.RED = "\033[1;31m";
        this.BLUE = "\033[1;92m";
    }

    /**
     * Imprime o menu principal do programa
     */
    public void showMenu() {
        System.out.println(BYELLOW + "\n»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»» MENU «««««««««««««««««««««««««««««««««««" + RESET);
        System.out.println(BYELLOW + "0 " + RESET + "Sair.");
        System.out.println(BYELLOW + "1 " + RESET + "Login.");
        System.out.println(BYELLOW + "2 " + RESET + "Registo.");
        System.out.println(BYELLOW + "3 " + RESET + "Ler/gravar de/para ficheiro.");
        System.out.println(BYELLOW + "4 " + RESET + "TOP 10 utilizadores que mais utilizam o sistema.");
        System.out.println(BYELLOW + "5 " + RESET + "TOP 10 transportadoras que mais utilizam o sistema.");
    }

    /**
     * Imprime o menu do utilizador
     */
    public void showMenuUtilizador() {
        System.out.println(BYELLOW + "\n****MENU UTILIZADOR****" + RESET);
        System.out.println(BYELLOW + "0 " + RESET + "Sair.");
        System.out.println(BYELLOW + "1 " + RESET + "Gravar estado em ficheiro objeto.");
        System.out.println(BYELLOW + "2 " + RESET + "Efetuar encomenda.");
        System.out.println(BYELLOW + "3 " + RESET + "Historial encomendas.");
        System.out.println(BYELLOW + "4 " + RESET + "Classificar encomendas.");
        System.out.println(BYELLOW + "5 " + RESET + "Consultar estado da encomenda.");
    }

    /**
     * Imprime o menu da transportadora
     */
    public void showMenuTransportadora() {
        System.out.println(BYELLOW + "\n****MENU TRANSPORTADORA****" + RESET);
        System.out.println(BYELLOW + "0 " + RESET + "Sair.");
        System.out.println(BYELLOW + "1 " + RESET + "Gravar estado em ficheiro objeto.");
        System.out.println(BYELLOW + "2 " + RESET + "Alterar estado.");
        System.out.println(BYELLOW + "3 " + RESET + "Ir buscar encomenda.");
        System.out.println(BYELLOW + "4 " + RESET + "Faturação num dado período de tempo.");
        System.out.println(BYELLOW + "5 " + RESET + "Historial encomendas.");
        System.out.println(BYELLOW + "6 " + RESET + "Estado aceitação encomendas médicas.");
        System.out.println(BYELLOW + "7 " + RESET + "Consultar classificação.");
    }

    /**
     * Imprime o menu do voluntário
     */
    public void showMenuVoluntario() {
        System.out.println(BYELLOW + "\n****MENU VOLUNTARIO****" + RESET);
        System.out.println(BYELLOW + "0 " + RESET + "Sair.");
        System.out.println(BYELLOW + "1 " + RESET + "Gravar estado em ficheiro objeto.");
        System.out.println(BYELLOW + "2 " + RESET + "Alterar estado.");
        System.out.println(BYELLOW + "3 " + RESET + "Ir buscar encomenda.");
        System.out.println(BYELLOW + "4 " + RESET + "Historial encomendas.");
        System.out.println(BYELLOW + "5 " + RESET + "Estado aceitação encomendas médicas.");
        System.out.println(BYELLOW + "6 " + RESET + "Consultar classificação.");
    }

    /**
     * Imprime o menu da loja
     */
    public void showMenuLoja() {
        System.out.println(BYELLOW + "\n****MENU LOJA****" + RESET);
        System.out.println(BYELLOW + "0 " + RESET + "Sair.");
        System.out.println(BYELLOW + "1 " + RESET + "Gravar estado em ficheiro objeto.");
        System.out.println(BYELLOW + "2 " + RESET + "Encomendas para serem entregues.");
        System.out.println(BYELLOW + "3 " + RESET + "Quantidade de pessoas em fila de espera.");
        System.out.println(BYELLOW + "4 " + RESET + "Histórico de encomendas.");
    }

    /**
     * Menu do registo de um produto numa dada loja
     */
    public void menuLerProduto()
    {
        System.out.println(BYELLOW + "\n---- PRODUTOS DA LOJA ----" + RESET);
        System.out.println(BYELLOW + "0 " + RESET + "Sair");
        System.out.println(BYELLOW + "1 " + RESET + "Novo Produto");
    }

    /**
     * Método que imprime uma mensagem recebida como argumento com mudança de linha
     * @param s string a imprimir
     */
    public void showMessage(String s) {
        System.out.println(s);
    }

    /**
     * Método que imprime uma mensagem recebida como argumento sem mudança de linha
     * @param s string a imprimir
     */
    public void showM(String s) {
        System.out.print(s);
    }

    /**
     * Imprime os menu dos registos
     */
    public void showRegistos() {
        System.out.println(BYELLOW + "\n****ENTIDADES****" + RESET);
        System.out.println(BYELLOW + "0 " + RESET + "Sair.");
        System.out.println(BYELLOW + "1 " + RESET + "Utilizador.");
        System.out.println(BYELLOW + "2 " + RESET + "Voluntario.");
        System.out.println(BYELLOW + "3 " + RESET + "Transportadora.");
        System.out.println(BYELLOW + "4 " + RESET + "Loja.");
    }

    /**
     * Imprime o pointer para receber input do utilizador
     */
    public void showPointer(){
        System.out.print(BYELLOW + "=> " + RESET);
    }

    /**
     * Menu para ler ou gravar para ficheiro objeto
     */
    public void showMenuLG() {
        System.out.println(BYELLOW + "\n****LER/GRAVAR FICHEIRO****" + RESET);
        System.out.println(BYELLOW + "0 " + RESET + "Sair.");
        System.out.println(BYELLOW + "1 " + RESET + "Gravar.");
        System.out.println(BYELLOW + "2 " + RESET + "Ler.");
    }

    /**
     * Menu que imprime uma lista de String, por exemplo, as lojas onde o utilizador pode comprar
     * @param lojas Lista de String
     */
    public void showLojas(List<String> lojas) {
        int i = 0, j = 1;

        for(String l : lojas) {
            if (i == 2) {
                System.out.print("\n");
                System.out.print(BYELLOW + j + " " + RESET);
                System.out.printf("%-25s",l);
                i=0;
            } else {
                System.out.print(BYELLOW + j + " " + RESET);
                System.out.printf("%-25s",l);
            }

            i++;
            j++;
        }

        System.out.print("\n");
    }

    /**
     * Método que imprime os produtos e o seu respetivo preço
     * @param prod map com os produtos e o respetivo preço
     */
    public void showProd(Map<String,Double> prod) {
        int i = 0, j = 1;

        if (prod.size() >= 2) {
            System.out.printf(BYELLOW + "\n%9s    %-25s %7s    %-25s\n" + RESET, "PRECO", "PRODUTO", "PRECO", "PRODUTO");
        }

        for(Map.Entry<String,Double> m : prod.entrySet()) {
            if (i == 2) {
                System.out.printf(BYELLOW + "\n%-3d" + " " + RESET,j);
                System.out.printf("€%-6.2f %-25s",m.getValue(),m.getKey());
                i=0;
            } else {
                System.out.printf(BYELLOW + "%-3d" + " " + RESET,j);
                System.out.printf("€%-6.2f %-25s",m.getValue(),m.getKey());
            }

            j++;
            i++;
        }

        if (i!=0) System.out.print("\n");
    }

    /**
     * Imprime o menu para registar uma nova linha de encomenda
     */
    public void menuRegistarEnc() {
        System.out.println(BYELLOW + "\n****NOVA LINHA ENCOMENDA****" + RESET);
        System.out.println(BYELLOW + "0 " + RESET + "Encomenda terminada.");
        System.out.println(BYELLOW + "1 " + RESET + "Registar um produto.");
    }

    /**
     * Método que imprime informações relativas a uma encomenda
     * @param vol entidade de transporte
     * @param loja loja onde foi efetuada a encomenda
     * @param user código do utilizador
     * @param enc encomenda em causa
     * @param tempo tempo que demora a entrega
     * @param preco preço da encomenda
     * @param peso peso da encomenda
     */
    public void showInfoEncVol(String vol, String loja, String user, String enc, double tempo, double preco,double peso) {
        System.out.println(BYELLOW + "\n INFORMAÇÃO DA ENCOMENDA " + RESET);
        System.out.println(YELLOW + "Encomenda: " + RESET+ enc);
        System.out.println(YELLOW + "Utilizador: " + RESET + user);
        System.out.println(YELLOW + "Loja:  " + RESET + loja);
        System.out.println(YELLOW + "Entidade de transporte:  " + RESET + vol);
        System.out.printf(YELLOW + "Tempo: " + RESET + "%.0fh:%.0fmin\n", tempo/60, tempo%60);
        System.out.printf(YELLOW + "Peso Encomenda:  " + RESET + "%.2f kg ", peso);
        System.out.printf(YELLOW + "\nPreço Encomenda:  " + RESET + "%.2f € \n\n" , preco);
    }

    /**
     * Método que imprime informações sobre uma encomenda de transportadora
     * @param vol entidade de transporte(transportadora)
     * @param preco preço da encomenda
     * @param taxa taxa de transporte
     */
    public void showInfoEncT(String vol, double preco,double taxa) {
        System.out.println(BYELLOW + "\n INFORMAÇÃO DA ENCOMENDA " + RESET);
        System.out.println(YELLOW + "Entidade de transporte:  " + RESET + vol);
        System.out.printf(YELLOW + "Preço Encomenda:  " + RESET + "%.2f €\n" , preco);
        System.out.printf(YELLOW + "Preço total encomenda:  " + RESET + "%.2f €\n" , preco+taxa);
        System.out.printf(YELLOW + "\nTAXA DE TRANSPORTE: "  + RESET + "%.2f € \n\n" ,taxa);
        System.out.println("Aceitar encomenda" + BYELLOW + " [Y/N] "  + RESET );
        showPointer();
    }

    /**
     * Imprime mensagem de erro recebida como argumento
     * @param s string a imprimir
     */
    public void showERROR(String s) {
        System.out.printf(RED + "\nERROR: %s\n", s );
    }

    /**
     * Método que imprime o estado do voluntario
     * @param estado booleano (true - livre, false - ocupado)
     */
    public void showEstadoVol(boolean estado) {
        if (estado) {
            System.out.println(YELLOW + "ESTADO: " + BYELLOW + "(1) LIVRE  (2)" + RESET + " OCUPADO" + RESET );
        } else {
            System.out.println(YELLOW + "ESTADO: " + BYELLOW + "(1)" + RESET + " LIVRE" + BYELLOW + "  (2) OCUPADO" + RESET );
        }
    }

    /**
     * Método que imprime o tamanho da fila de espera de uma loja
     * @param tamanhoFila tamanho da fila de espera
     */
    public void showTamanhoFilaEspera(int tamanhoFila){
        if (tamanhoFila == 0) System.out.println("Não existe informação sobre fila de espera !");
        else System.out.printf("Quantidade de pessoas em fila para serem atendidas : %d\n", tamanhoFila);

    }

    /**
     * Imprime uma string em caso de sucesso
     * @param s string a imprimir
     */
    public void showSucesso(String s) {
        System.out.println(BYELLOW + s);
    }

    /**
     * Método que imprime uma encomenda e o seu respetivo estado atual
     * @param info map com as encomendas e respetivo estado
     */
    public void showInfoEncUlt(Map<String,String> info)
    {
        if(info.size()!=0)
        {
            System.out.printf(BYELLOW + "\n%-9s    %-25s" + RESET, "ENCOMENDA", "ESTADO");
            for(Map.Entry<String,String> m : info.entrySet()) {
                System.out.printf("\n%-9s    %-25s",m.getKey(),m.getValue());
            }
        }
        else System.out.println("Todas as encomendas foram entreges");
        System.out.print("\n");
    }
    /**
     * Método que apresenta a faturação ao utilizador
     * @param fact faturação
     */
    public void showFaturacao(double fact) {
        System.out.printf("A faturação da transportadora no período indicado foi: " + BYELLOW + "%.2f € \n" + RESET, fact);
    }

    /**
     * Método que imprime menu do histórico
     */
    public void showMenuHistorico()
    {
        System.out.println(BYELLOW + "\n**** HISTORICO ****" + RESET);
        System.out.println(YELLOW + "0 " + RESET + "Sair.");
        System.out.println(YELLOW + "1 " + RESET + "Por período.");
        System.out.println(YELLOW + "2 " + RESET + "Por Voluntario/Transportadora.");
        System.out.println(YELLOW + "3 " + RESET + "Todas as encomendas realizadas.");
    }

    /**
     * Método que imprime o menu do historico dos voluntários e transportadoras
     */
    public void showMenuHistoricoVT()
    {
        System.out.println(BYELLOW + "\n**** HISTORICO ****" + RESET);
        System.out.println(YELLOW + "0 " + RESET + "Sair.");
        System.out.println(YELLOW + "1 " + RESET + "Por período.");
        System.out.println(YELLOW + "2 " + RESET + "Todas as encomendas realizadas.");
    }

    /**
     * Método que imprime as encomendas com a respetiva data
     * @param encs a imprimir
     */
    public void showEncPeriodo(Map<String, LocalDate> encs)
    {
        if(encs.size()==0) showERROR("Sem encomendas encontradas");
        else {
            System.out.printf(BYELLOW + "\n%-9s    %-25s" + RESET, "ENCOMENDA", "DATA");

            for (Map.Entry<String, LocalDate> m : encs.entrySet()) {
                System.out.printf("\n%-9s    %-25s", m.getKey(), m.getValue());
            }
            System.out.print("\n");
        }
    }

    /**
     * Método que imprime o menu
     * @param s string a imprimir
     */
    public void showMenuPeriodo(String s) {
        System.out.printf(YELLOW + "\n%s\n" + RESET, s);
        showMessage("Insira um Mes: ");
    }

    /**
     * Método que imprime o top 10 de utilizadores
     * @param top10Ut map com o utilizador e o seu número de encomendas
     */
    public void showTop10Utilizadores(Map<Integer, String> top10Ut){
        System.out.println(BYELLOW + "\nTOP10 UTILIZADORES" + RESET);
        if (top10Ut.size() == 0) System.out.println("Nao existe top 10 no momento");
        else {
            int top = 0;
            System.out.println(YELLOW + "\nRANKING - Utilizador  Encomendas"+ RESET);
            for (Map.Entry<Integer, String> aux : top10Ut.entrySet()) {
                String user = aux.getValue();
                int nrEncs = aux.getKey();
                top++;
                System.out.printf( YELLOW + "%4dº" + RESET + "  %9s  %9d\n", top, user, nrEncs);
                if (top == 10) break;
            }
        }
    }

    /**
     * Método que imprime o top 10 de transportadoras
     * @param top10T transportadora com o respetivo número de kms feitos
     */
    public void showTop10Transportadoras(Map<Double, String> top10T){
        System.out.println(BYELLOW + "\nTOP10 TRANSPORTADORAS" + RESET);
        if (top10T.size() == 0) System.out.println("Nao existe top 10 no momento");
        else {
            int top = 0;
            System.out.println(YELLOW + "\nRANKING - Transportador Kilometros"+ RESET);
            for (Map.Entry<Double, String> aux : top10T.entrySet()) {
                String user = aux.getValue();
                double nrKms = aux.getKey();
                top++;
                System.out.printf( YELLOW + "%4dº" + RESET + "  %10s  %12.2f\n", top, user, nrKms);
                if (top == 10) break;
            }
        }
    }

    /**
     * Método que imprime a classificação referente a cada encomenda
     * @param clas map com a string(encomenda) e a respetiva classificação
     */
    public void showClassificacao(Map<String, Integer> clas) {
        if(clas.size()==0) showERROR("Ainda nao tem classificações!");
        else {
            System.out.printf(BYELLOW + "\n%-9s    %-25s" + RESET, "ENCOMENDA", "CLASSIFICACAO");

            for (Map.Entry<String, Integer> m : clas.entrySet()) {
                System.out.printf("\n%-9s    %-25s", m.getKey(), m.getValue());
            }
            System.out.print("\n");
        }
    }

    /**
     * Método que imprime uma lista de string (encomendas)
     * @param enc lista com os códigos de encomenda
     */
    public void showEncProcess(List<String> enc)
    {
        int j = 1;

        if(enc.size() > 0) System.out.println(BYELLOW + "\n** ENCOMENDAS **" + RESET);

        for(String l : enc)
        {
            System.out.print(YELLOW + j + " " + RESET);
            System.out.printf("%-25s\n",l);
            j++;
        }
    }

    /**
     * Método que imprime o título e averigua se se trata de uma encomenda médica
     */
    public void showCreatEncomenda()
    {
        System.out.println(BYELLOW + "\n     ** NOVA ENCOMENDA **        \n" + RESET);
        System.out.println("ENCOMENDA MÉDICA " + YELLOW + "[Y/N]" + RESET);
    }

    /**
     * Método que imprime o título para as lojas
     */
    public void showTituloL() {
        System.out.println(BYELLOW + "\n     ---------- LOJAS DISPONIVEIS ---------        " + RESET);
    }
}
