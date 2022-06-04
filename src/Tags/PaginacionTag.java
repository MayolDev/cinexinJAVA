package Tags;


import javax.servlet.jsp.tagext.*;


import javax.servlet.jsp.*;
import java.io.*;

public class PaginacionTag extends SimpleTagSupport{
	
	private int pagina;
	private int numeroRegistros;
	private int categoria;
	private String busqueda;
	private String orden;
	private String href;



	public void setPagina(int pagina) {
		this.pagina = pagina;
	}



	public void setNumeroRegistros(int numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}



	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}



	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
	
	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	public void setHref(String href) {
		this.href=href;
	}



	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	      if(categoria >-1 && busqueda == null && orden == null) {
	    	  	
	    	  if(pagina > 1){
	    			out.print("<a href='"+href+"?page="+(pagina - 1) +"'> &#60;</a>");
	    		}

	    		out.print("<small>pagina " + pagina + "</small>");
	    		
	    		
	    		if(numeroRegistros > (5*pagina)){
	    		out.println("<a href='"+href+"?page="+(pagina + 1) +"'> &#62;</a> ");
	    		}
	    	  
	    	  
	      }else {
	    	  
	    	  if(pagina > 1){
	    			out.print("<a href='"+href+"?orden="+orden+"&page="+(pagina - 1) +"&categoria="+categoria+"&busqueda="+busqueda+"'> &#60;</a>");
	    		}

	    		out.print("<small>Mostrando página " + pagina + "</small>");




	    		if(numeroRegistros > (5*pagina)){
	    			out.println("<a href='"+href+"?orden="+orden+"&page="+(pagina + 1) +"&categoria="+categoria+"&busqueda="+busqueda+"'> &#62;</a> ");

	    		}

	    	  
	    	  
	    	  
	      }
	      
	     
	
	
	
}
	
}