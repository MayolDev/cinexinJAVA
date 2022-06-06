<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="models.Precioentradas"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.sql.Time"%>
<%@ page import="java.util.Base64"%>
<%@ page import="java.util.*"%>


<% 
HttpSession sesion;
sesion = request.getSession();

if(sesion.getAttribute("cantidad_nino") == null || sesion.getAttribute("cantidad_normal") == null || sesion.getAttribute("preciototal") == null 
|| sesion.getAttribute("id_sala") == null || sesion.getAttribute("nombre_pelicula") == null || sesion.getAttribute("nombre_cine") == null 
|| sesion.getAttribute("hora_entrada") == null || sesion.getAttribute("fecha") == null || sesion.getAttribute("foto_pelicula") == null 
|| sesion.getAttribute("id_sesion") == null || sesion.getAttribute("nombre_sala") == null || sesion.getAttribute("butacas") == null || sesion.getAttribute("posicionButacas") == null){
    response.sendRedirect("/cinexin/");
}

long preciototal;

preciototal = (long) sesion.getAttribute("preciototal");

String nombre_pelicula, nombre_cine, nombre_sala, id_sala, butacas, posicionButacas;
Time hora_entrada;
java.sql.Date fecha;
java.util.Date timerDate;
byte [] bfoto;
int cantidad_nino, cantidad_normal, id_sesion, cantidad_total;
String error = "";
cantidad_nino = (int) sesion.getAttribute("cantidad_nino");
cantidad_normal = (int) sesion.getAttribute("cantidad_normal");
id_sala = (String) sesion.getAttribute("id_sala");
butacas = (String) sesion.getAttribute("butacas");
nombre_pelicula = (String) sesion.getAttribute("nombre_pelicula");
nombre_cine = (String) sesion.getAttribute("nombre_cine");
hora_entrada = (Time) sesion.getAttribute("hora_entrada");
fecha = (java.sql.Date) sesion.getAttribute("fecha");
bfoto = (byte[]) sesion.getAttribute("foto_pelicula");
nombre_sala = (String) sesion.getAttribute("nombre_sala");
posicionButacas = (String) sesion.getAttribute("posicionButacas");
id_sesion = (int) sesion.getAttribute("id_sesion");
cantidad_total = cantidad_nino + cantidad_normal;
if(request.getAttribute("error") != null){
    error = (String)request.getAttribute("error");
}
timerDate = (java.util.Date) sesion.getAttribute("timerdate");


%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cinexin 🎬 - Tu cine de confianza</title>
    <link rel="icon" type="image/x-icon" href="/cinexin/images/favicon.ico">
    <link rel="stylesheet" href="/cinexin/css/global.css">
    <link rel="stylesheet" href="/cinexin/css/pasarelapago.css">

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
             <div id="clockdiv"></div>
             <form action="#" method=post>
             <h1>Información personal</h1>
             <p>Completa los datos del formulario para realizar el pago.</p>
             <div class="forms-celds">
                <span class="errorEmail" style="color:red;"></span>
                <label>Correo electrónico</label>
                <input class="email" type="text" placeholder="test@test.com" />
             </div>
             <div class="forms-celds">
                <div class="tarjeta">
                    <label>Información de la tarjeta  </label>
                    <img src="/cinexin/images/Visa.png" width="20px" height="20px" />
                    <img src="/cinexin/images/Mastercard.png" width="20px" height="20px" />
                    <img src="/cinexin/images/AmericanExpress.png" width="20px" height="20px" />
                    <img src="/cinexin/images/Discover.png" width="20px" height="20px" />
                </div>
                <span class="errorTarjeta" style="color:red;"></span>
                <input id="numeroTarjeta" type="text" placeholder="1234 1234 1234 1234" />
                <span class="errorCaducidad" style="color:red;"></span>
                <input id="caducidad" type="text" placeholder="MM / YY" />
                <span class="errorCVV" style="color:red;"></span>
                <input id="cvv" type="text" placeholder="CVV" />
             </div>
                <div class="forms-celds">
                <label>Titular de la tarjeta</label>
                <input id="titular" type="text" placeholder="Nombre Apellido" />
             </div>
             </form>
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
                        <p><span>Entradas:</span> <% out.print(cantidad_normal); %> Adultos , <% out.print(cantidad_nino); %> niños </p>
                        <p><span>Sala:</span> <% out.print(nombre_sala); %></p>
                        <p><span>Asiento:</span> <% out.print(posicionButacas); %> </p>
                    </div>
                </div>
                <p>Se realizará un cargo por servicio por cada entrada dentro de la orden</p>
                <div class="total">
                    <h2>Total (IVA incluido) : </h2>
                    <h4><% out.print(preciototal); %>€</h4>
                </div>
               <form id="formulariotarjeta" action="/cinexin/pago" method="post">
                    <input id="inputemail" name="email" type="text" value=""  hidden/>
                    <input id="inputnumeroTarjeta" name="numeroTarjeta" type="text" value=""  hidden/>
                    <input id="inputcaducidad" name="caducidad" type="text" value=""  hidden/>
                    <input id="inputcvv" name="cvv" type="text" value=""  hidden/>
                    <input id="inputtitular" name="titular" type="text" value=""  hidden/>

                    <button class="enviar" >Pagar ahora</button>
                </form> 
            </section>

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

<script src="/cinexin/scripts/pasarelapago.js"></script>
<script>
    const fecha = <%=timerDate.getTime()%>;
    const today = new Date();

    
    const newYear = new Date(fecha);
    let dif = (newYear - today);
    let minutos = Math.round((dif/1000)/60);


    var time_in_minutes = minutos ;
    var current_time = Date.parse(new Date());
    var deadline = new Date(current_time + time_in_minutes*60*1000);


    function time_remaining(endtime){
        var t = Date.parse(endtime) - Date.parse(new Date());
        var seconds = Math.floor( (t/1000) % 60 );
        var minutes = Math.floor( (t/1000/60) % 60 );
        var hours = Math.floor( (t/(1000*60*60)) % 24 );
        var days = Math.floor( t/(1000*60*60*24) );
        return {'total':t, 'days':days, 'hours':hours, 'minutes':minutes, 'seconds':seconds};
    }
    function run_clock(id,endtime){
        var clock = document.getElementById(id);
        function update_clock(){
            var t = time_remaining(endtime);
            clock.innerHTML = '<p style="color:red"> Tienes '+t.minutes+'minutos y '+t.seconds+' segundos para realizar el pago</p>';
            if(t.total<=0){ 
                clearInterval(timeinterval);
                clock.innerHTML="<p style='color:red'>Tiempo terminado, por favor, haga el proceso de nuevo. <a href='/cinexin/seleccionbutacas.jsp'>Ir a seleccion de butacas</a></p>"
                 }
        }
        update_clock(); // run function once at first to avoid delay
        var timeinterval = setInterval(update_clock,1000);
    }
    run_clock('clockdiv',deadline);

</script>
<script type="text/javascript" src="https://cdn.weglot.com/weglot.min.js"></script>

<script>
    Weglot.initialize({
        api_key: 'wg_57eb3e2a1f5c2734a5fc4ba30aec9df54'
    });
</script>
</body>
</html>