iimport java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * クローラサンプル (3年ゼミPJより改変)
 *
 * @author (TAT)chaN / lanevok
 * @version 31α
 * @since 2012.10
 *
 */
public class CR {

    // カレントディレクトリの設定
    final static String currentDirectory = new File("").getAbsolutePath()+"/";
    final int RequestWaitTime = 3;      // サーバにリクエストする時間間隔
    String exeTime;		// 最終UNIXタイムスタンプを保持
    int ErrorCount = 0;		// 連続エラー数
    int SerialNumber = 0;	// 通し番号(次発行番号)
    int requestCount = 0;	// リクエスト回数
    // 省略
    
    /**
     * 指定時間ドットを打ちながら待つ
     * @throws InterruptedException
     */
    void Wait() throws InterruptedException{
	for(int i=0;i<RequestWaitTime;i++){
	    TimeUnit.SECONDS.sleep(1);		// プログラムを1秒止める
	    System.err.print(".");
	}
    }
    
    /**
     * 指定されたURLのソースを取得し、一定時間待ってソースを返す
     * 	(クローリングの核となるメソッド)
     * @param url 取得するURL
     * @return ソースコード
     * @throws InterruptedException
     * @throws IOException
     */
    String Request(URL url) throws InterruptedException, IOException  {
	requestCount++;
	System.err.print("["+new Date().toString()+"] Call : ");
	InputStream in;
	try {
	    in = url.openStream();	// URLに対してHTTP展開 / 入力ストリームの生成
	} catch (IOException e) {
	    System.err.println();
	    Error("Request",String.valueOf(url));
	    return "";
	}
	StringBuilder sb = new StringBuilder();
	try {
	    // 入力ストリームからBufferedReaderの作成
	    BufferedReader bf = new BufferedReader(new InputStreamReader(in,"UTF-8"));
	    String s;
	    try {
		while ((s=bf.readLine())!=null) {
		    sb.append(s+"\n");
		}
	    } catch (IOException e) {
		System.err.println();
		Error("Connection", url.toString());
	    }
	} finally {
	    in.close();
	    System.err.print("Wait ");
	    Wait();
	    System.err.println(" Go!");
	}
	return sb.toString();
    }
    
    /**
     * ソースから指定された正規表現で合致するものを返す
     * @param regex 正規表現
     * @param Source テキストソース
     * @return パターンマッチングの結果
     */
    Matcher matchGet(String regex, String Source){
	return Pattern.compile(regex, Pattern.DOTALL).matcher(Source);
    }
    
    /**
     * 指定されたファイル名と文字コードでBufferedReaderを発行する
     * @param fileName パスを含まないファイル名
     * @param code 文字コード
     * @return BufferedReader
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    BufferedReader createReader(String fileName, String code) throws UnsupportedEncodingException, FileNotFoundException{
	return new BufferedReader(new InputStreamReader(new FileInputStream(currentDirectory+fileName),code));
    }
    
    /**
     * 指定されたファイル型でBufferedReaderを発行する
     * @param file File型変数
     * @return BufferedReader
     * @throws FileNotFoundException
     */
    BufferedReader createReader(File file) throws FileNotFoundException{
	return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }
    
