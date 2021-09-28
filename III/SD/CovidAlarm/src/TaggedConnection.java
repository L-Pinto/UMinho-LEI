import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class TaggedConnection implements AutoCloseable {
    private final DataInputStream in;
    private final DataOutputStream out;
    Socket socket;
    ReentrantLock lock = new ReentrantLock();
    ReentrantLock reslock = new ReentrantLock();

    public static class FrameCliente
    {
        public final int tag;
        public final byte[] data;

        public FrameCliente(int tag, byte[] data) {
            this.tag = tag;
            this.data = data;
        }
    }

    public static class FrameServidor
    {
        public final int tag;
        public final Object data;
        public final String nome;

        public FrameServidor(int tag, Object data, String nome) {
            this.tag = tag;
            this.data = data;
            this.nome = nome;
        }
    }

    public TaggedConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void sendAutenticacao(int tag, String nome, String passe) throws IOException {
        lock.lock();
        try {
            this.out.writeInt(tag);
            this.out.writeUTF(nome);
            this.out.writeUTF(passe);

            this.out.flush();
        }finally {
            lock.unlock();
        }
    }

    public void sendCoordenadas(int tag, String nomeUtilizador, int x, int y) throws IOException {
        lock.lock();
        try {
            this.out.writeInt(tag);
            this.out.writeUTF(nomeUtilizador);
            this.out.writeInt(x);
            this.out.writeInt(y);

            this.out.flush();
        }finally {
            lock.unlock();
        }
    }

    public void sendNotificacao(int tag, String nomeUtilizador)throws IOException{
        lock.lock();
        try {
            this.out.writeInt(tag);
            this.out.writeUTF(nomeUtilizador);

            this.out.flush();
        }finally {
            lock.unlock();
        }
    }

    public void sendDescargaMapa(int tag) throws IOException {
        lock.lock();
        try {
            this.out.writeInt(tag);
            this.out.flush();
        }finally {
            lock.unlock();
        }
    }


    public void send(int tag, byte[] data) throws IOException
    {
        lock.lock();
        try {
            this.out.writeInt(tag);
            this.out.writeInt(data.length);
            this.out.write(data);

            this.out.flush();
        }finally {
            lock.unlock();
        }
    }

    public FrameCliente receiveCliente() throws IOException {
        FrameCliente f;
        reslock.lock();
        try {
            int tag;
            tag = this.in.readInt();
            byte[] data = new byte[this.in.readInt()];

            this.in.readFully(data);

            f = new FrameCliente(tag, data);
            return f;

        } finally {
            reslock.unlock();
        }
    }

    public FrameServidor receiveServidor() throws IOException {
        FrameServidor f;
        reslock.lock();
        try {
            int tag;
            tag = this.in.readInt();

            switch (tag){
                case 0:
                case 1: {
                    Utilizador u = new Utilizador(this.in.readUTF(),this.in.readUTF());
                    f = new FrameServidor(tag,u, u.getNome());
                    return f;
                }
                case 2:
                case 3:
                case 4: {
                    String nome = this.in.readUTF();
                    Coordenadas c = new Coordenadas(this.in.readInt(),this.in.readInt());
                    f = new FrameServidor(tag,c,nome);
                    return f;
                }
                case 5:
                case 6: {
                    f = new FrameServidor(tag,null,this.in.readUTF());
                    return f;
                }
                case 8:{
                    f = new FrameServidor(tag,null,null);
                    return f;
                }
            }
            return null;

        } finally {
            reslock.unlock();
        }
    }

    public void close() throws IOException {
        this.socket.close();
    }
}
