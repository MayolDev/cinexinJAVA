const input = document.querySelector(".id_cine");
	const mostrador = document.querySelector(".mostrardatos");
	
	const HandleEvent = (e) => {
        e.preventDefault();

		mostrador.innerHTML = "";
	fetch('/cinexin/api?peticion=sala&id_cine=' + input.value)
	.then(response => response.json())
	.then(data => {
		console.log(data)
		const result = data.filter(dato => dato !== null);
		if(result.length > 0){

            mostrador.innerHTML= " <thead>" 
                
            +"<tr>"
            +"<th>id</th>"
            +"<th>id_cine</th>"
            +"<th>nombre</th>"
            +"<th>&nbsp;</th>"
            +"<th>&nbsp;</th>"

            +"</tr>"
            +"</thead>"
            +"</tbody>";
			
			result.map((dato, i) => {
				
				mostrador.innerHTML = mostrador.innerHTML + "<tr> " 
                + "<td>" + dato.id + "</td>"
                + "<td>" + dato.id_cine + "</td>"
                + "<td>" + dato.nombre + "</td>"
                + "<td><form method='post' action='/cinexin/administracion/eliminarsala'>"
                + "<input type='submit' value='Borrar'/>"
                + "<input type='hidden' value='" + dato.id + "' name='id' />"
                + "</form>"
                +"</td>"
                +"<td>"
                + "<form method='get' action='/cinexin/administracion/modificarsala'>"
                + "<input type='submit' value='Modificar'/>"
                + "<input type='hidden' value='" + dato.id+ "' name='id' />"
                + "<input type='hidden' value='" + dato.id_cine+ "' name='id_cine' />"
                + "<input type='hidden' value='" + dato.nombre+ "' name='nombre' />"
                + "</form></td>"
                + "</tr>"
				
			});

            mostrador.innerHTML = mostrador.innerHTML + "</tbody></table>"

		}else{
            mostrador.innerHTML = "<h2>No hay resultados para mostrar</h2>";
        }
		
		
		
		
	}
	);
	
	
	
		
		
	}