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


@WebServlet("administracion/modificarprovincia")
public class CModificarProvincia extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Provincia provincia;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Parseamiento parse;
		parse = new Parseamiento();
		int id_provincia;
		String nombre;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
	    	if(request.getParameter("id_provincia") != null) {
	    		
				id_provincia = parse.getInteger(request.getParameter("id_provincia"));
				nombre = request.getParameter("nombre");


				request.setAttribute("id_provincia", id_provincia);
				request.setAttribute("nombre", nombre);

				request.getRequestDispatcher("modificarprovincia.jsp").forward(request, response);

			}else {
				response.sendRedirect("provincias");
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
		Parseamiento parse;
		parse = new Parseamiento();
		int id_provincia;
		String nombre;
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.js");
	    	
	    }else {
	    	

			nombre = request.getParameter("nombre");
			id_provincia = parse.getInteger(request.getParameter("id_provincia"));
			
			
				
				provincia = new Provincia(con);
				
				provincia.setId_provincia(id_provincia);
				provincia.setNombre(nombre);
				provincia.modificarProvincia();

				response.sendRedirect("provincias");
				
			
			
			
			
	    }
		
		

		
		
		
	}

}