    /**
     * 指定されたファイル名でBufferedWriterを発行する (オーバーライド)
     * @param fileName パスを含まないファイル名
     * @return BufferedWriter
     * @throws FileNotFoundException
     */
    BufferedWriter createWriter(String fileName) throws FileNotFoundException{
	return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentDirectory+fileName)));
    }
    
    /**
     * 指定されたファイル名でBufferedWriterを発行する (オーバーライド)
     * @param fileName パスを含まないファイル名
     * @param append 上書き許可 (=true)
     * @return BufferedWriter
     * @throws FileNotFoundException
     */
    BufferedWriter createWriter(String fileName, boolean append) throws FileNotFoundException{
	return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentDirectory+fileName,append)));
    }
    
    /**
     * 現在日時のUNIXタイムスタンプ文字列を発行する
     * @return 現在時刻
     */
    String nowTimeStamp(){
	exeTime = String.valueOf(System.currentTimeMillis()/1000);
	return exeTime;
    }
    
    /**
     * エラーログを出力する
     * @param errName エラー内容
     * @param debug エラーに対するデバッグ情報
     * @throws IOException
     */
    void Error(String errName, String debug) throws IOException{
	System.err.println("["+new Date().toString()+"] Error : "+errName);
	System.err.println("\t{"+debug+"}");
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("errlog/ErrorLog-"+exeTime+".txt",true)));
	bw.write("["+new Date().toString()+"] Error : "+errName+"\n");
	bw.write("\t{"+debug+"}");
	ErrorCount++;
	if(ErrorCount>=10){		// 指定回数連続でエラーの場合は強制終了
	    System.err.println("["+new Date().toString()+"] Forced Termination !");
	    bw.write("["+new Date().toString()+"] Forced Termination !"+"\n");
	    bw.close();
	    System.exit(0);		// 強制終了
	}
	bw.close();
    }
    
    /**
     * 2つのファイルにおける重複したIDを除去し、新しく1つのファイルに新しく書き出す
     * @throws IOException
     */
    void combID() throws IOException{
	BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream("Before_1.txt"),"MS932"));
	BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream("Before_2.txt"),"MS932"));
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("After.txt")));
	HashSet<String> id = new HashSet<String>();
	String line;
	br1.readLine();
	br2.readLine();
	int cnt = 0;
	while((line=br1.readLine())!=null){
	    if(!id.contains(line)){
		id.add(line);
		cnt++;
	    }
	}
	while((line=br2.readLine())!=null){
	    if(!id.contains(line)){
		id.add(line);
		cnt++;
	    }
	}
	System.out.println(cnt);
	for(String idName : id){
	    bw.write(idName+"\n");
	}
	bw.close();
    }
    
    // 省略
    
    /**
     * テキストの文字数を調べる
     * 	(ただし、改行/全角空白/半角空白/タブ を除く)
     * @param text 対象となるテキスト文字列
     * @return テキストの文字数
     */
    int TextLength(String text){
	int cnt = 0;
	for(int i=0;i<text.length();i++){
	    if(text.charAt(i)!='\n'&&text.charAt(i)!=' '&&text.charAt(i)!='\t'&&text.charAt(i)!='　'){
		cnt++;
	    }
	}
	return cnt-1;
    }
    
    /**
     * アルファベットを含めない文字数を調べる
     * 	(TextLengthの条件は含む)
     * @param text 対象となるテキスト文字列
     * @return アルファベット等を除いたテキストの文字数
     */
    int TextLength2(String text){
	int cnt = 0;
	for(int i=0;i<text.length();i++){
	    if(text.charAt(i)!='\n'&&text.charAt(i)!=' '&&text.charAt(i)!='\t'&&text.charAt(i)!='　'){
		if((text.charAt(i)>='A'&&text.charAt(i)<='Z')||(text.charAt(i)>='a'&&text.charAt(i)<='z')){
		}
		else{
		    cnt++;
		}
	    }
	}
	return cnt-1;
    }
    
    /**
     * 4つ以上ある改行や空白またタブを3つまで圧縮する
     * @param text 対象となるテキスト文字列
     * @return 圧縮されたテキスト文字列
     */
    String Compression(String text){
	for(int i=0;i<20;i++){
	    text = text.replaceAll("\n\t\t\n\t\t\n", "\n\n\n");
	    text = text.replaceAll("\n\t\t\n\t\n", "\n\n\n");
	    text = text.replaceAll("\n\t\t\n\n", "\n\n\n");
	    text = text.replaceAll("\n\n \n\n", "\n\n\n");
	    text = text.replaceAll("\n\n  \n \n", "\n\n\n");
	    text = text.replaceAll("\t\t\t\t", "\t\t\t");
	    text = text.replaceAll("    ", "   ");
	    text = text.replaceAll("　　　　", "　　　");
	    text = text.replaceAll("\n \n \n \n", "\n\n\n");
	    text = text.replaceAll("\n\n\n\n","\n\n\n");
	}
	return text;
    }
    
    /**
     * 文字列のシリアルナンバー(通し番号)を発行する
     * 	(0でパディングする)
     * @return シリアルナンバー文字列
     */
    String serialNoPublish(){
	String stg = String.valueOf(SerialNumber++);
	String ans = "";
	for(int i=0;i<7-stg.length();i++){
	    ans = ans + "0";
	}
	return ans+stg;
    }
    
    /**
     * 指定したフォルダ内のすべてのファイルをファイル配列で返す
     * @param folderName パスを含まないファイル名
     * @return File型配列
     */
    File[] FilesGet(String folderName){
	return new File(currentDirectory+folderName).listFiles();
    }

    /**
     * タイトル(接頭,接尾)に含まれる『』文字を除去する
     * @param str 除去するタイトル文字列
     * @return 除去されたタイトル文字列
     */
    String cutBracket(String str){
	if(str.charAt(0)=='『'){
	    return str.substring(1, str.length()-1);
	}
	else{
	    return str;
	}
    }

    // 省略

    /**
     * HTML特殊文字を変換する
     * @param str 対象となる文字列
     * @return 変換後の文字列
     */
    String HTMLspCharChange(String str){
	String[][] table = {
	    {"&lt;" , "<"},
	    {"&gt;" , ">"},
	    {"&nbsp;" , " "},
	    {"&quot;" , "\""},
	    {"&#34;" , "\""},
	    {"&#38;" , "&"},
	    {"&amp;" , "&"},
	    {"&#60;" , "<"},
	    {"&#62;" , ">"},
	    {"&#160;" , " "},
	    {"&yen;" , "\\"},
	    {"&#165;", "\\"},
	};
	for(int i=0;i<table.length-1;i++){
	    str = str.replaceAll(table[i][0],table[i][1]);
	}
	return str;
    }

    /**
     * テキストの接頭と接尾のゴミを除去する
     * @param str 対象となるテキスト文字列
     * @return 接頭と接尾のゴミを除去したテキスト文字列
     */
    String SettingTB(String str) throws Exception{
	for(int i=0;i<20;i++){
	    if(str.charAt(i)!='\n'&&str.charAt(i)!=' '&&str.charAt(i)!='　'){
		for(int j=str.length()-1;j>str.length()-20;j--){
		    if(str.charAt(j)!='\n'&&str.charAt(j)!=' '&&str.charAt(j)!='　'){
			return str.substring(i, j+1);
		    }
		}
		break;
	    }
	}
	return str;
    }

    /**
     * テキスト文字列からストップワードを除去する
     * @param str 対象となるテキスト文字列
     * @return ストップワード除去後の文字列
     */
    String StopWords(String str){
	String[] sw = {
	    "ストップワードA",
	    "ストップワードB",
	    "ストップワードC",
	};
	for(int i=0;i<sw.length;i++){
	    str = str.replaceAll(sw[i], "");
	}
	return str;
    }

    // 省略


    /**
     * クローリング処理	その1
     * @throws MalformedURLException
     * @throws IOException
     * @throws InterruptedException
     */
    void CR1() throws MalformedURLException, IOException, InterruptedException{
	final String baseBeforeURL = "http://sample1234.jp/";
	// 以下の<a href="..."></a>について取得したい (この部分だけ使用し他は破棄)
	final String regex = "<a href=\"http://sample1234.jp/(.*?)/\".*?>(.*?)</a>";
	final int pageStart = 1, pageEnd = 10;

	Set<String> ID = new HashSet<String>();
	// 複数のジャンルページに対して実行する
	BufferedReader br = createReader("genre300.txt", "MS932");
	// 強制終了してもログが残るようにする
	System.setOut(new PrintStream(new File("ID_"+nowTimeStamp()+"-log.txt")));
	System.out.println("pageStart="+pageStart+",pageEnd="+pageEnd+",Time="+exeTime+"\n");

	String genre;
	while((genre=br.readLine())!=null){	// 全ジャンル実行
	    // 各ジャンル全てのページに対して
	    for(int pageNow=pageStart;pageNow<=pageEnd;pageNow++){
		// URLの生成
		URL targetURL = new URL(baseBeforeURL+genre+"&page="+pageNow);
		// リクエストの実行
		String htmlSource = Request(targetURL);
		// 本文抽出のパターンマッチング
		Matcher matcher = matchGet(regex, htmlSource);
		int cnt = 0;
		// 以下の.find()でパターンマッチングの結果要素が拾える
		while (matcher.find()) {
		    if(++cnt>50){
			break;
		    }
		    // パターンマッチングの結果を局所変数に代入する
		    String getID = matcher.group(1);
		    ID.add(getID);
		    System.out.println(getID);
		}
		if(cnt==0){
		    Error("HTML Source",htmlSource);
		    continue;
		}
		ErrorCount = 0;
	    }
	}
	// 正式に結果をファイルに書き出し
	BufferedWriter bw = createWriter("ID_"+exeTime+".txt");
	bw.write("pageStart="+pageStart+",pageEnd="+pageEnd+",Time="+exeTime+"\n");
	for(String id : ID){
	    bw.write(id+"\n");
	}
	bw.close();
    }

    // 約500行ほど省略

    /**
     * メインメソッド
     * @param args 実行引数(なし)
     * @throws MalformedURLException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
	//	CR cr = new CR();
	//	cr.CR1();
    }
}
