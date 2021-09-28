import java.util.*;

public class Utilizador {
    private String nome;
    private String passe;
    private boolean sessao;
    private List<Coordenadas> posicoes;


    public boolean isSessao() {
        return sessao;
    }

    public Utilizador(String nome, String passe) {
        this.nome = nome;
        this.passe = passe;
        this.sessao = false;

        List<Coordenadas> aux = new ArrayList<>();
        Coordenadas c = new Coordenadas();
        aux.add(c);

        this.posicoes = aux;
    }

    public void setSessao(boolean sessao) {
        this.sessao = sessao;
    }

    public boolean validaPasse(String passe) {
        return passe.equals(this.passe);
    }

    public String getNome() {
        return nome;
    }

    public String getPasse() {
        return passe;
    }

    public void adicionaCoordenada(Coordenadas coordenadas) {
        if(!this.posicoes.contains(coordenadas)) {
            this.posicoes.add(coordenadas);
        }
    }

    public Coordenadas ultimaCoordenada() {
        return this.posicoes.get(this.posicoes.size()-1);
    }

    public List<Coordenadas> getPosicoes() {
        return this.posicoes;
    }
}
