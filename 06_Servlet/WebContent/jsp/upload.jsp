<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>TODOタスクのアップロード画面</title>
</head>
<body>
アップロードするファイルを選択し、アップロードボタンを押下して下さい。
<form name="upload" action="../todo/upload" method="post" enctype="multipart/form-data">
	<input type="file" name="uploadfile" />
	<input type="submit" value="アップロード" />
	<input type="hidden" name="id" value="<c:out value="${id}"/>" />
</form>
●<a href="search">タスクの一覧へ戻る</a>
</body>
</html>