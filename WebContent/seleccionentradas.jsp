<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="models.Precioentradas"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.sql.Time"%>
<%@ page import="java.util.Base64"%>



<% 
HttpSession sesion;
sesion = request.getSession();
Precioentradas[] precios;

String nombre_pelicula, nombre_cine;
Time hora_entrada;
Date fecha;
byte [] bfoto;

nombre_pelicula = (String) sesion.getAttribute("nombre_pelicula");
nombre_cine = (String) sesion.getAttribute("nombre_cine");
hora_entrada = (Time) sesion.getAttribute("hora_entrada");
fecha = (Date) sesion.getAttribute("fecha");
bfoto = (byte[]) sesion.getAttribute("foto_pelicula");

precios = (Precioentradas[]) request.getAttribute("precios");


if(precios == null){

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
    <link rel="stylesheet" href="/cinexin/css/global.css">
    <link rel="stylesheet" href="/cinexin/css/seleccion.css">
</head>
<body>
    <header>
        <div class="app-title">
            <a href="/cinexin/"><img class="logo" src="/cinexin/images/logo.png" alt="logo" srcset="" >
            </a> 
            <a href="/cinexin/"> <h1 class="web-title">Cinexin</h1></a>
        </div>
        <div class="forms-header">
            
        </div>
    </header>
    <main>
        <article>
            <section>
                <h1>Selecciona tus entradas</h1>
                <p>Puedes comprar 4 entradas máximo por tipo de entrada y 8 por compra.</p>
                <div class="edad">
                    <span>NORMAL</span>
                    <div class="precio">
                        <span><%  out.print(precios[0].getPrecio()); %>€</span>
                        <form action="#">
                            <select name="cantidad-normal" id="cantidad-normal">
                                <option value="0">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                            </select>
                        </form>
                    </div>
                </div>
                <div class="edad">
                    <span>NIÑOS</span>
                    <div class="precio">
                        <span><%out.print(precios[1].getPrecio()); %>€</span>
                        <form action="#">
                            <select name="cantidad-nino" id="cantidad-nino">
                                <option value="0">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                            </select>
                        </form>
                    </div>
                </div>
            </section>
            <section>
                <h1>Resumen de la compra</h1>
                <div class="pelicula">
                                <% 
                
                if(bfoto !=null){
				    out.println("<img  src = 'data:image/png;base64,"+Base64.getEncoder().encodeToString(bfoto)+ " ' width = '120px' >");

                }else{
                    out.println("<img  width = '120px' >");

                }
                    
                %>
                    <div class="info">
                        <p><span>Película:</span> <% out.print(nombre_pelicula); %></p>
                        <p><span>Cine:</span> <% out.print(nombre_cine); %></p>
                        <p><span>Fecha:</span> <% out.print(fecha); %></p>
                        <p><span>Sesion:</span> <% out.print(hora_entrada); %></p>
                    </div>
                </div>
                <p>Se realizará un cargo por servicio por cada entrada dentro de la orden</p>
                <div class="total">
                    <h2>Total (IVA incluido) : </h2>
                    <h4 class="total-dinero">0€</h4>
                </div>
                <form action="/cinexin/seleccionbutacas" method="post">
                <input id="normal" name="normal" type="number" value=0 hidden/>
                <input id="ninos" name="ninos" type="number" value=0  hidden/>
                <button class="enviar" disabled=true>Continuar</button>
                </form>
            </section>
        </article>

    </main>
    <footer>
        created by @mayoldev
    </footer>

    <script src="/cinexin/scripts/seleccionentradas.js"></script>

</body>
</html>