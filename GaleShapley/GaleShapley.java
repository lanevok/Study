import java.util.Scanner;

/**
 *
 * Gale-Shapley アルゴリズム
 * (安定結婚問題)
 *
 * アルゴリズム
 * 1. 誰にもキープされていない男性1人が、(今までフラれていない中で)一番結婚したい女性に告白する。
 * 2. 告白を受けた女性は、
 * 　　受理: 相手がいない or 今キープしている人よりもよい相手なら告白してきた人をキープする
 * 　　　　　(今までキープしてた人とはさよならする)
 * 　　拒否: 今キープしている人の方がよいなら告白を断る
 * 3. 1,2 を繰り返し、全男性が誰かしらにキープされたらその時点のペア(完全マッチング)が完成
 *
 * 参考
 * http://mathtrain.jp/galeshapley
 *
 * 拡張版アルゴリズム(複数人対応版)
 * 1. 男性の最大キープ数まで男性が女性をキープしていない場合、
 * 　　今までフラれていなく、キープしてる人ではない人で、一番結婚したい女性に告白する。
 * 2. 告白を受けた女性は、
 * 　　受理: 女性の最大キープ数まで男性をキープしていない　or
 * 　　　　　　女性の最大キープ数まで男性をキープしているが、キープしている男性の最低(一番優先度が低い人)よりも
 * 　　　　　　　理想が高い(優先度が高い)人が告白してきたら、告白してきた人をキープする
 * 　　　　　　　　(キープしていた最低の人とさよならする)
 * 　　拒否: 今キープしている全員に告白してきた人が一人も勝てないなら断る
 *  3. 1,2 を繰り返し、全男性が最大キープ数までキープができたらその時点でペア(完全マッチング)が完成
 *
 * @author koike
 * @since 2015.10.16
 * @version 2
 *
 * memo 180min
 *
 * 入力サンプルは以下

 【テスト1件目: 3:3 でキープ1】
 ▼入力
 3
 3
 2 0 1
 2 1 0
 0 2 1
 0 1 2
 2 0 1
 2 1 0
 1
 1
 ▼出力
 blue 0 の告白, 0人はキープ済
 blue 0 の 0 番目候補 2 (redからは 2 番目候補) に告白 (理由 候補なし)
 blue 1 の告白, 0人はキープ済
 blueの 0 さんが 0 番目候補であるred 2 さんからキープ解除
 blue 1 の 0 番目候補 2 (redからは 1 番目候補) に告白 (理由 より良い)
 blue 0 の告白, 0人はキープ済
 blue 0 の 1 番目候補 0 (redからは 0 番目候補) に告白 (理由 候補なし)
 blue 2 の告白, 0人はキープ済
 blue 2 の 0 番目候補 0 に告白を断る
 blue 2 の告白, 0人はキープ済
 blueの 1 さんが 0 番目候補であるred 2 さんからキープ解除
 blue 2 の 1 番目候補 2 (redからは 0 番目候補) に告白 (理由 より良い)
 blue 1 の告白, 0人はキープ済
 blue 1 の 1 番目候補 1 (redからは 2 番目候補) に告白 (理由 候補なし)
 --- blue ---
 blue 0 : 0,
 blue 1 : 1,
 blue 2 : 2,

 --- red ---
 red 0 : 0,
 red 1 : 1,
 red 2 : 2,

 【テスト2件目: 6:4 でキープ2:3】
 ▼参考
 高校　　　　　　　　　　　大学
 a>b>c>d　１　a　1>2>3>4>5>6
 b>c>a>d　２　b　6>5>4>3>2>1
 c>d>a>b　３　c　3>2>6>5>4>1
 a>d>b>c　４　d　4>3>1>5>6>2
 d>a>c>b　５　
 b>a>d>c　６　　
 ▼入力
 6
 4
 0 1 2 3
 1 2 0 3
 2 3 0 1
 0 3 1 2
 3 0 2 1
 1 0 3 2
 0 1 2 3 4 5
 5 4 3 2 1 0
 2 1 5 4 3 0
 3 2 0 4 5 1
 2
 3
 ▼出力
 blue 0 の告白, 0人はキープ済
 blue 0 の 0 番目候補 0 (redからは 0 番目候補) に告白 (理由 候補なし)
 blue 0 の告白, 1人はキープ済
 blue 0 の 1 番目候補 1 (redからは 5 番目候補) に告白 (理由 候補なし)
 blue 1 の告白, 0人はキープ済
 blue 1 の 0 番目候補 1 (redからは 4 番目候補) に告白 (理由 候補なし)
 blue 1 の告白, 1人はキープ済
 blue 1 の 1 番目候補 2 (redからは 1 番目候補) に告白 (理由 候補なし)
 blue 2 の告白, 0人はキープ済
 blue 2 の 0 番目候補 2 (redからは 0 番目候補) に告白 (理由 候補なし)
 blue 2 の告白, 1人はキープ済
 blue 2 の 1 番目候補 3 (redからは 1 番目候補) に告白 (理由 候補なし)
 blue 3 の告白, 0人はキープ済
 blue 3 の 0 番目候補 0 (redからは 3 番目候補) に告白 (理由 候補なし)
 blue 3 の告白, 1人はキープ済
 blue 3 の 1 番目候補 3 (redからは 0 番目候補) に告白 (理由 候補なし)
 blue 4 の告白, 0人はキープ済
 blue 4 の 0 番目候補 3 (redからは 3 番目候補) に告白 (理由 候補なし)
 blue 4 の告白, 1人はキープ済
 blue 4 の 1 番目候補 0 (redからは 4 番目候補) に告白 (理由 候補なし)
 blue 5 の告白, 0人はキープ済
 blue 5 の 0 番目候補 1 (redからは 0 番目候補) に告白 (理由 候補なし)
 blue 5 の告白, 1人はキープ済
 blue 5 の 1 番目候補 0 に告白を断る
 blue 5 の告白, 1人はキープ済
 blue 5 の 2 番目候補 3 に告白を断る
 blue 5 の告白, 1人はキープ済
 blue 5 の 3 番目候補 2 (redからは 2 番目候補) に告白 (理由 候補なし)
 --- blue ---
 blue 0 : 0,1,
 blue 1 : 1,2,
 blue 2 : 2,3,
 blue 3 : 0,3,
 blue 4 : 3,0,
 blue 5 : 1,2,

 --- red ---
 red 0 : 0,3,4,
 red 1 : 5,1,0,
 red 2 : 2,1,5,
 red 3 : 3,2,4,

 【テスト3件目: 4:6 でキープ3:2】
 ▼参考
 大学　　　　　　　　　　高校
 1>2>3>4>5>6　a  1　a>b>c>d　
 6>5>4>3>2>1　b  2　b>c>a>d 　
 3>2>6>5>4>1　c  3　c>d>a>b　
 4>3>1>5>6>2　d  4　a>d>b>c
 　　　　　　　　  ５　d>a>c>b
 　　　　　　　　  ６　b>a>d>c
 ▼入力
 4
 6
 0 1 2 3 4 5
 5 4 3 2 1 0
 2 1 5 4 3 0
 3 2 0 4 5 1
 0 1 2 3
 1 2 0 3
 2 3 0 1
 0 3 1 2
 3 0 2 1
 1 0 3 2
 3
 2
 ▼出力
 blue 0 の告白, 0人はキープ済
 blue 0 の 0 番目候補 0 (redからは 0 番目候補) に告白 (理由 候補なし)
 blue 0 の告白, 1人はキープ済
 blue 0 の 1 番目候補 1 (redからは 2 番目候補) に告白 (理由 候補なし)
 blue 0 の告白, 2人はキープ済
 blue 0 の 2 番目候補 2 (redからは 2 番目候補) に告白 (理由 候補なし)
 blue 1 の告白, 0人はキープ済
 blue 1 の 0 番目候補 5 (redからは 0 番目候補) に告白 (理由 候補なし)
 blue 1 の告白, 1人はキープ済
 blue 1 の 1 番目候補 4 (redからは 3 番目候補) に告白 (理由 候補なし)
 blue 1 の告白, 2人はキープ済
 blue 1 の 2 番目候補 3 (redからは 2 番目候補) に告白 (理由 候補なし)
 blue 2 の告白, 0人はキープ済
 blue 2 の 0 番目候補 2 (redからは 0 番目候補) に告白 (理由 候補なし)
 blue 2 の告白, 1人はキープ済
 blue 2 の 1 番目候補 1 (redからは 1 番目候補) に告白 (理由 候補なし)
 blue 2 の告白, 2人はキープ済
 blue 2 の 2 番目候補 5 (redからは 3 番目候補) に告白 (理由 候補なし)
 blue 3 の告白, 0人はキープ済
 blue 3 の 0 番目候補 3 (redからは 1 番目候補) に告白 (理由 候補なし)
 blue 3 の告白, 1人はキープ済
 blueの 0 さんが 2 番目候補であるred 2 さんからキープ解除
 blue 3 の 1 番目候補 2 (redからは 1 番目候補) に告白 (理由 より良い)
 blue 0 の告白, 2人はキープ済
 blueの 1 さんが 2 番目候補であるred 3 さんからキープ解除
 blue 0 の 3 番目候補 3 (redからは 0 番目候補) に告白 (理由 より良い)
 blue 1 の告白, 2人はキープ済
 blue 1 の 3 番目候補 2 に告白を断る
 blue 1 の告白, 2人はキープ済
 blueの 0 さんが 1 番目候補であるred 1 さんからキープ解除
 blue 1 の 4 番目候補 1 (redからは 0 番目候補) に告白 (理由 より良い)
 blue 0 の告白, 2人はキープ済
 blue 0 の 4 番目候補 4 (redからは 1 番目候補) に告白 (理由 候補なし)
 blue 3 の告白, 2人はキープ済
 blue 3 の 2 番目候補 0 (redからは 3 番目候補) に告白 (理由 候補なし)
 --- blue ---
 blue 0 : 0,3,4,
 blue 1 : 5,4,1,
 blue 2 : 2,1,5,
 blue 3 : 3,2,0,

 --- red ---
 red 0 : 0,3,
 red 1 : 1,2,
 red 2 : 2,3,
 red 3 : 0,3,
 red 4 : 0,1,
 red 5 : 1,2,

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

	/**
	 * キープしている中で最悪者の候補番号を取得する
	 * @return
	 */
	int getKeepWorstIndex(){
	    for(int i=partner.length-1;i>=0;i--){
		if(selection[i]){
		    return i;
		}
	    }
	    return -1;
	}

	boolean isBad(int i){
	    return bad[i]? true : false;
	}

	/**
	 * index番目をキープしている
	 * @param index番目
	 * @return キープ済
	 */
	boolean isKeep(int index) {
	    return selection[index]? true : false;
	}

	/**
	 * キープ人数
	 * @return
	 */
	int getKeepCount(){
	    return keepCount;
	}

	void printSelected(){
	    for(int i=0;i<partner.length;i++){
		if(selection[i]){
		    System.out.print(partner[i]+",");
		}
	    }
	    System.out.println();
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
    int blue_max_keep;
    int red_max_keep;

    public static void main(String[] args) {
	new GaleShapley().run();

    }

    private void run() {
	input();
	calculate();
	output();
    }

    private void output() {
	System.out.println("--- blue ---");
	for(int i=0;i<blueN;i++){
	    System.out.print("blue "+i+" : ");
	    blue_request[i].printSelected();
	}
	System.out.println();
	System.out.println("--- red ---");
	for(int i=0;i<redN;i++){
	    System.out.print("red "+i+" : ");
	    red_request[i].printSelected();
	}
    }

    private void calculate() {
	while(!isAllSelected()){
	    //			processCount++;
	    //			if(processCount>10){
	    //				return;
	    //			}
	    SelectProcess:
	    // 告白者 i
	    for(int i=0;i<blueN;i++){
		// 告白者 i のキープ人数が 最大値になっていない
		if(blue_request[i].getKeepCount()!=blue_max_keep){
		    // 告白の選定後、告白実行
		    System.out.println("blue "+i+" の告白, "
				       +blue_request[i].getKeepCount()+"人はキープ済");
		    // 候補順に
		    for(int j=0;j<redN;j++){
			// 候補 j 番目の red t さんに着目
			int t = blue_request[i].getNumber(j);
			// blue 告白者 i の j 番目候補に対する既に断られてない かつ　j番目を既にキープしてない
			if(!blue_request[i].isBad(j)&&!blue_request[i].isKeep(j)){
			    // red tさんの キープ人数が 最大値になっていない
			    if(red_request[t].getKeepCount()!=red_max_keep){
				// blue i さん が 候補 j 番目の告白に成功
				blue_request[i].setSelect(j);
				// red t さんにとっての 告白者 blue i さんの優先度
				int y = red_request[t].getIndex(i);
				// red t さんの y番目の候補が実る
				red_request[t].setSelect(y);
				System.out.println("\tblue "+i+" の "+j+" 番目候補 "
						   +t+" (redからは "+y+" 番目候補) に告白 (理由 候補なし)");
				break SelectProcess;
			    }
			    // red tさんの最悪者キープの候補順位が　告白者blue i さんの候補順位より低い(数字が大きい)
			    else if(red_request[t].getKeepWorstIndex()>red_request[t].getIndex(i)){
				// red t さんの最悪キープ候補順位 before
				int before = red_request[t].getKeepWorstIndex();
				// red t さんの 候補 before 番目の最悪者は お断り
				red_request[t].removeSelect(before);
				// ★ブルー側を断る必要がある
				{
				    int before_blue = red_request[t].getNumber(before);
				    int blue_priority = blue_request[before_blue].getIndex(t);
				    blue_request[before_blue].removeSelect(blue_priority);
				    System.out.println("\tblueの "+before_blue+" さんが "
						       +blue_priority+" 番目候補であるred "+t+" さんからキープ解除");
				}
				blue_request[i].setSelect(j);
				int y = red_request[t].getIndex(i);
				red_request[t].setSelect(y);
				System.out.println("\tblue "+i+" の "+j+" 番目候補 "
						   +t+" (redからは "+y+" 番目候補) に告白 (理由 より良い)");
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

    /**
     * 告白側(ブルー)の全員について、空きがゼロか確認
     * @return 空きゼロの真偽値
     */
    private boolean isAllSelected() {
	for(int i=0;i<blueN;i++){
	    if(blue_request[i].getKeepCount()!=blue_max_keep){
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
	blue_max_keep = stdIn.nextInt();
	red_max_keep = stdIn.nextInt();
    }
}
