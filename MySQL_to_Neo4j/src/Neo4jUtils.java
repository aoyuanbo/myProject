import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;




public class Neo4jUtils {
	private static Driver driver;
	private static final String URL="bolt://localhost:7687";	
	private static final String USER="neo4j";
	private static final String PASS = "123";
	
	public static Driver getDriver() {
		return driver;
	}
	public static void connect(){
		driver=GraphDatabase.driver(URL, AuthTokens.basic(USER, PASS)); //Á¬½ÓNeo4j
	}
	
	public static void close(){
		driver.session().close();
		driver.close();
	}
}
