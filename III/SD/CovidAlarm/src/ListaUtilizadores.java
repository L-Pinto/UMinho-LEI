
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ListaUtilizadores {
    private Map<String, Utilizador> lista;
    private ReentrantLock lock = new ReentrantLock();

    public ListaUtilizadores() {
        lista = new HashMap<>();
    }

    public boolean autenticaUtilizador(Utilizador u) {

        lock.lock();
        try {
            Utilizador real = lista.get(u.getNome());
            if(real == null || real.isSessao()) return false;
            return real.validaPasse(u.getPasse());
        } finally {
            lock.unlock();
        }
    }

    public boolean criarUtilizador(Utilizador u) {

        lock.lock();
        try {
            boolean existe = lista.containsKey(u.getNome());
            if(!existe) lista.put(u.getNome(),u);
            return existe;
        }finally {
            lock.unlock();
        }
    }

    public Utilizador getUtilizador(String nome){
        lock.lock();
        try {
            return lista.get(nome);
        } finally {
            lock.unlock();
        }

    }
}
