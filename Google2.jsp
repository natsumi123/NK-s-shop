<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<%String msg=(String)request.getAttribute("msg");%>

<style>
.product-img {
width: 60px;
height: 60px;
object-fit: contain;
}
</style>


 <table border="1"> <tr>
  
   <th style="background-color:darkred; color:white;">Item Name</th>
    <th style="background-color:darkred; color:white;">Stock Amount</th>
     <th style="background-color:darkred; color:white;">Price</th> 
     <th style="background-color:darkred; color:white;">Image</th>
      <th style="background-color:darkred; color:white;">Buy</th>
      </tr>
       
       <c:forEach var="item" items="${google}"> 
       <tr>  
       <td>${item.name}</td> 
       <td>${item.zaikosu}</td> 
       <td>${item.tanka}€</td> 
       <td><img src="${item.url}" alt="${item.name}" width="100" ><br><c:if test="${item.hanbaiFlag == 1}"> Pre-Order Item </c:if></td>
      	
      
       <td>
     <form action ="BuyS" method="post">
       	<input type="hidden" name="productId" value="${item.id}">
       	<input type="hidden" name="productName" value="${item.name}">  
       	<input type="hidden" name="productPrice" value="${item.tanka}"> 
       <input type="hidden" name="url" value="${item.url}"> 
      	<input type="submit" name="buy" value="Add to Cart" style="background-color:darkgreen; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">
      
        </form>
          </td>
       </tr>
        </c:forEach> 
       
   
    </table><br>
        
        <p> <img src="cart.gif" name="SeeCart" alt="cart" width="50"></p>
       <form action="SeeCart2" method="post"> 
      <input type="submit" name="SeeCart" value="See Cart" style="background-color:darkred; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);"/>    
         </form>
    