package controladores;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;

import utils.Encriptador;

/**
 * Servlet implementation class CGenerarEntrada
 */
@WebServlet("/generarentrada")
public class CGenerarEntrada extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/cinexin/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		if(sesion.getAttribute("hash") != null || sesion.getAttribute("id_sesion") != null 
		|| sesion.getAttribute("nombre_pelicula") != null || sesion.getAttribute("nombre_sala") != null
		|| sesion.getAttribute("fecha") != null || sesion.getAttribute("hora_entrada") != null
		|| sesion.getAttribute("cantidad_nino") != null || sesion.getAttribute("cantidad_normal") != null
		|| sesion.getAttribute("posicionButacas") != null || sesion.getAttribute("nombre_cine") != null){
			String hash, nombre_pelicula, nombre_sala, posicionButacas, nombre_cine;
			Date fecha;
			Time hora_entrada;
			hash = (String)sesion.getAttribute("hash");
			nombre_pelicula = (String) sesion.getAttribute("nombre_pelicula");
			nombre_sala = (String)sesion.getAttribute("nombre_sala");
			fecha = (Date)sesion.getAttribute("fecha");
			hora_entrada = (Time)sesion.getAttribute("hora_entrada");
			int id_sesion, cantidad_nino, cantidad_normal;
			cantidad_nino = (int)sesion.getAttribute("cantidad_nino");
			cantidad_normal = (int)sesion.getAttribute("cantidad_normal");
			posicionButacas = (String)sesion.getAttribute("posicionButacas");
			id_sesion = (int)sesion.getAttribute("id_sesion");
			nombre_cine = (String)sesion.getAttribute("nombre_cine");
			OutputStream out = response.getOutputStream(); /* Get the output stream from the response object */
			try {
		   
	   
					
			//Invoke BarcodeQRCode class to create QR Code for the calendar
			BarcodeQRCode my_code = new BarcodeQRCode("id_sesion=" + id_sesion + "&hash=" + hash, 1, 1, null);
			
			//PDF option is selected. Generate QR code into a PDF docuement and send this response to the output.
		   
					response.setContentType("application/pdf");
					Document document = new Document();            
					PdfWriter.getInstance(document, out);
					document.open();
					document.add(new Paragraph("Tus entradas para " + nombre_pelicula + " en la fecha:  " + fecha + " en el cine: " + nombre_cine));           
					document.add(my_code.getImage());
					document.add(new Paragraph("En la Sala " + nombre_sala + " en la sesion de las " + hora_entrada ));    
					document.add(new Paragraph("Entradas para " + cantidad_nino + " niños y " + cantidad_normal + "adultos." ));           
					document.add(new Paragraph("Sus asientos son: " + posicionButacas ));           

					
					
					document.close();
			
			
   
					
			}
			catch (Exception e) {
					System.err.println(e.toString()); /* Throw exceptions to log files */
			}
			finally {
					out.close();/* Close the output stream */
			}
		   

		}

		
	}

}
