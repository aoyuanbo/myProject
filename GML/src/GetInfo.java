import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
//node \[(.*?)\]
import java.util.regex.Pattern;
public class GetInfo {

	public static void main(String[] args) {
		
		List<String> node=new ArrayList<>();
		List<String> edge=new ArrayList<>();
		
		String nodeReg="node \\[(.*?)\\]";
		String edgeReg="edge \\[(.*?)\\]";
		Pattern nodePattern=Pattern.compile(nodeReg);
		Pattern edgePattern=Pattern.compile(edgeReg);
		ReadGML readGML=new ReadGML();
		String result;
		result=readGML.getFile("src/1.gml");
		Matcher matcher=nodePattern.matcher(result);
		while(matcher.find()){
			node.add(matcher.group(1));
//			System.out.println(matcher.group(0));
		}
		matcher=edgePattern.matcher(result);
		while(matcher.find()){
			edge.add(matcher.group(1).trim());
//			System.out.println(matcher.group(0));
		}
		
		String temp[]=null;
		for (String string : node) {
			temp=string.trim().split(" ");
			for (int i = 0; i < temp.length; i++) {
				String string1 = temp[i];
				System.out.println(temp[i]);
			}
		}
		System.out.println(temp.length);
		for (String string : edge) {
			System.out.println(string);
		}
		
		
	}

	
}
