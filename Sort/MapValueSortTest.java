import java.util.HashMap;
import java.util.Map;

import study.KoikeLibrary;

/**
 * MapのValueSortライブラリ、テストプログラム
 * @author (TAT)chaN
 * @since 2014.10.29

 [output]
 {りんご=apple, メロン=melon, みかん=orange}
 {りんご=apple, メロン=melon, みかん=orange}
 {みかん=orange, メロン=melon, りんご=apple}
 {りんご=300, メロン=200, みかん=100}
 {りんご=300, メロン=200, みかん=100}
 {みかん=100, メロン=200, りんご=300}
 {りんご=300.5, メロン=200.3, みかん=100.2}
 {りんご=300.5, メロン=200.3, みかん=100.2}
 {みかん=100.2, メロン=200.3, りんご=300.5}
 {300=apple, 200=melon, 100=orange}
 {300=apple, 200=melon, 100=orange}
 {100=orange, 200=melon, 300=apple}
 {3=789, 1=456, 2=123}
 {3=789, 1=456, 2=123}
 {2=123, 1=456, 3=789}
 {1=300.5, 3=200.3, 2=100.2}
 {1=300.5, 3=200.3, 2=100.2}
 {2=100.2, 3=200.3, 1=300.5}
 {300.5=apple, 200.3=melon, 100.2=orange}
 {300.5=apple, 200.3=melon, 100.2=orange}
 {100.2=orange, 200.3=melon, 300.5=apple}
 {300.5=789, 200.3=456, 100.2=123}
 {300.5=789, 200.3=456, 100.2=123}
 {100.2=123, 200.3=456, 300.5=789}
 {1.1=300.5, 3.3=200.3, 2.2=100.2}
 {1.1=300.5, 3.3=200.3, 2.2=100.2}
 {2.2=100.2, 3.3=200.3, 1.1=300.5}

 *
 */
public class MapValueSortTest {

    public static void main(String[] args) {
	Map map = new HashMap<>();
	map.put("りんご", "apple");
	map.put("みかん", "orange");
	map.put("メロン", "melon");
	System.out.println(KoikeLibrary.getMapValueSort(map));
	System.out.println(KoikeLibrary.getMapValueSort(map,false));
	System.out.println(KoikeLibrary.getMapValueSort(map,true));
	map.clear();
	map.put("りんご", 300);
	map.put("みかん", 100);
	map.put("メロン", 200);
	System.out.println(KoikeLibrary.getMapValueSort(map));
	System.out.println(KoikeLibrary.getMapValueSort(map,false));
	System.out.println(KoikeLibrary.getMapValueSort(map,true));
	map.clear();
	map.put("りんご", 300.5);
	map.put("みかん", 100.2);
	map.put("メロン", 200.3);
	System.out.println(KoikeLibrary.getMapValueSort(map));
	System.out.println(KoikeLibrary.getMapValueSort(map,false));
	System.out.println(KoikeLibrary.getMapValueSort(map,true));
	map.clear();
	map.put(300, "apple");
	map.put(100, "orange");
	map.put(200, "melon");
	System.out.println(KoikeLibrary.getMapValueSort(map));
	System.out.println(KoikeLibrary.getMapValueSort(map,false));
	System.out.println(KoikeLibrary.getMapValueSort(map,true));
	map.clear();
	map.put(1, 456);
	map.put(2, 123);
	map.put(3, 789);
	System.out.println(KoikeLibrary.getMapValueSort(map));
	System.out.println(KoikeLibrary.getMapValueSort(map,false));
	System.out.println(KoikeLibrary.getMapValueSort(map,true));
	map.clear();
	map.put(1, 300.5);
	map.put(2, 100.2);
	map.put(3, 200.3);
	System.out.println(KoikeLibrary.getMapValueSort(map));
	System.out.println(KoikeLibrary.getMapValueSort(map,false));
	System.out.println(KoikeLibrary.getMapValueSort(map,true));
	map.clear();
	map.put(300.5, "apple");
	map.put(100.2, "orange");
	map.put(200.3, "melon");
	System.out.println(KoikeLibrary.getMapValueSort(map));
	System.out.println(KoikeLibrary.getMapValueSort(map,false));
	System.out.println(KoikeLibrary.getMapValueSort(map,true));
	map.clear();
	map.put(300.5, 789);
	map.put(100.2, 123);
	map.put(200.3, 456);
	System.out.println(KoikeLibrary.getMapValueSort(map));
	System.out.println(KoikeLibrary.getMapValueSort(map,false));
	System.out.println(KoikeLibrary.getMapValueSort(map,true));
	map.clear();
	map.put(1.1, 300.5);
	map.put(2.2, 100.2);
	map.put(3.3, 200.3);
	System.out.println(KoikeLibrary.getMapValueSort(map));
	System.out.println(KoikeLibrary.getMapValueSort(map,false));
	System.out.println(KoikeLibrary.getMapValueSort(map,true));
    }
}
