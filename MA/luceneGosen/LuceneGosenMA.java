import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.java.sen.SenFactory;
import net.java.sen.StringTagger;
import net.java.sen.dictionary.Token;

public class LuceneGosenMA {

	public static void main(String[] args) throws IOException {
	    
	    /**
	     * ライブラリは lucene-gosen-4.4.0-ipadic.jarを使用
	     */
	    
	    // StringTagger 初期化
	    StringTagger tagger = SenFactory.getStringTagger(null);
	    // Token リスト用意
	    List<Token> tokens = new ArrayList<Token>();
	    // 形態素解析
	    tagger.analyze("すもももももももものうち", tokens);
	    
	    // 解析結果の中身をいろいろ出力してみる
	    for (Token token : tokens) {
		System.out.println("==============================");
		System.out.println("surface : " + token.getSurface());
		System.out.println("cost : " + token.getCost());
		System.out.println("長さ : " + token.getLength());
		System.out.println("start : " + token.getStart());
		System.out.println("additionalInformation : " + token.getMorpheme().getAdditionalInformation());
		System.out.println("basicForm : " + token.getMorpheme().getBasicForm());
		System.out.println("conjugationalForm : " + token.getMorpheme().getConjugationalForm());
		System.out.println("conjugationalType : " + token.getMorpheme().getConjugationalType());
		System.out.println("partOfSpeech : " + token.getMorpheme().getPartOfSpeech());
		System.out.println("pronunciations : " + token.getMorpheme().getPronunciations());
		System.out.println("readings : " + token.getMorpheme().getReadings());
	    }
	}
}