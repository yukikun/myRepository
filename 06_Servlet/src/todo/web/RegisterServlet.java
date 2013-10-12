package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.dao.TodoDao;
import todo.entity.TodoValueObject;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/todo/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		TodoValueObject vo = new TodoValueObject();
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		vo.setId(id);
		vo.setTitle(request.getParameter("title"));
		vo.setTask(request.getParameter("task"));
		vo.setInputLimitdate(request.getParameter("limitdate"));
		vo.setUserid(request.getParameter("userid"));
		vo.setStatus(Integer.parseInt(request.getParameter("status")));

		TodoDao dao = new TodoDao();

		try{
			if(id == 0){
				dao.registerInsert(vo);
				setMessage(request, "タスクの新規登録処理が完了しました。");
			}else{
				dao.registerUpdate(vo);
				setMessage(request, "タスク[" + id + "]の更新処理が完了しました。");
			}
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			dao.closeConnection();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/todo/search");
		rd.forward(request, response);



	}

	protected void setMessage(HttpServletRequest request, String message){
		request.setAttribute("message", message);
	}
}
