import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Mapa {
    private Map<Coordenadas, Set<String>> localizacaoAtual;
    private Map<Coordenadas, Set<String>> localizacaoTotal;
    private Map<Coordenadas, Set<String>> localizacaoDoentes;
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();

    public Mapa() {
        Map<Coordenadas, Set<String>> aux = new HashMap<>();
        this.localizacaoTotal = new HashMap<>();
        this.localizacaoAtual = new HashMap<>();
        this.localizacaoDoentes = new HashMap<>();


        for(int i = 1; i<6;i++){
            for(int j = 1; j<6; j++){
                Coordenadas c = new Coordenadas(i,j);
                this.localizacaoTotal.put(c,new HashSet<>());
                this.localizacaoAtual.put(c,new HashSet<>());
                this.localizacaoDoentes.put(c,new HashSet<>());
            }
        }
    }

    public void atualizaCoordenadas (Coordenadas antiga, Coordenadas nova, String user) {

        lock.lock();
        try {
            this.localizacaoTotal.get(nova).add(user);
            this.localizacaoAtual.get(antiga).remove(user);
            this.localizacaoAtual.get(nova).add(user);

            if(nrUtilizadoresCoordenadas(antiga)==0) notEmpty.signalAll();

        } finally {
            lock.unlock();
        }
    }

    public void adicionaCoordenada(Coordenadas coord, String user) {
        try {
            lock.lock();
            this.localizacaoTotal.get(coord).add(user);
            this.localizacaoAtual.get(coord).add(user);
        } finally {
            lock.unlock();
        }
    }

    public int nrUtilizadoresCoordenadas(Coordenadas coordenadas) {
        lock.lock();
        try {
            return this.localizacaoAtual.get(coordenadas).size();
        } finally {
            lock.unlock();
        }
    }

    public void removeUtilizador(List<Coordenadas> localizacoes, String nome) {

        lock.lock();
        try {
            Coordenadas cAtual = localizacoes.get((localizacoes.size())-1);

            this.localizacaoAtual.get(cAtual).remove(nome);

            for(Coordenadas c: localizacoes){
                this.localizacaoTotal.get(c).remove(nome);
                this.localizacaoDoentes.get(c).add(nome);
            }
            this.notEmpty.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public void posicaoDisponivel(Coordenadas nova){

        lock.lock();
        try {
            while(nrUtilizadoresCoordenadas(nova)>0) {
                notEmpty.await();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Set<String> getPossiveisInfetados(List<Coordenadas> localizacoes) {

        Set<String> res = new HashSet<>();
        lock.lock();
        try {
            for(Coordenadas c: localizacoes) {
                Set<String> clientes = localizacaoTotal.get(c);
                res.addAll(clientes);
            }
            return res;
        }finally {
            lock.unlock();
        }
    }

    public String descarregaMapa() {
        lock.lock();
        try {
            StringBuilder res = new StringBuilder("");
            res.append("Coordenada (x,y) : doentes / utilizadores\n");
            for(int i = 1; i<6;i++){
                for(int j = 1; j<6; j++) {
                    Coordenadas c = new Coordenadas(i,j);
                    int utilizadores = this.localizacaoTotal.get(c).size();
                    int doentes = this.localizacaoDoentes.get(c).size();
                    int populacao = utilizadores+doentes;
                    res.append("Coordenada " + "(").append(i).append(",").append(j).append(") : ").append(doentes).append(" / ").append(populacao).append("\n");
                }
            }
            return res.toString();

        } finally {
            lock.unlock();
        }
    }
}
