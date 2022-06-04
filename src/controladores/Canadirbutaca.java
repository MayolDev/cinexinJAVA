package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.Butaca;
import models.Usuario;
import utils.Comprobaciones;
import utils.Parseamiento;


/**
 * Servlet implementation class anadirprovincia
 */
@WebServlet("/anadirbutaca")
public class Canadirbutaca extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Butaca butaca;

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
		butaca = new Butaca(con);
		Parseamiento parse;
		parse = new Parseamiento();
		String id, id_sala, columna; 
		int tipo, fila;
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			id = request.getParameter("id");
			id_sala = request.getParameter("id_sala");
			columna = request.getParameter("columna");
			
			
			if(id.contentEquals("") || !comprobacion.checkStringBetween(id, 1, 10) 
					|| id_sala.contentEquals("") || !comprobacion.checkStringBetween(id_sala, 1, 5) 
					| columna.contentEquals("") || !comprobacion.checkStringBetween(columna, 1, 1)
					|| !comprobacion.checkInteger(request.getParameter("tipo")) 
					|| !comprobacion.checkInteger(request.getParameter("fila"))) {
				
				request.setAttribute("insertado", false);
				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    	}else {
	    		
				tipo = parse.getInteger((request.getParameter("tipo")));
				fila = parse.getInteger((request.getParameter("fila")));

	    		butaca.setId(id);
	    		butaca.setId_sala(id_sala);
	    		butaca.setTipo(tipo);
	    		butaca.setFila(fila);
	    		butaca.setColumna(columna);
			
				if(butaca.insertarButaca() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				

				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    		
	    	}
			
			
	    }
		
	}

}
