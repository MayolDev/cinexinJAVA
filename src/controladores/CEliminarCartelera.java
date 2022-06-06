package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.Cartelera;
import models.Usuario;


@WebServlet("administracion/eliminarcartelera")
public class CEliminarCartelera extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	Cartelera cartelera;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
			response.sendRedirect("carteleras");

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
			
	    	cartelera = new Cartelera(con);
			
			codigo = request.getParameter("id");
			cartelera.setId(codigo);;
			int filas = cartelera.eliminarCartelera();

			if(filas != -1) {
				response.sendRedirect("carteleras");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("/cinexin/administracion/carteleras").forward(request, response);
			}
			
			
		}
	    }
		
	

}
