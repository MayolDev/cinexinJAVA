package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Usuario;



@WebServlet("/verificar")
public class CVerificar extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String correo;
		String hash;
		Usuario cliente;
		Connection con;	
		sesion  =request.getSession();
	


		
		if( request.getParameter("correo") != null && request.getParameter("hash") != null) {
			
			correo =(String) request.getParameter("correo");
			hash = (String) request.getParameter("hash");
			con = (Connection)sesion.getAttribute("conexion");
			
			cliente = new Usuario(con);
			cliente.setEmail(correo);
			cliente.setHash(hash);
			
			if(cliente.Verificar() != 0 && cliente.Verificar() != -1 ) {
				
				sesion.setAttribute("verificacionError", false);
				
			}else {
				
				sesion.setAttribute("verificacionError", true);
			}
			
			request.getRequestDispatcher("verificado.jsp").forward(request, response);
			
		}else {
			
		 response.sendRedirect("login");	
			
		}
		
	}



}
