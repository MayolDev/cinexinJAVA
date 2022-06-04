package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.Pelicula;
import models.Usuario;



@WebServlet("administracion/eliminarpelicula")
public class CEliminarpelicula extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	Pelicula pelicula;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
			response.sendRedirect("peliculas");

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
			
	    	pelicula = new Pelicula(con);
			
			codigo = request.getParameter("id");
			pelicula.setId(codigo);;
			int filas = pelicula.eliminarPelicula();

			if(filas != -1) {
				response.sendRedirect("peliculas");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("peliculas").forward(request, response);
			}
			
			
		}
	    }
		
	

}
