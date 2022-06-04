<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        <link rel="stylesheet" type="text/css" href="/cinexin/css/horario.css" >


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
            <a href="/cinexin/index.jsp"><img class="logo" src="/cinexin/images/logo.png" alt="logo" srcset="" >
            </a> 
            <a href="/cinexin/index.jsp" style="text-decoration: none"> <h1 class="web-title">Cinexin</h1></a>
        </div>
        <div class="forms-header">

        </div>
    </header>
    
    <main>
    
    <article>
    <form onSubmit="HandleEvent(event)">

    <label>Introduzca id_sesion</label>
    <input class="id_sesion" type="text" value="">    
    <button> Buscar </button>

    </form>


<table class="mostrartabla styled-table">

</table>

</article>
</main>

<script src="/cinexin/scripts/horario.js"></script>



</body>
</html>