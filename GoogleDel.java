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
 * Servlet implementation class GoogleDel
 */
@WebServlet("/GoogleDel")
public class GoogleDel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoogleDel() {
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
	@SuppressWarnings("unlikely-arg-type")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		
		String id=request.getParameter("productid");
		
		
		
		String delete =request.getParameter("delete");
		
		HttpSession session = request.getSession();

		
		List<Product> cart = (List<Product>)session.getAttribute("cart");
		
		if(delete!=null) {
			
			for(int i =0; i<cart.size();i++) {//cartのどれかがあたるようにする
				
				
				if(id.equals(cart.get(i).getId())) {
					
					cart.remove(i);
					
					break;
					
					
				}
				
				
			}
			
			session.setAttribute("cart",cart);//同じにすること！！上記の同じ箱にいれる！
			
			session.setAttribute("cartsize", cart.size());

		}
		

		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/Bought.jsp");
		rd.forward(request, response);
		
	}

}

