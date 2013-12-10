import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * 任意の単語でツイートを検索する．<br>
 */
public class TwitterSearcher {

    public static void main(String[] args){
	String consumerKey = "*****";
	String consumerSecret = "*****";
	String accessToken = "*****";
	String accessTokenSecret ="*****";
	TwitterSearcher ts = new TwitterSearcher(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	try{
	    String searchword = "かぼちゃ";
	    List<Status> searchResultList = ts.search(searchword );
	    for(Status status:searchResultList){
		System.out.printf("%s\t%s\n", status.getUser().getScreenName(), status.getText());
	    }
	}catch(TwitterException e){
	    e.printStackTrace();
	}
    }
    
    Twitter twitter;
    
    /**
     * ConsumerKey，ConsumerSecret,AccessToken,AccessTokenSecretを利用して検索インスタンスを作成
     * @param consumerKey
     * @param consumerSecret
     * @param accessToken
     * @param accessTokenSecret
     */
    public TwitterSearcher(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
	Configuration configuration = new ConfigurationBuilder().setOAuthConsumerKey(
										     consumerKey).setOAuthConsumerSecret(consumerSecret)
	    .setOAuthAccessToken(accessToken)
	    .setOAuthAccessTokenSecret(accessTokenSecret).build();
	
	TwitterFactory factory = new TwitterFactory(configuration);
	
	twitter = factory.getInstance();
    }
    
    /**
     * 単語を指定して検索を行う．獲得数は最大の100
     * @param word
     * @return
     * @throws TwitterException
     */
    public List<Status> search(String word) throws TwitterException{
	return search(word, 100);
    }
    
    /**
     * 単語を指定して検索を行う．任意の数を獲得する．(最大は100)
     * @param word
     * @param page
     * @param count
     * @return
     * @throws TwitterException
     */
    public List<Status> search(String word, int count) throws TwitterException{
	Query query = new Query(word);
	query.setResultType(Query.RECENT);
	query.setCount(count);
	QueryResult result = twitter.search(query);
	return result.getTweets();
    }
    
}