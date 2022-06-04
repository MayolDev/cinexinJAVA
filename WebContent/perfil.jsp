<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
String documento, nombre, direccion, direccion1, codigopostal, localidad, ciudad, correo, telefono, bfoto;
documento = "";
nombre = "";
direccion = "";
direccion1= "";
codigopostal = "";
localidad = "";
ciudad = "";
correo = "";
telefono = "";
bfoto= "";

if(request.getAttribute("correo") == null){
	response.sendRedirect("perfil");
}else {
	documento = (String)request.getAttribute("documento");
	nombre = (String)request.getAttribute("nombre");
	direccion = (String)request.getAttribute("direccion");
	direccion1 = (String)request.getAttribute("direccion1");
	codigopostal = (String)request.getAttribute("codigopostal");
	localidad = (String)request.getAttribute("localidad");
	ciudad = (String)request.getAttribute("ciudad");
	correo = (String)request.getAttribute("correo");
	telefono = (String)request.getAttribute("telefono");
	bfoto = (String)request.getAttribute("bfoto");
}



%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Perfil</title>
</head>
<body>

<h1>Bienvenido a tu perfil, <%=nombre %></h1>
<div style="display:flex; flex-direction: column;  align-items: center">
<img style='border-radius: 5px' border = '0' src = 'data:image/png;base64,<%=bfoto %>  ' width = '200' height = '220' >
<a href="subirimagen">Editar imagen</a>

<h2>Datos de usuario</h2>
<p>Documento: <strong><%=documento  %></strong></p>
<p>Direccion: <strong><%=direccion %></strong></p>
<p>Direccion adicional: <strong><%=direccion1 %></strong></p>
<p>Codigo postal: <strong><%=codigopostal %></strong></p>
<p>Localidad: <strong><%=localidad %></strong></p>
<p>Ciudad: <strong><%=ciudad %></strong></p>
<p>Correo: <strong><%=correo %></strong></p>
<p>Telefono: <strong><%=telefono %></strong></p>

<a href="editarperfil">Editar Perfil</a>
<form action='desconexion' method='get'>
<button>Desconectarse</button>
</form>
</div>



</body>
</html>