package controladores;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Pago;
import utils.Email;

/**
 * Servlet implementation class CPago
 */
@WebServlet("/pago")
public class CPago extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HttpSession sesion;
	Pago pago;
	
	/*ALgoritmo de luhn, para comprobar la validez de las tarjetas introducidas. 
	 * 
	 * 
	La fórmula verifica un número contra su dígito de chequeo incluido, el cual es usualmente agregado a un número de cuenta parcial para generar el número de cuenta completo. 
	Este número de cuenta debe pasar la siguiente prueba:

    1 A partir del dígito de chequeo incluido, el cual está a la derecha de todo el número, ir de derecha a izquierda duplicando cada segundo dígito.
    2 Sumar los dígitos del resultado: (ejemplo: 10 = 1 + 0 = 1, 14 = 1 + 4 = 5) juntos con los dígitos sin duplicar del número original.
    3 Si el total de módulo 10 es igual a 0 (si el total termina en cero), entonces el número es válido de acuerdo con la fórmula Luhn, de lo contrario no es válido.
	 * 
	 * 
	 * 
	*/
	
    public static boolean luhnTest(String numero){
        
        int s1 = 0, s2 = 0;
        String reversa = new StringBuffer(numero).reverse().toString();
        for(int i = 0 ;i < reversa.length();i++){
            int digito = Character.digit(reversa.charAt(i), 10);
            if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digito;
            }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digito;
                if(digito >= 5){
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % 10 == 0;
     
    
}
  
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
		String email, numeroTarjeta, caducidad, cvv, titular;
		Email Correo;
		TimerCheckout timer;
		//Expresiones para comprobar numero de tarjeta, email y fecha de caducidad.
		String regex = "^(\\d\\s?){15,16}$"; // 16 digitos o de 4 en 4 separados por espacios.
		String regexemail = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"; 
		String regexcaducidad = "^(0[1-9]|1[0-2])\\/?([0-9]{2})$";
		LocalDateTime horalocal;
		email = request.getParameter("email");
		numeroTarjeta = request.getParameter("numeroTarjeta");
		caducidad = request.getParameter("caducidad");
		cvv = request.getParameter("cvv");
		titular = request.getParameter("titular");

		
		//comprobaciones de valores recogido y atributos de sesion
		if((email != "" && email.matches(regexemail)) 
		&& (numeroTarjeta != "" && numeroTarjeta.matches(regex) ) 
		&& (caducidad != "" && caducidad.matches(regexcaducidad))
		&& (cvv != "" && cvv.length() > 2 && cvv.length() < 5) 
		&& titular != "" 
		&& (sesion.getAttribute("timer") != null && (boolean)sesion.getAttribute("timer") == true)
		&& sesion.getAttribute("timercheckout") != null
		&& sesion.getAttribute("preciototal") != null
		&& sesion.getAttribute("ticketid") != null){

			//SI pasa la validez de la tarjeta
			if(luhnTest(numeroTarjeta.replace(" ", ""))) {
			
				long precioTotal = (long) sesion.getAttribute("preciototal");
				Connection con;
				con = (Connection)sesion.getAttribute("conexion");
				String id_ticket = (String)sesion.getAttribute("ticketid");
				java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

				//Apagamos el timer de la pasarela de pago
				timer = (TimerCheckout) sesion.getAttribute("timercheckout");
				timer.interrupt();
				
				//Añadimos el pago a la base de datos
				pago = new Pago(con);
				pago.setCantidad((long)sesion.getAttribute("preciototal"));
				pago.setId("" + LocalDateTime.now() + precioTotal + titular );
				pago.setId_ticket(id_ticket);
				pago.setHora(sqlDate);
				pago.setMetodo_pago(1);

				pago.insertarPago();

				//Enviamos correo al usuario
				String asunto = "Pago realizado en Cinexin";
				String cuerpo = "Gracias por la compra, estamos generando su entrada, espero que disfrutes de la película." ;
				
				Correo = new Email(email, asunto, cuerpo);
				
				Correo.enviarEmail();
				

				response.sendRedirect("/cinexin/entradas.jsp");

			}

		


		}else{
			request.setAttribute("error", "Datos de pago incorrectos");
			request.getRequestDispatcher("pasarelapago.jsp").forward(request, response);

		}
		
		
		
		
		
		
	}

}
