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


@WebServlet("/cinexin/administracion/modificarsala")
public class CModificarSala extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Sala sala;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id, id_cine, nombre;

		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/index.js");
	    	
	    }else {
	    	
	    	if(request.getParameter("id") != null) {
	    		id = request.getParameter("id");
				id_cine = request.getParameter("id_cine");
				nombre = request.getParameter("nombre");

				request.setAttribute("id", id);
				request.setAttribute("id_cine", id_cine);
				request.setAttribute("nombre", nombre);




				request.getRequestDispatcher("/cinexin/administracion/modificarsala.jsp").forward(request, response);

			}else {
				response.sendRedirect("/cinexin/administracion/sala.jsp");
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

		String id, id_cine, nombre;		
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.js");
	    	
	    }else {
	    	
			id = request.getParameter("id");
			id_cine = request.getParameter("id_cine");
			nombre = request.getParameter("nombre");

			sala = new Sala(con);
				
				sala.setId(id);
				sala.setId_cine(id_cine);
				sala.setNombre(nombre);

				sala.modificarSala();
				response.sendRedirect("/cinexin/administracion/sala.jsp");
				
			
			
			
			
	    }
		
		

		
		
		
	}

}
