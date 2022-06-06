package controladores;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ButacaSesion;
import models.Ticket;
import utils.GeneradorStringAleatorio;

/**
 * Servlet implementation class CPasarelapago
 */
@WebServlet("/pasarelapago")
public class CPasarelapago extends HttpServlet {
	private static final long serialVersionUID = 1L;
       HttpSession sesion;
	   Ticket ticket;
	   ButacaSesion butacaocupada;

   
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
		//si no hay timer
		if(sesion.getAttribute("timer") == null || (boolean)sesion.getAttribute("timer") == false ){


			String butacas, posicionButacas;
			int cantidad_nino, cantidad_normal, cantidadtotal;
			Connection con;
			con = (Connection) sesion.getAttribute("conexion");
			Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
			boolean error = false;
			//si los parametros son correctos
			if(request.getParameter("butacas") != null || sesion.getAttribute("cantidad_nino") != null || sesion.getAttribute("cantidad_normal") != null || request.getParameter("posicion") != null){
				butacas = request.getParameter("butacas");
				posicionButacas = request.getParameter("posicion");
				//recogemos las ids de las butacas y su posicion, reemplacamos los espacios.
				butacas = butacas.replace(" ", "");
				//convertimos a array los ids recogidos
				String[] arrButacas = butacas.split(",");
				cantidad_nino = (int) sesion.getAttribute("cantidad_nino");
				cantidad_normal = (int) sesion.getAttribute("cantidad_normal");
				//miramos cuantas entradas va a comprar el usuario
				cantidadtotal = cantidad_nino + cantidad_normal;
				//recogemos un array de ids de tickets
				String[] arrTicketId;
				arrTicketId = new String[arrButacas.length];
				//añadimos un hash a los tickets, que nos servirá para el codigo QR
				String hash;
				hash = GeneradorStringAleatorio.generateRandomString(64);
				//si la cantidad de entradas coincide con las butacas cogidas, todo correcto
				if(cantidadtotal == arrButacas.length ){
					//comprobacion de parametros
					if(sesion.getAttribute("id_sesion") != null || sesion.getAttribute("nombre_pelicula") != null || sesion.getAttribute("nombre_cine") != null || sesion.getAttribute("preciototal") != null){
						
						//Realizamos una transaccion sql para que si 2 usuarios compran la misma entrada al mismo tiempo, una de estas transacciones falle y haga rollback.
						try{
							//Desactivamos el autocommit
							con.setAutoCommit(false);
							//Creamos ticket
							ticket  = new Ticket(con);
							String idTicket = "";
							if(sesion.getAttribute("idCliente") != null){
								ticket.setId_usuario((String)sesion.getAttribute("idCliente"));
							}else{
								ticket.setId_usuario("publico");
							}
							ticket.setPrecio((long) sesion.getAttribute("preciototal"));
							ticket.setFecha_compra(timeNow);
							ticket.setId_sesion((int)sesion.getAttribute("id_sesion"));
							ticket.setHash(hash);
							int index= 0;
							//insertamos tickets y reservamos butacas
							for(String butaca:arrButacas ){
								idTicket = ((int) sesion.getAttribute("id_sesion")) + ((String)sesion.getAttribute("nombre_pelicula")) + ((String)sesion.getAttribute("nombre_cine")) + butaca;
								arrTicketId[index] = idTicket;
								index++;
								ticket.setId(idTicket);
								if(ticket.insertarTicket() < 1){
									error = true;
								}
								butacaocupada = new ButacaSesion(con);
								butacaocupada.setId_ticket(idTicket);
								butacaocupada.setId_sesion((int)sesion.getAttribute("id_sesion"));
								butacaocupada.setId_butaca(butaca);
								if(butacaocupada.insertarHorarioSesion() < 1){
									error = true;
	
								}
	
							}
	
	
							// si todo ha ido bien, commit.
							//si hay algun error, rollback
							if(error){
									con.rollback();
									request.setAttribute("error", "No se han elegido correctamente los asientos. Es posible que otra persona haya escogido los asientos que usted desea. Por favor, elija otros.");
									request.getRequestDispatcher("seleccionbutacas.jsp").forward(request, response);
							}else{
								con.commit();

							}
							//establecemos de nuevo el autocommit
							con.setAutoCommit(true);

							sesion.setAttribute("butacas", butacas);
							//Activamos un hilo que hará de timer, estableciendo un tiempo para el pago de 5 minutos.
							TimerCheckout timer;
							timer = new TimerCheckout(300000, arrTicketId, arrButacas, ((int)sesion.getAttribute("id_sesion")), con, sesion);
							timer.start();
							//pasamos variables a la sesion
							sesion.setAttribute("ticketid", arrTicketId[0]);
							sesion.setAttribute("timercheckout", timer);
							sesion.setAttribute("hash", hash);
							sesion.setAttribute("posicionButacas", posicionButacas);
							request.getRequestDispatcher("pasarelapago.jsp").forward(request, response);
	
						}catch(SQLException e){
							e.printStackTrace();
							if(con != null){
								try {
									//si falla, rollback
									System.err.print("Transaction is being rolled back");
									con.rollback();
									request.setAttribute("error", "No se han elegido correctamente los asientos. Es posible que otra persona haya escogido los asientos que usted desea. Por favor, elija otros.");
									request.getRequestDispatcher("seleccionbutacas.jsp").forward(request, response);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
	
						}
	
					}else{
						response.sendRedirect("/cinexin/");
					}
						
	
	
	
				}else{
					request.setAttribute("error", "No se han elegido correctamente los asientos. Es posible que otra persona haya escogido los asientos que usted desea. Por favor, elija otros.");
					request.getRequestDispatcher("seleccionbutacas.jsp").forward(request, response);
				}
	
	
	
	
			}else{
				request.setAttribute("error", "No se han elegido correctamente los asientos. Es posible que otra persona haya escogido los asientos que usted desea. Por favor, elija otros.");
				request.getRequestDispatcher("seleccionbutacas.jsp").forward(request, response);
	
			}

		}else{

			if((boolean)sesion.getAttribute("timer") == true){

				response.sendRedirect("pasarelapago.jsp");
			}

			
		}
		


		



		
	}

}
