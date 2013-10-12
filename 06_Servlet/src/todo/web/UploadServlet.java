package todo.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import todo.dao.TodoDao;
import todo.entity.TodoValueObject;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet( urlPatterns={"/todo/upload"})
@MultipartConfig(location="C:/tmp/")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Part part = request.getPart("uploadfile");

		String filename = null;
		for(String cd :part.getHeader("Content-Disposition").split(";")){
			cd = cd.trim();
			if(cd.startsWith("filename")){
				filename = cd.substring(cd.indexOf("=") +1).trim().replace("\"", "");
				break;
			}
		}
		// リクエストパラメータのidを取得する。
        String idStr = request.getParameter("id");
        log("idStr:" + idStr);
        int id = Integer.parseInt(idStr);

        // アップロードしたファイルを書きだす
        String message = null;
        if ( filename != null ) {
            log(">> file write start.");

            // アップロードされたファイル名は、OS依存のファイルパスなどを含んでいるので置換する。
            // \は/に置換し、その後ファイル名のみ抽出する。
            filename = filename.replace("\\", "/");

            int pos = filename.lastIndexOf("/");
            if ( pos >= 0 ) {
                filename = filename.substring(pos+1);
            }
            log("filename : " + filename);
            part.write(filename);

            log("   complete!");

            // アップロードが完了した後はデータベースに登録する
            // 保存するのはファイル名のみ。完全パスは含まない。
            TodoValueObject vo = new TodoValueObject();
            vo.setId(id);
            vo.setFileName(filename);

            TodoDao dao = new TodoDao();
            try {
                int result = dao.updateUploadInfo(vo);
                log("アップロード登録結果:" + result);
            } catch (Exception e) {
                throw new ServletException(e);
            } finally {
                // DAOの処理が完了したら接続を閉じる
                dao.closeConnection();
            }

            message = "[ " + filename + " ]のアップロードが完了しました";
        } else {
            log("upload filename is blank.");
            message = "アップロードが失敗しました";
        }

        request.setAttribute("message", message);

        request.getRequestDispatcher("/todo/detail?id=" + id).forward(request, response);
        //request.getRequestDispatcher("/complete.jsp").forward(request, response);
	}


}
