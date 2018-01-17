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
		//����Directory,����λ��(�ڴ���ļ�)
		File indexFile=new File("C:\\Users\\lenovo\\Desktop\\lucene");
		Directory dir=FSDirectory.open(indexFile.toPath());
		
		Analyzer analyzer=new WhitespaceAnalyzer();
		//��������������
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		
		IndexWriter writer=new IndexWriter(dir, config);
		//����Document
		Document doc=null;
		//Document���Field
		//1.��Ҫ����������ļ�
		File file=new File("C:\\Users\\lenovo\\Desktop\\csv\\�ɼ�����\\haungye1.xlsx");
//		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		String [][] data=GetData.getData(file, 0);
		//2.�ļ�����д��doc��
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
	 * @param s ��ѯ������
	 * @throws Exception
	 */
	
	public void search(String s) throws Exception{

		//����Directory����ȡ����
		Directory directory=FSDirectory.open(new File("src\\lucene").toPath());
		//����IndexReader����ȡ����
		IndexReader reader=DirectoryReader.open(directory);
		//��������������
		IndexSearcher searcher=new IndexSearcher(reader);
		//������ѯ������

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
		//����IndexReader����ȡ����
		IndexReader reader=DirectoryReader.open(directory);
		//��������������
		IndexSearcher searcher=new IndexSearcher(reader);
		
		Analyzer analyzer=new WhitespaceAnalyzer();
		String searchText="����ְҵ";
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
//		indexManager.search("��Сǿ");
		indexManager.queryParser();
		long endTime=System.currentTimeMillis();

		System.out.println("wancheng");
		System.out.println(endTime-startTime);
	}

}
