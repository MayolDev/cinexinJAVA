package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.Provincia;
import models.Usuario;
import utils.Parseamiento;


@WebServlet("administracion/eliminarprovincia")
public class CEliminarprovincia extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	Provincia provincia;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
			response.sendRedirect("provincias");

	    }
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		String codigo;
		int id;
		Parseamiento parse;
		parse = new Parseamiento();
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			
	    	provincia = new Provincia(con);
			
			codigo = request.getParameter("id_provincia");
			id = parse.getInteger(codigo);
			provincia.setId_provincia(id);
			int filas = provincia.eliminarProvincia();

			if(filas != -1) {
				response.sendRedirect("provincias");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("/cinexin/administracion/provincias").forward(request, response);
			}
			
			
		}
	    }
		
	

}
