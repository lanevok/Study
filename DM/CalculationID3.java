import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * Learning of Decision Tree by ID3
 * (ID3アルゴリズムによる決定木の学習)
 *
 * @author T.Koike
 * @since 2014.8.20
 *

[input]
1行目にレコード数N (N=0で終了)
2行目にカラム(質問)数M
3～(3+N)行目にレコード c1,c2, ... , cM

[output]
3種類+先頭に階層深さのタブで構成
1. id=* , opt=* , parentID=*
=> 現在のノードにおける最適なカラム(質問)の出力
2. id=* , pattern=*
=> 前に出力される1.の情報から分岐する出現パターン
3. {Yes,No}
=> 前に出力される2.の情報について結果が一意に定まる

[sample input]
5
4
天気,風速,湿度,花火
晴れ,強い,高い,No
曇り,弱い,高い,Yes
曇り,強い,低い,No
晴れ,弱い,高い,Yes
雨,弱い,高い,No
7
4
天気,風速,湿度,花火
晴れ,強い,高い,No
曇り,弱い,高い,Yes
曇り,強い,低い,No
晴れ,弱い,高い,Yes
雨,弱い,高い,No
晴れ,強い,低い,No
晴れ,弱い,低い,Yes
10
5
性別,年齢,薬物,犯罪歴,実刑
女性,未成年,麻薬,累犯,Yes
男性,老人,麻薬,累犯,Yes
男性,未成年,シンナー,累犯,Yes
女性,成年,シンナー,累犯,Yes
男性,老人,麻薬,初犯,Yes
男性,老人,シンナー,累犯,Yes
男性,成年,覚醒剤,初犯,Yes
女性,老人,麻薬,初犯,No
女性,未成年,覚醒剤,初犯,No
男性,未成年,覚醒剤,初犯,No
14
5
Outlook,Temp,Humidity,Windy,Play
Sunny,Hot,High,False,No
Sunny,Hot,High,True,No
Overcast,Hot,High,False,Yes
Rainy,Mild,High,False,Yes
Rainy,Cool,Normal,False,Yes
Rainy,Cool,Normal,True,No
Overcast,Cool,Normal,True,Yes
Sunny,Mild,High,False,No
Sunny,Cool,Normal,False,Yes
Rainy,Mild,Normal,False,Yes
Sunny,Mild,Normal,False,Yes
Overcast,Mild,High,True,Yes
Overcast,Hot,Normal,False,Yes
Rainy,Mild,High,True,No
0

[sample output]
id=1 , opt=風速 , parentID=0
	id=2 , pattern=強い
		No
	id=3 , pattern=弱い
	id=3 , opt=天気 , parentID=1
		id=4 , pattern=雨
			No
		id=5 , pattern=晴れ
			Yes
		id=6 , pattern=曇り
			Yes
---------------------
id=1 , opt=風速 , parentID=0
	id=2 , pattern=強い
		No
	id=3 , pattern=弱い
	id=3 , opt=天気 , parentID=1
		id=4 , pattern=雨
			No
		id=5 , pattern=晴れ
			Yes
		id=6 , pattern=曇り
			Yes
---------------------
id=1 , opt=犯罪歴 , parentID=0
	id=2 , pattern=初犯
	id=3 , pattern=累犯
		Yes
	id=2 , opt=年齢 , parentID=1
		id=4 , pattern=成年
			Yes
		id=5 , pattern=未成年
			No
		id=6 , pattern=老人
		id=6 , opt=性別 , parentID=2
			id=7 , pattern=女性
				No
			id=8 , pattern=男性
				Yes
---------------------
id=1 , opt=Outlook , parentID=0
	id=2 , pattern=Rainy
	id=3 , pattern=Sunny
	id=4 , pattern=Overcast
		Yes
	id=2 , opt=Windy , parentID=1
		id=5 , pattern=True
			No
		id=6 , pattern=False
			Yes
	id=3 , opt=Humidity , parentID=1
		id=7 , pattern=Normal
			Yes
		id=8 , pattern=High
			No
---------------------


 [References]
 一部サンプルデータは以下から引用
 http://ocw.hokudai.ac.jp/Course/Faculty/Engineering/IntelligentInformationProcessing/2005/page/materials/IntelligentInformationProcessing-2005-Note-11.pdf
 http://www.sakurai.comp.ae.keio.ac.jp/classes/IntInfProc-class/2012/06DecisionTreeAbridged.pdf

 *
 */
