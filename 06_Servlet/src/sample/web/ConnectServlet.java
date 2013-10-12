package sample.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ConnectServlet
 */
@WebServlet("/ConnectServlet")
public class ConnectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectServlet() {
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
			//　JNDI参照コンテキストを取得
			InitialContext initCtx = new InitialContext();

			// Tomcatで管理されたデータベース雪像クォJNDI経由で取得
			// java:comp/env/を必ずつける
			DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/localDB");
			//データベース接続を取得
			connection = ds.getConnection();

			//接続が正しく完了するとコンソールにメッセージを出力
			log("接続を開きました");
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				connection.close();
				log("接続をとじました");
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

}
