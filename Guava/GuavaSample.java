import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;


public class GuavaSample {

    public static void main(String[] args) {
	
	/**
	 * ライブラリはguava-15.0.jarを使用
	 */
	
	Table<Integer, Integer, String> table = HashBasedTable.create();
	table.put(1, 1, "11");
	System.out.println(table.get(1, 1));
    }
    
}
