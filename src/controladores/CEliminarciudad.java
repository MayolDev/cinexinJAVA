package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.Ciudad;
import models.Usuario;


@WebServlet("administracion/eliminarciudad")
public class CEliminarciudad extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	Ciudad ciudad;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
			response.sendRedirect("ciudades");

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
			
	    	ciudad = new Ciudad(con);
			
			codigo = request.getParameter("cod_postal");
			ciudad.setCod_postal(codigo);
			int filas = ciudad.eliminarCiudad();

			if(filas != -1) {
				response.sendRedirect("ciudades");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("/cinexin/administracion/ciudades").forward(request, response);
			}
			
			
		}
	    }
		
	

}
