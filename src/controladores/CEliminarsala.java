package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.Sala;
import models.Usuario;


@WebServlet("administracion/eliminarsala")
public class CEliminarsala extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	Sala sala;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
			response.sendRedirect("sala.jsp");

	    }
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		String codigo;

		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			
	    	sala = new Sala(con);
			
			codigo = request.getParameter("id");
			sala.setId(codigo);
			int filas = sala.eliminarSala();

			if(filas != -1) {
				response.sendRedirect("sala.jsp");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("/cinexin/administracion/sala.jsp").forward(request, response);
			}
			
			
		}
	    }
		
	

}
