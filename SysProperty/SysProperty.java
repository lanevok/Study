import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class SysProperty {
	
    public static void main(String[] args) {
	PropertyOut();
    }
    
    public static void PropertyOut() {
	HashMap<Object, Object> hm = new HashMap<Object, Object>();
	Properties p = System.getProperties();
	hm.putAll(p);
	Iterator<Object> it = hm.keySet().iterator();
	int i = 0;
	while (it.hasNext()) {
	    String key = (String)it.next();
	    String val = (String)hm.get(key);
	    System.err.println(++i+"【" + key + "】" + val);
	}
	System.err.println();
    }
}
