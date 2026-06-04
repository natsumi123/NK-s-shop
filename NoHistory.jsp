<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購買履歴</title>

</head>
<body>
<%String msg=(String)request.getAttribute("msghis"); %>
<h2><%=msg!=null ? msg:""%></h2>

</body>
</html>