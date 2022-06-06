package controladores;

import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Usuario;
import utils.Encriptador;

/**
 * Servlet implementation class CLogin
 */
@WebServlet("/login")
public class CLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Usuario usuario;
	HttpSession sesion;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		
		if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
			
			response.sendRedirect("index.jsp");
			  
		}else {
			
			response.sendRedirect("login.jsp");

		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		Encriptador encriptador;
		encriptador = new Encriptador();
		sesion = request.getSession();
		con = (Connection)sesion.getAttribute("conexion");
		Date fechaActual;
		Date fechaSalida;
		Calendar calendar;
		Calendar calendar2;
		int intentos;
		String email;
		String contrasena;
		
		fechaActual = new Date();
		fechaSalida = new Date();
		calendar = Calendar.getInstance();
		calendar2 = Calendar.getInstance();
		calendar.setTime(fechaActual);
		intentos = 0;

		//intentos de login
		
		if(sesion.getAttribute("intentos") != null) {
			
			intentos = (int)sesion.getAttribute("intentos");
			
		}
		if(sesion.getAttribute("fechalogin") != null) {
			
			calendar2.setTime((Date)sesion.getAttribute("fechalogin") );
		}
		
		

		email = request.getParameter("email");
		contrasena = request.getParameter("password");
		
		//encriptamos la contraseña para compararla
		contrasena = encriptador.convertirSHA256(contrasena);
		
		usuario = new Usuario(con);
		
		usuario.setEmail(email);
		usuario.setContrasena(contrasena);
		
		usuario = usuario.LoginUsuario();
		
		//Comprobaciones, si el login es incorrecto, aumentamos el tiempo de espera para loguearse.
		try {

			if(usuario.getDni() == null) {
				
				sesion.setAttribute("loginError", true);
				request.setAttribute("loginEmail", email);  
				sesion.setAttribute("intentos", (intentos + 1));
				calendar.add(Calendar.MINUTE, intentos *2 );
				fechaSalida = calendar.getTime();
				sesion.setAttribute("fechalogin", fechaSalida);

				
				
				request.getRequestDispatcher("login.jsp").forward(request, response);

			}else {
				if(usuario.getVerificado() ) {
					
					if(sesion.getAttribute("fechalogin") != null) {
						
						calendar2.setTime((Date)sesion.getAttribute("fechalogin"));
						
						if(calendar.after(calendar2)){
							
							sesion.setAttribute("logged", true);
							sesion.setAttribute("idCliente", usuario.getDni() );
							sesion.setAttribute("rol", usuario.getRol());
							response.sendRedirect("perfil");
							
						}else {
							
							sesion.setAttribute("loginError", true);
							request.setAttribute("loginEmail", email);  
							sesion.setAttribute("intentos", (intentos + 1));
							calendar.add(Calendar.MINUTE, intentos *2 );
							fechaSalida = calendar.getTime();
							sesion.setAttribute("fechalogin", fechaSalida);
							request.getRequestDispatcher("login.jsp").forward(request, response);
							
						}
						
						
					}else {
						
						sesion.setAttribute("logged", true);
						sesion.setAttribute("idCliente", usuario.getDni() );
						sesion.setAttribute("rol", usuario.getRol());
						response.sendRedirect("index.jsp");
						
					}
					

				}else {
					
					sesion.setAttribute("verificacionError", true);
					request.setAttribute("loginEmail", email);
					request.getRequestDispatcher("login.jsp").forward(request, response);

				}


			}
		} catch (ServletException e) {
			System.out.println("Error en el servidor");
			e.printStackTrace();
		} 
		
		
		
		
	}

}
