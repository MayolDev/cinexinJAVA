package controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/desconexion")
public class CDesconexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		sesion.invalidate();
		response.sendRedirect("/cinexin/");
		
	}



}
