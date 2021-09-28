package armazem.business.abastecimento;

public class Corredor {
    private int codCorredor;
    private String zona;
    private int ocupacao;
    private int capacidadeTotal;


    /**
     * Construtor da classe Corredor
     * @param codCorredor código do corredor
     * @param zona de armazenamento
     * @param ocupacao atual do armazém
     * @param capacidadeTotal do armazém
     */
    public Corredor(int codCorredor, String zona, int ocupacao, int capacidadeTotal) {
        this.codCorredor = codCorredor;
        this.zona = zona;
        this.ocupacao = ocupacao;
        this.capacidadeTotal = capacidadeTotal;
    }

    /**
     * Método toString do corredor
     * @return String da classe corredor
     */
    public String toString() {
        return "\033[0;34m" + "Corredor " + codCorredor + ": \n" + "\033[0m" +
                "   zona = " + zona + "\n" +
                "   ocupacao = " + ocupacao + "/" + capacidadeTotal;
    }

    public int getCodCorredor() {
        return codCorredor;
    }

    public String getZona() {
        return zona;
    }

    public int getOcupacao() {
        return ocupacao;
    }

    public int getCapacidadeTotal() {
        return capacidadeTotal;
    }

    public void setOcupacao(int ocupacao) {
        this.ocupacao = ocupacao;
    }
}

