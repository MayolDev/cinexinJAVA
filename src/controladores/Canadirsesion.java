package controladores;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;

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


/**
 * Servlet implementation class anadirprovincia
 */
@WebServlet("/anadirsesion")
public class Canadirsesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Sesion Csesion;

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
		Csesion = new Sesion(con);
		Parseamiento parse;
		parse = new Parseamiento();
		String strid, id_pelicula, id_sala, strFecha, strHora_entrada, strHora_salida ; 
		int id;
		Date fecha;
		Time hora_entrada, hora_salida;
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			strid = request.getParameter("id");	
			id_pelicula = request.getParameter("id_pelicula");			
			id_sala = request.getParameter("id_sala");			
			strFecha = request.getParameter("fecha");			
			strHora_entrada = request.getParameter("hora_entrada");			
			strHora_salida = request.getParameter("hora_salida");			
		
			
			if(id_pelicula.contentEquals("") || !comprobacion.checkStringBetween(id_pelicula, 1, 5) 
					|| id_sala.contentEquals("") || !comprobacion.checkStringBetween(id_sala, 1, 5) 
					|| strHora_entrada.contentEquals("") 
					|| strFecha.contentEquals("") 
					|| strHora_salida.contentEquals("") 
					|| !comprobacion.checkInteger(strid) ) {
				
				request.setAttribute("insertado", false);
				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    	}else {
	    		
				id = parse.getInteger((request.getParameter("id")));
				fecha = Date.valueOf(strFecha);

				


				hora_entrada = Time.valueOf(strHora_entrada + ":00");
				hora_salida = Time.valueOf(strHora_salida + ":00");


				Csesion.setId(id);
				Csesion.setId_pelicula(id_pelicula);
				Csesion.setId_sala(id_sala);
				Csesion.setFecha(fecha);
				Csesion.setHora_entrada(hora_entrada);
				Csesion.setHora_salida(hora_salida);
			
				if(Csesion.insertarSesion() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				

				request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

	    		
	    	}
			
			
	    }
		
	}

}
