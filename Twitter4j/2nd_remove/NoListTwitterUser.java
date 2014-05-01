import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashSet;

/**
 * リストに登録されているユーザとフォローしているユーザを読み込んで，
 * リストに格納されおらず，フォローのみしているユーザを吐き出す
 * @author lanevok
 * @date 2014/05/01
 *
 */
public class NoListTwitterUser {

    HashSet<String> followUsers;
    HashSet<String> userListUsers;
    
    public NoListTwitterUser() {
	followUsers = new HashSet<>();
	userListUsers = new HashSet<>();
    }
    
    public static void main(String[] args) {
	try {
	    System.setOut(new PrintStream(new File("follow_only.txt")));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	NoListTwitterUser nltu = new NoListTwitterUser();
	nltu.readFollowUser();
	nltu.readUserList();
	nltu.compareUser();
    }
    
    private void compareUser() {
	int cnt = 0;
	for(String user : followUsers){
	    if(!userListUsers.contains(user)){
		System.out.println(user);
		cnt++;
	    }
	}
	System.err.println("compare user : "+cnt);
    }
    
    private void readUserList() {
	try {
	    @SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("list.txt")));
	    String user;
	    while((user=br.readLine())!=null){
		String[] split = user.split("\t");
		userListUsers.add(split[1]);
	    }
	} catch (IOException e) {}
	System.err.println("userLustUsers size = "+userListUsers.size());
    }
    
    private void readFollowUser() {
	try {
	    @SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("follow.txt")));
	    String user;
	    while((user=br.readLine())!=null){
		followUsers.add(user);
	    }
	} catch (IOException e) {}
	System.err.println("followUsers size = "+followUsers.size());
    }
}
