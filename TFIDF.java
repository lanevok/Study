import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/*
 * TF-IDF Calculator
 * 
 * TF-IDF(Term Frequency - Inverse Document Frequency)を計算するJavaプログラム
 * 
 * Author : T.Koike (lanevok)
 * Date : 2012/10/18
 * Version : 11
 * 
 * [補足]
 * 　TF-IDF = 文書中の単語に関する重み。重みは文章を代表する単語(特徴語)
 * 　TF = その文章において、単語の出現回数
 * 　DF = すべての文章において、単語の出現文書数
 * 　計算は、TF*log(文書数/DF) 。　※底はe
 */

public class TFIDF {
    
    /** Setting ***************************************************/
    // 1.カレントディレクトリパス
    final String currentDirectory = "C:/Users/UserName/workspace/Project/";
    // 2.入力ファイル名
    final String[] inputFileName = {
	"0.txt","1.txt","2.txt","3.txt","4.txt","5.txt","6.txt","7.txt","8.txt","9.txt"
    };
    // 3.ファイル出力有効化
    final boolean fileOutput = true;
    // 4.入力ファイル文字コード
    final String code = "MS932";		// Shift-JIS
    /************************************************************/
    
    String word;
    BufferedReader br;
    HashMap<String,Integer> DF_MAP;
    
    // TFを調べ TF-IDFを計算し出力する
    private void TF_Output() throws IOException{
	HashMap<String,Integer> TF_MAP;
	Iterator<String> it;
	double valueTFIDF;
	
	if(fileOutput) new File(currentDirectory+"/result").mkdirs();
	for(int i=0;i<inputFileName.length;i++){
	    System.err.println("TF Doc : "+inputFileName[i]);
	    br = new BufferedReader(new InputStreamReader(new FileInputStream(currentDirectory+inputFileName[i]), code));
	    if(fileOutput) System.setOut(new PrintStream(new File("result/"+inputFileName[i])));
	    TF_MAP = new HashMap<String,Integer>();		// Document毎に初期化
	    
	    while((word=br.readLine())!=null) TF_MAP.put(word, TF_MAP.containsKey(word)?TF_MAP.get(word)+1:1);
	    if(!fileOutput) System.out.println(TF_MAP.toString());
	    it = TF_MAP.keySet().iterator();		// TF_MAPのkeyとなるwordをSetとして保有させる
	    while(it.hasNext()){
		word = it.next();
		valueTFIDF = TF_MAP.get(word)*Math.log(1.0*inputFileName.length/DF_MAP.get(word));
		System.out.println(word+"\t"+valueTFIDF);
	    }
	}
    }
    
    // DF_MAPを作成する
    private void DF_Create() throws IOException{
	DF_MAP = new HashMap<String,Integer>();
	HashSet<String> DF_SET;		// DF_MAPを求める際に補助する集合
	
	for(int i=0;i<inputFileName.length;i++){
	    System.err.println("DF Doc : "+inputFileName[i]);
	    br = new BufferedReader(new InputStreamReader(new FileInputStream(currentDirectory+inputFileName[i]), code));
	    DF_SET = new HashSet<String>();		// Document毎に初期化
	    
	    while((word=br.readLine())!=null){
		// DF_MAPにkeyとなるwordの値をインクリメントするが
		// 　一度でも同じDocumentにwordが出てきているかを
		// 　　DF_SETを見て確認し、出ていればスルーする
		if(!DF_SET.contains(word)){
		    DF_SET.add(word);
		    DF_MAP.put(word, DF_MAP.containsKey(word)?DF_MAP.get(word)+1:1);
		}
	    }
	}
	if(!fileOutput) System.out.println(DF_MAP.toString());
    }
    
    public static void main(String[] args) throws IOException {
	long startTime = System.currentTimeMillis();
	TFIDF tfidf = new TFIDF();
	
	tfidf.DF_Create();		// DF_MAPを作成する
	tfidf.TF_Output();		// TFを調べ TF-IDFを計算し出力する
	System.err.println("----- Finish -----");
	System.err.println("Time : "+((System.currentTimeMillis()-startTime)*1.0/1000)+" sec");
    }
}
