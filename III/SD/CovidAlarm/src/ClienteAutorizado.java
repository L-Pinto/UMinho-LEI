import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienteAutorizado {

    public static void menuCliente() throws Exception {

        Socket s = new Socket("localhost", 56789);
        TaggedConnection tca = new TaggedConnection(s);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String opcao;
        System.out.println("\033[1;32m" + "*** ALARME COVID ***\n");

        do {
            System.out.println("\033[1;32m" + "*** CLIENTE AUTORIZADO ***");
            System.out.println("0" + "\033[0m" + " Sair");
            System.out.println("\033[1;32m" + "1" + "\033[0m" + " Descarregar mapa");
            System.out.print("\u001B[1;32m" + "=> " + "\u001B[0m");
            opcao = in.readLine();

            switch (opcao) {
                case "1": {
                    tca.sendDescargaMapa(8);
                    // get reply
                    TaggedConnection.FrameCliente frameCliente = tca.receiveCliente();
                    assert frameCliente.tag == 8;
                    String msg = new String(frameCliente.data);
                    System.out.println(msg + "\n");
                    break;
                }
                case "0": {
                    tca.close();
                    break;
                }
                default: {
                    System.out.println("\033[1;31m" + "Error\n" + "\033[0m");
                    break;
                }
            }
        }while (!opcao.equals("0"));
    }

    public static void main(String[] args) {

        Runnable worker = () -> {
            try {
                menuCliente();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(worker).start();
    }
}
