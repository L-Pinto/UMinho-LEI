package SGV.Model;

import SGV.InterNavModel;

import java.util.*;

public class NavModel implements InterNavModel {
    /*--------------------------  variaveis de instancia  --------------------------*/
    /** Paginas e informacao para query 1*/
    private Map<Integer, Set<String>> subsets;
    /** Paginas e informacao para queries 5, 6, 8 e 9 */
    private Map<Integer, Map<String,Double>> navQuery10;
    /** Paginas e informacao da query 10*/
    private Map<Integer, Map<String, Double[][]>> navQuery15;
    /** Pagina atual*/
    private int paginaAtual;

    /*-------------------------- Pagina Atual --------------------------*/

    /** Metodo para atualizar pagina atual
     * @param pagA - pagina atual*/
    public void setPaginaAtual(int pagA){
        this.paginaAtual = pagA;
    }

    /** Metodo de retorno da pagina atual
     * @return pagina atual */
    public int getPaginaAtual(){
        return this.paginaAtual;
    }
    /*-------------------------- QUERY 1 --------------------------*/

    /** Construtor parametrizado
     * @param infoNav - conteudo query 1*/
    public NavModel(TreeSet<String> infoNav) {
        subsets = new TreeMap<>();

        int pag=0, i=0;
        String inicio = "";

        for (String prod : infoNav) { //preenche subsets
            if (i == 0) inicio = prod; //inicio

            else if (i%80 == 0) { //durante
                pag ++;
                subsets.put(pag, infoNav.subSet( inicio, prod));
                inicio = prod;
            }

            else if (i+1 == infoNav.size()) { //fim
                pag++;
                subsets.put(pag, infoNav.subSet(inicio,true, prod,true));
            }

            i++;
        }
        this.paginaAtual = 1;
    }

    /** Numero de paginas total
     * @return numero de paginas */
    public int getSizeTotal(){
        return subsets.size();
    }

    /** Metodo que retorna conteudo de uma pagina
     * @param pag - pagina
     * @return conteudo da pagina */
    public Set<String> getPagina(int pag) {
        return subsets.getOrDefault(pag, null);
    }

    /** Metodo que encontra a pagina onde esta um produto
     * @param produto - produto a ser procurado
     * @return pagina */
    public int procuraProduto(String produto) {
        for(Map.Entry<Integer, Set<String>> p: this.subsets.entrySet()) {
            Set<String> aux = p.getValue();
            if(aux.contains(produto)) return p.getKey();
        }
        return 0;
    }

    /*-------------------------- QUERIES: 5,6,8,9 --------------------------*/

    /** Construtor Parametrizado
     * @param cat - conteudo para queries*/
    public NavModel(Map<String, Double> cat)
    {
        this.navQuery10 = new TreeMap<>();
        Map<String,Double> aux = new LinkedHashMap<>();
        int maxpag;

        if(cat.size()%10 == 0) maxpag = cat.size()/10;
        else maxpag = (cat.size()/10)+1;
        int rest = cat.size() - (cat.size()/10)*10;

        int i = 1;
        int pag = 1;

        for(Map.Entry<String, Double> prod: cat.entrySet())
        {
            if(i == 10 || (pag==maxpag && i==rest))
            {
                aux.put(prod.getKey(),prod.getValue());
                navQuery10.put(pag, aux);
                i=1;
                pag++;
                aux = new LinkedHashMap<>();
            }
            else {
                aux.put(prod.getKey(),prod.getValue());
                i++;
            }
        }
        this.paginaAtual = 1;
    }

    /** Construtor Parametrizado
     * @param cat - conteudo para queries*/
    public NavModel(Set<ProdQnt> cat) {
        this.navQuery10 = new TreeMap<>();
        Map<String,Double> aux = new LinkedHashMap<>();
        int maxpag;

        if(cat.size()%10 == 0) maxpag = cat.size()/10;
        else maxpag = (cat.size()/10)+1;
        int rest = cat.size() - (cat.size()/10)*10;

        int i = 1;
        int pag = 1;

        for(ProdQnt prod: cat) {
            if(i == 10 || (pag==maxpag && i==rest)) {
                aux.put(prod.getProdString(),prod.getQnt());
                navQuery10.put(pag, aux);
                i=1;
                pag++;
                aux = new LinkedHashMap<>();
            }
            else {
                aux.put(prod.getProdString(),prod.getQnt());
                i++;
            }
        }
        this.paginaAtual = 1;
    }

    /** Numero de paginas total
     * @return numero de paginas */
    public int getMaxPagQ10() { return this.navQuery10.size(); }

    /** Metodo que encontra a pagina onde esta um produto
     * @param produto - produto a ser procurado
     * @return pagina */
    public int procuraProdutoQ10(String produto) {
        for(Map.Entry<Integer, Map<String,Double>> p: this.navQuery10.entrySet()) {
            Map<String,Double> aux = p.getValue();
            if(aux.containsKey(produto)) return p.getKey();
        }
        return 0;
    }

    /** Metodo que retorna conteudo de uma pagina
     * @param i - pagina
     * @return conteudo da pagina */
    public Map<String,Double> getPagNavQ10(int i) {
        if(navQuery10.containsKey(i)) return navQuery10.get(i);
        return null;
    }

    /*-------------------------- QUERY 10--------------------------*/

    /** Construtor Parametrizado
     * @param infoCatalogo - conteudo para query */
    public NavModel( TreeMap<String, Double[][]> infoCatalogo) {
        this.navQuery15 = new TreeMap<>();
        int pag=0, i=0;
        for (Map.Entry<String, Double[][]> aux : infoCatalogo.entrySet()){
            pag++;
            Map<String, Double[][]> novo = new TreeMap<>();
            novo.put(aux.getKey(), aux.getValue());
            navQuery15.put(pag, novo);
        }
        this.paginaAtual = 1;
    }

    /** Numero de paginas total
     * @return numero de paginas */
    public int getMaxPagQ15() { return this.navQuery15.size(); }

    /** Metodo que encontra a pagina onde esta um produto
     * @param produto - produto a ser procurado
     * @return pagina */
    public int procuraProdutoQ15(String produto) {
        for(Map.Entry<Integer, Map<String,Double[][]>> p: this.navQuery15.entrySet()) {
            Map<String,Double[][]> aux = p.getValue();
            if(aux.containsKey(produto)) return p.getKey();
        }
        return 0;
    }

    /** Metodo que retorna conteudo de uma pagina
     * @param i - pagina
     * @return conteudo da pagina */
    public Map<String,Double[][]> getPagNavQ15(int i) {
        if(navQuery15.containsKey(i)) return navQuery15.get(i);
        return null;
    }
}
