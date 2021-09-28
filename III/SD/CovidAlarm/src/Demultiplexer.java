import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demultiplexer implements AutoCloseable {
    private TaggedConnection conn;
    private Lock lock = new ReentrantLock(); //lock para proteger o acesso ao mapa
    private Map<Integer,Entry> buf = new HashMap<>();
    private IOException exception = null;

    public class Entry {
        final Condition cond = lock.newCondition();
        final ArrayDeque<byte[]> queue = new ArrayDeque<>();
    }

    private Entry get(int tag) {
        Entry e = buf.get(tag);
        if(e == null) {
            e = new Entry();
            buf.put(tag,e);
        }
        return e;
    }

    public Demultiplexer(TaggedConnection conn) {
        this.conn = conn;
    }

    //cria e arranca um novo thread para libertar o thread principal
    public void start() {
        new Thread(() -> {
            try{
                for(;;) {
                    TaggedConnection.FrameCliente frame = conn.receiveCliente();
                    lock.lock();
                    try {
                        Entry e = get(frame.tag);
                        e.queue.add(frame.data);
                        e.cond.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (IOException e) {
                lock.lock();
                try {
                    exception = e;
                    buf.forEach((k,v) -> v.cond.signalAll());
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }

    public void sendAutenticacao(int tag, String nome, String passe) throws IOException {
        conn.sendAutenticacao(tag,nome,passe);
    }

    public void sendCoordenadas(int tag, String nomeUtilizador, int x, int y) throws IOException {
        conn.sendCoordenadas(tag,nomeUtilizador,x,y);
    }

    public void sendNotificacao(int tag, String nomeUtilizador)throws IOException{
        conn.sendNotificacao(tag,nomeUtilizador);
    }


    //fica bloqueado até haver conteúdo a receber correspondente aquela tag
    public byte[] receive (int tag) throws IOException, InterruptedException {
        try {
            lock.lock();
            // 1. obtem entrada correspndente á tag pedida
            Entry e = get(tag);

            // 2. enquanto não ha mendagens no buffer aguarda
            while (e.queue.isEmpty() && this.exception == null /* e não houve erro*/) {
                e.cond.await();
            }

            //se ha mensagens para ler
            if(!e.queue.isEmpty()){
                // 3. retorna dados
                return e.queue.poll();
            }

            //lança erro, pois o socket fechou
            //throw new IOException("Connection error");
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void close() throws Exception {
        conn.close();
    }
}