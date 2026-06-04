package first;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GoogleThanksBuy
 */
@WebServlet("/GoogleThanksBuy")
public class GoogleThanksBuy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoogleThanksBuy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		
		String thankyou = request.getParameter("decideBuy");//確定ボタン
		
		String GoukeiKingaku=request.getParameter("GoukeiKingaku");
		
		String userlogin=request.getParameter("userlogin");
		
		String point_left=request.getParameter("point_left");
		
		
		//カード情報
		String cardnumber = request.getParameter("cardNumber");
		
		String cardHolder = request.getParameter("cardHolder");
		
		String cvv = request.getParameter("cvv");

		String expiryDate = request.getParameter("expiryDate");
		
		String address = request.getParameter("address");
		
		//戻るボタン
		String Return = request.getParameter("Return");
		
		

		Integer GoukeiKingaku_int =0;
		
		String productName_DB="";
		
		Integer productName_kosu=0;
		
		int point=0;
		
		Integer point_left_int=0;
		
		HttpSession session = request.getSession();
		
		String[] productid=(String[])session.getAttribute("productid");
		
		String[] productName=(String[])session.getAttribute("productName");
		
		String[] productPrice =(String[])session.getAttribute("productPrice");
		
		String[] url_pro=(String[])session.getAttribute("url");
		
		
		String user1 = (String)session.getAttribute("user1");
		
		String checkboxcard = request.getParameter("checkbox");
		
		boolean checked = "on".equals(checkboxcard);
		
		session.setAttribute("checkbox",checked);
		

		HashMap<String, Integer> productMap = (HashMap<String, Integer>) session.getAttribute("proMap");
		//今回ポイントの計算は手動

		if(thankyou!=null) {//確定が押された場合
			
			//カードの登録
			if(checked) {
				GoogleDao gooledao= new GoogleDao();
				
				List<CardSelectBuhin> result;
				try {
					result = gooledao.SelectCardinfo(userlogin);
					
					/*if(result.get(0).getCardNm()==null || result.get(0).getCardNm().isEmpty()) {*/
		                 //登録する
					try {
						int cardresult = gooledao.UPDCard(userlogin,cardnumber,cardHolder,expiryDate,cvv,address);
									
					 } catch (ClassNotFoundException e) {
									// TODO 自動生成された catch ブロック
					   e.printStackTrace();
					 } catch (SQLException e) {
									// TODO 自動生成された catch ブロック
					   e.printStackTrace();
					 }
		

				} catch (ClassNotFoundException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				
				
				
			}
			

			if(GoukeiKingaku!=null && userlogin!=null) {//pointありusernameあり
				
				GoukeiKingaku_int=Integer.parseInt(GoukeiKingaku);
				
					point_left_int=Integer.parseInt(point_left);//null処理済
					
					GoogleDao gooledao= new GoogleDao();
					
					if(point_left_int==0) {
						try {
							List<GooglePoint> point_before=gooledao.selectPoint(userlogin);
							
							point_left_int=point_before.get(0).getPoint();
							
						} catch (ClassNotFoundException | SQLException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						
					}

				point=5 * GoukeiKingaku_int/20 + point_left_int;//ご購入後ポイント
				

				try {
					

					int point_updated=gooledao.UPD(point, userlogin);//
					
					//DBに注文履歴を登録する
					int currentid = gooledao.getMaxid();//IDを自然増加にしたい
		     		
					Integer protanka=0;
					
		     		//重複した商品名しスキップ用のhashMapset
		     		
		     		Set<String> proNames=new HashSet<>();

							for(int i=0; i< productid.length;i++) {
									String proname=productName[i];
											protanka=Integer.parseInt(productPrice[i]);
									String prourl=url_pro[i];
									
									int prokosu=1;
									
									LocalDate today = LocalDate.now();
									
									if(!proNames.contains(proname)) {//商品名ない場合実行、ある場合スキップ（一回だけ登録する）
										try {
											int result=gooledao.insertRireki_Item(currentid+1,user1,proname, prokosu, protanka, prourl,today);
											
										} catch (ClassNotFoundException | SQLException e) {
											// TODO 自動生成された catch ブロック
											e.printStackTrace();
										}
										
										//処理済商品名をsetに追加
										proNames.add(proname);
										
										currentid++;
									}

								}
							
							//重複個数をupdateする。キャンセルのID考慮、いったん不要
							
							/*for (HashMap.Entry<String, Integer> entry : productMap.entrySet()) {
								
								if (entry.getValue() > 1) {//重複ない場合は１
									
									//System.out.println(entry.getKey() + ": " + entry.getValue()); 
									
									productName_DB=entry.getKey();
									productName_kosu=entry.getValue();
									
									try {
										int result_dupli=gooledao.UPD_dupli(productName_kosu, productName_DB);
										
									} catch (ClassNotFoundException | SQLException e) {
										// TODO 自動生成された catch ブロック
										e.printStackTrace();
									}
							
							
								}
							}*/
							

					if(point_updated>=1) {
						
						request.setAttribute("msg", "ポイント獲得おめでとうございます！");
						
					}
					
				} catch (ClassNotFoundException | SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				
				ServletContext context = getServletContext();
				RequestDispatcher rd = context.getRequestDispatcher("/ThankYOU.jsp");
				rd.forward(request, response);
				
				
			}/*else {
				
			}*/
			
			
			
		}else {
			
			//戻るボタン押下された場合
			if(Return!=null) {
				

				ServletContext context = getServletContext();
				RequestDispatcher rd = context.getRequestDispatcher("/Bought.jsp");
				rd.forward(request, response);
				
			}
	
			
		}

	}
	

}
