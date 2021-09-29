package SGV.View;

import SGV.InterNavView;

import java.util.Map;
import java.util.Set;

public class NavView implements InterNavView {
    /*--------------------------  variaveis de instancia  --------------------------*/
    /** Cor Vermelha para colorir vista no terminal*/
    private String RED;
    /** Cor Amarela para colorir vista no terminal*/
    private String GREEN;
    /** Cor Ciano para colorir vista no terminal*/
    private String CYAN;
    /** Cor Ciano Destemido para colorir vista no terminal*/
    private String BCYAN;
    /** Cor do Titulo para colorir vista no terminal*/
    private String TITULO;
    /** Codigo de terminacao de cor*/
    private String RESET;

    /** Construtor Vazio */
    public NavView() {
        this.GREEN = "\033[1;33m";
        this.RED = "\033[1;31m";
        this.CYAN = "\033[1;36m";
        this.BCYAN = "\033[0;96m";
        this.TITULO = "\033[1;96m";
        this.RESET = "\033[0m";
    }

    /** Titulo do Navegador */
    public void showNavTitulo() {
        System.out.println(TITULO + "\nNavController- SISTEMA DE GESTÃO DE PAGINAS\n" + RESET);
    }

    /** Menu do Navegador */
    public void showNavMenu() {
        System.out.println(CYAN + "0 " + RESET + "Sair.");
        System.out.println(CYAN +"1 " + RESET + "<= | Saltar para pagina anterior ");
        System.out.println(CYAN +"2 " + RESET + "=> | Saltar para proxima  pagina ");
        System.out.println(CYAN +"3 " + RESET + " ? | Saltar diretamente para uma pagina ");
        System.out.println(CYAN +"4 " + RESET + " ? | Encontrar um  elemento ");
    }

    /** Mostra uma mensagem
     * @param s - Mensagem */
    public void showNavMessage(String s) {
        System.out.println(s);
    }

    /** Local de Resposta do Utilizador */
    public void showNavPointer() {
        System.out.print(CYAN + "=> " + RESET);
    }

    /** Mostra mensagem de erro
     * @param s- Mensagem de erro */
    public void showNavERROR(String s) {
        System.out.println(RED + "ERROR: " + RESET + s + "\n");
    }

    /** Cabeçalho do Navegador
     * @param pag - pagina atual
     * @param total - total de paginas */
    public void showNavegador(int pag, int total) {
        System.out.print(BCYAN+ "\n------------------------------------\n"+ RESET);
        System.out.printf(BCYAN+"           NAVEGADOR (%d/%d)     \n"+ RESET,pag, total);
        System.out.print(BCYAN+"------------------------------------\n"+ RESET);
    }

    /** Mostra produto encontrado na pagina
     * @param pagina - pagina
     * @param produto - produto */
    public void showPagProd(Set<String> pagina, String produto) {
        int i = 0;

        for(String s : pagina)
        {
            if (i == 4) {
                i = 0;
                System.out.print("\n");
            }
            if (s.equals(produto)) System.out.print(GREEN + s + RESET + "    ");
            else System.out.print(s + "    ");
            i++;
        }
        System.out.print("\n");
    }

    /** Mostra pagina do mavegador
     * @param pag- pagina */
    public void showPagina(Set<String> pag) {
        int i = 4;
        for(String s: pag)
        {
            System.out.printf("%-10s", s);
            i--;
            if(i==0){ System.out.print("\n"); i=4;}
        }
            System.out.print("\n");
    }

    /** Mostra mensagem de erro de uma pagina
     * @param s - mensagem de erro
     * @param pagAt - pagina atual
     * @param totalSize - numero total de paginas
     * @param pagina - conteudo pagina */
    public void showErrorPag(String s, int pagAt, int totalSize, Set<String> pagina) {
        showNavERROR(s);
        showTela(pagAt,totalSize,pagina);
    }

    /** Mostra tela com Cabecalho, Pagina, Menu de servicos e Local de resposta do utilizador
     * @param pagAt - pagina atual
     * @param totalSize - total de paginas
     * @param pagina - conteudo pagina */
    public void showTela(int pagAt, int totalSize, Set<String> pagina) {
        showNavegador(pagAt, totalSize);
        showPagina(pagina);
        showNavMenu();
        showNavPointer();
    }

