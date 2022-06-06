const input = document.querySelector(".id_cartelera");

const mostrador = document.querySelector(".mostrardatos");
const tabla = document.querySelector(".mostrartabla");
const tabla2 = document.querySelector(".mostrartabla2");

	
	const HandleEvent = (e) => {

        e.preventDefault();
		mostrador.innerHTML = "";
	fetch('/cinexin/api?peticion=contenidocartelera&id_cartelera=' + input.value)
	.then(response => response.json())
	.then(data => {
		const result = data.filter(dato => dato !== null);

		console.log(result)

        let content = "";


		if(result.length > 0){

            content = "<thead>" 
                
            +"<tr>"
            +"<th>id_cartelera</th>"
            +"<th>id_pelicula</th>"
            +"<th>&nbsp;</th>"

            +"</tr>"
            +"</thead>"
            +"</tbody>";

            result.sort((a, b) => a.id_pelicula - b.id_pelicula).map((contenido) => {
                content = content + "<tr> " 
                + "<td>" + contenido.id_cartelera + "</td>"
                + "<td>" + contenido.id_pelicula + "</td>"
  


                + "<td><form method='post' action='/cinexin/administracion/eliminarcontenido'>"
                + "<input type='submit' value='Borrar'/>"
                + "<input type='hidden' value='" + contenido.id_cartelera + "' name='id_cartelera' />"
                + "<input type='hidden' value='" + contenido.id_pelicula + "' name='id_pelicula' />"

                + "</form>"
                +"</td>"
                + "</tr>"



            })

            content = content + "</tbody></table>";


            tabla.innerHTML = content;


            const ids = result.map((contenido) => {
                return contenido.id_pelicula;
            })

            fetch('/cinexin/api?peticion=pelicula&ids_peliculas=' + ids)
            .then(response => response.json())
            .then(data => {
                console.log(data)

                const result = data.filter(dato => dato !== null);

		        console.log(result)

                if(result.length > 0){

                    content = "<thead>" 
                
                    +"<tr>"
                    +"<th>id</th>"
                    +"<th>imagen</th>"
                    +"<th>nombre</th>"
                    +"<th>duracion</th>"
                    +"<th>director</th>"
                    +"<th>trailer</th>"
                    +"<th>categoria</th>"
                    +"<th>actores</th>"
                    +"<th>calificacion</th>"
                    +"<th>&nbsp;</th>"
                    +"</tr>"
                    +"</thead>"
                    +"</tbody>";


                    result.map((pelicula) => {
                      
                        content = content + "<tr> " 
                        + "<td>" + pelicula.id + "</td>"
                        +"<td> <img style='border-radius: 5px' border = '0' src = 'data:image/png;base64," + pelicula.imagen+ " ' width = '200' height = '220' ></td>"
                        + "<td>" + pelicula.nombre + "</td>"
                        + "<td>" + pelicula.duracion + "</td>"
                        + "<td>" + pelicula.director + "</td>"
                        + "<td> <iframe width='560' height='315' src='https://www.youtube.com/embed/"+ pelicula.trailer +"' title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe></td>"
                        + "<td>" + pelicula.categoria + "</td>"
                        + "<td>" + pelicula.actores + "</td>"
                        + "<td>" + pelicula.calificacion + "</td>"

                        +"<td>"
                        + "<form method='get' action='/cinexin/administracion/modificarpelicula'>"
                        + "<input type='submit' value='Modificar'/>"
                        + "<input type='hidden' value='" + pelicula.id+ "' name='id' />"
                        + "<input type='hidden' value='" + pelicula.nombre+ "' name='nombre' />"
                        + "<input type='hidden' value='" + pelicula.duracion+ "' name='duracion' />"
                        + "<input type='hidden' value='" + pelicula.director+ "' name='director' />"
                        + "<input type='hidden' value='" + pelicula.trailer+ "' name='trailer' />"
                        + "<input type='hidden' value='" + pelicula.categoria+ "' name='categoria' />"
                        + "<input type='hidden' value='" + pelicula.actores+ "' name='actores' />"
                        + "<input type='hidden' value='" + pelicula.calificacion+ "' name='calificacion' />"
        
        
                        + "</form></td>"
                        + "</tr>"
        

                    })

                    content = content + "</tbody></table>";

                    tabla2.innerHTML = content;




                }



            })

   

            

		}else{
            mostrador.innerHTML = "<h2>No hay resultados para mostrar</h2>";
        }
		
		
		
		
	}
	);
	
	
	
		
		
	}