import java.io.Serializable;

@SuppressWarnings("serial")
public class Car implements Serializable {

    public String name;
    
    public Car(String _name) {
	name = _name;
    }
    
    public void print(){
	System.out.println(name);
    }
    
    public void setName(String _name){
	name = _name;
    }
    
}
