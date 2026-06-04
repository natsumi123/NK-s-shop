<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New registration</title>

	<style> body { font-family: Arial, sans-serif; }
	 header { font-size: 18px; margin-bottom: 20px; font-weight: bold; } 
	 table { border-collapse: collapse; /* 枠線の隙間をなくす */ margin: auto; width: 50%; } 
	 
	 th { background-color: darkred; color: white; font-weight: bold; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2); padding: 10px; text-align: center; }
	  td { padding: 10px; text-align: center; } 
	  input[type="text"], input[type="submit"] { padding: 10px; border-radius: 5px; border: 1px solid #ccc; } 
	  input[type="submit"] { background-color: darkred; color: white; font-weight: bold; cursor: pointer; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2); } 
	  input[type="submit"]:hover { background-color: crimson; } p { color: red; /* エラーメッセージ用の色 */ text-align: center; } 
	  </style>
  
</head>
<body>

<%String login_msg=(String)request.getAttribute("Login_msg");
String msg_empty=(String)request.getAttribute("msg_empty");
String msg=(String)request.getAttribute("msg");


HttpSession session3 = request.getSession();
String decide=(String)session3.getAttribute("decide");

String[] productid=(String[])session.getAttribute("productid");
session.setAttribute("productid", productid);


String[] productName=(String[])session.getAttribute("productName");
session.setAttribute("productName", productName);


String[] url=(String[])session.getAttribute("url");
session.setAttribute("url", url);


String[] price =(String[])session.getAttribute("productPrice");
session.setAttribute("productPrice", price);


HttpSession session2 = request.getSession();
String orderhis = (String)session2.getAttribute("orderHis");

HashMap<String, Integer> productMap = (HashMap<String, Integer>) session.getAttribute("proMap");

if(productMap!=null){
session.setAttribute("productMap",productMap);

} 

String forgotpass=(String)request.getAttribute("forgotpass");


String user1=(String)session.getAttribute("user1");
session.setAttribute("user", user1);

String updated_msg = (String)request.getAttribute("updated_msg");

%>
<header>Login or Create Account</header><br>

<form action="Regs" method="post">


<table border="1">
	<tr>
		<th style="background-color:darkred; color:white; font-weight:bold; box-shadow:0 4px 6px rgba(0,0,0,2);">Username</th>
	    <th style="background-color:darkgreen; color:white; font-weight:bold; box-shadow:0 4px 6px rgba(0,0,0,2);">Password</th>
	</tr>
	
	
	<tr>
<td>
<input type="text" name="user" value=""><!--ユーザ名 -->
</td>

<td>
<input type="text" name="pass" value=""><!--パスワード-->

</td>

</tr>
</table>
<p><%=login_msg!=null ?login_msg:"" %></p>
<p><%=msg_empty!=null ?msg_empty:"" %></p>

<p><input type="submit" name="btn" value="CreateAccount" style="background-color:darkred; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);"> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="hidden" name="orderhis" value="<%=orderhis!=null ? orderhis:"" %>"> 
<input type="hidden" name="decide" value="<%=decide!=null ? decide:"" %>"> 
<input type="submit" name="login" value="Login" style="background-color:darkorange; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);"></p>

<p><%=msg!=null ? msg :""%></p>
<p><%=updated_msg!=null ? updated_msg:"" %>

</form>
<%=forgotpass!=null ?forgotpass:"" %><br><br>
<%
if(request.getAttribute("showbutton")!=null && (boolean)request.getAttribute("showbutton")){
%>

<button onclick="location.href='resetpass.jsp'" style="background-color:darkgreen; color:white; text-align:center; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">ResetPassword</button>
	
<%} %>


</body>
</html>