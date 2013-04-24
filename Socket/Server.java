import java.io.*;
import java.net.*;

public class Server implements Runnable {

    private Socket sock_ = null;
    
    public Server(Socket sock) {
	this.sock_ = sock;
    }
    
    protected void finalize() {
	try {
	    sock_.close();
	} catch (Exception e) {}
    }
    
    public void run() {
	try {
	    DataInputStream in = new DataInputStream(sock_.getInputStream());
	    int sum = Integer.valueOf(in.readUTF())+Integer.valueOf(in.readUTF());
	    DataOutputStream out = new DataOutputStream(sock_.getOutputStream());
	    out.writeUTF(String.valueOf(sum));
	    sock_.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    public static void main(String[] args) throws NumberFormatException, IOException {
	String port = "64998";
	ServerSocket svsock = new ServerSocket(Integer.parseInt(port));
	for (;;) {
	    Socket sock = svsock.accept();
	    Server sv = new Server(sock);
	    Thread tr = new Thread(sv);
	    tr.start();
	}
    }
}
