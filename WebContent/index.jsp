<%@ page language="java" %>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <!--<meta charset="UTF-8"> -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cinexin 🎬 - Tu cine de confianza</title>
    <link rel="icon" type="image/x-icon" href="/cinexin/images/favicon.ico">
    <link rel="stylesheet" type="text/css" href="/cinexin/css/global.css" >
        <link rel="stylesheet" type="text/css" href="/cinexin/css/index.css" >

</head>
<body >
<nav class='top-nav'>

<%
    
    HttpSession sesion;
    sesion = request.getSession();

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
        <a href="index.jsp"><img class="logo" src="./images/logo.png" alt="logo" srcset="" >
        </a> 
        <a href="index.jsp" style="text-decoration: none"> <h1 class="web-title">Cinexin</h1></a>
    </div>
    <div class="forms-header">
        <form>
                <div class="form">
                <label>Provincia</label>
                <select name="provincia" id="provincia">
                    <option value="0">Selecciona provincia</option>
                </select>
            </div>
            <div class="form">
                <label>Ciudad</label>
                <select name="ciudad" id="ciudad">
                    <option value="0">Selecciona ciudad</option>
                </select>
            </div>

            <div class="form">
                <label>Cines cercanos</label>
                <select name="cine" id="cine">
                    <option value="0">Selecciona un cine</option>
                </select>
            </div>
            
        </form>
    </div>
</header>

<main>

<article>

<h1>Cádiz</h1>
<div id="cartelera"></div>


</article>
</main>

<script src="/cinexin/scripts/menu.js"></script>

</body>
</html>