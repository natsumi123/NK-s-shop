<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

 <meta charset="UTF-8"> 
 <title>キャンセル確認</title>
  <style>
   body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; } 
   
   .container { border: 2px solid #000; display: inline-block; padding: 20px; }
   
    .button { margin: 20px; padding: 10px 20px; font-size: 16px; cursor: pointer; }
    
 </style> 
     </head> 
<body>
<%String Id=(String)session.getAttribute("CancelID");

String name=(String)session.getAttribute("cancelNM");

%>   

      <div class="container">
       <h2>キャンセルでよろしいですか？</h2>
        <!-- サーブレットに送信 ポイントはクリア-->
         <form action="Cancel" method="post"> 
	         <button class="button" type="submit" name="response" value="yes" style="background-color:red; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">はい</button> 
	         <button class="button" type="submit" name="response" value="no" style="background-color:navy; color:white; font-weight:bold; border-radius:10px; box-shadow:0 4px 6px rgba(0,0,0,2);">いいえ</button> 
         	<input type="hidden" name="IDCancel" value="<%=Id%>">
         	<input type="hidden" name="NameCancel" value="<%=name%>">
         </form>
       </div> 
    
</body> 

</html>