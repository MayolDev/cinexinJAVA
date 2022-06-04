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
import utils.Comprobaciones;
import utils.Parseamiento;


/**
 * Servlet implementation class anadirprovincia
 */
@WebServlet("/anadirciudad")
public class Canadirciudad extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Ciudad ciudad;

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
		ciudad = new Ciudad(con);
		Parseamiento parse;
		parse = new Parseamiento();
		String nombre, cod_postal; 
		int id_provincia;
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			nombre = request.getParameter("nombre");
			cod_postal = request.getParameter("cod_postal");
			
			
			if(nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 120) 
					|| cod_postal.contentEquals("") || !comprobacion.checkStringBetween(cod_postal, 1, 7) 
					|| !comprobacion.checkInteger(request.getParameter("id_provincia")) ) {
				
				request.setAttribute("insertado", false);
				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    	}else {
	    		
				id_provincia = parse.getInteger((request.getParameter("id_provincia")));
	    		ciudad.setId_provincia(id_provincia);
				ciudad.setNombre(nombre);
				ciudad.setCod_postal(cod_postal);
			
				if(ciudad.insertarCiudad() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				

				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    		
	    	}
			
			
	    }
		
	}

}
