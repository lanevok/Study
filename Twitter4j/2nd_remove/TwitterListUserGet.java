import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitterのリストに格納されているユーザをリスト名と共にファイルへ吐き出す
 * @author lanevok
 * @date 2014/05/01
 */
public class TwitterListUserGet {

    final static String consumerKey = "**********";
    final static String consumerSecret = "**********";
    final static String accessToken = "**********";
    final static String accessTokenSecret ="**********";
    
    public static void main(String[] args) throws TwitterException, FileNotFoundException{
	Twitter twitter = initTwitter();
	
	System.setOut(new PrintStream(new File("result.txt")));
	
	ResponseList<UserList> lists = twitter.getUserLists(twitter.getScreenName());
	for(UserList list:lists){
	    boolean flag = true;
	    long cursol = -1;
	    while(flag){
		PagableResponseList<User> tmp = twitter.getUserListMembers(list.getId(), cursol);
		for(User nowUser : tmp){
		    System.out.println(list.getSlug()+"\t"+nowUser.getScreenName());
		}
		if(tmp.hasNext()) {
		    cursol = tmp.getNextCursor();
		} else {
		    // ループ終了
		    flag = false;
		}
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