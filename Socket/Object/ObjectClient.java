import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class ObjectClient {

    public static void main(String[] args) throws Exception {
	
	Car car = new Car("BM");
	
	Socket socket = new Socket("localhost", 5555);
	
	ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );
	
	oos.writeObject(car);
	
	ObjectInputStream ois = new ObjectInputStream( socket.getInputStream() );
	car= (Car)( ois.readObject() );
	
	car.print();
	
	socket.close();
    }
}