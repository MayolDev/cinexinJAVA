package Tags;


import javax.servlet.jsp.tagext.*;

import modelos2dk.MSubfamilia;

import javax.servlet.jsp.*;
import java.io.*;

public class CategoriasTag extends SimpleTagSupport{

	private MSubfamilia[] subfamilias;


	public void setCategorias(MSubfamilia[] subfamilias) {
		this.subfamilias = subfamilias;
	}
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	      

	      if(subfamilias != null) {
	    	  	out.print("<select name=\"categoria\">\n"
	    	  			+ "<option value=\"\"> Todas </option>");
	    		int indice = 0;
	    		while(indice < subfamilias.length){
	    			
	    			if(subfamilias[indice] != null)
	    			out.println("<option value='"+subfamilias[indice].getId()+"'> "+subfamilias[indice].getNombre()+"  </option>");
	    			
	    			indice++;
	    			
	    		}
	    		out.print("</select>");
	    	  
	    	  
	      }else {
	    	  out.println("<p style='color:red'>No hay subfamilias que mostrar</p>");
	      }
	      
	      
	}
	
	
	
	
	
	
}
