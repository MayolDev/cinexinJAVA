package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Pago {
	
	String id;
	long cantidad;
	Date hora;
	int metodo_pago;
	String id_ticket;
	Connection con;

	public static final int BUSQUEDA_SALA = 1;


	public Pago(Connection con) {
		this.con = con;
	}
	
	public Pago() {
		
	}
	
	
	public int insertarPago() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO pago (id, cantidad,  hora, metodo_pago, id_ticket ) VALUES (? , ? , ? , ? , ? )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1,id);
			stmt.setLong(2, cantidad);
			stmt.setDate(3, hora);
			stmt.setInt(4, metodo_pago);
			stmt.setString(5, id_ticket);
			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar el pago");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	
	
	
	public Pago obtenerPagoPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Pago pago;
		pago = new Pago();
		
		try {
			
			query="SELECT * FROM pago where id = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				pago.setId(rs.getString(1));
				pago.setCantidad(rs.getLong(2));
				pago.setHora(rs.getDate(3));
				pago.setMetodo_pago(rs.getInt(4));
				pago.setId_ticket(rs.getString(5));
			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener el pago");
			e.printStackTrace();
		}
		
		
		
		
		return pago;
	}
	
	
	
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public int getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(int metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	public String getId_ticket() {
		return id_ticket;
	}

	public void setId_ticket(String id_ticket) {
		this.id_ticket = id_ticket;
	}
	
	

	
	
	
	
	
	
	
}
