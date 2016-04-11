package BBS.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String URL = "jdbc:mysql://localhost";
	private static final String DB_NAME = "/bbs";
	private static final String USER = "root";
	private static final String PASSWORD = "sato1991";

	static{
		try{
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e){
			throw new RuntimeException();
		}
	}

	public static Connection getConnection(){
		Connection connection = null;
		try{
			connection = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
			connection.setAutoCommit(false);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return connection;
	}

	public static void commit(Connection connection){
		try{
			connection.commit();
			System.out.println("コミット");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void rollback(Connection connection){
		try{
			connection.rollback();
			System.out.println("ロールバック");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void close(Connection connection){
		try{
			connection.close();
			System.out.println("クローズ");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
