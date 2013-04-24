import java.io.*;
import java.net.*;
import java.util.Random;

public class Client {
    public static void main(String[] args) throws NumberFormatException, IOException {
	String host = "localhost";
	String port = "64998";
	
	Socket sock = new Socket();
	sock.connect(new InetSocketAddress(host, Integer.parseInt(port)));
	
	DataOutputStream out = new DataOutputStream(sock.getOutputStream());
	Random rdm = new Random();
	int a = rdm.nextInt(1000);
	int b = rdm.nextInt(1000);
	out.writeUTF(String.valueOf(a));
	out.writeUTF(String.valueOf(b));
	
	DataInputStream in = new DataInputStream(sock.getInputStream());
	System.out.println(a+"+"+b+"="+in.readUTF());
	
	sock.close();
    }
}
