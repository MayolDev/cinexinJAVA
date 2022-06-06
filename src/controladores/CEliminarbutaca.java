package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Butaca;
import models.Usuario;


@WebServlet("administracion/eliminarbutaca")
public class CEliminarbutaca extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	Butaca butaca;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
			response.sendRedirect("butaca.jsp");

	    }
		
		
		
	}

	//Eliminar butaca, mediante el id.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		String codigo;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			
	    	butaca = new Butaca(con);
			
			codigo = request.getParameter("id");
			butaca.setId(codigo);
			
			int filas = butaca.eliminarButaca();

			if(filas != -1) {
				response.sendRedirect("butaca.jsp");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("/cinexin/administracion/butaca.jsp").forward(request, response);
			}
			
			
		}
	    }
		
	

}
