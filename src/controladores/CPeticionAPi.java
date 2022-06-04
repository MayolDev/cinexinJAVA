package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		ObjectMapper mapeador;
		Parseamiento parse;
		parse = new Parseamiento();
		String peticion;
		Connection con;
	    sesion = request.getSession();
		con = (Connection)sesion.getAttribute("conexion");

		peticion = request.getParameter("peticion");
		
		mapeador= new ObjectMapper();
		
		PrintWriter pw=response.getWriter();
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    if(peticion == null) {
	    	
	    	pw.print(mapeador.writeValueAsString("error"));
	    	
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


			    	pw.print(mapeador.writeValueAsString(salas));

		    	}
		    	break;
			case "butaca":
				Butaca butaca;
				Butaca[] butacas;

				String id_sala;



				id_sala = request.getParameter("id_sala");



				if(id_sala == null ){
			    	pw.print(mapeador.writeValueAsString("error"));

				}else{
					butaca = new Butaca(con);
					butaca.setId_sala(id_sala);
					int numbutacas = parse.getInteger(butaca.getNumeroRegistrosPorSala());

					if(numbutacas <= 0){
						numbutacas = 1;
					}



					butacas = new Butaca[numbutacas];
					butacas = butaca.ObtenerButacas(numbutacas, 0, Butaca.BUSQUEDA_SALA);
					pw.print(mapeador.writeValueAsString(butacas));


				}

		    	
		    	
		    	 
		    	
		    	break;
			case "butacasocupadas":
				ButacaSesion horario;
				ButacaSesion[] horarios;

				String  strid_sesion;
				int id_sesion;

				strid_sesion = request.getParameter("id_sesion");

				if(strid_sesion == null || !comprobacion.checkInteger(strid_sesion) ){
			    	pw.print(mapeador.writeValueAsString("error"));

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
					pw.print(mapeador.writeValueAsString(horarios));


				}

				break;
			case "contenidocartelera":
				ContenidoCartelera contenido;
				ContenidoCartelera[] contenidos;


				String id_cartelera;

				id_cartelera = request.getParameter("id_cartelera");




				if(id_cartelera == null){
					pw.print(mapeador.writeValueAsString("error"));

				}else {

					contenido = new ContenidoCartelera(con);
					
					contenido.setId_cartelera(id_cartelera);

				int numeroContenido = parse.getInteger(contenido.getNumeroRegistrosPorCiudad());

				if(numeroContenido <= 0){
					numeroContenido = 1;
				}
				contenidos = new ContenidoCartelera[numeroContenido];

					contenidos = contenido.ObtenerContenido(numeroContenido, 0);

					pw.print(mapeador.writeValueAsString(contenidos));




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
					pw.print(mapeador.writeValueAsString("error"));

				}else{
					pelicula = new Pelicula(con);
					peliculas = new Pelicula[ids.length];

					peliculas = pelicula.ObtenerPeliculasPorIds(ids.length, 0, ids);

					pw.print(mapeador.writeValueAsString(peliculas));



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
					pw.print(mapeador.writeValueAsString("error"));

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
					pw.print(mapeador.writeValueAsString(MSesiones));


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
						pw.print(mapeador.writeValueAsString("error"));

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
						pw.print(mapeador.writeValueAsString(MSesiones2));


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

					pw.print(mapeador.writeValueAsString(provincias));

				break;
				case "ciudad":
				Ciudad ciudad;
				Ciudad[] ciudades;
				int id_provincia;
				String srtIdProvincia;

				srtIdProvincia = request.getParameter("id_provincia");

				if(srtIdProvincia == null || !comprobacion.checkInteger(srtIdProvincia)){
					pw.print(mapeador.writeValueAsString("error"));

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
	
						pw.print(mapeador.writeValueAsString(ciudades));

				}


			
				break;
				case "cine":
				Cine cine;
				Cine[] cines;
				String id_ciudad;

				id_ciudad = request.getParameter("id_ciudad");

				if(id_ciudad == null ){
					pw.print(mapeador.writeValueAsString("error"));

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
	
						pw.print(mapeador.writeValueAsString(cines));

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
					pw.print(mapeador.writeValueAsString("error"));

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

	
					pw.print(mapeador.writeValueAsString(precioFinal));

				}


				

				break;
		    default:
		    	pw.print(mapeador.writeValueAsString("No hay datos para mostrar"));

		    
		    }
	    	
	    }
	    

		
		
		
		
		
	}



}
