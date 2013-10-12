<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ログイン画面</title>
</head>
<body>
<form method="post" action="${fn:escapeXml('j_security_check')}">
	<table border="1">
		<tr>
			<td>ユーザID</td>
			<td><input type="text" name="j_username" /></td>
		</tr>
		<tr>
			<td>パスワード</td>
			<td><input type="password" name="j_password" /></td>
		</tr>
	</table>
	id:user01<br/>
	pw:password1
	<br />
	<input type="submit" value="ログイン" />
	<input type="reset" value="リセット" name="reset" />
</form>
</body>
</html>