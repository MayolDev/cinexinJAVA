package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.Sesion;
import models.Usuario;
import utils.Comprobaciones;
import utils.Parseamiento;


@WebServlet("administracion/eliminarsesion")
public class CEliminarsesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	Sesion MSesion;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			response.sendRedirect("/cinexin/administracion/sesiones");

	    }
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		String codigo;
		int id;
		Comprobaciones comprobacion;
		Parseamiento parse;
		parse = new Parseamiento();
		comprobacion = new Comprobaciones();
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			
	    	MSesion = new Sesion(con);
			
			codigo = request.getParameter("id");
			
			if(comprobacion.checkInteger(codigo)) {
				
				id= parse.getInteger(codigo);
				
				MSesion.setId(id);
				int filas = MSesion.eliminarSesion();

				if(filas != -1) {
					response.sendRedirect("/cinexin/administracion/sesiones");

				}else {
					request.setAttribute("DeleteError", true);
					request.getRequestDispatcher("/cinexin/administracion/sesiones").forward(request, response);
				}
				
			}
			
			
			
		}
	    }
		
	

}
