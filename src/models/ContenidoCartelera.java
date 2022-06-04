package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ContenidoCartelera {

	
	String id_cartelera;
	String id_pelicula;

	Connection con;
	
	
	
	public ContenidoCartelera(Connection con) {
		this.con = con;
	}
	
	public ContenidoCartelera() {
		
	}

	
	
	public int insertarContenidoCartelera() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO contenido_cartelera (id_cartelera, id_pelicula) VALUES (? , ? )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1,id_cartelera);
			stmt.setString(2, id_pelicula);

			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar el horario");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	
	
	public ContenidoCartelera[] ObtenerContenido(int limit, int offset) {
		ContenidoCartelera[] contenidos;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		contenidos = new ContenidoCartelera[limit];
		
		try {
			

				query="SELECT * FROM contenido_cartelera WHERE id_cartelera = ? LIMIT ? OFFSET ?  " ; 	

			
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, id_cartelera);
			stmt.setInt(2, limit);
			stmt.setInt(3, offset);

						

			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				ContenidoCartelera contenido;
				
				contenido = new ContenidoCartelera();
				
				contenido.setId_cartelera(rs.getString(1));
				contenido.setId_pelicula(rs.getString(2));

				
				contenidos[indice] = contenido;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las salas");
			e.printStackTrace();
		}
		
		
		
		
		return contenidos;
	}
	
	
	
	public int eliminarContenido() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM contenido_cartelera WHERE id_cartelera = ? AND id_pelicula = ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id_cartelera);
			stmt.setString(2, id_pelicula);
			filasEliminadas = stmt.executeUpdate();			
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return filasEliminadas;

	}


	@JsonIgnore
	public String getNumeroRegistrosPorCiudad() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM contenido_cartelera WHERE id_cartelera = ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id_cartelera);

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

	public String getId_cartelera() {
		return id_cartelera;
	}

	public void setId_cartelera(String id_cartelera) {
		this.id_cartelera = id_cartelera;
	}

	public String getId_pelicula() {
		return id_pelicula;
	}

	public void setId_pelicula(String id_pelicula) {
		this.id_pelicula = id_pelicula;
	}


	
	
	
}
