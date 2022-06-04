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
import utils.Parseamiento;


@WebServlet("/cinexin/administracion/modificarsesion")
public class CModificarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Sesion MSesion;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id_pelicula, id_sala;
		int id;
		Date fecha;
		Time hora_entrada, hora_salida;
		Parseamiento parse;
		parse = new Parseamiento();
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/index.js");
	    	
	    }else {
	    	
	    	if(request.getParameter("id") != null) {
	    		id = parse.getInteger(request.getParameter("id"));
				id_pelicula = request.getParameter("id_pelicula");
				id_sala = request.getParameter("id_sala");
				fecha = Date.valueOf(request.getParameter("fecha"));
				hora_entrada = Time.valueOf(request.getParameter("hora_entrada"));
				hora_salida = Time.valueOf(request.getParameter("hora_salida"));


				request.setAttribute("id", id);
				request.setAttribute("id_pelicula", id_pelicula);
				request.setAttribute("id_sala", id_sala);
				request.setAttribute("fecha", fecha);
				request.setAttribute("hora_entrada", hora_entrada);
				request.setAttribute("hora_salida", hora_salida);




				request.getRequestDispatcher("/cinexin/administracion/modificarsesion.jsp").forward(request, response);

			}else {
				response.sendRedirect("/cinexin/administracion/sesiones.jsp");
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

		String id_pelicula, id_sala;
		int id;
		Date fecha;
		Time hora_entrada, hora_salida;
		Parseamiento parse;
		parse = new Parseamiento();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.js");
	    	
	    }else {
	    	
			id = parse.getInteger(request.getParameter("id"));
			id_pelicula = request.getParameter("id_pelicula");
			id_sala = request.getParameter("id_sala");
			fecha = Date.valueOf(request.getParameter("fecha"));
			hora_entrada = Time.valueOf(request.getParameter("hora_entrada"));
			hora_salida = Time.valueOf(request.getParameter("hora_salida"));

			MSesion = new Sesion(con);
				
			MSesion.setId(id);
			MSesion.setId_pelicula(id_pelicula);
			MSesion.setId_sala(id_sala);
			MSesion.setFecha(fecha);
			MSesion.setHora_entrada(hora_entrada);
			MSesion.setHora_salida(hora_salida);

			MSesion.modificarSesion();
				response.sendRedirect("/cinexin/administracion/sesiones.jsp");
				
			
			
			
			
	    }
		
		

		
		
		
	}

}
