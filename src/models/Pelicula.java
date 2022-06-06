package models;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Pelicula {

	String id;
	String nombre;
	int duracion;
	String director;
	String trailer;
	String categoria;
	String actores;
	int calificacion;
	byte[] imagen;
	FileInputStream fis;
	File file;
	Connection con;
	
	
	public Pelicula(Connection con) {
		this.con = con;
	}
	
	public Pelicula() {
		
	}
	
	
	
	
	public int insertarPelicula() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO pelicula (id, nombre, duracion, director, trailer, categoria, actores, calificacion, imagen) VALUES (? , ? , ? , ? , ? , ? , ? , ?, ?)"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1,id);
			stmt.setString(2, nombre);
			stmt.setInt(3, duracion);
			stmt.setString(4, director);
			stmt.setString(5, trailer);
			stmt.setString(6, categoria);
			stmt.setString(7, actores);
			stmt.setInt(8, calificacion);
			stmt.setBinaryStream(9, fis, (int)file.length());
			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar la pelicula");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	public int modificarPelicula() {
		
		int filasActualizadas;
		filasActualizadas = -1;
		
		try {
			String query="UPDATE pelicula SET nombre = ? , duracion = ? , director = ? , trailer = ? , categoria = ? , actores = ? , calificacion = ? WHERE id = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, nombre);
			stmt.setInt(2, duracion);
			stmt.setString(3, director);
			stmt.setString(4, trailer);
			stmt.setString(5, categoria);
			stmt.setString(6, actores);
			stmt.setInt(7, calificacion);
			stmt.setString(8, id);

			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle){
			System.err.println("No se ha podido modificar la pelicula");
			sqle.printStackTrace();
		}
		
		
		return filasActualizadas;
	}
	
	
	public Pelicula[] ObtenerPelicula(int limit, int offset) {
		Pelicula[] peliculas;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		peliculas = new Pelicula[limit];
		
		try {
			
			query="SELECT * FROM pelicula ORDER BY id LIMIT ? OFFSET ? " ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			

			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				Pelicula pelicula;
				
				pelicula = new Pelicula();
				
				pelicula.setId(rs.getString(1));
				pelicula.setNombre(rs.getString(2));
				pelicula.setDuracion(rs.getInt(3));
				pelicula.setDirector(rs.getString(4));
				pelicula.setTrailer(rs.getString(5));
				pelicula.setCategoria(rs.getString(6));
				pelicula.setActores(rs.getString(7));
				pelicula.setCalificacion(rs.getInt(8));
				pelicula.setImagen(rs.getBytes(9));
				
				peliculas[indice] = pelicula;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las peliculas");
			e.printStackTrace();
		}
		
		
		
		
		return peliculas;
	}


	public Pelicula[] ObtenerPeliculasPorIds(int limit, int offset, String[] ids) {
		Pelicula[] peliculas;
		ResultSet rs;
		PreparedStatement stmt;
		
		if(limit < 1) {
			limit = 1;
		}
		
		peliculas = new Pelicula[limit];
		
		try {
			StringBuilder query = new StringBuilder("SELECT * FROM pelicula WHERE id IN (");


			for(int i = 0; i < ids.length; i++){
				query.append(" ? ,");

			}

			query.delete(query.length()-1, query.length());
			query.append(" ) ORDER BY id LIMIT ? OFFSET ?");
			
			stmt = con.prepareStatement(query.toString());
			for(int i = 0; i < ids.length; i++){
				stmt.setString(i+1, ids[i]);
			}

			stmt.setInt(ids.length+1, limit);
			stmt.setInt(ids.length+2, offset);
			

			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				Pelicula pelicula;
				
				pelicula = new Pelicula();
				
				pelicula.setId(rs.getString(1));
				pelicula.setNombre(rs.getString(2));
				pelicula.setDuracion(rs.getInt(3));
				pelicula.setDirector(rs.getString(4));
				pelicula.setTrailer(rs.getString(5));
				pelicula.setCategoria(rs.getString(6));
				pelicula.setActores(rs.getString(7));
				pelicula.setCalificacion(rs.getInt(8));
				pelicula.setImagen(rs.getBytes(9));

				
				peliculas[indice] = pelicula;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las peliculas");
			e.printStackTrace();
		}
		
		
		
		
		return peliculas;
	}
	
	
	public Pelicula obtenerPeliculaPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Pelicula pelicula;
		pelicula = new Pelicula();
		
		try {
			
			query="SELECT * FROM pelicula where id = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				pelicula.setId(rs.getString(1));
				pelicula.setNombre(rs.getString(2));
				pelicula.setDuracion(rs.getInt(3));
				pelicula.setDirector(rs.getString(4));
				pelicula.setTrailer(rs.getString(5));
				pelicula.setCategoria(rs.getString(6));
				pelicula.setActores(rs.getString(7));
				pelicula.setCalificacion(rs.getInt(8));
				pelicula.setImagen(rs.getBytes(9));

			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener la pelicula");
			e.printStackTrace();
		}
		
		
		
		
		return pelicula;
	}
	
	
	
	
	
	public int eliminarPelicula() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM pelicula WHERE id = ? ";
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
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM pelicula";
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
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getTrailer() {
		return trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getActores() {
		return actores;
	}
	public void setActores(String actores) {
		this.actores = actores;
	}
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public FileInputStream getFis() {
		return fis;
	}

	public void setFis(FileInputStream fis) {
		this.fis = fis;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
	
	
	

	
	
}
