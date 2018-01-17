import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//edge\[(.*)\]
public class test {

	public static void main(String[] args) {
		String result;
		ExtractGml extractGml=new ExtractGml();
		SaveToNeo4j saveToNeo4j=new SaveToNeo4j();
		result=extractGml.readGML("src/temp.gml");
		System.out.println(result);
		List<String> nodes=extractGml.getNode(result);

		List<String> edges=extractGml.getEdge(result);
		saveToNeo4j.conNeo4j();
		saveToNeo4j.saveNode(nodes);
		saveToNeo4j.saveEdge(edges);
		saveToNeo4j.closeCon();
		System.out.println("wanc");

		
		
	}

}
