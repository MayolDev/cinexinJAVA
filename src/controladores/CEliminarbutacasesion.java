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


@WebServlet("administracion/eliminarbutacaocupada")
public class CEliminarbutacasesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	ButacaSesion butaca;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
			response.sendRedirect("/cinexin/administracion/butacasocupadas.jsp");

	    }
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		String codigo;
		int id;

		Comprobaciones comprobacion;
		Parseamiento parse;

		comprobacion = new Comprobaciones();
		parse = new Parseamiento();

		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			
	    	butaca = new ButacaSesion(con);
			
			codigo = request.getParameter("id");

			if(!comprobacion.checkInteger(codigo) || codigo == null ){

				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("/cinexin/administracion/butacasocupadas.jsp").forward(request, response);

			}else{

				id = parse.getInteger(codigo);
				butaca.setId(id);
			
				int filas = butaca.eliminarHorario();
	
				if(filas != -1) {
					response.sendRedirect("/cinexin/administracion/butaca.jsp");
	
				}else {
					request.setAttribute("DeleteError", true);
					request.getRequestDispatcher("/cinexin/administracion/butacasocupadas.jsp").forward(request, response);
				}
			}


			
			
		}
	    }
		
	

}
