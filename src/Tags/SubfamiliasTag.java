package Tags;


import javax.servlet.jsp.tagext.*;

import modelos2dk.MSubfamilia;

import javax.servlet.jsp.*;
import java.io.*;

public class SubfamiliasTag extends SimpleTagSupport{

	private MSubfamilia[] subfamilias;
	private String tipo;


	public void setSubfamilias(MSubfamilia[] subfamilias) {
		this.subfamilias = subfamilias;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	      
	      
	      if(subfamilias != null && tipo != null) {
	    	  int indice;
	    	  switch(tipo) {
	    	  case "formulario":
	    		  out.print("<select name='subfamilia'>");
		    		indice = 0;
		    		while( indice < subfamilias.length ){
		    			if(subfamilias[indice] != null )
		    			out.print("<option value='"+subfamilias[indice].getId() +"'> "+subfamilias[indice].getNombre() +"</option>");
		    		
		    			indice++;
		    		}
		    		out.print("</select>");
		    		break;
	    	  case "tabla":
	    		  out.print("		<table class=\"styled-table\">\n"
	    		  		+ "			<thead>\n"
	    		  		+ "				<tr>\n"
	    		  		+ "					<th>id</th>\n"
	    		  		+ "					<th>nombre</th>\n"
	    		  		+ "					<th>familia</th>\n"
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "				</tr>\n"
	    		  		+ "			</thead>\n"
	    		  		+ "			<tbody>\n"
	    		  		+ "");

	    		  indice = 0;
	    		  while(indice < subfamilias.length)
	    		  	
	    		  {
	    		  	if(subfamilias[indice] != null){
	    		  		
	    		  		out.println("<tr>");
	    		  		out.println("<td>"+subfamilias[indice].getId()+"</td>");
	    		  		out.println("<td>"+subfamilias[indice].getNombre()+"</td>");
	    		  		out.println("<td>"+subfamilias[indice].getIdFamilia()+"</td>");
	    		  		out.println("<td>");
	    		  		out.println("<form method='post' action='eliminarsubfamilia'>");
	    		  		out.println("<input type='submit' value='Borrar'/>");
	    		  		out.println("<input type='hidden' value='" + subfamilias[indice].getId() + "' name='idSubfamilia' />");
	    		  		out.println("</form></td>");
	    		  		
	    		  		out.println("</td>");
	    		  		out.println("<td>");
	    		  		out.println("<form method='get' action='modificarsubfamilia'>");
	    		  		out.println("<input type='submit' value='Modificar'/>");
	    		  		out.println("<input type='hidden' value='" + subfamilias[indice].getId() + "' name='id' />");
	    		  		out.println("<input type='hidden' value='" + subfamilias[indice].getNombre() + "' name='nombre' />");
	    		  		out.println("<input type='hidden' value='" + subfamilias[indice].getIdFamilia() + "' name='familia' />");




	    		  		out.println("</form></td>");
	    		  		out.println("</tr>");
	    		  		
	    		  	}

	    		  	
	    		  	
	    		  	indice++;
	    		  	
	    		  }
	    		  
	    		  
		    		out.print("</tbody>\n"
		    				+ "</table>");
		    	  break;
	    	  default:
	    		  out.print("<small>No hay resultados para mostar</small>");
		    	  
		      }
		      
		      
		
	    	  }
	    	  
	    	 
	
	
	
	
	
	
}
}