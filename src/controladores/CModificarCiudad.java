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
import utils.Parseamiento;


@WebServlet("/cinexin/administracion/modificarciudad")
public class CModificarCiudad extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Ciudad ciudad;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Parseamiento parse;
		parse = new Parseamiento();
		int id_provincia;
		String nombre, cod_postal;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/index.js");
	    	
	    }else {
	    	
	    	if(request.getParameter("cod_postal") != null) {
	    		cod_postal = request.getParameter("cod_postal");
				id_provincia = parse.getInteger(request.getParameter("id_provincia"));
				nombre = request.getParameter("nombre");

				request.setAttribute("cod_postal", cod_postal);
				request.setAttribute("id_provincia", id_provincia);
				request.setAttribute("nombre", nombre);

				request.getRequestDispatcher("/cinexin/administracion/modificarciudad.jsp").forward(request, response);

			}else {
				response.sendRedirect("/cinexin/administracion/ciudades");
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
		Parseamiento parse;
		parse = new Parseamiento();
		int id_provincia;
		String nombre, cod_postal;
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.js");
	    	
	    }else {
	    	
			cod_postal = request.getParameter("cod_postal");
			nombre = request.getParameter("nombre");
			id_provincia = parse.getInteger(request.getParameter("id_provincia"));
			
			
				
			ciudad = new Ciudad(con);
				
				ciudad.setCod_postal(cod_postal);
				ciudad.setId_provincia(id_provincia);
				ciudad.setNombre(nombre);
				ciudad.modificarCiudad();

				response.sendRedirect("/cinexin/administracion/ciudades");
				
			
			
			
			
	    }
		
		

		
		
		
	}

}
