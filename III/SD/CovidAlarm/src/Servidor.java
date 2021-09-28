import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Servidor {
    final static int WORKERS_PER_CONNECTION = 5;

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(12345);
        ServerSocket ssAutorizado = new ServerSocket(56789);

        ListaUtilizadores lista = new ListaUtilizadores();
        Mapa mapa = new Mapa();
        Map<String, TaggedConnection> connections = new HashMap<>();

        Thread clienteListener = new Thread(new ClientListener(ss, lista,mapa,connections));
        Thread autorizadoListener = new Thread(new AutorizadoListener(ssAutorizado, mapa));

        clienteListener.start();
        autorizadoListener.start();

        clienteListener.join();
        autorizadoListener.join();
    }

    private static class ClientListener implements Runnable {
        private ServerSocket socket;
        private ListaUtilizadores lista;
        private Mapa mapa;
        private Map<String, TaggedConnection> connections;

        public ClientListener(ServerSocket ss, ListaUtilizadores lista, Mapa mapa, Map<String, TaggedConnection> connections) {
            this.socket = ss;
            this.lista = lista;
            this.mapa = mapa;
            this.connections = connections;
        }

        public void run(){
            try {
                while (true) {

                    Socket s = socket.accept();
                    TaggedConnection tcs = new TaggedConnection(s);
                    ServerWorker st = new ServerWorker(tcs, lista, mapa, connections);

                    for (int i = 0; i < WORKERS_PER_CONNECTION; ++i) {
                        new Thread(st).start();
                    }
                }

            }catch (IOException e){
                e.printStackTrace(); }
        }
    }

    private static class  AutorizadoListener implements Runnable {
        private ServerSocket socket;
        private Mapa mapa;

        public AutorizadoListener(ServerSocket ss, Mapa mapa) {
            this.socket = ss;
            this.mapa = mapa;
        }

        public void run(){
            try {
                while (true) {
                    Socket s = socket.accept();
                    TaggedConnection tcs = new TaggedConnection(s);
                    ServerWorkerAutorizado stAutorizado = new ServerWorkerAutorizado(tcs, mapa);

                    for (int i = 0; i < WORKERS_PER_CONNECTION; ++i) {
                        new Thread(stAutorizado).start();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // CLASSE SERVERWORKER
    public static class ServerWorker implements Runnable {
        private ListaUtilizadores lista;
        private Mapa mapa;
        private final TaggedConnection tcs;
        private Map<String, TaggedConnection> connections;
        private ReentrantLock lock = new ReentrantLock();

        public ServerWorker(TaggedConnection tcs, ListaUtilizadores lista, Mapa mapa, Map<String, TaggedConnection> connections) {
            this.tcs = tcs;
            this.lista = lista;
            this.mapa = mapa;
            this.connections = connections;
        }

        private void notificaUtilizadores(Set<String> users) {

            lock.lock();
            try {
                for (String user : users) {
                    TaggedConnection tc = connections.get(user);
                    tc.send(7,"\n[!] NOTIFICACAO: Possivel Contaminacao [!] ".getBytes());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void run() {
            try (tcs) {
                for (; ; ) {
                    TaggedConnection.FrameServidor frame = tcs.receiveServidor();
                    int tag = frame.tag;
                    switch (tag) {
                        case 0: {
                            Utilizador u = (Utilizador) frame.data;
                            String msg;
                            Boolean verifica = lista.autenticaUtilizador(u);
                            if (verifica) {
                                u.setSessao(true);
                                msg = "Autenticação com sucesso";
                                connections.put(u.getNome(), this.tcs);
                            } else {
                                msg = "Não foi possivel autenticar";
                            }
                            tcs.send(frame.tag, msg.getBytes());
                            break;
                        }
                        case 1: {
                            Utilizador u = (Utilizador) frame.data;
                            boolean existe = lista.criarUtilizador(u);
                            if (existe) {
                                tcs.send(frame.tag, "Nome de utilizador já existe".getBytes());
                            } else {
                                u.setSessao(true);
                                Coordenadas coordenadas = u.ultimaCoordenada();
                                mapa.adicionaCoordenada(coordenadas, u.getNome());
                                connections.put(u.getNome(), this.tcs);
                                String msg = "Utilizador criado em : " + coordenadas.toString();
                                tcs.send(frame.tag, msg.getBytes());
                            }
                            break;
                        }
                        case 2: {
                            Coordenadas coordenadas = (Coordenadas) frame.data;
                            Utilizador u = lista.getUtilizador(frame.nome);
                            Coordenadas coordenadaAntiga = u.ultimaCoordenada();
                            u.adicionaCoordenada(coordenadas); //UTILIZADOR ATUALIZA MAPA
                            mapa.atualizaCoordenadas(coordenadaAntiga, coordenadas, frame.nome);
                            String msg = "Localização do utilizador atualizada com sucesso para " + coordenadas.toString();
                            tcs.send(frame.tag, msg.getBytes());
                            break;
                        }
                        case 3: {
                            Coordenadas coordenadas = (Coordenadas) frame.data;
                            int size = mapa.nrUtilizadoresCoordenadas(coordenadas);
                            String msg = "Numero de utilizadores nas " + coordenadas.toString() + ": " + size;
                            tcs.send(frame.tag, msg.getBytes());
                            break;
                        }
                        case 4: {
                            Coordenadas coordenadas = (Coordenadas) frame.data;
                            Utilizador u = lista.getUtilizador(frame.nome);
                            Coordenadas coordenadaAntiga = u.ultimaCoordenada();
                            mapa.posicaoDisponivel(coordenadas);

                            u.adicionaCoordenada(coordenadas);
                            mapa.atualizaCoordenadas(coordenadaAntiga, coordenadas, frame.nome);

                            String msg = "Localização do utilizador atualizada com sucesso para " + coordenadas.toString();
                            tcs.send(frame.tag, msg.getBytes());
                            break;
                        }
                        case 5: {
                            String nome = frame.nome;
                            Utilizador u = lista.getUtilizador(frame.nome);
                            List<Coordenadas> localizacoes = u.getPosicoes();
                            mapa.removeUtilizador(localizacoes, nome);
                            Set<String> users = mapa.getPossiveisInfetados(localizacoes);
                            notificaUtilizadores(users);
                            String msg = "Notificação Concluída. As Melhoras!";
                            tcs.send(frame.tag, msg.getBytes());
                            break;
                        }
                        case 6: {
                            Utilizador u = lista.getUtilizador(frame.nome);
                            String msg;
                            u.setSessao(false);
                            msg = "Sessão terminada com sucesso";
                            tcs.send(frame.tag, msg.getBytes());
                            break;
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static class ServerWorkerAutorizado implements Runnable {

        private Mapa mapa;
        private final TaggedConnection tcs;

        public ServerWorkerAutorizado(TaggedConnection tcs, Mapa mapa) {
            this.tcs = tcs;
            this.mapa = mapa;
        }

        // CLASSE SERVERWORKER AUTORIZADO
        public void run() {
            try (tcs) {
                for (; ; ) {
                    TaggedConnection.FrameServidor frame = tcs.receiveServidor();
                    int tag = frame.tag;
                    switch (tag) {
                        case 8: {
                            String msg = mapa.descarregaMapa();
                            tcs.send(frame.tag, msg.getBytes());
                            break;
                        }
                    }
                }
            } catch (Exception ignore) {
            }
        }
    }


}