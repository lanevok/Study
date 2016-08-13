package study5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import study5.Main2.TweetD;

public class ROUGE {

	List<String> source;
	List<String> target;

	public ROUGE(Map<Integer, TweetD> _data, List<TweetD> _picked) {
		source = new ArrayList<String>();
		target = new ArrayList<String>();
		for(Integer id : _data.keySet()){
			TweetD d = _data.get(id);
			source.add(d.ma_super);
		}
		for(TweetD d : _picked){
			target.add(d.ma_super);
		}
	}

	public double getROUGE1(){
		Map<String, Integer> source_key = new HashMap<String, Integer>();
		for(String str : source){
			String[] split = str.split(",");
			for(String w : split){
				if(source_key.containsKey(w)){
					source_key.put(w, source_key.get(w)+1);
				}
				else{
					source_key.put(w, 1);
				}
			}
		}
		Map<String, Integer> target_key = new HashMap<String, Integer>();
		for(String str : target){
			String[] split = str.split(",");
			for(String w : split){
				if(target_key.containsKey(w)){
					target_key.put(w, target_key.get(w)+1);
				}
				else{
					target_key.put(w, 1);
				}
			}
		}
		return calc(source_key, target_key);
	}

	private double calc(Map<String, Integer> source_key, Map<String, Integer> target_key) {
		double deno = 0.0;
		for(String str : source_key.keySet()){
			int value = source_key.get(str);
			deno += value;
		}
		double nume = 0.0;
		for(String str : target_key.keySet()){
			int value = target_key.get(str);
			nume += Math.min(value, source_key.get(str));
		}

//		for(String str : source_key.keySet()){
//			System.out.println(str+"\t"+source_key.get(str));
//		}
//		for(String str : target_key.keySet()){
//			System.out.println(str+"\t"+target_key.get(str));
//		}
		return nume/deno;
	}
}
