<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="javax.servlet.http.HttpSession"%>
 
 
 
 <%

HttpSession sesion;
sesion = request.getSession();


if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 2) || sesion.getAttribute("rol") == null) {
	response.sendRedirect("/cinexin/");
}


if(request.getParameter("id") == null){
	
	response.sendRedirect("/administracion/peliculas");
}

String id, nombre, director, trailer,  categoria, actores;
int duracion, calificacion;


id = (String)request.getAttribute("id");
nombre = (String)request.getAttribute("nombre");
director = (String)request.getAttribute("director");
trailer = (String)request.getAttribute("trailer");
categoria = (String)request.getAttribute("categoria");
actores = (String)request.getAttribute("actores");
duracion = (int)request.getAttribute("duracion");
calificacion = (int)request.getAttribute("calificacion");




request.setAttribute("id", id);


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
        <link rel="stylesheet" type="text/css" href="/cinexin/css/butacas.css" >


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
            <a href="/cinexin/index.jsp"><img class="logo" src="/cinexin/images/logo.png" alt="logo" srcset="" >
            </a> 
            <a href="/cinexin/index.jsp" style="text-decoration: none"> <h1 class="web-title">Cinexin</h1></a>
        </div>
        <div class="forms-header">

        </div>
    </header>
    
    <main>
    
    <article>


<form action="modificarpelicula" method="post">
<input type='hidden' value="<%=id %>" name='id' />
<label>Introduce nombre</label>
<input name="nombre" type="text" value="<%=nombre %>"/>
<label>Introduce duracion</label>
<input name="duracion" type="number" value="<%=duracion %>"/>
<label>Introduce director</label>
<input name="director" type="text" value="<%=director %>"/>
<label>Introduce trailer</label>
<input name="trailer" type="text" value="<%=trailer %>"/>
<label>Introduce categoria</label>
<input name="categoria" type="text" value="<%=categoria %>"/>
<label>Introduce actores</label>
<input name="actores" type="text" value="<%=actores %>"/>
<label>Introduce calificacion</label>
<input name="calificacion" type="number" value="<%=calificacion %>"/>

<button>Enviar</button>

</form>

</main>
</article>




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