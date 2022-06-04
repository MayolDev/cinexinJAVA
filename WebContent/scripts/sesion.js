const input = document.querySelector(".id_sala");
const input2 = document.querySelector(".fecha");

const tabla = document.querySelector(".mostrartabla");
	
	const HandleEvent = (e) => {

        e.preventDefault();
		tabla.innerHTML = "";
	fetch('/cinexin/api?peticion=sesion&id_sala=' + input.value + "&fecha=" + input2.value)
	.then(response => response.json())
	.then(data => {
		const result = data.filter(dato => dato !== null);

        let content = "";


		if(result.length > 0){


            content = "<thead>" 
                
            +"<tr>"
            +"<th>id</th>"
            +"<th>id_pelicula</th>"
            +"<th>id_sala</th>"
            +"<th>fecha</th>"
            +"<th>hora_entrada</th>"
            +"<th>hora_salida</th>"
            +"<th>&nbsp;</th>"
            +"<th>&nbsp;</th>"

            +"</tr>"
            +"</thead>"
            +"</tbody>";

            result.map((horario) => {
                let date = new Date(horario.fecha);
                content = content + "<tr> " 
                + "<td>" + horario.id + "</td>"
                + "<td>" + horario.id_pelicula + "</td>"
                + "<td>" + horario.id_sala + "</td>"
                + "<td>" + date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear()  + "</td>"
                + "<td>" + horario.hora_entrada + "</td>"
                + "<td>" + horario.hora_salida + "</td>"



                + "<td><form method='post' action='/cinexin/administracion/eliminarsesion'>"
                + "<input type='submit' value='Borrar'/>"
                + "<input type='hidden' value='" + horario.id + "' name='id' />"
                + "</form>"
                +"</td>"
                +"<td>"
                + "<form method='get' action='/cinexin/administracion/modificarsesion'>"
                + "<input type='submit' value='Modificar'/>"
                + "<input type='hidden' value='" + horario.id+ "' name='id' />"
                + "<input type='hidden' value='" + horario.id_pelicula+ "' name='id_pelicula' />"
                + "<input type='hidden' value='" + horario.id_sala+ "' name='id_sala' />"
                + "<input type='hidden' value='" + horario.fecha+ "' name='fecha' />"
                + "<input type='hidden' value='" + horario.hora_entrada+ "' name='hora_entrada' />"
                + "<input type='hidden' value='" + horario.hora_salida+ "' name='hora_salida' />"


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