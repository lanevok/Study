package study5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import study5.Main2.TweetD;

public class MMI_MS {


	double PARAM;
	Sim sim;
	Map<Integer, TweetD> data;
	List<TweetD> picked;

	public MMI_MS() {
		sim = new Sim();
		picked = new ArrayList<Main2.TweetD>();
	}

	public void init(double param, Map<Integer, TweetD> _data){
		PARAM = param;
		data = new HashMap<Integer, Main2.TweetD>();
		for(Integer id : _data.keySet()){
			data.put(id, _data.get(id));
		}
	}

	public List<TweetD> getPickedList(){
		return picked;
	}

	/**
	 * MMI-MS 最適を1つ得る
	 * @return 最適なツイートデータクラス
	 */
	public TweetD getOpt(){
		int optID = -1;
		double opt_value = Double.MIN_VALUE;
		for(Integer id : data.keySet()){
			double sim_value = Double.MIN_VALUE;
			TweetD target = data.get(id);
			for(TweetD d : picked){
				sim_value = Math.max(sim_value, getTweetSim(target.ma, d.ma));
			}
			double score = (PARAM*target.impotant_value) - ((1-PARAM)*sim_value);
			if(opt_value<score){
				opt_value = score;
				optID = id;
			}
		}

		TweetD optD = data.get(optID);
		picked.add(optD);
		data.remove(optID);
		return optD;
	}

	private double getTweetSim(String tw1, String tw2) {
		double sum = 0.0;
		String[] tw1_w = tw1.split(",");
		String[] tw2_w = tw2.split(",");
		for(String w1 : tw1_w){
			for(String w2 : tw2_w){
				sum += sim.getSim(w1, w2);
			}
		}
		double ans = sum/(1.0*tw1_w.length*tw2_w.length);
		return ans;
	}
}
