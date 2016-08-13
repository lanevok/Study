package study5;

import java.util.ArrayList;
import java.util.List;

public class Ngram {

	public List<String> getList(List<String> list) {
		List<String> result = new ArrayList<String>();
		for(String str : list){
			result.add(getNgram(str));
		}
		return result;
	}

	private String getNgram(String str) {
		StringBuilder sb = new StringBuilder();

		String[] split = str.split(",");
		int length = split.length;
		for(int s=0;s<length;s++){
			for(int g=s;g<length;g++){
				if(g-s>=10) continue;	// 11つ以上の形態素は結合しない
				if(!split[s].contains("s*")||!split[g].contains("*g")) continue;
				StringBuilder sb2 = new StringBuilder();
				for(int i=s;i<=g;i++){
					sb.append(split[i]);
				}
//				if(!tma.isNoun(sb2.toString())){
//					continue;
//				}
				sb.append(sb2);
				sb.append(",");
			}
		}
		if(sb.toString().length()<1) return "";
		return sb.toString().substring(0, sb.length()-1);
	}
}
