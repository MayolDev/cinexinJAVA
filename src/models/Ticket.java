package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ticket {

	String id;
	int id_sesion;
	String id_usuario;
	long precio;
	Date fecha_compra;
	String hash;
	Connection con;

	public Ticket(Connection con) {
		this.con = con;
	}
	
	public Ticket() {
		
	}
	
	
	
	public int insertarTicket() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO ticket (id, id_sesion, id_usuario, precio, fecha_compra, hash) VALUES (? , ? , ? , ? , ?, ? )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1,id);
			stmt.setInt(2, id_sesion);
			stmt.setString(3, id_usuario);
			stmt.setLong(4, precio);
			stmt.setDate(5, fecha_compra);
			stmt.setString(6, hash);
			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar el ticket");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	
	
	
	public Ticket[] ObtenerTickets(int limit, int offset) {
		Ticket[] tickets;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		tickets = new Ticket[limit];
		
		try {
			
			query="SELECT * FROM ticket LIMIT ? OFFSET ? where id_sesion = ? AND hash = ? " ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			stmt.setInt(3, id_sesion);
			stmt.setString(4, hash);

			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				Ticket ticket;
				
				ticket = new Ticket();
				
				ticket.setId(rs.getString(1));
				ticket.setId_usuario(rs.getString(2));
				ticket.setPrecio(rs.getLong(3));
				ticket.setFecha_compra(rs.getDate(4));
				ticket.setId_sesion(rs.getInt(5));
				ticket.setHash(rs.getString(6));

				tickets[indice] = ticket;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener los tickets");
			e.printStackTrace();
		}
		
		
		
		
		return tickets;
	}
	
	
	public Ticket obtenerTicketPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Ticket ticket;
		ticket = new Ticket();
		
		try {
			
			query="SELECT * FROM ticket where id = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				ticket.setId(rs.getString(1));
				ticket.setId_usuario(rs.getString(2));
				ticket.setPrecio(rs.getLong(3));
				ticket.setFecha_compra(rs.getDate(4));
				ticket.setId_sesion(rs.getInt(5));
			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener el ticket");
			e.printStackTrace();
		}
		
		
		
		
		return ticket;
	}
	
	
	
	
	
	public int eliminarTicket() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM ticket WHERE id = ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			filasEliminadas = stmt.executeUpdate();			
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return filasEliminadas;

	}
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getId_sesion() {
		return id_sesion;
	}

	public void setId_sesion(int id_sesion) {
		this.id_sesion = id_sesion;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public long getPrecio() {
		return precio;
	}

	public void setPrecio(long precio) {
		this.precio = precio;
	}

	public Date getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(Date fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}


	
	
	
	
	
}
