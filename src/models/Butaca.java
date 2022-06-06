package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Butaca {
	
	String id;
	String id_sala;
	int tipo;
	int fila;
	String columna;
	Connection con;

	public static final int BUSQUEDA_SALA = 1;


	public Butaca(Connection con) {
		this.con = con;
	}
	
	public Butaca() {
		
	}
	
	
	public int insertarButaca() {
		
		int filasInsertadas;
		filasInsertadas = -1;
		
		try {
			
			String query="INSERT INTO butaca (id, id_sala,  tipo, fila, columna ) VALUES (? , ? , ? , ? , ? )"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1,id);
			stmt.setString(2, id_sala);
			stmt.setInt(3, tipo);
			stmt.setInt(4, fila);
			stmt.setString(5, columna);
			
			filasInsertadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle) {
			System.err.println("No se ha podido insertar la butaca");
			sqle.printStackTrace();
		}
		
		
		
		return filasInsertadas;
		
	}
	
	public int modificarButaca() {
		
		int filasActualizadas;
		filasActualizadas = -1;
		
		try {
			String query="UPDATE butaca SET id_sala = ? ,  tipo = ? , fila = ? , columna = ?   WHERE id = ?" ; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, id_sala);
			stmt.setInt(2, tipo);
			stmt.setInt(3, fila);
			stmt.setString(4, columna);
			stmt.setString(5, id);
			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			
			
		}catch(SQLException sqle){
			System.err.println("No se ha podido modificar la butaca");
			sqle.printStackTrace();
		}
		
		
		return filasActualizadas;
	}
	
	
	public Butaca[] ObtenerButacas(int limit, int offset, int busqueda) {
		Butaca[] butacas;
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		if(limit < 1) {
			limit = 1;
		}
		
		butacas = new Butaca[limit];
		
		try {


			switch(busqueda) {
				case BUSQUEDA_SALA: 
					query="SELECT * FROM butaca WHERE id_sala = ? LIMIT ? OFFSET ?  " ; 	
					break;

				default:
					query="SELECT * FROM butaca  LIMIT ? OFFSET ? " ; 	
					
				}
			
			
			stmt = con.prepareStatement(query);


			switch(busqueda) {
				case BUSQUEDA_SALA: 
					stmt.setString(1, id_sala);
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
				
				Butaca butaca;
				
				butaca = new Butaca();
				
				butaca.setId(rs.getString(1));
				butaca.setId_sala(rs.getString(2));
				butaca.setTipo(rs.getInt(3));
				butaca.setFila(rs.getInt(4));
				butaca.setColumna(rs.getString(5));

				
				butacas[indice] = butaca;
				
				indice++;
				
			}
			
	
		}catch(SQLException e) {
			System.out.println("Error al obtener las butacas");
			e.printStackTrace();
		}
		
		
		
		
		return butacas;
	}
	
	
	public Butaca obtenerButacaPorId() {
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		Butaca butaca;
		butaca = new Butaca();
		
		try {
			
			query="SELECT * FROM butaca where id = ?" ; 	
			
			stmt = con.prepareStatement(query);
			
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();
			stmt.close();
			
			if(rs.next()) {
				
				butaca.setId(rs.getString(1));
				butaca.setId_sala(rs.getString(2));
				butaca.setTipo(rs.getInt(3));
				butaca.setFila(rs.getInt(4));
				butaca.setColumna(rs.getString(5));
			}	
			
		}catch(SQLException e) {
			System.out.println("Error al obtener la butaca");
			e.printStackTrace();
		}
		
		
		
		
		return butaca;
	}
	
	
	
	
	
	public int eliminarButaca() {
		int filasEliminadas;
		filasEliminadas = -1;
		String query;
		PreparedStatement stmt;
		
		try {


			query= "DELETE FROM butaca WHERE id = ? ";
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
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM butaca";
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
	public String getNumeroRegistrosPorSala() {
		String numeroRegistros;
		numeroRegistros = "0";
		ResultSet rs;
		String query;
		PreparedStatement stmt;
		
		
		try {
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM butaca WHERE id_sala = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, id_sala);
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

	public String getId_sala() {
		return id_sala;
	}

	public void setId_sala(String id_sala) {
		this.id_sala = id_sala;
	}

	

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public String getColumna() {
		return columna;
	}

	public void setColumna(String columna) {
		this.columna = columna;
	}
	
	
	
	
	
	
	
	
	
}
