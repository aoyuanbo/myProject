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
	 * @param file ��Ҫ�����������ļ�
	 * @param type ��������������(������¼����)
	 * @throws IOException 
	 */
	
	public void createIndex() throws IOException{
//		if(file==null){
//			throw new IllegalArgumentException("�ļ�·������Ϊ��");
//		}
//		if(type==null){
//			throw new IllegalArgumentException("��¼���Բ���Ϊ��");
//		}
		File file=new File("C:\\Users\\lenovo\\Desktop\\csv\\�ɼ�����\\haungye2.txt");
		//��������λ��
		File indexFile=new File("src\\index");
		Directory dir=FSDirectory.open(indexFile.toPath());
		//������

//		SmartChineseAnalyzer smartChineseAnalyzer=new SmartChineseAnalyzer();
		Analyzer analyzer=new WhitespaceAnalyzer();
		//��������������
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, config);
		//����Document
		Document doc=new Document();
		//Document���Field
		//1.��Ҫ����������ļ�
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		//2.�ļ�����д��doc��
		doc.add(new TextField("content", reader));
		
		writer.addDocument(doc);
		writer.close();
		dir.close();
				
	};
	
	/**
	 * 
	 * @param content ����������
	 * @param type	�������ݵ�����(����type���Եļ�¼)
	 */
	public void searchIndex(String content,String type){};
	
	
}
