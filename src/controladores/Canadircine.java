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
import utils.Comprobaciones;


/**
 * Servlet implementation class anadirprovincia
 */
@WebServlet("/anadircine")
public class Canadircine extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Cine cine;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    sesion = request.getSession();
	    
	    if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
	    	
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);
	    }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		sesion = request.getSession();
		con = (Connection)sesion.getAttribute("conexion");
		cine = new Cine(con);

		String id, id_ciudad, id_cartelera, nombre, coordenadas; 
		Boolean disponible;
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			id = request.getParameter("id");
			id_ciudad = request.getParameter("id_ciudad");
			id_cartelera = request.getParameter("id_cartelera");
			coordenadas = request.getParameter("coordenadas");
	    	
			nombre = request.getParameter("nombre");
			
			
			if(nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 30) || id.contentEquals("") || !comprobacion.checkStringBetween(id, 1, 5)|| id_cartelera.contentEquals("") || !comprobacion.checkStringBetween(id_cartelera, 1, 10)|| id_ciudad.contentEquals("") || !comprobacion.checkStringBetween(id_ciudad, 1, 5)|| coordenadas.contentEquals("") || !comprobacion.checkStringBetween(coordenadas, 1, 120) )  {
				
				request.setAttribute("insertado", false);
				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    	}else {
	    		
	    		disponible = Boolean.parseBoolean( request.getParameter("disponible"));
	    		cine.setId(id);
	    		cine.setId_cartelera(id_cartelera);
	    		cine.setId_ciudad(id_ciudad);
	    		cine.setNombre(nombre);
	    		cine.setCoordenadas(coordenadas);
	    		cine.setDisponible(disponible);
	    		
			
				if(cine.insertarCine() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				

				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    		
	    	}
			
			
	    }
		
	}

}
