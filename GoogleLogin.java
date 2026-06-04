package first;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GoogleLogin
 */
@WebServlet("/GoogleLogin")
public class GoogleLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoogleLogin() {
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
		HttpSession session = request.getSession();
		
		session.getAttribute("cart");
		
		String point_DBmsg = "";
		
		String user=(String)session.getAttribute("userlogin");
		
		String decidePoint=request.getParameter("decidePoint");//利用ボタン押下
		
		String Usepoint=request.getParameter("Usepoint");//いくら使用するか
		
		String GoukeiKingaku= request.getParameter("GoukeiKingaku");
		
		String GoukeiKingaku2= request.getParameter("GoukeiKingaku2");
		
		Integer GoukeiKingaku2_int =0;
		
		
		String point_usedmsg="";

		if(GoukeiKingaku2!=null) {
			
			GoukeiKingaku2_int=Integer.parseInt(GoukeiKingaku2);
			
		}
			

		
		Integer GoukeiKingaku_int =0;
		
		Integer Usepoint_int=0;
		
		
		
		int point=0;
		
		
		if(user!=null) {
			//DBからポイント取得
			GoogleDao gooledao= new GoogleDao();
			try {
				List<GooglePoint> google=gooledao.selectPoint(user);
				
				 point =google.get(0).getPoint();
				
				point_DBmsg="You Have "+point+" Points (・∀・) Now, Use it?";
				
				session.setAttribute("point_DBmsg", point_DBmsg);
				
				
				//ポイント利用する場合、金額から引く
				
				if(GoukeiKingaku!=null) {
					
					GoukeiKingaku_int=Integer.parseInt(GoukeiKingaku);
					

					if(decidePoint!=null) {
						
						if(Usepoint!=null) {
							Usepoint_int=Integer.parseInt(Usepoint);
							
							if(Usepoint_int<=point) {
								//ポイント使用後金額算出
								GoukeiKingaku_int=GoukeiKingaku2_int-Usepoint_int;
								
								session.setAttribute("price", GoukeiKingaku_int);
								
								//DBにポイント残額計算
								
								int point_zangaku = point-Usepoint_int;
								
								point_DBmsg="現在ご利用可能ポイントは"+point_zangaku+"ポイントです";
								
								session.setAttribute("point_DBmsg", point_DBmsg);
								
								session.setAttribute("point_left", point_zangaku);
								
										
							}else {
								
								session.setAttribute("point_husoku", "ポイントが不足しています");
							}

						}
						
					}
					
					
				}
				List<CardSelectBuhin> result;
				
				result = gooledao.SelectCardinfo(user);
				
				//すでにある場合、引っ張ってくる
				
				if(result!=null) {
					
					if(result.get(0).getCardNumber()!=null && !result.get(0).getCardNumber().isEmpty()) {
												
						String cardnumberStored = result.get(0).getCardNumber();
										
						
						request.setAttribute("cardnumberStored", cardnumberStored);

					ServletContext context = getServletContext();
					RequestDispatcher rd = context.getRequestDispatcher("/CardNumber.jsp");
					rd.forward(request, response);
					
					}
			
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
		}
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("utf-8");
		String decide=request.getParameter("decide");//ご購入へ
		request.setAttribute("msg", "");//nullエラー発生防ぐ
		String sumprice=request.getParameter("sumprice2");//boughtjsp

		HttpSession session = request.getSession();
		
		session.getAttribute("cart");
		
		String orderHis=request.getParameter("orderHis");
		
		
		String Buymore = request.getParameter("Buymore");
		
		//購入情報
		String[] productid=request.getParameterValues("productid");
		
		String[] productName =request.getParameterValues("productName");
		
		String[] productPrice=request.getParameterValues("productPrice");
		
		String[] url=request.getParameterValues("url");
		
		String productName_dupli="";//重複数える用
		
		int product_kosu_dupli=0;
		
		
		// 文字列の出現回数を数える、複数購入商品を後でupdateして数量更新ｓ
				HashMap<String, Integer> countMap = new HashMap<>();
				// listをループして、各文字列の出現回数を数える
				
				if(productName!=null) {
					for (String str : productName) {
						
						countMap.put(str, countMap.getOrDefault(str, 0) + 1); 
						
					} 
				}
				

		
		if(orderHis!=null) {
			
			//注文履歴クリックした場合、参照用処理
			

			HttpSession session2 = request.getSession();
			session2.setAttribute("orderHis", orderHis);
			

			if(sumprice!=null) {//合計金額がnullではない（買い物ある）
				//intにする
				Integer sumprice_int=Integer.parseInt(sumprice);
				
				if(sumprice_int!=0) {
					
					session2.setAttribute("sumprice2", sumprice);//合計金額も履歴に出したい
				}else {
					session2.setAttribute("sumprice2", 0);
				}
			
			}
			

			ServletContext context = getServletContext();
			RequestDispatcher rd = context.getRequestDispatcher("/reg.jsp");
			rd.forward(request, response);

			return;
			
			
		}else if(decide!=null) {//ご購入へクリックの場合
			
			if(sumprice!=null) {//合計金額がnullではない（買い物ある）
				//intにする
				Integer sumprice_int=Integer.parseInt(sumprice);
				
				if(sumprice_int!=0) {
					
					HashMap<String,Integer> proMap=new HashMap<>();
					// 重複している商品名とそのカウントを表示 
					for (Entry<String, Integer> entry : countMap.entrySet()) {
						

						if (entry.getValue() > 1) {
							
							//System.out.println(entry.getKey() + ": " + entry.getValue()); 
							
							productName_dupli=entry.getKey();
							product_kosu_dupli=entry.getValue();
							
							//session.setAttribute("productName_dupli",productName_dupli);
							//session.setAttribute("product_kosu_dupli",product_kosu_dupli);
							
							
							proMap.put(productName_dupli, product_kosu_dupli);
							

							//デフォルト１でinsertして、複数行あればupdate実行
							
						} 
						
					}	
					
					session.setAttribute("decide", decide);
					
					session.setAttribute("proMap", proMap);//ここでmapをセットする

					session.setAttribute("url", url);

					session.setAttribute("productid", productid);
					
					session.setAttribute("productName", productName);
					
					session.setAttribute("productPrice", productPrice);

					session.setAttribute("price", sumprice_int);//合計金額
					
					session.setAttribute("price2", sumprice_int);//どんどん減らないようにデフォルト金額をセッションにキープ
					
					request.setAttribute("Login_msg", "Register to Get 100 Point Bouns！");
					
					ServletContext context = getServletContext();
					RequestDispatcher rd = context.getRequestDispatcher("/reg.jsp");
					rd.forward(request, response);
					
					return;
					
				}else {
				
					
					session.setAttribute("empty_msg", "The Cart is Empty,Select Product");
					
					ServletContext context = getServletContext();
					RequestDispatcher rd = context.getRequestDispatcher("/Bought.jsp");
					rd.forward(request, response);
					return;
					
				}
				
			}

		}else if(Buymore!=null){
			ServletContext context = getServletContext();
			RequestDispatcher rd = context.getRequestDispatcher("/Google.jsp");
			rd.forward(request, response);
			
			return;
		}

	}

}
