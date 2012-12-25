import java.sql.*;

class UserDBSample {

    public static void main(String[] args) {
	try {
	    final String driver = "com.mysql.jdbc.Driver";   // JDBC ドライバの登録
	    final String server = "localhost";   // MySQL Server IP または HostName
	    final String dbname = "test";
	    final String url = "jdbc:mysql://"+server+"/"+dbname+"";
	    final String user = "root";
	    final String password = "root";
	    Class.forName(driver);
	    // データベースとの接続
	    Connection con = DriverManager.getConnection(url, user, password);
	    // テーブル照会実行
	    Statement stmt = con.createStatement();
	    // SQL文
	    String sql = "SELECT * FROM userdb";
	    // SQL実行
	    ResultSet result = stmt.executeQuery(sql);
	    // 結果を出力
	    while(result.next()){
		int id = result.getInt("id");
		int age = result.getInt("age");
		System.out.println(id+" ("+age+")");
	    }
	    // クローズ
	    result.close();
	    stmt.close();
	    con.close();
	    
	    // 例外処理
	} catch (SQLException e) {
	    System.err.println("SQL failed.");
	    e.printStackTrace ();
	} catch (ClassNotFoundException ex) {
	    ex.printStackTrace ();
	}
    }

/* テーブル ----------

CREATE TABLE test.userdb
(
  id INT NOT NULL,
  age INT,
  CONSTRAINT PRIMARY KEY (id)
)
------------------- */
}
