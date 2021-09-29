package SGV.Model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ValueFilial implements Serializable {
    /*--------------------------  variaveis de instancia  --------------------------*/
    /** Map de Meses de uma filial*/
    private Map<Integer, Map<Produto, ValueProd>> meses;

    /*--------------------------  construtores  --------------------------*/
    /** Construtor Vazio*/
    public ValueFilial(){
        this.meses = new TreeMap<>();
    }
    /*-------------------------- Outros metdos--------------------------*/

    /** Metodo para adicionar informaçao no ValueProd
     * @param produto - produto
     * @param mes - mes
     * @param qnt - quantidade
     * @param preco - preco*/
    public void addValueFilial(Produto produto,int mes,double qnt, double preco){
        ValueProd vp = new ValueProd();

        Map<Produto, ValueProd > aux = new TreeMap<>();
        vp.atualizaVProd("", preco, qnt);
        aux.put(produto,vp);

        this.meses.put(mes, aux );
    }

    /**Metodo para atualizar informaçao no ValueProd
     * @param produto - produto
     * @param mes - mes
     * @param qnt - quantidade
     * @param preco - preco */
    public void atualizaValueFilial(Produto produto,int mes,double qnt, double preco){
        if (this.meses.containsKey(mes)) {
            Map<Produto,ValueProd> aux =  this.meses.get(mes);
            if ( aux.containsKey(produto) ) aux.get(produto).atualizaVProd("",preco, qnt);
            else{
                ValueProd aux1 = new ValueProd();
                aux1.atualizaVProd("",preco, qnt);
                aux.put(produto,aux1);
            }
        }
        else addValueFilial(produto, mes, qnt,preco);
    }
    /*--------------------------to String--------------------------*/

    /** Metodo para passar Value Filial numa string de forma a lerem-se os seus valores
     * @return String do Value Filial*/
    public String toString() {
        return "ValueFilial{" +
                "meses=" + meses +
                '}';
    }
    /*------------------------QUERIES------------------------*/
    //---------ESTATICAS----------------//
    /** Metodo que verifica se cliente comprou num mes de um filial
     * @param meses - 12 meses do ano*/
    public void verificaCliente(int [] meses) {
        for(int mes : this.meses.keySet()) {
            meses[mes-1]++;
        }
    }
    //----------------Dinamicas----------//
    //q7 - Query 2
    /** Metodo que dado um mes verifica se esta contido no ValueFilial
     * @param mes -um mes
     * @return booleano */
    public boolean contemMes(int mes) {
        return this.meses.containsKey(mes);
    }

    /** Metodo para devolve numero mensal de vendas por filial
     * @param res -resultado final
     * @param filial - filial
     * @param mes -mes */
    public void atualizaRes(double[][] res, int filial, int mes) {
        for(Map.Entry<Produto,ValueProd> m : this.meses.get(mes).entrySet()) {
            res[filial-1][0] += m.getValue().getVendas();
        }
    }

    //q8 - Query 3
    /** Metodo que devolve numero compras mensais
     * @param meses - meses por: nr de vendas, faturacao e nr produtos*/
    public void verificaMeses(double [][] meses)
    {

        for(Map.Entry<Integer,Map<Produto,ValueProd>> v : this.meses.entrySet())
        {
            int mes = v.getKey()-1;
            for(Map.Entry<Produto,ValueProd> vp : this.meses.get(mes+1).entrySet())
            {
                meses[mes][0] =  meses[mes][0] + vp.getValue().getVendas(); //codigo para o nr de vendas
                meses[mes][2] =  meses[mes][2] + vp.getValue().getFact(); //codigo para o nr de faturacao
            }
            meses[mes][1] =  meses[mes][1] + v.getValue().size(); //nr de produtos
        }
    }

    //q9 - Query 4

    /** Verifica se existe um produto num dado mes
     * @param prd - produto
     * @param mes - mes
     * @return booleano */
    public boolean existeCliMesProd(Produto prd, int mes) {
        if(meses.containsKey(mes)) return meses.get(mes).containsKey(prd);
        else return false;
    }

    //q10 - Query 5
    /** Metodo que adiciona produtos comprados
     * @param aux - aux onde fica a informacao*/
    public void getProdutosCli(Set<ProdQnt> aux) {
        for(Map<Produto,ValueProd> prod : this.meses.values())
        {
            for(Map.Entry<Produto, ValueProd> v: prod.entrySet())
            {
                double qnt = v.getValue().getQnt();
                Produto p = v.getKey();
                ProdQnt produto = new ProdQnt(p,qnt);
                if(!produto.containsString(aux)) aux.add(produto);
            }
        }
    }
    //q12 - Query 7
    /** Metodo que devolve a faturacao total
     * @return total faturado */
    public double getFatTotalCli() {
        double res = 0;
        for (Map.Entry<Integer,Map<Produto,ValueProd>> v : this.meses.entrySet()){ //para cada mes
            int mes = v.getKey(); //para cada mes

            for(Map.Entry<Produto,ValueProd> vp : this.meses.get(mes).entrySet()){ //faturacao
                res += vp.getValue().getFact(); //faturacao anual do cliente
            }
        }
        return res;
    }

    //q13 - Query 8
    /**  Metodo de devolve numero de vendas total
     * @return numero de vendas */
    public double getVendasTotalCli() {
        double res = 0;
        for (Map.Entry<Integer, Map<Produto, ValueProd>> v : this.meses.entrySet()) {
            int mes = v.getKey();

            for (Map.Entry<Produto, ValueProd> vp : this.meses.get(mes).entrySet()) {
                res = res + vp.getValue().getVendas();
            }
        }
        return res;
    }

    //q14 - Query 9
    /** Metodo que devolve se possivel a quantidade e faturacao de um produto
     * @param p - produto
     * @param res - quantidade e preco que podera ser atualizado */
    public void verificaProdCli(Produto p,Double [] res) {

        for(Map.Entry<Integer,Map<Produto,ValueProd>> v : this.meses.entrySet()) {
            if (v.getValue().containsKey(p)) {
                res[0] += v.getValue().get(p).getQnt();
                res[1] += v.getValue().get(p).getFact();
            }
        }
    }
}
