package SGV.Model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class ValueFact implements Serializable
{
    /** Value para a faturacao.
     * Relaciona uma filial com cada mes e com as informaçoes sobre um produto */
    private Map<Integer, Map<Integer, ValueProd>> filiais;

    /**
     * Construtor vazio
     */
    public ValueFact()
    {
        this.filiais = new TreeMap<>();
    }

    /**
     * Insere/atualiza a informacao numa dada filial num dado mes
     * @param preco - preço unitario de um produto
     * @param qnt - quantidade de um produto
     * @param mes - mes a inseriri/atualizar
     * @param filial - filial a inserir/atualizar
     */
    public void atualizaVFact(double preco, double qnt, int mes, int filial)
    {
        if(this.filiais.containsKey(filial))
        {
            Map<Integer, ValueProd> n = this.filiais.get(filial);
            String f = Integer.toString(filial);

            if(n.containsKey(mes)) n.get(mes).atualizaVProd(f,preco, qnt);
            else
            {
                ValueProd vp = new ValueProd(f, qnt, preco, 1);
                n.put(mes, vp);
                this.filiais.put(filial, n);
            }
        }
        else
        {
            Map<Integer, ValueProd> value = new TreeMap<>();
            ValueProd vp = new ValueProd("", qnt, preco, 1);
            value.put(mes, vp);
            this.filiais.put(filial,value); 
        }
    }

    /** ------------------- QUERIES -------------- */

    /**
     * Informaçao das vendas para um dado mes
     * @param mes - mes a verificar
     * @return - vendas totais realizadas nesse mes
     */
    public int vendasPorMes(int mes)
    {
        int count = 0;
        for(Map<Integer, ValueProd> value: this.filiais.values())
        {
            if(value.containsKey(mes))
            {
                count += value.get(mes).getVendas();
            }
            else count += 0;
        }
        return count;
    }

    /**
     * Faturacao de um dado mes numa dada filial de um produto
     * @param filial - filial a verificar
     * @param mes - mes a verificar
     * @return -
     */
    public double getFactVF(int filial, int mes)
    {
        double fatMes = 0;

        if( filiais.containsKey(filial) )
        {
            if (filiais.get(filial).containsKey(mes) ) fatMes = filiais.get(filial).get(mes).getFact();
        }

        return fatMes;
    }

    /**
     * Faturacao e o numero vendas para cada mes e cada filial de um produto
     * @param meses - matriz a preencher
     */
    public void verificaMeses(double[][] meses)
    {
        int mes;
        for(Map.Entry<Integer,Map<Integer,ValueProd>> v: this.filiais.entrySet())
        {
            for(Map.Entry<Integer, ValueProd> vp: this.filiais.get(v.getKey()).entrySet())
            {
                mes = vp.getKey()-1;
                meses[mes][0] =  meses[mes][0] + vp.getValue().getVendas(); //codigo para o nr de vendas
                meses[mes][1] =  meses[mes][1] + vp.getValue().getFact(); //faturacao
            }
        }
    }

    /**
     * Faturacao de um dado produto dividida em meses e filial a filial
     * @param aux - matriz a preencher
     */
    public void atualizaFactProd(Double[][] aux)
    {
        for(Map.Entry<Integer,Map<Integer,ValueProd>> v : this.filiais.entrySet()) //filial
        {
            int filial = v.getKey();
            for(Map.Entry<Integer,ValueProd> vp : this.filiais.get(filial).entrySet()) //mes
            {
                int mes = vp.getKey();
                aux[mes-1][filial-1] += vp.getValue().getFact();
            }
        }
    }

    /** ------------------ OUTROS ------------------*/

    /**
     * Informacao para testes debug
     * @return - converte a classe numa string
     */
    public String toString()
    {
        return "\nValueFact{\n" +
                "filiais=" + filiais +
                '}';
    }
}
