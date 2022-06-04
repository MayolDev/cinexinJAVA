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
import models.Precioentradas;
import models.Sala;
import models.Sesion;
import utils.Comprobaciones;
import utils.Parseamiento;

/**
 * Servlet implementation class CSeleccionbutacas
 */
@WebServlet("/seleccionbutacas")
public class CSeleccionbutacas extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
	Sesion Msesion;
    Butaca butaca;
	Sala sala;
	Precioentradas precio;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("/cinexin/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Precioentradas[] precios;
		sesion = request.getSession();
		Connection con;
		con = (Connection) sesion.getAttribute("conexion");
		Parseamiento parse;
		Comprobaciones comprobacion;
		int cantidad_nino;
		int cantidad_normal;
		long preciototal;
		parse = new Parseamiento();
		comprobacion = new Comprobaciones();

		if( request.getParameter("ninos") == null || request.getParameter("normal") == null 
		|| !comprobacion.checkInteger(request.getParameter("ninos"))  || !comprobacion.checkInteger(request.getParameter("normal")) ){
			response.sendRedirect("/cinexin/");

		} else{

			cantidad_nino = parse.getInteger(request.getParameter("ninos"));
			cantidad_normal = parse.getInteger(request.getParameter("normal"));
			System.out.println(cantidad_nino + " - "+ cantidad_normal );

			if(cantidad_nino > 0 || cantidad_normal > 0 ){
				precio = new Precioentradas(con);
				int numeroprecios = parse.getInteger(precio.getNumeroRegistros());
				if (numeroprecios <= 0){
					numeroprecios = 1;
				}
				precios = new Precioentradas[numeroprecios];
				precios = precio.ObtenerPrecio(numeroprecios, 0);

				preciototal = (precios[0].getPrecio() * cantidad_normal) + (precios[1].getPrecio() * cantidad_nino);

				sesion.setAttribute("preciototal", preciototal);
				sesion.setAttribute("cantidad_nino", cantidad_nino);
				sesion.setAttribute("cantidad_normal", cantidad_normal);

				sala = new Sala(con);
				sala.setId((String) sesion.getAttribute("id_sala"));
				sala = sala.obtenerSalaPorId();
				System.out.println(sala.getNombre());
				request.setAttribute("nombre_sala", sala.getNombre());


				request.getRequestDispatcher("seleccionbutacas.jsp").forward(request, response);

								


			}else{
				response.sendRedirect("/cinexin/");

			}
		}
		





	}
	
}
