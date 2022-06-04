package Tags;


import javax.servlet.jsp.tagext.*;

import modelos2dk.MFamilia;

import javax.servlet.jsp.*;
import java.io.*;

public class FamiliasTag extends SimpleTagSupport{

	private MFamilia[] familias;
	private String tipo;


	public void setFamilias(MFamilia[] familias) {
		this.familias = familias;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      int indice;
	      

	      if(familias != null && tipo != null) {
	    	  
	    	  switch(tipo) {
	    	  
	    	  case "formulario":
		    	  out.print("<select name='familia'>");
		    		indice=0;
		    		while( indice < familias.length ){
		    			if(familias[indice] != null)
		    			out.print("<option value='"+familias[indice].getId() +"'> "+familias[indice].getNombre() +"</option>");
		    		
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
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "				</tr>\n"
	    		  		+ "			</thead>\n"
	    		  		+ "			<tbody>");
	    		  

	    			indice = 0;
	    		while(indice < familias.length)
	    		{
	    			
	    			if(familias[indice] != null){
	    				
	    				out.println("<tr>");
	    				//Mostramos en cada celda td los datos correspondientes a su columna
	    				out.println("<td>"+familias[indice].getId()+"</td>");
	    				out.println("<td>"+familias[indice].getNombre()+"</td>");
	    				out.println("<td>");
	    				out.println("<form method='post' action='eliminarfamilia'>");
	    				out.println("<input type='submit' value='Borrar'/>");
	    				out.println("<input type='hidden' value='" + familias[indice].getId() + "' name='idFamilia' />");
	    				out.println("</form></td>");
	    				
	    				//Para modificar el montadito llamamos a otro jsp pasandole el id de montadito
	    				out.println("</td>");
	    				out.println("<td>");
	    				out.println("<form method='get' action='modificarfamilia'>");
	    				out.println("<input type='submit' value='Modificar'/>");
	    				out.println("<input type='hidden' value='" + familias[indice].getId()+ "' name='id' />");
	    				out.println("<input type='hidden' value='" + familias[indice].getNombre() + "' name='nombre' />");



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
