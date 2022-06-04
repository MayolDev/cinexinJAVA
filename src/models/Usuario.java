package models;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Usuario {

	public static final int ROL_CLIENTE = 1;
	public static final int ROL_ADMINISTRATIVO = 2;
	String dni;
	String nombre;
	String apellidos;
	int rol;
	String direccion;
	Date fecha_nac;
	String email;
	String contrasena;
	String hash;
	Boolean verificado;
	Connection con;

	public Usuario(Connection con) {
		this.con = con;
	}
	
	public Usuario() {
		
	}
	
	
	
	
	
	public Usuario[] mostrarTodosUsuarios(  int limit, int offset) {
		Usuario[] usuarios;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		usuarios = new Usuario[limit];
		
		
		try {
			
				
			query="SELECT * FROM usuario ORDER BY nombre LIMIT ? OFFSET ?" ; 	

		
			stmt = con.prepareStatement(query);
			
			
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			
			rs = stmt.executeQuery();
			stmt.close();
	
			int indice = 0;

			while(rs.next()) {
				Usuario usuario;
				usuario = new Usuario();
				
				usuario.setDni(rs.getString(1));
				usuario.setNombre(rs.getString(2));
				usuario.setApellidos(rs.getString(3));
				usuario.setRol(rs.getInt(4));
				usuario.setDireccion(rs.getString(5));
				usuario.setFecha_nac(rs.getDate(6));
				usuario.setEmail(rs.getString(7));
				usuario.setVerificado(rs.getBoolean(10));
				
				usuarios[indice] = usuario;
				indice++;
				
			}
			
		}catch(SQLException e) {
			System.out.println("Error al recoger los datos de cliente");
			e.printStackTrace();
		}
			
		return usuarios;
	}	
	
	
	
	
	
	
	
	
	public Usuario LoginUsuario() {
		Usuario usuario;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		
		usuario = new Usuario();

		try {
			query="SELECT dni, verificado, rol FROM usuario WHERE email = ? AND contrasena = ?" ; 	
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, email);
			stmt.setString(2, contrasena);
			
			
			rs = stmt.executeQuery();
			
					
			if(rs.next()) {
				
				usuario.setDni(rs.getString(1));
				usuario.setVerificado(rs.getBoolean(2));
				usuario.setRol(rs.getInt(3));
				
				
				
			}
		} catch (SQLException e) {
			System.out.print("Error en el login del cliente");
			e.printStackTrace();
		}
		
		
		
		return usuario;
	}	
	
	public Usuario ConsultarUsuarioPorId() {
		Usuario usuario;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		usuario = new Usuario();
		
		try {

			
			query="SELECT dni, nombre, apellidos, rol, direccion, fecha_nac, email FROM usuario WHERE dni = ?" ; 	
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, dni);
			
			
			rs = stmt.executeQuery();
			
					
			if(rs.next()) {
				
				usuario.setDni(rs.getString(1));
				usuario.setNombre(rs.getString(2));
				usuario.setApellidos(rs.getString(3));
				usuario.setRol(rs.getInt(4));
				usuario.setDireccion(rs.getString(5));
				usuario.setFecha_nac(rs.getDate(6));
				usuario.setEmail(rs.getString(7));
				
				
				
			}
		} catch (SQLException e) {
			System.out.print("Error en el login del usuario");
			e.printStackTrace();
		}
		
		
		return usuario;
	}	
	
	
	
	public int ModificarUsuario() {
		int filasActualizadas;
		filasActualizadas = -1;
		
		try {

			String query="UPDATE usuario SET nombre = ? , apellidos = ? , direccion = ?  , fecha_nac = ?  WHERE dni = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, nombre);
			stmt.setString(2, apellidos);
			stmt.setString(3, direccion);
			stmt.setDate(4, fecha_nac);
			stmt.setString(5, dni);

			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			

		} catch (SQLException sqle) {
			System.err.println("Ha dado un error al ejecutar la query");
			sqle.printStackTrace();
		}
		
		
		
		return filasActualizadas;
		
		
	}

	
	public int Verificar() {
		int filasActualizadas;
		filasActualizadas = -1;
	
		try {
			String query="UPDATE usuario SET verificado = ? WHERE email = ? AND hash = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setBoolean(1, true);
			
			stmt.setString(2, email);
			stmt.setString(3, hash);
			
			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();		
			
		} catch (SQLException e) {
			System.out.print("Error al ejecutar la query");
			e.printStackTrace();
		}

				
		return filasActualizadas;
		
	}
	

	
	
	
	public int InsertarCliente() {
		int filasInsertadas;
		filasInsertadas = -1;


		
		try {

			String query="INSERT INTO usuario (dni, nombre, apellidos, rol, direccion, fecha_nac, email, contrasena, hash ) VALUES (? , ?  , ? , ? , ? , ? , ? , ? , ?  )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
		
			stmt.setString(1, dni);
			stmt.setString(2, nombre);
			stmt.setString(3, apellidos);
			stmt.setInt(4, rol);
			stmt.setString(5, direccion);
			stmt.setDate(6, fecha_nac);
			stmt.setString(7, email);
			stmt.setString(8, contrasena);
			stmt.setString(9, hash);

			
			
			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			

		} catch (SQLException sqle) {
			System.err.println("Ha dado un error al ejecutar la query");
			sqle.printStackTrace();
		}
		
		
		return filasInsertadas;
		
	}
	
	
	
	
	public int EliminarUsuario() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		
		try {


			query= "DELETE FROM usuario WHERE dni = ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, dni);
			filasEliminadas = stmt.executeUpdate();			
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return filasEliminadas;
		
	}
	
	

	
	

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public Date getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
