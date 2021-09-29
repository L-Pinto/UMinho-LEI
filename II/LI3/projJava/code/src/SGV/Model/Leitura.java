package SGV.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Leitura {

    /**
     * Método que verifica se uma dada linha de venda é válida.
     * @param line linha de venda.
     * @param c Catálogo de Clientes.
     * @param p Catálogo de Produtos.
     * @return true caso a venda seja válida, false caso contrário.
     */
    public boolean validaVendas(String line, InterCatClientes c, InterCatProdutos p) {
        String [] s = line.split(" ");
        boolean res = true;

        Produto prod = new Produto(s[0]);

        if(!p.containsProduto(prod)) {
            res = false;
        }

        if(res && Double.parseDouble(s[1]) < 0 || Double.parseDouble(s[1]) >= 1000) {
            res = false;
        }

        if(res && Integer.parseInt(s[2]) < 1 || Integer.parseInt(s[2]) > 200) {
            res = false;
        }

        if(res && !s[3].equals("P") && !s[3].equals("N")) {
            res = false;
        }

        Cliente cli = new Cliente(s[4]);

        if(res && !c.containsCliente(cli)) {
            res = false;
        }

        if(res && Integer.parseInt(s[5]) < 1 || Integer.parseInt(s[5]) > 12) {
            res = false;
        }

        if(res && Integer.parseInt(s[6]) < 1 || Integer.parseInt(s[6]) > 3) {
            res = false;
        }

        return res;
    }

    /**
     * Método que efetua a leitura do ficheiro de clientes e preenche a estrutura respetiva.
     * @param ficheiro caminho do ficheiro que irá ser lido.
     * @param clientes Estrutura a ser preenchida.
     */
    public int leituraClientes(String ficheiro, InterCatClientes clientes) {
        int i = 0;
        String line;
        try
        {
            BufferedReader bf = new BufferedReader(new FileReader(ficheiro));

            while((line = bf.readLine()) != null)
            {
                i++;
                Cliente c = new Cliente(line);
                if(c.valido_cli()) clientes.adicionaCliente(c.clone());
            }
        }
        catch (IOException e)
        {
            return 1;
        }
        clientes.setLidas(i);
        
        return 0;
    }

    /**
     * Método que efetua a leitura do ficheiro de produtos e preenche a estrutura respetiva.
     * @param ficheiro caminho do ficheiro que irá ser lido.
     * @param produtos Estrutura a ser preenchida.
     */
    public int leituraProdutos(String ficheiro, InterCatProdutos produtos) {
        int i = 0;
        String line;
        try
        {
            BufferedReader bf = new BufferedReader(new FileReader(ficheiro));

            while((line = bf.readLine()) != null)
            {
                i++;
                Produto p = new Produto(line);
                if(p.valido_prod()){
                    produtos.adicionaProduto(p.clone());
                }
            }
        }
        catch (IOException e)
        {
            return 1;
        }
        produtos.setLidas(i);
        
        return 0;
    }

    /**
     * Método que efetua a leitura do ficheiro das vendas, preenchendo/atualizando as estruturas do programa.
     * @param ficheiro caminho do ficheiro a ser lido.
     * @param p Catálogo de Produtos.
     * @param c Catálogo de Clientes.
     * @param g Gestão de Filial.
     * @param f Faturação Global.
     */
    public int leituraVendas(String ficheiro, InterCatProdutos p, InterCatClientes c, InterGestFilial g, InterFactGlobal f) {
        int i = 0;
        int v = 0;
        int zero = 0;
        double fact = 0;
        String line;
        try
        {
            BufferedReader bf = new BufferedReader(new FileReader(ficheiro));

            while((line = bf.readLine()) != null)
            {
                i++;
                if(validaVendas(line, c, p))
                {
                    v++;
                    String [] s = line.split(" ");
                    Produto prod = new Produto(s[0]);
                    Cliente cli = new Cliente(s[4]);
                    f.insereVenda(s);
                    g.insereVenda(s);
                    c.replaceValue(cli,s[6]);
                    double preco = Double.parseDouble(s[1]);
                    double qnt = Double.parseDouble(s[2]);
                    if(preco == 0.0) zero++;
                    fact = fact + preco*qnt;
                    p.replaceValue(prod, s[6], preco, qnt);
                }
            }
        }
        catch (IOException e)
        {
            return 1;
        }
        f.setFactT(fact);
        f.setLidas(i);
        f.setValidas(v);
        f.setGratis(zero);
        
        return 0;
    }

}
