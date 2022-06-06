<%@ page language="java" %>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.sql.Time"%>
<%@ page import="java.util.Base64"%>
<%@ page import="java.util.*"%>

<%

    HttpSession sesion;
    sesion = request.getSession();

if(sesion.getAttribute("cantidad_nino") == null || sesion.getAttribute("cantidad_normal") == null || sesion.getAttribute("preciototal") == null 
|| sesion.getAttribute("id_sala") == null || sesion.getAttribute("nombre_pelicula") == null || sesion.getAttribute("nombre_cine") == null 
|| sesion.getAttribute("hora_entrada") == null || sesion.getAttribute("fecha") == null || sesion.getAttribute("foto_pelicula") == null 
|| sesion.getAttribute("id_sesion") == null || sesion.getAttribute("nombre_sala") == null || sesion.getAttribute("butacas") == null || sesion.getAttribute("posicionButacas") == null){
    response.sendRedirect("/cinexin/");
}

long preciototal;

preciototal = (long) sesion.getAttribute("preciototal");

String nombre_pelicula, nombre_cine, nombre_sala, id_sala, butacas, posicionButacas;
Time hora_entrada;
java.sql.Date fecha;
java.util.Date timerDate;
byte [] bfoto;
int cantidad_nino, cantidad_normal, id_sesion, cantidad_total;
String error = "";
cantidad_nino = (int) sesion.getAttribute("cantidad_nino");
cantidad_normal = (int) sesion.getAttribute("cantidad_normal");
id_sala = (String) sesion.getAttribute("id_sala");
butacas = (String) sesion.getAttribute("butacas");
nombre_pelicula = (String) sesion.getAttribute("nombre_pelicula");
nombre_cine = (String) sesion.getAttribute("nombre_cine");
hora_entrada = (Time) sesion.getAttribute("hora_entrada");
fecha = (java.sql.Date) sesion.getAttribute("fecha");
bfoto = (byte[]) sesion.getAttribute("foto_pelicula");
nombre_sala = (String) sesion.getAttribute("nombre_sala");
posicionButacas = (String)sesion.getAttribute("posicionButacas");
id_sesion = (int) sesion.getAttribute("id_sesion");
cantidad_total = cantidad_nino + cantidad_normal;

%>


<!DOCTYPE html>
<html lang="es">
<head>
    <!--<meta charset="UTF-8"> -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cinexin 🎬 - Tu cine de confianza</title>
    <link rel="icon" type="image/x-icon" href="/cinexin/images/favicon.ico">
    <link rel="stylesheet" href="/cinexin/css/global.css">
    <link rel="stylesheet" href="/cinexin/css/descarga.css">
    <link rel="stylesheet" href="/cinexin/css/seleccion-entradas.css">



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
<section>
                <img class="transaccion-img" src="/cinexin/images/transaccion.png" alt="">
            </section>
            <section>
                <h1>Entradas</h1>
                <div class="pelicula">
                                        <%
                       if(bfoto !=null){
				    out.println("<img  src = 'data:image/png;base64,"+Base64.getEncoder().encodeToString(bfoto)+ " ' width = '120px' >");

                }else{
                    out.println("<img  width = '120px' >");

                }
                    
                    %>
                    <div class="info">
                        <p><span>Película:</span> <%=nombre_pelicula%></p>
                        <p><span>Cine:</span> <%=nombre_cine%></p>
                        <p><span>Fecha:</span> <%=fecha%></p>
                        <p><span>Sesion:</span> <%=hora_entrada%></p>
                        <p><span>Entradas:</span> <% out.print(cantidad_normal); %> Adultos , <% out.print(cantidad_nino); %> niños</p>
                        <p><span>Número de sala:</span> <% out.print(nombre_sala); %></p>
                        <p><span>Asientos:</span> <% out.print(posicionButacas); %></p>
                    </div>
                </div>
                <form action="generarentrada" method="post">
                                <button id="enviar">Descargar entradas</button>  
                </form>
            </section>
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
<script type="text/javascript" src="https://cdn.weglot.com/weglot.min.js"></script>

<script>
    Weglot.initialize({
        api_key: 'wg_57eb3e2a1f5c2734a5fc4ba30aec9df54'
    });
</script>
</body>
</html>