public class CalculationID3 {

    Scanner stdIn;
    int serialCreateID;
    Node root;
    Queue<Node> queue;

    /**
     * ノードクラス
     */
    class Node{
	int id;
	List<String> name;
	Set<List<String>> node;
	int parentID;
	int depth;

	/**
	 * ノードのコンストラクタ
	 */
	public Node() {
	    id = ++serialCreateID;
	    name = new ArrayList<String>();
	    node = new HashSet<List<String>>();
	    parentID = -1;
	}

	/**
	 * あるカラム(質問)においてレコードがある文字列，さらに結果が指定通りである数を取得する
	 * @param k 対象のカラム(質問)
	 * @param target 一致対象の文字列
	 * @param result レコードの結果値
	 * @return 該当数
	 */
	public int getCount(int k, String target, String result){
	    int count = 0;
	    int resultIndex = name.size()-1;
	    for(List<String> list : node)
		if(list.get(k).equals(target)&&list.get(resultIndex).equals(result)) count++;
	    return count;
	}

	/**
	 * ノード情報吐き出しデバッグ出力用
	 */
	public void output(){
	    System.out.println("id="+id+" , parentID="+parentID);
	    for(String column : name)
		System.out.print(column+"\t|");
	    System.out.println();
	    for(List<String> recode : node){
		for(String cell : recode)
		    System.out.print(cell+"\t|");
		System.out.println();
	    }
	}

	/**
	 * あるカラム(質問)における出現パターンをリストで取得する
	 * @param k 対象のカラム
	 * @return 出現パターンのリスト
	 */
	public List<String> getPattern(int k) {
	    Set<String> key = new HashSet<String>();
	    for(List<String> list : node)
		key.add(list.get(k));
	    List<String> list = new ArrayList<String>();
	    list.addAll(key);
	    return list;
	}
    }

    public static void main(String[] args) {
	new CalculationID3().run();
    }

    /**
     * 全てのデータセットを扱うメソッド
     */
    private void run() {
	stdIn = new Scanner(System.in);
	while(true){
	    if(input()){
		solve();
		System.out.println("---------------------");
	    }
	    else break;
	}
    }

    /**
     * 1データセットを計算する親メソッド
     */
    private void solve() {
	queue = new LinkedList<Node>();
	queue.add(root);
	// 処理待ちキューが空になるまで実行
	while(!queue.isEmpty()){
	    Node currentNode = queue.poll();
	    int columnSize = currentNode.name.size()-1;
	    List<Double> expectedValue = new ArrayList<Double>();
	    // 1カラムに対する期待値を算出
	    for(int i=0;i<columnSize;i++){
		List<String> pattern = currentNode.getPattern(i);
		int patternSize = pattern.size();
		List<Integer> ratioList = new ArrayList<Integer>();
		List<Double> entropyList = new ArrayList<Double>();
		// そのカラムにおける出現パターンそれぞれについて処理する
		for(int j=0;j<patternSize;j++){
		    int yesCount = currentNode.getCount(i, pattern.get(j), "Yes");
		    int noCount = currentNode.getCount(i, pattern.get(j), "No");
		    ratioList.add(yesCount+noCount);
		    entropyList.add(getEntropy(yesCount, noCount));
		}
		expectedValue.add(getExpectedValue(ratioList, entropyList));
	    }
	    int optimizedIndex = getOptimizedIndex(expectedValue);

	    // 現在のノードにおける最適なカラムの出力
	    for(int i=0;i<currentNode.depth;i++) System.out.print("\t");
	    System.out.println("id="+currentNode.id+" , opt="+
			       currentNode.name.get(optimizedIndex)+" , parentID="+currentNode.parentID);

	    // 下位ノードの処理
	    List<Node> nextList = getNextNode(currentNode, optimizedIndex);
	    for(Node nextNode : nextList){
		// 下位ノードが末端(判定が一意に定まる)か判定
		if(nextNode.getPattern(nextNode.name.size()-1).size()!=1){
		    queue.add(nextNode);
		}
	    }
	}
    }

