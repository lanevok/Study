import java.util.Scanner;

/**
 *
 * Gale-Shapleyアルゴリズムを応用したマッチング
 * (安定結婚問題)
 *
 * @author koike
 * @since 2015.11.12
 * @version 3
 *
 */
public class GaleShapley2 {

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

	/**
	 * 候補が全てブロックされているか確認
	 * @return 全てブロック済
	 */
	boolean getAllBlocked(){
	    for(int i=0;i<partner.length;i++){
		if(!selection[i]&&!bad[i]){
		    return false;
		}
	    }
	    return true;
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

	String getBad(int i){
	    return String.valueOf(bad[i]);
	}

    }

    int blueN;
    int redN;
    Request[] blue_request;
    Request[] red_request;
    int processCount = 0;
    int[] blue_max_keep_array;
    int[] red_max_keep_array;

    public static void main(String[] args) {
	new GaleShapley2().run();

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
	while(!isAllBlocked()){
	    // processCount++;
	    // if(processCount>40){
	    //    return;
	    // }
	    SelectProcess:
	    // 告白者 i
	    for(int i=0;i<blueN;i++){
		// 告白者 i のキープ人数が 最大値になっていない & すべての候補が潰れていない
		if(blue_request[i].getKeepCount()!=blue_max_keep_array[i]&&!blue_request[i].getAllBlocked()){
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
			    if(red_request[t].getKeepCount()!=red_max_keep_array[t]){
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
     * キープしていないブルーの候補が全滅したか確認(終了条件)
     * @since 11/12
     * @return 全滅した
     */
    private boolean isAllBlocked() {
	for(int i=0;i<blueN;i++){
	    if(blue_request[i].getKeepCount()!=blue_max_keep_array[i]){
		if(!blue_request[i].getAllBlocked()){
		    return false;
		}
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
	blue_max_keep_array = new int[blueN];
	for(int i=0;i<blueN;i++){
	    blue_max_keep_array[i] = stdIn.nextInt();
	}
	red_max_keep_array = new int[redN];
	for(int i=0;i<redN;i++){
	    red_max_keep_array[i] = stdIn.nextInt();
	}
    }
}
