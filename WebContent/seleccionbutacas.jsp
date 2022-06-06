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

if(sesion.getAttribute("cantidad_nino") == null || sesion.getAttribute("cantidad_normal") == null || sesion.getAttribute("preciototal") == null 
|| sesion.getAttribute("id_sala") == null || sesion.getAttribute("nombre_pelicula") == null || sesion.getAttribute("nombre_cine") == null 
|| sesion.getAttribute("hora_entrada") == null || sesion.getAttribute("fecha") == null || sesion.getAttribute("foto_pelicula") == null 
|| sesion.getAttribute("id_sesion") == null ){
    response.sendRedirect("/cinexin/");
}

if(sesion.getAttribute("timer") != null ){
    if((boolean)sesion.getAttribute("timer") == true ){
        response.sendRedirect("pasarelapago.jsp");
    }
}

long preciototal;

preciototal = (long) sesion.getAttribute("preciototal");

String nombre_pelicula, nombre_cine, nombre_sala, id_sala;
Time hora_entrada;
Date fecha;
byte [] bfoto;
int cantidad_nino, cantidad_normal, id_sesion, cantidad_total;
String error = "";
cantidad_nino = (int) sesion.getAttribute("cantidad_nino");
cantidad_normal = (int) sesion.getAttribute("cantidad_normal");
id_sala = (String) sesion.getAttribute("id_sala");

