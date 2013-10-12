package todo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import todo.entity.TodoValueObject;

public class TodoDao extends CommonMySQLDao {

	public List<TodoValueObject> todoList() throws Exception {

		List<TodoValueObject> returnList = new ArrayList<TodoValueObject>();

		String sql = "SELECT id, title, task, limitdate, lastupdate, userid ,label, td.status,filename "
				+ " FROM todo_list td LEFT JOIN status_list stts ON stts.status = td.status"
				+ " ORDER BY id";

		PreparedStatement statement = getPreparedStatement(sql);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			TodoValueObject vo = new TodoValueObject();

			vo.setId(rs.getInt("id"));
			vo.setTitle(rs.getString("title"));
			vo.setTask(rs.getString("task"));
			vo.setLimitdate(rs.getTimestamp("limitdate"));
			vo.setLastupdate(rs.getTimestamp("lastupdate"));
			vo.setUserid(rs.getString("userid"));
			vo.setLabel(rs.getString("label"));
			vo.setFileName(rs.getString("filename"));

			returnList.add(vo);
		}

		return returnList;
	}

	/**
	 * タスク詳細を取得する
	 */
	public TodoValueObject detail(int id) throws Exception {

		TodoValueObject vo = new TodoValueObject();

		String sql = "SELECT id ,title, task, limitdate, lastupdate, userid, label, td.status, filename "
				+ " FROM todo_list td LEFT JOIN status_list stts ON stts.status = td.status"
				+ " WHERE id  = ?";

		PreparedStatement statement = getPreparedStatement(sql);

		statement.setInt(1, id);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {

			vo.setId(rs.getInt("id"));
			vo.setTitle(rs.getString("title"));
			vo.setTask(rs.getString("task"));
			vo.setLimitdate(rs.getTimestamp("limitdate"));
			vo.setLastupdate(rs.getTimestamp("lastupdate"));
			vo.setUserid(rs.getString("userid"));
			vo.setLabel(rs.getString("label"));
			vo.setStatus(rs.getInt("status"));
			vo.setFileName(rs.getString("filename"));

		}
		return vo;
	}

	/**
	 * 新規登録を行う
	 */
	public int registerInsert(TodoValueObject vo) throws Exception{

		String sql = "INSERT INTO todo_list (title, task, limitdate, lastupdate, userid, status) "
				+ " VALUES(?,?,?,now(),?,0)";

		int result = 0;

		try{
			PreparedStatement statement = getPreparedStatement(sql);

			statement.setString(1, vo.getTitle());
			statement.setString(2, vo.getTask());
			statement.setString(3, vo.getInputLimitdate());
			statement.setString(4, vo.getUserid());

			result = statement.executeUpdate();

			super.commit();
		}catch(Exception e){
			super.rollback();
			throw e;
		}

		return result;
	}

	/**
	 * 更新処理を行う
	 */
	public int registerUpdate(TodoValueObject vo) throws Exception{
		String sql = "UPDATE todo_list SET title = ?, task = ? ,limitdate = ?"
				+ ", lastupdate = now(), userid = ? , status = ? WHERE id = ?";

		int result = 0;

		try{
			PreparedStatement statement = getPreparedStatement(sql);

			statement.setString(1, vo.getTitle());
			statement.setString(2, vo.getTask());
			statement.setString(3, vo.getInputLimitdate());
			statement.setString(4, vo.getUserid());
			statement.setInt(5, vo.getStatus());
			statement.setInt(6, vo.getId());

			result = statement.executeUpdate();

			super.commit();
		}catch(Exception e){
			super.rollback();
			throw e;
		}

		return result;
	}

	/**
	 * 削除処理を行う
	 */
	public int delete(int id) throws Exception{
		String sql = "DELETE FROM todo_list where id = ?";

		int result = 0;

		try{
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setInt(1, id);
			result = statement.executeUpdate();

			super.commit();
		}catch(Exception e){
			super.rollback();
			throw e;
		}
		return result;
	}

	/**
	 * アップロードを行う
	 */
	public int updateUploadInfo(TodoValueObject vo) throws Exception{

		String sql = "UPDATE todo_list set filename = ? WHERE id = ?";
		int result = 0;

		try{
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, vo.getFileName());
			statement.setInt(2, vo.getId());

			result = statement.executeUpdate();
			super.commit();
		}catch(Exception e){
			throw e;
		}
		return result;
	}
}
