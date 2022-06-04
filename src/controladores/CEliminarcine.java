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


@WebServlet("administracion/eliminarcine")
public class CEliminarcine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	Cine cine;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			response.sendRedirect("/cinexin/administracion/cines");

	    }
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		String codigo;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			
	    	cine = new Cine(con);
			
			codigo = request.getParameter("id");
			cine.setId(codigo);;
			int filas = cine.eliminarCine();

			if(filas != -1) {
				response.sendRedirect("/cinexin/administracion/cines");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("/administracion/cines").forward(request, response);
			}
			
			
		}
	    }
		
	

}
