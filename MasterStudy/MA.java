package study5;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.java.sen.StringTagger;
import net.java.sen.Token;


public class MA {

	public MA() {
		System.setProperty("sen.home","s:/sen/");
	}

	public List<String> analyze(String path) {
		List<String> result = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
			String text;
			while((text=br.readLine())!=null){
				List<String> element = getEraseMA(text);
				StringBuilder sb = new StringBuilder();
				for(String e : element){
					sb.append(e);
					sb.append(",");
				}
				if(sb.toString().length()<1){
					result.add("");
					continue;
				}
				result.add(sb.toString().substring(0, sb.length()-1));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<String> getEraseMA(String text) {
		List<String> element = new ArrayList<String>();
		try{
			StringTagger tagger = StringTagger.getInstance();
			Token[] token = tagger.analyze(text);
			for (int i = 0; i < token.length; i++) {
				if(!token[i].getPos().contains("記号")&&!token[i].getPos().contains("数")&&token[i].getPos().contains("名詞")){
					element.add(token[i].toString());
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return element;
	}

	private List<String> getNounMA(String text) {
		List<String> element = new ArrayList<String>();
		try{
			StringTagger tagger = StringTagger.getInstance();
			Token[] token = tagger.analyze(text);
			for (int i = 0; i < token.length; i++) {
				if(	token[i].getPos().contains("名詞")){
					element.add(token[i].toString());
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return element;
	}

	private List<String> getNormalMA(String text) {
		List<String> element = new ArrayList<String>();
		try{
			StringTagger tagger = StringTagger.getInstance();
			Token[] token = tagger.analyze(text);
			for (int i = 0; i < token.length; i++) {
					element.add(token[i].toString());
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return element;
	}
}
