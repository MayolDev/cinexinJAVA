<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="javax.servlet.http.HttpSession"%>
  <%@ page import="models.Cine" %>
  
<%

HttpSession sesion;
sesion = request.getSession();
Cine[] cines;
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

cines = null;
if (request.getAttribute("rs")!=null){
	
	cines = (Cine[])request.getAttribute("rs");
	
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
        <a href="/cinexin/"><img class="logo" src="../images/logo.png" alt="logo" srcset="" >
        </a> 
        <a href="/cinexin/" style="text-decoration: none"> <h1 class="web-title">Cinexin</h1></a>
    </div>
    <div class="forms-header">

    </div>
</header>
<main>

<article>

<div class="container">
<h1>Todos los cines</h1>


<%

if(cines != null){
	
	int indice;
	out.print("		<table class=\"styled-table\">\n"
	  		+ "			<thead>\n"
	  		+ "				<tr>\n"
	  		+ "					<th>id</th>\n"
	  		+ "					<th>id_ciudad</th>\n"
	  		+ "					<th>id_cartelera</th>\n"
	  		+ "					<th>nombre</th>\n"
	   		+ "					<th>coordenadas</th>\n"
	  		+ "					<th>disponible</th>\n"
	  		+ "					<th>&nbsp;</th>\n"
	  		+ "					<th>&nbsp;</th>\n"
	  		+ "				</tr>\n"
	  		+ "			</thead>\n"
	  		+ "			<tbody>");
	  

		indice = 0;
	while(indice < cines.length)
	{
		
		if(cines[indice] != null){
			
			out.println("<tr>");
			//Mostramos en cada celda td los datos correspondientes a su columna
			out.println("<td>"+cines[indice].getId()+"</td>");
			out.println("<td>"+cines[indice].getId_ciudad()+"</td>");
			out.println("<td>"+cines[indice].getId_cartelera()+"</td>");
			out.println("<td>"+cines[indice].getNombre()+"</td>");
			out.println("<td>"+cines[indice].getCoordenadas()+"</td>");
			out.println("<td>"+cines[indice].getDisponible()+"</td>");
			out.println("<td>");
			out.println("<form method='post' action='/cinexin/administracion/eliminarcine'>");
			out.println("<input type='submit' value='Borrar'/>");
			out.println("<input type='hidden' value='" + cines[indice].getId() + "' name='id' />");
			out.println("</form></td>");
			
			//Para modificar el montadito llamamos a otro jsp pasandole el id de montadito
			out.println("</td>");
			out.println("<td>");
			out.println("<form method='get' action='/cinexin/administracion/modificarcine'>");
			out.println("<input type='submit' value='Modificar'/>");
			out.println("<input type='hidden' value='" + cines[indice].getId()+ "' name='id' />");
			out.println("<input type='hidden' value='" + cines[indice].getId_ciudad() + "' name='id_ciudad' />");
			out.println("<input type='hidden' value='" + cines[indice].getId_cartelera() + "' name='id_cartelera' />");
			out.println("<input type='hidden' value='" + cines[indice].getNombre() + "' name='nombre' />");
			out.println("<input type='hidden' value='" + cines[indice].getCoordenadas() + "' name='coordenadas' />");
			out.println("<input type='hidden' value='" + cines[indice].getDisponible() + "' name='disponible' />");




			out.println("</form></td>");
			out.println("</tr>");
		}

		
		indice++;
	}


	out.print("</tbody>\n"
			+ "</table>");


	
	
}else{
	out.print("<h2>No hay cines para mostrar</h2>");
	out.print("<a href='/cinexin/administracion/cines'>Prueba de nuevo pulsando aquí.</a><br/>");
}



%>


<%

if(pagina > 1){
	out.print("<a href='/cinexin/administracion/cines?page="+(pagina - 1) +"'> &#60;</a>");
}

out.print("<small>pagina " + pagina + "</small>");


if(numeroRegistros > (5*pagina)){
out.println("<a href='/cinexin/administracion/cines?page="+(pagina + 1) +"'> &#62;</a> ");
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