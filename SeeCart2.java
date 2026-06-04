package first;

import java.io.IOException;
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
 * Servlet implementation class SeeCart2
 */
@WebServlet("/SeeCart2")
public class SeeCart2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeeCart2() {
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
		
		String empty=request.getParameter("empty") ;
		
		
		String pointcheck=request.getParameter("pointcheck");
		
		int point=0;
		
		//int point_waribiki=0;
		
		String point_msg="";
		
		int sum2 =0;
		
		String sum=request.getParameter("sum");
		
		if(sum!=null) {
			
			 sum2= Integer.parseInt(sum);
 
		}
		

			HttpSession session = request.getSession();
			
			
			if(empty!=null) {
				session.invalidate();
				request.setAttribute("cartsize", 0);

			}else {
				

				List<Product> cart = (List<Product>)session.getAttribute("cart");
				
			
				/*HashMap<String, Integer> countMap = new HashMap<>();
				// listをループして、各文字列の出現回数を数える
				for (Product product : cart) {
					
					String proname=product.getName();
					
					countMap.put(proname, countMap.getOrDefault(proname, 0) + 1); 
					
				}
				
				
				// 重複している文字列とそのカウントを表示 
				for (HashMap.Entry<String, Integer> entry : countMap.entrySet()) {
					
					if (entry.getValue() > 1) {

						String productName_DB=entry.getKey();
						Integer productName_kosu=entry.getValue();
						
						

						//デフォルト１でinsertして、複数行あればupdate実行
						
					} 
					
				}
				
				
				cart.get(0).getName();*/
				
				int cartsize =0;
				
				 //point_waribiki = sum2/5000*20;
				
				if(cart!=null) {
					
					cartsize=cart.size();
				}

				if(pointcheck!=null) {
					if(sum2 >= 20) {
						
						point=5*sum2/20;
						
						point_msg="You receive "+point+" points💛";
					}else if (sum2 <20){
						point_msg= (20-sum2)+"€ to reach points!";
					}else {
					
						point_msg="Oops ! Your Cart is Empty(´；ω；`)";
					}
					
					session.setAttribute("point_msg", point_msg);

				}
				

				session.setAttribute("cart", cart);
				
				session.setAttribute("cartsize",cartsize);
				
			}
				


			ServletContext context = getServletContext();
			RequestDispatcher rd = context.getRequestDispatcher("/Bought.jsp");
			rd.forward(request, response);
			
			
			
		}
		
		
		
		
	

}
