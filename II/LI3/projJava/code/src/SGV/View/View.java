package SGV.View;

import SGV.InterGestView;

public class View implements InterGestView
{
    private String CYAN;
    private String RESET;
    private String BCYAN;
    private String TITULO;
    private String RED;
    private String GREEN;

    /**
     * Contrutor vazio da View - Inicializa as cores
     */
    public View()
    {
        this.CYAN = "\033[1;36m";
        this.RESET = "\033[0m";
        this.BCYAN = "\033[0;96m";
        this.TITULO = "\033[1;96m";
        this.RED = "\033[1;31m";
        this.GREEN = "\033[1;33m";
    }

    /**
     * Mostra o menu do programa
     */
    public void showMenu()
    {
        System.out.println(BCYAN + "\n»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»» MENU «««««««««««««««««««««««««««««««««" + RESET);
        System.out.println(CYAN +"0 " + RESET + "Sair.");
        System.out.println(CYAN +"1 " + RESET + "Leitura e tratamento de novos dados.");
        System.out.println(CYAN +"2 " + RESET + "Guardar dados do sistema em ficheiro objeto.");
        System.out.println(CYAN +"3 " + RESET + "Ler dados de um ficheiro objeto para sistema.");
        System.out.println(BCYAN + "»»»»»»»»»»»»»»»»»»»»»»»»»»»»» CONSULTAS ESTATISTICAS «««««««««««««««««««««««" + RESET);
        System.out.println(CYAN +"4 " + RESET + "Informacao sobre o ficheiro de vendas.");
        System.out.println(CYAN +"5 " + RESET + "Consulta de dados sobre os ficheiros.");
        System.out.println(BCYAN + "»»»»»»»»»»»»»»»»»»»»»»»»»»»»» CONSULTAS INTERATIVAS ««««««««««««««««««««««««" + RESET);
        System.out.println(CYAN +"6 " + RESET + "Lista ordenada dos produtos que nunca foram comprados.");
        System.out.println(CYAN +"7 " + RESET + "Informacao sobre as vendas e compras feitas num dado mes.");
        System.out.println(CYAN +"8 " + RESET + "Informacao sobre as compras de um dado cliente em cada mes.");
        System.out.println(CYAN +"9 " + RESET + "Informacao sobre as vendas de um dado produto em cada mes.");
        System.out.println(CYAN +"10 " + RESET + "Lista dos produtos mais comprados por um cliente.");
        System.out.println(CYAN +"11 " + RESET + "Lista dos N produtos mais vendidos.");
        System.out.println(CYAN +"12 " + RESET + "Lista dos 3 maiores compradores para cada filial.");
        System.out.println(CYAN +"13 " + RESET + "Lista dos N clientes que mais produtos compraram.");
        System.out.println(CYAN +"14 " + RESET + "Lista dos N clientes que mais compraram um determinado produto.");
        System.out.println(CYAN +"15 " + RESET + "Faturacao Total para cada mes e em cada filial de cada produto.");
    }

    /**
     * Mostra o menu da consulta estatistica
     */
    public void showMenuEstatistic()
    {
        System.out.println(CYAN + "\n***** CONSULTAS ESTATISTICAS *****" + RESET);
        System.out.println(CYAN +"0 " + RESET + "Sair");
        System.out.println(CYAN +"1 " + RESET + "Nº total de compras por mes.");
        System.out.println(CYAN +"2 " + RESET + "Faturacao total por mes.");
        System.out.println(CYAN +"3 " + RESET + "Nº total de clientes que compraram em cada mes.");
    }

    /**
     * Mostra uma mensagem
     * @param s - Mensagem a amostrar
     */
    public void showMessage(String s) { System.out.println(s); }


    /**
     * Mostra uma mensagem de Erro
     * @param s - Mensagem a amostrar
     */
    public void showERROR(String s)
    {
        System.out.println(RED + "ERROR: " + RESET + s + "\n");
    }

    /**
     * Mostra o titulo do programa
     */
    public void showTitulo()
    {
        System.out.println(TITULO + "\nSGV - SISTEMA DE GESTÃO DE VENDAS" + RESET);
    }

