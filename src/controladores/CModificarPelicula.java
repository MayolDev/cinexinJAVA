package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Pelicula;
import models.Usuario;
import utils.Parseamiento;


@WebServlet("administracion/modificarpelicula")
public class CModificarPelicula extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Pelicula pelicula;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id, nombre, director, trailer, categoria, actores, sinopsis;
		int duracion, calificacion;
		Parseamiento parse;
		parse = new Parseamiento();
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
	    	if(request.getParameter("id") != null) {
	    		id = request.getParameter("id");
				nombre = request.getParameter("nombre");
				director = request.getParameter("director");
				trailer = request.getParameter("trailer");
				categoria = request.getParameter("categoria");
				actores = request.getParameter("actores");

				duracion = parse.getInteger(request.getParameter("duracion"));
				calificacion = parse.getInteger(request.getParameter("calificacion"));
				sinopsis = request.getParameter("sinopsis");


				request.setAttribute("id", id);
				request.setAttribute("nombre", nombre);
				request.setAttribute("director", director);
				request.setAttribute("trailer", trailer);
				request.setAttribute("categoria", categoria);
				request.setAttribute("actores", actores);
				request.setAttribute("duracion", duracion);
				request.setAttribute("calificacion", calificacion);
				request.setAttribute("sinopsis", sinopsis);



				request.getRequestDispatcher("modificarpelicula.jsp").forward(request, response);

			}else {
				response.sendRedirect("peliculas");
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
		String id, nombre, director, trailer, categoria, actores, sinopsis;
		int duracion, calificacion;
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.js");
	    	
	    }else {
	    	
			id = request.getParameter("id");
			nombre = request.getParameter("nombre");
			director = request.getParameter("director");
			trailer = request.getParameter("trailer");
			categoria = request.getParameter("categoria");
			actores = request.getParameter("actores");
			sinopsis = request.getParameter("sinopsis");


			duracion = parse.getInteger(request.getParameter("duracion"));
			calificacion = parse.getInteger(request.getParameter("calificacion"));

			pelicula = new Pelicula(con);
				
				pelicula.setId(id);
				pelicula.setNombre(nombre);
				pelicula.setDirector(director);
				pelicula.setTrailer(trailer);
				pelicula.setCategoria(categoria);
				pelicula.setActores(actores);
				pelicula.setDuracion(duracion);
				pelicula.setCalificacion(calificacion);
				pelicula.setSinopsis(sinopsis);
				pelicula.modificarPelicula();
				response.sendRedirect("peliculas");
				
			
			
			
			
	    }
		
		

		
		
		
	}

}
