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


@WebServlet("administracion/eliminarcontenido")
public class CEliminarcontenido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	ContenidoCartelera contenido;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
			response.sendRedirect("contenido.jsp");

	    }
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		String codigo, codigo2;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			
	    	contenido = new ContenidoCartelera(con);
			
			codigo = request.getParameter("id_cartelera");
			codigo2 = request.getParameter("id_pelicula");
			contenido.setId_cartelera(codigo);
			contenido.setId_pelicula(codigo2);
			int filas = contenido.eliminarContenido();

			if(filas != -1) {
				response.sendRedirect("contenido.jsp");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("/cinexin/administracion/contenido.jsp").forward(request, response);
			}
			
			
		}
	    }
		
	

}
