import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramSocket;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map;



public class FastFileSrv {
    private Map<String,String> data;


    public static void main(String[] args) throws IOException{
        // args[0] é o IP
        // args[1] é a porta

        if (args.length < 4) { //se nao recebe argumentos suficientes, acaba o processo. tem q receber ip, porta, username e password
            System.exit(0);
        }

	
	InetAddress inetA_aux = null;


	Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
	for(NetworkInterface netint : Collections.list(nets)) {
		if (!netint.isLoopback() || !netint.isUp()) {
        		Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
	
			for(InetAddress inetA : Collections.list(inetAddresses) ) {
				if (inetA instanceof Inet4Address) 
					inetA_aux = inetA;
			}
		}
	}

	final InetAddress inetAddress = inetA_aux;


        DatagramSocket socket = new DatagramSocket(8888); 
        InetAddress addressDest = InetAddress.getByName(args[0]); //vê qual é o inetAddress do servidor ao qual se quer conectar
    
        int portDest = Integer.parseInt(args[1]); //porta à qual ele se quer conectar
        new Ping(5,socket,addressDest,inetAddress);
        
        new Thread(() -> {
            try {

                while(true) {
                    //escuta por pedidos provenientes do server
                    byte[] inData = new byte[12345];
                    DatagramPacket receivePkt = new DatagramPacket(inData, inData.length);
                    socket.receive(receivePkt); 


                    FSChunkProtocol fs_receive = FSChunkProtocol.deserialize(receivePkt.getData());
                    System.out.println("Vou ver se tenho este ficheiro: " + fs_receive.getFilename());

                    String path = System.getProperty("user.dir") + "/files" + fs_receive.getFilename();
                    path = path.replace("\\","/");
                    File file = new File(path);

                    byte[] bytes = new byte[(int) file.length()];

                    FileInputStream fis = null;
                    try {

                        fis = new FileInputStream(file);

                        //read file into bytes[]
                        fis.read(bytes);


                    } catch(FileNotFoundException e) {
                        String fileNF = "File not found!";
                        FSChunkProtocol response = new FSChunkProtocol(fs_receive.getFilename(), fileNF.getBytes(), 0, 0, 0,0,0, socket.getLocalPort(),inetAddress, 4, fs_receive.getClient());
                        byte[] dataResponse = response.serialize();
			
			System.out.println("Ficheiro não encontrado");

                        DatagramPacket pktResponse = new DatagramPacket(dataResponse, dataResponse.length, addressDest, portDest);
                        socket.send(pktResponse);
                        break;

                    } finally {
                        if (fis != null) {
                            fis.close();
                        }
                    }

                    switch(fs_receive.getType()) {
                        case 1: //pedido normal
                            int packetNr = 1;
                            if(bytes.length > 5000) {
                                packetNr = (int) Math.ceil(bytes.length / 5000.0);
                                for(int i = 0; i < packetNr; i++){
                                    //Thread.sleep(1);

                                    if(i!=packetNr-1) {
                                        System.out.println("Mandei " + i);

                                        byte[] fragmented = new byte[5000];
                                        System.arraycopy(bytes, i * 5000, fragmented, 0, 5000);
                                        FSChunkProtocol response = new FSChunkProtocol(fs_receive.getFilename(), fragmented, 0, packetNr, i * 5000, i+1,0,0, null, 2, fs_receive.getClient());
                                        byte[] dataResponse = response.serialize();

                                        DatagramPacket pktResponse = new DatagramPacket(dataResponse, dataResponse.length, addressDest, portDest);
                                        socket.send(pktResponse);
                                    } else {
                                        System.out.println("Mandei " + i);

                                        byte[] fragmented = new byte[bytes.length%5000];

                                        System.arraycopy(bytes,i*5000, fragmented, 0, bytes.length%5000);

                                        FSChunkProtocol response = new FSChunkProtocol(fs_receive.getFilename(), fragmented, 0, packetNr, i * 5000, i + 1, 1, 0, null, 2, fs_receive.getClient());
                                        byte[] dataResponse = response.serialize();

                                        DatagramPacket pktResponse = new DatagramPacket(dataResponse, dataResponse.length, addressDest, portDest);
                                        socket.send(pktResponse);

                                    }

                                }
                            }

                            else { //fragment = 0 significa que não houve fragmentação
                                FSChunkProtocol response = new FSChunkProtocol(fs_receive.getFilename(), bytes, 0, 0, 0, 0,0,0, null, 2, fs_receive.getClient());
                                byte[] dataResponse = response.serialize();
				System.out.println("Vou mandar " + bytes.length + " bytes");

                                DatagramPacket pktResponse = new DatagramPacket(dataResponse, dataResponse.length, addressDest, portDest);
                                socket.send(pktResponse);
                            }

                            break;
                        case 2: //pedido de apenas uma parte do ficheiro

                            int numberFragments = (int) Math.ceil(bytes.length / 5000.0);
                            int nrFragment = fs_receive.getFragment(); //numero do fragmento pretendido
                            int lastFragment = fs_receive.getLastFragment();

                            int size = 0;
                            if (lastFragment == 0) size = 5000; else if (lastFragment == 1) size = bytes.length%5000;

                            byte[] fragmented = new byte[size];

                            System.out.println("Vou enviar o fragmento: " + nrFragment);

                            if (lastFragment == 1) System.arraycopy(bytes,(nrFragment-1)*5000, fragmented, 0, bytes.length%5000);
                            else if (lastFragment == 0) System.arraycopy(bytes,(nrFragment-1)*5000, fragmented, 0, 5000);

                            FSChunkProtocol response = new FSChunkProtocol(fs_receive.getFilename(), fragmented, 0, numberFragments, (nrFragment-1)*5000, nrFragment,lastFragment,0, null, 2, fs_receive.getClient());
                            byte[] dataResponse = response.serialize();

                            DatagramPacket pktResponse = new DatagramPacket(dataResponse, dataResponse.length, addressDest, portDest);
                            socket.send(pktResponse);

                            break;
                        default:
                            break;
                    }


                }

            }
            catch (SocketException ignore) {}
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //Tentar conectar-se ao servidor enviando uma mensagem de conexão
        String username = args[2];
        String password = args[3];
        String connection = username + "\n" + password + "\n" ; //envia uma string com o username e a password
	
	File folder = new File(System.getProperty("user.dir") + "/files");
	File[] listOfFiles = folder.listFiles();

	StringBuilder sb = new StringBuilder();
	sb.append(connection);

	for(File file : listOfFiles) {
		if(file.isFile()) {
			System.out.println(file.getName());
			sb.append("/" + file.getName()+"\n");
		}
	}	

	
	String files = sb.toString(); 	


        FSChunkProtocol FSC_connection = new FSChunkProtocol(" ",files.getBytes(),0,0,0,0,0,socket.getLocalPort(),inetAddress,1,-1); //cria um pacote FSC_connection para enviar
	
        //cria um novo pacote FSChunkProtocol
        byte [] data_connection = FSC_connection.serialize(); //transforma o FSChunkProtocol em byte array

        DatagramPacket connectionPacket = new DatagramPacket(data_connection,data_connection.length, addressDest,portDest); //envia para aquela porta e endereço
        socket.send(connectionPacket); //enviar o pacote FSChunkProtocol
    }

}




