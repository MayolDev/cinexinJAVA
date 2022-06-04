package Tags;


import javax.servlet.jsp.tagext.*;

import modelos2dk.MArticulo;

import javax.servlet.jsp.*;
import java.io.*;
import java.util.Base64;

public class ArticulosTag extends SimpleTagSupport{

	private MArticulo[] articulos;
	private String tipo;


	public void setArticulos(MArticulo[] articulos) {
		this.articulos = articulos;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	      

	      if(articulos != null && tipo != null) {
	    	  
	    	  
	    	  switch(tipo) {
	    	  
	    	  case "tabla":
	    		  
	    		  out.print("		<table class=\"styled-table\">\n"
	    		  		+ "			<thead>\n"
	    		  		+ "				<tr>\n"
	    		  		+ "					<th>codigo</th>\n"
	    		  		+ "					<th>ean</th>\n"
	    		  		+ "					<th>nombre</th>\n"
	    		  		+ "					<th>descripcion</th>\n"
	    		  		+ "					<th>tipoIva</th>\n"
	    		  		+ "					<th>pcd</th>\n"
	    		  		+ "					<th>pvp</th>\n"
	    		  		+ "					<th>stock</th>\n"
	    		  		+ "					<th>disponible</th>\n"
	    		  		+ "					<th>minimimo</th>\n"
	    		  		+ "					<th>subfamilia</th>\n"
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "				</tr>\n"
	    		  		+ "			</thead>\n"
	    		  		+ "			<tbody>");
	    		  
	    		  for(int i = 0; i < (articulos.length -1); i++)
	    		  {
	    		  	
	    		  if(articulos[i] != null){
	    		  		

	    		  	out.println("<tr>");
	    		  	out.println("<td>"+articulos[i].getCodigo()+"</td>");
	    		  	out.println("<td>"+articulos[i].getEan()+"</td>");
	    		  	out.println("<td>"+articulos[i].getNombre()+"</td>");
	    		  	out.println("<td>"+articulos[i].getDescripcion()+"</td>");
	    		  	out.println("<td>");
	    		  	switch (articulos[i].getTipoiva())
	    		  	{
	    		  	case 1:
	    		  		out.println("Reducido");
	    		  		break;
	    		  	case 2:
	    		  		out.println("Normal");
	    		  		break;
	    		  	default:
	    		  		out.println("No definido");
	    		  	}
	    		  	out.println("</td>");
	    		  	
	    		  	out.println("<td>"+articulos[i].getPcd()+"</td>");
	    		  	out.println("<td>"+articulos[i].getPvp()+"</td>");
	    		  	out.println("<td>"+articulos[i].getStock()+"</td>");
	    		  	out.println("<td>"+articulos[i].getDisponible()+"</td>");
	    		  	out.println("<td>"+articulos[i].getMinimimo()+"</td>");
	    		  	out.println("<td>"+articulos[i].getSubfamilia()+"</td>");
	    		  	out.println("<td>");
	    		  	out.println("<form method='post' action='eliminararticulo'>");
	    		  	out.println("<input type='submit' value='Borrar'/>");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getCodigo() + "' name='idArticulo' />");
	    		  	out.println("</form></td>");
	    		  	
	    		  	out.println("</td>");
	    		  	out.println("<td>");
	    		  	out.println("<form method='get' action='modificararticulo'>");
	    		  	out.println("<input type='submit' value='Modificar'/>");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getCodigo()+ "' name='codigo' />");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getEan() + "' name='ean' />");
	    		  	out.println("<input type='hidden' value='" +articulos[i].getNombre() + "' name='nombre' />");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getDescripcion() + "' name='descripcion' />");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getTipoiva() + "' name='tipoiva' />");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getPcd() + "' name='pcd' />");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getPvp() + "' name='pvp' />");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getStock() + "' name='stock' />");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getDisponible() + "' name='disponible' />");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getMinimimo() + "' name='minimimo' />");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getSubfamilia() + "' name='subfamilia' />");
	    		  	out.println("</form></td>");
	    		  	out.println("<td>");
	    		  	out.println("<form method='get' action='subirimagenarticulo.jsp'>");
	    		  	out.println("<input type='submit' value='Subir Imagen'/>");
	    		  	out.println("<input type='hidden' value='" + articulos[i].getCodigo()+ "' name='codigo' />");
	    		  	out.println("</form></td>");
	    		  	out.println("</tr>");

	    		  	
	    		  	}

	    		  	
	    		  	
	    		  }
	    		  
	    		  break;
	    		  
	    	  case "catalogo":
	    		  byte[] foto;
	    		  String bfoto;
	    		  
	    		  out.print("<div class=\"articulos\" style=\"display:flex; flex-wrap: wrap; justify-content: center; align-items:center; \">\n"
	    		  		+ "");
	    		  for(int i = 0; i < (articulos.length); i++)
	    		  {
	    		  	
	    		  	if(articulos[i] != null){
	    		  		
	    		  		foto = articulos[i].getImagen();
	    		  		if(foto != null){
	    		  		bfoto = "data:image/png;base64," + Base64.getEncoder().encodeToString(foto);
	    		  		}else{
	    		  			bfoto = "./image/imagen.webp";
	    		  		}
	    		  		out.println("<div class='articulo' style='width: 200px; margin: 10px; border-radius: 5px; padding: 10px; background-color:#d1d1d1; '>");
	    		  		out.println("<a style='display:flex; width: 100%; heigth: 100%; justify-content: center; align-items:center; flex-direction: column ' href='producto?codigo="+articulos[i].getCodigo()+"'>");
	    		  		if(bfoto != null)
	    		  		out.println("<img style='border-radius: 5px' border = '0' src = '"+ bfoto +"' width = '100' height = '120' >");
	    		  		out.println("<h3>"+articulos[i].getNombre()+"</h3>");
	    		  		out.println("<p>Precio : "+ articulos[i].getPvp() + "€ </p>");
	    		  		out.println("<small>Stock:  "+ articulos[i].getStock() +" </small>");
	    		  		out.println("</a>");

	    		  		out.println("</div>");
	    		  	}


	    		  }
	    		  out.print("</div>");
	    		  
	    		  
	    		  break;
	    	  
	    	  default:
	    		  out.print("<small>No hay resultados para mostar</small>");
	    	  	
	    	  }
	    	  
	    	  
	    	  
  		  	out.println("</tbody>"
		  			+ "</table>");
	    	
	      }
	      
	      
	}
	
	
	
	
	
	
}
