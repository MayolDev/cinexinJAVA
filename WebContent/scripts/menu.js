const provincia = document.querySelector("#provincia");
const ciudad = document.querySelector("#ciudad");
const cine = document.querySelector("#cine");
const fecha = document.querySelector("#fecha");
const main = document.querySelector("#cartelera");

let idCine = 0;

fetch('/cinexin/api?peticion=provincia')
.then(response => response.json())
.then(data => {

    console.log(data);

    data.map((prov)=> {
        const option = document.createElement('option');
        option.value = prov.id_provincia;
        option.innerHTML = prov.nombre;
        provincia.appendChild(option);


    })




})


provincia.addEventListener("change", (e) => {
    console.log(e.target.value);

    ciudad.innerHTML = ""

    fetch('/cinexin/api?peticion=ciudad&id_provincia=' + e.target.value)
    .then(response => response.json())
    .then(data => {

        console.log(data);

        const option = document.createElement('option');
        option.value = "0";
        option.innerHTML = "Seleccione ciudad";
        ciudad.appendChild(option);

        data.map((city) => {
            if(city !== null){
                const option = document.createElement('option');
                option.value = city.cod_postal;
                option.innerHTML = city.nombre;
                ciudad.appendChild(option);
            }

        })

    })

})


ciudad.addEventListener("change", (e) => {
    console.log(e.target.value);

    cine.innerHTML = ""

    fetch('/cinexin/api?peticion=cine&id_ciudad=' + e.target.value)
    .then(response => response.json())
    .then(data => {

        console.log(data);
        const option = document.createElement('option');
        option.value = "0";
        option.innerHTML = "Seleccione cine";
        cine.appendChild(option);

        data.map((cinema) => {
            if(cinema !== null){
                const option = document.createElement('option');
                option.value = '{"id_cartelera" : ' + cinema.id_cartelera +' , "id_cine" : ' + cinema.id + '}';
                option.innerHTML = cinema.nombre;
                cine.appendChild(option);
            }

        })

    })

})


cine.addEventListener("change" , (e) => {
    const value = (JSON.parse(e.target.value));
    idCine = value.id_cine;
    main.innerHTML = "";


    fetch('/cinexin/api?peticion=contenidocartelera&id_cartelera=' + value.id_cartelera)
    .then(response => response.json())
    .then(data => {
        console.log(data);

        const ids = data.map((contenido) => {
            return contenido.id_pelicula;
        })

        fetch('/cinexin/api?peticion=pelicula&ids_peliculas=' + ids)
            .then(response => response.json())
            .then(data => {
                main.innerHTML = "<h1 style='text-align:center'>Cartelera</h1>";
                const content = document.createElement('div');

                content.className = "cartelera";


                data.map((pelicula) => {
                    if(pelicula !== null){
                        const div = document.createElement('div');

                        div.innerHTML = "<a href='/cinexin/pelicula-cartelera?id="+pelicula.id+"&idcine="+idCine+"'> <img style='border-radius: 5px' border = '0' src = 'data:image/png;base64," + pelicula.imagen+ " ' width = '200' height = '220' >  </a>"

                        content.appendChild(div);
                    }



                })
                main.appendChild(content);

            })

    })



})