package SGV.Model;

import java.io.Serializable;
import java.util.*;
/*-------------------------- gets, sets  --------------------------*/
public class GestFilial implements InterGestFilial,Serializable {
    /*--------------------------  variaveis de instancia  --------------------------*/
    /** Filial 1*/
    private Map<Cliente, ValueFilial> filial1;
    /** Filial 2*/
    private Map<Cliente, ValueFilial> filial2;
    /** Filial 3*/
    private Map<Cliente, ValueFilial> filial3;

    /*--------------------------  construtores  --------------------------*/
    /** Construtor vazio*/
    public GestFilial() {
            this.filial1 = new TreeMap<>();
            this.filial2 = new TreeMap<>();
            this.filial3 = new TreeMap<>();
    }
    /*-------------------------- Outros metdos--------------------------*/
    /** Metodo de insercao de uma venda na GestFilial
     * @param venda - Linha de Venda, dividida por parametros */
    public void insereVenda(String [] venda){
        Produto produto = new Produto(venda[0]);
        double preco = Double.parseDouble(venda[1]);
        double qnt = Double.parseDouble(venda[2]);
        Cliente cliente = new Cliente(venda[4]);
        int mes = Integer.parseInt(venda[5]);
        switch (venda[6]){
            case "1":
            {
                if (!this.filial1.containsKey(cliente) ){
                    ValueFilial novo = new ValueFilial();
                    novo.addValueFilial(produto, mes, qnt, preco);
                    this.filial1.put(cliente, novo);
                }
                else this.filial1.get(cliente).atualizaValueFilial(produto,mes,qnt,preco);
                break;
            }
            case "2":{
                if (!this.filial2.containsKey(cliente)) {
                    ValueFilial novo = new ValueFilial();
                    novo.addValueFilial(produto, mes, qnt, preco);
                    this.filial2.put(cliente, novo);
                }
                else this.filial2.get(cliente).atualizaValueFilial(produto,mes,qnt,preco);
                break;
            }
            case "3":{
                if (!this.filial3.containsKey(cliente)) {
                    ValueFilial novo = new ValueFilial();
                    novo.addValueFilial(produto, mes, qnt, preco);
                    this.filial3.put(cliente, novo);
                }
                else this.filial3.get(cliente).atualizaValueFilial(produto,mes,qnt,preco);
                break;
            }
        }
    }
    /*--------------------------to String--------------------------*/

    /** Metodo para imprimir GestFilial
     * @return GestFilial em formato mais facil de ler */
    public String toString() {
        return "GestFilial{" +
                "filial1=" + filial1 +
                ", filial2=" + filial2 +
                ", filial3=" + filial3 +
                '}';
    }
    //------------------------------------------QUERIES------------------------------------------//
    //---------ESTATICAS----------------//
    /** Metodo que retorna numero de clientes distintos que compraram a cada mes, filial a filial
     * @return resultado por mes e filial */
    public int [][] getClientesFilial() {
        int [][] filiais = new int [3][12];

            for(int j = 0; j < 12; j++) {
                filiais[0][j] = 0;
                filiais[1][j] = 0;
                filiais[2][j] = 0;
            }

        for(ValueFilial v : this.filial1.values()) {
            v.verificaCliente(filiais[0]);
        }
        for(ValueFilial v : this.filial2.values()) {
            v.verificaCliente(filiais[1]);
        }
        for(ValueFilial v : this.filial3.values()) {
            v.verificaCliente(filiais[2]);
        }

        return filiais;
    }
    //----------------Dinamicas----------//
    //q7- query 2
    /** Metodo que retorna número de total de vendas e e de clientes que compraram naquele mes em cada uma das filiais
     * @param mes - Um mes
     * @return - numero de vendas e clientes por filial*/
    public double [][] verificaMes(int mes) { //outra forma de fazer query 2
        double [][] res = new double [3][2];
        int i;

        for(i = 0; i < 3; i++) {
            for(int j = 0; j < 2; j++) {
                res[i][j] = 0;
            }
        }

        for(Map.Entry<Cliente,ValueFilial> m : this.filial1.entrySet()) {
            if (m.getValue().contemMes(mes)) {
                m.getValue().atualizaRes(res,1,mes);
                res[0][1] += 1;
            }
        }

        for(Map.Entry<Cliente,ValueFilial> m : this.filial2.entrySet()) {
            if (m.getValue().contemMes(mes)) {
                m.getValue().atualizaRes(res,2,mes);
                res[1][1] += 1;
            }
        }

        for(Map.Entry<Cliente,ValueFilial> m : this.filial3.entrySet()) {
            if (m.getValue().contemMes(mes)) {
                m.getValue().atualizaRes(res,3,mes);
                res[2][1] += 1;
            }
        }

        return res;
    }

