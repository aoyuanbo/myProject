import static org.neo4j.driver.v1.Values.parameters;

import java.util.List;

import org.neo4j.driver.v1.Session;

public class Test {

	public static void main(String[] args) {
		List<String> tableNames,columnNames,pkNames,fkNames;
		Session session;
		tableNames=MySQLUtils.getTables();
		Neo4jUtils.connect();
		session=Neo4jUtils.getDriver().session();
		//创建实体节点和属性节点，并建立联系
		for (String tableName : tableNames) {
			int rowCount=MySQLUtils.getRowCount(tableName);
			columnNames=MySQLUtils.getColumns(tableName);
			pkNames=MySQLUtils.getPrimaryKey(tableName);
			System.out.println(pkNames.toString());
			for (int i = 1; i <=rowCount; i++) {
				session.run("CREATE (p:"+tableName+":entity {id:$id} )",parameters("id",i));
				for(int j=0;j<pkNames.size();j++){
					session.run("match (p:"+tableName+":entity {id:$id} ) set p+={"+pkNames.get(j)+":$value}",parameters("id",i,"value",MySQLUtils.getValue(tableName, pkNames.get(j), i)));
				}				

				columnNames.removeAll(pkNames);
				List<String> temp2=MySQLUtils.getRowValue(tableName, columnNames,i);
				for(int j=0; j<columnNames.size();j++){
					System.out.println(columnNames.get(j)+":"+temp2.get(j));
					session.run("match (p:"+tableName+" {id:$name}) with p merge (w:"+tableName+":attr{"+columnNames.get(j)+":$vaule}) create (p)-[:"+columnNames.get(j)+"]->(w)",parameters("name",i,"vaule", temp2.get(j)));
				}
			}

			System.gc();
		}
		//根据外键穿件联系
		for (String tableName : tableNames){
			int rowCount=MySQLUtils.getRowCount(tableName);
			columnNames=MySQLUtils.getColumns(tableName);
			fkNames=MySQLUtils.getForeignKey(tableName);
				for(int j=0;j<fkNames.size();j+=3){
					session.run("match (p:"+tableName+"),(w:"+fkNames.get(j+1)+") "
							+ "where p." +fkNames.get(j)+" =w. "+fkNames.get(j+2)+" create(p)-[:fk]->(w),(w)-[:fk]->(p)");
				}		
		}
		Neo4jUtils.close();
	}
		
}


