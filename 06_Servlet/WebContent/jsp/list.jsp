<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" href="/jsp/fav.ico" />
<title>TODOタスクの一覧</title>
</head>
<body>
<table border="1">

	<tr>
		<th>タイトル</th>
		<th>タスク</th>
		<th>ユーザID</th>
		<th>ステータス</th>
	</tr>
	<c:forEach items="${list}" var="todo">
	<tr>
		<td><c:out value="${todo.title}" /></td>
		<td><c:out value="${todo.task}" /></td>
		<td><c:out value="${todo.userid}" /></td>
		<td><c:out value="${todo.status}" /></td>
	</tr>
	</c:forEach>
</table>
</body>
</html>