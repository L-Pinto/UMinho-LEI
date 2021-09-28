
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.net.*;

public class Ping {
    Timer timer;
    DatagramSocket socket;
    InetAddress IPDest;
    InetAddress IPOrigem;

    public Ping(int seconds, DatagramSocket socket, InetAddress IPDest, InetAddress IPOrigem) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000); // schedule the task
        this.socket = socket;
        this.IPDest = IPDest;
	this.IPOrigem = IPOrigem;
    }

    class RemindTask extends TimerTask{
        public void run() {
            String state = "Ping";
            byte[] outData = new byte[1024];
            outData = state.getBytes();
            assert socket != null;
            FSChunkProtocol response = new FSChunkProtocol(" ", outData, 0, 0, 0,0,0, socket.getLocalPort(), IPOrigem, 3, -1);
            byte[] dataResponse = new byte[0];
            try {
                dataResponse = response.serialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DatagramPacket sendPkt = new DatagramPacket(dataResponse, dataResponse.length, IPDest, 8888);


	    System.out.println("Destino: " + IPDest);
	    System.out.println("Origem: " + IPOrigem);
            try {
                socket.send(sendPkt);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Ping(5, socket, IPDest,IPOrigem); //Terminate the timer thread
        }
    }

}


