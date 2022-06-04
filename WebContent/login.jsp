<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
 <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>
<%
HttpSession sesion;
Date fechaSesion;
boolean loginError;
boolean verificationError;
loginError = false;
verificationError = false;
sesion = request.getSession();

if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
	
	response.sendRedirect("/cinexin/");
	  
}

fechaSesion = null;
if(sesion.getAttribute("fechalogin") != null){
	fechaSesion = (Date) sesion.getAttribute("fechalogin");

}
if(sesion.getAttribute("loginError") != null ){
	
	loginError = (boolean)sesion.getAttribute("loginError");
}

if(sesion.getAttribute("verificacionError") != null && (boolean)sesion.getAttribute("verificacionError")){
	verificationError= (boolean)sesion.getAttribute("verificacionError");
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

<h1>Formulario de Login</h1>

<ex:fechalogin fechaSesion="<%=fechaSesion %>"/>
<ex:error error="<%=loginError %>" errorMessage="Email o contraseña incorrectos...">

</ex:error>
<ex:error error="<%=verificationError %>" errorMessage="Cuenta no verificada, por favor, revisa tu email...">

</ex:error>

<form action="login" method="post">
<label>Introduce correo electrónico</label>
<input type="email" name="email" />
<label>Introduce contraseña:</label>
<input type="password" name="password" />
<button>Enviar</button>

</form>
<a href="registro.jsp">Registrarse</a>


</article>
</main>


</body>
</html>