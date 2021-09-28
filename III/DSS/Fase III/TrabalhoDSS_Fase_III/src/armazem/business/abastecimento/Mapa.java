package armazem.business.abastecimento;

import java.util.ArrayList;
import java.util.List;

public class Mapa {
    private int nVertices;
    private List<Integer>[] matriz;
    private List<Integer> caminho;

    /**
     * Construtor da classe mapa
     * @param nVertices número de vértices (prateleira) de um mapa
     */
    public Mapa(int nVertices) {
        this.nVertices = nVertices;
        initMatriz();
        this.caminho = new ArrayList<>();
    }

    /**
     * Inicializar matriz adjacencia
     */
    private void initMatriz() {
        matriz = new ArrayList[this.nVertices];

        for (int i = 0; i < this.nVertices; i++) {
            matriz[i] = new ArrayList<>();
        }
    }

    /**
     * Método que adiciona uma aresta de u para v
     * @param u vertice inicial
     * @param v vertice final
     */
    public void addVertice(int u, int v) {
        // Add v a lista do u
        this.matriz[u].add(v);
    }


    /**
     * Método que calcula um percurso entre dois pontos
     * @param origem inicio do percurso
     * @param destino final do percurso
     * @return lista de vértices (prateleiras) a percorrer
     */
    public List <Integer> caminho(int origem, int destino)
    {
        boolean[] visitados = new boolean[this.nVertices];
        ArrayList<Integer> listCaminho = new ArrayList<>();

        // add origem to caminho[]
        listCaminho.add(origem);

        // chamada recursiva
        getTodosCaminhos(origem, destino, visitados, listCaminho);

        return this.caminho;
    }


    /**
     * Método recursivo que calcula todos os caminhos de uma origem para um destino
     * @param origem origem do caminho
     * @param destino destino do caminho
     * @param visitados indica quais os vértices visitados no caminho atual
     * @param listCaminho guarda vertices no caminho
     */
    private void getTodosCaminhos(int origem, int destino, boolean[] visitados, List<Integer> listCaminho) {

        if (origem == destino) {
            this.caminho = new ArrayList<>(listCaminho) ; // se deu match acaba a travessia
            return ;
        }
        // Marca o nodo atual
        visitados[origem] = true;

        // Recorrente para todos os vertices
        // adjacentes ao atual
        for (Integer i : matriz[origem]) {
            if (!visitados[i]) {
                // guarda o nodo atual
                // em caminho[]
                listCaminho.add(i);
                getTodosCaminhos(i, destino, visitados, listCaminho);
                // remove no atual
                // em caminho[]
                listCaminho.remove(i);
            }
        }
        // Marca o nodo atual
        visitados[origem] = false;
    }

    /**
     * Método que junta duas listas em 1
     * @param caminhoRP caminho robot-palete
     * @param caminhoPD caminho palete-destino
     * @return
     */
    public List <Integer> juntaCaminhos(List<Integer> caminhoRP, List<Integer> caminhoPD) {

        for(int j = 1; j < caminhoPD.size(); j++) {
            caminhoRP.add(caminhoPD.get(j));
        }
        return caminhoRP;
    }

}