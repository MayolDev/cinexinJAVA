const input = document.querySelector(".id_sala");

const mostrador = document.querySelector(".mostrardatos");
const tabla = document.querySelector(".mostrartabla");
	
	const HandleEvent = (e) => {

        e.preventDefault();
		mostrador.innerHTML = "";
	fetch('/cinexin/api?peticion=butaca&id_sala=' + input.value )
	.then(response => response.json())
	.then(data => {
		const result = data.filter(dato => dato !== null);


        const columnA = result.filter(dato => dato.columna === "A" || dato.columna === "a");
        const columnB = result.filter(dato => dato.columna === "B" || dato.columna === "b");
        const columnC = result.filter(dato => dato.columna === "C" || dato.columna === "c");
        const columnD = result.filter(dato => dato.columna === "D" || dato.columna === "d");

        let content = "";


		if(result.length > 0){

            if(columnA.length > 0) {

                content = content + "<div class='columnA'>";
                content = content + "<span>A</span>";

                columnA.sort((a,b) => a.fila - b.fila).map((asiento)=> {
                    content = content + "<div class='asiento'>";

                    content = content + "<span>"+ asiento.fila +"</span>";

                   
                    content = content + "<img src='/cinexin/images/sillas/red-chair.png' width: '40px' height: '40px'  />"
                    
                    content = content + "</div>";

                })

                content = content + "</div>";


            }

            if(columnB.length > 0) {

                content = content + "<div class='columnB'>";
                content = content + "<span>B</span>";

                columnB.sort((a,b) => a.fila - b.fila).map((asiento)=> {
                    content = content + "<div class='asiento'>";

                    content = content + "<span>"+ asiento.fila +"</span>";

                    content = content + "<img src='/cinexin/images/sillas/red-chair.png' width: '40px' height: '40px'  />"
                    
    
                    content = content + "</div>";

                })

                content = content + "</div>";


            }


            if(columnC.length > 0) {

                content = content + "<div class='columnC'>";
                content = content + "<span>C</span>";

                columnC.sort((a,b) => a.fila - b.fila).map((asiento)=> {
                    content = content + "<div class='asiento'>";

                    content = content + "<span>"+ asiento.fila +"</span>";

                    
                    content = content + "<img src='/cinexin/images/sillas/red-chair.png' width: '40px' height: '40px'  />"
                    
    
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

                    content = content + "<img src='/cinexin/images/sillas/red-chair.png' width: '40px' height: '40px'  />"
                    
    
                    content = content + "</div>";

                })

                content = content + "</div>";


            }
            mostrador.innerHTML = content;  


            content = "<thead>" 
                
            +"<tr>"
            +"<th>id</th>"
            +"<th>id_sala</th>"
            +"<th>tipo</th>"
            +"<th>fila</th>"
            +"<th>columna</th>"
            +"<th>&nbsp;</th>"
            +"<th>&nbsp;</th>"

            +"</tr>"
            +"</thead>"
            +"</tbody>";

            result.sort((a, b) => a.id - b.id).map((asiento) => {
                content = content + "<tr> " 
                + "<td>" + asiento.id + "</td>"
                + "<td>" + asiento.id_sala + "</td>"
                + "<td>" + asiento.tipo + "</td>"
                + "<td>" + asiento.fila + "</td>"
                + "<td>" + asiento.columna + "</td>"


                + "<td><form method='post' action='/cinexin/administracion/eliminarbutaca'>"
                + "<input type='submit' value='Borrar'/>"
                + "<input type='hidden' value='" + asiento.id + "' name='id' />"
                + "</form>"
                +"</td>"
                +"<td>"
                + "<form method='get' action='/cinexin/administracion/modificarbutaca'>"
                + "<input type='submit' value='Modificar'/>"
                + "<input type='hidden' value='" + asiento.id+ "' name='id' />"
                + "<input type='hidden' value='" + asiento.id_sala+ "' name='id_sala' />"
                + "<input type='hidden' value='" + asiento.tipo+ "' name='tipo' />"
                + "<input type='hidden' value='" + asiento.fila+ "' name='fila' />"
                + "<input type='hidden' value='" + asiento.columna+ "' name='columna' />"

                + "</form></td>"
                + "</tr>"



            })

            content = content + "</tbody></table>";


            tabla.innerHTML = content;



   

            

		}else{
            mostrador.innerHTML = "<h2>No hay resultados para mostrar</h2>";
        }
		
		
		
		
	}
	);
	
	
	
		
		
	}