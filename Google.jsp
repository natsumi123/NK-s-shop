<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- searchPage.jsp --> 
<!DOCTYPE html>
 <html> <head> <meta charset="UTF-8"> 

 <title>検索画面</title> 
 <%String msg=(String)request.getAttribute("msg");%>
 <style> /* ページ全体を中央に配置 */
 
 
  body, html 
  { height: 100%; margin: 0; 
  display: flex; align-items:
   center; justify-content: 
   center; font-family: 
   Arial, sans-serif; 
   background-color: #f2f2f2; } 
   /* コンテナのスタイル */ 
   .search-container { 
   text-align: center; }
    /* 検索バーのスタイル */
     .search-bar { 
     width: 500px; 
     height: 40px;
    padding: 10px; 
     font-size: 16px;
      border: 1px solid #ccc; 
      border-radius: 20px; 
      outline: none; } 
      /* 検索ボタンのスタイル */ 
      .search-button { 
      margin-top: 20px; 
      padding: 10px 20px; 
      font-size: 16px;
       color: white;
        background-color: #8B0000; 
        /* Google風の青色 */
         border: none; 
         border-radius: 5px; 
         cursor: pointer; } 
         .search-button:hover
          { background-color: #357ae8; }
          
          /* マラキーターテキストのスタイル */ 
.marquee { width: 100%; overflow: hidden; white-space: nowrap; box-sizing: border-box; position:fixed;top:20px;left:0;font-size:50px;} 


.marquee span { 
color:red;
font-weight:bold;
font-family: 'Arial Rounded MT Bold', 'ヒラギノ角ゴ Pro W3', 'Hiragino Kaku Gothic Pro', 'メイリオ', sans-serif; /* 丸ゴシック風のフォント */
display: inline-block; 
padding-left: 100%;
 animation: marquee 18s linear infinite; }
 @keyframes marquee { 
 from {
  transform: translateX(100%); } to { 
  transform: translateX(-100%); } 
  } 
          
           </style> 
           </head> 
           <body> 
           <div class="marquee"> <span>On Christmas Sell!&#x1f384;&#x1f385; Don't miss it!&#x1f381;</span> </div>
           <div class="search-container"> 
           <!-- ロゴ --> <h1 style="color: #FF0000; font-size: 64px; margin: 0;">N K's SHOP
           </h1> <!-- 検索バー -->
            <form action="GoogleS" method="post">
             <input type="text" 
             name="query"
              class="search-bar"
               placeholder="Search Items Here">
                <button type="submit" class="search-button">Search</button> 
                </form>
                 <div style="text-align:center; margin-top:10px; color:red;"><%=msg!=null ? msg:""%></div>
                 </div> 
                 </body>
                  </html>
