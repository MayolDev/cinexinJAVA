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
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js"></script> 


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
<h1 id="elige"> <i class="fa-solid fa-arrow-up-long"></i> Elige tu cine en el menú de arriba  <i class="fa-solid fa-arrow-up-long"></i></h1>
<div id="cartelera">
</div>

<div class="wrapper">
        <input checked type=radio name="slider" id="slide1" />
        <input type=radio name="slider" id="slide2" />
        <input type=radio name="slider" id="slide3" />
        <input type=radio name="slider" id="slide4" />
        <input type=radio name="slider" id="slide5" />
      
        <div class="slider-wrapper">
          <div class=inner>
            <article>
              <div class="info top-left">
                <h3>Ven y diviertete en Cinexin</h3></div>
              <img src="./images/slider1.jpg" />
            </article>
      
            <article>
              <div class="info bottom-right">
                <h3>Los mejores cines en tu ciudad</h3></div>
              <img src="./images/slider2.jpg" />
            </article>
      
            <article>
              <div class="info bottom-left">
                <h3>Ven con la mejor compañia</h3></div>
              <img src="./images/slider3.jpg" />
            </article>
      
            <article>
              <div class="info top-right">
                <h3>Los mejores estrenos para ti</h3></div>
              <img src="./images/slider4.jpg" />
            </article>
      
            <article>
              <div class="info bottom-left">
                <h3>Disfruta del buen cine, en Cinexin</h3></div>
              <img src="./images/slider5.jpg" />
            </article>
          </div>
          <!-- .inner -->
        </div>
        <!-- .slider-wrapper -->
      
        <div class="slider-prev-next-control">
          <label for=slide1></label>
          <label for=slide2></label>
          <label for=slide3></label>
          <label for=slide4></label>
          <label for=slide5></label>
        </div>
        <!-- .slider-prev-next-control -->
      
        <div class="slider-dot-control">
          <label for=slide1></label>
          <label for=slide2></label>
          <label for=slide3></label>
          <label for=slide4></label>
          <label for=slide5></label>
        </div>
        <!-- .slider-dot-control -->
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
<script type="text/javascript" charset="UTF-8" src="/cinexin/scripts/menu.js"></script>

</body>
</html>