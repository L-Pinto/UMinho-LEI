package armazem.business.catalogo;

public class Localizacao {
    private String zona;
    private int prateleira;

    public Localizacao() {
        this.zona = "rececao";
        this.prateleira = 0;
    }

    public Localizacao(int prateleira,String zona) {
        this.zona = zona;
        this.prateleira = prateleira;
    }

    public String getZona() {
        return zona;
    }

    public int getPrateleira() {
        return prateleira;
    }

    public String toString() {
        return "\n" + "\033[0;34m" + "Localizacao: " + "\033[0m" +
                "zona = " + zona +
                "   prateleira = " + prateleira ;
    }
}
