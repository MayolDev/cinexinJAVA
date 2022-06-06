<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ page import="javax.servlet.http.HttpSession"%>
    <%
    
    HttpSession sesion;
    sesion = request.getSession();


    if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 2) || sesion.getAttribute("rol") == null) {
    	response.sendRedirect("/cinexin/");
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
    <link rel="stylesheet" type="text/css" href="/cinexin/css/panelcontrol.css" >

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body >
<nav class="top-nav"></nav>
<header>
    <div class="app-title">
        <a href="/cinexin/"><img class="logo" src="/cinexin/images/logo.png" alt="logo" srcset="" >
        </a> 
        <a href="/cinexin/" style="text-decoration: none"> 
        <h1 class="web-title">Cinexin</h1>
        </a>
    </div>
    <div class="forms-header">
        
    </div>
</header>
<main>
<article>

<div class="botones">

<a href="/cinexin/administracion/provincias" target="_blank">Provincias</a>
<a href="/cinexin/administracion/ciudades" target="_blank">Ciudades</a>
<a href="/cinexin/administracion/cines" target="_blank">Cines</a>
<a href="/cinexin/administracion/peliculas" target="_blank">Peliculas</a>
<a href="/cinexin/administracion/carteleras" target="_blank">Carteleras</a>
<a href="/cinexin/administracion/butaca.jsp" target="_blank">Butacas</a>
<a href="/cinexin/administracion/contenido.jsp" target="_blank">Contenido Cartelera</a>
<a href="/cinexin/administracion/butacasocupadas.jsp" target="_blank">Butacas ocupadas</a>
<a href="/cinexin/administracion/sala.jsp" target="_blank">Salas</a>
<a href="/cinexin/administracion/sesiones.jsp" target="_blank">Sesiones</a>





</div>








<% 

Boolean insertado;
if(request.getAttribute("insertado") != null){
	insertado = (Boolean) request.getAttribute("insertado");
	
	if(insertado){
		out.println("<h2 style='color: green; text-align: center'>  Insertado con exito</h2>");
	}else{
		out.println("<h2 style='color: red; text-align: center'> Valor no insertado. Ha ocurrido un error</h2>");

	}
}



%>
<div class="accordion" id="accordionExample">
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingOne">
      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
         Añadir provincia
      </button>
    </h2>
    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
      <div class="accordion-body">
      <form action="anadirprovincia" method="post">
		<h1>Añadir provincia</h1>
		<label>Introduce id</label>
		<input name="id" type="text" value=""/>
		<label>Introduce nombre</label>
		<input name="nombre" type="text" value=""/>
		<button>Enviar</button>
		</form>
      </div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
			Añadir ciudad
      </button>
    </h2>
    <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <form action="anadirciudad" method="post">
			<h1>Añadir ciudad</h1>
			<label>Introduce cod Postal</label>
			<input name="cod_postal" type="text" value=""/>
			<label>Introduce id provincia</label>
			<input name="id_provincia" type="number" value=""/>
			<label>Introduce nombre</label>
			<input name="nombre" type="text" value=""/>
			<button>Enviar</button>
		</form>
	</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingThree">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
        Añadir cine
      </button>
    </h2>
    <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#accordionExample">
      <div class="accordion-body">
			<form action="anadircine" method="post">
				<h1>Añadir cine</h1>
				<label>Introduce id</label>
				<input name="id" type="text" value=""/>
				<label>Introduce id ciudad</label>
				<input name="id_ciudad" type="text" value=""/>
				<label>Introduce id cartelera</label>
				<input name="id_cartelera" type="text" value=""/>
				<label>Introduce nombre</label>
				<input name="nombre" type="text" value=""/>
				<label>Introduce coordenadas</label>
				<input name="coordenadas" type="text" value=""/>
				<label>Introduce disponible</label>
				<input name="disponible" type="checkbox" value="true" checked/>
				<button>Enviar</button>
			</form>      
			</div>
    </div>
  </div>
    <div class="accordion-item">
    <h2 class="accordion-header" id="headingFour">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
        Añadir pelicula
      </button>
    </h2>
    <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-bs-parent="#accordionExample">
      <div class="accordion-body">
		<form action="anadirpelicula" method="post" enctype="multipart/form-data">
			<h1>Añadir pelicula</h1>
      <input type=file size=60 name="file" value="Examinar">

			<label>Introduce id</label>
			<input name="id" type="text" value=""/>
			<label>Introduce nombre</label>
			<input name="nombre" type="text" value=""/>
			<label>Introduce duracion</label>
			<input name="duracion" type="text" value=""/>
			<label>Introduce director</label>
			<input name="director" type="text" value=""/>
			<label>Introduce trailer</label>
			<input name="trailer" type="text" value=""/>
			<label>Introduce categoria</label>
			<input name="categoria" type="text" value=""/>
			<label>Introduce actores</label>
			<input name="actores" type="text" value=""/>
			<label>Introduce calificación</label>
			<input name="calificacion" type="text" value=""/>
			<button>Enviar</button>
		</form>  
			</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingFive">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
        Añadir sala
      </button>
    </h2>
    <div id="collapseFive" class="accordion-collapse collapse" aria-labelledby="headingFive" data-bs-parent="#accordionExample">
      <div class="accordion-body">
		<form action="anadirsala" method="post">
			<h1>Añadir sala</h1>
			<label>Introduce id</label>
			<input name="id" type="text" value=""/>
			<label>Introduce id_cine</label>
			<input name="id_cine" type="text" value=""/>
			<label>Introduce nombre</label>
			<input name="nombre" type="text" value=""/>
			
			<button>Enviar</button>
		</form>
			</div>
    </div>
  </div>
  
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingSix">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
        Añadir butaca
      </button>
    </h2>
    <div id="collapseSix" class="accordion-collapse collapse" aria-labelledby="headingSix" data-bs-parent="#accordionExample">
      <div class="accordion-body">
	  <form action="anadirbutaca" method="post">
			<h1>Añadir butaca</h1>
			<label>Introduce id</label>
			<input name="id" type="text" value=""/>
			<label>Introduce id_sala</label>
			<input name="id_sala" type="text" value=""/>
			<label>Introduce tipo</label>
			<input name="tipo" type="number" value=""/>
			<label>Introduce fila</label>
			<input name="fila" type="number" value=""/>
			<label>Introduce columna</label>
			<input name="columna" type="text" value=""/>
			
			<button>Enviar</button>
	</form>
  
			</div>
    </div>
  </div>
  
    <div class="accordion-item">
    <h2 class="accordion-header" id="headingSeven">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSeven" aria-expanded="false" aria-controls="collapseSeven">
        Añadir cartelera
      </button>
    </h2>
    <div id="collapseSeven" class="accordion-collapse collapse" aria-labelledby="headingSeven" data-bs-parent="#accordionExample">
      <div class="accordion-body">
	 <form action="anadircartelera" method="post">
		<h1>Añadir cartelera</h1>
		<label>Introduce id</label>
		<input name="id" type="text" value=""/>
		<label>Introduce nombre</label>
		<input name="nombre" type="text" value=""/>
		<button>Enviar</button>
	</form>
		  
			</div>
    </div>
  </div>
  
      <div class="accordion-item">
    <h2 class="accordion-header" id="headingEight">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseEight" aria-expanded="false" aria-controls="collapseEight">
        Añadir contenido cartelera
      </button>
    </h2>
    <div id="collapseEight" class="accordion-collapse collapse" aria-labelledby="headingEight" data-bs-parent="#accordionExample">
      <div class="accordion-body">
		<form action="anadircontenidocartelera" method="post">
			<h1>Añadir contenido cartelera</h1>
			<label>Introduce id_cartelera</label>
			<input name="id_cartelera" type="text" value=""/>
			<label>Introduce id_pelicula</label>
			<input name="id_pelicula" type="text" value=""/>
			<button>Enviar</button>
		</form>
		  
			</div>
    </div>
  </div>
  
        <div class="accordion-item">
    <h2 class="accordion-header" id="headingNine">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseNine" aria-expanded="false" aria-controls="collapseNine">
        Añadir sesión
      </button>
    </h2>
    <div id="collapseNine" class="accordion-collapse collapse" aria-labelledby="headingNine" data-bs-parent="#accordionExample">
      <div class="accordion-body">
		<form action="anadirsesion" method="post">
			<h1>Añadir sesion</h1>
			<label>Introduce id</label>
			<input name="id" type="number" value=""/>
			<label>Introduce id_pelicula</label>
			<input name="id_pelicula" type="text" value=""/>
      <label>Introduce id_sala</label>
			<input name="id_sala" type="text" value=""/>
      <label>Introduce fecha</label>
			<input name="fecha" type="date" value=""/>
      <label>Introduce hora_entrada</label>
			<input name="hora_entrada" type="time" />
			<label>Introduce hora_salida</label>
			<input name="hora_salida" type="time" />
			<button>Enviar</button>
		</form>
		  
			</div>
    </div>
  </div>
          <div class="accordion-item">
    <h2 class="accordion-header" id="headingTen">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTen" aria-expanded="false" aria-controls="collapseTen">
        Añadir Butaca sesion
      </button>
    </h2>
    <div id="collapseTen" class="accordion-collapse collapse" aria-labelledby="headingTen" data-bs-parent="#accordionExample">
      <div class="accordion-body">
		<form action="anadirhorariosesion" method="post">
			<h1>Añadir Butaca sesion</h1>
			<label>Introduce id</label>
			<input name="id" type="number" value=""/>
			<label>Introduce id_butaca</label>
			<input name="id_butaca" type="text" value=""/>
      <label>Introduce id_sesion</label>
			<input name="id_sesion" type="number" value=""/>
      <label>Introduce id_ticket</label>
			<input name="id_ticket" type="text" value=""/>


			<button>Enviar</button>
		</form>
		  
			</div>
    </div>
  </div>

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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>