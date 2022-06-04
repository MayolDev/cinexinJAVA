package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Cine;
import models.Usuario;


@WebServlet("/cinexin/administracion/modificarcine")
public class CModificarCine extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Cine cine;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id, id_ciudad, id_cartelera, nombre, coordenadas;
		boolean disponible;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/index.js");
	    	
	    }else {
	    	
	    	if(request.getParameter("id") != null) {
	    		id = request.getParameter("id");
				id_ciudad = request.getParameter("id_ciudad");
				id_cartelera = request.getParameter("id_cartelera");
				nombre = request.getParameter("nombre");
				coordenadas = request.getParameter("coordenadas");
				
				disponible = Boolean.parseBoolean(request.getParameter("disponible"));


				request.setAttribute("id", id);
				request.setAttribute("id_ciudad", id_ciudad);
				request.setAttribute("id_cartelera", id_cartelera);
				request.setAttribute("nombre", nombre);
				request.setAttribute("coordenadas", coordenadas);
				request.setAttribute("disponible", disponible);




				request.getRequestDispatcher("/cinexin/administracion/modificarcine.jsp").forward(request, response);

			}else {
				response.sendRedirect("/cinexin/administracion/cines");
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
		String id, id_ciudad, id_cartelera, nombre, coordenadas;
		boolean disponible;
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.js");
	    	
	    }else {
	    	
			id = request.getParameter("id");
			id_ciudad = request.getParameter("id_ciudad");
			id_cartelera = request.getParameter("id_cartelera");
			nombre = request.getParameter("nombre");
			coordenadas = request.getParameter("coordenadas");
			
			disponible = Boolean.parseBoolean(request.getParameter("disponible"));

			cine = new Cine(con);
				
				cine.setId(id);
				cine.setId_ciudad(id_ciudad);
				cine.setId_cartelera(id_cartelera);
				cine.setNombre(nombre);
				cine.setCoordenadas(coordenadas);
				cine.setDisponible(disponible);

				cine.modificarCine();

				response.sendRedirect("/cinexin/administracion/cines");
				
			
			
			
			
	    }
		
		

		
		
		
	}

}
