import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;

public class TFIDF {
    
    final String currentDirectory = "C:/Users/UserName/workspace/Project/";
    final String[] inputFileName = {
	"0.txt","1.txt","2.txt","3.txt","4.txt","5.txt","6.txt","7.txt","8.txt","9.txt"
    };
    final boolean fileOutput = true;
    final String code = "MS932";
    
    String word;
    BufferedReader br;
    HashMap<String,Integer> DF_MAP;
    
    private void Calc() throws IOException{
	DF_MAP = new HashMap<String,Integer>();
	HashMap<String,Integer>[] TF_MAP = new HashMap[inputFileName.length];
	Iterator<String> it;
	double valueTFIDF;
	
	for(int i=0;i<inputFileName.length;i++){
	    TF_MAP[i] = new HashMap<String, Integer>();
	    System.err.println("TF,DF Doc : "+inputFileName[i]);
	    br = new BufferedReader(new InputStreamReader(new FileInputStream(currentDirectory+inputFileName[i]), code));
	    while((word=br.readLine())!=null){
		if(!TF_MAP[i].containsKey(word)){
		    TF_MAP[i].put(word, 1);
		    DF_MAP.put(word, DF_MAP.containsKey(word)?DF_MAP.get(word)+1:1);
		}
		else{
		    TF_MAP[i].put(word, TF_MAP[i].get(word)+1);
		}		
	    }
	}
	if(fileOutput) new File(currentDirectory+"/result").mkdirs();
	for(int i=0;i<inputFileName.length;i++){
	    br = new BufferedReader(new InputStreamReader(new FileInputStream(currentDirectory+inputFileName[i]), code));
	    if(fileOutput) System.setOut(new PrintStream(new File("result/"+inputFileName[i])));
	    System.err.println("Output Doc : "+inputFileName[i]);
	    it = TF_MAP[i].keySet().iterator();	
	    while(it.hasNext()){
		word = it.next();
		valueTFIDF = TF_MAP[i].get(word)*Math.log(1.0*inputFileName.length/DF_MAP.get(word));
		System.out.println(word+"\t"+valueTFIDF);
	    }
	    TF_MAP[i].clear();
	}
    }
    
    public static void main(String[] args) throws IOException {
	long startTime = System.currentTimeMillis();
	TFIDF tfidf = new TFIDF();
	
	tfidf.Calc();
	System.err.println("----- Finish -----");
	System.err.println("Time : "+((System.currentTimeMillis()-startTime)*1.0/1000)+" sec");
    }
}