    /**
     * Mostra o apontador para o utilizador
     */
    public void showPointer()
    {
        System.out.print(CYAN + "=> " + RESET);
    }

    /**
     * Mostra a informaçao para o tempo da cada query ao utilizador
     */
    public void showTime(String s)
    {
        System.out.println(BCYAN + "\nTIME: " + RESET + s + " segundos");
    }

    /** ------------------- QUERIES ------------------------------- */

    /**
     * Mostra uma mensagem válida ao utilizador
     */
    public void showMessageValido(String s)
    {
        System.out.println( GREEN + s +  RESET +"\n");
    }

    /**
     * Mostra mensagem sim e nao para o utilizador
     */
    public void showMessageYN(String s)
    {
        System.out.println(s + BCYAN + "[Y/N]" + RESET);
    }

    /**
     * Mostra a informaçao para a query 1
     * @param nome - nome do ficheiro de vendas
     * @param errados - nº de vendas erradas
     * @param nrProd - nº de produtos totais
     * @param prodDif - nº de produtos comprados
     * @param prodNC - nº de produtos nunca comprados
     * @param totC - nº total de clientes
     * @param cliC - nº de clientes totais
     * @param cliNC - nº de clientes que nunca compraram
     * @param nvZero - nº de vendas com faturacao a zero
     * @param factT - nº de faturaçao total
     */
    public void showInfoVendas(String nome, int errados, int nrProd, int prodDif,int prodNC, int totC, int cliC, int cliNC, int nvZero, double factT) {
        System.out.println(CYAN + "\nFicheiro de vendas lido: " + RESET + nome);
        System.out.println(CYAN + "Nº total de registo de vendas erradas: " + RESET + errados);
        System.out.println(CYAN + "Nº total de produtos: " + RESET + nrProd);
        System.out.println(CYAN + "Nº total de produtos diferentes comprados: " + RESET + prodDif);
        System.out.println(CYAN + "Nº total de produtos nunca comprados: " + RESET + prodNC);
        System.out.println(CYAN + "Nº total de clientes: " + RESET + totC);
        System.out.println(CYAN + "Nº total de clientes que efetuaram compras: " + RESET + cliC);
        System.out.println(CYAN + "Nº total de clientes que nunca efetuaram compras: " + RESET + cliNC);
        System.out.println(CYAN + "Nº total de vendas com faturacao zero: " + RESET + nvZero);
        System.out.println(CYAN + "Faturaçao total: " + RESET + factT + "\n");
    }

    /**
     * Mostra informacao da consulta estatistica 1
     * @param vendas - número de vendas para cada mes
     */
    public void showEstatistica1(int [] vendas)
    {
        System.out.printf(CYAN+ "\nMES   %7s\n" + RESET, "VENDAS");

        for(int i = 0; i < 12; i++) {
            System.out.printf(CYAN + "%-6d" + RESET + "%6d\n",i+1,vendas[i]);

        }
        System.out.println();
    }

    /**
     * Mostra informacao da consulta estatistica 2
     * @param faturacao - faturacao para cada mes
     */
    public void showEstatistica2(double [] faturacao)
    {
        System.out.println(CYAN + "\nMES         F1              F2               F3               FT" + RESET);

        for(int i = 0; i < 12; i++) {
            double total = faturacao[i]+faturacao[i+12]+faturacao[i+24];
            System.out.printf(CYAN + "%-6d" + RESET + "%-16.2f %-16.2f %-16.2f %-16.2f\n", i+1,faturacao[i],faturacao[i+12],faturacao[i+24],total);
        }
    }

    /**
     * Mostra informaçao da consulta estatistica 3
     * @param filiais - número de clientes que compraram em cada filial por mes
     */
    public void showEstatistica3(int [][] filiais)
    {
        System.out.println(CYAN + "\nMES     F1        F2        F3" + RESET);

        for(int i = 0; i < 12; i++) {
            System.out.printf(CYAN + "%-6d" + RESET + "%-9d %-9d %-9d\n",i+1,filiais[0][i],filiais[1][i],filiais[2][i]);
        }

        System.out.print("\n");
    }

