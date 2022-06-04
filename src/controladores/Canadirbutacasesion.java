package controladores;

import java.io.IOException;
import java.sql.Connection;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.ButacaSesion;

import models.Usuario;
import utils.Comprobaciones;
import utils.Parseamiento;


/**
 * Servlet implementation class anadirprovincia
 */
@WebServlet("/anadirhorariosesion")
public class Canadirbutacasesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    ButacaSesion horario;

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
		horario = new ButacaSesion(con);
		Parseamiento parse;
		parse = new Parseamiento();
		String id_butaca, id_ticket, strid, strid_sesion; 
		int id, id_sesion;

		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			id_butaca = request.getParameter("id_butaca");
			id_ticket = request.getParameter("id_ticket");
			strid = request.getParameter("id");
			strid_sesion = request.getParameter("id_sesion");

			
			
			if(id_butaca.contentEquals("") || !comprobacion.checkStringBetween(id_butaca, 1, 10) 
					|| id_ticket.contentEquals("") 
					|| !comprobacion.checkInteger(strid)
					|| !comprobacion.checkInteger(strid_sesion) ) {
				
				request.setAttribute("insertado", false);
				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    	}else {
	    		
				id_sesion = parse.getInteger(strid_sesion);
				id = parse.getInteger(strid);


				horario.setId(id);
				horario.setId_butaca(id_butaca);
				horario.setId_sesion(id_sesion);
				horario.setId_ticket(id_ticket);
				
				if(horario.insertarHorarioSesion() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				

				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    		
	    	}
			
			
	    }
		
	}

}
