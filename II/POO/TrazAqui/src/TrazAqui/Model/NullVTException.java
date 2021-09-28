package TrazAqui.Model;

public class NullVTException extends Exception {

    /**
     * Construtor vazio da exceção
     */
    public NullVTException(){
        super();
    }

    /**
     * Construtor parametrizado
     * @param m mensagem a apresentar
     */
    public NullVTException(String m){
        super(m);
    }

}