    /** Mostra produto encontrado na pagina
     * @param pagina - conteudo pagina
     * @param produto - produto
     * @param num - numero query*/
    public void showPagProdQ10(Map<String,Double> pagina, String produto, int num) {

        if (num == 10) System.out.printf(BCYAN + "%-10s %-10s\n" + RESET, "PRODUTO","QUANTIDADE");  //query10
        else if (num == 11) System.out.printf(BCYAN + "%-10s %-10s\n" + RESET, "PRODUTO","Nº CLIENTES");    //query11
        else if (num == 13) System.out.printf(BCYAN + "%-10s %-10s\n" + RESET, "CLIENTE","Nº VENDAS"); //query13
        else  System.out.printf(BCYAN + "%-10s %-10s\n" + RESET, "CLIENTES","FATURACAO"); //query14

        for(Map.Entry<String,Double> s: pagina.entrySet())
        {
            if(num == 14) {
                if (s.getKey().equals(produto)) System.out.printf(GREEN +"%-10s %-10.2f\n" + RESET , s.getKey(), s.getValue());
                else System.out.printf("%-10s %-10.2f\n", s.getKey(),s.getValue());;
            }
            else {
                if (s.getKey().equals(produto)) System.out.printf(GREEN +"%-10s %-10.0f\n" + RESET , s.getKey(), s.getValue());
                else System.out.printf("%-10s %-10.0f\n", s.getKey(),s.getValue());;
            }
        }
        System.out.print("\n");
    }

    /** Mostra pagina do navegador
     * @param pag- conteudo pagina
     * @param num - numero query*/
    public void showPaginaQ10(Map<String,Double> pag, int num) {

        if (num == 10) System.out.printf(BCYAN + "%-10s %-10s\n" + RESET, "PRODUTO","QUANTIDADE");         //query10
        else if (num == 11) System.out.printf(BCYAN + "%-10s %-10s\n" + RESET, "PRODUTO","Nº CLIENTES");   //query11
        else if (num == 13) System.out.printf(BCYAN + "%-10s %-10s\n" + RESET, "CLIENTE","Nº VENDAS");     //query13
        else  System.out.printf(BCYAN + "%-10s %-10s\n" + RESET, "CLIENTES","FATURACAO"); //query14

        for(Map.Entry<String,Double> s: pag.entrySet()) {
            if(num == 14) System.out.printf("%-10s %-10.2f\n", s.getKey(),s.getValue()) ;
            else System.out.printf("%-10s %-10.0f\n", s.getKey(),s.getValue());
        }
        System.out.print("\n");
    }

    /** Mostra mensagem de erro
     * @param s - Mensagem de erro
     * @param pagAt - pagina atual
     * @param totalSize - numero total de paginas
     * @param pagina - conteudo pagina
     * @param num - numero query*/
    public void showErrorPagQ10(String s, int pagAt, int totalSize, Map<String,Double> pagina, int num) {
        showNavERROR(s);
        showTelaQ10(pagAt,totalSize,pagina, num);
    }

    /** Mostra tela com Cabecalho, Pagina, Menu de servicos e Local de resposta do utilizador
     * @param pagAt - pagina atual
     * @param totalSize - numero total de paginas
     * @param pagina - conteudo pagina
     * @param num - numero query */
    public void showTelaQ10(int pagAt, int totalSize,Map<String,Double>  pagina , int num) {
        showNavegador(pagAt, totalSize);
        showPaginaQ10(pagina,num);
        showNavMenu();
        showNavPointer();
    }

    /** Mostra pagina
     * @param pag - conteudo pagina */
    public void showPaginaQ15(Map<String,Double[][]> pag ){

        for(Map.Entry<String,Double[][]> s: pag.entrySet()) {
            String prod = s.getKey();
            Double [][] aux = s.getValue();

            System.out.printf("\n"+GREEN+" %-8s"+RESET, "PRODUTO - "+prod);
            System.out.printf(CYAN + "\n %-8s %8s %17s %15s\n" + RESET, "MES","FILIAL1","FILIAL2","FILIAL3" );

            for (int i =0 ; i<12; i++){
                System.out.printf(CYAN + " %-8d" + RESET,i+1);
                System.out.printf("%-14.2f %16.2f %15.2f\n",aux[i][0],aux[i][1],aux[i][2]);
            }
        }
        System.out.print("\n");
    }

    /** Mostra mensagem de erro em relacao a uma pagina
     * @param s - Mensagem
     * @param pagAt -pagina atual
     * @param totalSize - numero total de paginas
     * @param pagina - conteudo pagina*/
    public void showErrorPagQ15( String s, int pagAt, int totalSize, Map<String,Double[][]>  pagina){
        showNavERROR(s);
        showTelaQ15(pagAt,totalSize,pagina);
    }

    /** Mostra tela com Cabecalho, Pagina, Menu de servicos e Local de resposta do utilizador
     * @param pagAt - pagina atual
     * @param totalSize- total de paginas
     * @param pagina - conteudo pagina*/
    public void showTelaQ15(int pagAt, int totalSize, Map<String,Double[][]>  pagina ){
        System.out.printf(BCYAN+"\n                   NAVEGADOR (%d/%d)                   \n"+ RESET,pagAt, totalSize);
        System.out.print(BCYAN+"--------------------------------------------------------"+ RESET);
        showPaginaQ15(pagina);
        showNavMenu();
        showNavPointer();
    }
}
