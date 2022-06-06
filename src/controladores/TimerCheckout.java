package controladores;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import models.ButacaSesion;
import models.Ticket;
//Hilo usado como timer para que el usuario efectue el pago antes de que termine este timer. SI no, se borrará su reserva de butacas y su ticket de entrada.
public class TimerCheckout extends Thread{

	int tiempo;
	Ticket ticket;
	ButacaSesion butacaocupada;
	String[] arrIds_tickets;
	String[] arrIds_butacaocupada;
	int id_sesion;
	Date fechaActual;
	Date fechaSalida;
	Calendar calendar;
	Calendar calendar2;
	Connection con;
	HttpSession sesion;

	public TimerCheckout(int tiempo, String[] arrIds_tickets , String[] arrIds_butacaocupada , int id_sesion, Connection con , HttpSession sesion) {
		this.tiempo = tiempo;
		this.arrIds_tickets = arrIds_tickets;
		this.arrIds_butacaocupada = arrIds_butacaocupada;
		this.id_sesion = id_sesion;
		this.con = con;
		this.sesion = sesion;
	}
	
	
	@Override
	public void run() {
		fechaActual = new Date();
		fechaSalida = new Date();
		
		calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		calendar.add(Calendar.MINUTE, 5 );
		fechaSalida = calendar.getTime();
		sesion.setAttribute("timerdate", fechaSalida);
		System.out.println("entro al hilo");
		try{
			sesion.setAttribute("timer", true);

			Thread.sleep(tiempo);
			System.out.println("pasó el tiempo");

			for(String butaca: arrIds_butacaocupada){
				
				butacaocupada = new ButacaSesion(con);
				butacaocupada.setId_butaca(butaca);
				butacaocupada.setId_sesion(id_sesion);
				butacaocupada.eliminarHorario();
			}

			for(String ticketid : arrIds_tickets){
				ticket = new Ticket(con);
				ticket.setId(ticketid);
				ticket.eliminarTicket();
			}
			sesion.setAttribute("timer", false);
			System.out.println("Hilo terminado");

		}catch(InterruptedException ex){
			System.out.print("Hilo interrumpido");
			sesion.setAttribute("timer", false);

		}
		
	}
	
	
	
}
