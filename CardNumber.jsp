<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja"> 

<head> <meta charset="UTF-8"> 
<title>Card Info</title>
</head>
 <body> <h2 style="color:navy;">Card Info & Address</h2>
 <p style="color:navy;">VISA/MASTER</p> 
 
 <%Integer cartsize = 0;
Integer price=0;
 
 String point_DBmsg="";
 
String username="";

String point_husoku="";

Integer price_used =0;

Integer price2=0;

Integer point_left=0;

 %>


<%if(session!=null){
	
	cartsize=(Integer)session.getAttribute("cartsize");
	
	price=(Integer)session.getAttribute("price");
	
	point_DBmsg=(String)session.getAttribute("point_DBmsg");
	
	username=(String)session.getAttribute("userlogin");
	
	point_husoku=(String)session.getAttribute("point_husoku");
	
	price_used=(Integer)session.getAttribute("price_used");
	
	price2=(Integer)session.getAttribute("price2");
	
	point_left=(Integer)session.getAttribute("point_left");
	
	String[] productid=(String[])session.getAttribute("productid");
	session.setAttribute("productid", productid);
	
	String[] productName=(String[])session.getAttribute("productName");
	session.setAttribute("productName", productName);
	
	String[] productPrice =(String[])session.getAttribute("productPrice");
	session.setAttribute("productPrice", productPrice);
	
	String[] url_pro=(String[])session.getAttribute("url_pro");
	session.setAttribute("url_pro", url_pro);
	
	String user1 =(String)session.getAttribute("user1");
	session.setAttribute("user1", user1);
	

	%>

<%} %>


 
   <form action="GoogleThanksBuy" method="post"> 
   
   <label for="address" style="color:navy;">Your Address:</label>
	<input type="text" id="address" name="address" value="<c:out value='${empty address ? "": address}'/>" <br><br> 
	
	  <label for="cardNumber" style="color:navy;">Card Number:</label>
	   <input type="text" id="cardNumber" name="cardNumber" value="<c:out value='${empty cardnumberStored ? "": cardnumberStored}'/>" pattern="\d{4} \d{4} \d{4} \d{4}" placeholder="1234 5678 9012 3456" required><br><br> 
	   <label for="cardHolder" style="color:navy;">
	   Card Holder's Name:</label>
	    <input type="text" id="cardHolder" name="cardHolder"  value="<c:out value='${empty cardnm ? "": cardnm}'/>" style="color:navy;" required><br><br> 
	   <label for="expiryDate" style="color:navy;">expired date:</label> <input type="month" id="expiryDate" name="expiryDate" value="<c:out value='${empty expiredate ? "": expiredate}'/>" required><br><br> 
	   <label for="cvv" style="color:navy;">CVV:</label> <input type="text" id="cvv" name="cvv"value="<c:out value='${empty cvv ? "": cvv}'/>" pattern="\d{3}" placeholder="123" required><br><br> 
	   <input type="hidden" name="userlogin" value="<%=username!=null ? username:""%>">
	   <input type="hidden" name="GoukeiKingaku" value="<%=price2!=null ? price2:0%>">
	  <input type="hidden" name="point_left" value="<%=point_left!=null ? point_left:0%>">
	  <input type="hidden" name="GoukeiKingaku2" value="<%=price2%>">
	   <input type="submit" name="decideBuy" value="Confirm" style="background-color:darkred; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);"> 
	   <input type="submit" name="Return" value="Back" style="background-color:darkgreen; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);"> 
   		<p>★Save This Card for Next Time<input type="checkbox" name="checkbox" value="on"></p>

   </form> 
   
   <form action="Regs" method="post">   
  
   <input type="submit" name="useNew" value="Use New Card" style="background-color:darkred; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);"> 
   </form>
   


  <p style="color:darkgreen;"><strong>Total：<%=cartsize!=null ? cartsize:0 %>&nbsp;Items、Total Amount&nbsp;<%=price%>&nbsp;€</strong></p>
 
  <form action="GoogleLogin" method="get">
  <input type="hidden" name="userlogin" value="<%=username%>">
  <input type="submit" value="Check Current Point" style="background-color:darkgreen; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">
  <br><br><%=point_DBmsg!=null ? point_DBmsg:""%>
    <!-- ポイント利用 -->
  <p> Use Point <input type="text" name="Usepoint"> Point
	<input type="hidden" name="GoukeiKingaku" value="<%=price%>">
	<input type="hidden" name="GoukeiKingaku2" value="<%=price2%>">
 	<input type="submit" name="decidePoint" value="Use" style="background-color:darkred; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);"></p>
  
  </form>
  
  <p><%=point_husoku!=null ? point_husoku:""%></p>

   </body>
</html>