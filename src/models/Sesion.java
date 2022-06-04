package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Sesion {

	
	int id;
	String id_pelicula;
	String id_sala;
	Date fecha;
	Time hora_entrada;
	Time hora_salida;


	Connection con;
	public static final int BUSQUEDA_SALA = 1;
	public static final int BUSQUEDA_PELICULA = 2;
	public static final int BUSQUEDA_PELICULA_SALA = 3;



	
	public Sesion(Connection con) {
		this.con = con;
	}
	
	public Sesion() {
		
	}
	
	
	
	public int insertarSesion() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO sesion (id, id_pelicula, id_sala, fecha, hora_entrada, hora_salida ) VALUES (? , ? , ? , ? , ? , ? )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setInt(1,id);
			stmt.setString(2, id_pelicula);
			stmt.setString(3, id_sala);
			stmt.setDate(4, fecha);
			stmt.setTime(5, hora_entrada);
			stmt.setTime(6, hora_salida);
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar la sesion");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	public int modificarSesion() {
		
		int filasActualizadas;
		filasActualizadas = -1;
		
		try {
			String query="UPDATE sesion SET id_pelicula = ? , id_sala = ? , fecha = ? , hora_entrada = ? , hora_salida = ? WHERE id = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, id_pelicula);
			stmt.setString(2, id_sala);
			stmt.setDate(3, fecha);
			stmt.setTime(4, hora_entrada);
			stmt.setTime(5, hora_salida);
			stmt.setInt(6, id);

			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle){
			System.err.println("No se ha podido modificar la sesion");
			sqle.printStackTrace();
		}
		
		
		return filasActualizadas;
	}
	
	
	public Sesion[] ObtenerSesion(int limit, int offset , int busqueda) {
		Sesion[] sesiones;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		sesiones = new Sesion[limit];
		
		try {

			switch(busqueda) {
				case BUSQUEDA_SALA: 
					query="SELECT * FROM sesion WHERE fecha = ? AND id_sala = ? LIMIT ? OFFSET ? "; 	
					break;
				case BUSQUEDA_PELICULA: 
					query="SELECT * FROM sesion WHERE fecha = ? AND id_pelicula = ?  LIMIT ? OFFSET ? "; 	
					break;
				case BUSQUEDA_PELICULA_SALA:
					query="SELECT * FROM sesion WHERE fecha = ? AND id_pelicula = ? AND id_sala = ?  LIMIT ? OFFSET ? "; 	
					break;
				default:
					query="SELECT * FROM sesion  LIMIT ? OFFSET ? " ; 	
					
				}

			
			
			stmt = con.prepareStatement(query);
			
			
			switch(busqueda) {
				case BUSQUEDA_SALA: 
					stmt.setDate(1, fecha);
					stmt.setString(2, id_sala);
					stmt.setInt(3, limit);
					stmt.setInt(4, offset);
					break;
				case BUSQUEDA_PELICULA: 
					stmt.setDate(1, fecha);
					stmt.setString(2, id_pelicula);
					stmt.setInt(3, limit);
					stmt.setInt(4, offset);
					break;
				case BUSQUEDA_PELICULA_SALA: 
					stmt.setDate(1, fecha);
					stmt.setString(2, id_pelicula);
					stmt.setString(3, id_sala);
					stmt.setInt(4, limit);
					stmt.setInt(5, offset);
					break;
				default:
				stmt.setInt(1, limit);
				stmt.setInt(2, offset);
				}

			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				Sesion sesion;
				
				sesion = new Sesion();
				
				sesion.setId(rs.getInt(1));
				sesion.setId_pelicula(rs.getString(2));
				sesion.setId_sala(rs.getString(3));
				sesion.setFecha(rs.getDate(4));
				sesion.setHora_entrada(rs.getTime(5));
				sesion.setHora_salida(rs.getTime(6));
								
				
				sesiones[indice] = sesion;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las sesiones");
			e.printStackTrace();
		}
		
		
		
		
		return sesiones;
	}
	
	
	public Sesion obtenerSesionPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Sesion sesion;
		sesion = new Sesion();
		
		try {
			
			query="SELECT * FROM sesion where id = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				sesion.setId(rs.getInt(1));
				sesion.setId_pelicula(rs.getString(2));
				sesion.setId_sala(rs.getString(3));
				sesion.setFecha(rs.getDate(4));
				sesion.setHora_entrada(rs.getTime(5));
				sesion.setHora_salida(rs.getTime(6));
								
			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener la sesion");
			e.printStackTrace();
		}
		
		
		
		
		return sesion;
	}
	



	public Sesion[] ObtenerSesionesPorIdsSalas(int limit, int offset, String[] ids) {
		Sesion[] sesiones;
		ResultSet rs;
		PreparedStatement stmt;
		
		if(limit < 1) {
			limit = 1;
		}
		
		sesiones = new Sesion[limit];
		
		try {
			StringBuilder query = new StringBuilder("SELECT * FROM sesion WHERE id_pelicula = ? AND id_sala IN (");


			for(int i = 0; i < ids.length; i++){
				query.append(" ? ,");

			}

			query.delete(query.length()-1, query.length());
			query.append(" ) ORDER BY fecha LIMIT ? OFFSET ?");
			
			stmt = con.prepareStatement(query.toString());
			for(int i = 1; i < ids.length+1; i++){
				stmt.setString(i+1, ids[i-1]);
			}
			stmt.setString(1, id_pelicula);
			stmt.setInt(ids.length+2, limit);
			stmt.setInt(ids.length+3, offset);
			
			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				Sesion sesion;
				
				sesion = new Sesion();
				
				sesion.setId(rs.getInt(1));
				sesion.setId_pelicula(rs.getString(2));
				sesion.setId_sala(rs.getString(3));
				sesion.setFecha(rs.getDate(4));
				sesion.setHora_entrada(rs.getTime(5));
				sesion.setHora_salida(rs.getTime(6));

				
				sesiones[indice] = sesion;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las sesiones");
			e.printStackTrace();
		}
		
		
		
		
		return sesiones;
	}




	
	
	
	
	public int eliminarSesion() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM sesion WHERE id = ? ";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, id);
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
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM sesion";
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
	public String getNumeroRegistrosPorSala() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM sesion WHERE id_sala = ? AND fecha = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id_sala);
			stmt.setDate(2, fecha);

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
	public String getNumeroRegistrosPorSalayPelicula() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM sesion WHERE id_sala = ? AND fecha = ? AND id_pelicula = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id_sala);
			stmt.setDate(2, fecha);
			stmt.setString(3, id_pelicula);


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

	public String getId_pelicula() {
		return id_pelicula;
	}

	public void setId_pelicula(String id_pelicula) {
		this.id_pelicula = id_pelicula;
	}

	public String getId_sala() {
		return id_sala;
	}

	public void setId_sala(String id_sala) {
		this.id_sala = id_sala;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora_entrada() {
		return hora_entrada;
	}

	public void setHora_entrada(Time hora_entrada) {
		this.hora_entrada = hora_entrada;
	}

	public Time getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(Time hora_salida) {
		this.hora_salida = hora_salida;
	}

	
	
	
	
	
	
	
	


	
	
	
	
	
}
