import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import java.io.File;
import java.io.FileReader;
import java.util.Date;

public class IndexFiles2 {

    static final String INDEX_DIR = "index";
    static final String FILE_DIR = "D";
    static final String[] FILE_DIR2 = {"D/00","D/01","D/02","D/03","D/04",
				       "D/05","D/06","D/07","D/08","D/09",
				       "D/10","D/11","D/12","D/13","D/14"
    };
    
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
	
	Date start = new Date();
	System.out.println("インデックス作成中...");
	
	Analyzer analyzer = new SimpleAnalyzer();
	IndexWriter writer = new IndexWriter(FSDirectory.open(new File(INDEX_DIR)),
					     analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
	
	// フォルダ数分ループ
	for(int i=0;i<FILE_DIR2.length;i++){
	    File[] files = new File(FILE_DIR2[i]).listFiles();
	    for (File file : files) {
		// System.out.println("ファイル追加: " + file.getPath());
		Document doc = new Document();
		doc.add(new Field("path", file.getPath(),
				  Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("contents", new FileReader(file)));
		writer.addDocument(doc);
	    }
	}
	
	System.out.println("最適化中...");
	writer.optimize();
	
	writer.close();
	
	Date end = new Date();
	System.out.println("インデックス作成時間: " + (end.getTime() - start.getTime()) + " ミリ秒");
    }
}