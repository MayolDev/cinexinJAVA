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
import utils.Comprobaciones;
import utils.Email;
import utils.Encriptador;
import utils.GeneradorStringAleatorio;

/**
 * Servlet implementation class CRegistro
 */
@WebServlet("/registro")
public class CRegistro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	Usuario usuario;
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
			
			response.sendRedirect("perfil");
			  
		}else {
			response.sendRedirect("registro.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		Comprobaciones comprobacion;
		boolean error;
		error = false;
		Connection con;
		Encriptador encriptador;
		String asunto;
		String cuerpo;
		Email email;
		
		String dni, nombre, apellidos, direccion , correo , contrasena, contrasena2; 
		
		comprobacion = new Comprobaciones();
		
		dni = request.getParameter("dni");
		nombre= request.getParameter("nombre");
		apellidos = request.getParameter("apellidos");
		direccion = request.getParameter("direccion");
		correo = request.getParameter("email");
		contrasena = request.getParameter("contrasena");
		contrasena2 = request.getParameter("contrasena2");
		
		if(!contrasena.contentEquals(contrasena2) || contrasena.contentEquals("") || !comprobacion.checkStringBetween(contrasena, 6, 30)) {
			sesion.setAttribute("passwdError", true);
			error= true;

		}else {
			sesion.setAttribute("passwdError", false);
		}
		
		
		if(dni.contentEquals("") || !comprobacion.checkDNI(dni) || !comprobacion.checkStringBetween(dni, 1, 29)) {
			sesion.setAttribute("dniError", true);
			error=true;
		}else {
			sesion.setAttribute("dniError", false);

		}
		
		if(nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 35)) {
			sesion.setAttribute("nombreError", true);
			error=true;
		}else {
			sesion.setAttribute("nombreError", false);

	}
		
		if(correo.contentEquals("") || !comprobacion.checkEmail(correo) || !comprobacion.checkStringBetween(correo, 1, 50)) {
			sesion.setAttribute("correoError", true);
			error=true;
		}else {
			sesion.setAttribute("correoError", false);

		}
		
		if(error) {
		    request.setAttribute("nombre", nombre);
		    request.setAttribute("dni", dni);
		    request.setAttribute("apellidos", apellidos);
		    request.setAttribute("email", correo);
		    request.setAttribute("direccion", direccion);

			request.getRequestDispatcher("registro.jsp").forward(request, response);
		}else {
			encriptador = new Encriptador();
			con = (Connection) sesion.getAttribute("conexion");
			
			usuario = new Usuario(con);
			usuario.setNombre(nombre);
			usuario.setApellidos(apellidos);
			usuario.setDni(dni);
			usuario.setEmail(correo);
			usuario.setDireccion(direccion);

			usuario.setContrasena(encriptador.convertirSHA256(contrasena));
			usuario.setRol(Usuario.ROL_CLIENTE);
						
			usuario.setHash(GeneradorStringAleatorio.generateRandomString(64));
			usuario.InsertarCliente();

			asunto = "Bienvenido a 2dk, Este es su correo de verificacion";
			cuerpo = "Para verificar su cuenta, haga click en el siguiente enlace: http://ns3034756.ip-91-121-81.eu:8080/cinexin/verificar?correo=" + correo + "&hash=" + usuario.getHash();
			
			email = new Email(correo, asunto, cuerpo);
			
			email.enviarEmail();
			
			
			response.sendRedirect("index.jsp");
		}
		

}}
