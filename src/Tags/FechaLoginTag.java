package Tags;


import javax.servlet.jsp.tagext.*;


import javax.servlet.jsp.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class FechaLoginTag extends SimpleTagSupport{

	private Date fechaSesion;


	
	public void setFechaSesion(Date fechaSesion) {
		this.fechaSesion = fechaSesion;
	}
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	      Date fechaActual;
	      Calendar cal1;
	      Calendar cal2;

	      if(fechaSesion != null) {
	    	  
	    	  fechaActual = new Date();
	    		cal1 = Calendar.getInstance();
	    		cal2 = Calendar.getInstance();
	    		
	    		cal1.setTime(fechaActual);
	    		cal2.setTime(fechaSesion);
	    		
	    		if(cal1.before(cal2)){
	    			out.print("<h1>No puedes iniciar sesion hasta  "+cal2.getTime()+" </h1>");
	    		}
	    	  
	      }
	      
	      
	}
	
	
	
	
	
	
}
