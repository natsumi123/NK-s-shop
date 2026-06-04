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

/**サイト起動用
 * Servlet implementation class GoogleS
 */
@WebServlet("/GoogleS")
public class GoogleS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoogleS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		ServletContext context = getServletContext(); 
		
		RequestDispatcher rd = context.getRequestDispatcher("/Google.jsp");
		
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		request.setCharacterEncoding("UTF-8");
		
		String query = request.getParameter("query");
		String jsp="";
		
		String msg="";
		
		
		
		if(!query.isEmpty()) {
			try{
				
				GoogleDao gooledao= new GoogleDao();
				List<GoogleB> google=gooledao.selectGoogle(query);
				
				if(google.size()==0) {
					msg="No Result";
					
					request.setAttribute("msg", msg);
					jsp="/Google.jsp";
				}else {
					request.setAttribute("google", google);
					jsp="/Google2.jsp";
				}
				

			}catch(Exception ex) {
				
				ex.printStackTrace();
				
			}
			
		}else {
			
			msg="Search Items with key Word";
			
			request.setAttribute("msg", msg);
			jsp="/Google.jsp";
		}
		
		
		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher(jsp);
		rd.forward(request, response);
	}

}
