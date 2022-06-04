package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.ContenidoCartelera;
import models.Usuario;
import utils.Comprobaciones;


/**
 * Servlet implementation class anadirprovincia
 */
@WebServlet("/anadircontenidocartelera")
public class Canadircontenidocartelera extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    ContenidoCartelera contenido;

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
		contenido = new ContenidoCartelera(con);

		String id_cartelera, id_pelicula; 
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			id_cartelera = request.getParameter("id_cartelera");
			id_pelicula = request.getParameter("id_pelicula");
			
			
			if(id_cartelera.contentEquals("") || !comprobacion.checkStringBetween(id_cartelera, 1, 10) 
					|| id_pelicula.contentEquals("") || !comprobacion.checkStringBetween(id_pelicula, 1, 5)  ) {
				
				request.setAttribute("insertado", false);
				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    	}else {

	    		contenido.setId_cartelera(id_cartelera);
	    		contenido.setId_pelicula(id_pelicula);
			
				if(contenido.insertarContenidoCartelera() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				

				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    		
	    	}
			
			
	    }
		
	}

}
