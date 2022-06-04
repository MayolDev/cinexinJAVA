package Tags;


import javax.servlet.jsp.tagext.*;

import modelos2dk.MArticulo;

import javax.servlet.jsp.*;
import java.io.*;
import java.util.Base64;

public class ProductoTag extends SimpleTagSupport{

	private MArticulo articulo;
	private String atributo;


	public void setArticulo(MArticulo articulo) {
		this.articulo = articulo;
	}
	
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	  	if(articulo != null){
	  		
	  		switch(atributo) {
	  		
	  		case "titulo":
	  			out.print(articulo.getNombre());
	  			break;
	  		case "descripcion":
	  			out.print(articulo.getDescripcion());
	  			break;
	  		case "pcd":
	  			out.print(articulo.getPcd());

	  			break;
	  		case "pvp":
	  			out.print(articulo.getPvp());

	  			break;
	  		case "stock":
	  			out.print(articulo.getStock());
	  			break;
	  		case "foto":
	  			byte[] foto = articulo.getImagen();
	  			String bfoto;
	  			bfoto = "./image/imagen.webp";
				if(foto != null){
					bfoto = "data:image/png;base64," + Base64.getEncoder().encodeToString(foto);

				}
				out.print("<img style='border-radius: 5px' border = '0' src = '"+bfoto+"' width = '300' height = '320' >");

	  		
	  			break;
	  		default:
	  			out.print("No hay resultados para mostrar");
			


		}

	      
	      
	}
	
	
	
	
	
	
	}
	}
