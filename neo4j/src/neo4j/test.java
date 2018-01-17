package neo4j;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.AuthenticationException;

import static org.neo4j.driver.v1.Values.parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;
public class test {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Neo4jdriver driver;
		
		driver = new Neo4jdriver("bolt://localhost:7687","neo4j","123");
		
		Session session=driver.getSession();
		
		StatementResult result=session.run("match(p) return p");

//		for(int i=0,j=0; i<10; i++){
//			session.run("CREATE (p:Person {name: {name}})",parameters( "name", data[i][j] ));
//			session.run("MERGE (g:Gender {label: {label}})",parameters( "label", data[i][j+1] ));
//			session.run("MERGE (b:Birthday {data: {data}})",parameters( "data", data[i][j+2] ));
//			session.run("MERGE (t:Phone {number: {number}})",parameters( "number", data[i][j+3] ));
//			session.run("MERGE (w:Workunit {address: {address}})",parameters( "address", data[i][j+4] ));
//			
//			session.run("match (p:Person {name:$name}),(g:Gender {label: $label}) with p,g create (p)-[:sex]->(g)",parameters("name", data[i][j],"label", data[i][j+1]));
//			session.run("match (p:Person {name:$name}),(b:Birthday {data: $data}) with p,b create (p)-[:birthday]->(b)",parameters("name", data[i][j],"data", data[i][j+2]));
//			session.run("match (p:Person {name:$name}),(t:Phone {number: $number}) with p,t create (p)-[:phone]->(t)",parameters("name", data[i][j],"number", data[i][j+3]));
//			session.run("match (p:Person {name:$name}),(w:Workunit {address:$address}) with p,w create (p)-[:workunit]->(w)",parameters("name", data[i][j],"address", data[i][j+4]));
//			
//		}
		session.run( "CREATE (a:Person {name: {name}, title: {title}})",parameters( "name", "Arthur", "title", "King" ) );

//		StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = {name} " +
//		                                      "RETURN a.name AS name, a.title AS title",parameters( "name", "Arthur" ) );
//		while ( result.hasNext() )
//		{
//		    Record record = result.next();
//		    System.out.println( record.get( "title" ).asString() + " " + record.get( "name" ).asString() );
//		}
		while (result.hasNext()) {
			Record record=result.next();
			System.out.println(record.values().get(0).asNode().labels());
			
		}
		System.out.println("Íê³É");                                      
		session.close();
		driver.getDriver().close();
	}
}
