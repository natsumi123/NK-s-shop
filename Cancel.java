package first;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
 * Servlet implementation class Cancel
 */
@WebServlet("/Cancel")
public class Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cancel() {
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
		
		String cancelButton = request.getParameter("cancel");
		
		String cancelNM= request.getParameter("loginNM");
		
		String CancelID = request.getParameter("CancelID");
		
		String reply=request.getParameter("response");//キャンセルしますかの返信
		
		LocalDate today = LocalDate.now();

		//セッションから取得
		HttpSession session = request.getSession();
		
		session.setAttribute("cancelNM", cancelNM);
		
		session.setAttribute("CancelID", CancelID);
		
		
		String back = request.getParameter("back");
		
		
		if(cancelButton!=null) {
			
			//キャンセル処理
			 ServletContext con2 = getServletContext();
	   		 RequestDispatcher dispatcher2 = con2.getRequestDispatcher("/YesNo.jsp");//購買履歴ページ遷移
	   		 dispatcher2.forward(request, response);
			
			
		}else {//遷移後はnullになる
			
			if(back != null) {
				//キャンセル処理
				
			
				
				 ServletContext con2 = getServletContext();
		   		 RequestDispatcher dispatcher2 = con2.getRequestDispatcher("/Bought.jsp");//購買履歴ページ遷移
		   		 dispatcher2.forward(request, response);
				
				
			}
			
			if(reply!=null&&!reply.isEmpty()) {//yes/no返ってくる場合
				
				if(reply.equals("yes")) {
					
					String IDCancel=(String)request.getParameter("IDCancel");
					
					String NameCancel=(String)request.getParameter("NameCancel");
					
					if(!NameCancel.isEmpty()) {
						
						GoogleDao dao = new GoogleDao();
						
						try {
							
							DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

							if(IDCancel!=null) {
								
								Integer id = Integer.parseInt(IDCancel);
								
								List<ItemHistory> delete_before = dao.selectHis_ID(id);
								

									String Bought_date =delete_before.get(0).getDate();
									
									int  tanka_before= delete_before.get(0).getTanka();
									
									//sqlのstring日付を比較用に変換
									LocalDate forcompare = LocalDate.parse(Bought_date, fomatter);
									
									//購入日から２日超過はキャンセル不可
									Long daysdiff = ChronoUnit.DAYS.between(forcompare,today); //購入日が今日の二日前かどうか
									
									if(daysdiff > 2) {

										session.setAttribute("deleteHuka", "二日経過の商品はキャンセルできません");
				            			
					 	            	 ServletContext con = getServletContext();
							 	   		 RequestDispatcher dispatcher = con.getRequestDispatcher("/OrderHistory.jsp");//購買履歴ページ遷移
							 	   		 dispatcher.forward(request, response);	

									}else {
										
										//ポイント取消し
										if(tanka_before>=5000) {
											
											int point_del = 20*tanka_before/5000;
											
											List<GooglePoint> point=dao.selectPoint(NameCancel);
											
											int pointminus_before=point.get(0).getPoint();
											
											int pointdeleted = pointminus_before -point_del;
											
											//削除後更新
											
											dao.UPD(pointdeleted,NameCancel);
											
											
											
										}
										//5000以下はそもそも付与していない
										
										dao.DEL(id);//該当商品を削除する
										
										 List<ItemHistory> delete_after = dao.selectHis(NameCancel);
										 
										 session.setAttribute("itemhis", delete_after);
										 
										 session.setAttribute("deleteHuka", "商品はキャンセルされました");
					            			
					 	            	 ServletContext con = getServletContext();
							 	   		 RequestDispatcher dispatcher = con.getRequestDispatcher("/OrderHistory.jsp");//購買履歴ページ遷移
							 	   		 dispatcher.forward(request, response);	
												


									}
									

							}else {
								//ID空の場合防ぐ
								 ServletContext con = getServletContext();
					 	   		 RequestDispatcher dispatcher = con.getRequestDispatcher("/OrderHistory.jsp");//購買履歴ページ遷移
					 	   		 dispatcher.forward(request, response);	
								
							}

			
						} catch (ClassNotFoundException | SQLException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}

						
					}
					
					
				}else {
					//キャンセル画面でnoクリック、何もせずに遷移
					 ServletContext con = getServletContext();
		 	   		 RequestDispatcher dispatcher = con.getRequestDispatcher("/OrderHistory.jsp");//購買履歴ページ遷移
		 	   		 dispatcher.forward(request, response);	
					
				}

			}
		}
		

		
	}

}
