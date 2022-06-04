<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
               <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Verificacion</title>
</head>
<body>
<%
HttpSession sesion;
boolean error;
error = false;

sesion = request.getSession();

if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
	
	response.sendRedirect("/cinexin/");
	  
}

if((sesion.getAttribute("verificacionError") != null) ){
	error = (boolean)sesion.getAttribute("verificacionError");
}


%>
<ex:error error="<%=error %>" errorMessage="Ha ocurrido un error durante la verificacion. Contacte con soporte o intentelo de nuevo más tarde."> 
Su cuenta ha sido verificada correctamente.
</ex:error>
<p>Haga click <a href="login"> Aquí</a> para ir al login</p>




</body>
</html>