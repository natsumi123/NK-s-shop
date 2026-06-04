<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order History</title>
</head>
<body>
<%String login=(String)session.getAttribute("userlogin"); %>

<%String huka = (String)session.getAttribute("deleteHuka");%>

<h2><%=login!=null ?login:"" %>'s Order History&#x1f447;</h2>


  <table  border="1">
     <c:set var="total" value="0"/>
   <tr>
    	<th style="background-color:darkred; color:white;">Item Name</th>
	    <th style="background-color:darkred; color:white;">Numbers</th>
	     <th style="background-color:darkred; color:white;">Amount</th>
		<th style="background-color:darkred; color:white;">Image of Item</th>   
	   <th style="background-color:darkred; color:white;">Order Date</th> 
	   <th style="background-color:darkred; color:white;">Cancel</th>     
	   </tr>
  
   		<c:forEach var = "cartItem" items="${itemhis}">
   		
   		<tr>
   			<td style="display:none;">${cartItem.id}</td>
   			<td>${cartItem.productname}</td>
   			<td>${cartItem.kosu}</td>
   			<td>${cartItem.tanka}円</td>
   			<td><img src="${cartItem.url}" alt="${cartItem.url}" width="100"></td>
   			<td>${cartItem.date}</td>
   			
			<td>
		 <form action="Cancel" method="post">
		 	<input type="hidden" name="CancelID" value="${cartItem.id}">
			<input type="submit" name="cancel" value="Cancel" style="background-color:navy; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">
			<input type="hidden" name="loginNM" value="<%=login!=null ?login:"" %>">
		</form>
		</td>
		</tr>
		
</c:forEach>
</table>




<p style="color:red;"><%=huka!=null ? huka:"" %></p>
<form action="Cancel" method="post">
	<p><input type="submit" name="back" value="←BACK" style="background-color:navy; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);"></p>
</form>
</body>
</html>