<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<%
HttpSession sesion;

sesion = request.getSession();


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
    <link rel="stylesheet" type="text/css" href="/cinexin/css/other.css" >

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
    </nav><header>

    <div class="app-title">
        <a href="/cinexin/"><img class="logo" src="/cinexin/images/logo.png" alt="logo" srcset="" >
        </a> 
        <a href="/cinexin/" style="text-decoration: none"> <h1 class="web-title">Cinexin</h1></a>
    </div>
    <div class="forms-header">
       
    </div>
</header>
<body >

<main>

<article>

<h1>¿Quiénes somos?</h1>
<p>Somos una cadena de cines repartidos por España, que desde 2005 estamos brindando el mejor servicio a nuestros clientes, con unas proyecciones de calidad.</p>
<p>Nuestro primer cine abrió el 10 de junio de 2005, en gran vía Madrid.</p>
<img src="/cinexin/images/slider2.jpg" alt="Primer cine de nuestra cadena"  width="600px" />
<p>Actualmente damos servicio vía online desde esta web, dando la posibilidad a nuestros clientes de comprar las entradas para su película favorita de forma totalmente telemática, mediante un sencillo proceso de compra.</p>

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