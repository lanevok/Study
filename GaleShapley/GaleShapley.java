import java.util.Scanner;

/**
 *
 * Gale-Shapley アルゴリズム
 * (安定結婚問題)
 *
 * アルゴリズム
 * 1. 誰にもキープされていない男性1任が、(今までふられていない中で)一番結婚したい女性に告白する。
 * 2. 告白を受けた女性は、
 * 　　受理: 相手がいない or 今キープしている人よりもよい相手なら告白してきた人をキープする
 * 　　　　　(今までキープしてた人とはさよならする)
 * 　　拒否: 今キープしている人の河がよいなら告白を断る
 * 3. 1,2 を繰り返し、全男性が誰かしらにキープされたらその時点のペア(完全マッチング)が完成
 *
 * 参考
 * http://mathtrain.jp/galeshapley
 *
 * @author koike
 * @since 2015.10.2
 * @version 1
 *
 * memo 120min
 *
 * 入力サンプルは以下

 3
 3
 2 0 1
 2 1 0
 0 2 1
 0 1 2
 2 0 1
 2 1 0

 * 出力サンプルは以下

 blue 0 の告白
 blue 0 の 0 番目候補 2 (redからは 2 番目候補) に告白 (理由 候補なし)
 blue 1 の告白
 blueの 0 さんが 0 番目候補であるred 2 さんからキープ解除
 blue 1 の 0 番目候補 2 (redからは 1 番目候補) に告白 (理由 より良い)
 blue 0 の告白
 blue 0 の 1 番目候補 0 (redからは 0 番目候補) に告白 (理由 候補なし)
 blue 2 の告白
 blue 2 の 0 番目候補 0 に告白を断る
 blue 2 の告白
 blueの 1 さんが 0 番目候補であるred 2 さんからキープ解除
 blue 2 の 1 番目候補 2 (redからは 0 番目候補) に告白 (理由 より良い)
 blue 1 の告白
 blue 1 の 1 番目候補 1 (redからは 2 番目候補) に告白 (理由 候補なし)
 0	0
 1	1
 2	2

*/
public class GaleShapley {

    class Request{
	int[] partner;
	boolean[] selection;
	boolean[] bad;
	int keepCount;

	public Request(int n) {
	    partner = new int[n];
	    selection = new boolean[n];
	    bad = new boolean[n];
	    keepCount = 0;
	}

	int getNumber(int i){
	    return partner[i];
	}

	int getIndex(int number){
	    for(int i=0;i<partner.length;i++){
		if(partner[i]==number){
		    return i;
		}
	    }
	    return -1;
	}

	int getKeepIndex(){
	    for(int i=0;i<partner.length;i++){
		if(selection[i]){
		    return i;
		}
	    }
	    return -1;
	}

	int getKeepNumber(){
	    for(int i=0;i<partner.length;i++){
		if(selection[i]){
		    return partner[i];
		}
	    }
	    return -1;
	}

	boolean isBad(int i){
	    return bad[i]? true : false;
	}

	int getKeepCount(){
	    return keepCount;
	}

	void setPartner(int i, int p){
	    partner[i] = p;
	}

	void setSelect(int i){
	    if(!selection[i]){
		keepCount++;
	    }
	    selection[i] = true;
	}

	void setBad(int i){
	    bad[i] = true;
	}

	void removeSelect(int i){
	    if(selection[i]){
		keepCount--;
	    }
	    setBad(i);
	    selection[i] = false;
	}
    }

    int blueN;
    int redN;
    Request[] blue_request;
    Request[] red_request;
    int processCount = 0;

    public static void main(String[] args) {
	new GaleShapley().run();

    }

    private void run() {
	input();
	calculate();
	output();
    }

    private void output() {
	for(int i=0;i<blueN;i++){
	    System.out.println(i+"\t"+blue_request[i].getKeepNumber());
	}
    }

    private void calculate() {
	while(!isAllSelected()){
	    processCount++;
	    if(processCount>10){
		return;
	    }
	    SelectProcess:
	    // 告白者 i
	    for(int i=0;i<blueN;i++){
		// 告白者 i のキープ人数が 0
		if(blue_request[i].getKeepCount()==0){
		    // 告白の選定後、告白実行
		    System.out.println("blue "+i+" の告白");
		    // 候補順に
		    for(int j=0;j<redN;j++){
			// 候補 j 番目の red t さんに着目
			int t = blue_request[i].getNumber(j);
			// blue 告白者 i の j 番目候補に対する既に断られてない
			if(!blue_request[i].isBad(j)){
			    // red tさんの キープ人数が 0
			    if(red_request[t].getKeepCount()==0){
				// blue i さん が 候補 j 番目の告白に成功
				blue_request[i].setSelect(j);
				// red t さんにとっての 告白者 blue i さんの優先度
				int y = red_request[t].getIndex(i);
				// red t さんの y番目の候補が実る
				red_request[t].setSelect(y);
				System.out.println("\tblue "+i+" の "+j+" 番目候補 "+t+" (redからは "+y+" 番目候補) に告白 (理由 候補なし)");
				break SelectProcess;
			    }
			    // red tさんのキープの候補順位が　告白者blue i さんの候補順位より低い(数字が大きい)
			    else if(red_request[t].getKeepIndex()>red_request[t].getIndex(i)){
				// red t さんのキープ候補順位 before
				int before = red_request[t].getKeepIndex();
				// red t さんの 候補 before 番目は お断り
				red_request[t].removeSelect(before);
				// ★ブルー側を断る必要がある
				{
				    int before_blue = red_request[t].getNumber(before);
				    int blue_priority = blue_request[before_blue].getIndex(t);
				    blue_request[before_blue].removeSelect(blue_priority);
				    System.out.println("\tblueの "+before_blue+" さんが "+blue_priority+" 番目候補であるred "+t+" さんからキープ解除");
				}
				blue_request[i].setSelect(j);
				int y = red_request[t].getIndex(i);
				red_request[t].setSelect(y);
				System.out.println("\tblue "+i+" の "+j+" 番目候補 "+t+" (redからは "+y+" 番目候補) に告白 (理由 より良い)");
				break SelectProcess;
			    }
			    else{
				blue_request[i].setBad(j);
				System.out.println("\tblue "+i+" の "+j+" 番目候補 "+t+" に告白を断る");
				break SelectProcess;
			    }
			}
		    }
		}
	    }
	}
    }

    private boolean isAllSelected() {
	for(int i=0;i<redN;i++){
	    if(red_request[i].getKeepCount()!=1){
		return false;
	    }
	}
	return true;
    }

    private void input() {
	@SuppressWarnings("resource")
	    Scanner stdIn = new Scanner(System.in);
	blueN = stdIn.nextInt();
	redN = stdIn.nextInt();
	blue_request = new Request[blueN];
	for(int i=0;i<blueN;i++){
	    blue_request[i] = new Request(redN);
	    for(int j=0;j<redN;j++){
		blue_request[i].setPartner(j, stdIn.nextInt());
	    }
	}
	red_request = new Request[redN];
	for(int i=0;i<redN;i++){
	    red_request[i] = new Request(blueN);
	    for(int j=0;j<blueN;j++){
		red_request[i].setPartner(j, stdIn.nextInt());
	    }
	}
    }
}
