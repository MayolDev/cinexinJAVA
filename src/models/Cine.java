package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Cine {

	String id;
	String id_ciudad;
	String id_cartelera;
	String nombre;
	String coordenadas;
	Boolean disponible;
	Connection con;
	
	public static final int BUSQUEDA_CIUDAD = 1;
	public static final int BUSQUEDA_DISPONIBLE = 2;
	

	public Cine(Connection con) {
		this.con = con;

	}
	
	public Cine() {
		
	}
	
	
	public int insertarCine() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO cine (id, id_ciudad, id_cartelera, nombre, coordenadas, disponible ) VALUES (? , ? , ? , ? , ? , ?)"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1,id);
			stmt.setString(2, id_ciudad);
			stmt.setString(3, id_cartelera);
			stmt.setString(4, nombre);
			stmt.setString(5, coordenadas);
			stmt.setBoolean(6, disponible);
			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar la butaca");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	
	public int modificarCine() {
		
		int filasActualizadas;
		filasActualizadas = -1;
		
		try {
			String query="UPDATE cine SET id_provincia = ? , id_cartelera = ? , nombre = ? , coordenadas = ? , disponible = ?   WHERE id = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, id_ciudad);
			stmt.setString(2, id_cartelera);
			stmt.setString(3, nombre);
			stmt.setString(4, coordenadas);
			stmt.setBoolean(5, disponible);
			stmt.setString(6, id);
			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle){
			System.err.println("No se ha podido modificar la butaca");
			sqle.printStackTrace();
		}
		
		
		return filasActualizadas;
	}
	
	
	public Cine[] ObtenerCines(int limit, int offset, int busqueda) {
		Cine[] cines;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		cines = new Cine[limit];
		
		try {
			
			switch(busqueda) {
			case BUSQUEDA_CIUDAD: 
				query="SELECT * FROM cine WHERE id_ciudad = ? AND disponible = true ORDER BY id LIMIT ? OFFSET ? " ; 	
				break;
			case BUSQUEDA_DISPONIBLE:
				query="SELECT * FROM cine WHERE disponible = ? ORDER BY id LIMIT ? OFFSET ? " ; 	
				break;
			default:
				query="SELECT * FROM cine ORDER BY id LIMIT ? OFFSET ? " ; 	
				
			}
			
			
			stmt = con.prepareStatement(query);
			
			
			switch(busqueda) {
			case BUSQUEDA_CIUDAD: 
				stmt.setString(1, id_ciudad); 
				stmt.setInt(2, limit);
				stmt.setInt(3, offset);	
				break;
			case BUSQUEDA_DISPONIBLE:
				stmt.setBoolean(1, disponible); 
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
				
				Cine cine;
				
				cine = new Cine();
				
				cine.setId(rs.getString(1));
				cine.setId_ciudad(rs.getString(2));
				cine.setId_cartelera(rs.getString(3));
				cine.setNombre(rs.getString(4));
				cine.setCoordenadas(rs.getString(5));
				cine.setDisponible(rs.getBoolean(6));
				
				cines[indice] = cine;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener los cines");
			e.printStackTrace();
		}
		
		
		
		
		return cines;
	}
	
	
	
	public Cine obtenerCinePorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Cine cine;
		cine = new Cine();
		
		try {
			
			query="SELECT * FROM cine where id = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				cine.setId(rs.getString(1));
				cine.setId_ciudad(rs.getString(2));
				cine.setId_cartelera(rs.getString(3));
				cine.setNombre(rs.getString(4));
				cine.setCoordenadas(rs.getString(5));
				cine.setDisponible(rs.getBoolean(6));
			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener la butaca");
			e.printStackTrace();
		}
		
		
		
		
		return cine;
	}
	
	
	public int eliminarCine() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM cine WHERE id = ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
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
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM cine";
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
	public String getNumeroRegistrosPorCiudad() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM cine WHERE id_ciudad = ? AND disponible = true ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id_ciudad);

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
	public String getId_ciudad() {
		return id_ciudad;
	}
	public void setId_ciudad(String id_ciudad) {
		this.id_ciudad = id_ciudad;
	}
	public String getId_cartelera() {
		return id_cartelera;
	}
	public void setId_cartelera(String id_cartelera) {
		this.id_cartelera = id_cartelera;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}
	public Boolean getDisponible() {
		return disponible;
	}
	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}

	
	
	
	
	
}
