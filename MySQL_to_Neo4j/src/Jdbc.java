import java.sql.*;

public class Jdbc {
	static final String Driver="com.mysql.jdbc.Driver";
	static final String URL="jdbc:mysql://localhost/classicmodels";	
	static final String USER="root";
	static final String PASS = "123456";
	private Connection conn=null;
	private Statement stmt=null;
	
	public Connection getConn() {
		return conn;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void connect(){
		try {
			
			Class.forName("com.mysql.jdbc.Driver");			
			conn=DriverManager.getConnection(URL,USER,PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void close() {
		try {
			if(stmt!=null)
				stmt.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		try {
			if(conn!=null)
				conn.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
}
