import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MahoutSample {

    /**
     * Mahoutの簡単なサンプル １番のユーザに対するレコメンドを１つだけ出力します。
     * src/main/java下にこのファイルを置く。
     * mahoutセットアップ方法等の詳細は、http://d.hatena.ne.jp/lanevok/20131210/1386630580
     * @param args
     */
    public static void main(String[] args) {
	try {
	    // データの取り込み
	    DataModel dataModel = new FileDataModel(new File(
							     "src/main/resources/data.csv"));
	    // 相関性の評価基準の設定
	    UserSimilarity similarity = new PearsonCorrelationSimilarity(
									 dataModel);
	    // 評価の近い人を探すロジックを決めてる？
	    UserNeighborhood neighborhood = new NearestNUserNeighborhood(3,
									 similarity, dataModel);
	    // レコメンダの作成
	    Recommender recommender = new GenericUserBasedRecommender(
								      dataModel, neighborhood, similarity);
	    
	    // 1番の人に対するレコメンドが1つ
	    List<RecommendedItem> recommendations = recommender.recommend(1, 1);
	    for (RecommendedItem recommendation : recommendations) {
		System.out.println(recommendation);
	    }
	    
	    System.out.println("end");
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (TasteException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
}

