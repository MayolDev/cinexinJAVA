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

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import models.Precioentradas;
import utils.Parseamiento;



/**
 * Servlet implementation class CSeleccionButacas
 */
@WebServlet("/seleccionentradas")
public class CSeleccionEntradas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
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
		sesion = request.getSession();
		Connection con;
		String id_sala;
		int id_sesion;
		Date fecha;
		Time hora_entrada;
		String JSON;
		Precioentradas [] precios;
		JSON = request.getParameter("horas");
		Parseamiento parse;
		parse = new Parseamiento();

		if(JSON != null){

		    JSONParser parser = new JSONParser();
		    JSONObject obj;
		    try {
		        obj = (JSONObject) parser.parse(JSON);

		        System.out.println(obj.get("id_sala"));
		        System.out.println(obj.get("id"));
		        System.out.println(obj.get("fecha"));
		        System.out.println(obj.get("hora_entrada"));
		        id_sala = (String) obj.get("id_sala");
				id_sesion = (int)(long) obj.get("id");
				fecha = Date.valueOf((String) obj.get("fecha"));
				hora_entrada = Time.valueOf((String) obj.get("hora_entrada")+":00");
				
				sesion.setAttribute("id_sala", id_sala);
				sesion.setAttribute("id_sesion", id_sesion);
				sesion.setAttribute("fecha", fecha);
				sesion.setAttribute("hora_entrada", hora_entrada);
				con = (Connection)sesion.getAttribute("conexion");
				precio = new Precioentradas(con);
				int cantidadPrecios = parse.getInteger(precio.getNumeroRegistros());
				if(cantidadPrecios <= 0){
					cantidadPrecios = 1;
				}
				precios = new Precioentradas[cantidadPrecios];
				precios = precio.ObtenerPrecio(cantidadPrecios, 0);

				request.setAttribute("precios", precios);
				request.getRequestDispatcher("seleccionentradas.jsp").forward(request, response);


		      } catch (ParseException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        response.sendRedirect("/cinexin/");
		      }
		    
			

			
			



		}



		
	
		response.sendRedirect("/cinexin/");
		


	}

}
