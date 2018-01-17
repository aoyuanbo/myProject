import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class Index {	
	/**
	 * 
	 * @param file 需要创建索引的文件
	 * @param type 创建索引的类型(索引记录属性)
	 * @throws IOException 
	 */
	
	public void createIndex() throws IOException{
//		if(file==null){
//			throw new IllegalArgumentException("文件路径不能为空");
//		}
//		if(type==null){
//			throw new IllegalArgumentException("记录属性不能为空");
//		}
		File file=new File("C:\\Users\\lenovo\\Desktop\\csv\\采集数据\\haungye2.txt");
		//创建索引位置
		File indexFile=new File("src\\index");
		Directory dir=FSDirectory.open(indexFile.toPath());
		//分析器

//		SmartChineseAnalyzer smartChineseAnalyzer=new SmartChineseAnalyzer();
		Analyzer analyzer=new WhitespaceAnalyzer();
		//创建索引生成器
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, config);
		//创建Document
		Document doc=new Document();
		//Document添加Field
		//1.需要添加索引的文件
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		//2.文件内容写入doc中
		doc.add(new TextField("content", reader));
		
		writer.addDocument(doc);
		writer.close();
		dir.close();
				
	};
	
	/**
	 * 
	 * @param content 搜索的内容
	 * @param type	搜索内容的类型(查找type属性的记录)
	 */
	public void searchIndex(String content,String type){};
	
	
}
