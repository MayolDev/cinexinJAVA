package models;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Precioentradas {

    int id;
    String nombre;
    long precio;

    Connection con;

    public static final int ENTRADA_NORMAL = 1;
    public static final int ENTRADA_NIÑO = 2;


    public Precioentradas(){

    }

    public Precioentradas(Connection con){
        this.con = con;
    }



    public Precioentradas[] ObtenerPrecio(int limit, int offset) {
		Precioentradas[] precios;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		precios = new Precioentradas[limit];
		
		try {
			
			query="SELECT * FROM precioentrada ORDER BY id LIMIT ? OFFSET ? " ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			

			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;
			
			while(rs.next()) {
				
				Precioentradas precio;
				
				precio = new Precioentradas();
				
				precio.setId(rs.getInt(1));
                precio.setNombre(rs.getString(2));
                precio.setPrecio(rs.getLong(3));
				precios[indice] = precio;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las peliculas");
			e.printStackTrace();
		}
		
		
		
		
		return precios;
	}

    @JsonIgnore
	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM precioentrada";
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




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getPrecio() {
		return precio;
	}

	public void setPrecio(long precio) {
		this.precio = precio;
	}
    
    





}
