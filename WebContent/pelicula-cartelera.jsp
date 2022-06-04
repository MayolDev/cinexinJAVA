<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="models.Pelicula"%>
<%@ page import="models.Cine"%>
<%@ page import="models.Sesion"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.util.stream.*"%>


    <%@ page import="java.util.Base64"%>



<% 

Pelicula pelicula;
Cine cine;
Sesion MSesion;
Sesion[] sesiones;
byte [] bfoto;


pelicula = (Pelicula)request.getAttribute("pelicula");

cine = (Cine)request.getAttribute("cine");
sesiones = (Sesion[])request.getAttribute("sesiones");
if(pelicula == null || cine == null){
    	response.sendRedirect("/cinexin/");

}


bfoto = pelicula.getImagen();



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
            <link rel="stylesheet" href="/cinexin/css/pelicula.css">


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
        <a href="/cinexin/"><img class="logo" src="/cinexin/images/logo.png" alt="logo" srcset="" >
        </a> 
        <a href="/cinexin/" style="text-decoration: none"> <h1 class="web-title">Cinexin</h1></a>
    </div>
    <div class="forms-header">
        
    </div>
</header>

<main>

<article>
    <section class="intro">
                <div class="pelicula">
                <% 
                
                if(bfoto !=null){
				    out.println("<img  src = 'data:image/png;base64,"+Base64.getEncoder().encodeToString(bfoto)+ " ' width = '120px' >");

                }else{
                    out.println("<img  width = '120px' >");

                }
                    
                %>
                    <div class="pelicula-right">
                        <h2><% out.print(pelicula.getNombre()); %></h2>
                        <span class="mini-description"><% out.print(pelicula.getNombre()); %> (EUA, 2021)</span>
                        <div class="tags">
                            <span class="tag">B</span>
                            <span class="tag"><% out.print(pelicula.getDuracion()); %> min</span>
                            <span class="tag"><% out.print(pelicula.getCategoria()); %></span>
                        </div>

                    </div>
                </div>
                <div class="horario">
                    <h2>Horario disponibles</h2>
                      
                    <form onsubmit="HandleClick(event)">
                    <label>Fecha</label>
                        <select name="fecha" id="fecha">
                            <option>Selecciona fecha</option>
                            <% 
                            
                            Date[] fechas;

                            fechas = new Date[sesiones.length];

                            int index = 0;
                             %>
                            <% for(Sesion Msesion: sesiones){
                                if(Msesion != null){
                                    fechas[index] = Msesion.getFecha();
                                    index = index + 1;
                                }
                               
                            } 
                                 for(int i = 0; i < fechas.length; i++)
                                    {
                                        for(int j = i + 1; j < fechas.length; j++)
                                        {
                                            if(i != j && fechas[i] != null && fechas[j] != null)
                                            {

                                                if(fechas[i].compareTo(fechas[j]) == 0)
                                                {
                                                fechas[j] = null;
                                                }
                                            }
                                        }
                                    }

                                    for(Date fecha: fechas){
                                        if(fecha != null){
                                            out.print("<option value='{\"fecha\":\""+fecha+"\",\"id_sala\":"+sesiones[0].getId_sala()+" , \"id_pelicula\":"+pelicula.getId()+"}'>"+ fecha +"</option>");
                                        }
                                    } 
                            
                            %>
                        </select>
                        <input type="submit" value="Enviar" >

                    </form>
                    
                    <span>Elige el horario que prefieras.</span>
                    <span>Cine: <% out.print(cine.getNombre());  %></span>
                    <span>ESP</span>
                    <form class="form-horario" action="/cinexin/seleccionentradas" method="post">
                        <select name="horas" id="horas">
                            <option >
                                Selecciona hora
                            </option>

                        </select>
                        <input style="cursor: pointer;" type="submit" class="entradas" value="Seleccionar entradas" disabled = true>
                    </form>
                </div>
            </section>
            <section>
                <div class="trailer">
                    <h3>Trailer</h3>
			    <iframe width='560' height='315' src='https://www.youtube.com/embed/<% out.print(pelicula.getTrailer()); %>' title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen > </iframe>
                </div>
                <div class="sinopsis">
                    <h3>Sinopsis</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse id velit sed enim molestie pretium sed nec nulla. Donec non augue dignissim, porta sem nec, pretium ante.</p>

                </div>
                <div class="creditos">
                    <h3>Créditos y reparto</h3>
                    <h4>Actores</h4>
                    <p><%out.print(pelicula.getActores());%></p>
                    <h4>Directores</h4>
                    <p><% out.print(pelicula.getDirector()); %></p>
                </div>
            </section>



</article>
</main>

<script src="/cinexin/scripts/pelicula-cartelera.js"></script>

</body>
</html>