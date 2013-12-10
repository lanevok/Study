import java.io.IOException;

import net.java.sen.StringTagger;
import net.java.sen.Token;

public class MorphologicalAnalysis {

    public static void main(String[] args) throws IOException {
	
	/**
	 * sen
	 *
	 * ライブラリは、
	 * sen.jar
	 * commons-logging.jar
	 * junit.jar
	 */
	
	System.setProperty("sen.home","s:/sen/");
	StringTagger tagger = StringTagger.getInstance();
	String str = "すもももももももものうち";
	Token[] token = tagger.analyze(str);
	for (int i = 0; i < token.length; i++) {
	    System.out.println(token[i].getBasicString()+"\t\t"+token[i].getTermInfo());
	}
    }
}