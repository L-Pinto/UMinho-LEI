

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class HttpGw {

    private static HashMap<Map.Entry<InetAddress,Integer>,Map.Entry<Integer,Integer>> serverPool = new HashMap<>(); //IP,Porta, NrPins, NrPedidos
    private static ReentrantLock lockPool = new ReentrantLock(); //lock para proteger o map de serverPool porque vai ser acedido por várias threads

    private static HashMap<TCPConnection,List<String>> httpConnections = new HashMap<>();
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition cond = lock.newCondition();

    private static HashMap<TCPConnection,HashMap<String,Packet>> httpResponse = new HashMap<>();
    private static ReentrantLock lockRes = new ReentrantLock();
    private static Condition condRes = lockRes.newCondition();

    private static Set<String> filesServer = new TreeSet<>();

    private static int clientCounter = 0;

    public static void main (String[] args) throws Exception {

        //conexao UDP - escuta por datagramas FSChunkProtocol

	InetAddress inetAddress = null;


	Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
	for(NetworkInterface netint : Collections.list(nets)) {
		if (!netint.isLoopback() || !netint.isUp()) {
        		Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
	
			for(InetAddress inetA : Collections.list(inetAddresses) ) {
				if (inetA instanceof Inet4Address) 
					inetAddress = inetA;
			}
		}
	}



	//String hostName = inetAddress.getHostName();
        System.out.println("Ativo em " + inetAddress + " porta 8080 \n");

        DatagramSocket socket = new DatagramSocket(8888);

        Thread udpListener = new Thread(new UDPListener(socket));
        udpListener.start();

        //conexao TCP - escuta pedidos HTTP GET Request na porta 8080 sobre TCP
        ServerSocket ss = new ServerSocket(8080);

        Thread tcpListener = new Thread(new TCPListener(ss));
        tcpListener.start();

        udpListener.join();
        tcpListener.join();

    }

    public static Map.Entry<InetAddress,Integer> getServerLessBusy() {
        int min = Integer.MAX_VALUE;
        Map.Entry<InetAddress, Integer> res = null;

        try {
            lockPool.lock();

            for (Map.Entry<InetAddress, Integer> e : serverPool.keySet()) {
                Map.Entry<Integer, Integer> value = serverPool.get(e);

                if (value.getValue() <= min) {
                    min = value.getValue();
                    res = e;
                }
            }

            Map.Entry<Integer, Integer> valueServerRes = serverPool.get(res);
            int key = valueServerRes.getKey();
            int val = valueServerRes.getValue();

            Map.Entry<Integer, Integer> newValue = new AbstractMap.SimpleEntry<>(key, val+1); //atualiza o número de pedidos referentes ao server selecionado
            serverPool.put(res, newValue);
        } finally {
            lockPool.unlock();
        }

        return res;
    }

    private static class  UDPListener implements Runnable {
        private DatagramSocket socket;

        public UDPListener(DatagramSocket s) {
            socket = s;
        }

        public void run ()  {
            try {

                new Thread(() -> {
                    while(true) {
                        try {
                            lock.lock();

                            while (httpConnections.size() == 0) //quando não tem pedidos fica adormecido
                                cond.await();//Espera até que seja adicionada uma nova conexão/pedidos ao map

                            System.out.println("Vou enviar pedido ao fastfileSrv");

                            //Considerando que vamos remover o que já estiver tratado
                            for(Map.Entry<TCPConnection,List<String>> e : httpConnections.entrySet()) { //percorrer todos os clientes novos para mandar pedidos de ficheiro
                                System.out.println("tamanho httpconnections " + httpConnections.entrySet().size());
                                System.out.println("tamanho : " + e.getValue().size());

                                for (int i = 0; i < e.getValue().size(); i++) { //percorrer a lista de nomes de ficheiros
                                    FSChunkProtocol fs_requestFile = new FSChunkProtocol(e.getValue().get(i), " ".getBytes(), 0, 0, 0,0,0,0, null, 1,e.getKey().getClient());
                                    System.out.println("Pedido do cliente " + e.getKey().getClient() + " e ficheiro: " + e.getValue().get(i));
                                    byte[] requestFile = fs_requestFile.serialize();

                                    //envia pedido de ficheiro para o FastFileSrv menos ocupado
                                    Map.Entry<InetAddress,Integer> server = getServerLessBusy();
                                    DatagramPacket dp = new DatagramPacket(requestFile, requestFile.length, server.getKey(), server.getValue());
                                    socket.send(dp);
                                }
                            }

                            httpConnections.clear(); //limpa o map de pedidos de ficheiro pq ja foram enviados os pedidos

                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlock();
                        }
                    }
                }).start();

                new TimeoutFS(20, socket, httpResponse,lockRes,condRes);

                while (true) { //está à escuta.
                    byte [] inData = new byte[6000];

                    DatagramPacket receivePkt = new DatagramPacket(inData, inData.length);
                    socket.receive(receivePkt);
                    FSChunkProtocol fs_receive = FSChunkProtocol.deserialize(inData);

                    switch(fs_receive.getType()) {
                        case 1: //1 significa que é mensagem para se conectar
                            String [] payload = new String(fs_receive.getPayload()).split("\n");
                            if ("server".equals(payload[0]) && "1234".equals(payload[1])) {
                                //guardar o inetAddress e a porta da conexao
                                InetAddress IP = fs_receive.getAddress();
                                int port = fs_receive.getPort();
                                try {
                                    lockPool.lock();

                                    int max = IPRemover.maxValue(serverPool);
                                    Map.Entry<InetAddress, Integer> par = new AbstractMap.SimpleEntry<>(IP, port);
                                    if (serverPool.get(par) != null)
                                        serverPool.put(par, new AbstractMap.SimpleEntry<>(serverPool.get(par).getKey() + 1, serverPool.get(par).getValue()));
                                    serverPool.putIfAbsent(par, new AbstractMap.SimpleEntry<>(max, 0));


                                    for (Map.Entry<InetAddress, Integer> p : serverPool.keySet()) {

                                        System.out.println(p.getKey());
                                        System.out.println(p.getValue());
                                        System.out.println(serverPool.get(p));
                                    }
					
					
				    for(int i = 2; i < payload.length; i++) {
				    	filesServer.add(payload[i]);
					System.out.println(payload[i]);
				    }				    
 
                                } finally {
                                    lockPool.unlock();
                                }

                                System.out.println("vou guardar esta conexão\n");
                            } else {
                                System.out.println("Conexão inválida\n");
                            }

                            break;
                        case 2: //recebeu um ficheiro

                            HashMap<String,Packet> val = new HashMap<>();
                            int complete = 0; //0 se nao houve retransmissao
                            try {
                                lockRes.lock();

                                for(TCPConnection c : httpResponse.keySet()) {
                                    if (c.getClient() == fs_receive.getClient() && (fs_receive.getFragment() == 0 || fs_receive.getFragment() == 1)) { //se foi recebido um pacote que não vai ser fragmentado ou o primeiro fragmento
                                        System.out.println("Recebi fragmento " + fs_receive.getFragment());

                                        val = httpResponse.get(c);
                                        Packet p = val.get(fs_receive.getFilename()); //ir buscar o packet correspondente ao ficheiro que veio agora no fschunkprotocol
                                        

                                        if (fs_receive.getFragment() == 0) {
						p.setComplete(1); //se não houver fragmentação, o pacote está completo
						p.setPayload(fs_receive.getPayload());
					}
                                        else p.addFragment(fs_receive.getFragment(),0,fs_receive.getPayload());

                                        
                                        val.put(fs_receive.getFilename(),p); //colocar o packet com os bytes
					

                                        //ver os bytes que tinham no map relativos aquele ficheiro e acrescentar com os bytes q estamos a receber agora
                                    } else if(c.getClient() == fs_receive.getClient() && fs_receive.getFragment() > 1) {
                                        System.out.println("Recebi fragmento " + fs_receive.getFragment());
                                        

                                        val = httpResponse.get(c);
                                        Packet p = val.get(fs_receive.getFilename()); //vai buscar o que ja la tinha

                                        byte[] novoFragmento = fs_receive.getPayload();
                                        p.addFragment(fs_receive.getFragment(),fs_receive.getLastFragment(),novoFragmento); //adiciona ao map cada fragmento
                                        p.setTotalFragments(fs_receive.getTotalFragments()); //atualiza o numero de fragmentos

					
                                        if (p.lastFragmentInt() > 0) {   //se existir o ultimo fragmento
                                            List<Integer> lost_fragments = p.fragmentsIncomplete();

                                            if (lost_fragments.size() == 0) {
                                                p.setComplete(1);
                                                complete = 1;
                                            }
                                            /*
                                            else {//precisa de retransmissao
                                                for(int i : lost_fragments) { //enviar pedido de retransmissão daqueles fragmentos
                                                    Thread.sleep(1);
						    FSChunkProtocol fs_requestFile = new FSChunkProtocol(fs_receive.getFilename(), " ".getBytes(), 0, 0, 0,i,0,0, null, 2,fs_receive.getClient());
                                                    System.out.println("Pedido do cliente " + fs_receive.getClient() + " e ficheiro: " + fs_receive.getFilename() + " fragmento: " + i);
                                                    byte[] requestFile = fs_requestFile.serialize();

                                                    //envia pedido de ficheiro para o fastFileSrv menos ocupado
                                                    Map.Entry<InetAddress,Integer> server = getServerLessBusy();
                                                    DatagramPacket dp = new DatagramPacket(requestFile, requestFile.length, server.getKey(), server.getValue());
                                                    socket.send(dp);

                                                }
                                            }*/
                                        }


                                        val.put(fs_receive.getFilename(),p);
                                        httpResponse.put(c,val);
                                    }
                                }

                                
                                if (fs_receive.getFragment() == 0 || fs_receive.getLastFragment() == 1 | complete == 1) condRes.signalAll(); //acordar as threads adormecidas

                            } finally {
                                lockRes.unlock();
                            }

                            break;
                        case 3: //para o ping
                            InetAddress IP = fs_receive.getAddress();
                            int port = fs_receive.getPort();
			    Map.Entry<InetAddress,Integer> ffs = new AbstractMap.SimpleEntry<>(IP,port);

                            try {
                                lockPool.lock();
				
				if(serverPool.containsKey(ffs)) { //se o ping recebido corresponde a um fast file conhecido

				
                                int max = IPRemover.maxValue(serverPool);

                                Map.Entry<InetAddress, Integer> par = new AbstractMap.SimpleEntry<>(IP, port);
                                if (serverPool.get(par) != null)
                                    serverPool.put(par, new AbstractMap.SimpleEntry<>(serverPool.get(par).getKey() + 1, serverPool.get(par).getValue()));
                                serverPool.putIfAbsent(par, new AbstractMap.SimpleEntry<>(max, 0));


                                for (Map.Entry<InetAddress, Integer> p : serverPool.keySet()) {

                                    System.out.println(p.getKey());
                                    System.out.println(p.getValue());
                                    System.out.println(serverPool.get(p));
                                }
				}
                            } finally {
                                lockPool.unlock();
                            }
                            break;                
			default:
                            System.out.println("Tipo de mensagem desconhecida. \n");
                            break;
                    }
                    try {
                        lockPool.lock();
                        new IPRemover(serverPool);
                    } finally {
                        lockPool.unlock();
                    }


                }
            } catch(IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


    private static class  TCPListener implements Runnable {
        private ServerSocket serverSocket;

        public TCPListener(ServerSocket s) {
            serverSocket = s;
        }

        public void run() {
            try {
                while (true) {
                    //recebeu uma conexão na porta 8080
                    Socket socket = serverSocket.accept(); //cria um socket por conexão => Recebeu um cliente e fez uma conexão
                    System.out.println("Aceitou conexão de novo cliente");
                    //criar uma thread para receber multiplos cliente
                    new Thread(() -> {
                        try {
                            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream())); //para receção/leitura
                            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())); //para escrita

                            byte[] data = new byte[1024];
                            List<String> files = new ArrayList<>();

                            in.read(data); 

                            String httpRequest = new String(data); 
                            System.out.println(httpRequest);

                            String[] linesHttpR = httpRequest.split("\n"); //divide o pedido HTTP pelas suas linhas
                            String[] fstLine = linesHttpR[0].split(" ");
                            String fileName = fstLine[1]; //nome do ficheiro desejado

                            System.out.println(fileName);
			    TCPConnection conn = new TCPConnection(in, out, socket, clientCounter);
                            clientCounter++;

                            if (!filesServer.contains(fileName))  { //Caso o ficheiro nao exista 
				out.writeBytes("HTTP/1.1 204 No Content\r\n\r\n");
				out.writeBytes("Ficheiro inexistente");
				out.flush();
				conn.close();
			    }
			    else { //Caso o ficheiro exista
				files.add(fileName); 
                            

                            

                            HashMap<String, Packet> val = new HashMap<>();

                            try {
                                lock.lock();
                                httpConnections.put(conn, files);
                                cond.signalAll(); //fazer signal para a thread
                            } finally {
                                lock.unlock();
                            }

                            try {
                                lockRes.lock();
                                Packet p = new Packet();

                                for (int i = 0; i < files.size(); i++) {
                                    val.put(files.get(i), p);
                                }
                                httpResponse.put(conn, val); 


                                
                                while (httpResponse.get(conn).get(files.get(0)).getComplete() == 0) //se ainda não está completo, fica no await
                                    condRes.await();


                                for (TCPConnection c : httpResponse.keySet()) {
                                    if (c.getClient() == conn.getClient()) {
                                        val = httpResponse.get(c);

                                        out.writeBytes("HTTP/1.1 200 OK\r\n\r\n");

                                        for (Map.Entry<String, Packet> e : val.entrySet()) {
                                            if (e.getValue().lastFragmentInt() > 0) e.getValue().fillPayload();
                                            if (e.getValue().getPayload() != null) out.write(e.getValue().getPayload());
                                        }

                                        out.flush();
                                    } 
                                }

                                httpResponse.remove(conn); //depois de enviar pode remover a conexao

                            } finally {
                                lockRes.unlock();
                            }

                            conn.close();
			}
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    static class TCPConnection {
        private DataInputStream in;
        private DataOutputStream out;
        private Socket socket;
        private int client;

        public TCPConnection(DataInputStream in, DataOutputStream out, Socket socket, int client) {
            this.in = in;
            this.out = out;
            this.socket = socket;
            this.client = client;
        }

        public int getClient() {
            return this.client;
        }

        public void close() throws IOException {
            this.socket.close();
        }

    }

    static class Packet {
        private byte[] payload;
        private int complete; //flag que indica que aquele pacote foi recebido completamente
        private int totalFragments;
        private Map<Integer, Map.Entry<Integer, byte[]>> fragments; //key = nr do fragmento, value = se é o ultimo ou não + bytes

        public Packet(byte[] payload, int complete) {
            this.payload = payload;
            this.complete = complete;
        }

        public Packet() {
            complete = 0; //inicializa a zero porque ainda não tem os dados todos
            fragments = new HashMap<>();
        }

        public byte[] getPayload() {
            return payload;
        }

        public int getComplete() {
            return complete;
        }

        public void setPayload(byte[] payload) {
            this.payload = payload;
        }

        public void setTotalFragments(int total) {
            this.totalFragments = total;
        }

        public int getTotalFragments() { return this.totalFragments; }

        public void setComplete(int complete) {
            this.complete = complete;
        }

        public void addFragment(int nr, int last, byte[] data) {
            Map.Entry<Integer, byte[]> data_fragment = new AbstractMap.SimpleEntry<>(last, data);
            fragments.putIfAbsent(nr, data_fragment);
        }

        public int lastFragmentInt(){
            int res = -1;
            for(Map.Entry<Integer,Map.Entry<Integer,byte[]>> f : this.fragments.entrySet()){
                if(f.getValue().getKey()==1) res = f.getKey();
            }
            return res;
        }

        public List<Integer> fragmentsIncomplete(){
            List<Integer> res = new ArrayList<>();

            //last fragment index
            int fragments = lastFragmentInt();

            for(int i=1; i <= fragments ;i++){
                if( !this.fragments.containsKey(i) ) res.add(i);
            }

            return res;
        }

        public List<Integer> missingFragments() {
            List<Integer> res = new ArrayList<>();

            //last fragment index
            int fragments = totalFragments;

            for(int i=1; i <= fragments ;i++){
                if( !this.fragments.containsKey(i) ) res.add(i);
            }

            return res;
        }

        public void fillPayload() {

            int fragments = lastFragmentInt();
            int countBytes = 0;
            for(int i=1; i <= fragments; i++) {
                byte[] novo = this.fragments.get(i).getValue();
                countBytes += novo.length;
            }

            this.payload = new byte[countBytes];

            for(int i=1; i <= fragments; i++) {
                byte[] novoPayload = this.fragments.get(i).getValue();

                System.arraycopy(novoPayload, 0, payload, (i-1)*5000, novoPayload.length);
            }

        }

    }

}