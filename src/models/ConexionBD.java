package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	
	Connection conexion;
	String url = "jdbc:postgresql://";
	String user;
	String password;
	
	
	
	
	public ConexionBD(String url, String user, String password) {
		this.url = this.url +  url;
		this.user = user;
		this.password = password;
	}


	public Connection conectar() {
		conexion=null; 
		try {
			conexion = DriverManager.getConnection(url, user, password);
		}
		catch (SQLException e) {
			System.out.println("No se ha podido conectar con la base de datos.");
			e.printStackTrace();
		}
		return conexion;
	}

}
