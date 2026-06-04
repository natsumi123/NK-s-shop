<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html> 
<html>
 <head>
  <title>Cart Items</title>

   </head>
   <body>
    <h1>Item In Cart<img src="thankyou2.gif" alt="thankyou" width="80px"><img src="hear5.gif" alt="heart" width="60px"></h1>
<%Integer cartsize = 0;

String empty_msg="";
%>

<%String showpoint = "";%>

<%if(session!=null){
	
	cartsize=(Integer)session.getAttribute("cartsize");

	showpoint =(String)session.getAttribute("point_msg");
	
	empty_msg=(String)session.getAttribute("empty_msg");
	
	
	%>

<%} %>
   
   <table  border="1">
     <c:set var="total" value="0"/>
   <tr>
	    <th style="background-color:darkred; color:white;">Items</th>
	     <th style="background-color:darkred; color:white;">Image</th>
		<th style="background-color:darkred; color:white;">Price</th>   
	   <th style="background-color:darkred; color:white;">Delete</th>   
	   </tr>
  
   		<c:forEach var = "cartItem" items="${cart}">
   		
   		<tr>
   			<td style="display:none;">${cartItem.id}</td>
   			<td>${cartItem.name}</td>
   			<td><img src="${cartItem.url}" alt="${cartItem.url}" width="100"></td>
   			<td>${cartItem.price}€ </td>
			
   			<td>
   			
   			 <form action="GoogleDel" method="post">
   			 
   			<input type="hidden" name="productid" value="${cartItem.id}">
       		<input type="hidden" name="productName" value="${cartItem.name}">  
       		<input type="hidden" name="productPrice" value="${cartItem.price}"> 
       		<input type="hidden" name="url" value="${cartItem.url}"> 
   			<input type="submit" name="delete" value="Delete Item" style="background-color:darkgreen; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">
   			</form>

   			</td>
   			
   		</tr>
		<c:set var="total" value="${total+cartItem.price}"/>
		
		</c:forEach>
		
      <tr>
      </table>
       
      <p>Total:
	<%=cartsize!=null ? cartsize:0%>
      Items、Total Amount:${total}€</p>
      
      <form action ="SeeCart2" method="post">
       <input type="hidden" name="sum" value="${total}">

      <p><input type="submit" name="pointcheck" value="Check Point" style="background-color:darkred; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);"> 
      
     <%=showpoint!=null ? showpoint:""%></p>
     
       </form>
       
<form action="GoogleLogin" method="post">
       
       <c:forEach var = "cartItem2" items="${cart}">

				<input type="hidden" name="productid" value="${cartItem2.id}">
	       		<input type="hidden" name="productName" value="${cartItem2.name}">  
	       		<input type="hidden" name="productPrice" value="${cartItem2.price}"> 
	       		<input type="hidden" name="url" value="${cartItem2.url}"> 

			<c:set var="total2" value="${total+cartItem2.price}"/>
		
		</c:forEach>
       

	       <input type="submit" name="decide" value="Purchase" style="background-color:darkorange; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">
	       <input type="hidden" name="sumprice2" value="${total}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

	     <input type="submit" name="orderHis" value="Order History" style="margin-right:40px;background-color:deepskyblue; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">
	     
	     &nbsp;

	     <input type="submit" name="Buymore" value="Add Item" style="margin-right:40px;background-color:navy; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">
	     
	     
       </form>
       <p><%=empty_msg !=null ? empty_msg:""%></p>
           
      <form action ="SeeCart2" method="post">
      <br><input type="submit" name="empty" value="Empty the Cart" style="background-color:darkred; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">
     
      </form>
     
      <p> <a href="Google.jsp">Back</a></p>
       </body> 
       </html>