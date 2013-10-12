package todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CommonMySQLDao {


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

	public PreparedStatement getPreparedStatement(String sql) throws Exception{
		return getConnection().prepareStatement(sql);
	}

	/**
	 * トランザクションのコミットを行う
	 */
	public void commit() throws SQLException{
		connection.commit();
	}

	/**
	 * トランザクションのロールバックを行う
	 */
	public void rollback() throws SQLException{
		connection.rollback();
	}
}
