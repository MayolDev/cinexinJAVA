<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="javax.servlet.http.HttpSession"%>
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
<nav class="top-nav"></nav>
<header>

    <div class="app-title">
        <a href="/cinexin/"><img class="logo" src="/cinexin/images/logo.png" alt="logo" srcset="" >
        </a> 
        <a href="/cinexin/" style="text-decoration: none"> <h1 class="web-title">Cinexin</h1></a>
    </div>
    <div class="forms-header">
       
    </div>
</header>

<% 
HttpSession sesion; 
sesion = request.getSession();


if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
	
	response.sendRedirect("/cinexin/");
	  
}



boolean error;
error = false;
String dni,nombre, apellidos, direccion,  email;

dni = "";
nombre = "";
apellidos = "";
direccion = "";
email="";


if(sesion.getAttribute("passwdError") != null || sesion.getAttribute("nombreError") != null || sesion.getAttribute("documentoError") != null || sesion.getAttribute("correoError") != null){
	if((boolean)sesion.getAttribute("passwdError") || (boolean)sesion.getAttribute("nombreError") || (boolean)sesion.getAttribute("documentoError") || (boolean)sesion.getAttribute("correoError")    ){
		error = true;
	}
	
	
}

if(error){
	if(request.getAttribute("dni") != null){
		dni = (String)request.getAttribute("dni");
	}
	
	if(request.getAttribute("nombre") != null){
		nombre = (String)request.getAttribute("nombre");
	}
	
	if(request.getAttribute("apellidos") != null){
		apellidos = (String)request.getAttribute("cp");
	}
	
	if(request.getAttribute("direccion") != null){
		direccion = (String)request.getAttribute("direccion");
	}


	
	if(request.getAttribute("email") != null){
		email = (String)request.getAttribute("email");
	}
	
}



%>
<main>
<article>
<h1 style='text-align: center'>Formulario de registro</h1>
<form style='display:flex; flex-direction:column; justify-content: center; align-items: center; padding: 10px' action= 'registro' method='post'>

<%
if(sesion.getAttribute("dniError") != null){
	if((boolean)sesion.getAttribute("dniError")){
		out.print("<small Style='color:red'>** DNI incorrecto o nulo ** </small>");
	}
}

%>
<label>DNI </label>
<input type='text' name='dni' value= '<%=dni%>'  />
<%

if(sesion.getAttribute("nombreError") != null){
	if((boolean)sesion.getAttribute("nombreError")){
		out.print("<small Style='color:red'>** Nombre incorrecto o nulo ** </small>");
	}
}

%>
<label>Nombre </label>
<input type='text' name='nombre' value= '<%=nombre%>'  />
<%
if(sesion.getAttribute("apellidosError") != null){
	if((boolean)sesion.getAttribute("apellidosError")){
		out.print("<small Style='color:red'>** apellidos incorrecto o nulo ** </small>");
	}
}

%>
<label>Apellidos</label>
<input type='text' name='apellidos' value= '<%=apellidos%>'/>
<%
if(sesion.getAttribute("direccionError") != null){
	if((boolean)sesion.getAttribute("direccionError")){
		out.print("<small Style='color:red'>** direccion incorrecta ** </small>");
	}
}

%>
<label>Direccion</label>
<input type='text' name='direccion' value= '<%=direccion%>'  />




<%
if(sesion.getAttribute("correoError") != null){
	if((boolean)sesion.getAttribute("correoError")){
		out.print("<small Style='color:red'>** Email incorrecto o nulo ** </small>");
	}
}

%>
<label>Correo electrónico</label>
<input type='email' name='email' value= '<%=email%> '/>




<%
if(sesion.getAttribute("passwdError") != null){
	if((boolean)sesion.getAttribute("passwdError")){
		out.print("<small Style='color:red'>** Contraseñas no coinciden, vuelva a escribirlas ** </small>");
	}
}

%>



<label>Contraseña</label>
<input type='password' name='contrasena' />
<label>Vuelva a introducir la contraseña</label>
<input type='password' name='contrasena2' />
<button>Enviar</button>
</form>





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