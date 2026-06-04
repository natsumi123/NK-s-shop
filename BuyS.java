package first;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class BuyS
 */
@WebServlet("/BuyS")
public class BuyS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyS() {
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
		
	
			String productID=request.getParameter("productId");
			String productName = request.getParameter("productName");
			String url=request.getParameter("url");
			int productPrice = Integer.parseInt(request.getParameter("productPrice"));
			
			
			HttpSession session= request.getSession();
		
		
			List<Product> cart=(List<Product>)session.getAttribute("cart");//箱名つける。
			
			
			if(cart==null) {
				
				cart = new ArrayList<>();
			}

			cart.add(new Product(productID,productName,productPrice,url));
			
			session.setAttribute("cart",cart);//同じにすること！！上記の同じ箱にいれる！
			
			if(cart!=null) {
				request.setAttribute("msg","追加しました");
				
				
			}
			

			
		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/Google2.jsp");
		rd.forward(request, response);

	}

}
