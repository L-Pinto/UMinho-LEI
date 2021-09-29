package SGV;

public interface InterGestView
{
    void showMenu();
    void showMessage(String s);
    void showTitulo();
    void showPointer();
    void showERROR(String s);
    void showMessageValido(String s);
    void showMessageYN(String s);
    void showInfoVendas(String nome, int errados, int produtos, int proddif, int naoprod, int clie, int compclie, int naoclie, int zero, double fact);
    void showMenuEstatistic();
    void showEstatistica1(int [] vendas);
    void showEstatistica2(double [] faturacao);
    void showEstatistica3(int [][] filiais);
    void showQuery6(int tamanho);
    void showQuery8(double[][] clie);
    void showQuery9(double[][] prod, int[] cli);
    void showQuery7(double[][] vendas);
    void showQUERY12(String[][] filial1, String[][] filial2, String[][] filial3);
    void showTime(String s);
}
