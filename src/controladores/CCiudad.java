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
import utils.Parseamiento;


@WebServlet("/administracion/ciudades")
public class CCiudad extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Ciudad ciudad;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			doPost(request, response);

	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		Connection con;
		Ciudad[] ciudades;
		Parseamiento parse;
		int numeroRegistros;
		parse = new Parseamiento();
		int pagina;
		int offset;
		offset=0;
		pagina =1;
		
		con = (Connection) sesion.getAttribute("conexion");

		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
	    	ciudad = new Ciudad(con);
			
			if(request.getParameter("page") != null) {
				try {
					pagina = parse.getInteger(request.getParameter("page")) ;
					if(pagina <1) {
						pagina = 1;
					}
					if(pagina == 1) {
						offset = 0;
					}else {
						offset = (5*pagina) -5;
					}
					
					request.setAttribute("page", pagina);
					
				}catch(NumberFormatException e) {
					
					pagina = 1;
					offset = 0;
					request.setAttribute("page", pagina);
					e.printStackTrace();

				}

				
			}
			
			
			ciudades = new Ciudad[5];
			ciudades = ciudad.ObtenerCiudades( 5, offset , -1);
			request.setAttribute("rs", ciudades);
			
			numeroRegistros = parse.getInteger(ciudad.getNumeroRegistros()); 
			
			request.setAttribute("numeroRegistros", numeroRegistros);
			
			if(request.getAttribute("DeleteError") != null && (boolean) request.getAttribute("DeleteError")) {
				
				request.setAttribute("DeleteError", true);
			}
			
			request.getRequestDispatcher("/administracion/ciudades.jsp").forward(request, response);
			
		}
	    }
		
		

}
