package share.test;
import java.sql.ResultSet;

import share.db.mysql.DataBase;


public class DBAccessTest {

    /**
     * データベース操作のテストコード
     * @param args
     */
    public static void main(String[] args) {
	try {
	    // データベースとの接続クラスを生成する
	    DataBase db = new DataBase("localhost:3306","db","root","root");
	    // データベースにコマンドを送り結果を受け取る
	    db.executeSQL("SELECT * FROM db.user");
	    ResultSet result = db.stmt.getResultSet();
	    while(result.next()){
		int id = result.getInt("id");
		String name = result.getString("name");
		System.out.println(id+" ("+name+")");
	    }
	    db.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
