import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitterでフォローしているユーザのスクリームネームをすべて取得し，テキストファイルに吐き出す
 *
 * 実行は，API Request制限が厳しいために，20ユーザ取得するごとに2分のインターバルを設ける
 *
 * @author lanevok
 * @date 2014/05/01
 */
public class TwitterFollowUserGet {
    
    final static String consumerKey = "**********";
    final static String consumerSecret = "**********";
    final static String accessToken = "**********";
    final static String accessTokenSecret ="**********";
    
    public static void main(String[] args) throws TwitterException, FileNotFoundException{
	
	// ツイッターにアクセスするための設定を用意する
	Twitter twitter = initTwitter();
	
	System.setOut(new PrintStream(new File("follow.txt")));
	
	boolean flag = true;
	long cursol = -1;		// 取得対象ページ
	int cnt = 0;				// リクエスト回数
	while(flag){
	    cnt++;
	    System.err.println("request : "+cnt);
	    
	    // Twitterにcursolページのフォローユーザ20名をリクエストする
	    PagableResponseList<User> tmp = twitter.getFriendsList("lanevok", cursol);
	    
	    try {
		// インターバル
		System.err.print("sleep ... ");
		TimeUnit.MINUTES.sleep(2);
		System.err.println("go!");
	    } catch (InterruptedException e) {}
	    
	    // ユーザの出力
	    for(User nowUser : tmp){
		System.out.println(nowUser.getScreenName());
	    }
	    
	    // 次ページがあるか確かめ，あれば，カーソルをずらす
	    if(tmp.hasNext()) {
		cursol = tmp.getNextCursor();
	    } else {
		// ループ終了
		flag = false;
	    }
	}
    }
    
    /**
     * ツイッターにアクセスするための設定を用意する
     */
    private static Twitter initTwitter() {
	ConfigurationBuilder cb = new ConfigurationBuilder();
	cb.setDebugEnabled(true)
	    .setOAuthConsumerKey(consumerKey)
	    .setOAuthConsumerSecret(consumerSecret)
	    .setOAuthAccessToken(accessToken)
	    .setOAuthAccessTokenSecret(accessTokenSecret);
	TwitterFactory tf = new TwitterFactory(cb.build());
	return tf.getInstance();
    }
}