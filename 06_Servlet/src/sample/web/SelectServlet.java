package sample.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import sample.entity.Todo;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;


/**
 * Servlet implementation class SelectServlet
 */
@WebServlet("/SelectServlet")
public class SelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectServlet() {
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

			List<Todo> resultList = select(connection);

			request.setAttribute("list", resultList);

			request.getRequestDispatcher("/jsp/list.jsp").forward(request, response);
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private List<Todo> select(Connection connection) throws Exception
	{

		String sql = "SELECT title , task, limitdate, lastupdate, userid, status"
						+ " FROM todo_list WHERE userid like CONCAT('%',?,'%');";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, "USER");

		ResultSet rs = statement.executeQuery();

		List<Todo> resultList = new ArrayList<>();

		while(rs.next()){

			Todo todo = new Todo();
			todo.setTitle(rs.getString("title"));
			todo.setTask(rs.getString("task"));
			todo.setLimitdate(rs.getTimestamp("limitdate"));
			todo.setLastupdate(rs.getTimestamp("lastupdate"));
			todo.setUserid(rs.getString("userid"));
			todo.setStatus(rs.getInt("status"));
			resultList.add(todo);
		}

		return resultList;

	}
}