nombre_pelicula = (String) sesion.getAttribute("nombre_pelicula");
nombre_cine = (String) sesion.getAttribute("nombre_cine");
hora_entrada = (Time) sesion.getAttribute("hora_entrada");
fecha = (Date) sesion.getAttribute("fecha");
bfoto = (byte[]) sesion.getAttribute("foto_pelicula");
if(sesion.getAttribute("nombre_sala") != null){
nombre_sala = (String) sesion.getAttribute("nombre_sala");
}else{
nombre_sala = (String) request.getAttribute("nombre_sala");
sesion.setAttribute("nombre_sala", nombre_sala);

}
id_sesion = (int) sesion.getAttribute("id_sesion");
cantidad_total = cantidad_nino + cantidad_normal;
if(request.getAttribute("error") != null){
    error = (String)request.getAttribute("error");
}



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
    <link rel="stylesheet" href="/cinexin/css/seleccion-butacas.css">

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
                 <%if(request.getAttribute("error") != null){
            out.print("<small style='color:red; font-weight: bold'>"+error +"</small>");
        }%>
                <h1>Selecciona tus asientos</h1>
                <p>Para cambiar tu lugar asignado da clic en el asiento deseado.</p>
                <div class="asientos-info">
                    <div>
                        <img src="/cinexin/images/sillas/cian-chair.png" alt="">
                        <span>Selección</span>
                    </div>
                    <div>
                        <img src="/cinexin/images/sillas/black-chair.png" alt="">
                        <span>Asientos normales</span>
                    </div>
                    <div>
                        <img src="/cinexin/images/sillas/red-chair.png" alt="">
                        <span>Ocupados</span>
                    </div>
                </div>
                <hr>

                <div class="mostrardatos">
                </div >
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
                        <p><span>Asiento:</span> <span class="asientos"></span> </p>
                    </div>
                </div>
                <p>Se realizará un cargo por servicio por cada entrada dentro de la orden</p>
                <div class="total">
                    <h2>Total (IVA incluido) : </h2>
                    <h4><% out.print(preciototal); %>€</h4>
                </div>
                <form action="/cinexin/pasarelapago" method="post">
                    <input id="butacas" name="butacas" type="text" value=""  hidden/>
                    <input id="butacasPosicion" name="posicion" type="text" value="" hidden/>
                    <button class="enviar" disabled=true>Continuar</button>
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

        <script>
    const mostrador = document.querySelector(".mostrardatos");
    const butacasPosicion = document.querySelector("#butacasPosicion");


    fetch('/cinexin/api?peticion=butaca&id_sala=' + <% out.print(id_sala); %> )
	.then(response => response.json())
	.then(data => {
        console.log(data);

		const result = data.filter(dato => dato !== null);


        const columnA = result.filter(dato => dato.columna === "A" || dato.columna === "a");
        const columnB = result.filter(dato => dato.columna === "B" || dato.columna === "b");
        const columnC = result.filter(dato => dato.columna === "C" || dato.columna === "c");
        const columnD = result.filter(dato => dato.columna === "D" || dato.columna === "d");


        fetch('/cinexin/api?peticion=butacasocupadas&id_sesion=' +  <% out.print(id_sesion); %>)
	    .then(response =>  response.json())
        .then( (data) => {
            if(data != null && data.error != "error"){
                const butacasocupadas = data.filter(dato => dato !== null);

                const idsbutacasocupadas = butacasocupadas.map((asiento) => {return asiento.id_butaca});
                console.log(idsbutacasocupadas);


                if(result.length > 0){

                        if(columnA.length > 0) {

                            content = content + "<div class='columnA'>";
                            content = content + "<span>A</span>";

                            columnA.sort((a,b) => a.fila - b.fila).map((asiento)=> {
                                console.log(asiento);

                                    if(idsbutacasocupadas.indexOf(asiento.id) != -1){
                                        content = content + "<div class='asiento'>";
                                        content = content + "<span>"+ asiento.fila +"</span>";
                                        content = content + "<img class='inactivo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/red-chair.png' width='40px' height='40px'  />"
                                        content = content + "</div>";
                                    }else{
                                        content = content + "<div class='asiento' >";
                                        content = content + "<span>"+ asiento.fila +"</span>";
                                        content = content + "<img class='activo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"'  src='/cinexin/images/sillas/black-chair.png' width='40px' height='40px'  />"
                                        content = content + "</div>";
                                    }

                            });

                            content = content + "</div>";
                        }

                        if(columnB.length > 0) {

                            content = content + "<div class='columnB'>";
                            content = content + "<span>B</span>";

                            columnB.sort((a,b) => a.fila - b.fila).map((asiento)=> {
                                    if(idsbutacasocupadas.indexOf(asiento.id) != -1){
                                        content = content + "<div class='asiento' >";
                                        content = content + "<span>"+ asiento.fila +"</span>";
                                        content = content + "<img class='inactivo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/red-chair.png' width='40px' height='40px'  />"
                                        content = content + "</div>";
                                    }else{
                                        content = content + "<div class='asiento' >";
                                        content = content + "<span>"+ asiento.fila +"</span>";
                                        content = content + "<img class='activo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/black-chair.png' width='40px' height='40px'  />"
                                        content = content + "</div>";
                                    }
                            

                            });

                            content = content + "</div>";


                        }



                        if(columnC.length > 0) {

                                content = content + "<div class='columnC'>";
                                content = content + "<span>C</span>";

                                columnC.sort((a,b) => a.fila - b.fila).map((asiento)=> {
                                    if(idsbutacasocupadas.indexOf(asiento.id) != -1){
                                            content = content + "<div class='asiento'>";
                                            content = content + "<span>"+ asiento.fila +"</span>";
                                            content = content + "<img class='inactivo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/red-chair.png' width='40px' height='40px'  />"
                                            content = content + "</div>";
                                    }else{
                                            content = content + "<div class='asiento' >";
                                            content = content + "<span>"+ asiento.fila +"</span>";
                                            content = content + "<img class='activo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/black-chair.png' width='40px' height='40px'  />"
                                            content = content + "</div>";
                                    }

                                });

                                        content = content + "</div>";


                        }



                        if(columnD.length > 0) {

                            content = content + "<div class='columnD'>";
                            content = content + "<span>D</span>";

                            columnD.sort((a,b) => a.fila - b.fila).map((asiento)=> {
                                    if(idsbutacasocupadas.indexOf(asiento.id) != -1){
                                        content = content + "<div class='asiento'>";
                                        content = content + "<span>"+ asiento.fila +"</span>";
                                        content = content + "<img class='inactivo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/red-chair.png' width='40px' height='40px'  />"
                                        content = content + "</div>";
                                    }else{
                                        content = content + "<div class='asiento'>";
                                        content = content + "<span>"+ asiento.fila +"</span>";
                                        content = content + "<img class='activo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/black-chair.png' width='40px' height='40px'  />"
                                        content = content + "</div>";
                                    }
                                
                            })

                            content = content + "</div>";


                        }

                        mostrador.innerHTML = content;  


                }else{

                    mostrador.innerHTML = "<h2>No hay butacas para mostrar</h2>";
                }

            }else{

                        if(columnA.length > 0) {

                            content = content + "<div class='columnA'>";
                            content = content + "<span>A</span>";

                            columnA.sort((a,b) => a.fila - b.fila).map((asiento)=> {

                                content = content + "<div class='asiento' >";
                                content = content + "<span>"+ asiento.fila +"</span>";
                                content = content + "<img class='activo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"'  src='/cinexin/images/sillas/black-chair.png' width='40px' height='40px'  />"
                                content = content + "</div>";
                                    

                           })
                           content = content + "</div>";

                        }

                        if(columnB.length > 0) {

                            content = content + "<div class='columnB'>";
                            content = content + "<span>B</span>";

                            columnB.sort((a,b) => a.fila - b.fila).map((asiento)=> {

                                    content = content + "<div class='asiento' >";
                                    content = content + "<span>"+ asiento.fila +"</span>";
                                    content = content + "<img class='activo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/black-chair.png' width='40px' height='40px'  />"
                                    content = content + "</div>";
                                    
                            

                            })

                            content = content + "</div>";


                        }



                        if(columnC.length > 0) {

                            content = content + "<div class='columnC'>";
                            content = content + "<span>C</span>";

                            columnC.sort((a,b) => a.fila - b.fila).map((asiento)=> {

                                content = content + "<div class='asiento' >";
                                content = content + "<span>"+ asiento.fila +"</span>";
                                content = content + "<img class='activo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/black-chair.png' width='40px' height='40px'  />"
                                content = content + "</div>";
                                                
                            })

                            content = content + "</div>";


                        }



                        if(columnD.length > 0) {

                            content = content + "<div class='columnD'>";
                            content = content + "<span>D</span>";

                            columnD.sort((a,b) => a.fila - b.fila).map((asiento)=> {

                                content = content + "<div class='asiento'>";
                                content = content + "<span>"+ asiento.fila +"</span>";
                                content = content + "<img class='activo' id='"+asiento.columna+asiento.fila +"' data-id='"+ asiento.id +"' src='/cinexin/images/sillas/black-chair.png' width='40px' height='40px'  />"
                                content = content + "</div>";
                                    
                                
                            })

                            content = content + "</div>";


                        }

                        mostrador.innerHTML = content;  



            }


            const butacasActivas = document.querySelectorAll(".activo");
            const numAsientos = document.querySelector(".asientos");
            const butacas = document.querySelector("#butacas");
            const enviar = document.querySelector(".enviar");
            let arrasientos = [];
            let arrids = [];
            butacasActivas.forEach(butaca => {
                butaca.addEventListener("click", (e) => {
                      e.stopPropagation();
                    const asiento = e.target.id;
                    const asientoid = e.target.dataset.id;

                    if(arrasientos.length  < <%=cantidad_total %>){

                       if( arrasientos.indexOf(asiento) == -1){
                            arrasientos.push(asiento);
                            arrids.push(asientoid);
                            e.target.src="/cinexin/images/sillas/cian-chair.png"
                            numAsientos.innerHTML = arrasientos;
                            butacasPosicion.value=arrasientos;
                            butacas.value = arrids;

                       }else{
                           arrasientos.splice(arrasientos.indexOf(asiento), 1);
                           arrids.splice(arrids.indexOf(asientoid), 1);
                            e.target.src="/cinexin/images/sillas/black-chair.png"
                            numAsientos.innerHTML = arrasientos;
                            butacasPosicion.value=arrasientos;

                            butacas.value = arrids;


                       }   

                       if(arrasientos.length +1 == <% out.print(cantidad_total); %>){
                           butacas.value = arrids;
                            enviar.disabled = false;
                       } 

                       
                    }else{

                        butacas.value = arrids;
                        enviar.disabled = false;

                        if(arrasientos.indexOf(asiento) != -1){
                            arrasientos.splice(arrasientos.indexOf(asiento), 1);
                            arrids.splice(arrids.indexOf(asientoid), 1);
                            e.target.src="/cinexin/images/sillas/black-chair.png"
                            numAsientos.innerHTML = arrasientos;
                            butacasPosicion.value=arrasientos;

                            butacas.value = arrids;

                            enviar.disabled = true;

                        } 


                    }
                
                    if(arrasientos.length === <%=cantidad_total %>){
                        enviar.disabled = false;

                    }
              




                });
                
            })//dd


        })


     let content = "";



		
		
	});
        
        </script>

</body>
</html>