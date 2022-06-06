<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="javax.servlet.http.HttpSession"%>
    <%@ page import="java.util.Base64"%>
  <%@ page import="models.Pelicula" %>
  
<%

HttpSession sesion;
byte [] bfoto;
sesion = request.getSession();
Pelicula[] peliculas;
boolean error;
error = false;

if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 2) || sesion.getAttribute("rol") == null) {
	response.sendRedirect("/cinexin/");
}


int pagina;
pagina =1;
if(request.getAttribute("page") != null ){
	pagina = (int) request.getAttribute("page"); 
}

if(request.getAttribute("DeleteError") != null) {
	error =(boolean) request.getAttribute("DeleteError");
}

peliculas = null;
if (request.getAttribute("rs")!=null){
	
	peliculas = (Pelicula[])request.getAttribute("rs");
	
}

int numeroRegistros;
numeroRegistros= 0;
if(request.getAttribute("numeroRegistros") != null){
	numeroRegistros = (int) request.getAttribute("numeroRegistros");
}


%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cinexin 🎬 - Tu cine de confianza</title>
    <link rel="icon" type="image/x-icon" href="/cinexin/images/favicon.ico">
    <link rel="stylesheet" type="text/css" href="/cinexin/css/global.css" >
	    <link rel="stylesheet" type="text/css" href="/cinexin/css/style.css" >

</head>
<body >
<nav class='top-nav'>

<%
    


    if( sesion.getAttribute("rol") == null) {
        out.println("<a class='link-login' href='/cinexin/login.jsp'>Login</a> <a class='register-link' href='/cinexin/registro.jsp'> Registro</a>");
    
    }else if( (sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") == 2)){
                out.println("<a class='register-link' href='/cinexin/panelcontrol.jsp'>Panel de control</a>");
           	 	out.println("<a class='register-link' href='/cinexin/desconexion'>Cerrar sesion</a>");


    }else{
    	 out.println("<a class='register-link' href='/cinexin/desconexion'>Cerrar sesion</a>");
    }
    
    %>
    </nav>
<header>

    <div class="app-title">
        <a href="/cinexin/"><img class="logo" src="/cinexin/images/logo.png" alt="logo" srcset="" >
        </a> 
        <a href="/cinexin/" style="text-decoration: none"> <h1 class="web-title">Cinexin</h1></a>
    </div>
    <div class="forms-header">
 
    </div>
</header>

<main>


<article>
<div class="container">
<h1>Todas las peliculas</h1>


<%

if(peliculas != null){
	
	int indice;
	out.print("		<table class=\"styled-table\">\n"
	  		+ "			<thead>\n"
	  		+ "				<tr>\n"
	  		+ "					<th>id</th>\n"
			+ "					<th>imagen</th>"
	  		+ "					<th>nombre</th>\n"
	  		+ "					<th>duracion</th>\n"

	  		+ "					<th>director</th>\n"

	  		+ "					<th>trailer</th>\n"

	  		+ "					<th>categoria</th>\n"
	  		+ "					<th>actores</th>\n"
	  		+ "					<th>calificacion</th>\n"
			+ "					<th>sinopsis</th>\n"


	  		+ "					<th>&nbsp;</th>\n"
	  		+ "					<th>&nbsp;</th>\n"
	  		+ "				</tr>\n"
	  		+ "			</thead>\n"
	  		+ "			<tbody>");
	  

		indice = 0;
	while(indice < peliculas.length)
	{
		
		if(peliculas[indice] != null){
			bfoto = peliculas[indice].getImagen();
			
			out.println("<tr>");
			//Mostramos en cada celda td los datos correspondientes a su columna
			out.println("<td>"+peliculas[indice].getId()+"</td>");

			if(bfoto !=null){
				out.println("<td><img style='border-radius: 5px' border = '0' src = 'data:image/png;base64,"+Base64.getEncoder().encodeToString(peliculas[indice].getImagen())+ " ' width = '100px' height = '100px' ></td>");

			}else{
			out.println("<td><img style='border-radius: 5px' border = '0'  ' width = '200' height = '220' ></td>");

			}

			out.println("<td>"+peliculas[indice].getNombre()+"</td>");
			out.println("<td>"+peliculas[indice].getDuracion()+"</td>");
			out.println("<td>"+peliculas[indice].getDirector()+"</td>");
			out.println("<td> <iframe width='160' height='115' src='https://www.youtube.com/embed/" + peliculas[indice].getTrailer() + "' title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe></td>");
			out.println("<td>"+peliculas[indice].getCategoria()+"</td>");
			out.println("<td>"+peliculas[indice].getActores()+"</td>");
			out.println("<td>"+peliculas[indice].getCalificacion()+"</td>");
			out.println("<td>"+peliculas[indice].getSinopsis()+"</td>");




			out.println("<td>");
			out.println("<form method='post' action='/cinexin/administracion/eliminarpelicula'>");
			out.println("<input type='submit' value='Borrar'/>");
			out.println("<input type='hidden' value='" + peliculas[indice].getId() + "' name='id' />");
			out.println("</form></td>");
			
			//Para modificar el montadito llamamos a otro jsp pasandole el id de montadito
			out.println("</td>");
			out.println("<td>");
			out.println("<form method='get' action='/cinexin/administracion/modificarpelicula'>");
			out.println("<input type='submit' value='Modificar'/>");
			out.println("<input type='hidden' value='" + peliculas[indice].getId() + "' name='id' />");
			out.println("<input type='hidden' value='" + peliculas[indice].getNombre()+ "' name='nombre' />");
			out.println("<input type='hidden' value='" + peliculas[indice].getDuracion()+ "' name='duracion' />");
			out.println("<input type='hidden' value='" + peliculas[indice].getDirector()+ "' name='director' />");
			out.println("<input type='hidden' value='" + peliculas[indice].getTrailer()+ "' name='trailer' />");
			out.println("<input type='hidden' value='" + peliculas[indice].getCategoria()+ "' name='categoria' />");
			out.println("<input type='hidden' value='" + peliculas[indice].getActores()+ "' name='actores' />");
			out.println("<input type='hidden' value='" + peliculas[indice].getCalificacion()+ "' name='calificacion' />");
			out.println("<input type='hidden' value='" + peliculas[indice].getSinopsis()+ "' name='sinopsis' />");


			out.println("</form></td>");
			out.println("</tr>");
		}

		
		indice++;
	}


	out.print("</tbody>\n"
			+ "</table>");


	
	
}else{
	out.print("<h2>No hay peliculas para mostrar</h2>");
	out.print("<a href='/cinexin/administracion/peliculas'>Prueba de nuevo pulsando aquí.</a><br/>");
}



%>


<%

if(pagina > 1){
	out.print("<a href='/cinexin/administracion/peliculas?page="+(pagina - 1) +"'> &#60;</a>");
}

out.print("<small>pagina " + pagina + "</small>");


if(numeroRegistros > (5*pagina)){
out.println("<a href='/cinexin/administracion/peliculas?page="+(pagina + 1) +"'> &#62;</a> ");
}


 %>

</div>



</article>

</main>

<footer>

<div id="enlaces">
    <a href="/cinexin/quienes-somos.jsp">Quienes somos</a>
    <a href="/cinexin/contacto.jsp">Contacto</a>
    <a href="/cinexin/politica-privacidad.jsp">Politica privacidad</a>
</div>

    <p>© 2022 MayolDev, Inc. All rights reserved.</p>
</footer>

</body>
</html>