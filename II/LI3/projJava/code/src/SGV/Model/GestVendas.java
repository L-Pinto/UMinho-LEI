package SGV.Model;
import SGV.InterGestModel;

import java.io.*;
import java.util.*;

public class GestVendas implements InterGestModel , Serializable {
    private InterCatClientes catC;
    private InterCatProdutos catP;
    private InterFactGlobal fact;
    private InterGestFilial gest;
    private String fileVendas;

    /**
     * Construtor vazio da classe GestVendas. Inicializa as variáveis de instância.
     */
    public GestVendas() {
        this.catC = new CatClientes();
        this.catP = new CatProdutos();
        this.fact = new FactGlobal();
        this.gest = new GestFilial();
        this.fileVendas = "";
    }
    
    /**
     * Método que efetua a leitura dos ficheiros, preenchendo as variáveis de instância da classe.
     * @param clientes caminho do ficheiro de clientes.
     * @param produtos caminho do ficheiro de produtos.
     * @param vendas caminho do ficheiro de vendas.
     * @return 0 em caso de sucesso, maior do que 0 em caso de insucesso na leitura.
     */
    public int leituraGestVendas(String clientes, String produtos, String vendas) {
        Leitura l = new Leitura();
        int res = 0;

        res += l.leituraClientes(clientes, catC);
        res += l.leituraProdutos(produtos, catP);
        res += l.leituraVendas(vendas, catP, catC, gest, fact);

        setFileVendas(vendas);
        
        return res;
    }

    /**
     * Método que retorna o caminho do último ficheiro de vendas lido.
     * @return caminho do ficheiro.
     */
    public String getFileVendas() {
        return fileVendas;
    }

    /**
     * Método que atualiza o caminho do ficheiro de vendas.
     * @param fileVendas caminho do ficheiro.
     */
    public void setFileVendas(String fileVendas) {
        this.fileVendas = fileVendas;
    }

