import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import java.io.File;
import java.io.FileReader;
import java.util.Date;

public class IndexFiles {

    static final String INDEX_DIR = "index";		// インデックス保管フォルダ
    static final String FILE_DIR = "D/00";		// インデキシング対象フォルダ
    
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
	
	Date start = new Date();
	System.out.println("インデックス作成中...");
	
	Analyzer analyzer = new SimpleAnalyzer();
	IndexWriter writer = new IndexWriter(FSDirectory.open(new File(INDEX_DIR)),
					     analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
	
	File[] files = new File(FILE_DIR).listFiles();
	for (File file : files) {
	    System.out.println("ファイル追加: " + file.getPath());
	    Document doc = new Document();
	    doc.add(new Field("path", file.getPath(),
			      Field.Store.YES, Field.Index.NOT_ANALYZED));
	    doc.add(new Field("contents", new FileReader(file)));
	    writer.addDocument(doc);
	}
	
	System.out.println("最適化中...");
	writer.optimize();
	
	writer.close();
	
	Date end = new Date();
	System.out.println("インデックス作成時間: " + (end.getTime() - start.getTime()) + " ミリ秒");
    }
}