const input = document.querySelector(".id_sesion");

const tabla = document.querySelector(".mostrartabla");
	
	const HandleEvent = (e) => {

        e.preventDefault();
		tabla.innerHTML = "";
	fetch('/cinexin/api?peticion=butacasocupadas&id_sesion=' + input.value )
	.then(response => response.json())
	.then(data => {
		const result = data.filter(dato => dato !== null);

        let content = "";


		if(result.length > 0){


            content = "<thead>" 
                
            +"<tr>"
            +"<th>id</th>"
            +"<th>id_butaca</th>"
            +"<th>id_sesion</th>"
            +"<th>id_ticket</th>"
            +"<th>&nbsp;</th>"
            +"<th>&nbsp;</th>"

            +"</tr>"
            +"</thead>"
            +"</tbody>";

            result.map((horario) => {
                content = content + "<tr> " 
                + "<td>" + horario.id + "</td>"
                + "<td>" + horario.id_butaca + "</td>"
                + "<td>" + horario.id_sesion + "</td>"
                + "<td>" + horario.id_ticket + "</td>"




                + "<td><form method='post' action='/cinexin/administracion/eliminarbutacaocupada'>"
                + "<input type='submit' value='Borrar'/>"
                + "<input type='hidden' value='" + horario.id + "' name='id' />"
                + "</form>"
                +"</td>"
                +"<td>"
                + "<form method='get' action='/cinexin/administracion/modificarbutacaocupada'>"
                + "<input type='submit' value='Modificar'/>"
                + "<input type='hidden' value='" + horario.id+ "' name='id' />"
                + "<input type='hidden' value='" + horario.id_butaca+ "' name='id_butaca' />"
                + "<input type='hidden' value='" + horario.id_sesion+ "' name='id_sesion' />"
                + "<input type='hidden' value='" + horario.id_ticket+ "' name='id_ticket' />"



                + "</form></td>"
                + "</tr>"



            })




            content = content + "</tbody></table>";


            tabla.innerHTML = content;



   

            

		}else{
            tabla.innerHTML = "<h2>No hay resultados para mostrar</h2>";
        }
		
		
		
		
	}
	);
	
	
	
		
		
	}