    /**
     * Método que guarda as informações desta classe num ficheiro objeto.
     * @param filename nome do ficheiro.
     * @throws IOException exceção.
     */
    public void gravarObj(String filename) throws IOException {
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(filename));
        o.writeObject(this);
        o.flush();
        o.close();
    }

    /**
     * Método que lê dum ficheiro objeto e preenche esta classe com as informações lidas.
     * @param filename nome do ficheiro a ser lido.
     * @return estrutura preenchida.
     * @throws IOException exceção.
     * @throws ClassNotFoundException exceção.
     */
    public GestVendas lerObj(String filename) throws IOException , ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream(new FileInputStream(filename));
        GestVendas g = (GestVendas) o.readObject();
        o.close();
        return g;
    }

    /**
     * Método que retorna o número de vendas válidas.
     * @return vendas válidas.
     */
    public int vendasValidas() {
        return fact.getValidas();
    }

    /**
     * Método que retorna o número de vendas lidas.
     * @return vendas lidas.
     */
    public int vendasLidas() {
        return fact.getLidas();
    }

    /**
     * Método que transforma esta classe numa String.
     * @return String da classe.
     */
    public String toString() {
        return "GestVendas{" +
                "catC=" + catC +
                ", catP=" + catP +
                ", fact=" + fact +
                ", gest=" + gest +
                '}';
    }

    /**
     * Retorna uma matriz (meses e filial) com o nº de clientes distintos que compraram a cada mês.
     * @return matriz.
     */
    public int [][] getClientesFilial() {
        int [][] filiais;
        filiais = this.gest.getClientesFilial();

        return filiais;
    }

    /**
     * Retorna o número de clientes que não compraram.
     * @return nr de clientes que não compraram.
     */
    public int getCliNC() {
        return this.catC.getNaoCompraram();
    }

    /**
     * Retorna o número de clientes que compraram.
     * @return nr de clientes que compraram.
     */
    public int getCliC() {
        return this.catC.getCompraram();
    }

    /**
     * Calcula o número de produtos nunca comprados.
     * @return número de produtos nunca comprados.
     */
    public int getProdNC() {
        return (this.catP.getSize() - getProdDif());
    }

    /**
     * Método que calcula o número de produtos diferentes comprados.
     * @return o número de produtos diferentes comprados.
     */
    public int getProdDif() {
        return fact.getSize();
    }

    /**
     * Calcula o número de vendas lidas erradas.
     * @return número de vendas erradas.
     */
    public int getVendasErradas() {
        return fact.getErradas();
    }

    /**
     * Calcula o número de vendas grátis.
     * @return o número de vendas grátis.
     */
    public int getVendasGratis() {
        return fact.getGratis();
    }

    /**
     * Método que retorna a faturação total.
     * @return faturação total.
     */
    public double getFactTotal() {
        return fact.getFactT();
    }

    /**
     * Método que calcula o número de vendas por mês.
     * @return array com 12 entradas(para cada mês) com o número de vendas.
     */
    public int [] comprasPorMes()
    {
        return this.fact.vendasMes();
    }

    /**
     * Método que retorna a faturacao de cada filial, mes a mes.
     * @return array com 36 entradas, uma para cada mes de cada filial.
     */
    public double [] getFaturacaoFilial(){
        return fact.getFactFilial();
    }


    /**
     * Preenche num conjunto os produtos que nunca foram comprados.
     * @return conjunto dos produtos não comprados.
     */
    public Set<String> produtosNuncaComprados() {
        return catP.listaProdNC();
    }

    /**
     * Método que calcula o nr de compras, quantos produtos comprou e quanto gastou, para um dado cliente
     * @param cliente cliente a analisar.
     * @return matriz com os valores organizados por mes.
     */
    public double [][] resultadosCliente(Cliente cliente) {
        return gest.verificaCliente(cliente);
    }

    /**
     * Calcula o numero total de vendas e o numero total de clientes que as fizeram, dado um mes.
     * @param mes a analisar.
     * @return matriz com os resultados.
     */
    public double[][] getInfoMes(int mes) {
        return gest.verificaMes(mes);
    }

    /**
     * Verifica se um dado produto está presente no catálogo de produtos.
     * @param prod produto a analisar.
     * @return true caso esteja, false caso nao esteja.
     */
    public boolean contemProduto(Produto prod) {
        return catP.containsProduto(prod);
    }

    /**
     * Verifica se um dado cliente está presente no catálogo de clientes.
     * @param cli cliente a analisar.
     * @return true caso esteja, false caso não esteja.
     */
    public boolean contemCliente(Cliente cli) {
        return catC.containsCliente(cli);
    }

    /**
     * Calcula, mês a mês, quantas vezes um produto foi comprado, por quantos clientes diferentes e o total faturado.
     * @param produto produto a verificar.
     * @return matriz de resultados.
     */
    public double [][] resultadosProduto(Produto produto) {
        return fact.verificaProduto(produto);
    }

    /**
     * Calcula quantos clientes distintos compraram um dado produto, mes a mes.
     * @param prod produto a verificar.
     * @return array com os resultados organizados por meses.
     */
    public int[] getNClientesMes(Produto prod) {
        Set<Cliente> aux;
        int[] meses = new int[12];

        for (int i = 0; i<12; i++) {
            meses[i] = 0;
            aux = gest.getClientesProd(prod,i+1);
            meses[i] = aux.size();
        }

        return meses;
    }

    /**
     * Calcula o TOP de produtos mais vendidos em todo o ano.
     * @param top número de produtos desejados.
     * @return Map com o código do produto e com
     */
    public Map<String,Double> produtosTOPN(int top) {
        return catP.verificaTOPN(top);
    }

    /**
     * Verifica quais foram os produtos mais comprados por um dado cliente.
     * @param cli cliente a analisar.
     * @return conjunto com os produtos mais comprados.
     */
    public Set<ProdQnt> getProdMaisComp(Cliente cli)
    {
        return gest.cliProdMaisComp(cli);
    }

    /**
     * Calcula os 3 maiores compradores, filial a filial.
     * @param filial verifica os 3 maiores compradores para a filial dada como argumento.
     * @return matriz com os resultados.
     */
    public String[][] getTop3BuyersFilial(int filial){
        return gest.getTop3Filial(filial);
    }

    /**
     * Calcula o TOP de clientes que mais compraram um dado produto.
     * @param prod produto a analisar.
     * @param top top desejado pelo utilizador.
     * @return estrutura com os clientes e o seu respetivo
     */
    public Map<String,Double> getClientesProduto(Produto prod, int top) {
        Map<Cliente,Double[]> aux = gest.getClientesProduto(prod); //map com os clientes de um dado produto
        Map<CliQnt,Double> aux1 = new TreeMap<>();

        for(Map.Entry<Cliente,Double[]> m : aux.entrySet()) {
            CliQnt c = new CliQnt(m.getKey(),m.getValue()[0]);
            aux1.put(c,m.getValue()[1]);
        }

        Map<String,Double> res = new LinkedHashMap<>();

        int i = 0;
        for(Map.Entry<CliQnt,Double> m : aux1.entrySet()) {
            if (i == top) return res;
            else {
                res.put(m.getKey().getCliString(),m.getValue());
                i++;
            }
        }

        return res;

    }

    /**
     * Retorna a lista dos N clientes que mais produtos compraram.
     * @param top pretendido com o utilizador.
     * @return
     */
    public Map<String,Double> clientesTOPN(int top) {
        return gest.verificaTOPN(top);
    }

    /**
     * Calcula a faturação total para cada produto, mês a mês, filial a filial.
     * @return Map com os resultados.
     */
    public Map<String, Double[][]> getFactEachProd(){
        return fact.factEachProd();
    }
}