    //q8
    /** Metodo que retorna nº de compras feitas, nº de produtos que comprou e o total gasto para cada mes de um cliente
     * @param cliente - Um cliente
     * @return - nº de compras feitas, produtos comprados e total gasto*/
    public double [][] verificaCliente(Cliente cliente)
    {
        double [][] res = new double [12][3];

        for(int i = 0; i < 12; i++) {
            for(int j = 0; j < 3; j++) {
                res[i][j] = 0;
            }
        }

        ValueFilial vf1 = this.filial1.get(cliente);
        vf1.verificaMeses(res);

        ValueFilial vf2 = this.filial2.get(cliente);
        vf2.verificaMeses(res);

        ValueFilial vf3 = this.filial3.get(cliente);
        vf3.verificaMeses(res);

        return res;
    }
    //q9
    /** Metodo que retorna conjunto dos clientes que compraram um produto num mes
     * @param prd - produto
     * @param mes - respetivo mes
     * @return - clientes */
    public Set<Cliente> getClientesProd(Produto prd, int mes) {
        Set<Cliente> aux = new TreeSet<>();

        for (Map.Entry<Cliente, ValueFilial> v : this.filial1.entrySet()) {
            if (v.getValue().existeCliMesProd(prd, mes)) aux.add(v.getKey());
        }

        for (Map.Entry<Cliente, ValueFilial> v : this.filial2.entrySet()) {
            if (v.getValue().existeCliMesProd(prd, mes)) aux.add(v.getKey());
        }

        for (Map.Entry<Cliente, ValueFilial> v : this.filial3.entrySet()) {
            if (v.getValue().existeCliMesProd(prd, mes)) aux.add(v.getKey());
        }
        return aux;
    }
    //q10
    /**
     * Metodo que devolve Conjuto ProdQnt,relacionando assim a quantidade comprada por um cliente com o produto comprado
     * @param c - cliente
     * @return - conjunto ProdQnt*/
    public Set<ProdQnt> cliProdMaisComp(Cliente c) {
        Set<ProdQnt> aux = new TreeSet<>();

        if(this.filial1.containsKey(c)) this.filial1.get(c).getProdutosCli(aux);
        if(this.filial2.containsKey(c)) this.filial2.get(c).getProdutosCli(aux);
        if(this.filial3.containsKey(c)) this.filial3.get(c).getProdutosCli(aux);

        return aux;
    }

    //q12

    /** Metodo que retorna Top 3 clientes com mais dinheiro gasto
     * @param filial - filial a verificar
     * @return - Top 3 dos maiores compradadores para cada filial */
    public String[][] getTop3Filial(int filial){
        String [][] aux = new String[3][2];
        for (int i=0; i<3;i++) {
            aux[i][0]= "";  //string cliente
            aux[i][1]= "0"; //string totalFaturado
        }
        Map<Cliente,ValueFilial> f = new TreeMap<>();

        switch (filial) {
            case 1: {f = this.filial1; break;}
            case 2: {f = this.filial2; break;}
            case 3: {f = this.filial3; break;}
        }

        //atualiza se encontrar cliente melhor que o top 3 atual
        for (Map.Entry<Cliente, ValueFilial> v : f.entrySet()) {
            Cliente cli = v.getKey(); //
            double totFact = f.get(cli).getFatTotalCli();

            double fat1 = Double.parseDouble((aux[0][1]));
            double fat2 = Double.parseDouble(aux[1][1]);
            double fat3 = Double.parseDouble(aux[2][1]);

            if ( fat3 < totFact ) {
                if (fat2 < totFact ) {
                    String newTop3 = aux[1][0] ; //info 3º <- info do antigo 2ª
                    String newF3 =  aux[1][1] ;

                    if ( fat1 < totFact ) {
                        String newTop2 = aux[0][0] ; //info do 2º <- info do antigo 1ª
                        String newF2 =  aux[0][1] ;

                        aux[0][0] = cli. getCliente();         //atualiza 1 posicao
                        aux[0][1] = Double.toString(totFact);
                        aux[1][0] = newTop2;                    //atualiza 2 posicao
                        aux[1][1] = newF2;
                        aux[2][0] = newTop3;                    //atualiza 3 posicao
                        aux[2][1] = newF3;

                    }
                    else {
                        aux[1][0] = cli. getCliente(); //atualiza 2 posicao
                        aux[1][1] = Double.toString(totFact);
                        aux[2][0] = newTop3;            //atualiza 3 posicao
                        aux[2][1] = newF3;
                    }
                }
                else {
                        aux[2][0] = cli. getCliente();
                        aux[2][1] = Double.toString(totFact);
                    }
            }
        }
        return aux;
    }

