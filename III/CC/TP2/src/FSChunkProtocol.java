import java.io.*;
import java.net.*;
import java.util.Arrays;

public class FSChunkProtocol implements Serializable {
    private String filename;
    private byte[] payload;
    private long checksum;
    private int totalFragments;
    private int offset;
    private int fragment;
    private int lastFragment;
    //fragmented

    private int port; //porta do processo origem
    private InetAddress address; //endereço do processo origem
    private int type; //tipo da mensagem. ex: 1 = conexão/autenticação
    private int client; //ind

    public FSChunkProtocol(String filename, byte[] payload, long checksum, int totalFragments, int offset, int fragment, int lastFragment, int port, InetAddress address, int type, int client) {
        this.filename = filename;
        this.payload = payload;
        this.payload = payload;
        this.checksum = checksum;
        this.totalFragments = totalFragments;
        this.offset = offset;
        this.fragment = fragment;
        this.lastFragment = lastFragment;
        this.port = port;
        this.address = address;
        this.type = type;
        this.client = client;
    }

    static byte[] parityPayload(byte[] payload){
        String [] words = new String[payload.length];
        for (int i=0; i < payload.length; i++) {
            words[i] = String.format("%8s", Integer.toBinaryString(payload[i] & 0xFF)).replace(' ', '0');
        }
        words = twodParityCheck.encode(words);

        byte[] res = new byte[words.length];
        for(int i=0; i < words.length; i++){
            res[i]= (byte)Integer.parseInt(words[i], 2);
        }

        return res;
    }

    public int getTotalFragments() {
        return this.totalFragments;
    }

    public int getFragment() {
        return this.fragment;
    }

    public int getLastFragment() {
        return this.lastFragment;
    }

    public String getFilename(){
        return filename;
    }

    public int getClient(){
        return client;
    }

    public void setPayload(byte [] payload) {
        this.payload = payload;
    }

    public byte[] getPayload() {
        return payload;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getType() {
        return type;
    }

    public long getChecksum() {
        return checksum;
    }

    public static String[] encode(String[] dataWord)
    {
        int count = 0;
        char[] rRowBit = new char[dataWord.length];
        while(count<dataWord.length)
        {
            rRowBit[count] = dataWord[count].charAt(0);
            for(int i = 1 ; i < dataWord[count].length() ; i++)
            {
                if(rRowBit[count] == dataWord[count].charAt(i))
                {
                    rRowBit[count] = '0';
                }
                else
                {
                    rRowBit[count] = '1';
                }
            }
            dataWord[count] += rRowBit[count];
            count++;
        }
        System.out.print("Row parities: ");
        String RowBit = "";
        for(int i = 0 ; i<rRowBit.length ; i++)
        {
            RowBit += rRowBit[i];
            System.out.print(rRowBit[i]);
        }
        System.out.println();

        count = 0;
        char rColBit[] = new char[dataWord[0].length()];
        String ColBit = "";
        while(count<dataWord[0].length())
        {
            rColBit[count] = dataWord[0].charAt(count);
            for(int i = 1 ; i < dataWord.length ; i++)
            {
                //System.out.println(dataWord[i].charAt(count));
                if(rColBit[count] == dataWord[i].charAt(count))
                {
                    rColBit[count] = '0';
                }
                else
                {
                    rColBit[count] = '1';
                }
            }
            count++;
        }
        System.out.print("Column parities: ");
        for(int i = 0 ; i<rColBit.length ; i++)
        {
            ColBit += rColBit[i];
            System.out.print(rColBit[i]);
        }
        System.out.println();

        String code[] = new String[dataWord.length+1];
        String codeWord = "";

        for(int i = 0 ; i<code.length ; i++)
        {
            if( i == dataWord.length)
            {
                code[i] = ColBit;
            }
            else
            {
                code[i] = dataWord[i];
            }
        }
        System.out.print("Codeword: ");
        for(int i = 0 ; i<code.length ; i++)
        {
            System.out.print(code[i] + " ");
            codeWord += code[i];
        }

        String[] CodeWord = new String[3];
        for(int i = 0 ; i<3 ; i++)
        {
            switch (i) {
                case 0:
                    CodeWord[i] = codeWord;
                    break;
                case 1:
                    CodeWord[i] = RowBit;
                    break;
                default:
                    CodeWord[i] = ColBit;
                    break;
            }
        }
        return CodeWord;
    }

    byte[] serialize() throws IOException {

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream);
        oo.writeObject(this);
        oo.close();

        byte[] serializedMessage = bStream.toByteArray();

        return serializedMessage;

    }

    static FSChunkProtocol deserialize(byte[] recBytes) throws IOException, ClassNotFoundException {
        ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(recBytes));
        FSChunkProtocol messageClass = (FSChunkProtocol) iStream.readObject();
        iStream.close();

        return messageClass;
    }

    public static byte[] trailZeros(byte[] packet)
    {
        int i = packet.length - 1;
        while( i > 0 && packet[i] == 0)
        {
            --i;
        }
        byte[] temp = new byte[i + 1];
        System.arraycopy(packet,0, temp, 0, i + 1);

        return temp;
    }

public static int errorCheck(FSChunkProtocol fs){
        byte[] payload = fs.getPayload();
        int totalErrors = 0;

        long cs = ChecksumFS.getCRC32Checksum(payload);

        if(fs.getChecksum()==cs){
            System.out.println("Checksum checked! No errors!");
        }
        else{
            System.out.println("Checksum error detected!");
            totalErrors++;
        }


        String[] res = new String[payload.length];
        for(int i=0; i<payload.length; i++){
            res[i] = String.format("%9s", Integer.toBinaryString(payload[i] & 0xFF)).replace(' ', '0');
        }

        int errors = twodParityCheck.checkError(res);

        if(errors == 0){
            System.out.println("Received packet without errors!");
        }
        else if(errors == 1){
            System.out.println("Found and corrected error in packet!");
        }
        else{
            totalErrors++;
            //Retransmição
        }

        //Payload sem bits paridade
        res = twodParityCheck.decode(res);

        byte[] newPayload = new byte[res.length];
        for(int i=0; i < res.length; i++) {
            int a = Integer.parseInt(res[i], 2);
            newPayload[i] = (byte) a;
        }

        fs.setPayload(newPayload);

        return totalErrors;
    }
}
