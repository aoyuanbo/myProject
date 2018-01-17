import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLUtils {
	private static List<String> tablesName;
	private static List<String> primaryKeyName;
	private static List<String> foreignKeyName;
	private static List<String> columnsName;

	public static List<String> getColumns(String table){
		columnsName=new ArrayList<>();
		Jdbc mysqlJDBC=new Jdbc();
		mysqlJDBC.connect();
		try {
			
			DatabaseMetaData metaData=mysqlJDBC.getConn().getMetaData();
			ResultSet pkSet=metaData.getColumns(null,"%", table,"%");
			while(pkSet.next()){
				columnsName.add(pkSet.getString("COLUMN_NAME"));
//				System.out.println(pkSet.getString("COLUMN_NAME"));
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysqlJDBC.close();
		return columnsName;
	}
	
	public static List<String> getPrimaryKey(String table) {
		primaryKeyName=new ArrayList<>();
		Jdbc mysqlJDBC=new Jdbc();
		mysqlJDBC.connect();
		try {
			
			DatabaseMetaData metaData=mysqlJDBC.getConn().getMetaData();
			ResultSet pkSet=metaData.getPrimaryKeys(null, null, table);
			while(pkSet.next()){
				primaryKeyName.add(pkSet.getString("COLUMN_NAME"));
//				System.out.println(primaryKeyName);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysqlJDBC.close();
		return primaryKeyName;
	}
		
	public static List<String> getForeignKey(String table) {
		foreignKeyName=new ArrayList<>();
		Jdbc mysqlJDBC=new Jdbc();
		mysqlJDBC.connect();
		try {
			
			DatabaseMetaData metaData=mysqlJDBC.getConn().getMetaData();
			ResultSet pkSet=metaData.getImportedKeys(null, null, table);
			while(pkSet.next()){
				foreignKeyName.add(pkSet.getString("FKCOLUMN_NAME"));
				foreignKeyName.add(pkSet.getString("PKTABLE_NAME"));
				foreignKeyName.add(pkSet.getString("PKCOLUMN_NAME"));
				System.out.println(pkSet.getString("FKCOLUMN_NAME"));
				System.out.println(pkSet.getString("PKTABLE_NAME"));
				System.out.println(pkSet.getString("PKCOLUMN_NAME"));
//				System.out.println(pkSet.getString("FKTABLE_NAME"));
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysqlJDBC.close();
		return foreignKeyName;
	}
		
	public static List<String> getTables() {
		tablesName=new ArrayList<>();
		Jdbc mysqlJDBC=new Jdbc();
		mysqlJDBC.connect();
		try {
			
			DatabaseMetaData metaData=mysqlJDBC.getConn().getMetaData();
			ResultSet tables=metaData.getTables(null, "%", "%", new String[]{"TABLE"});
			while(tables.next()){
				tablesName.add(tables.getString("TABLE_NAME"));
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysqlJDBC.close();
		return tablesName;
	}
	
	public static String getValue(String table,String column){
		String value=null;
		Jdbc mysqlJDBC=new Jdbc();
		mysqlJDBC.connect();
		try {
			
			Statement stmt=mysqlJDBC.getConn().createStatement();
			String sql="select * from "+table;
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				if (rs.getObject(column)!=null) {
					value = rs.getObject(column).toString();
					System.out.println(value);
				}else {
					value="null";
					System.out.println(value);
				}
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysqlJDBC.close();		
		return value;
	}
	
	public static String getValue(String table,String column,int row){
		String value=null;
		Jdbc mysqlJDBC=new Jdbc();
		mysqlJDBC.connect();
		try {
			
			Statement stmt=mysqlJDBC.getConn().createStatement();
			String sql="select * from "+table;
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.absolute(row)){
				if (rs.getObject(column)!=null) {
					value = rs.getObject(column).toString();
//					System.out.println(value);
				}else {
					value="null";
//					System.out.println(value);
				}
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysqlJDBC.close();		
		return value;
	}
	
	public static List<String> getRowValue(String table, List<String> cloumns){
		List<String> rowValue=new ArrayList<>();
		Jdbc mysqlJDBC=new Jdbc();
		mysqlJDBC.connect();
		try {
			
			Statement stmt=mysqlJDBC.getConn().createStatement();
			String sql="select * from "+table;
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				for(int i=0; i<cloumns.size();i++){
					if (rs.getObject(cloumns.get(i))!=null) {
						rowValue.add(rs.getObject(cloumns.get(i)).toString());
//						System.out.println(cloumns.get(i)+":"+rs.getObject(cloumns.get(i)).toString());
					}else {
						rowValue.add("null");
//						System.out.println(cloumns.get(i)+":null");
					}
				}
				System.out.println();
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysqlJDBC.close();
		return  rowValue;
	}
	
	public static List<String> getRowValue(String table, List<String> cloumns,int row){
		List<String> rowValue=new ArrayList<>();
		Jdbc mysqlJDBC=new Jdbc();
		mysqlJDBC.connect();
		try {
			
			Statement stmt=mysqlJDBC.getConn().createStatement();
			String sql="select * from "+table;
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.absolute(row)){
				for(int i=0; i<cloumns.size();i++){
					if (rs.getObject(cloumns.get(i))!=null) {
						rowValue.add(rs.getObject(cloumns.get(i)).toString());
//						System.out.println(cloumns.get(i)+":"+rs.getObject(cloumns.get(i)).toString());
					}else {
						rowValue.add("null");
//						System.out.println(cloumns.get(i)+":null");
					}
				}
//				System.out.println();
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysqlJDBC.close();
		return  rowValue;
	}
	
	public static int getRowCount(String table){
		int rowCount=0;
		Jdbc mysqlJDBC=new Jdbc();
		mysqlJDBC.connect();
		try {
			
			Statement stmt=mysqlJDBC.getConn().createStatement();
			String sql="select * from "+table;
			ResultSet rs=stmt.executeQuery(sql);
			rs.last();
			rowCount=rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysqlJDBC.close();		
		return rowCount;
	}
	
	public static void main(String[] args) {
		MySQLUtils.getForeignKey("payments");
	}
}
