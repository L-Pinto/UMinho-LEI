package SGV.Model;

import java.util.Map;

public interface InterCatClientes {
    Map<Cliente,String> getCatalogo();
    void setCatalogo(Map<Cliente,String> catalogo);
    int getLidas();
    void setLidas(int lidas);
    void replaceValue(Cliente cli, String filial);
    boolean containsCliente(Cliente c);
    void adicionaCliente(Cliente c);
    int getNaoCompraram();
    int getCompraram();
}
