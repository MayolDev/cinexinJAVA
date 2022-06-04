package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ciudad {

	String cod_postal;
	int id_provincia;
	String nombre;
	Connection con;
	public static final int BUSQUEDA_PROVINCIA = 1;

	public Ciudad(Connection con) {
		this.con = con;
	}
	
	public Ciudad() {
		
	}
	
	public int insertarCiudad() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO ciudad (cod_postal, id_provincia, nombre ) VALUES (? , ? , ? )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1,cod_postal);
			stmt.setInt(2, id_provincia);
			stmt.setString(3, nombre);

			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar la ciudad");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	public int modificarCiudad() {
		
		int filasActualizadas;
		filasActualizadas = -1;
		
		try {
			String query="UPDATE ciudad SET id_provincia = ? , nombre = ? WHERE cod_postal = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setInt(1, id_provincia);
			stmt.setString(2, nombre);
			stmt.setString(3, cod_postal);

			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle){
			System.err.println("No se ha podido modificar la ciudad");
			sqle.printStackTrace();
		}
		
		
		return filasActualizadas;
	}
	
	
	public Ciudad[] ObtenerCiudades(int limit, int offset, int busqueda) {
		Ciudad[] ciudades;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		ciudades = new Ciudad[limit];
		
		try {


			switch(busqueda) {
				case BUSQUEDA_PROVINCIA: 
					query="SELECT * FROM ciudad WHERE id_provincia = ? ORDER BY nombre LIMIT ? OFFSET ? " ; 	
					break;
				default:
					query="SELECT * FROM ciudad ORDER BY cod_postal LIMIT ? OFFSET ?" ; 	
					
				}

			
			
			stmt = con.prepareStatement(query);
			

			switch(busqueda) {
				case BUSQUEDA_PROVINCIA: 
					stmt.setInt(1, id_provincia);
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
				
				Ciudad ciudad;
				
				ciudad = new Ciudad();
				
				ciudad.setCod_postal(rs.getString(1));
				ciudad.setId_provincia(rs.getInt(2));
				ciudad.setNombre(rs.getString(3));

				
				ciudades[indice] = ciudad;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las ciudad");
			e.printStackTrace();
		}
		
		
		
		
		return ciudades;
	}
	
	
	public Ciudad obtenerCiudadPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Ciudad ciudad;
		ciudad = new Ciudad();
		
		try {
			
			query="SELECT * FROM ciudad where cod_postal = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, cod_postal);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				ciudad.setCod_postal(rs.getString(1));
				ciudad.setId_provincia(rs.getInt(2));
				ciudad.setNombre(rs.getString(3));

			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener la ciudad");
			e.printStackTrace();
		}
		
		
		
		
		return ciudad;
	}
	
	
	
	
	
	public int eliminarCiudad() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM ciudad WHERE cod_postal = ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, cod_postal);
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
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM ciudad";
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
	public String getNumeroRegistrosPorProvincia() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM ciudad WHERE id_provincia = ? ";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, id_provincia);

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
	
	
	
	public String getCod_postal() {
		return cod_postal;
	}
	public void setCod_postal(String cod_postal) {
		this.cod_postal = cod_postal;
	}
	public int getId_provincia() {
		return id_provincia;
	}
	public void setId_provincia(int id_provincia) {
		this.id_provincia = id_provincia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	
	
	

	
	
}
