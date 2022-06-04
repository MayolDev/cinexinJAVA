package controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import models.Pelicula;
import models.Usuario;
import utils.Comprobaciones;
import utils.Parseamiento;


/**
 * Servlet implementation class anadirprovincia
 */
@WebServlet("/anadirpelicula")
@MultipartConfig
public class Canadirpelicula extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    Pelicula pelicula;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    sesion = request.getSession();
	    
	    if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
	    	
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);
	    }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		sesion = request.getSession();
		con = (Connection)sesion.getAttribute("conexion");
		pelicula = new Pelicula(con);
		Parseamiento parse;
		parse = new Parseamiento();
		String id, nombre, director, trailer, categoria, actores, strduracion, strcalificacion;
		int duracion, calificacion;
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();

		id= "";
		nombre = "";
		director = "";
		trailer = "";
		categoria = "";
		actores = "";
		strduracion = "";
		strcalificacion = "";
		duracion = 0;
		calificacion = 0;

		
		//imagen

			File file;
			int maxFileSize;
			int maxMemSize;
			String filePath;
			FileItem fi;
			List <FileItem> fileItems;
			DiskFileItemFactory factory;
			String fileName;
	    	FileInputStream fis;


		file=null;
		maxFileSize = 5000 * 1024;
		maxMemSize= 5000 * 1024;
		fileName=null;
		filePath = "data";

		factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("."));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax( maxFileSize );





		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != Usuario.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("index.jsp");
	    	
	    }else {
	    	
		


			try{ 


				// Parse the request to get file items.
				fileItems = upload.parseRequest(request);


				// Process the uploaded file items
				Iterator<FileItem> i = fileItems.iterator();
				

	
				while ( i.hasNext () ) {

				   fi = (FileItem)i.next();
				   if ( !fi.isFormField () ){
	

	
				   		fileName = fi.getName();
	
				   
				   		if( fileName.lastIndexOf("/") >= 0 ){
					   		file = new File( filePath + 
					   		fileName.substring( fileName.lastIndexOf("/"))) ;
				   		}else{
					   		file = new File( filePath + "/" +
					   		fileName.substring(fileName.lastIndexOf("/")+1)) ;
				   			}
				   fi.write( file ) ;
				   }else {

				   		}
					



				}

				for( FileItem parametro:fileItems){
					if(parametro.isFormField()){

						switch(parametro.getFieldName()){
							case "id":
								id = parametro.getString();
								break;
							case "nombre":
								nombre = parametro.getString();
								break;
							case "duracion":
								strduracion = parametro.getString();
								break;
							case "director":
								director = parametro.getString();
								break;
							case "trailer":
								trailer = parametro.getString();
								break;
							case "categoria":
								categoria = parametro.getString();
								break;
							case "actores":
								actores = parametro.getString();
								break;
							case "calificacion":
								strcalificacion = parametro.getString();
								break;
						}



					}
				}
				
				
						
				if( !comprobacion.checkInteger(strduracion) || !comprobacion.checkInteger(strcalificacion) 
				|| nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 120) 
				|| director.contentEquals("") || !comprobacion.checkStringBetween(director, 1, 120)
				|| trailer.contentEquals("")
				|| categoria.contentEquals("") || !comprobacion.checkStringBetween(categoria, 1, 30)
				|| actores.contentEquals("")  )  {
			
						request.setAttribute("insertado", false);
						request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

				}else {
					
					duracion = parse.getInteger(strduracion);
					calificacion = parse.getInteger(strcalificacion);
					fis = new FileInputStream(file);



					pelicula.setId(id);
					pelicula.setNombre(nombre);
					pelicula.setDuracion(duracion);
					pelicula.setDirector(director);
					pelicula.setTrailer(trailer);
					pelicula.setCategoria(categoria);
					pelicula.setActores(actores);
					pelicula.setCalificacion(calificacion);
					pelicula.setFis(fis);
					pelicula.setFile(file);



					if(pelicula.insertarPelicula() != -1) {
						
						request.setAttribute("insertado", true);
						file.delete();
						
					}else {
						
						request.setAttribute("insertado", false);

					}
					

					request.getRequestDispatcher("panelcontrol.jsp").forward(request, response);

					
				}
					
					
					
					

	
			 }catch(Exception ex) {
				System.out.println(ex);
			 }




			
			
	    }
		
	}

}
