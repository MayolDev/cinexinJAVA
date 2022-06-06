package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Provincia {

	int id_provincia;
	String nombre;
	Connection con;
	
	public Provincia(Connection con) {
		this.con = con;
	}
	
	public Provincia() {
		
	}

	
	
	
	
	
	public int insertarProvincia() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO provincia (id_provincia, nombre ) VALUES (? , ? )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setInt(1,id_provincia);
			stmt.setString(2, nombre);

			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar la provincia");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	public int modificarProvincia() {
		
		int filasActualizadas;
		filasActualizadas = -1;
		
		try {
			String query="UPDATE provincia SET nombre = ?  WHERE id_provincia = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, nombre);
			stmt.setInt(2, id_provincia);

			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle){
			System.err.println("No se ha podido modificar la provincia");
			sqle.printStackTrace();
		}
		
		
		return filasActualizadas;
	}
	
	
	public Provincia[] ObtenerProvincias(int limit, int offset) {
		Provincia[] provincias;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		provincias = new Provincia[limit];
		
		try {
			
			query="SELECT * FROM provincia ORDER BY nombre LIMIT ? OFFSET ? " ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			

			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				Provincia provincia;
				
				provincia = new Provincia();
				
				provincia.setId_provincia(rs.getInt(1));
				provincia.setNombre(rs.getString(2));

				
				provincias[indice] = provincia;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las butacas");
			e.printStackTrace();
		}
		
		
		
		
		return provincias;
	}
	
	
	public Provincia obtenerButacaPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Provincia provincia;
		provincia = new Provincia();
		
		try {
			
			query="SELECT * FROM provincia where id_provincia = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, id_provincia);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				provincia.setId_provincia(rs.getInt(1));
				provincia.setNombre(rs.getString(2));

			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener la provincia");
			e.printStackTrace();
		}
		
		
		
		
		return provincia;
	}
	
	
	
	
	
	public int eliminarProvincia() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM provincia WHERE id_provincia = ? ";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, id_provincia);
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
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM provincia";
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
