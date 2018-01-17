import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class IndexManager {
	@Test
	public void createIndex() throws Exception {
		//创建Directory,索引位置(内存和文件)
		File indexFile=new File("C:\\Users\\lenovo\\Desktop\\lucene");
		Directory dir=FSDirectory.open(indexFile.toPath());
		
		Analyzer analyzer=new WhitespaceAnalyzer();
		//创建索引生成器
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		
		IndexWriter writer=new IndexWriter(dir, config);
		//创建Document
		Document doc=null;
		//Document添加Field
		//1.需要添加索引的文件
		File file=new File("C:\\Users\\lenovo\\Desktop\\csv\\采集数据\\haungye1.xlsx");
//		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		String [][] data=GetData.getData(file, 0);
		//2.文件内容写入doc中
		for(int i=0;i<data.length;i++){
				doc=new Document();
				doc.add(new TextField("name", data[i][0], Field.Store.YES));
				doc.add(new TextField("phone", data[i][3], Field.Store.YES));
				doc.add(new TextField("address", data[i][4], Field.Store.YES));
				writer.addDocument(doc);
		}
		
		writer.close();
		dir.close();
	}
	
	/**
	 * 
	 * @param s 查询的内容
	 * @throws Exception
	 */
	
	public void search(String s) throws Exception{

		//创建Directory，获取索引
		Directory directory=FSDirectory.open(new File("src\\lucene").toPath());
		//创建IndexReader，读取索引
		IndexReader reader=DirectoryReader.open(directory);
		//创建索引查找器
		IndexSearcher searcher=new IndexSearcher(reader);
		//创建查询解析器

		Query query=new TermQuery(new Term("address",s));
//		Query query=new FuzzyQuery(new Term("address",s));
		TopDocs docs=searcher.search(query, 10);
		System.out.println(docs.totalHits);
		for (ScoreDoc scoreDoc : docs.scoreDocs) {

			Document document=searcher.doc(scoreDoc.doc);

			System.out.println(document.get("address"));
		}
		reader.close();
		directory.close();
	}
	
	@Test
	public void queryParser() throws ParseException, IOException{
		Directory directory=FSDirectory.open(new File("C:\\Users\\lenovo\\Desktop\\lucene").toPath());
		//创建IndexReader，读取索引
		IndexReader reader=DirectoryReader.open(directory);
		//创建索引查找器
		IndexSearcher searcher=new IndexSearcher(reader);
		
		Analyzer analyzer=new WhitespaceAnalyzer();
		String searchText="自由职业";
		QueryParser queryParser=new QueryParser("address", analyzer);
		Query query=queryParser.parse(searchText);
		TopDocs docs=searcher.search(query, 10);
		System.out.println(docs.totalHits);
		for (ScoreDoc scoreDoc : docs.scoreDocs) {

			Document document=searcher.doc(scoreDoc.doc);

			System.out.println(document.get("name")+" "+document.get("phone")+" "+document.get("address"));
		}
	}
	 
	
	public static void main(String[] args) throws Exception {
		IndexManager indexManager=new IndexManager();
//		indexManager.createIndex();
		long startTime =System.currentTimeMillis();
//		indexManager.search("赵小强");
		indexManager.queryParser();
		long endTime=System.currentTimeMillis();

		System.out.println("wancheng");
		System.out.println(endTime-startTime);
	}

}
