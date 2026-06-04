package first;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Regs
 */
@WebServlet("/Regs")
public class Regs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	//DB接続情報

	
	  final String url  ="jdbc:mysql://localhost:3306/natsumi";
      final String user = "root";
      final String password = "Bakabaka1234K";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Regs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		request.setAttribute("msg", "");
		ServletContext con = getServletContext();
		RequestDispatcher dispatcher = con.getRequestDispatcher("/reg.jsp");
		dispatcher.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		
		String user1 = request.getParameter("user");
		
		String password1 = request.getParameter("pass");
		
		String toroku= request.getParameter("btn");//登録
		
		String login= request.getParameter("login");//ログイン
		
		String decidebuy = (String)request.getParameter("decide");
		
		String useNewcard = (String)request.getParameter("useNew");//新規カード
		

		//セッションから取得
		HttpSession session = request.getSession();

		String orderhis=(String)request.getParameter("orderhis");//注文履歴
		
		List<Product> cart = (List<Product>)session.getAttribute("cart");
		
		//session.setAttribute("user1", user1);
		//session.setAttribute("userlogin", user1);//必ず転送前に設定する！！
		
		//response.sendRedirect("GoogleLogin");
		
		//購入予定のID、商品名、プライス、urlを取得する
		
		String[] productid=(String[])session.getAttribute("productid");
		
		String[] productName=(String[])session.getAttribute("productName");
		
		String[] productPrice =(String[])session.getAttribute("productPrice");
		
		String[] url_pro=(String[])session.getAttribute("url");
		
		//String sumprice2=(String)session.getAttribute("sumprice2");
		
		HashMap<String, Integer> productMap = (HashMap<String, Integer>) session.getAttribute("proMap");//loginから取得する


		int cartsize =0;
		
		List<CardSelectBuhin> result;
		

		if(cart!=null) {
			
			cartsize=cart.size();

			session.setAttribute("cartsize",cartsize);
			
			//session.setAttribute("price", price);
		}else {
			
			session.setAttribute("cartsize",0);
		}

		
		//ユーザIDパスワードnullチェック
		
		if((user1==null||user1.isEmpty()||password1==null||password1.isEmpty()) && useNewcard==null) {
			
			request.setAttribute("msg_empty", "User or Password is Empty");

         		ServletContext con = getServletContext();
	   			RequestDispatcher dispatcher = con.getRequestDispatcher("/reg.jsp");
	   			dispatcher.forward(request, response);
			
		}else if(useNewcard!=null) {
			
			//新規カード使用画面
				ServletContext con = getServletContext();
	   			RequestDispatcher dispatcher = con.getRequestDispatcher("/CardNumber.jsp");
	   			dispatcher.forward(request, response);
						
			
		}

	else {//両方ある場合
			
			
			try {
				//ドライバー
					Class.forName("com.mysql.jdbc.Driver");//絶対必要！！！！！！
				
				try(Connection conn = 
		                DriverManager.getConnection(url, user, password)){

		            conn.setAutoCommit(false);
		            String sql= "insert into natsumi.reg (username, password,point) value(?,?,?);";
		            String sql2="Select * from natsumi.reg where username=? and password=?;";
		            String checkuser="Select * from natsumi.reg where username = ?";
		            
		            //登録の場合
		            
		            if(toroku!=null) {
		            	//登録の場合

		            		//登録実行
		           	     try(PreparedStatement ps = conn.prepareStatement(checkuser)){//まずはユーザいるかどうか探す
		           	    	ResultSet rs=null;
		     	              
		           	    	 ps.setString(1,user1);	
		           	    	 //ps.setString(2,password1);

		     	             rs=ps.executeQuery();
		           	    	 
		           	    	 if(rs.next()==false) {//ない場合、新規登録

		           	    		 try(PreparedStatement ps2 = conn.prepareStatement(sql)){
			           	    		ps2.setString(1,user1);
			           	    		ps2.setString(2,password1);
			           	    		ps2.setInt(3, 100);//新規登録者には100ポイント付与
			      	                
			      	                ps2.executeUpdate();//
			      	                
			      	                conn.commit();
		      	                
		           	    		 } catch (Exception e) {
		          	                conn.rollback();
		         	                System.out.println("rollback");
		         	                throw e;
		         	             }finally {
		         	            	System.out.println("Registered!");
		         	            	
		         	            	
		         	            	request.setAttribute("msg", "Registered ! Login Here");
		    	 	            	
		         	            	//session.setAttribute("userlogin", user1);
		    	 	            	ServletContext con = getServletContext();
		    		 	   			RequestDispatcher dispatcher = con.getRequestDispatcher("/reg.jsp");
		    		 	   			dispatcher.forward(request, response);
		         	             }
				 
		           	    		 
		           	    	 }else {
		           	    		
		           	    		request.setAttribute("msg", "User Exist,Try Login");
		           	    		
		           	    		request.setAttribute("forgotpass","Wrong PassWord.Forgot Password？");
			 	            	
		           	    		request.setAttribute("showbutton", true);//パスワードリセット用ボタン
		           	    	
			 	            	ServletContext con = getServletContext();
				 	   			RequestDispatcher dispatcher = con.getRequestDispatcher("/reg.jsp");
				 	   			dispatcher.forward(request, response);
		           	    		 
		           	    	 }
			
		           	     }  

		            
		            }else {
		            	
		            	 //ログインの場合
		            	ResultSet rs1=null;
		            	try(PreparedStatement check = conn.prepareStatement(checkuser)){
		            		
		            		check.setString(1,user1);
		            		
		            		rs1=check.executeQuery();
		            		
		            		if(rs1.next()) {//IDだけまず見つかった場合
		            			
		            			ResultSet rs=null;
				            	
				            	try(PreparedStatement sel = conn.prepareStatement(sql2)){//ID、パスワード両方チェック
				 	               
				 	            	sel.setString(1,user1);
				 	                sel.setString(2,password1);
				 	              
				 	                
				 	                rs=sel.executeQuery();
				 	                
					 	             if(rs.next()) {//IDもパスワードもDBにある
					 	            	 

					 	            	session.setAttribute("userlogin", user1);
					 	            	
					 	            	if(decidebuy!="") {
					 	            		
					 	            		orderhis="";//履歴ボタンのパラメータをクリアする
					 	            	}
					 	            	
					 	            	if(orderhis!="") {//注文履歴ボタン押下

					 	            		decidebuy="";//購入ボタンのパラメータクリア
					 	            		
					 	            		
					 	            		//注文履歴確認の場合
					 	            		//DBにある注文履歴を取得して、jspにセット
					 	            		GoogleDao gooledao= new GoogleDao();
					 	            		
					 	            		List<ItemHistory> itemhis= gooledao.selectHis(user1);
					 	            		
					 	            		if(itemhis.size()==0) {
					 	            			
					 	            			request.setAttribute("msghis", "No Order History");
					 	            			
					 	            			ServletContext con = getServletContext();
								 	   			RequestDispatcher dispatcher = con.getRequestDispatcher("/NoHistory.jsp");//購買履歴ないページ
								 	   			dispatcher.forward(request, response);
					 	            			
					 	            			
					 	            		}else {
					 	            			session.setAttribute("itemhis", itemhis);
					 	            			session.setAttribute("userlogin", user1);
						 	            		
						 	            		ServletContext con = getServletContext();
								 	   			RequestDispatcher dispatcher = con.getRequestDispatcher("/OrderHistory.jsp");//購買履歴ページ遷移
								 	   			dispatcher.forward(request, response);
					 	            		}

					 	            	 }else {
					 	            		//ご購入ボタン押下or他の場合、注文履歴としてDBに登録するため、ここでまずsessionに入れる

					 							//sessionにセットする、確定押したら正式登録する
					 							
					 							session.setAttribute("productid", productid);
					 							
					 							session.setAttribute("productName", productName);
					 							
					 							session.setAttribute("productPrice", productPrice);
					 							session.setAttribute("url_pro", url_pro);
					 							session.setAttribute("user1", user1);
					 							

					 							
					 							//すでにある場合、引っ張ってくる
					 							GoogleDao gooledao= new GoogleDao();
					 							
					 							result = gooledao.SelectCardinfo(user1);
					 							
					 							//すでにある場合、引っ張ってくる
					 							
					 							//else if(useNewcard != null) {//新規カード登録する場合
						 	            		 
						 	            		 //何もしない
						 	            		 

					 							if(result!=null) {
					 								
					 								if(result.get(0).getCardNumber()!=null && !result.get(0).getCardNumber().isEmpty()) {
					 														 									
					 									String cardnumberStored = result.get(0).getCardNumber();
					 									
					 									String cardnm = result.get(0).getCardNm();
					 									
					 									String expiredate = result.get(0).getExpiredate();
					 									
					 									String cvv = result.get(0).getCvv();
					 									
					 									String address = result.get(0).getAddress();
					 									
					 									request.setAttribute("cardnumberStored", cardnumberStored);
					 									
					 									request.setAttribute("cardnm", cardnm);
					 									
					 									request.setAttribute("expiredate",  expiredate);
					 									
					 									request.setAttribute("cvv",  cvv);
					 									
					 									request.setAttribute("address",address);

					 								}
					 						
					 							}	
					 							
					 							
					 							ServletContext con = getServletContext();
								 	   			RequestDispatcher dispatcher = con.getRequestDispatcher("/CardNumber.jsp");
								 	   			dispatcher.forward(request, response);
					 							
					 	            	}
		            			
					 	             }else {//IDあるが該当パスワードない、正しくない
					 	            	 
					 	            	session.setAttribute("user1", user1);
					 	            	 
					 	            	request.setAttribute("forgotpass","Wrong PassWord, Forgot PassWord？");
					 	            	
				           	    		request.setAttribute("showbutton", true);//パスワードリセット用ボタン
				           	    	
					 	            	ServletContext con = getServletContext();
						 	   			RequestDispatcher dispatcher = con.getRequestDispatcher("/reg.jsp");
						 	   			dispatcher.forward(request, response);
					 	            	 
					 	             }
		            		
				            	}

		            		}else {//IDすらない場合
			 	            	 
			 	            	
			 	            	request.setAttribute("msg", "The User Not Exist,Please Register");//ログイン失敗
			 	            	
			 	            	
			 	            	ServletContext con = getServletContext();
				 	   			RequestDispatcher dispatcher = con.getRequestDispatcher("/reg.jsp");
				 	   			dispatcher.forward(request, response);
			 	   			
			 	             }
			 	                rs1.close();
		 	                
		 	                //conn.commit();
		 	                
		 	            } catch (Exception e) {
		 	                conn.rollback();
		 	                System.out.println("rollback");
		 	                throw e;
		 	            }
		            	
		            }
		            

		        	} 
				
				
				}catch (Exception e) {
		            e.printStackTrace();
		        }finally {
		            System.out.println("処理が完了しました");
		        }
				

			}
			
			
		}
		


}
