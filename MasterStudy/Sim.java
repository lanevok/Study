package study5;

import java.util.HashMap;
import java.util.Map;

import study.KoikeLibrary.MyBufferedReader;

public class Sim {
	Map<String, Double> map;

	public Sim() {
		MyBufferedReader br = new MyBufferedReader("study4/kohaku_sim2.txt");
		map = new HashMap<String, Double>();
		String line;
		while((line=br.readLine())!=null){
			String[] word = line.split(",");
			if(map.containsKey(word[1]+","+word[0])) continue;
			try {
				map.put(word[0]+","+word[1],Double.parseDouble(word[2]));
			} catch (Exception e) {

			}
		}
//		map = (Map<String, Double>) KoikeLibrary.getMapValueSort(map);
		/*int cnt = 0;
		for(String str : map.keySet()){
			if(cnt++%10000==0){
				System.out.println(str.replaceAll(",","\t")+"\t"+map.get(str));
			}
		}*/
	}

	public double getSim(String w1, String w2){
		if(w1.equals(w2)){
			return 1.0;
		}
		else if(map.containsKey(w1+","+w2)){
			return map.get(w1+","+w2);
		}
		else if(map.containsKey(w2+","+w1)){
			return map.get(w2+","+w1);
		}
		else{
			return 0.0;
		}
	}
	/*
	public static void main(String[] args) {
		MyBufferedReader br = new MyBufferedReader("study4/kohaku_sim2.txt");
		Map<String, Double> map = new HashMap<String, Double>();
		String line;
		while((line=br.readLine())!=null){
			String[] word = line.split(",");
			if(map.containsKey(word[1]+","+word[0])) continue;
			try {

				map.put(word[0]+","+word[1],Double.parseDouble(word[2]));
			} catch (Exception e) {

			}
		}
		map = (Map<String, Double>) KoikeLibrary.getMapValueSort(map);
		int cnt = 0;
		for(String str : map.keySet()){
			if(cnt++%10000==0){
				System.out.println(str.replaceAll(",","\t")+"\t"+map.get(str));
			}
		}

	}
*/
}
