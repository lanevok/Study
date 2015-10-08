import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TFIDF_Thread {
    static final String code = "MS932";
    static HashMap<String, Integer>[] TF_MAP = new HashMap[10];
    static HashMap<String, Integer> DF_MAP = new HashMap<String, Integer>(100000);
    static final int TASK = 10;
    static ExecutorService ex = Executors.newFixedThreadPool(TASK);

    public static class Reading implements Runnable{
	private int i;

	public Reading(int id){
	    i = id;
	}

	public void run(){
	    String word;
	    TF_MAP[i] = new HashMap<String, Integer>();
	    BufferedReader br;
	    try {
		br = new BufferedReader(new InputStreamReader(new FileInputStream("input/"+i+".txt"),code));
		while((word=br.readLine())!=null){
		    if(!TF_MAP[i].containsKey(word)){
			TF_MAP[i].put(word, 1);
			int value = 0;
			synchronized (DF_MAP) {
			    if(DF_MAP.containsKey(word)){
				value = DF_MAP.get(word);
			    }
			    DF_MAP.put(word, value+1);
			}
		    }
		    else{
			TF_MAP[i].put(word, TF_MAP[i].get(word)+1);
		    }
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public static class Writing implements Runnable{
	private int i;

	public Writing(int id){
	    i = id;
	}

	public void run(){
	    double valueTFIDF;
	    BufferedWriter bw;
	    try {
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("target/"+i+".txt"),code));
		for(String word : TF_MAP[i].keySet()){
		    valueTFIDF = TF_MAP[i].get(word)*Math.log(10.0/DF_MAP.get(word));
		    bw.write(word+"\t"+valueTFIDF+"\n");
		}
		bw.close();
		TF_MAP[i].clear();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void main(String[] args) {
	long startTime = System.currentTimeMillis();
	start();
	long stopTime = System.currentTimeMillis();
	System.err.println("Time : "+((stopTime-startTime)*1.0/1000)+" sec");
    }

    public static void start() {
	for(int i=0; i<10; i++){
	    ex.execute(new Reading(i));
	}
	ex.shutdown();
	while(!ex.isTerminated());
	ex = Executors.newFixedThreadPool(TASK);
	for(int i=0;i<10;i++){
	    ex.execute(new Writing(i));
	}
	ex.shutdown();
	while(!ex.isTerminated());
    }
}