    /**
     * 下位ノードの生成
     * つまり，最適なカラム(質問)から分岐する子ノードの作成
     * @param currentNode 現在のノード
     * @param optimizedIndex 最適なカラム
     * @return 下位ノードのリスト
     */
    private List<Node> getNextNode(Node currentNode, int optimizedIndex) {
	List<Node> nextList = new ArrayList<Node>();
	List<String> pattern = currentNode.getPattern(optimizedIndex);
	int patternSize = pattern.size();
	for(int i=0;i<patternSize;i++){
	    Node nextNode = new Node();
	    for(int j=0;j<currentNode.name.size();j++){
		if(optimizedIndex!=j)
		    nextNode.name.add(currentNode.name.get(j));
	    }
	    for(List<String> recode : currentNode.node){
		if(recode.get(optimizedIndex).equals(pattern.get(i))){
		    List<String> nextRecode = new ArrayList<String>();
		    for(int j=0;j<currentNode.name.size();j++){
			if(optimizedIndex!=j)
			    nextRecode.add(recode.get(j));
		    }
		    nextNode.node.add(nextRecode);
		}
	    }
	    nextNode.parentID = currentNode.id;
	    nextNode.depth = currentNode.depth+1;
	    nextList.add(nextNode);

	    // 下位ノード情報の出力
	    for(int j=0;j<nextNode.depth;j++) System.out.print("\t");
	    System.out.println("id="+nextNode.id+" , pattern="+pattern.get(i));
	    // 下位ノードが末端(判定が一意に定まる)の場合の出力
	    if(nextNode.getPattern(nextNode.name.size()-1).size()==1){
		for(int j=0;j<currentNode.depth;j++) System.out.print("\t");
		System.out.println("\t\t"+nextNode.getPattern(nextNode.name.size()-1).get(0));
	    }
	}
	return nextList;
    }

    /**
     * 最適なカラム(質問)を調べる
     * 期待値が最小のカラムが必ず認識力(情報ゲイン)が大きくなる．
     * @param expectedValue 期待値のリスト
     * @return 最適なカラムのインデックス
     */
    private int getOptimizedIndex(List<Double> expectedValue){
	int optimizedIndex = 0;
	for(int i=1;i<expectedValue.size();i++){
	    if(expectedValue.get(optimizedIndex)>expectedValue.get(i))
		optimizedIndex = i;
	}
	return optimizedIndex;
    }

    /**
     * 期待値を計算する．
     * @param ratioList データ数比のリスト
     * @param entropyList エントロピーのリスト
     * @return 期待値
     */
    private double getExpectedValue(List<Integer> ratioList, List<Double> entropyList){
	double expectedValue = 0.0;
	for(int i=0;i<ratioList.size();i++)
	    expectedValue += ratioList.get(i)*entropyList.get(i);
	return expectedValue;
    }

    /**
     * エントロピーを計算する．
     * 式は，(m/(m+n))*log2((m+n)/m)+(n/(m+n))*log2((m+n)/n)
     * @param yesCount mにあたる"Yes"の数
     * @param noCount nにあたる"No"の数
     * @return エントロピー値
     */
    private double getEntropy(int yesCount, int noCount) {
	double yes = yesCount*1.0;
	double no = noCount*1.0;
	double total = (yes+no)*1.0;
	double entropy = 0.0;
	if(yes!=0) entropy += Math.log(total/yes)/Math.log(2)*yes/total;
	if(no!=0)	entropy += Math.log(total/no)/Math.log(2)*no/total;
	return entropy;
    }

    /**
     * データの読み込みを行い，ルートノードを生成する．
     * @return データの入力があるかの真偽値
     */
    private boolean input() {
	int n = Integer.valueOf(stdIn.nextLine());
	if(n==0) return false;
	int m = Integer.valueOf(stdIn.nextLine());
	serialCreateID = 0;
	root = new Node();
	List<String> list = new ArrayList<String>();
	String[] line = stdIn.nextLine().split(",");
	for(int i=0;i<m;i++) list.add(line[i]);
	root.name = list;
	for(int i=0;i<n;i++){
	    list = new ArrayList<String>();
	    line = stdIn.nextLine().split(",");
	    for(int j=0;j<m;j++) list.add(line[j]);
	    root.node.add(list);
	}
	root.parentID = 0;
	root.depth = 0;
	return true;
    }
}
