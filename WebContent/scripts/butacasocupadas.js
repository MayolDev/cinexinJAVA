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
            +"<th>id_butaca</th>"
            +"<th>id_sesion</th>"
            +"<th>id_ticket</th>"
  

            +"</tr>"
            +"</thead>"
            +"</tbody>";

            result.map((horario) => {
                content = content + "<tr> " 
                + "<td>" + horario.id_butaca + "</td>"
                + "<td>" + horario.id_sesion + "</td>"
                + "<td>" + horario.id_ticket + "</td>"

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