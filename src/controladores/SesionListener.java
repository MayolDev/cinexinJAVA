package controladores;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import models.ConexionBD;

/**
 * Application Lifecycle Listener implementation class SesionListener
 *
 */
@WebListener
public class SesionListener implements HttpSessionListener {

	HttpSession sesion;
	Connection con;
	ConexionBD conexion;
	
	//SesionListener para establecer parametros de Sesion, ademas de la conexion a la base de datos
    public void sessionCreated(HttpSessionEvent se)  { 
        
		conexion = new ConexionBD("ns3034756.ip-91-121-81.eu/a21_ima?currentSchema=cinexin", "a21_ima", "a21_ima");
		con = conexion.conectar();
		
		
        sesion = se.getSession();
        
        sesion.setMaxInactiveInterval(20*60);
        
        sesion.setAttribute("conexion", con);
           
        
    	
    }
    
    public void sessionDestroyed(HttpSessionEvent se)  { 

        try {
            sesion = se.getSession();   
            con = (Connection)sesion.getAttribute("conexion");
            con.close();
		} catch (SQLException e) {
			System.out.println("Error al cerrar conexion con la base de datos");
			e.printStackTrace();
		}
   }

	
}
