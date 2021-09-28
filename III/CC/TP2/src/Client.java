import java.io.*;
import java.net.Socket;

public class Client {

    public static void main (String[] args) throws IOException {
        Socket socket = new Socket("localhost",8080); //envia pedidos para a porta 8080

        //ler texto do system in
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //escrever em binário para o socket
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        String userInput;
        //enquanto tiver coisas para ler, vai criando um novo contacto
        while ((userInput = in.readLine()) != null) {
            out.writeInt(userInput.getBytes().length);
            out.write(userInput.getBytes());

            out.flush();

            System.out.println(userInput);
        }

        socket.close();

    }

}
