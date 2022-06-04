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
import models.Pelicula;
import models.Sala;
import models.Sesion;
import utils.Parseamiento;

/**
 * Servlet implementation class CPeliculaUsuarios
 */
@WebServlet("/pelicula-cartelera")
public class CPeliculaUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Pelicula pelicula;
	   Cine cine;
	   Sala sala;
	   Sesion Msesion;
	   HttpSession sesion;
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idPelicula;
		String idCine;
		Connection con;
		Sala[] salas;
		Sesion[] sesiones;
		Parseamiento parse;
		sesion = request.getSession();
		int numeroSalas;

		con = (Connection) sesion.getAttribute("conexion");

		idPelicula = request.getParameter("id");
		idCine = request.getParameter("idcine");

		if(idPelicula != null &&  idCine != null){

			sesion.setAttribute("id_pelicula", idPelicula);
			sesion.setAttribute("id_cine", idCine);

			
			pelicula = new Pelicula(con);
			pelicula.setId(idPelicula);

			pelicula = pelicula.obtenerPeliculaPorId();

			sesion.setAttribute("nombre_pelicula", pelicula.getNombre());
			sesion.setAttribute("foto_pelicula", pelicula.getImagen());
			


			request.setAttribute("pelicula", pelicula);

			cine = new Cine(con);
			cine.setId(idCine);
			cine = cine.obtenerCinePorId();
			request.setAttribute("cine", cine);
			sesion.setAttribute("nombre_cine", cine.getNombre());


			sala = new Sala(con);
			sala.setId_cine(idCine);
			parse = new Parseamiento();
			numeroSalas = parse.getInteger(sala.getNumeroRegistrosPorSesion());
			if(numeroSalas <= 0) {
				numeroSalas = 1;
			}
			salas = new Sala[numeroSalas];
			salas = sala.ObtenerSalas(numeroSalas, 0, Sala.BUSQUEDA_CINE);

			String[] idsSalas;
			idsSalas = new String[numeroSalas];

			int index = 0;
			for (Sala sala :salas){
				idsSalas[index] = sala.getId();
				index++;
			}

			Msesion = new Sesion(con);
			Msesion.setId_pelicula(idPelicula);
			sesiones = new Sesion[idsSalas.length];
			
			sesiones = Msesion.ObtenerSesionesPorIdsSalas(10, 0, idsSalas);

			request.setAttribute("sesiones", sesiones);

			

			request.getRequestDispatcher("pelicula-cartelera.jsp").forward(request, response);

		}




	}



}
