package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.*;
import models.Sala;
import models.Sesion;
import utils.Comprobaciones;
import utils.Parseamiento;
import models.Butaca;
import models.ContenidoCartelera;
import models.ButacaSesion;
import models.Cine;
import models.Ciudad;
import models.Pelicula;
import models.Precioentradas;
import models.Provincia;


/**
 * Servlet implementation class CPeticionAPi
 */
@WebServlet("/api")
public class CPeticionAPi extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		Parseamiento parse;
		parse = new Parseamiento();
		String peticion;
		Connection con;
	    sesion = request.getSession();
		con = (Connection)sesion.getAttribute("conexion");
		peticion = request.getParameter("peticion");
		DateFormat format = new SimpleDateFormat("HH:mm");
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		PrintWriter pw=response.getWriter();

	    
	    if(peticion == null) {
			JSONObject jsonObj;
	    	jsonObj = new JSONObject();
	    	jsonObj.put("error", "error");
	    	pw.print(jsonObj);
	    	
	    }else {
	    	
		    switch(peticion) {
		    
		    case "sala":
		    	Sala sala;
		    	Sala[] salas;
		    	
		    	String id_cine;
		    	
		    	id_cine = request.getParameter("id_cine");


		    	
		    	if(id_cine == null) {
		    		
		    	}else {
			    	sala = new Sala(con);
		    		sala.setId_cine(id_cine);

					int numeroSalas = parse.getInteger(sala.getNumeroRegistrosPorSesion());
					if(numeroSalas <= 0){
						numeroSalas = 1;
					}

			    	salas = new Sala[numeroSalas];
			    	
			    	salas = sala.ObtenerSalas(numeroSalas, 0, Sala.BUSQUEDA_CINE);
			    	
					JSONObject salasJson;
					
					JSONArray arrSalas = new JSONArray();   
					
					for(Sala unaSala: salas) {
						salasJson = new JSONObject();
						salasJson.put("id", unaSala.getId());
						salasJson.put("id_cine",unaSala.getId_cine() );
						salasJson.put("nombre", unaSala.getNombre());
						arrSalas.add(salasJson);
						
					}


			    	pw.print(arrSalas);
			    	

		    	}
		    	break;
				case "butaca":
				Butaca butaca;
				Butaca[] butacas;

				String id_sala;



				id_sala = request.getParameter("id_sala");



				if(id_sala == null ){
			    	JSONObject jsonObj;
					jsonObj = new JSONObject();
					jsonObj.put("error", "error");
					pw.print(jsonObj);

				}else{
					butaca = new Butaca(con);
					butaca.setId_sala(id_sala);
					int numbutacas = parse.getInteger(butaca.getNumeroRegistrosPorSala());

					if(numbutacas <= 0){
						numbutacas = 1;
					}



					butacas = new Butaca[numbutacas];
					butacas = butaca.ObtenerButacas(numbutacas, 0, Butaca.BUSQUEDA_SALA);

					JSONObject ButacaJson;
					
					JSONArray arrButacas = new JSONArray();   
					
					for(Butaca unaButaca: butacas) {
						ButacaJson = new JSONObject();
						ButacaJson.put("id", unaButaca.getId());
						ButacaJson.put("id_sala",unaButaca.getId_sala() );
						ButacaJson.put("tipo", unaButaca.getTipo());
						ButacaJson.put("fila", unaButaca.getFila());
						ButacaJson.put("columna", unaButaca.getColumna());

						arrButacas.add(ButacaJson);
						
					}

					pw.print(arrButacas);


				}

		    	
		    	
		    	 
		    	
		    	break;
				case "butacasocupadas":
				ButacaSesion horario;
				ButacaSesion[] horarios;

				String  strid_sesion;
				int id_sesion;

				strid_sesion = request.getParameter("id_sesion");

				if(strid_sesion == null || !comprobacion.checkInteger(strid_sesion) ){
			    	JSONObject jsonObj;
					jsonObj = new JSONObject();
					jsonObj.put("error", "error");
					pw.print(jsonObj);
				}else{
					id_sesion = parse.getInteger(strid_sesion);
					horario = new ButacaSesion(con);
					horario.setId_sesion(id_sesion);

					int numeroButacasOcupadas = parse.getInteger(horario.getNumeroRegistrosPorSesion());

					if(numeroButacasOcupadas <= 0){
						numeroButacasOcupadas = 1;		
					}



					horarios = new ButacaSesion[numeroButacasOcupadas];
					horarios = horario.ObtenerHorarios(numeroButacasOcupadas, 0, ButacaSesion.BUSQUEDA_SESION);

					if(horarios[0] != null){


						JSONObject ButacaOcupadaJson;
					
						JSONArray arrButacasOcupadas = new JSONArray();   
						
						for(ButacaSesion unaButaca: horarios) {
	
							ButacaOcupadaJson = new JSONObject();
							ButacaOcupadaJson.put("id_butaca", unaButaca.getId_butaca());
							ButacaOcupadaJson.put("id_sesion",unaButaca.getId_sesion() );
							ButacaOcupadaJson.put("id_ticket", unaButaca.getId_ticket());
	
	
							arrButacasOcupadas.add(ButacaOcupadaJson);
							
						}
						pw.print(arrButacasOcupadas);
	
	


					}else{
						JSONObject jsonObj;
						jsonObj = new JSONObject();
						jsonObj.put("error", "error");
						pw.print(jsonObj);
					}



				}

				break;
				case "contenidocartelera":
				ContenidoCartelera contenido;
				ContenidoCartelera[] contenidos;


				String id_cartelera;

				id_cartelera = request.getParameter("id_cartelera");




				if(id_cartelera == null){
					JSONObject jsonObj;
					jsonObj = new JSONObject();
					jsonObj.put("error", "error");
					pw.print(jsonObj);

				}else {

					contenido = new ContenidoCartelera(con);
					
					contenido.setId_cartelera(id_cartelera);

				int numeroContenido = parse.getInteger(contenido.getNumeroRegistrosPorCiudad());

				if(numeroContenido <= 0){
					numeroContenido = 1;
				}
				contenidos = new ContenidoCartelera[numeroContenido];

					contenidos = contenido.ObtenerContenido(numeroContenido, 0);
					JSONObject contenidoJSON;
					
					JSONArray arrContenidos = new JSONArray();   
					
					for(ContenidoCartelera contenido1: contenidos) {
						contenidoJSON = new JSONObject();
						contenidoJSON.put("id_cartelera", contenido1.getId_cartelera());
						contenidoJSON.put("id_pelicula",contenido1.getId_pelicula() );


						arrContenidos.add(contenidoJSON);
						
					}

					pw.print(arrContenidos);



				}
				break;
				case "pelicula":
				Pelicula pelicula;
				Pelicula[] peliculas;

				String[] ids;
				String cadena;

				ids = request.getParameterValues("ids_peliculas");

				cadena = ids[0];

				ids = cadena.split(",");
				

				if(ids.length <= 0){
					JSONObject jsonObj;
					jsonObj = new JSONObject();
					jsonObj.put("error", "error");
					pw.print(jsonObj);

				}else{
					pelicula = new Pelicula(con);
					peliculas = new Pelicula[ids.length];

					peliculas = pelicula.ObtenerPeliculasPorIds(ids.length, 0, ids);

					JSONObject peliculaJSON;
					
					JSONArray arrPeliculas = new JSONArray();   
					
					for(Pelicula pelicula1: peliculas) {
						peliculaJSON = new JSONObject();
						peliculaJSON.put("id", pelicula1.getId());
						peliculaJSON.put("nombre",pelicula1.getNombre() );
						peliculaJSON.put("duracion",pelicula1.getDuracion() );
						peliculaJSON.put("director",pelicula1.getDirector() );
						peliculaJSON.put("trailer",pelicula1.getTrailer() );
						peliculaJSON.put("categoria",pelicula1.getCategoria() );
						peliculaJSON.put("actores",pelicula1.getActores() );
						peliculaJSON.put("calificacion",pelicula1.getCalificacion() );
						peliculaJSON.put("sinopsis",pelicula1.getSinopsis() );

						peliculaJSON.put("imagen",Base64.getEncoder().encodeToString(pelicula1.getImagen()));



						arrPeliculas.add(peliculaJSON);
						
					}
					pw.print(arrPeliculas);




				}


			break;
			case "sesion":
				Sesion MSesion;
				Sesion[] MSesiones;

				String id_sala2, strfecha;
				Date fecha;

				id_sala2 = request.getParameter("id_sala");
				strfecha = request.getParameter("fecha");

				if(id_sala2 == null || strfecha == null || !comprobacion.checkDate(strfecha)){
					JSONObject jsonObj;
					jsonObj = new JSONObject();
					jsonObj.put("error", "error");
					pw.print(jsonObj);

				}else {
					fecha = Date.valueOf(strfecha);
					MSesion = new Sesion(con);
					MSesion.setId_sala(id_sala2);
					MSesion.setFecha(fecha);
					int numeroSesiones =  parse.getInteger(MSesion.getNumeroRegistrosPorSala()) ;

					if(numeroSesiones <= 0){
						numeroSesiones = 1;
					}

					MSesiones = new Sesion[numeroSesiones];
					MSesiones = MSesion.ObtenerSesion(numeroSesiones, 0, Sesion.BUSQUEDA_SALA);
					JSONObject sesionJSON;
					
					JSONArray arrSesiones = new JSONArray();   
					
					for(Sesion sesion1: MSesiones) {
						sesionJSON = new JSONObject();
						sesionJSON.put("id", sesion1.getId());
						sesionJSON.put("id_pelicula",sesion1.getId_pelicula() );
						sesionJSON.put("id_sala",sesion1.getId_sala() );
						sesionJSON.put("fecha","" +sesion1.getFecha() + ""  );
						sesionJSON.put("hora_entrada",format.format(sesion1.getHora_entrada().getTime()) );
						sesionJSON.put("hora_salida",format.format(sesion1.getHora_salida().getTime()));



						arrSesiones.add(sesionJSON);
						
					}
					System.out.println(arrSesiones);
					pw.print(arrSesiones);


				}


				break;

				case "sesion2":
					Sesion MSesion2;
					Sesion[] MSesiones2;

					String id_sala3, strfecha2, id_pelicula;
					Date fecha2;

					id_pelicula = request.getParameter("id_pelicula");
					id_sala3 = request.getParameter("id_sala");
					strfecha2 = request.getParameter("fecha");

					if(id_sala3 == null || strfecha2 == null || id_pelicula == null  || !comprobacion.checkDate(strfecha2)){
						JSONObject jsonObj;
						jsonObj = new JSONObject();
						jsonObj.put("error", "error");
						pw.print(jsonObj);
					}else {
						fecha2 = Date.valueOf(strfecha2);
						MSesion2 = new Sesion(con);
						MSesion2.setId_sala(id_sala3);
						MSesion2.setFecha(fecha2);
						MSesion2.setId_pelicula(id_pelicula);
						int numeroSesiones =  parse.getInteger(MSesion2.getNumeroRegistrosPorSalayPelicula()) ;

						if(numeroSesiones <= 0){
							numeroSesiones = 1;
						}

						MSesiones2 = new Sesion[numeroSesiones];
						MSesiones2 = MSesion2.ObtenerSesion(numeroSesiones, 0, Sesion.BUSQUEDA_PELICULA_SALA);
						JSONObject sesion2JSON;
					
						JSONArray arrSesiones2 = new JSONArray();   
						
						for(Sesion sesion2: MSesiones2) {
							sesion2JSON = new JSONObject();
							sesion2JSON.put("id", sesion2.getId());
							sesion2JSON.put("id_pelicula",sesion2.getId_pelicula() );
							sesion2JSON.put("id_sala",sesion2.getId_sala() );
							sesion2JSON.put("fecha", "'" + sesion2.getFecha() +"'" );
							sesion2JSON.put("hora_entrada",format.format(sesion2.getHora_entrada().getTime()) );
							sesion2JSON.put("hora_salida",format.format(sesion2.getHora_salida().getTime()) );
	
	
	
							arrSesiones2.add(sesion2JSON);
							
						}

						pw.print(arrSesiones2);


					}


				break;
				case "provincia":
				Provincia provincia;
				Provincia[] provincias;

				provincia = new Provincia(con);
				int numeroProvincias;
				numeroProvincias = parse.getInteger(provincia.getNumeroRegistros());

				if(numeroProvincias <= 0){
					numeroProvincias = 1;
				}

				provincias = new Provincia[numeroProvincias];
				provincias = provincia.ObtenerProvincias(numeroProvincias, 0);

					JSONObject provinciasJson;
					
					JSONArray arrProvincias = new JSONArray();   
					
					for(Provincia unaProvincia: provincias) {
						provinciasJson = new JSONObject();
						provinciasJson.put("id_provincia", unaProvincia.getId_provincia());
						provinciasJson.put("nombre", unaProvincia.getNombre());

						arrProvincias.add(provinciasJson);
						
					}
					

				    
					pw.print(arrProvincias);

				break;

				case "ciudad":
				Ciudad ciudad;
				Ciudad[] ciudades;
				int id_provincia;
				String srtIdProvincia;

				srtIdProvincia = request.getParameter("id_provincia");

				if(srtIdProvincia == null || !comprobacion.checkInteger(srtIdProvincia)){
					JSONObject jsonObj;
					jsonObj = new JSONObject();
					jsonObj.put("error", "error");
					pw.print(jsonObj);
				}else{

					id_provincia = parse.getInteger(srtIdProvincia);
					ciudad = new Ciudad(con);
					ciudad.setId_provincia(id_provincia);
					int numeroCIudades;
					numeroCIudades = parse.getInteger(ciudad.getNumeroRegistrosPorProvincia());
	
					if(numeroCIudades <= 0){
						numeroCIudades = 1;
					}
	
					ciudades = new Ciudad[numeroCIudades];
					ciudades = ciudad.ObtenerCiudades(numeroCIudades, 0, Ciudad.BUSQUEDA_PROVINCIA);
	
						
					JSONObject ciudadJson;
					
					JSONArray arrCiudades = new JSONArray();   
					
					for(Ciudad unaCiudad: ciudades) {
						ciudadJson = new JSONObject();
						ciudadJson.put("cod_postal", unaCiudad.getCod_postal());
						ciudadJson.put("id_provincia", unaCiudad.getId_provincia());
						ciudadJson.put("nombre", unaCiudad.getNombre());
						arrCiudades.add(ciudadJson);
						
					}

					pw.print(arrCiudades);

				}


			
				break;
				case "cine":
				Cine cine;
				Cine[] cines;
				String id_ciudad;

				id_ciudad = request.getParameter("id_ciudad");

				if(id_ciudad == null ){
					JSONObject jsonObj;
					jsonObj = new JSONObject();
					jsonObj.put("error", "error");
					pw.print(jsonObj);

				}else{

					cine = new Cine(con);
					cine.setId_ciudad(id_ciudad);
					int numeroCines;
					numeroCines = parse.getInteger(cine.getNumeroRegistrosPorCiudad());
	
					if(numeroCines <= 0){
						numeroCines = 1;
					}
	
					cines = new Cine[numeroCines];
					cines = cine.ObtenerCines(numeroCines, 0, Cine.BUSQUEDA_CIUDAD);
	
					JSONObject cineJson;
					
					JSONArray arrCines = new JSONArray();   
					
					for(Cine unCine: cines) {
						cineJson = new JSONObject();
						cineJson.put("id", unCine.getId());
						cineJson.put("id_ciudad", unCine.getId_ciudad());
						cineJson.put("id_cartelera", unCine.getId_cartelera());
						cineJson.put("nombre", unCine.getNombre());
						cineJson.put("coordenadas", unCine.getCoordenadas());
						cineJson.put("disponible", unCine.getDisponible());
						arrCines.add(cineJson);
						
					}

					pw.print(arrCines);

				}


				

				break;
				case "precio":
				Precioentradas precio;
				Precioentradas[] precios;
				int cantidad_nino;
				int cantidad_normal;
				long precioFinal;


				if(request.getParameter("cantidad_normal") == null || request.getParameter("cantidad_nino") == null 
				|| !comprobacion.checkInteger(request.getParameter("cantidad_nino")) || !comprobacion.checkInteger(request.getParameter("cantidad_normal"))){
					JSONObject jsonObj;
					jsonObj = new JSONObject();
					jsonObj.put("error", "error");
					pw.print(jsonObj);

				}else{
					cantidad_nino = parse.getInteger(request.getParameter("cantidad_nino"));
					cantidad_normal = parse.getInteger(request.getParameter("cantidad_normal")); 
					
					precio = new Precioentradas(con);
					int numeroPrecios = parse.getInteger(precio.getNumeroRegistros());
					if(numeroPrecios <=0){
						numeroPrecios = 1;
					}

					precios = new Precioentradas[numeroPrecios];

					precios = precio.ObtenerPrecio(numeroPrecios, 0);

					precioFinal = (cantidad_nino * precios[1].getPrecio()) + (cantidad_normal * precios[0].getPrecio());

	
					pw.print(precioFinal);

				}


				

				break;


		    default:
		    	JSONObject jsonObj;
		    	jsonObj = new JSONObject();
		    	jsonObj.put("error", "error");
		    	pw.print(jsonObj);

		    
		    }
	    	
	    }
	    

		
		
		
		
		
	}



}