    /**
     * Mostra informaçao da query6
     * @param tamanho - nº de produtos que nunca foram comprados
     */
    public void showQuery6(int tamanho)
    {
        System.out.printf("\nNúmero de produtos nunca comprados: %d\n", tamanho);
    }

    /**
     * Mostra informacao para a query8
     * @param clie - nº de vendas, produtos e faturacao de um cliente para cada mes
     */
    public void showQuery8(double[][] clie)
    {
        int j;
        System.out.printf(CYAN + "\n%-8s%-10s %10s %10s\n" + RESET, "MES","FATURACAO","VENDAS","PRODUTOS" );
        for(j = 0; j<12; j++)
        {
            System.out.printf(CYAN + "%-8d" + RESET,j+1);
            System.out.printf("%-10.2f %10.0f %10.0f\n",clie[j][2],clie[j][0],clie[j][1]);
        }
    }

    /**
     * Mostra informacao para a query9
     * @param query9 - nº de vendas e faturacao de um produto para cada mes
     * @param cli - nº de clientes que compraram um determinado produto em cada mes
     */
    public void showQuery9(double[][] query9, int[] cli)
    {
        System.out.printf(CYAN + "\n%-8s%-10s %12s %11s\n" + RESET, "MES","FATURACAO","VENDAS","CLIENTES");
        double total0=0,total1=0,total2 = 0;
        for (int i=0;i<12;i++){
            total2 += query9[i][1];
            total1 += query9[i][0];
            total0 += cli[i];
        }
        for(int j = 0; j<12; j++)
        {
            System.out.printf(CYAN + "%-8d" + RESET,j+1);
            System.out.printf("%-10.2f %10.0f %10d\n",query9[j][1],query9[j][0],cli[j]);
        }
        System.out.printf(CYAN + "%-8s" + RESET,"TOTAL");
        System.out.printf("%-10.2f %10.0f %10.0f\n",total2,total0,total1);

    }

    /**
     * Mostra informação da query7
     * @param vendas - nº de vendas e clientes para cada mes e para cada filial
     */
    public void showQuery7(double[][] vendas)
    {
        int j;
        double totalV = 0;
        double totalC = 0;
        System.out.printf(CYAN + "\n%-8s%-10s %-10s\n" + RESET, "FILIAL","VENDAS","CLIENTES");
        for(j = 0; j<3; j++)
        {
            totalV = totalV + vendas[j][0];
            totalC = totalC + vendas[j][1];
            System.out.printf(CYAN + "%-8d" + RESET,j+1);
            System.out.printf("%-10.0f %-10.0f\n",vendas[j][0],vendas[j][1]);
        }
        System.out.printf(BCYAN + "%-8s%-10.0f %-10.0f\n" + RESET, "TOTAL",totalV,totalC);
    }

    /**
     * Mostra informaçao para a querr12
     * @param filial1 - top dos 3 clientes que mais compraram na filial 1
     * @param filial2 - top dos 3 clientes que mais compraram na filial 2
     * @param filial3 - top dos 3 clientes que mais compraram na filial 3
     */
    public void showQUERY12(String[][] filial1, String[][] filial2, String[][] filial3)
    {
        System.out.printf(BCYAN + "\n%-8s %16s %16s %16s\n" + RESET, "TOP3","FILIAL1","FILIAL2","FILIAL3");

        for(int j = 0; j<3; j++) //linha a linha
        {
            double tf1,tf2,tf3;
            tf1 =Double.parseDouble(filial1[j][1]);
            tf2 =Double.parseDouble(filial2[j][1]);
            tf3 =Double.parseDouble(filial3[j][1]);
            System.out.printf(BCYAN + "%-8s" + RESET, (j+1)+"º CLIENTE");
            System.out.printf(CYAN + "%14s %16s %16s\n" + RESET,filial1[j][0], filial2[j][0], filial3[j][0]);
            System.out.printf(CYAN + "%-8s" + RESET, "FATURADO");
            System.out.printf("%16.2f %16.2f %16.2f\n",tf1,tf2,tf3 );
        }
    }
}