    //q13
    /** Metodo que retorna Top dos clientes que mais compraram produtos diferentes
     * @param top - número de Clientes a pertencer ao top
     * @return - o top pretendido */
    public Map<String,Double> verificaTOPN(int top) {
        TreeSet<CliVendas> aux = new TreeSet<>();
        Map<String,Double> res = new LinkedHashMap<>();

        for(Map.Entry<Cliente,ValueFilial> v : this.filial1.entrySet()) {
            double vendas = v.getValue().getVendasTotalCli();
            CliVendas cv = new CliVendas(v.getKey(),vendas);

            if(cv.containsCliente(aux)) aux.add(cv);
        }

        for(Map.Entry<Cliente,ValueFilial> v : this.filial2.entrySet()) {
            double vendas = v.getValue().getVendasTotalCli();
            CliVendas cv = new CliVendas(v.getKey(),vendas);

            if(cv.containsCliente(aux)) aux.add(cv);
        }

        for(Map.Entry<Cliente,ValueFilial> v : this.filial3.entrySet()) {
            double vendas = v.getValue().getVendasTotalCli();
            CliVendas cv = new CliVendas(v.getKey(),vendas);

            if(cv.containsCliente(aux)) aux.add(cv);
        }

        int count = 0;

        for(CliVendas cli: aux) {
            if(count == top){ return res; }
            else {
                res.put(cli.getCodClie(),cli.getVendas());
                count++;
            }
        }
        return res;
    }

    //q14
    /** Metodo que retorna top dos clientes que mais compraram
     * @param prod - produto
     * @return - top e valor gasto por cada cliente */
    public Map<Cliente,Double[]> getClientesProduto(Produto prod) {
        Map<Cliente,Double[]> aux = new TreeMap<>();

        for(Map.Entry<Cliente,ValueFilial> vf : this.filial1.entrySet()) {
            Cliente cli = vf.getKey();
            Double [] d = new Double[2];
            d[0] = 0.0;
            d[1] = 0.0;

            if(aux.containsKey(cli)) {
                d = aux.get(cli);
            }

            vf.getValue().verificaProdCli(prod,d);
            if (d[0] != 0) aux.put(cli,d);
        }

        for(Map.Entry<Cliente,ValueFilial> vf : this.filial2.entrySet()) {
            Cliente cli = vf.getKey();
            Double [] d = new Double[2];
            d[0] = 0.0;
            d[1] = 0.0;

            if(aux.containsKey(cli)) {
                d = aux.get(cli);
            }

            vf.getValue().verificaProdCli(prod,d);
            if (d[0] != 0) aux.put(cli,d);
        }

        for(Map.Entry<Cliente,ValueFilial> vf : this.filial3.entrySet()) {
            Cliente cli = vf.getKey();
            Double [] d = new Double[2];
            d[0] = 0.0;
            d[1] = 0.0;

            if(aux.containsKey(cli)) {
                d = aux.get(cli);
            }

            vf.getValue().verificaProdCli(prod,d);
            if (d[0] != 0) aux.put(cli,d);
        }
        return aux;
    }
}
