import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class ObjectServer {

    public static void main(String argv[]) throws Exception {
	
	ServerSocket serverSocket = new ServerSocket(5555);
	
	while(true) {
	    Socket s = serverSocket.accept();
	    
	    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
	    Car car = (Car) (ois.readObject());

	    car.setName("BMW");
	    
	    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
	    oos.writeObject(car);
	    
	    s.close();
	}
    }
}