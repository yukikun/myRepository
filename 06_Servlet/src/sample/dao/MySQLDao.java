package sample.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MySQLDao {


	protected Connection connection = null;
	/**
	 * データベースとの接続を取得する。
	 */
	public Connection getConnection() throws Exception{
		try{
			if(connection == null || connection.isClosed()){
				InitialContext initCtx = new InitialContext();


				DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/localDB");
				connection = ds.getConnection();

				connection.setAutoCommit(false);
			}

		}catch(Exception e){
			e.printStackTrace();
			connection = null;
			throw e;
		}
		return connection;

	}

	/**
	 * 接続を閉じる
	 *
	 */
	public void closeConnection(){
		try{
			connection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			connection = null;
		}
	}
}
