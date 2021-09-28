import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class TimeoutFS {
    Timer timer;
    DatagramSocket socket;
    HashMap<HttpGw.TCPConnection, HashMap<String, HttpGw.Packet>> httpResponse;
    ReentrantLock lockRes;
    Condition condRes;

    public TimeoutFS (int seconds, DatagramSocket socket, HashMap<HttpGw.TCPConnection, HashMap<String, HttpGw.Packet>> httpResponse, ReentrantLock lockRes, Condition condRes) {
        timer = new Timer();
        timer.schedule(new TimeoutFS.RemindTaskTimeout(), seconds*1000); // schedule the task
        this.socket = socket;
        this.httpResponse = httpResponse;
        this.lockRes = lockRes;
	this.condRes = condRes;
    }

    class RemindTaskTimeout extends TimerTask {
        public void run() {
            try {
                lockRes.lock();

                System.out.println("Timeout");

                for(HttpGw.TCPConnection t : httpResponse.keySet()) {
                    HashMap<String, HttpGw.Packet> val = httpResponse.get(t);

                    for(String s : val.keySet()) {
                        HttpGw.Packet p = val.get(s);
                        int last = p.getTotalFragments(); //retorna o número
                        int flagLast = 0;

                        List<Integer> lost_fragments = p.missingFragments();

                        if (lost_fragments.size() == 0) { //se não há fragmentos perdidos, entao o pedido está completo
                            p.setComplete(1);
                        }
                        else {//precisa de retransmissao
                            for(int i : lost_fragments) { //enviar pedido de retransmissão daqueles fragmentos
                                if (i == last) flagLast = 1; else flagLast = 0;

                                FSChunkProtocol fs_requestFile = new FSChunkProtocol(s, " ".getBytes(), 0, 0, 0,i,flagLast,0, null, 2,t.getClient());
                                System.out.println("Pedido do cliente timeout " + t.getClient() + " e ficheiro: " + s + " fragmento: " + i);
                                byte[] requestFile = fs_requestFile.serialize();

                                //envia pedido de ficheiro para o fastFileSrv menos ocupado
                                Map.Entry<InetAddress,Integer> server = HttpGw.getServerLessBusy();
                                DatagramPacket dp = new DatagramPacket(requestFile, requestFile.length, server.getKey(), server.getValue());
                                socket.send(dp);

                            }
                        }

                    }
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                lockRes.unlock();
            }

            new TimeoutFS(20, socket, httpResponse,lockRes,condRes); //Terminate the timer thread
        }
    }
    
    
}
