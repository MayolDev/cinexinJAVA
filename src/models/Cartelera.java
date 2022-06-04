package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cartelera {

	String id;
	String nombre;
	Connection con;
	
	public Cartelera(Connection con) {
		this.con = con;

	}
	
	public Cartelera() {
		
	}
	
	
	public int insertarCartelera() {
		
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		
		try {
			
			String query="INSERT INTO cartelera (id, nombre ) VALUES (? , ? )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, id);
			stmt.setString(2, nombre);
			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar la cartelera");
			sqle.printStackTrace();
		}
		
		
		return filasInsertadas;
		
	}
	
	
	
public int modificarCartelera() {
		
		int filasActualizadas;
		filasActualizadas = -1;
		
		try {
			String query="UPDATE cartelera SET nombre = ?  WHERE id = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, nombre);
			stmt.setString(2, id);
			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle){
			System.err.println("No se ha podido modificar la cartelera");
			sqle.printStackTrace();
		}
		
		
		return filasActualizadas;
	}
	
	
	
	
	public int eliminarCartelera() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM cartelera WHERE id = ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			filasEliminadas = stmt.executeUpdate();			
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return filasEliminadas;

	}
	
	public Cartelera[] ObtenerCarteleras(int limit, int offset) {
		Cartelera[] carteleras;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		carteleras = new Cartelera[limit];
		
		try {
			
			query="SELECT * FROM cartelera ORDER BY id LIMIT ? OFFSET ?"  ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			

			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				Cartelera cartelera;
				
				cartelera = new Cartelera();
				
				cartelera.setId(rs.getString(1));
				cartelera.setNombre(rs.getString(2));
				
				carteleras[indice] = cartelera;
				
				indice++;
				
			}
		
		}catch(SQLException e) {
			System.out.println("Error al obtener las carteleras");
			e.printStackTrace();
		}
		
		
		
		
		return carteleras;
	}
	
	
	public Cartelera obtenerCarteleraPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Cartelera cartelera;
		cartelera = new Cartelera();
		
		try {
			
			query="SELECT * FROM cartelera where id = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				cartelera.setId(rs.getString(1));
				cartelera.setNombre(rs.getString(2));

			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener la cartelera");
			e.printStackTrace();
		}
		
		
		
		
		return cartelera;
	}
	
	
	
	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM cartelera";
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
	
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	
	

}
