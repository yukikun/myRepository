package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.dao.TodoDao;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/todo/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		TodoDao dao = new TodoDao();
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			int result = dao.delete(id);
			setMessage(request, "タスク[" + id + "]の削除処理が完了しました。");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			dao.closeConnection();
		}

		RequestDispatcher rd = request.getRequestDispatcher("/todo/search");
		rd.forward(request, response);

	}

	protected void setMessage(HttpServletRequest request, String message) {
		request.setAttribute("message", message);
	}
}
