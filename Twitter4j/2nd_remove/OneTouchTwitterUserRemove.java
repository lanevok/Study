import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * リストに追加されておらず，フォローのみとなっているユーザが書かれているテキストファイルを読み込み，
 * 1ユーザずつ，プロフィールと呟きを表示する．
 * また，その際にリムーブするのであれば，「y」を入力することで，リムーブ操作も行う．
 *
 * @author lanevok
 * @date 2014/05/01
 */
public class OneTouchTwitterUserRemove {

    final static String consumerKey = "**********";
    final static String consumerSecret = "**********";
    final static String accessToken = "**********";
    final static String accessTokenSecret ="**********";
    
    public static void main(String[] args) throws TwitterException, FileNotFoundException{
	
	// ツイッターにアクセスするための設定を用意する
	Twitter twitter = initTwitter();
	
	// テキストから読み込んで保管するユーザのリスト
	List<String> users = new ArrayList<>();
	
	// テキストからユーザを読み込みリストに格納する
	try {
	    @SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("follow_only.txt")));
	    String user;
	    while((user=br.readLine())!=null){
		users.add(user);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	// リストの途中から開始するための変数
	int start = 0;
	
	// リストのユーザを順に実行
	for(int i=start;i<users.size();i++){
	    // リストからユーザを取得する
	    String user = users.get(i);
	    
	    // ツイッターからユーザに関する情報を取得する
	    User user1 = twitter.showUser(user);
	    
	    // ユーザ関連情報を出力する
	    System.out.println("List \t\t\t: "+i);
	    System.out.println("ScreenName \t: " + user1.getScreenName());
	    System.out.println("User's Name \t: " + user1.getName());
	    System.out.println("Followers \t\t: " + user1.getFollowersCount());
	    System.out.println("Friends \t\t: " + user1.getFriendsCount());
	    System.out.println("location \t\t: " + user1.getLocation());
	    System.out.println("URL \t\t\t: "+user1.getURL());
	    System.out.println("Description \t: "+user1.getDescription());
	    
	    // ユーザのタイムラインを取得する
	    ResponseList<Status> statuses = twitter.getUserTimeline(user);
	    
	    // ユーザのタイムラインを出力する
	    for (Status status : statuses) {
		System.out.print("["+status.getCreatedAt()+"] [");
		System.out.println(status.getText().replaceAll("\n", " ")+"]");
	    }
	    
	    // リムーブするかの入力を受け取る
	    System.out.print("remove?   ");
	    @SuppressWarnings("resource")
		String input = new Scanner(System.in).next();
	    
	    // リムーブする際の処理を行う
	    if(input.equals("y")){
		twitter.destroyFriendship(user);
		System.out.println("removed !");
	    }
	    else {
		System.out.println("");
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