<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thankyou</title>
</head>
<body>
<%String point_got=(String)request.getAttribute("msg"); %>

<p style="color:darkred;"><%=point_got!=null ? point_got:"" %></p>

<h2  style="color:darkred;">

<strong>Thank you！&#x1f618;</strong></h2>

</body>
</html>