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
import utils.Comprobaciones;


/**
 * Servlet implementation class anadirprovincia
 */
@WebServlet("/anadirsala")
public class Canadirsala extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Sala sala;

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
		sala = new Sala(con);
		String id, id_cine, nombre; 
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			id = request.getParameter("id");
			id_cine = request.getParameter("id_cine");
			nombre = request.getParameter("nombre");
			
			
			if(nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 20) 
					|| id.contentEquals("") || !comprobacion.checkStringBetween(id, 1, 5) 
					|| id_cine.contentEquals("") || !comprobacion.checkStringBetween(id_cine, 1, 5)) {
				
				request.setAttribute("insertado", false);
				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    	}else {
	    		
				sala.setNombre(nombre);
				sala.setId(id);
				sala.setId_cine(id_cine);
			
				if(sala.insertarSala() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				

				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    		
	    	}
			
			
	    }
		
	}

}
