package share.db.mysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
 * データベースに接続・SQLコマンドを実行
 *
 * @author (TAT)chaN
 * @version 04
 * @since 2013.5.12
 *
 */
public class DataBase {
    private Connection con;
    public Statement stmt;
    
    /**
     * データベースに接続
     * (オーバーロードあり)
     * @param server サーバーURL
     * @param schema スキーマデータベース名
     * @param user mySQLログインユーザ名
     * @param password mySQLログインユーザパスワード
     * @param noTrace SQL生成トレース出力拒否
     */
    public DataBase(String server, String schema, String user, String password, boolean noTrace) {
	if(!noTrace) System.err.println("[SQL Creating]");
	final String driver = "com.mysql.jdbc.Driver";
	//		final String server = "localhost:3306";
	//		final String schema = "db";
	final String url = "jdbc:mysql://"+server+"/"+schema+"";
	//		final String user = "root";
	//		final String password = "root";
	try {
	    Class.forName(driver);
	} catch (ClassNotFoundException e) {
	    System.err.println("!![SQL Driver Init Error] Please Check (mySQL Driver) & (Java Build Path)");
	    System.exit(0);
	}
	try {
	    this.con = DriverManager.getConnection(url, user, password);
	} catch (SQLException e) {
	    System.err.println("!![SQL Connection Error] Please Check (ServerURL) & (DB Schema) & (UserName) & (Password)");
	    System.exit(0);
	}
	try {
	    this.stmt = con.createStatement();
	    if(!noTrace) System.err.println("[SQL Created]");
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * データベースに接続
     * (オーバーロードあり)
     * @param server サーバーURL
     * @param schema スキーマデータベース名
     * @param user mySQLログインユーザ名
     * @param password mySQLログインユーザパスワード
     */
    public DataBase(String server, String schema, String user, String password){
	this(server, schema, user, password, false);
    }
    
    /**
     * SQLを実行する。
     * 警告やエラーが発生したらその場で情報を出力する。
     * (オーバーロードあり)
     * @param sql SQL文
     * @param noTrace SQL実行トレース出力拒否
     */
    public void executeSQL(String sql, boolean noTrace){
	try {
	    if(!noTrace) System.err.println("[SQL Request] "+sql+";");
	    long s = System.currentTimeMillis();
	    // SQL実行
	    boolean flag = stmt.execute(sql);
	    // SQL実行時間の出力
	    if(!noTrace) System.err.println("[SQL Response] "+(System.currentTimeMillis()-s)/1000.0+" sec");
	    // SQL結果の出力
	    if(!noTrace){
		String info = flag?"T:select":"F:update/insert/delete";
		System.err.println("[SQL Info] "+info+" , Line:"+stmt.getUpdateCount());
	    }
	    // Warning取得
	    SQLWarning w = stmt.getWarnings();
	    if(w!=null) System.err.println("!![SQL Execute Warning] SQL ("+sql+";)");
	    while (w != null) {
		// Warning文出力
		System.err.println ("\tWarning Description: " + w.getMessage());
		System.err.println ("\tSQL State: " + w.getSQLState());
		System.err.println ("\tCode: " + w.getErrorCode());
		w=w.getNextWarning();
	    }
	} catch (SQLException e) {
	    // Error例外取得
	    System.err.println("!![SQL Execute Error] Please Check SQL ("+sql+";)");
	    while(e != null) {
		// Error文出力
		System.err.println("\tError Message:  " + e.getMessage());
		System.err.println("\tSQL State: " + e.getSQLState());
		System.err.println("\tError Code: " + e.getErrorCode());
		e=e.getNextException();
	    }
	}
    }
    
    /**
     * SQLを実行する。
     * 警告やエラーが発生したらその場で情報を出力する。
     * (オーバーロードあり)
     * @param sql SQL文
     */
    public void executeSQL(String sql){
	executeSQL(sql, false);
    }
    
    /**
     * データベースのクローズ
     * (オーバーロードあり)
     * @param noTrace SQL終了トレース出力拒否
     */
    public void close(boolean noTrace) {
	try {
	    this.stmt.close();
	    this.con.close();
	    if(!noTrace) System.err.println("[SQL Closed]");
	} catch (SQLException e) {
	    System.err.println("!![SQL Close Error]");
	}
    }
    
    /**
     * データベースのクローズ
     * (オーバーロードあり)
     */
    public void close(){
	close(false);
    }
    
}
