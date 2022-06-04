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


@WebServlet("/cinexin/administracion/modificarcartelera")
public class CModificarCartelera extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Cartelera cartelera;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id,nombre;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/index.js");
	    	
	    }else {
	    	
	    	if(request.getParameter("id") != null) {
	    		id = request.getParameter("id");
				nombre = request.getParameter("nombre");



				request.setAttribute("id", id);
				request.setAttribute("nombre", nombre);



				request.getRequestDispatcher("/cinexin/administracion/modificarcartelera.jsp").forward(request, response);

			}else {
				response.sendRedirect("/cinexin/administracion/carteleras");
			}

	    }
		
		
		

		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		sesion = request.getSession();
		con =  (Connection)sesion.getAttribute("conexion");
		String id,nombre;
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.js");
	    	
	    }else {
	    	
			id = request.getParameter("id");
			nombre = request.getParameter("nombre");



			cartelera = new Cartelera(con);
				
				cartelera.setId(id);
				cartelera.setNombre(nombre);

				cartelera.modificarCartelera();

				response.sendRedirect("/cinexin/administracion/carteleras");
				
			
			
			
			
	    }
		
		

		
		
		
	}

}
