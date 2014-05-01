import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UserList;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitterに作成したリストの名前を取得する
 * @author lanevok
 * @date 2014/05/01
 */
public class TwitterListNameGet {

    final static String consumerKey = "**********";
    final static String consumerSecret = "**********";
    final static String accessToken = "**********";
    final static String accessTokenSecret ="**********";
    
    public static void main(String[] args) throws TwitterException{
	Twitter twitter = initTwitter();
	
	ResponseList<UserList> lists = twitter.getUserLists(twitter.getScreenName());
	for(UserList list:lists){
	    System.out.println(list.getFullName());
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