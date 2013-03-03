import java.util.Map;
import gnu.trove.map.hash.THashMap;

public class TroveSample {

    public static void main(String[] args) {
	Map<String, String> map = new THashMap<String, String>();
	map.put("りんご", "apple");
	map.put("もも", "peach");
	for(String key : map.keySet()){
	    System.out.println(key+" "+map.get(key));
	}
    }
}
