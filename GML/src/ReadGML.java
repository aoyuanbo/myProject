import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadGML {
	private String url;
	private File file;
	
	public String getFile(String url) {
		file=new File(url);
		StringBuffer sbBuffer=new StringBuffer();
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			int i;
			while((i=reader.read())!=-1){
				if(((char)i) != '\n'){
					sbBuffer.append((char)i);
				}else {
					sbBuffer.append(' ');
				}
			}
			System.out.println(sbBuffer);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return sbBuffer.toString();
	}
}
