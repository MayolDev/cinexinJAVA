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
import utils.Parseamiento;


@WebServlet("administracion/modificarbutaca")
public class CModificarButaca extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Butaca butaca;
 
	//recogemos los valores en el get, del componente sin modificar
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Parseamiento parse;
		parse = new Parseamiento();
		int tipo, fila;
		String id,id_sala, columna;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
	    	if(request.getParameter("id") != null) {
	    		id = request.getParameter("id");
				id_sala = request.getParameter("id_sala");
				columna = request.getParameter("columna");
				tipo = parse.getInteger(request.getParameter("tipo"));
				fila = parse.getInteger(request.getParameter("fila"));


				request.setAttribute("id", id);
				request.setAttribute("id_sala", id_sala);
				request.setAttribute("columna", columna);
				request.setAttribute("tipo", tipo);
				request.setAttribute("fila", fila);


				request.getRequestDispatcher("modificarbutaca.jsp").forward(request, response);

			}else {
				response.sendRedirect("butaca.jsp");
			}

	    }
		
		
		

		
		
		
	}

	//En el post recogemos los valores a modificar y modificamos
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		sesion = request.getSession();
		con =  (Connection)sesion.getAttribute("conexion");
		Parseamiento parse;
		parse = new Parseamiento();
		int tipo, fila;
		String id,id_sala, columna;
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("/cinexin/");
	    	
	    }else {
	    	
			id = request.getParameter("id");
			id_sala = request.getParameter("id_sala");
			columna = request.getParameter("columna");

			tipo = parse.getInteger(request.getParameter("tipo"));
			fila = parse.getInteger(request.getParameter("fila"));

			butaca = new Butaca(con);
				
				butaca.setId(id);
				butaca.setId_sala(id_sala);
				butaca.setColumna(columna);
				butaca.setTipo(tipo);
				butaca.setFila(fila);	

				butaca.modificarButaca();

				response.sendRedirect("butaca.jsp");
				
			
			
			
			
	    }
		
		

		
		
		
	}

}
