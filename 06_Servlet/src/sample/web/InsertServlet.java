package sample.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/InsertServlet")
public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection connection = null;
		try{
			InitialContext initCtx = new InitialContext();

			DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/localDB");

			connection = ds.getConnection();

			connection.setAutoCommit(false);

			log("接続を開きました");

			insert(connection);
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				connection.close();
				log("接続を閉じました");
			}catch(SQLException e){
			 throw new ServletException(e);
			}
		}
		request.getRequestDispatcher("/complete.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private void insert(Connection connection) throws Exception
	{
//		String sql = "INSERT INTO 'sample'.'todo_list' "
//				+ " ( 'title','task','limitdate','lastupdate','userid','status' "
//				+ " ) VALUES (?,?,?,now(),?,?);";
		String sql = "INSERT INTO `sample`.`todo_list` ( `title`,`task`, `limitdate`, `lastupdate`, `userid`, `status` "
                + ") VALUES ( ? , ? , ? , now() , ? , ? );";
		try{
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "講習会");
			statement.setString(2, "講習会のスライド作成、配布ソースコードの準備");
			statement.setString(3, "2013-04-09");
			statement.setString(4, "USER99");
			statement.setString(5, "0");

			int count = statement.executeUpdate();

			log("1つ目の追加：" + count);


			statement.setString(1, "講習会");
			statement.setString(2, "講習会のスライド作成、配布ソースコードの準備2");
			statement.setString(3, "2013-04-10");
			statement.setString(4, "USER99");
			statement.setString(5, "0");

			count = statement.executeUpdate();

			log("2つ目の追加：" + count);

			connection.commit();
		}catch(Exception e){
			connection.rollback();
			throw e;
		}

	}
}
