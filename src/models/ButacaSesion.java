package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ButacaSesion {

	int id;
	String id_butaca;
	int id_sesion;
	String id_ticket;

	Connection con;
	
	public static final int BUSQUEDA_SESION = 1;
	public static final int BUSQUEDA_TICKET = 2;
	
	public ButacaSesion(Connection con) {
		this.con = con;
	}
	
	public ButacaSesion() {
		
	}

	
	
	public int insertarHorarioSesion() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO butaca_sesion (id_butaca, id_sesion , id_ticket) VALUES (? , ? , ? )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, id_butaca);
			stmt.setInt(2, id_sesion);
			stmt.setString(3, id_ticket);

			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar el horario");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	

	
	
	public ButacaSesion[] ObtenerHorarios(int limit, int offset, int busqueda) {
		ButacaSesion[] horarios;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		horarios = new ButacaSesion[limit];
		
		try {
			


				switch(busqueda) {
					case BUSQUEDA_SESION: 
						query="SELECT * FROM butaca_sesion WHERE id_sesion = ? LIMIT ? OFFSET ?  " ; 	
						break;
					case BUSQUEDA_TICKET:
						query="SELECT * FROM butaca_sesion WHERE id_ticket = ? LIMIT ? OFFSET ? " ; 	
						break;
					default:
						query="SELECT * FROM butaca_sesion  LIMIT ? OFFSET ? " ; 	
						
					}
				

			
			
			stmt = con.prepareStatement(query);
			
			switch(busqueda) {
				case BUSQUEDA_SESION: 
					stmt.setInt(1, id_sesion);
					stmt.setInt(2, limit);
					stmt.setInt(3, offset);
					break;
				case BUSQUEDA_TICKET:
					stmt.setString(1, id_ticket);
					stmt.setInt(2, limit);
					stmt.setInt(3, offset);
					break;
				default:
				stmt.setInt(1, limit);
				stmt.setInt(2, offset);
				}

						

			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				ButacaSesion horario;
				
				horario = new ButacaSesion();
				
				horario.setId_butaca(rs.getString(1));
				horario.setId_sesion(rs.getInt(2));
				horario.setId_ticket(rs.getString(3));

				
				horarios[indice] = horario;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las salas");
			e.printStackTrace();
		}
		
		
		
		
		return horarios;
	}
	
	
	public ButacaSesion obtenerSalaPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		ButacaSesion horario;
		horario = new ButacaSesion();
		
		try {
			
			query="SELECT * FROM butaca_sesion where id = ? " ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				horario.setId_butaca(rs.getString(1));
				horario.setId_sesion(rs.getInt(2));
				horario.setId_ticket(rs.getString(3));
				


			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener el horario");
			e.printStackTrace();
		}
		
		
		
		
		return horario;
	}
	
	
	
	
	
	public int eliminarHorario() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM butaca_sesion WHERE id_butaca = ? AND id_sesion = ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id_butaca);
			stmt.setInt(2, id_sesion);

			filasEliminadas = stmt.executeUpdate();			
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return filasEliminadas;

	}


	@JsonIgnore
	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM butaca_sesion";
			stmt = con.prepareStatement(query);
			
			rs = stmt.executeQuery();
			stmt.close();

			if(rs.next()) {
				numeroRegistros = rs.getString("CANTIDAD");
			}
			
		} catch (SQLException e) {
			System.out.println("Error al recibir la cantidad de registros");
			e.printStackTrace();
		}
		
		
		return numeroRegistros;
	}
	@JsonIgnore
	public String getNumeroRegistrosPorSesion() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM butaca_sesion WHERE id_sesion = ?";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, id_sesion);
			rs = stmt.executeQuery();
			stmt.close();

			if(rs.next()) {
				numeroRegistros = rs.getString("CANTIDAD");
			}
			
		} catch (SQLException e) {
			System.out.println("Error al recibir la cantidad de registros");
			e.printStackTrace();
		}
		
		
		return numeroRegistros;
	}
	







	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getId_butaca() {
		return id_butaca;
	}

	public void setId_butaca(String id_butaca) {
		this.id_butaca = id_butaca;
	}

	public int getId_sesion() {
		return id_sesion;
	}

	public void setId_sesion(int id_sesion) {
		this.id_sesion = id_sesion;
	}

	public String getId_ticket() {
		return id_ticket;
	}

	public void setId_ticket(String id_ticket) {
		this.id_ticket = id_ticket;
	}

	

	
	
	
}
