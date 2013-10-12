package sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sample.entity.Todo;

public class TodoDao extends MySQLDao {

	public List<Todo> select() throws Exception {
		String sql = "SELECT title, task, limitdate, lastupdate,userid, status"
				+ " FROM todo_list WHERE userid like CONCAT('%', ? ,'%')";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, "USER");

		ResultSet rs = statement.executeQuery();

		List<Todo> resultList = new ArrayList<>();

		while (rs.next()) {

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

	public void insert(Connection connection) throws Exception {
		String sql = "INSERT INTO `sample`.`todo_list` ( `title`,`task`, `limitdate`, `lastupdate`, `userid`, `status` "
				+ ") VALUES ( ? , ? , ? , now() , ? , ? );";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "講習会3");
			statement.setString(2, "講習会3のスライド作成、配布ソースコードの準備");
			statement.setString(3, "2013-05-09");
			statement.setString(4, "USER99");
			statement.setString(5, "0");

			int count = statement.executeUpdate();

			System.out.println("1つ目の追加" + count);

			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}

	}
}
