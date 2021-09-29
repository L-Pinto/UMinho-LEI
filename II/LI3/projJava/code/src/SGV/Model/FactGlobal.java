package SGV.Model;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;


public class FactGlobal implements Serializable, InterFactGlobal
{
    /*** faturacao para cada produto*/
    private Map<Produto, ValueFact> faturacao;
    /** nº de vendas lidas do ficheiro*/
    private int lidas;
    /*** nº de vendas validas do ficheiro*/
    private int validas;
    /*** nº de vendas vcom faturacao zero*/
    private int gratis;
    /*** faturacao total com as vendas feitas*/
    private double factT;

    /**
     * Construtor vazio
     */
    public FactGlobal() {
        this.faturacao = new TreeMap<>();
        this.lidas = 0;
        this.validas = 0;
        this.gratis = 0;
        this.factT = 0.0;
    }

    /**
     * Limpa o conteudo da classe
     */
    public void limpaFaturacao() {
        this.faturacao.clear();
        this.lidas = 0;
        this.validas = 0;
        this.gratis = 0;
        this.factT = 0.0;
    }

    /** ----- GETS E SETS ------ */


     /** Devolve o nº de vendas validas
     * @return nº de vendas validas
     */
    public int getValidas() {
        return validas;
    }

    /** Devolve o nº de vendas lidas
     * @return nº de vendas lidas
     */
    public int getLidas() {
        return lidas;
    }

    /**
     * Atualiza a faturacao total
     * @param factT - faturacao total
     */
    public void setFactT(double factT) {
        this.factT = factT;
    }

    /**
     * Atualiza o nº de vendas com faturacao a zero
     * @param gratis - nº de vendas com faturacao a zero
     */
    public void setGratis(int gratis) {
        this.gratis = gratis;
    }

    /**
     * Atualiza o nº de vendas lidas
     * @param lidas -  nº de vendas lidas
     */
    public void setLidas(int lidas) {
        this.lidas = lidas;
    }

    /**
     * Atualiza o nº de vendas validas
     * @param validas - nº de vendas validas
     */
    public void setValidas(int validas) {
        this.validas = validas;
    }

    /**
     * Devolve o nº de vendas com faturacao a zero
     * @return nº de vendas com faturacao a zero
     */
    public int getGratis() {
        return gratis;
    }

    /**
     * Devolve a faturacao total de todas as vendas
     * @return - faturacao total
     */
    public double getFactT() {
        return factT;
    }

    /**
     * Insere uma nova venda na faturacao
     * @param venda - Array de String com os parametros da venda divididos
     */
    public void insereVenda(String[] venda) {
        String prod = venda[0];
        Produto produto = new Produto(prod);
        double preco = Double.parseDouble(venda[1]);
        double qnt = Double.parseDouble(venda[2]);
        int mes = Integer.parseInt(venda[5]);
        int filial = Integer.parseInt(venda[6]);

        if (this.faturacao.containsKey(produto)) {
            this.faturacao.get(produto).atualizaVFact(preco, qnt, mes, filial);
        } else {
            ValueFact vf = new ValueFact();
            vf.atualizaVFact(preco, qnt, mes, filial);
            this.faturacao.put(produto, vf); //FAZER CLONE
        }
    }

    /** ----------- QUERIES -----------------*/

    /**
     * Informacao para a consulta estatistica
     * @return - nº de vendas erradas
     */
    public int getErradas() {
        return lidas - validas;
    }

    /**
     * Informacao para a consulta estatistica
     * @return - nº de produtos que foram comprados
     */
    public int getSize() {
        return faturacao.size();
    }

    /**
     * Informacao para a consulta estatistica
     * @return - nº de vendas para cada mes
     */
    public int[] vendasMes() {
        int[] vendas = new int[12];
        int i = 0;

        for (ValueFact v : this.faturacao.values()) {
            for (i = 0; i < 12; i++) {
                vendas[i] += v.vendasPorMes(i + 1);
            }
        }
        return vendas;
    }

    /**
     * Informacao para a consulta estatistica
     * @return - faturacao total para cada mes em cada filial
     */
    public double[] getFactFilial() {
        double[] fatTot = new double[36];
        for (ValueFact vf : faturacao.values()) {
            for (int i = 0; i < 12; i++) {
                fatTot[i] += vf.getFactVF(1, i + 1);     //filial 1
                fatTot[i + 12] += vf.getFactVF(2, i + 1); //filial 2
                fatTot[i + 24] += vf.getFactVF(3, i + 1); //filial 3
            }
        }
        return fatTot;
    }

    /**
     * Informacao para a query9
     * @param produto - Produto a determinar informaçao
     * @return - nº de vendas e o total faturado por um determinado produto em cada mes
     */
    public double[][] verificaProduto(Produto produto) {
        double[][] res = new double[12][2];
        int i;

        for (i = 0; i < 12; i++) {
            for (int j = 0; j < 2; j++) {
                res[i][j] = 0;
            }
        }
        ValueFact vf = this.faturacao.get(produto);
        vf.verificaMeses(res);

        return res;
    }

    /**
     * Informaçao oara a query15
     * @return - Para cada produto a faturacao associada a cada filial e a cada mes
     */
    public Map<String, Double[][]> factEachProd()
    {
        Map<String, Double[][]> res = new TreeMap<>();
        Double[][] aux = new Double[12][3];

        for (int i = 0; i < 12; i++)
        {
            aux[i][0] = 0.0; //filial1
            aux[i][1] = 0.0; // "" 2
            aux[i][2] = 0.0; // "" 3
        }

        for (Map.Entry<Produto, ValueFact> v : faturacao.entrySet()) {
            String prod = v.getKey().getProduto();
            v.getValue().atualizaFactProd(aux);
            res.put(prod, aux);
        }

        return res;
    }

    /** --------------- OUTROS -------------*/

    /**
     * Informacao para testes debug
     * @return - Classe convertida para uma string
     */
    public String toString() {
        return "\nFactGlobal{\n" +
                "faturacao =" + faturacao +
                ", lidas =" + lidas +
                '}';
    }


}
