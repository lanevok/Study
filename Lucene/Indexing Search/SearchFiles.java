import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import java.io.File;
import java.util.Scanner;

@SuppressWarnings("deprecation")
public class SearchFiles {

    static final String INDEX_DIR = "index";		// インデックス保管フォルダ
    
    public static void main(String[] args) throws Exception {
	
	Scanner stdIn = new Scanner(System.in, "UTF-8");
	String keyword = stdIn.next();
	System.out.println("検索実行中(keyword=" + keyword + ")...");
	
	Searcher searcher = new IndexSearcher(FSDirectory.open(new File(INDEX_DIR)));
	Analyzer analyzer = new SimpleAnalyzer();
	
	QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", analyzer);
	
	Query query = parser.parse(keyword);
	
	TopDocs topDocs = searcher.search(query, 10);
	System.out.println("該当件数: " + topDocs.totalHits + " 件");
	
	for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
	    Document doc = searcher.doc(scoreDoc.doc);
	    System.out.println(doc.get("path"));
	}
    }
}