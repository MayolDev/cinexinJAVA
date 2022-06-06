package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Sala {

	String id;
	String id_cine;
	String nombre;
	Connection con;
	
	public static final int BUSQUEDA_CINE = 1;
	
	
	public Sala(Connection con) {
		this.con = con;
	}
	
	public Sala() {
		
	}

	
	
	public int insertarSala() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO sala (id, id_cine , nombre) VALUES (? , ? , ?)"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1,id);
			stmt.setString(2, id_cine);
			stmt.setString(3, nombre);

			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar la sala");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	public int modificarSala() {
		
		int filasActualizadas;
		filasActualizadas = -1;
		
		try {
			String query="UPDATE sala SET id_cine = ? , nombre = ?  WHERE id = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, id_cine);
			stmt.setString(2, nombre);
			stmt.setString(3, id);

			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle){
			System.err.println("No se ha podido modificar la sala");
			sqle.printStackTrace();
		}
		
		
		return filasActualizadas;
	}
	
	
	public Sala[] ObtenerSalas(int limit, int offset, int busqueda) {
		Sala[] salas;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		salas = new Sala[limit];
		
		try {
			
			switch(busqueda) {
			case BUSQUEDA_CINE: 
				query="SELECT * FROM sala WHERE id_cine = ? LIMIT ? OFFSET ?  " ; 	
				break;
			default:
				query="SELECT * FROM sala  LIMIT ? OFFSET ? " ; 	
				
			}
			

			stmt = con.prepareStatement(query);
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			
			switch(busqueda) {
			case BUSQUEDA_CINE: 
				stmt.setString(1, id_cine);
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
				
				Sala sala;
				
				sala = new Sala();
				
				sala.setId(rs.getString(1));
				sala.setId_cine(rs.getString(2));
				sala.setNombre(rs.getString(3));
				
				salas[indice] = sala;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las salas");
			e.printStackTrace();
		}
		
		
		
		
		return salas;
	}
	
	
	public Sala obtenerSalaPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Sala sala;
		sala = new Sala();
		
		try {
			
			query="SELECT * FROM sala where id = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				sala.setId(rs.getString(1));
				sala.setId_cine(rs.getString(2));
				sala.setNombre(rs.getString(3));

			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener la sala");
			e.printStackTrace();
		}
		
		
		
		
		return sala;
	}

	
	
	
	
	
	
	public int eliminarSala() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM sala WHERE id = ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			filasEliminadas = stmt.executeUpdate();			
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return filasEliminadas;

	}



	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM sala";
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
	public String getNumeroRegistrosPorSesion() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM sala WHERE id_cine = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id_cine);
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




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId_cine() {
		return id_cine;
	}

	public void setId_cine(String id_cine) {
		this.id_cine = id_cine;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
	
}
